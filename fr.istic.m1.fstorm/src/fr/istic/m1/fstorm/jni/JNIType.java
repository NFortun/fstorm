package fr.istic.m1.fstorm.jni;

import fr.istic.m1.fstorm.beans.CBean;

public interface JNIType {
	public static JType javaFromString(String s) {
		switch (s) {
		case "char":
			return new JPrim(Primitive.CHAR);
		case "short":
			return new JPrim(Primitive.SHORT);
		case "int":
			return new JPrim(Primitive.INT);
		case "long":
			return new JPrim(Primitive.LONG);
		case "float":
			return new JPrim(Primitive.FLOAT);
		case "double":
			return new JPrim(Primitive.DOUBLE);
		case "Integer":
			return new JInteger();
		case "Float":
			return new JFloat();
		case "String":
			return new JString();
		case "void":
			return new JVoid();
		default:
			CBean cb;
			if ((cb = WrapperEnvironment.getBeanScope().getBean(s)) != null) {
				return new JBean(cb);
			} else if (s.substring(s.length()-2,s.length()).equals("[]")) {
				return new JArray(javaFromString(s.substring(0,s.length()-2)));
			} else {
				return null;
			}
		}
	}
	public static CType cFromString(String s) {
		switch (s) {
		case "void":
			return new CVoid();
		case "char":
			return new CPrim(Primitive.CHAR);
		case "short": case "Short":
			return new CPrim(Primitive.SHORT);
		case "int": case "Integer":
			return new CPrim(Primitive.INT);
		case "long": case "Long":
			return new CPrim(Primitive.LONG);
		case "float": case "Float":
			return new CPrim(Primitive.FLOAT);
		case "double": case "Double":
			return new CPrim(Primitive.DOUBLE);
		case "String":
			return cFromString("*(char)");
		default:
			if (WrapperEnvironment.getBeanScope().getBean(s) != null) {
				return new CStruct(WrapperEnvironment.getBeanScope().getBean(s));
			} else if (s.charAt(0) == '*') {
				return new CArray(cFromString(s.substring(2, s.length()-1)));
			} else if (s.substring(0,2).equals("[]")) {
				return new CArray(cFromString(s.substring(3, s.length()-1)));
			} else {
				return null;
			}
		}
	}
	
	/**
	 * Retourne le nom de type d�crit par un JNIType
	 * ex1. un JPrim(CHAR) retournera "jchar"
	 * ex2. un CPrim(INT) retournera "int"
	 * 
	 * @return le nom de type C correspondant au JNIType
	 */
    public abstract String getJniType();
    
    /**
     * Retourne si oui ou non, le JNIType peut �tre converti
     * en un autre.
     * 
     * @param t le type dont on voudrait faire une conversion
     * @return
     */
    public abstract boolean canBe(JNIType t);
}
