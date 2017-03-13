package fr.istic.m1.fstorm;

public class ParameterCountException extends Exception {
	private static final long serialVersionUID = -10837427333783040L;
	private int paramCount;
	private int pragmaParamCount;

	public ParameterCountException(int pragmaParamCount, int paramCount) {
		this.setPragmaParamCount(pragmaParamCount);
		this.setParamCount(paramCount);
	}
	
	public int getParamCount() {
		return paramCount;
	}

	public void setParamCount(int paramCount) {
		this.paramCount = paramCount;
	}

	public int getPragmaParamCount() {
		return pragmaParamCount;
	}

	public void setPragmaParamCount(int pragmaParamCount) {
		this.pragmaParamCount = pragmaParamCount;
	}
}
