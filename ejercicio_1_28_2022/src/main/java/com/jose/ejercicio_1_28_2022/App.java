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
import com.jose.ejercicio_1_28_2022.Entidades.*;
import com.jose.ejercicio_1_28_2022.Utilidades.*;

/**
 * @author JOSE IGNACIO JIMENEZ MAYOR
 *
 */
public class App 
{
    static int opcion;
    static Scanner teclado = new Scanner(System.in);
    static Date fechaInicio;
    static Date fechaFin;
    static HistoricoBusqueda weatherLatLonFind = null;
	static String equipoLocal = "";
	static String equipoVisitante = "";
	static String latitud;
	static String longitud;
    
	public static void main( String[] args ) throws Exception
    {
		mostrarMenu();
    }
    
    /**
     * Método que muestra el menú principal de la aplicación
     */
    private static void mostrarMenu() throws IOException {
        System.out.println( Literals.menu_title );
        System.out.println( Literals.menu_1 );
        System.out.println( Literals.menu_2 );
        System.out.println( Literals.menu_3 );
        System.out.println( Literals.menu_4 );
        System.out.println( Literals.menu_5 ); 
        System.out.println( Literals.menu_6 ); 
        System.out.println( Literals.menu_exit ); 
        
        try {
        	opcion = Integer.parseInt(teclado.next());
        }catch (Exception e) {
        	System.out.println(Literals.error_no_num);
        }
        
                
        switch (opcion) {
	    	case 0:
	    		cerrarApp();
	    		break;
	        case 1:
	        	climaSegunCoordenadas();
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
	    		enfrentamientosFutbol();
	    		break;
	    	default:
	            System.out.println(Literals.choose_option);
	            mostrarMenu();
        }
    }
       
    /**
     * Método que vuelve a lanzar el menú tras la ejecución de cada opción
     */
    private static void repetir() {
		System.out.println(Literals.continue_exit);
		
		teclado = new Scanner(System.in);

		String seleccion = teclado.next();
		char caracter = seleccion.charAt(0);
		
		while (caracter != 'c' && caracter != 's') {
			System.out.println(Literals.continue_exit);
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
			System.out.println(Literals.app_closed);
			System.exit(0);
		}
	}
    
    private static void cerrarApp() {
		teclado.close();
		System.out.println(Literals.app_closed);
		System.exit(0);
    } 
    
    /**
	 * Pedir latitud y longitud y mostrar: nombre, temperatura, humedad y lista de weather
	 * de la información obtenida de la API en JSON
	 */
    private static void climaSegunCoordenadas(){
		pedirCoordenadas();
		
    	String requestUrl = Literals.urlBase + Literals.url_latitude + latitud + Literals.url_longitude + longitud + Literals.tokenWeather;
    	WeatherRegistryComplex response = JsonUtils.devolverObjetoGsonGenerico(requestUrl, WeatherRegistryComplex.class);
    	System.out.println(response.toString());
    	weatherLatLonFind = new HistoricoBusqueda(
	    						LocalDate.now(),
					    		response.getName(),
					    		response.getMain().getTemp(),
					    		response.getMain().getHumidity(),
					    		response.getWeather());
    	
    	repetir();
    }
    
    /**
	 * Pedir y validar latitud y longitud
	 */
    private static void pedirCoordenadas(){
		teclado = new Scanner(System.in);
  	
    	do {
    		System.out.println(Literals.insert_lat);
        	latitud = teclado.next().replace(",", ".");
		} while (!FormatUtils.validarCoordenada(latitud, "latitude"));
    	

    	do {
        	System.out.println(Literals.insert_lon);
        	longitud = teclado.next().replace(",", ".");
		} while (!FormatUtils.validarCoordenada(longitud, "longitude"));
    }
    
    /**
	 * Pedir nombre localidad y mostrar: nombre, temperatura, humedad y lista de weather
	 * de la información obtenida de la API en XML
	 */
    private static void pedirLocalidad(){
    	System.out.println(Literals.insert_city);
		teclado = new Scanner(System.in);
    	String localidad = teclado.nextLine();
    	String requestUrl = Literals.urlBase + Literals.urlCity + localidad + Literals.urlXml + Literals.tokenWeather;
    	
    	String cadenaXml = InternetUtils.readUrl(requestUrl);
    	
    	WeatherRegistry result = XmlUtils.procesarRegistroXml(cadenaXml);
    	System.out.println(result);
    	weatherLatLonFind = new HistoricoBusqueda(
	    		LocalDate.now(),
	    		result.getCity(),
	    		Double.parseDouble(result.getTemp()),
	    		Double.parseDouble(result.getHumidity()),
	    		result.getweather());
    	
    	repetir();
    }
      
