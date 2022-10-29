package com.jose.ejercicio_1_28_2022.Utilidades;

public class FormatUtils {
	public static boolean validarCoordenada(String coord, String type) {
		boolean result = false;
		try {
			Double coordenada = Double.parseDouble(coord);
			
			if ((type.equalsIgnoreCase("latitude") && coordenada <= 90 && coordenada >= -90) ||
				(type.equalsIgnoreCase("longitude") && coordenada <= 180 && coordenada >= -180))
			{
				return true;
			} else {
				String message = type.equalsIgnoreCase("latitude") ? Literals.errorLatitud : Literals.errorLongitud;
				System.out.println(Literals.wrongFormat + message);
				return false;				
			}		
		} 
		catch (Exception e) {
			System.out.println("Formato incorrecto, pruebe a introducir de nuevo");
			return false;
		}
	}	
}
