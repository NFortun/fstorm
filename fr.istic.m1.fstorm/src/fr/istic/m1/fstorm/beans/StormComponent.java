package fr.istic.m1.fstorm.beans;

import java.util.List;

import gecos.core.Procedure;

public class StormComponent {
	private Procedure kernel;
	private String kernelName;
	private StormComponentType nodeType;
	private List<String> paramTypes;
	private String returnType;
	
	public Procedure getKernel() {
		return kernel;
	}

	public void setKernel(Procedure kernel) {
		this.kernel = kernel;
	}

	public String getKernelName() {
		return kernelName;
	}
	
	public void setKernelName(String kernelName) {
		this.kernelName = kernelName;
	}
	
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

	public static StormComponent createNewBolt() {
		StormComponent bolt = new StormComponent();
		bolt.setNodeType(StormComponentType.BOLT);
		return bolt;
	}
	
	public static StormComponent createNewSpout() {
		StormComponent spout = new StormComponent();
		spout.setNodeType(StormComponentType.SPOUT);
		return spout;
	}
	
	/**
	 * Function generating the Java code of the spout/bolt.
	 * @return The Java code of the spout/bolt.
	 */
	public String generateJavaCode() {
		String classDeclaration = "public class " + this.kernelName + " extends ";
		
		if(this.nodeType == StormComponentType.SPOUT)
			classDeclaration += "IRichSpout {";
		else
			classDeclaration += "IRichBolt {";
		
		return classDeclaration + "\n}";
	}
}
