package fr.istic.m1.fstorm.jni;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JInteger extends JClass {

	static private String getIntValue = null;
	static private String valueOf = null;
	public JInteger() {
		super("java/lang/Integer");

		if (valueOf == null) {
			getIntValue = WrapperEnvironment.getScope().fresh();
			String env = WrapperEnvironment.getScope().getEnvironment();
			WrapperEnvironment.getBuffer().append("jmethodID "+getIntValue+" = (*"+env+")->GetMethodID("+env+", "+classVar+", \"intValue\", \"()I\");\n");
		
			valueOf = WrapperEnvironment.getScope().fresh();
			WrapperEnvironment.getBuffer().append("jmethodID "+valueOf+" = (*"+env+")->GetMethodID("+env+", "+classVar+", \"valueOf\", \"(I)Ljava/lang/Integer\");\n");
		}
	}

	@Override
	public List<Variable> toKernel(Variable v, CType ct) {
		
		if (!canBe(ct))
			return null;

		return new ArrayList<Variable>(Arrays.asList(new Variable(ct,
			intValue(v.getName()))));
	}
	
	@Override
	public void assignKernel(String from, List<Variable> to, CType ct) {
		WrapperEnvironment.getBuffer().append(to.get(0).getName()+" = "+intValue(from)+";\n");
	}

	public static String fromInteger(String string) {
		String env = WrapperEnvironment.getScope().getEnvironment();
		return "(*"+env+")->CallStaticObjectMethod("+env+", "+classVar+", "+valueOf+", "+string+")";
	}
	
	public static String intValue(String str) {
		String env = WrapperEnvironment.getScope().getEnvironment();
		return "(*"+env+")->CallIntMethod("+env+", "+str+", "+getIntValue+")";
	}	
	
	@Override
	public boolean canBe(JNIType t) {
		if (t instanceof JInteger)
			return true;

		if (t instanceof JPrim) {
			switch (((JPrim)t).getPrim()) {
			case INT:
			case LONG:
				return true;
			default: return false;
			}
		}
		
		if (t instanceof CPrim) {
			switch (((CPrim)t).getPrim()) {
			case INT:
			case LONG:
				return true;
			default: return false;
			}
		}
		
		return false;
	}
	
	public static void reset() {
		valueOf = null;
		getIntValue = null;
	}
}
