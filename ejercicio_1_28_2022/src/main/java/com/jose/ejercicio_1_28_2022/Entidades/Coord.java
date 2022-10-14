package com.jose.ejercicio_1_28_2022.Entidades;

public class Coord {
	private double lat;
	private double lon;
	
	public Coord() {
		super();
		this.lat = 0;
		this.lon = 0;
	}
	
	public Coord(double lat, double lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}
	
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
}
