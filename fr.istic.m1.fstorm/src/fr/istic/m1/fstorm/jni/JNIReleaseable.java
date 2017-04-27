package fr.istic.m1.fstorm.jni;

/**
 *
 * @author Arthur Blanleuil
 *
 * Définit un type qu'on aura besoin de libérer plus tard
 * ex. les tableaux doivent être libérés avec Release<Type>Array();
 */
public interface JNIReleaseable {
	public void release(String arr, String body);
}
