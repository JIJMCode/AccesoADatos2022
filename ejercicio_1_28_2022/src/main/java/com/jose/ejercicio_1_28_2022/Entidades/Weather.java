package com.jose.ejercicio_1_28_2022.Entidades;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Weather implements Serializable {
	private int id;
	private String main;
	private String description;
	private String icon;
	
	public Weather() {
		super();
	}

	public Weather(int id, String main, String description, String icon) {
		super();
		this.id = id;
		this.main = main;
		this.description = description;
		this.icon = icon;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "Wheather [Main=" + main + ", Description=" + description + "]";
	}
}