    /**
	 * Pedir 2 fechas y mostrar la evolución de la temperatura
	 * en los dias comprendidos entre "fechaInicio" y "fechaFin"
	 */
	private static void leerRangoFichero(){
    	pedirDosFechas();
    	
    	List<String> lineas = Ficheros.leerFichero8(Literals.basePath,Literals.datos_path);    	
    	List<RegistroTemp> registros = new ArrayList<>();
    	
    	lineas.stream()
		.skip(1)
		.forEach(i ->  {String[] parts = i.split(",");
			RegistroTemp consulta = new RegistroTemp
										(
											dateUtils.comprobarFecha4(parts[5]),
											Double.parseDouble(parts[6].replace(",", "."))	
										);
			boolean duplicado = registros.stream().anyMatch(x -> x.getFecha().equals(consulta.getFecha()));
			if (consulta.getFecha().compareTo(fechaInicio) >= 0 &&
				consulta.getFecha().compareTo(fechaFin) <= 0 &&
				duplicado == false) {
				registros.add(consulta);
			}
		});
    	
    	registros.sort(Comparator.comparing(RegistroTemp::getFecha));
    	registros.forEach(x->System.out.println(x));
    	System.out.println(String.format(Literals.history_count, registros.size()));
    	    	
    	vaciarFechas();
    	repetir();
    }
    
    private static void serializarBusquedas(){
    	try {
        	Ficheros.crearFichero(Literals.historicoPath);
        	
        	List<HistoricoBusqueda> listaBusquedasOld = SerializacionUtils.desSerializarListaObjetos(Literals.historicoPath);
        	   	
        	if(listaBusquedasOld.size()>0) {
        		if (weatherLatLonFind != null)
        			listaBusquedasOld = ListUtils.eliminarDuplicado(weatherLatLonFind, listaBusquedasOld); 
        	} else
        		listaBusquedasOld.add(weatherLatLonFind);  	
        	
        	if(weatherLatLonFind == null) {
        		System.out.println(Literals.nothing_serialize);   		
        	} else {
        		System.out.println(Literals.serializing);
        		SerializacionUtils.serializarListaObjetos(Literals.historicoPath, listaBusquedasOld);
        		System.out.println(Literals.serialize_ok);
        	}   		
    	} catch(Exception e) {
    		System.out.println(Literals.error);
    		System.out.println(e.getMessage());
    	}

    	repetir();
    }
    
    private static void mostrarBusquedasGuardadas(){
    	List<HistoricoBusqueda> listaBusquedas = SerializacionUtils.desSerializarListaObjetos(Literals.historicoPath);
    	System.out.println(Literals.history_list);   	
    	listaBusquedas.stream().forEach(e -> System.out.println("- " + e));   	
    	
    	repetir();
    }
    
    private static void enfrentamientosFutbol(){
    	pedirDosEquiposFutbol();
    	    	
    	String urlFutbolCompleta = String.format(Literals.urlBaseFutbol + equipoLocal + Literals.url_vs + equipoVisitante);
    	
    	RootEventoFutbol evento = JsonUtils.devolverObjetoGsonGenerico(urlFutbolCompleta, RootEventoFutbol.class);
    	evento.getEvent().stream().forEach(e -> System.out.println(e));
    	
    	int totalScoreHome = evento.getEvent().stream().mapToInt(e -> Integer.parseInt(e.getIntHomeScore())).sum();
    	int totalScoreAway = evento.getEvent().stream().mapToInt(e -> Integer.parseInt(e.getIntAwayScore())).sum();
    	
    	System.out.println(String.format(Literals.total_goals, evento.getEvent().stream().findFirst().get().getStrHomeTeam(), totalScoreAway));
    	System.out.println(String.format(Literals.total_goals, evento.getEvent().stream().findFirst().get().getStrAwayTeam(), totalScoreAway));
    	repetir();
    } 
    
	/**
	 * Método que pide 2 fechas por consola y comprueba que tienen el formato correcto
	 */
	private static void pedirDosEquiposFutbol() {
        //Scanner teclado2 = new Scanner(System.in);
		teclado = new Scanner(System.in);
		
		try {
			while (equipoLocal.isBlank()) {
		        System.out.println( Literals.insert_local );   
		        equipoLocal = teclado.nextLine();
			}

	        while (equipoVisitante.isBlank()) {
	            System.out.println( Literals.insert_visitant );
	            equipoVisitante = teclado.nextLine();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
    
	/**
	 * Método que pide 2 fechas por consola y comprueba que tienen el formato correcto
	 */
	private static void pedirDosFechas() {
        //Scanner teclado2 = new Scanner(System.in);
		teclado = new Scanner(System.in);
		
		while (fechaInicio == null) {
	        System.out.println(Literals.insert_start_date);   
	        fechaInicio = dateUtils.comprobarFecha(teclado.nextLine());
		}

        while (fechaFin == null) {
            System.out.println(Literals.insert_end_date);
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
