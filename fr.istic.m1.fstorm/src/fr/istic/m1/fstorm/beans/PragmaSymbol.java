package fr.istic.m1.fstorm.beans;

import fr.istic.m1.fstorm.PragmaLexicalUnit;

public class PragmaSymbol extends PragmaLexicalUnit {
	public PragmaSymbol() {
		
	}
	
	public PragmaSymbol(String sym) {
		this.setValue(sym);
	}
	
	@Override
	public boolean isIdentifier() {
		return false;
	}

	@Override
	public boolean isIdentifier(String id) {
		return false;
	}

	@Override
	public boolean isSymbol() {
		return true;
	}

	@Override
	public boolean isSymbol(String sym) {
		return sym.equals(this.getValue());
	}
}