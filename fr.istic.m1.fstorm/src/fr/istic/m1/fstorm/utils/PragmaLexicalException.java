package fr.istic.m1.fstorm.utils;

public class PragmaLexicalException extends Exception {
	private static final long serialVersionUID = 4429872611116114153L;
	private String message;
	
	public PragmaLexicalException(String msg) {
		this.message = "lexical error: " + msg;
	}
	
	public String getMessage() {
		return this.message;
	}
}
