package com.jose.ejercicio_2_15_2022;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.jose.ejercicio_2_15_2022_Utilidades.*;
import com.jose.ejercicio_2_15_2022_Entidades.*;

/**
 * @author Jose Ignacio Jimenez Mayor
 */
public class App 
{
    static int opcion;
    static Scanner teclado = new Scanner(System.in);
    static Date fechaInicio;
    static Date fechaFin;
	static String equipoLocal = "";
	static String equipoVisitante = "";
	static String latitud;
	static String longitud;
    static List<String> categorias;
    static List<String> flags;
    static List<String> types;
    static List<Language> idiomas = new ArrayList<Language>();
    static List<Joke> chistes = new ArrayList<Joke>();
    
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
        System.out.println( Literals.menu_exit ); 
        
        try {
        	opcion = Integer.parseInt(teclado.next());
        }catch (Exception e) {
        	System.out.println(Literals.error_no_num);
        }
                        
        switch (opcion) {
	        case 1:
	        	resetearBdd();
	    		break;
	    	case 2:
	        	anyadirChisteStatement();
	    		break;
	    	case 3:
	    		anyadirChistePreparedStatement();
	    		break;
	    	case 4:
				buscarPorTexto();
	    		break;
	    	case 5:
	    		chistesSinFlags();
	    		break;
	    	default:
	            System.out.println(Literals.choose_option);
	            mostrarMenu();
        }
    }
    
	/**
	 * Método ofrece la posibilidad de volver al menú principal o salir de la aplicación,
	 * tras cada consulta
	 */
    private static void repetir() {

    	System.out.println();
		
		String seleccion = teclado.next();
		char caracter = seleccion.charAt(0);
		
		while (caracter != 'c' && caracter != 's') {
			System.out.println("\nInténtelo de nuevo, pulce C para ir al menú o S para salir\n");
			caracter = teclado.next().charAt(0);
		}
		
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
	 * Método que vacía la base de datos y vuelve a cargar todos los datos desde la api.
	 */
	protected static void cargaBdd() {
    	JokesInfo rootCategorias = JsonUtils.devolverObjetoGsonGenerico(Literals.url_get_categories, RootInfo.class).getJokesInfo();
		categorias = rootCategorias.getCategories();
    	flags = rootCategorias.getFlags();
    	types = rootCategorias.getTypes();
    	RootLanguages languages = JsonUtils.devolverObjetoGsonGenerico(Literals.url_get_languages, RootLanguages.class);
    	idiomas = languages.getPossibleLanguages().stream().filter(e-> languages.getLanguages().contains(e.getCode())).collect(Collectors.toList());
//    	languages.getPossibleLanguages().stream().forEach(x-> {
//    		if (languages.getLanguages().contains(x.getCode())) {
//				idiomas.add(x);}});;
    	idiomas.forEach(e-> {
    		e.setCount(rootCategorias.getSafeJokes().stream().filter(x->x.getLang().equals(e.getCode())).findFirst().get().getCount());
    	});
    	
    	
//    	utilsJava.insertarPlanetasBdd("https://swapi.dev/api/planets/?format=json");
//    	utilsJava.insertarEspeciesBdd("https://swapi.dev/api/species/?format=json");
//    	utilsJava.insertarNavesBdd("https://swapi.dev/api/starships/?format=json");
//    	utilsJava.insertarVehiculosBdd("https://swapi.dev/api/vehicles/?format=json");
//  	utilsJava.insertarPeliculasBdd("https://swapi.dev/api/films/?format=json");
//    	utilsJava.insertarPersonajesBdd("https://swapi.dev/api/people/?format=json");
//    	utilsJava.insertarRelacionesPeliculasBdd("https://swapi.dev/api/films/?format=json");
//    	utilsJava.insertarRelacionesPersonajesBdd("https://swapi.dev/api/people/?format=json");
		System.out.println(Literals.bdd_full);
		idiomas.forEach(e-> System.out.println(e.getCode() + " - " + e.getName()));
        repetir();
    }
     
    protected static void resetearBdd(){
    	//utilsPostgre.vaciarBdd();
		
		cargaBdd();
    }
    protected static void anyadirChisteStatement(){}
    protected static void anyadirChistePreparedStatement(){}
    protected static void buscarPorTexto(){}
    protected static void chistesSinFlags(){}
    

}
