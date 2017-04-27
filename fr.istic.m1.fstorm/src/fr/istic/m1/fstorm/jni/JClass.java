package fr.istic.m1.fstorm.jni;

import java.util.List;

/**
 * 
 * @author Arthur Blanleuil
 *
 * Définit une classe Java dans la JNI.
 * Cette classe s'occupe de générer le code
 * d'acquisition de la classe dans la JNI, ainsi
 * que de son constructeur par défaut.
 */
public class JClass implements JType {

	static protected String className;
	static protected String classVar;
	static protected String constructorVar;

	public JClass(String className) {
		this.className = className;
		
		if ((classVar = WE.getScope().getClass(className)) == null) {
			classVar = WE.getScope().addClass(className);
			String env = WE.getScope().getEnvironment();
			WE.getBuffer().append("jclass "+classVar+" = (*"+env+")->FindClass("+env+", \""+className+"\");\n");
			constructorVar = WE.getScope().fresh();
			WE.getBuffer().append("jmethodID "+constructorVar+" = (*"+env+")->GetMethodID("+env+", "+classVar+", \"<init>\", \"()V\");\n");
		}
	}

	@Override
	public String getJniType() {
		return "jobject";
	}

	@Override
	public List<Variable> toKernel(Variable v, CType ct) {
		return null;
	}

	@Override
	public boolean canBe(JNIType t) {
		return false;
	}

	@Override
	public void assignKernel(String from_var, List<Variable> to_vars, CType ct) {
		// TODO Auto-generated method stub
		
	}
	
	public String getConstructor() {
		return constructorVar;
	}

	@Override
	public String getSignature() {
		return "L"+className+";";
	}
}
