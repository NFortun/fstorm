package fr.istic.m1.fstorm;

/**
 * Exception thrown when the count of parameters asked for by the pragma is different from the kernel's arity.
 * @author Kévin Le Bon.
 */
public class ParameterCountException extends Exception {
	private static final long serialVersionUID = -10837427333783040L;
	/** Kernel's arity. */
	private int paramCount;
	/** Pragma's parameter count. */
	private int pragmaParamCount;

	/**
	 * Constructor using both counts.
	 * @param pragmaParamCount the pragma's parameter count.
	 * @param paramCount the kernel's arity.
	 */
	public ParameterCountException(int pragmaParamCount, int paramCount) {
		this.setPragmaParamCount(pragmaParamCount);
		this.setParamCount(paramCount);
	}
	
	/**
	 * Getter for the kernel's arity.
	 * @return the kernel's arity
	 */
	public int getParamCount() {
		return paramCount;
	}

	/**
	 * Setter for the kernel's arity.
	 * @param paramCount the kernel's arity.
	 */
	public void setParamCount(int paramCount) {
		this.paramCount = paramCount;
	}

	/**
	 * Getter for the pragma's parameter count.
	 * @return the pragma's parameter count.
	 */
	public int getPragmaParamCount() {
		return pragmaParamCount;
	}

	/**
	 * Setter for the pragma's parameter count.
	 * @param pragmaParamCount the pragma's parameter count.
	 */
	public void setPragmaParamCount(int pragmaParamCount) {
		this.pragmaParamCount = pragmaParamCount;
	}
}
