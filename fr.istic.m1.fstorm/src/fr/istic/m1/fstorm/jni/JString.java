package fr.istic.m1.fstorm.jni;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Arthur Blanleuil
 * 
 * D�finit la classe String de Java
 * dans la JNI. Cette classe n'impl�mente
 * pas l'interface "JClass" car elle n'est pas
 * r�ellement manipul�e comme tel dans la JNI
 */
public class JString implements JType, JNIReleaseable {

	@Override
	public String getJniType() {
		return "jstring";
	}

	@Override
	public boolean canBe(JNIType t) {
		return t.getJniType().equals("char*");
	}

	@Override
	public List<Variable> toKernel(Variable v, CType ct) {
		if (!canBe(ct))
			return null;

		String env = WrapperEnvironment.getScope().getEnvironment();
		return new ArrayList<>(Arrays.asList(new Variable(ct, "(*"+env+")->GetStringUTFChars("+env+", "+v.getName()+", 0)")));
	}

	@Override
	public void assignKernel(String from_var, List<Variable> to_vars, CType ct) {
		String env = WrapperEnvironment.getScope().getEnvironment();
		WrapperEnvironment.getBuffer().append(to_vars.get(0).getName()+" = (*"+env+")->GetStringUTFChars("+env+", "+from_var+", 0)");
	}

	@Override
	public String getSignature() {
		return "Ljava/lang/String";
	}

	@Override
	public void release(String arr, String body) {
		String env = WrapperEnvironment.getScope().getEnvironment();
		WrapperEnvironment.getBuffer().append("(*"+env+")->ReleaseStringUTFChars("+env+", "+arr+", "+body+");\n");
	}
}
