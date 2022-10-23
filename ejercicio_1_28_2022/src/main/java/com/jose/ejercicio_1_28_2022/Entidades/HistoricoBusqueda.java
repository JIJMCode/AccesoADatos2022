package com.jose.ejercicio_1_28_2022.Entidades;

import java.time.LocalDate;

public class HistoricoBusqueda {
	private LocalDate date;
	private String city;
	private Double temperature;
	private Double humidity;
	
	public HistoricoBusqueda() {
		super();
	}

	public HistoricoBusqueda(LocalDate date, String city, Double temperature, Double humidity) {
		super();
		this.date = date;
		this.city = city;
		this.temperature = temperature;
		this.humidity = humidity;
	}

	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	@Override
	public String toString() {
		return "HistoricoBusqueda [date=" + date + ", city=" + city + ", temperature=" + temperature + ", humidity="
				+ humidity + "]";
	}

	public String serializeString() {
		return date + "," + city + "," + temperature + "," + humidity;
	}



}
