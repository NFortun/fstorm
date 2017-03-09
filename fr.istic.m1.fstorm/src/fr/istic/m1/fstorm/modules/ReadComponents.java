package fr.istic.m1.fstorm.modules;

import java.util.ArrayList;
import java.util.List;

import fr.istic.m1.fstorm.beans.StormComponent;
import fr.istic.m1.fstorm.beans.StormComponentType;
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

class InvalidPragmaSyntaxException extends RuntimeException {
	private static final long serialVersionUID = -3387058830838843908L;
	private String message;
	
	public InvalidPragmaSyntaxException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return "Pragma syntax error : " + message;
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
	
	public List<StormComponent> compute() {
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
					}
					
					//! TODO voir Xtend pour la génération
					
					components.add(component);
				}
			}
			
			catch(InvalidPragmaSyntaxException e) {
				System.err.println(e.toString());
			}
		}
		
		return components;
	}

	private PragmaInformations parsePragma(PragmaAnnotation annotation) throws InvalidPragmaSyntaxException {
		String content = null;
		PragmaInformations infos = null;
		StormComponentType type = StormComponentType.SPOUT;
		
		for(String s : annotation.getContent()) {
			if(s.startsWith("fstorm spout")) {
				content = s;
				type = StormComponentType.SPOUT;
				break;
			}
			
			else if(s.startsWith("fstorm bolt")) {
				content = s;
				type = StormComponentType.BOLT;
				break;
			}
		}
		
		if(content != null) {
			infos = new PragmaInformations();
			infos.setNodeType(type);
		}

		return infos;
	}
}