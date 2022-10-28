package com.jose.ejercicio_1_28_2022.Utilidades;

public class FormatUtils {
	public static boolean validarCoordenada(String coord, String type) {
		boolean result = false;
		try {
			Double coordenada = Double.parseDouble(coord);
			
			if ((type.equalsIgnoreCase("latitude") && coordenada <= 90 || coordenada >= 90) ||
				(type.equalsIgnoreCase("longitude") && coordenada <= 180 || coordenada >= -180))
			{
				result = true;
			} else {
				result = false;				
			}		
		} 
		catch (Exception e) {
			result = false;
		}
		finally {
			if (!result) {System.out.println("Formato incorrecto, pruebe a introducir de nuevo");}
			return result;
		}	
	}	
}
