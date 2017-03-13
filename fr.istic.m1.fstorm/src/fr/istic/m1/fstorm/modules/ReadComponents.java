package fr.istic.m1.fstorm.modules;

import java.util.ArrayList;
import java.util.List;

import fr.istic.m1.fstorm.FStormPragma;
import fr.istic.m1.fstorm.InvalidPragmaSyntaxException;
import fr.istic.m1.fstorm.ParameterCountException;
import fr.istic.m1.fstorm.PragmaLexicalUnit;
import fr.istic.m1.fstorm.beans.ComponentPragma;
import fr.istic.m1.fstorm.beans.StormComponent;
import fr.istic.m1.fstorm.beans.StormComponentType;
import fr.istic.m1.fstorm.utils.PragmaLexer;
import fr.istic.m1.fstorm.utils.PragmaLexicalException;
import fr.istic.m1.fstorm.utils.PragmaParser;
import gecos.annotations.AnnotatedElement;
import gecos.annotations.PragmaAnnotation;
import gecos.core.ProcedureSymbol;

class PragmaInformations {
	private StormComponentType nodeType;
	private List<String> paramTypes;
	private String returnType;
	
	public StormComponentType getNodeType() {
		return nodeType;
	}
	public void setNodeType(StormComponentType nodeType) {
		this.nodeType = nodeType;
	}
	public List<String> getParamTypes() {
		return paramTypes;
	}
	public void setParamTypes(List<String> paramTypes) {
		this.paramTypes = paramTypes;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
}

/**
 * ReadComponents is a sample module. When the script evaluator encounters
 * the 'ReadComponents' function, it calls the compute method.
 */
public class ReadComponents {
	private List<PragmaAnnotation> annotations;
	
	public ReadComponents(List<PragmaAnnotation> annotations) {
		this.annotations = annotations;
	}
	
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
						components.add(component);
					}
					
					//! TODO voir Xtend pour la génération
				}
			}
			
			catch(InvalidPragmaSyntaxException e) {
				System.err.println(e.toString());
			}
			
			catch(PragmaLexicalException e) {
				// Not a FStorm pragma, no nothing
			}
		}
		
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
		
		return components;
	}

	/**
	 * Function that checks whether the number of arguments the kernel takes corresponds to the number of arguments needed by the pragma.
	 * @param component the component which parameter lists are compared.
	 * @throws ParameterCountException 
	 */
	private void checkParameters(StormComponent component) throws ParameterCountException {
		int paramCount = component.getKernel().getSymbol().listParameters().size();
		int pragmaParamCount = component.getParamTypes().size();

		if(paramCount != pragmaParamCount)
			throw new ParameterCountException(pragmaParamCount, paramCount);
	}

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