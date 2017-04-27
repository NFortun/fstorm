package fr.istic.m1.fstorm.jni;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.istic.m1.fstorm.beans.CBean;
import fr.istic.m1.fstorm.beans.CBeanAttribute;

public class Topkek {
	public static void main(String[] args) {
		WE.Init();
		Variable ji = new Variable(new JInteger(), null);
		Variable ci = ji.toKernel(new CPrim(Primitive.INT)).get(0);
		Variable jf = new Variable(new JFloat(), JFloat.fromFloat("12.0"));
		jf.toKernel(new CPrim(Primitive.FLOAT));
		Variable arr = new Variable(new JArray(new JPrim(Primitive.INT)), null);
		List<Variable> carr = arr.toKernel(new CArray(new CPrim(Primitive.INT)));
		
		CArray prout = (CArray)JNIType.cFromString("[](int)");
		new Variable(JNIType.javaFromString("Integer[]"), null).toKernel(prout);
		
		if (prout != null) {
			Variable azodij = new Variable(prout, "\"hello\"");
		}
		CBean cb = new CBean();
		cb.setName("test");
		CBeanAttribute attr1 = new CBeanAttribute();
		attr1.setName("i");
		attr1.setTypeName("int");
		CBeanAttribute attr2 = new CBeanAttribute();
		attr2.setName("x");
		attr2.setTypeName("float");
		cb.setAttributes(new ArrayList<>(Arrays.asList(attr1, attr2)));
		
		JBean cbt = new JBean(cb);
		
		Variable jstruct = new Variable(cbt, null);		
		Variable cstruct = jstruct.toKernel(new CStruct(cb)).get(0);
		cstruct.toJava(cbt);
		
		carr.get(0).getCType().toJava(carr, new JArray(new JInteger()));
		System.out.println(WE.getBuffer().toString());
		System.out.println();
	}
}
