package fr.istic.m1.fstorm.beans;

import fr.istic.m1.fstorm.PragmaLexicalUnit;

/**
 * Representation of an identifier in the context of the lexical analysis of fstorm pragmas.
 * @author Kévin Le Bon
 */
public class PragmaIdentifier extends PragmaLexicalUnit {
	/**
	 * Default constructor.
	 */
	public PragmaIdentifier() {
		
	}
	
	/**
	 * Constructor using a string.
	 * @param id the text value of the identifier.
	 */
	public PragmaIdentifier(String id) {
		this.setValue(id);
	}
	
	/**
	 * Tests if this lexical unit represents an identifier.
	 * In the case of a PragmaIdentifier, it always returns true.
	 * @return always returns true.
	 */
	@Override
	public boolean isIdentifier() {
		return true;
	}

	/**
	 * Tests if this lexical unit is an identifier and
	 * if its value is the same as the one given.
	 * @param id the value to compare this lexical unit to.
	 * @return true if both values are equal, false otherwise.
	 */
	@Override
	public boolean isIdentifier(String id) {
		return id.equals(this.getValue());
	}

	/**
	 * Tests if this lexical unit represents a symbol.
	 * @return always returns false.
	 */
	@Override
	public boolean isSymbol() {
		return false;
	}

	/**
	 * Tests if this lexical unit represents a symbol which value is the same as this ones.
	 * @return always returns false.
	 */
	@Override
	public boolean isSymbol(String sym) {
		return false;
	}
}