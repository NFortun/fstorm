package fr.istic.m1.fstorm.beans;

import fr.istic.m1.fstorm.PragmaLexicalUnit;

public class PragmaIdentifier extends PragmaLexicalUnit {
	public PragmaIdentifier() {
		
	}
	
	public PragmaIdentifier(String id) {
		this.setValue(id);
	}
	
	@Override
	public boolean isIdentifier() {
		return true;
	}

	@Override
	public boolean isIdentifier(String id) {
		return id.equals(this.getValue());
	}

	@Override
	public boolean isSymbol() {
		return false;
	}

	@Override
	public boolean isSymbol(String sym) {
		return false;
	}
}