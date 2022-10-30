package com.jose.ejercicio_1_28_2022.Entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("serial")
public class HistoricoBusqueda implements Serializable {
	private LocalDate date;
	private String city;
	private Double temperature;
	private Double humidity;
	private List weather;
	
	public HistoricoBusqueda() {
		super();
	}

	/**
	 * @param date
	 * @param city
	 * @param temperature
	 * @param humidity
	 * @param weather
	 */
	public HistoricoBusqueda(LocalDate date, String city, Double temperature, Double humidity, List weather) {
		super();
		this.date = date;
		this.city = city;
		this.temperature = temperature;
		this.humidity = humidity;
		this.weather = weather;
	}

	/**
	 * @return the weather
	 */
	public List getWeather() {
		return weather;
	}

	/**
	 * @param weather the weather to set
	 */
	public void setWeather(List weather) {
		this.weather = weather;
	}


	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the temperature
	 */
	public Double getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the humidity
	 */
	public Double getHumidity() {
		return humidity;
	}

	/**
	 * @param humidity the humidity to set
	 */
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	@Override
	public String toString() {
		return date + "," + city + "," + temperature + "," + humidity;
	}
}
