package fr.istic.m1.fstorm;

public class InvalidPragmaSyntaxException extends RuntimeException {
	private static final long serialVersionUID = -3387058830838843908L;
	private String message;
	
	public InvalidPragmaSyntaxException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return "Pragma syntax error : " + message;
	}
}