package fr.istic.m1.fstorm.jni;

import java.util.List;

/**
 * 
 * @author Arthur Blanleuil
 *
 * D�finit un type primitif C (int, char, short, float ...)
 */
public class CPrim implements CType {

	private Primitive prim;
	
	public CPrim(Primitive prim) {
		this.prim = prim;
	}
	
	@Override
	public String getJniType() {
		return prim.toString();
	}

	@Override
	public Variable toJava(List<Variable> v, JType jt) {
		String val = null;
		if (canBe(jt)) {
			if (jt instanceof JInteger) {
				val = JInteger.fromInteger(v.get(0).getName());
			} else if (jt instanceof JFloat) {
				val = JFloat.fromFloat(v.get(0).getName());
			} else {
				val = v.get(0).getName();
			}
		}
		
		Variable ret = new Variable(jt, val);
		return ret;
	}

	@Override
	public boolean canBe(JNIType t) {
		if (t instanceof JPrim)
			return prim == ((JPrim)t).getPrim();
		if (t instanceof CPrim)
			return prim == ((CPrim)t).getPrim();
		
		switch (prim) {
		case CHAR:
		case SHORT:
		case INT:
		case LONG:
			if (t instanceof JInteger)
				return true;
			break;
		case FLOAT:
		case DOUBLE:
			if (t instanceof JFloat)
				return true;
			break;
		default:
		}

		return false;
	}

	@Override
	public void assignJava(List<String> from_vars, String to_var, JType jt) {
		if (canBe(jt)) {
			if (jt instanceof JInteger) {
				WrapperEnvironment.getBuffer().append(to_var+" = "+JInteger.fromInteger(from_vars.get(0))+";\n");
			} else if (jt instanceof JFloat) {
				WrapperEnvironment.getBuffer().append(to_var+" = "+JFloat.fromFloat(from_vars.get(0))+";\n");
			} else {
				WrapperEnvironment.getBuffer().append(to_var+" = "+from_vars.get(0)+";\n");
			}
		}
	}

	public Primitive getPrim() {
		return prim;
	}

}
