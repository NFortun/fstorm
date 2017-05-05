package fr.istic.m1.fstorm.modules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import fr.irisa.cairn.tools.ecore.query.EMFUtils;
import fr.istic.m1.fstorm.FStormPragma;
import fr.istic.m1.fstorm.InvalidPragmaSyntaxException;
import fr.istic.m1.fstorm.ParameterCountException;
import fr.istic.m1.fstorm.PragmaLexicalUnit;
import fr.istic.m1.fstorm.beans.ComponentPragma;
import fr.istic.m1.fstorm.beans.FStormParameters;
import fr.istic.m1.fstorm.beans.StormComponent;
import fr.istic.m1.fstorm.beans.StormComponentType;
import fr.istic.m1.fstorm.utils.PragmaLexer;
import fr.istic.m1.fstorm.utils.PragmaLexicalException;
import fr.istic.m1.fstorm.utils.PragmaParser;
import gecos.annotations.AnnotatedElement;
import gecos.annotations.PragmaAnnotation;
import gecos.core.ProcedureSymbol;
import gecos.instrs.RetInstruction;

/**
 * Auxiliary class used to store informations from pragma annotations.
 * @author Kévin Le Bon
 *
 */
class PragmaInformations {
	/** The storm component type (spout or bolt). */
	private StormComponentType nodeType;
	/** The list of parameters' Java type. */
	private List<String> paramTypes;
	/** The function's return type (Java side). */
	private String returnType;
	
	/**
	 * Getter for the component type.
	 * @return the component type.
	 */
	public StormComponentType getNodeType() {
		return nodeType;
	}
	
	/**
	 * Setter for the component type.
	 * @param nodeType the component's type.
	 */
	public void setNodeType(StormComponentType nodeType) {
		this.nodeType = nodeType;
	}
	
	/**
	 * Getter for the parameter-type list.
	 * @return the parameter-type list.
	 */
	public List<String> getParamTypes() {
		return paramTypes;
	}
	
	/**
	 * Setter for the parameter-type list.
	 * @param paramTypes the parameter-type list.
	 */
	public void setParamTypes(List<String> paramTypes) {
		this.paramTypes = paramTypes;
	}
	
	/**
	 * Getter for the return type.
	 * @return the return type.
	 */
	public String getReturnType() {
		return returnType;
	}
	
	/**
	 * Setter for the return type.
	 * @param returnType the return type.
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
}

/**
 * ReadComponents is a GeCoS module. When the script evaluator encounters
 * the 'ReadComponents' function, it calls the compute method.
 * @author Kévin Le Bon
 */
public class ReadComponents {
	private List<PragmaAnnotation> annotations;
	private FStormParameters appParam;
	
	/**
	 * Constructor using a list of annotations in the C code.
	 * @param annotations pragma annotations detected in the C code.
	 */
	public ReadComponents(FStormParameters params, List<PragmaAnnotation> annotations) {
		this.annotations = annotations;
		this.appParam = params;
	}
	
	/**
	 * Fonction computing a list of Storm components from the given list of annotations.
	 * @return the list of components taken from the annotations.
	 * @throws ParameterCountException
	 */
	public List<StormComponent> compute() throws ParameterCountException {
		List<StormComponent> components = new ArrayList<StormComponent>();
		
		for(PragmaAnnotation annotation : annotations) {
			try {
				PragmaInformations infos = parsePragma(annotation);
				
				if(infos != null) {
					StormComponent component = new StormComponent();
					component.setNodeType(infos.getNodeType());
					component.setParamTypes(infos.getParamTypes());
					component.setReturnType(infos.getReturnType());
					
					AnnotatedElement elem = annotation.getAnnotatedElement();
					
					if(elem instanceof ProcedureSymbol) {
						ProcedureSymbol proc = (ProcedureSymbol) elem;
						component.setKernelName(proc.getName());
						component.setKernel(proc.getProcedure());
						
						checkParameters(component);
						EList<PragmaAnnotation> ann_in =
								EMFUtils.eAllContentsInstancesOf(proc, PragmaAnnotation.class);
						component.setFlat(false);
						for (PragmaAnnotation ann : ann_in) {
							if (ann.getAnnotatedElement() instanceof RetInstruction) {
								String[] ann_list = ann.getContent().get(0).split(" ");
								if (ann_list[0].equals("fstorm") && ann_list[1].equals("flat")) {
									component.setFlat(true);
								}
							}
						}
						components.add(component);
					}
				}
			}
			
			catch(InvalidPragmaSyntaxException e) {
				System.err.println(e.toString());
			}
			
			catch(PragmaLexicalException e) {
				// Not a FStorm pragma, no nothing
			}
		}
		
		if(appParam.isVerbose()){
			for(StormComponent comp : components) {
				System.out.print(comp.getNodeType().toString());
				System.out.print(" " + comp.getKernelName());
				if(!comp.getParamTypes().isEmpty()) {
					String plist = "(" + comp.getParamTypes().get(0);
					
					for(int i = 1; i < comp.getParamTypes().size(); ++i)
						plist += "," + comp.getParamTypes().get(i);
					
					plist += ")";
					System.out.print(plist);
				}
				
				System.out.println(" returns(" + comp.getReturnType() + ")");
			}
		}
		
		return components;
	}

	/**
	 * Function that checks whether the number of arguments the kernel takes corresponds to the number of arguments needed by the pragma.
	 * @param component the component which parameter lists are compared.
	 * @throws ParameterCountException 
	 */
	private void checkParameters(StormComponent component) throws ParameterCountException {
		@SuppressWarnings("unused")
		int paramCount = component.getKernel().getSymbol().listParameters().size();
		@SuppressWarnings("unused")
		int pragmaParamCount = component.getParamTypes().size();

		/*
		if(paramCount != pragmaParamCount)
			throw new ParameterCountException(pragmaParamCount, paramCount);
		*/
	}

	/**
	 * Function that take a pragma annotation, checks whether it is a valid fstorm pragma and returns the associated informations.
	 * @param annotation the annotation from which the informations are retrieved.
	 * @return the informations associated with the pragma annotation.
	 * @throws InvalidPragmaSyntaxException
	 * @throws PragmaLexicalException
	 */
	private PragmaInformations parsePragma(PragmaAnnotation annotation) throws InvalidPragmaSyntaxException, PragmaLexicalException {
		PragmaInformations infos = null;
		
		for(String s : annotation.getContent()) {
			List<PragmaLexicalUnit> tokenList = PragmaLexer.generateTokenList(s);
			FStormPragma pragma = PragmaParser.parsePragma(tokenList);
			
			if(pragma instanceof ComponentPragma) {
				ComponentPragma comp = (ComponentPragma) pragma;
				
				infos = new PragmaInformations();
				infos.setNodeType(comp.getType());
				infos.setParamTypes(comp.getParamTypes());
				infos.setReturnType(comp.getReturnType());
				
				break;
			}
		}

		return infos;
	}
}