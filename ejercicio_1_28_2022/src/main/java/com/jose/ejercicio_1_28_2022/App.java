package com.jose.ejercicio_1_28_2022;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
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
    static String tokenWeather = "&appid=871a1c2012aa13906ae6b27ae2aff30b";
    static String urlBaseMarvel = "https://gateway.marvel.com:443/v1/public/characters?";
    static String tokenMarvel = "apikey=";
    static HistoricoBusqueda weatherLatLonFind = null;
    static HistoricoBusqueda weatherCityFind = null;
    
	public static void main( String[] args ) throws Exception
    {
		mostrarMenu();
    }
    
    /**
     * Método que muestra el menú principal de la aplicación
     */
    private static void mostrarMenu() throws IOException {
        System.out.println( "\nMenú (Selecione una opción)" );
        System.out.println( "1. Obtener información en base a unas coordenadas." );
        System.out.println( "2. Obtener información en una localidad determinada." );
        System.out.println( "3. Obterner información en una fecha determinada." );
        System.out.println( "4. Guardar datos de las búsquedas." );
        System.out.println( "5. Mostrar búsquedas guardadas." ); 
        System.out.println( "6. Obtener información de la API de Marvel" ); 
        
        try {
        	opcion = Integer.parseInt(teclado.next());
        }catch (Exception e) {
        	System.out.println( "\n***El valor introducido no es un número" );
        }
        
                
        switch (opcion) {
	    	case 1:
	    		pedirCoordenadas();
	    		break;
	    	case 2:
	    		pedirLocalidad();
	    		break;
	    	case 3:
				leerRangoFichero();
	    		break;
	    	case 4:
	    		serializarBusquedas();
	    		break;
	    	case 5:
	    		mostrarBusquedasGuardadas();
	    		break;
	    	case 6:
	    		caso6();
	    		break;
	    	default:
	            System.out.println( "\n***Debe seleccionar una de las opciones propuestas." );
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
    
    /**
	 * Pedir latitud y longitud y mostrar: nombre, temperatura, humedad y lista de weather
	 * de la información obtenida de la API en JSON
	 */
    private static void pedirCoordenadas(){
		teclado = new Scanner(System.in);
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
   	
    	String requestUrl = urlBase + urlLatitude + latitud + urlLongitude +longitud + tokenWeather;
    	WeatherRegistryComplex response = JsonUtils.devolverObjetoGsonGenerico(requestUrl, WeatherRegistryComplex.class);
    	System.out.println(response.toString());
    	weatherLatLonFind = new HistoricoBusqueda(
	    						LocalDate.now(),
					    		response.getName(),
					    		response.getMain().getTemp(),
					    		response.getMain().getHumidity());
    	
    	//tecladoCoordenadas.close();
    	repetir();
    }
    
    /**
	 * Pedir nombre localidad y mostrar: nombre, temperatura, humedad y lista de weather
	 * de la información obtenida de la API en XML
	 */
    private static void pedirLocalidad(){
    	String urlCity = "q=";
    	String urlXml = "&mode=xml";
    	System.out.println("Introducir localidad:");
		teclado = new Scanner(System.in);
    	String localidad = teclado.next();
    	String requestUrl = urlBase + urlCity + localidad + urlXml + tokenWeather;
    	
    	String cadenaXml = InternetUtils.readUrl(requestUrl);
    	
    	WeatherRegistry result = XmlUtils.procesarRegistroXml(cadenaXml);
    	System.out.println(result);
    	weatherCityFind = new HistoricoBusqueda(
	    		LocalDate.now(),
	    		result.getCity(),
	    		Double.parseDouble(result.getTemp()),
	    		Double.parseDouble(result.getHumidity()));
    	
    	//teclado.close();
    	
    	repetir();
    }
      
    /**
	 * Pedir 2 fechas y mostrar la evolución de la temperatura
	 * en los dias comprendidos entre "fechaInicio" y "fechaFin"
	 */
	private static void leerRangoFichero(){
    	pedirDosFechas();
    	
    	List<String> lineas = Ficheros.leerFichero8(".","datos.csv");    	
    	List<RegistroTemp> registros = new ArrayList<>();
    	
    	lineas.stream()
		.skip(2)
		.forEach(i ->  {String[] parts = i.split(",");
			RegistroTemp consulta = new RegistroTemp
										(
											dateUtils.comprobarFecha4(parts[5]),
											Double.parseDouble(parts[6].replace(",", "."))	
										);
			if (consulta.getFecha().compareTo(fechaInicio) >= 0 && consulta.getFecha().compareTo(fechaFin) <= 0) {
				registros.add(consulta);
			}
		});
    	
    	registros.sort(Comparator.comparing(RegistroTemp::getFecha));
    	registros.forEach(x->System.out.println(x));
    	    	
    	vaciarFechas();
    	repetir();
    }
    
	
    private static void serializarBusquedas(){
    	try {
        	Ficheros.crearFichero("./busquedas.dat");
        	
        	List<HistoricoBusqueda> listaBusquedasOld = SerializacionUtils.desSerializarListaObjetos("./busquedas.dat");       	
        	List<HistoricoBusqueda> nuevaListaBusquedas = new ArrayList<>();
        	   	
        	if(weatherLatLonFind != null) {
        		nuevaListaBusquedas = listaBusquedasOld.stream()
		        		.filter(e -> e.getDate() != LocalDate.now())
		        		.collect(Collectors.toList());      		
        		nuevaListaBusquedas.add(weatherLatLonFind);
        	}
        	
        	if(weatherCityFind != null)
        	{
        		if (nuevaListaBusquedas.isEmpty())
	        		nuevaListaBusquedas = listaBusquedasOld.stream()
					                		.filter(e -> !e.getDate().isEqual(LocalDate.now()))
					                		.collect(Collectors.toList());
        		
        		System.out.println(LocalDate.now());
        		
        		nuevaListaBusquedas.add(weatherCityFind);
        	}
        	
        	
        	if(nuevaListaBusquedas.isEmpty()) {
        		System.out.println("No hay búsquedas para serializar.");   		
        	} else {
        		System.out.println("Serializando...");
        		nuevaListaBusquedas.stream().forEach(e -> System.out.println("- " + e));
        		SerializacionUtils.serializarListaObjetos("./busquedas.dat", nuevaListaBusquedas);
        		System.out.println("Búsquedas recientes serializadas con éxito.");
        	}   		
    	} catch(Exception e) {
    		System.out.println("ERROR");
    		System.out.println(e.getMessage());
    	}

    	repetir();
    }
    
    private static void mostrarBusquedasGuardadas(){
    	List<HistoricoBusqueda> listaBusquedas = SerializacionUtils.desSerializarListaObjetos("./busquedas.dat");
    	
    	listaBusquedas.stream().forEach(e -> System.out.println("- " + e));   	
    	
    	repetir();
    }
    
    
    private static void caso6(){System.out.println("caso6");repetir();}    
    
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
        
        Instant instant = fechaFin.toInstant();
        Instant nextDay = instant.plus(1, ChronoUnit.DAYS);
        fechaFin = Date.from(nextDay);
	}
    
    /**
	 * Vacia las variables "fechaInicio" y "fechaFin"
	 */
	private static void vaciarFechas() {
        fechaInicio = null;
        fechaFin = null;
	}
}
