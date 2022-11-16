package com.jose.ejercicio_2_15_2022_Entidades;

public class Flag {
	private String name;
	private boolean value;
	
	
	/**
	 * @param name
	 * @param value
	 */
	public Flag(String name) {
		super();
		this.name = name;
		this.value = true;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the value
	 */
	public boolean isValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(boolean value) {
		this.value = value;
	}

	
}
