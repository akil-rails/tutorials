package com.quantiguous.iib.tutorial;

public class SimpleClass {

	private String myName;
	
	public SimpleClass() {
		this.myName = "hello";
	}
	
	public SimpleClass(String name) {
		this.myName = name;
	}
	
	public String getName() {
		return this.myName;
	}
}
