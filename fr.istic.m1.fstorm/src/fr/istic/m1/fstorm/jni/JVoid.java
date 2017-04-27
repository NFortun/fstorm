package fr.istic.m1.fstorm.jni;

import java.util.List;

public class JVoid implements JType {

	@Override
	public String getJniType() {
		return "void";
	}

	@Override
	public boolean canBe(JNIType t) {
		return false;
	}

	@Override
	public List<Variable> toKernel(Variable v, CType ct) {
		return null;
	}

	@Override
	public void assignKernel(String from_var, List<Variable> to_vars, CType ct) {
	}

	@Override
	public String getSignature() {
		return "V";
	}

}
