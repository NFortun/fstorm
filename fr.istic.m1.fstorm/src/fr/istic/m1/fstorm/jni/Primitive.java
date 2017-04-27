package fr.istic.m1.fstorm.jni;

/**
 * 
 * @author Arthur Blanleuil
 *
 * Cette énumération décrit les différents
 * types primitifs C/JNI
 */
public enum Primitive {
	CHAR, SHORT, INT, LONG, FLOAT, DOUBLE;

    public String toString() {
        switch (this) {
            case CHAR: return "char";
            case SHORT: return "short";
            case INT: return "int";
            case LONG: return "long";
            case FLOAT: return "float";
            case DOUBLE: return "double";
            default: return null;
        }
    }
}
