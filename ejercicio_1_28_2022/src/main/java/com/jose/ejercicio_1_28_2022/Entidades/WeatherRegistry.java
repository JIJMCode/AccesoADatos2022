package com.jose.ejercicio_1_28_2022.Entidades;

import java.util.List;

public class WeatherRegistry {
	String city;
	String temp;
	String humidity;
	List weather;
	
	public WeatherRegistry() {
		super();
	}

	public WeatherRegistry(String city, String temp, String humidity, List weather) {
		super();
		this.city = city;
		this.temp = temp;
		this.humidity = humidity;
		this.weather = weather;
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

	public List<Weather> getweather() {
		return weather;
	}

	public void setweather(List<Weather> weather) {
		this.weather = weather;
	}

	@Override
	public String toString() {
		return "WeatherRegistry:\nCiudad: " + city + "\nTemperatura: " + Math.round(((Double.parseDouble(temp)-273.15)*100.0)/100.0) +
				"ºC\nHumedad: " + humidity + "%\n" + weather.toString();
	}
}
