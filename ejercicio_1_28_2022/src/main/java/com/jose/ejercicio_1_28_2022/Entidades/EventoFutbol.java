package com.jose.ejercicio_1_28_2022.Entidades;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class EventoFutbol {
	private String strEvent;
	private String strHomeTeam;
	private String strAwayTeam;
	private String strLeague;
	private String strSeason;
	private String dateEvent;
	private String intHomeScore;
	private String intAwayScore;
	//SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
	
	public EventoFutbol() {
		super();
	}

	public EventoFutbol(String strEvent, String strHomeTeam, String strAwayTeam, String strLeague, String strSeason,
			String dateEvent, String intHomeScore,String intAwayScore) {
		super();
		this.strEvent = strEvent;
		this.strHomeTeam = strHomeTeam;
		this.strAwayTeam = strAwayTeam;
		this.strLeague = strLeague;
		this.strSeason = strSeason;
		this.dateEvent = dateEvent;
		this.intHomeScore = intHomeScore;
		this.intAwayScore = intAwayScore;
	}

	public String getStrEvent() {
		return strEvent;
	}

	public void setStrEvent(String strEvent) {
		this.strEvent = strEvent;
	}

	public String getStrLeague() {
		return strLeague;
	}

	public void setStrLeague(String strLeague) {
		this.strLeague = strLeague;
	}

	public String getStrSeason() {
		return strSeason;
	}

	public void setStrSeason(String strSeason) {
		this.strSeason = strSeason;
	}

	public String getDateEvent() {
		return dateEvent;
	}

	public void setDateEvent(String dateEvent) {
		this.dateEvent = dateEvent;
	}

	public String getIntHomeScore() {
		return intHomeScore;
	}

	public void setIntHomeScore(String intHomeScore) {
		this.intHomeScore = intHomeScore;
	}

	public String getIntAwayScore() {
		return intAwayScore;
	}

	public void setIntAwayScore(String intAwayScore) {
		this.intAwayScore = intAwayScore;
	}

	public String getStrHomeTeam() {
		return strHomeTeam;
	}

	public void setStrHomeTeam(String strHomeTeam) {
		this.strHomeTeam = strHomeTeam;
	}

	public String getStrAwayTeam() {
		return strAwayTeam;
	}

	public void setStrAwayTeam(String strAwayTeam) {
		this.strAwayTeam = strAwayTeam;
	}

	@Override
	public String toString() {
		return "Evento: " + strEvent + "\nCampeonato: " + strLeague + "\nTemporada: " + strSeason
				+ "\nFecha evento: " + dateEvent + "\nResultado: " + intHomeScore + " - " + intAwayScore +
				"\n------------------------------------\n";
	}
	
	
}
