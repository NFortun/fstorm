package fr.istic.m1.fstorm.jni;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.istic.m1.fstorm.beans.CBean;

public class BeanScope {
	private Map<String,CBean> beans_by_name = new HashMap<>();
	
	public CBean getBean(String name) {
		return beans_by_name.get(name);
	}
	
	public void addAlias(String real, String alias) {
		beans_by_name.put(alias, getBean(real));
	}
	
	public void addBean(CBean bean) {
		beans_by_name.put(bean.getName(), bean);
	}

	public List<CBean> getAllBeans() {
		return new ArrayList<>(beans_by_name.values());
	}
}
