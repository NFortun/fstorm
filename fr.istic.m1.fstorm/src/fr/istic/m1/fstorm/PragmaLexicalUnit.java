package fr.istic.m1.fstorm;

public abstract class PragmaLexicalUnit {
	private String value;
	
	public abstract boolean isIdentifier();
	public abstract boolean isIdentifier(String id);
	
	public abstract boolean isSymbol();
	public abstract boolean isSymbol(String sym);
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}