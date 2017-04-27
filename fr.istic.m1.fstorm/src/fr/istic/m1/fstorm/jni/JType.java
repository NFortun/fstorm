package fr.istic.m1.fstorm.jni;

import java.util.List;

public interface JType extends JNIType {
	/**
	 * Cré et initialise des Variables décrivant un CType
	 * vers lequel le JType doit être converti:
	 * 
	 * ex. d'un JArray vers un CArray, la fonction
	 *     créra un CArray ET un CPrim(INT) pour définir un tableau
	 * 
	 * La fonction doit aussi modifier le buffer du WrapperEnvironment
	 * en y écrivant les étapes de conversion. (cf. doc de CType)
	 * 
	 * @param v la variable a convertir
	 * @param ct le type d'arrivée
	 * @return les variables décrivant le nouveau type
	 */
    public abstract List<Variable> toKernel(Variable v, CType ct);
    
    /**
     * Assigne des valeurs à des Variables C dans le but "d'assigner" la valeur
     * d'une Variable Java.
     * 
     * Cette fonction est similaire à toKernel(), mais elle ne cré pas de nouvelles variables,
     * elle prend des variables déjà existantes.
     * 
     * @param from_var la variable Java de départ
     * @param to_vars les variables C à modifier
     * @param ct le type C d'arrivée
     */
    public abstract void assignKernel(String from_var, List<Variable> to_vars, CType ct);
    
    /**
     * Retourne la signature Java d'un type
     * ex. JString().getSignature() == "Ljava/lang/String;"
     *     JPrim(CHAR).getSignature() == "C"
     *     JArray(JPrim(INT)).getSignature() == "[I"
     * @return
     */
    public abstract String getSignature();
}
