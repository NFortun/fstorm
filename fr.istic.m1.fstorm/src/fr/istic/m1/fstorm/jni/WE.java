package fr.istic.m1.fstorm.jni;

/**
 * 
 * @author Arthur Blanleuil
 * 
 * Décrit l'environement d'une fonction wrapper
 * pour la JNI. Cette classe agit à la manière d'un
 * Service Provider, donnant accès en global à
 * un Scope contenant les variables créées, un StringBuffer
 * contenant le code généré, et un BeanScope contenant
 * les informations des structures C à convertir en Java.
 * 
 * Avant chaque génération de code, le WE devra être initialisé
 * par la méthode statique Init().
 */
public final class WE {
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
