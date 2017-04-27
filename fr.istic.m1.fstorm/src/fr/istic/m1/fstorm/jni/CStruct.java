package fr.istic.m1.fstorm.jni;

import java.util.Arrays;
import java.util.List;

import fr.istic.m1.fstorm.beans.CBean;
import fr.istic.m1.fstorm.beans.CBeanAttribute;

public class CStruct implements CType {

	private CBean bean;
	
	public CStruct(CBean bean) {
		if (bean == null)
			throw new NullPointerException();
		
		this.bean = bean;
		
		new JBean(this.bean);
	}
	
	@Override
	public String getJniType() {
		return bean.getName();
	}

	@Override
	public boolean canBe(JNIType t) {
		if (t instanceof JBean)
			return ((JBean) t).getBean() == getBean();
		if (t instanceof CStruct)
			return ((CStruct) t).getBean() == getBean();
		
		return false;
	}

	@Override
	public Variable toJava(List<Variable> v, JType jt) {
		if (canBe(jt)) {
			Variable from = v.get(0);
			Variable jstruct = new Variable(jt, ((JBean)jt).newInstance());
			this.assignJava(Arrays.asList(from.getName()), jstruct.getName(), jt);
			
			return jstruct;
		}
		
		return null;
	}

	@Override
	public void assignJava(List<String> from_vars, String to_var, JType jt) {
		if (canBe(jt)) {
			String from = from_vars.get(0);
			for (CBeanAttribute attr : ((JBean)jt).getBean().getAttributes()) {
				JType jattr = JNIType.javaFromString(attr.getTypeName());
				CType ct = JNIType.cFromString(attr.getTypeName());
				
				Variable a = new Variable(jattr, null);
				ct.assignJava(Arrays.asList(from+"."+attr.getName()), a.getName(), jattr);
				WrapperEnvironment.getBuffer().append(((JBean)jt).setAttribute(to_var, attr.getName(), a.getName())+";\n");
			}
		}
	}

	@SuppressWarnings("unused")
	private JBean setAttribute(String name, String name2) {
		// TODO Auto-generated method stub
		return null;
	}

	public CBean getBean() {
		return bean;
	}

}
