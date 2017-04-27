package fr.istic.m1.fstorm.beans;

import java.util.List;

public class CBean {
	private String name;
	private List<CBeanAttribute> attributes;
	
	public CBean () {
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<CBeanAttribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<CBeanAttribute> attributes) {
		this.attributes = attributes;
	}
}
