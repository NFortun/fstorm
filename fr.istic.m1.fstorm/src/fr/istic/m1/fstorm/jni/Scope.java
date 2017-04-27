package fr.istic.m1.fstorm.jni;

import java.util.HashMap;
import java.util.Map;

import fr.istic.m1.fstorm.beans.CBean;
import fr.istic.m1.fstorm.beans.CBeanAttribute;

public class Scope {
	private String _environment = null;
	private String _calling_class = null;
	private Map<String, String> _classes = null;
	private Map<String, CBeanInfo> beans = null;
	
	private class CBeanInfo {
		public CBean b;
		public Map<String,String> gs;
		public Map<String,String> ss;
	}
	
	private String _fresh;
	
	public Scope() {
		_fresh = "a";
		_environment = null;
		_calling_class = null;
		_classes = new HashMap<>();
		beans = new HashMap<>();
	}
	
	public String fresh() {
		String ret = new String(_fresh);
		boolean carry = true;
		
		_fresh = "";
		for (int i = 0; i < ret.length() || carry; ++i) {
			char c = i < ret.length() ? ret.charAt(i) : 'a'-1;
			if (c == 'z' && carry) {
				_fresh += (char)'a';
				carry = true;
			} else if (carry) {
				_fresh += (char)(c+1);
				carry = false;
			} else {
				_fresh += (char)c;
			}
		}

		return ret;
	}
	
	public String getEnvironment() {
		if (_environment == null)
			_environment = fresh();
		
		return _environment;
	}
	
	public String getCallingClass() {
		if (_calling_class == null)
			_calling_class = fresh();
		
		return _calling_class;
	}
	
	public String getClass(String cls) {
		if (!_classes.containsKey(cls))
			return null;
		
		return _classes.get(cls);
	}
	
	public String addClass(String cls) {
		if (!_classes.containsKey(cls))
			_classes.put(cls, fresh());
		
		return _classes.get(cls);
	}

	public CBean getBean(String s) {
		if (beans.get(s) == null)
			return null;
		
		return beans.get(s).b;
	}
	
	public void addBean(CBean b, String classVar) {
		if (beans.get(b.getName()) == null) {
			CBeanInfo i = new CBeanInfo();
			i.b = b;
			i.gs = new HashMap<>();
			i.ss = new HashMap<>();
			for (CBeanAttribute attr : b.getAttributes()) {
				String nname = attr.getName();
				nname = nname.substring(0, 1).toUpperCase() + nname.substring(1);
				
				String get = fresh();
				WrapperEnvironment.getBuffer().append("jmethodID "+get+" = (*"+getEnvironment()+")->GetMethodID("+getEnvironment()+", "+getClass(b.getName())+", \"get"+nname+"\", "
						+"\"()"+JNIType.javaFromString(attr.getTypeName()).getSignature()+"\");\n");
				String set = fresh();
				WrapperEnvironment.getBuffer().append("jmethodID "+set+" = (*"+getEnvironment()+")->GetMethodID("+getEnvironment()+", "+getClass(b.getName())+", \"set"+nname+"\", "
						+"\"("+JNIType.javaFromString(attr.getTypeName()).getSignature()+")V\");\n");
				
				
				i.gs.put(attr.getName(), get);
				i.ss.put(attr.getName(), set);
			}
			beans.put(b.getName(), i);
		}
	}

	public String getGetter(String name, String b) {
		if (beans.get(name) == null)
			return null;
		
		return beans.get(name).gs.get(b);
	}

	public String getSetter(String name, String b) {
		if (beans.get(name) == null)
			return null;
		
		return beans.get(name).ss.get(b);
	}
}