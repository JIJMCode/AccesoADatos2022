package com.jose.ejercicio_1_28_2022.Entidades;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WeatherXml implements Serializable {
	private String main;
	private String description;
	private String icon;
	
	public WeatherXml() {
		super();
	}

	public WeatherXml(String main, String description, String icon) {
		super();
		this.main = main;
		this.description = description;
		this.icon = icon;
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
		return "\nWheatherXml [Main=" + main + ", Description=" + description + "]";
	}
}
