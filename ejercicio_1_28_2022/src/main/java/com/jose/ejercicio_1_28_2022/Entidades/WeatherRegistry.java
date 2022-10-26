package com.jose.ejercicio_1_28_2022.Entidades;

import java.util.List;

public class WeatherRegistry {
	String city;
	String temp;
	String humidity;
	List wheather;
	
	public WeatherRegistry() {
		super();
	}

	public WeatherRegistry(String city, String temp, String humidity, List wheather) {
		super();
		this.city = city;
		this.temp = temp;
		this.humidity = humidity;
		this.wheather = wheather;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public List<Weather> getWheather() {
		return wheather;
	}

	public void setWheather(List<Weather> wheather) {
		this.wheather = wheather;
	}

	@Override
	public String toString() {
		return "WeatherRegistry:\n Ciudad: " + city + "\nTemperatura: " + Math.round(((Double.parseDouble(temp)-273.15)*100.0)/100.0) + "ºC\nHumedad: " + humidity +
				"%\nWheather => " + "[" + wheather.toString()	+ "]";
	}
}
