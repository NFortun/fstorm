package fr.istic.m1.fstorm.jni;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Arthur Blanleuil
 *
 * Définit un type primitif Java dans la JNI
 * (jint, jchar, jshort, jfloat ...)
 */
public class JPrim implements JType {

	private Primitive prim;
	
	public JPrim(Primitive prim) {
		this.prim = prim;
	}
	
	@Override
	public String getJniType() {
		return "j"+prim.toString();
	}

	@Override
	public List<Variable> toKernel(Variable v, CType ct) {
		List<Variable> ret = new ArrayList<>();
		ret.add(new Variable(ct, v.getName()));
		return ret;
	}

	public Primitive getPrim() {
		return prim;
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
	public void assignKernel(String from_var, List<Variable> to_vars, CType ct) {
		if (canBe(ct)) {
			WE.getBuffer().append(to_vars.get(0).getName()+" = "+from_var+";\n");
		}
	}

	@Override
	public String getSignature() {
		switch (prim) {
		case CHAR: return "C";
		case SHORT: return "S";
		case INT: return "I";
		case LONG: return "J";
		case FLOAT: return "F";
		case DOUBLE: return "D";
		default: return "";
		}
	}

}
