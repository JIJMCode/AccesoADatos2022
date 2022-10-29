package com.jose.ejercicio_1_28_2022.Utilidades;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.jose.ejercicio_1_28_2022.Entidades.HistoricoBusqueda;

public class ListUtils {
	// Elimina resgistro con misma fecha  
	public static List<HistoricoBusqueda> eliminarDuplicado ( HistoricoBusqueda historicoWeather, List<HistoricoBusqueda> listaBusquedasNew) {
		Optional<HistoricoBusqueda> weatherCityFindDuplicado = listaBusquedasNew.stream()
				.filter(x -> x.getDate().equals(historicoWeather.getDate()))
				.findAny();

		if (weatherCityFindDuplicado != null) {
		listaBusquedasNew.remove(weatherCityFindDuplicado.get());
		}
		
		listaBusquedasNew.add(historicoWeather);
		
		return listaBusquedasNew;
	}
	    
}
