package fr.istic.m1.fstorm.jni;

import java.util.Arrays;
import java.util.List;

public class CArray implements CType {

	private CType base;
	
	public CArray(CType base) {
		this.base = base;
	}

	@Override
	public String getJniType() {
		return base.getJniType()+"*";
	}

	@Override
	public Variable toJava(List<Variable> v, JType jt) {		
		if (!(jt instanceof JArray))
			return null;
	
		JArray jr = (JArray)jt;
		String env = WE.getScope().getEnvironment();
		
		Variable arrs = v.get(0);
		Variable ns   = v.get(1);
		Variable ret  = new Variable(jt, null);
		if (jr.getBase() instanceof JPrim)
			new Variable(jt, null);
		else
			new Variable(jt, newArray(jt, ns.getName()));
		
		assignJava(Arrays.asList(arrs.getName(), ns.getName()), ret.getName(), jt);
		
		return ret;
	}

	public CType getBase() {
		return base;
	}

	public String newArray(JType jt, String size) {
		String env = WE.getScope().getEnvironment();
		if (jt instanceof JPrim) {
			String b = ((JPrim)base).getPrim().toString();
			b = b.substring(0, 1).toUpperCase() + b.substring(1);
			return "(*"+env+")->New"+b+"Array("+env+", "+size+")";
		} else if (jt instanceof JClass){
			return "(*"+env+")->NewObjectArray("+env+", "+size+", "+((JClass)jt).getConstructor()+")";
		} else {
			return null;
		}
	}

	public String setArrayRegion(JType jt, String arr, String size, String region) {
		String env = WE.getScope().getEnvironment();
		if (jt instanceof JPrim) {
			String b = ((JPrim)base).getPrim().toString();
			b = b.substring(0, 1).toUpperCase() + b.substring(1);
			return "(*"+env+")->Set"+b+"ArrayRegion("+env+", "+arr+", 0, "+size+", "+region+")";
		}

		return null;
	}

	@Override
	public void assignJava(List<String> from_vars, String to_var, JType jt) {
		if (!(jt instanceof JArray))
			return;
		
		JArray jr = (JArray)jt;
		String arrs = from_vars.get(0);
		String ns = from_vars.get(1);
		
		if (base instanceof CPrim && jr.getBase() instanceof JPrim) {
			String b = ((JPrim)base).getPrim().toString();
			b = b.substring(0, 1).toUpperCase() + b.substring(1);
			WE.getBuffer().append(to_var+" = "+newArray(jt, ns)+";\n");
			WE.getBuffer().append(setArrayRegion(jt, to_var, ns, arrs)+";\n");
		} else if (base.canBe(jr.getBase())) {
			String env = WE.getScope().getEnvironment();
			String is = WE.getScope().fresh();
			WE.getBuffer().append("for (int "+is+"=0; "+is+" < "+ns+"; ++"+is+") {\n");
			Variable tmp = new Variable(jr.getBase(), null);
			base.assignJava(Arrays.asList(arrs+"["+is+"]"), tmp.getName(), jr.getBase());
			WE.getBuffer().append("*("+WE.getScope().getEnvironment()+")->SetObjectArrayElement("+env+", "+to_var+", "+is+", "+tmp.getName()+");\n");
			WE.getBuffer().append("}\n");
		}
	}

	@Override
	public boolean canBe(JNIType t) {
		return (t instanceof CArray && ((CArray)t).getBase()==getBase()) || (t instanceof JArray && base.canBe(((JArray)t).getBase()));
	}
}
