package fr.istic.m1.fstorm.jni;

import java.util.List;

public interface CType extends JNIType {
	/**
	 * Cré une variable définie par un JType à partir d'un CType
	 * 
	 * Cette fonction doit renvoyer une Variable valide si la conversion
	 * a pu se faire. Le buffer du WrapperEnvironment (WE) doit être modifié
	 * en conséquence (ajouter les lignes de code nécessaires au cast).
	 * 
	 * @param v les variables décrivant les attributs du type
	 * @param jt le type java vers lequel on converti 
	 * @return La variable java créée
	 */
    public abstract Variable toJava(List<Variable> v, JType jt);
    
    /**
     * Assigne la valeur de variables décrivant un CType
     * dans une variable décrivant un JType.
     * 
     * Cette fonction doit modifier le buffer du WE pour y faire
     * apparaitre les lignes de code de la conversion et de l'assignation.
     * (ex. "jvar = cvar;") pour un simple cast entre deux types primitifs (int -> jint)
     *  
     * @param from_vars les noms des variables décrivant le CType
     * @param to_var le nom de la variable décrivant le JType
     * @param jt le JType de la variable à assigner 
     */
    public abstract void assignJava(List<String> from_vars, String to_var, JType jt);
}
