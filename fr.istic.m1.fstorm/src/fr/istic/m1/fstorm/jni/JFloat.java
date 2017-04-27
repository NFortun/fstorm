package fr.istic.m1.fstorm.jni;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JFloat extends JClass {

	static private String getFloatValue;
	static private String fromFloat;
	public JFloat() {
		super("java/lang/Float");
		
		getFloatValue = WE.getScope().fresh();
		String env = WE.getScope().getEnvironment();
		WE.getBuffer().append("jmethodID "+getFloatValue+" = (*"+env+")->GetMethodID("
				+env+", "+classVar+", \"floatValue\", \"()F\");\n");
		
		fromFloat = WE.getScope().fresh();
		WE.getBuffer().append("jmethodID "+fromFloat+" = (*"+env+")->GetStaticMethodID("
				+env+", "+classVar+", \"valueOf\", \"(F)Ljava/lang/Float\");\n");
	}

	@Override
	public List<Variable> toKernel(Variable v, CType ct) {
		if (!canBe(ct))
			return null;
		
		return new ArrayList<Variable>(Arrays.asList(new Variable(ct,
			floatValue(v.getName()))));
	}

	public static String fromFloat(String string) {
		String env = WE.getScope().getEnvironment();
		return "(*"+env+")->CallStaticObjectMethod("+env+", "+classVar+", "+fromFloat+", "+string+")";
	}
	
	public static String floatValue(String str) {
		String env = WE.getScope().getEnvironment();
		return "(*"+env+")->CallFloatMethod("+env+", "+str+", "+getFloatValue+")";
	}
	
	@Override
	public boolean canBe(JNIType t) {
		if (t instanceof JFloat)
			return true;

		if (t instanceof JPrim) {
			switch (((JPrim)t).getPrim()) {
			case FLOAT:
			case DOUBLE:
				return true;
			default: return false;
			}
		}
		
		if (t instanceof CPrim) {
			switch (((CPrim)t).getPrim()) {
			case FLOAT:
			case DOUBLE:
				return true;
			default: return false;
			}
		}
		
		return false;
	}

	@Override
	public void assignKernel(String from_var, List<Variable> to_vars, CType ct) {
		if (canBe(ct)) {
			if (ct instanceof CPrim)
				WE.getBuffer().append(to_vars.get(0)+" = "+floatValue(from_var)+";\n");
			else
				WE.getBuffer().append(to_vars.get(0)+" = "+from_var+";\n;");
		}
	}
	
	public static void reset() {
		getFloatValue = null;
		fromFloat = null;
	}
}
