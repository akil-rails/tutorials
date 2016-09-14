package com.quantiguous.iib.tutorial;

import com.quantiguous.iib.tutorial.TestClass;

public class TestClass {
	public static void main(String argv[]) {
		
		SimpleClass x = new SimpleClass();
		System.out.println(x.getName());
		
		SimpleClass y = new SimpleClass("manoj");
		
		y.getName();
		
		System.out.println(y.getName());
	}
}
