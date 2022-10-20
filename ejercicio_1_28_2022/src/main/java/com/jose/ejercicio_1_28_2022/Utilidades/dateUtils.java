package com.jose.ejercicio_1_28_2022.Utilidades;

import java.text.ParseException;
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

public class dateUtils {
	// Suma los días recibidos a la fecha  
		public static Date sumarRestarDiasFecha(Date fecha, int dias){
	      Calendar calendar = Calendar.getInstance();

	      calendar.setTime(fecha); // Configuramos la fecha que se recibe
	
	      calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
	
	      return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
	 }
	 
	    public static Date comprobarFecha(String fecha) {
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        Date objDate = null;
	        String date = fecha;
	        
	        try{
	        	objDate = sdf.parse(date);
	        } catch (Exception e){ 
	        	System.out.println("Formato de fecha incorrecto");
	        	return null;
	        	}
	 
	        if (!sdf.format(objDate).equals(date) || date == null){
	            System.out.println("Formato de fecha incorrecto");
	        	return null;
	        }else {
	        	return objDate;
	        } 
		}
	
	    public static Date comprobarFecha2(String fecha) {
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	        Date objDate = null;
	        String date = fecha;
	        
	        try{
	        	objDate = sdf.parse(date);
	        } catch (Exception e){ 
	        	System.out.println("Formato de fecha incorrecto");
	        	return null;
	        	}
	 
	        if (!sdf.format(objDate).equals(date) || date == null){
	            System.out.println("Formato de fecha incorrecto");
	        	return null;
	        }else {
	        	return objDate;
	        } 
		}
	        
	    public static Date comprobarFecha3(String fecha) {
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	        CharSequence date = fecha.subSequence(0, 10);
	        
	    	Date objDate;

	    	try{
	        	objDate = sdf.parse((String) date);
;	        } catch (Exception e){ 
	        	System.out.println("Formato de fecha incorrecto");
	        	return null;
	        	}
	 	        
			return objDate; 
		}
	    
	    public static Date comprobarFecha4(String fecha) {
	    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss +0000 z");
	    	LocalDateTime dateTime = LocalDateTime.parse(fecha, dtf);
	    	Instant instantDate;
	    	Date objDate2;
	    	
	    	try{
	        	instantDate = dateTime.toInstant(ZoneOffset.UTC);
	        	objDate2 = Date.from(instantDate);
	        } catch (Exception e){ 
	        	System.out.println("Formato de fecha incorrecto");
	        	return null;
	        	}
	 	        
			return objDate2; 
		}
	    
	    
	    
//	    public static Date comprobarFecha4(String fecha) {
//	    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss +0000 z");
//	    	LocalDateTime dateTime = LocalDateTime.parse(fecha, dtf);
//	    	Instant instantDate;
//	    	Date objDate2;
//	    	
//	    	try{
//	        	instantDate = dateTime.toInstant(ZoneOffset.UTC);
//	        	objDate2 = Date.from(instantDate);
//	        } catch (Exception e){ 
//	        	System.out.println("Formato de fecha incorrecto");
//	        	return null;
//	        	}
//	 	        
//			return objDate2; 
//		}

	    public static Date removeTime(Date date) {      
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	Date dateWithoutTime = null;
			try {
				dateWithoutTime = sdf.parse(sdf.format(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ojooooo");
			}
	        return dateWithoutTime; 
	    }
	    
}
