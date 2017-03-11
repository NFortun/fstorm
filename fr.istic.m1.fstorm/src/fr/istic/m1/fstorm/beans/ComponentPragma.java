package fr.istic.m1.fstorm.beans;

import java.util.ArrayList;
import java.util.List;

import fr.istic.m1.fstorm.FStormPragma;

public class ComponentPragma implements FStormPragma {
	private List<String> paramTypes;
	private String returnType;
	private StormComponentType type;
	
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
	public StormComponentType getType() {
		return type;
	}
	public void setType(StormComponentType type) {
		this.type = type;
	}
	
	public static ComponentPragma createNewBolt(List<String> paramTypes, String returnType) {
		ComponentPragma comp = new ComponentPragma();
		comp.setParamTypes(paramTypes);
		comp.setReturnType(returnType);
		comp.setType(StormComponentType.BOLT);
		
		return comp;
	}
	
	public static ComponentPragma createNewSpout(String returnType) {
		ComponentPragma comp = new ComponentPragma();
		comp.setParamTypes(new ArrayList<String>());
		comp.setReturnType(returnType);
		comp.setType(StormComponentType.SPOUT);
		
		return comp;
	}
}
