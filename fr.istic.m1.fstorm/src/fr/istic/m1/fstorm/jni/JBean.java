package fr.istic.m1.fstorm.jni;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import fr.istic.m1.fstorm.beans.CBean;
import fr.istic.m1.fstorm.beans.CBeanAttribute;

public class JBean implements JType {

	private CBean bean;
	private String classVar;
	private String constructorVar;
	
	public JBean(CBean bean) {
		if (bean == null)
			throw new NullPointerException();
		
		this.bean = bean;

		if ((classVar = WE.getScope().getClass(bean.getName())) == null) {
			classVar = WE.getScope().addClass(bean.getName());
			String env = WE.getScope().getEnvironment();
			WE.getBuffer().append("jclass "+classVar+" = (*"+env+")->FindClass("+env+", \""+bean.getName()+"\");\n");
			constructorVar = WE.getScope().fresh();
			WE.getBuffer().append("jmethodID "+constructorVar+" = (*"+env+")->GetMethodID("+env+", "+classVar+", \"<init>\", \"()V\");\n");
			
			WE.getScope().addBean(bean, classVar);
		}
	}

	@Override
	public String getJniType() {
		return "jobject";
	}

	@Override
	public boolean canBe(JNIType t) {
		return (t instanceof JBean && ((JBean)t).getBean() == getBean()) || (t instanceof CStruct && ((CStruct)t).getBean() == getBean());
	}

	public CBean getBean() {
		return bean;
	}

	@Override
	public List<Variable> toKernel(Variable v, CType ct) {
		if (ct instanceof CStruct) {
			Variable ret = new Variable(ct, null);
			assignKernel(v.getName(), Arrays.asList(ret), ct);
			return new ArrayList<>(Arrays.asList(ret));
		}

		return null;
	}

	@Override
	public void assignKernel(String from_var, List<Variable> to_vars, CType ct) {
		for (CBeanAttribute attr : ((CStruct)ct).getBean().getAttributes()) {
			JType jt = JNIType.javaFromString(attr.getTypeName());
			CType cattr = JNIType.cFromString(attr.getTypeName());
			
			Variable a = new Variable(jt, getAttribute(from_var, attr.getName(), jt));
			List<Variable> struct_attribs = jt.toKernel(a, cattr);
			WE.getBuffer().append(to_vars.get(0).getName()+"."+attr.getName()+" = "+struct_attribs.get(0).getName()+";\n");
		}
	}

	public String getAttribute(String struct, String attrname, JType jt) {
		String env = WE.getScope().getEnvironment();
		String b;
		if (jt instanceof JPrim) {
			b = ((JPrim) jt).getPrim().toString();
			b = b.substring(0, 1).toUpperCase() + b.substring(1);
		} else {
			b = "Object";
		}

		return "(*"+env+")->Call"+b+"Method("+env+", "+struct+", "+WE.getScope().getGetter(bean.getName(), attrname)+")";
	}

	public String setAttribute(String name, String name2, String name3) {
		String env = WE.getScope().getEnvironment();
		return "(*"+env+")->CallVoidMethod("+env+", "+name+", "+WE.getScope().getSetter(bean.getName(),name2)+", "+name3+")";
	}
	
	public String newInstance() {
		String env = WE.getScope().getEnvironment();
		return "(*"+env+")->NewObject("+env+", "+classVar+", "+constructorVar+")";
	}

	@Override
	public String getSignature() {
		return "Lbeans/"+bean.getName();
	}
}
