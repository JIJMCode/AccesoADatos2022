package com.jose.ejercicio_1_28_2022.Entidades.Fran;

import java.util.Arrays;
import java.util.Objects;

public class People {

	String name;
	String height;
	String mass;
	String hair_color;
	String skin_color;
	String eye_color;
	String birth_year;
	String gender;
	String homeworld;
	String[] films;
	String[] species;
	String[] vehicles;
	String[] starships;
	String created;
	String edited;
	String url;
	
	public People() {
		
	}

	public People(String name, String height, String mass, String hair_color, String skin_color, String eye_color,
			String birth_year, String gender, String homeworld, String[] films, String[] species, String[] vehicles,
			String[] starships, String created, String edited, String url) {
		super();
		this.name = name;
		this.height = height;
		this.mass = mass;
		this.hair_color = hair_color;
		this.skin_color = skin_color;
		this.eye_color = eye_color;
		this.birth_year = birth_year;
		this.gender = gender;
		this.homeworld = homeworld;
		this.films = films;
		this.species = species;
		this.vehicles = vehicles;
		this.starships = starships;
		this.created = created;
		this.edited = edited;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getMass() {
		return mass;
	}

	public void setMass(String mass) {
		this.mass = mass;
	}

	public String getHair_color() {
		return hair_color;
	}

	public void setHair_color(String hair_color) {
		this.hair_color = hair_color;
	}

	public String getSkin_color() {
		return skin_color;
	}

	public void setSkin_color(String skin_color) {
		this.skin_color = skin_color;
	}

	public String getEye_color() {
		return eye_color;
	}

	public void setEye_color(String eye_color) {
		this.eye_color = eye_color;
	}

	public String getBirth_year() {
		return birth_year;
	}

	public void setBirth_year(String birth_year) {
		this.birth_year = birth_year;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHomeworld() {
		return homeworld;
	}

	public void setHomeworld(String homeworld) {
		this.homeworld = homeworld;
	}

	public String[] getFilms() {
		return films;
	}

	public void setFilms(String[] films) {
		this.films = films;
	}

	public String[] getSpecies() {
		return species;
	}

	public void setSpecies(String[] species) {
		this.species = species;
	}

	public String[] getVehicles() {
		return vehicles;
	}

	public void setVehicles(String[] vehicles) {
		this.vehicles = vehicles;
	}

	public String[] getStarships() {
		return starships;
	}

	public void setStarships(String[] starships) {
		this.starships = starships;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getEdited() {
		return edited;
	}

	public void setEdited(String edited) {
		this.edited = edited;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "People [name=" + name + ", height=" + height + ", mass=" + mass + ", hair_color=" + hair_color
				+ ", skin_color=" + skin_color + ", eye_color=" + eye_color + ", birth_year=" + birth_year + ", gender="
				+ gender + ", homeworld=" + homeworld + ", films=" + Arrays.toString(films) + ", species="
				+ Arrays.toString(species) + ", vehicles=" + Arrays.toString(vehicles) + ", starships="
				+ Arrays.toString(starships) + ", created=" + created + ", edited=" + edited + ", url=" + url + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(url);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		People other = (People) obj;
		return Objects.equals(url, other.url);
	}
	
}
