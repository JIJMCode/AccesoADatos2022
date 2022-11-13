package com.jose.ejercicio_2_15_2022;

import java.io.IOException;
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
    static List<String> categorias = new ArrayList<>();
    static List<String> flags = new ArrayList<>();
    static List<String> types = new ArrayList<>();
    static List<Language> idiomas = new ArrayList<>();
    static List<Joke> chistes = new ArrayList<>();
    static String insertCategorias = Literals.scriptInsertCategory;
    static String insertFlags = Literals.scriptInsertFlag;
    static String insertTypes = Literals.scriptInsertType;
    static String insertLanguages = Literals.scriptInsertLanguage;
    static int jokes_count;
    
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
			System.out.println(Literals.choose_option);
			System.out.println(Literals.repeat_title);
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
			System.out.println(Literals.app_closed);
			System.exit(0);
		}
	}
    
	/**
	 * Método que vacía la base de datos y vuelve a cargar todos los datos desde la api.
	 */
	protected static void cargaBdd() {
    	try {
		//Obtener datos
    		JokesInfo rootCategorias = JsonUtils.devolverObjetoGsonGenerico(Literals.url_get_categories, RootInfo.class).getJokesInfo();
		//Insertar categorías
    		rootCategorias.getCategories().forEach(e-> {
    			insertCategorias += (String.format(Literals.scriptCategory, e) + ", ");
    			categorias.add(e);
    		});
			int numCategorias = utilsPostgre.cargarBdd((insertCategorias.substring(0, insertCategorias.length() - 2)) + ";");
			System.out.println("Categorías añadidas: " + numCategorias);
		//Insertar flags
    		rootCategorias.getFlags().forEach(e-> {
    			insertFlags += (String.format(Literals.scriptFlag, e) + ", ");
    			flags.add(e);
    		});
			int numFlags = utilsPostgre.cargarBdd((insertFlags.substring(0, insertFlags.length() - 2)) + ";");
			System.out.println("Flags añadidos: " + numFlags);
		//Insertar types
    		rootCategorias.getTypes().forEach(e-> {
    			insertTypes += (String.format(Literals.scriptType, e) + ", ");
    			types.add(e);
    		});
			int numTypes = utilsPostgre.cargarBdd((insertTypes.substring(0, insertTypes.length() - 2)) + ";");
			System.out.println("Tipos añadidos: " + numTypes);
		//Insertar idiomas
	    	RootLanguages languages = JsonUtils.devolverObjetoGsonGenerico(Literals.url_get_languages, RootLanguages.class);
	    	idiomas = languages.getPossibleLanguages().stream().filter(e-> languages.getLanguages().contains(e.getCode())).collect(Collectors.toList());
	    	
	    	idiomas.forEach(e-> {
	    		insertLanguages += (String.format(Literals.scriptLanguage, e.getCode(), e.getName()) + ", ");
	    		e.setCount(rootCategorias.getSafeJokes().stream().filter(x->x.getLang().equals(e.getCode())).findFirst().get().getCount());
	    		});
	    	int numLanguages = utilsPostgre.cargarBdd((insertLanguages.substring(0, insertLanguages.length() - 2)) + ";");
			System.out.println("Idiomas añadidos: " + numLanguages);
		//Insertar chistes
			jokes_count = 0;
			idiomas.forEach(x-> {
	    		do {
	            //creación y grabació ndel chiste
	    			Joke chiste = JsonUtils.devolverObjetoGsonGenerico(String.format(Literals.url_get_joke, String.valueOf(jokes_count), x.getCode()), Joke.class);
	            	chistes.add(chiste);
	    			String jokeFormatted = chiste.getJoke() == null ? null : chiste.getJoke().replace("'", "''");
	            	String setupFormatted = chiste.getSetup() == null ? null : chiste.getSetup().replace("'", "''");
	            	String deiveryFormatted = chiste.getDelivery() == null ? null : chiste.getDelivery().replace("'", "''");
	            	String consultaJokeSql = String.format(Literals.scriptInsertJoke, chiste.getId(), chiste.getCategory(), chiste.getType(),
	            							jokeFormatted, setupFormatted, deiveryFormatted, chiste.getLang());
	            //creación y grabación de las relaciones del chiste con sus flags
	            	consultaJokeSql += String.format(Literals.scriptInsertJokesFlags, chiste.getId(), "nsfw", chiste.getFlags().getNsfw() ? 1 : 0);
	            	consultaJokeSql +=	String.format(Literals.scriptInsertJokesFlags, chiste.getId(), "religious", chiste.getFlags().getReligious());
	            	consultaJokeSql +=	String.format(Literals.scriptInsertJokesFlags, chiste.getId(), "political", chiste.getFlags().getPolitical());
	            	consultaJokeSql +=	String.format(Literals.scriptInsertJokesFlags, chiste.getId(), "racist", chiste.getFlags().getRacist());
	            	consultaJokeSql +=	String.format(Literals.scriptInsertJokesFlags, chiste.getId(), "sexist", chiste.getFlags().getSexist());
	            	consultaJokeSql +=	String.format(Literals.scriptInsertJokesFlags, chiste.getId(), "explicit", chiste.getFlags().getExplicit());
	            	utilsPostgre.ejecutarConsultaBdd(String.format(consultaJokeSql));
	            	jokes_count++;
					//pausa para no superar el límite de peticiones a la API 
	            	try {
						Thread.sleep(550);
					} catch (InterruptedException e1) {
						System.out.println(e1.getMessage());
						e1.printStackTrace();
					}
				} while (jokes_count<=x.getCount());
	    	});
			System.out.println("Chistes añadidos: " + jokes_count);
			System.out.println(Literals.bdd_full);
	
	        repetir();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    }
     
    protected static void resetearBdd(){
    	utilsPostgre.vaciarBdd();
		cargaBdd();
		repetir();
    }
    
    protected static void anyadirChisteStatement(){}
    protected static void anyadirChistePreparedStatement(){}
    protected static void buscarPorTexto(){}
    protected static void chistesSinFlags(){}
}
