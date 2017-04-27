package fr.istic.m1.fstorm.jni;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JArray implements JType, JNIReleaseable {
	private JType base;
	
	public JArray(JType base) {
		this.base = base;
	}

	@Override
	public String getJniType() {
		if (base instanceof JPrim) {
			return base.getJniType()+"Array";
		} else {
			return "jobjectArray";
		}
	}

	@Override
	public List<Variable> toKernel(Variable v, CType ct) {

		String env = WrapperEnvironment.getScope().getEnvironment();
		Variable s = new Variable(new CPrim(Primitive.INT)
				, "(*"+env+")->GetArrayLength("+env+", "+v.getName()+")");
		
		if (base instanceof JPrim) {
			Variable arr = new Variable(ct
					, getArrayElements(v.getName()));
			
			return new ArrayList<Variable>(Arrays.asList(arr, s));
		} else {
			Variable arr = new Variable(ct, "malloc("+s.getName()+"*sizeof("+((CArray)ct).getBase().getJniType()+"))");
			Variable is = new Variable(new CPrim(Primitive.INT), null);
			WrapperEnvironment.getBuffer().append("for ("+is.getName()+"=0; "+is.getName()+"<"+s.getName()+"; ++"+is.getName()+") {\n");
			
			Variable elem = new Variable(((CArray)ct).getBase(), null);
			base.assignKernel("(*"+env+")->GetObjectArrayElement("+env+", "+v.getName()+", "+is.getName()+")", Arrays.asList(elem), ((CArray)ct).getBase());
			
			WrapperEnvironment.getBuffer().append(arr.getName()+"["+is.getName()+"] = "+elem.getName()+";\n");
			WrapperEnvironment.getBuffer().append("}\n");
			return new ArrayList<Variable>(Arrays.asList(arr, s));
		}
	}

	public JType getBase() {
		return base;
	}

	public String getArrayElements(String name) {
		if (base instanceof JPrim) {
			String b = ((JPrim)base).getPrim().toString();
			b = b.substring(0, 1).toUpperCase() + b.substring(1);
			return "(*"+WrapperEnvironment.getScope().getEnvironment()+")->Get"+b+"ArrayElements("+WrapperEnvironment.getScope().getEnvironment()+", "+name+", 0)";
		}

		return null;
	}
	
	public String getArrayLength(String name) {
		return "(*"+WrapperEnvironment.getScope().getEnvironment()+")->GetArrayLength("+WrapperEnvironment.getScope().getEnvironment()+", "+name+")";
	}

	@Override
	public void assignKernel(String from_var, List<Variable> to_vars, CType ct) {
		
	}

	@Override
	public boolean canBe(JNIType t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSignature() {
		return "["+base.getSignature();
	}

	@Override
	public void release(String arr, String body) {
		if (base instanceof JPrim) {
			String b = ((JPrim)base).getPrim().toString();
			b = b.substring(0, 1).toUpperCase() + b.substring(1);
			String env = WrapperEnvironment.getScope().getEnvironment();
			
			WrapperEnvironment.getBuffer().append("(*"+env+")->Release"+b+"ArrayElements("+env+", "+arr+", "+body+");\n");
		}
	}
	
}
