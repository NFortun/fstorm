package fr.istic.m1.fstorm.jni;

import java.util.List;

public class CVoid implements CType {

	@Override
	public String getJniType() {
		return "void";
	}

	@Override
	public boolean canBe(JNIType t) {
		return false;
	}

	@Override
	public Variable toJava(List<Variable> v, JType jt) {
		return null;
	}

	@Override
	public void assignJava(List<String> from_vars, String to_var, JType jt) {
	}

}
