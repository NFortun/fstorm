package fr.istic.m1.fstorm.utils;

import java.util.ArrayList;
import java.util.List;

import fr.istic.m1.fstorm.FStormPragma;
import fr.istic.m1.fstorm.InvalidPragmaSyntaxException;
import fr.istic.m1.fstorm.PragmaLexicalUnit;
import fr.istic.m1.fstorm.beans.ComponentPragma;
import fr.istic.m1.fstorm.beans.FStormHLSPragma;

public class PragmaParser {
	public static FStormPragma parsePragma(List<PragmaLexicalUnit> tokenList) throws InvalidPragmaSyntaxException {
		List<PragmaLexicalUnit> lexer = tokenList; // copy list
		
		if(!lexer.isEmpty() && lexer.get(0).isIdentifier("fstorm")) {
			lexer.remove(0);
			
			if(lexer.isEmpty() || !lexer.get(0).isIdentifier())
				throw new InvalidPragmaSyntaxException("expected identifier \"spout\", \"bolt\", \"hls\" or \"HLS\"");
			else {
				switch(lexer.get(0).getValue()) {
					case "spout":
						lexer.remove(0);
						return parseSpout(lexer);
					case "bolt":
						lexer.remove(0);
						return parseBolt(lexer);
					case "hls":
					case "HLS":
						return new FStormHLSPragma();
					default:
						throw new InvalidPragmaSyntaxException("expected identifier \"spout\", \"bolt\", \"hls\" or \"HLS\"");
				}
			}
		}
		
		// Not a fstorm pragma. 
		return null;
	}
	
	private static FStormPragma parseBolt(List<PragmaLexicalUnit> lexer) throws InvalidPragmaSyntaxException {
		List<String> paramTypes = new ArrayList<String>();
		String returnType = "void";
		
		if(!lexer.isEmpty() && lexer.get(0).isSymbol("("))
			paramTypes = parseParamList(lexer);
		if(!lexer.isEmpty() && lexer.get(0).isIdentifier("returns"))
			returnType = parseReturns(lexer);
		
		return ComponentPragma.createNewBolt(paramTypes, returnType);
	}

	private static String parseJavaType(List<PragmaLexicalUnit> lexer) throws InvalidPragmaSyntaxException {
		if(lexer.isEmpty() || !lexer.get(0).isIdentifier())
			throw new InvalidPragmaSyntaxException("expected Java type");
		String classname = lexer.get(0).getValue();
		lexer.remove(0);
		
		return classname;
	}
	
	private static List<String> parseParamList(List<PragmaLexicalUnit> lexer) throws InvalidPragmaSyntaxException {
		// Syntax : ('JavaType', 'JavaType...')
		List<String> paramList = new ArrayList<String>();
		
		if(lexer.isEmpty() || !lexer.get(0).isSymbol("("))
			throw new InvalidPragmaSyntaxException("expected symbol \"(\"");
		lexer.remove(0);
		
		paramList.add(parseJavaType(lexer));
		
		while(!lexer.isEmpty() && lexer.get(0).isSymbol(",")) {
			lexer.remove(0);
			paramList.add(parseJavaType(lexer));
		}
		
		if(lexer.isEmpty() || !lexer.get(0).isSymbol(")"))
			throw new InvalidPragmaSyntaxException("expected symbol \")\"");
		lexer.remove(0);
		
		return paramList;
	}
	
	private static String parseReturns(List<PragmaLexicalUnit> lexer) throws InvalidPragmaSyntaxException {
		// Syntax : returns('JavaType')
		if(lexer.isEmpty() || !lexer.get(0).isIdentifier("returns"))
			throw new InvalidPragmaSyntaxException("expected keyword \"returns\"");
		lexer.remove(0);
		
		if(lexer.isEmpty() || !lexer.get(0).isSymbol("("))
			throw new InvalidPragmaSyntaxException("expected symbol \"(\"");
		lexer.remove(0);
		
		String returnType = parseJavaType(lexer);
		
		if(lexer.isEmpty() || !lexer.get(0).isSymbol(")"))
			throw new InvalidPragmaSyntaxException("expected symbol \")\"");
		lexer.remove(0);
		
		return returnType;
	}
	
	private static FStormPragma parseSpout(List<PragmaLexicalUnit> lexer) throws InvalidPragmaSyntaxException {
		return ComponentPragma.createNewSpout(parseReturns(lexer));
	}
}
