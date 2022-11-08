package com.jose.ejercicio_2_15_2022.Utilidades;

import java.util.List;
import java.util.Optional;


public class ListUtils {
	// Elimina resgistro con misma fecha  
//	public static List<HistoricoBusqueda> eliminarDuplicado (HistoricoBusqueda historicoWeather, List<HistoricoBusqueda> listaBusquedasNew) {
//		Optional<HistoricoBusqueda> weatherCityFindDuplicado = listaBusquedasNew.stream()
//				.filter(x -> x.getDate().equals(historicoWeather.getDate()))
//				.findAny();
//
//		if (!weatherCityFindDuplicado.isEmpty()) {
//			System.out.println(Literals.deleting + " (" + weatherCityFindDuplicado.get().getCity() + "-" + weatherCityFindDuplicado.get().getDate() + ").");
//			listaBusquedasNew.remove(weatherCityFindDuplicado.get());
//			System.out.println(Literals.deleted);
//		}
//		
//		listaBusquedasNew.add(historicoWeather);
//		
//		return listaBusquedasNew;
//	}
//	    
}
