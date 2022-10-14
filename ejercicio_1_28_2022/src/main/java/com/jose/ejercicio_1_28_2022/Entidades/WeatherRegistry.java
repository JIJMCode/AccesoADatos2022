package com.jose.ejercicio_1_28_2022.Entidades;

import java.util.List;

import com.jose.ejercicio_1_28_2022.Entidades.Fran.Asignatura;

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

	public List getWheather() {
		return wheather;
	}

	public void setWheather(List wheather) {
		this.wheather = wheather;
	}

	@Override
	public String toString() {
		return "WeatherRegistry [city=" + city + ", temp=" + temp + ", humidity=" + humidity + ", wheather=" + "\n" + wheather.toString()
				+ "]";
	}
}
