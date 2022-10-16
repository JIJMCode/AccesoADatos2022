package com.jose.ejercicio_1_28_2022;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.jose.ejercicio_1_28_2022.Entidades.*;
import com.jose.ejercicio_1_28_2022.Utilidades.*;

/**
 * Hello world!
 *
 */
public class App 
{
    static int opcion;
    static Scanner teclado = new Scanner(System.in);
    static Date fechaInicio;
    static Date fechaFin;
    static String urlBase = "https://api.openweathermap.org/data/2.5/weather?";
    static String token = "&appid=871a1c2012aa13906ae6b27ae2aff30b";
    
	public static void main( String[] args ) throws Exception
    {
		mostrarMenu();
    }
    
    /**
     * Método que muestra el menú principal de la aplicación
     */
    private static void mostrarMenu() throws IOException {
        System.out.println( "\nMenú (Selecione una opción)" );
        System.out.println( "1. Obtener PCR's realizadas entre dos fechas." );
        System.out.println( "2. Obtener días con mayor número de fallecimientos entre dos fechas." );
        System.out.println( "3. Incidencia acumulada en una fecha determinada." );
        System.out.println( "4. Ver datos de incidencias registrados." );
        System.out.println( "5. Saber cuantos días ha habido más PCR's positivas en hombres que en mujeres, y viceversa." ); 
        System.out.println( "6. Comparar entre dos fuentes, los datos de fallecidos y casos confirmados en una fecha." ); 
        
        opcion = teclado.nextInt();
        
        
        switch (opcion) {
	    	case 1:
	    		pedirLatitud();
	    		break;
	    	case 2:
	    		pedirLocalidad();
	    		break;
	    	case 3:
				leerRangoFichero();
	    		break;
	    	case 4:
	    		caso4();
	    		break;
	    	case 5:
	    		caso5();
	    		break;
	    	case 6:
	    		caso6();
	    		break;
	    	default:
	            System.out.println( "\nDebe seleccionar una de las opciones propuestas." );
	            mostrarMenu();
        }
    }
       
    /**
     * Método que vuelve a lanzar el menú tras la ejecución de cada opción
     */
    private static void repetir() {
		System.out.println("\nPulse C para continuar o S para salir\n");
		
		teclado = new Scanner(System.in);

		String seleccion = teclado.next();
		char caracter = seleccion.charAt(0);
		
		while (caracter != 'c' && caracter != 's') {
			System.out.println("\nDebe elgir C para continuar o S para salir\n");
			caracter = teclado.next().charAt(0);}
		
		if (caracter == 'c') {
			try {
				mostrarMenu();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			teclado.close();
			System.out.println("\nAplicación cerrada. Hasta pronto!!!");
			System.exit(0);
		}
	}
    
    private static void pedirLatitud(){
    	Scanner tecladoCoordenadas = new Scanner(System.in);
    	String urlLongitude = "&lon=";
    	String urlLatitude = "lat=";
    	String latitud;
    	do {
    		System.out.println("Introducir latitud:");
        	latitud = teclado.next();       	
		} while (!FormatUtils.validarCoordenada(latitud, "latitude"));
    	
    	String longitud;
    	do {
        	System.out.println("Introducir longitud:");
        	longitud = teclado.next();
		} while (!FormatUtils.validarCoordenada(longitud, "longitude"));
   	
    	String requestUrl = urlBase + urlLatitude + latitud + urlLongitude +longitud + token;
    	WeatherRegistryComplex response = JsonUtils.devolverObjetoGsonGenerico(requestUrl, WeatherRegistryComplex.class);
    	System.out.println(response.toString());
    	tecladoCoordenadas.close();
    	repetir();
    }
    
    private static void pedirLocalidad(){
    	String urlCity = "q=";
    	System.out.println("Introducir localidad:");
    	Scanner tecladoLocalidad = new Scanner(System.in);
    	String localidad = tecladoLocalidad.nextLine();
    	String requestUrl = urlBase + urlCity + localidad + token;
    	
    	WeatherRegistryComplex response = JsonUtils.devolverObjetoGsonGenerico(requestUrl, WeatherRegistryComplex.class);
    	System.out.println(response.toString());
    	tecladoLocalidad.close();
    	repetir();
    }
    
	/**
	 * Método que pide 2 fechas por consola y comprueba que tienen el formato correcto
	 */
	private static void pedirDosFechas() {
        //Scanner teclado2 = new Scanner(System.in);
		teclado = new Scanner(System.in);
		
		while (fechaInicio == null) {
	        System.out.println( "\nIntroduzca la primera fecha con formato dd/mm/yyyy:" );   
	        fechaInicio = dateUtils.comprobarFecha(teclado.nextLine());
		}

        while (fechaFin == null) {
            System.out.println( "\nIntroduzca la segunda fecha con formato dd/mm/yyyy:" );
            fechaFin = dateUtils.comprobarFecha(teclado.nextLine());
		}
        
        /*Instant instant = fechaFin.toInstant();
        Instant nextDay = instant.plus(1, ChronoUnit.DAYS);
        fechaFin = Date.from(nextDay);*/
	}
    
    /**
	 * Vacia las variables "fechaInicio" y "fechaFin"
	 */
	private static void vaciarFechas() {
        fechaInicio = null;
        fechaFin = null;
	}

	
	private static void leerRangoFichero(){
    	pedirDosFechas();
    	
    	List<String> lineas = Ficheros.leerFichero8(".","datos.csv");
    	
    	List<RegistroTemp> registros = new ArrayList<>();
    	
    	lineas.stream()
		.skip(2)
		.forEach(i ->  {String[] parts = i.split(",");
			RegistroTemp consulta = new RegistroTemp
										(
												dateUtils.comprobarFecha3(parts[5]),
												Double.parseDouble(parts[6].replace(",", "."))	
										);
			if (consulta.getFecha().compareTo(fechaInicio) >= 0 && consulta.getFecha().compareTo(fechaFin) <= 0) {
				registros.add(consulta);
			}
			/*if (consulta.getFecha().compareTo(fechaInicio) == 0) {
				registros.add(consulta);
			} */
		});
    	
    	/*List<RegistroTemp> registrosFiltrados = registros.stream()
    	.filter(x-> !x.getFecha().compateTo(fechaFin))
    	.collect(Collectors.toList());*/
    	
    	/*.filter(x-> !x.getFecha().after(fechaFin))
    	//.filter(x-> !x.getFecha().before(fechaInicio)*/

    	registros.sort(Comparator.comparing(RegistroTemp::getFecha));
    	registros.forEach(x->System.out.println(x));
    	    	
    	vaciarFechas();
    	repetir();
    }
    
    private static void caso4(){System.out.println("caso4");repetir();}
    private static void caso5(){System.out.println("caso5");repetir();}
    private static void caso6(){System.out.println("caso6");repetir();}
    
    
}