package fr.istic.m1.fstorm.jni;

/**
 * 
 * @author Arthur Blanleuil
 * 
 * D�crit l'environement d'une fonction wrapper
 * pour la JNI. Cette classe agit � la mani�re d'un
 * Service Provider, donnant acc�s en global �
 * un Scope contenant les variables cr��es, un StringBuffer
 * contenant le code g�n�r�, et un BeanScope contenant
 * les informations des structures C � convertir en Java.
 * 
 * Avant chaque g�n�ration de code, le WE devra �tre initialis�
 * par la m�thode statique Init().
 */
public final class WrapperEnvironment {
	private static BeanScope bscope = new BeanScope();
	private static Scope scope = null;
	private static StringBuffer buffer = null;

	public static BeanScope getBeanScope() {
		return bscope;
	}
	public static Scope getScope() {
		return scope;
	}
	public static StringBuffer getBuffer() {
		return buffer;
	}
	public static void Init() {
		JInteger.reset();
		JFloat.reset();
		scope = new Scope();
		buffer = new StringBuffer();
	}
}
