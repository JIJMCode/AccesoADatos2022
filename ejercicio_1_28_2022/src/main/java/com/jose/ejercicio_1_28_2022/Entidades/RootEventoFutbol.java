package com.jose.ejercicio_1_28_2022.Entidades;

import java.util.List;

public class RootEventoFutbol {
	private List<EventoFutbol> event;

	@Override
	public String toString() {
		return "RootEventoFutbol []";
	}

	public RootEventoFutbol(List<EventoFutbol> event) {
		super();
		this.event = event;
	}

	public List<EventoFutbol> getEvent() {
		return event;
	}

	public void setEvent(List<EventoFutbol> event) {
		this.event = event;
	}

	
}
