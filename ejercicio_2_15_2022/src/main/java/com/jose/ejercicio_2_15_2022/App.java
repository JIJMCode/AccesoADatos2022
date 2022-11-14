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
    static String url = "jdbc:postgresql://localhost:5432/jokes";
    static String usuario = "postgres";
    static String password = "postgre";
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
    static int menu_count;
    
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
	            	consultaJokeSql += String.format(Literals.scriptInsertJokesFlags, chiste.getId(), "nsfw", chiste.getFlags().getNsfw());
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
    
    protected static void menuNuevoChiste(){
        try {
        	Joke newJoke = new Joke();
	    //solicitar categoría
        	menu_count = 0;
	    	System.out.println(Literals.new_joke); 
	        System.out.println(Literals.new_joke_category);
	        categorias.forEach(e-> {
	        	menu_count++;
	        	System.out.println(menu_count + "-. " + e);
	        	});
        	int new_category = Integer.parseInt(teclado.next());
        	newJoke.setCategory(new_category);
    	//solicitar idioma
        	menu_count = 0;
	        System.out.println(Literals.new_joke_language);
	        idiomas.forEach(e-> {
	        	menu_count++;
	        	System.out.println(menu_count + "-. " + e.getName());
	        	});
        	int new_lang = Integer.parseInt(teclado.next());
        	String new_language = idiomas.get(new_lang).getCode();
        //solicitar tipo
        	menu_count = 0;
	        System.out.println(Literals.new_joke_category);
	        types.forEach(e-> {
	        	menu_count++;
	        	System.out.println(menu_count + "-. " + e);
	        	});
        	int new_type = Integer.parseInt(teclado.next());
        	newJoke.setType(new_type);
        //solicitar flags
        	Flags newFlags = new Flags();
	        System.out.println(Literals.new_joke_nsfw);
	        System.out.println(Literals.menu_yes_no);       	
	        newFlags.setNsfw(checkTrueFalse());
	        System.out.println(Literals.new_joke_religious);
	        System.out.println(Literals.menu_yes_no);       	
	        newFlags.setReligious(checkTrueFalse());
	        System.out.println(Literals.new_joke_political);
	        System.out.println(Literals.menu_yes_no);       	
	        newFlags.setPolitical(checkTrueFalse());
	        System.out.println(Literals.new_joke_racist);
	        System.out.println(Literals.menu_yes_no);       	
	        newFlags.setRacist(checkTrueFalse());
	        System.out.println(Literals.new_joke_sexist);
	        System.out.println(Literals.menu_yes_no);       	
	        newFlags.setSexist(checkTrueFalse());
	        System.out.println(Literals.new_joke_explicit);
	        System.out.println(Literals.menu_yes_no);       	
	        newFlags.setExplicit(checkTrueFalse());
        	newJoke.setFlags(newFlags);
        //solicitar chiste
        	menu_count = 0;
        	String chiste;
        	String setup;
        	String delivery;
        	do {
            	if (new_type == 1) {
        	        System.out.println(Literals.new_joke_joke);
                	chiste = teclado.nextLine();
                	newJoke.setJoke(chiste);
    			} else {
        	        System.out.println(Literals.new_joke_setup);
        	        setup = teclado.nextLine();
        	        newJoke.setSetup(setup);
        	        System.out.println(Literals.new_joke_delivery);
        	        delivery = teclado.nextLine();
        	        newJoke.setDelivery(delivery);
    			}
			} while (new_type < 1 || new_type > 2);	
        //almacenar chiste
        	utilsPostgre.guardarChiste(newJoke);
        	
        }catch (Exception e) {
        	System.out.println(Literals.error_no_num);
        }
    }
       
    public static boolean checkTrueFalse() {
        int option;
        do {
    		try {
	        	option = Integer.parseInt(teclado.next());
    		} catch (Exception e) {
    			option = 0;
    		}
		} while (option<1 || option>2);
        
        boolean result = option == 1 ? true : false;
        
        return result;
    }
    
    protected static void anyadirChisteStatement(){
    	menuNuevoChiste();
    }
    protected static void anyadirChistePreparedStatement(){}
    protected static void buscarPorTexto(){}
    protected static void chistesSinFlags(){}
}
