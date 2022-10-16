package com.jose.ejercicio_1_28_2022;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.jose.ejercicio_1_28_2022.Entidades.WeatherRegistryComplex;
import com.jose.ejercicio_1_28_2022.Utilidades.FormatUtils;
import com.jose.ejercicio_1_28_2022.Utilidades.InternetUtils;
import com.jose.ejercicio_1_28_2022.Utilidades.JsonUtils;

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
				caso3();
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
    
    private static void caso3(){System.out.println("caso3");repetir();}
    private static void caso4(){System.out.println("caso4");repetir();}
    private static void caso5(){System.out.println("caso5");repetir();}
    private static void caso6(){System.out.println("caso6");repetir();}
    
    
}
