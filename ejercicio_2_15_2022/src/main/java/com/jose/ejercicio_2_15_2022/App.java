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
    static JokesInfo rootCategorias;
    static RootLanguages languages;
    
	public static void main( String[] args ) throws Exception
    {
		//Obtener datos
		rootCategorias = JsonUtils.devolverObjetoGsonGenerico(Literals.url_get_categories, RootInfo.class).getJokesInfo();
		languages = JsonUtils.devolverObjetoGsonGenerico(Literals.url_get_languages, RootLanguages.class);
		rellenarListas();
		mostrarMenu();
    }
	
	public static void rellenarListas() {
		rootCategorias.getCategories().forEach(e-> {categorias.add(e);});		
		rootCategorias.getFlags().forEach(e-> {	flags.add(e);});
		rootCategorias.getTypes().forEach(e-> {types.add(e);});
    	idiomas = languages.getPossibleLanguages().stream().filter(e-> languages.getLanguages().contains(e.getCode())).collect(Collectors.toList());
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
		System.out.println(Literals.repeat_title);
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
    
    
    static String consultaJokeSql;
	/**
	 * Método que vacía la base de datos y vuelve a cargar todos los datos desde la api.
	 */
	protected static void cargaBdd() {
    	try {
		//Insertar categorías
    		categorias.forEach(e-> {insertCategorias += (String.format(Literals.scriptCategory, e) + ", ");});
			int numCategorias = utilsPostgre.cargarBdd((insertCategorias.substring(0, insertCategorias.length() - 2)) + ";");
			System.out.println("Categorías añadidas: " + numCategorias);
		//Insertar flags
    		flags.forEach(e-> {insertFlags += (String.format(Literals.scriptFlag, e) + ", ");});
			int numFlags = utilsPostgre.cargarBdd((insertFlags.substring(0, insertFlags.length() - 2)) + ";");
			System.out.println("Flags añadidos: " + numFlags);
		//Insertar types
    		types.forEach(e-> {insertTypes += (String.format(Literals.scriptType, e) + ", ");});
			int numTypes = utilsPostgre.cargarBdd((insertTypes.substring(0, insertTypes.length() - 2)) + ";");
			System.out.println("Tipos añadidos: " + numTypes);
		//Insertar idiomas    	
	    	idiomas.forEach(e-> {
	    		insertLanguages += (String.format(Literals.scriptLanguage, e.getCode(), e.getName()) + ", ");
	    		e.setCount(rootCategorias.getSafeJokes().stream().filter(x->x.getLang().equals(e.getCode())).findFirst().get().getCount());});
	    	int numLanguages = utilsPostgre.cargarBdd((insertLanguages.substring(0, insertLanguages.length() - 2)) + ";");
			System.out.println("Idiomas añadidos: " + numLanguages);
		//Insertar chistes
			jokes_count = 0;
			idiomas.forEach(x-> {
	    		do {
	            //creación y grabació ndel chiste
	    			Joke chiste = JsonUtils.devolverObjetoGsonGenerico(String.format(Literals.url_get_joke, String.valueOf(jokes_count), x.getCode()), Joke.class);
	            	chistes.add(chiste);
	    			String jokeFormatted = chiste.getJoke() == null ? null : chiste.getJoke().replace("'", "''").replace("%", "%%");
	            	String setupFormatted = chiste.getSetup() == null ? null : chiste.getSetup().replace("'", "''").replace("%", "%%");
	            	String deiveryFormatted = chiste.getDelivery() == null ? null : chiste.getDelivery().replace("'", "''").replace("%", "%%");
	            	consultaJokeSql = String.format(Literals.scriptInsertJoke, chiste.getCategory(), chiste.getType(),
	            																	jokeFormatted, setupFormatted, deiveryFormatted, chiste.getLang());
	            //creación y grabación de las relaciones del chiste con sus flags
	            	if(chiste.getFlags() != null) {
		            	if (chiste.getFlags().getNsfw()) { newFlags.add(new Flag("nsfw"));}
		            	if (chiste.getFlags().getReligious()) { newFlags.add(new Flag("religious"));}
		            	if (chiste.getFlags().getPolitical()) { newFlags.add(new Flag("political"));}
		            	if (chiste.getFlags().getRacist()) { newFlags.add(new Flag("racist"));}
		            	if (chiste.getFlags().getSexist()) { newFlags.add(new Flag("sexist"));}
		            	if (chiste.getFlags().getExplicit()) { newFlags.add(new Flag("explicit"));}
		            	
		            	if (newFlags.size()>0) {
			            	newFlags.forEach(e-> {consultaJokeSql += String.format(Literals.scriptInsertJokesFlags, chiste.getId(), e.getName());});
			            	newFlags.clear();
						}
	            	}
            	
	            	utilsPostgre.ejecutarConsultaBdd(String.format(consultaJokeSql));
	            	jokes_count++;
	            	System.out.println(jokes_count);
					//pausa para no superar el límite de peticiones a la API 
	            	try {
						Thread.sleep(550);
					} catch (InterruptedException e1) {
						System.out.println(e1.getMessage());
						e1.printStackTrace();
					}
				} while (jokes_count <= x.getCount());
	    	});
			System.out.println("Chistes añadidos: " + jokes_count);
			System.out.println(Literals.bdd_full);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    }
     
	/**
	 * Menú opción 2
	 * Método que vacía la base de datos y vuelve a cargar todos los datos desde la api.
	 */
    protected static void resetearBdd(){
    	//utilsPostgre.vaciarBdd();
		//cargaBdd();
		repetir();
    }
    
    static ArrayList<Flag> newFlags = new ArrayList<>();
    
    protected static void menuNuevoChiste(){
        try {
        	NewJoke newJoke = new NewJoke();
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
        	newJoke.setLang(new_lang);
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
	        flags.forEach(e -> {
		        System.out.println(String.format(Literals.new_flag_question,e));
		        System.out.println(Literals.menu_yes_no);
		        if (ValidateUtils.checkTrueFalse(teclado)) {newFlags.add(new Flag(e));}
	        });
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
        	newFlags.clear();
        }catch (Exception e) {
        	System.out.println(Literals.error_no_num);
        }
    }
        
    protected static void anyadirChisteStatement(){
    	menuNuevoChiste();
    }
    protected static void anyadirChistePreparedStatement(){}
    protected static void buscarPorTexto(){}
    protected static void chistesSinFlags(){}
}
