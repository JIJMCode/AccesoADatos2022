package com.jose.ejercicio_1_28_2022.Entidades;

import java.util.Date;

public class RegistroTemp {
	private Date fecha;
	private Double temp;
	
	public RegistroTemp(Date fecha, Double temp) {
		super();
		this.fecha = fecha;
		this.temp = temp;
	}

	public RegistroTemp() {
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getTemp() {
		return temp;
	}

	public void setTemp(Double temp) {
		this.temp = temp;
	}

	@Override
	public String toString() {
		return "RegistroTemp [Fecha= " + fecha + ", Temperatura= " + temp + "]"; //new SimpleDateFormat("dd-MM-yyyy").format(fecha)
	}
}
