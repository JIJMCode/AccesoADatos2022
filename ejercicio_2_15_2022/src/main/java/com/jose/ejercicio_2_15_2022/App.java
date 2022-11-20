package com.jose.ejercicio_2_15_2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.jose.ejercicio_2_15_2022_Utilidades.*;
import com.jose.ejercicio_2_15_2022_Entidades.*;

/**
 * @author Jose Ignacio Jimenez Mayor
 * ACCESO A DATOS 2022
 */
public class App 
{
    static Scanner teclado = new Scanner(System.in);
	static Connection con;
	static Statement st;
	static PreparedStatement ps;
	static ResultSet rs;	
	static CallableStatement cs = null;
	static String url = Literals.url;
    static String usuario = Literals.usuario;
    static String password = Literals.password;
	static String equipoLocal = "";
	static String equipoVisitante = "";
	static String latitud;
	static String longitud;
    static String insertCategorias = Literals.scriptInsertCategory;
    static String insertFlags = Literals.scriptInsertFlag;
    static String insertTypes = Literals.scriptInsertType;
    static String insertLanguages = Literals.scriptInsertLanguage;
    static String consultaJokeSql;
	static int opcion;
    static int jokes_count;
    static int menu_count;
    static List<String> categorias = new ArrayList<>();
    static List<String> flags = new ArrayList<>();
    static List<String> types = new ArrayList<>();
    static List<Language> idiomas = new ArrayList<>();
    static List<Joke> chistes = new ArrayList<>();
    static JokesInfo rootCategorias;
    static RootLanguages languages;
    static ArrayList<Flag> newFlags = new ArrayList<>();
    static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    
	public static void main( String[] args ) throws Exception
    {
		//Obtener datos
		rootCategorias = JsonUtils.devolverObjetoGsonGenerico(Literals.url_get_categories, RootInfo.class).getJokesInfo();
		languages = JsonUtils.devolverObjetoGsonGenerico(Literals.url_get_languages, RootLanguages.class);
		rellenarListas();
		mostrarMenu();
    }
	
	/*
	 * Método que rellen las listas cuando se obtiene la información de la API para
	 * ser utilizadas por todos los métodos. 
	 */
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
			try {
				buscarPorTexto();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    		break;
	    	case 5:
			try {
				chistesSinFlags();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    		break;
	    	default:
	            System.out.println(Literals.choose_option);
	            mostrarMenu();
        }
    }
    
	/**
	 * Método ofrece la posibilidad de volver al menú principal o salir de la aplicación,
	 * tras cada consulta
	 * @throws IOException 
	 */
    private static void repetir() throws IOException {
    	System.out.println();
		System.out.println(Literals.repeat_title);
		//Scanner teclado = new Scanner(System.in);
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
			br.close();
			System.out.println(Literals.app_closed);
			System.exit(0);
		}
	}
  
	/**
	 * Método que vacía la base de datos y vuelve a cargar todos los datos desde la api.
	 */
	protected static void cargaBdd() {
    	try {
		//Insertar categorías
    		categorias.forEach(e-> {insertCategorias += (String.format(Literals.scriptCategory, e) + ", ");});
			int numCategorias = utilsPostgre.cargarBdd((insertCategorias.substring(0, insertCategorias.length() - 2)) + ";");
			System.out.println(Literals.addedCat + numCategorias);
		//Insertar flags
    		flags.forEach(e-> {insertFlags += (String.format(Literals.scriptFlag, e) + ", ");});
			int numFlags = utilsPostgre.cargarBdd((insertFlags.substring(0, insertFlags.length() - 2)) + ";");
			System.out.println(Literals.addedFlags + numFlags);
		//Insertar types
    		types.forEach(e-> {insertTypes += (String.format(Literals.scriptType, e) + ", ");});
			int numTypes = utilsPostgre.cargarBdd((insertTypes.substring(0, insertTypes.length() - 2)) + ";");
			System.out.println(Literals.addedTypes + numTypes);
		//Insertar idiomas    	
	    	idiomas.forEach(e-> {
	    		insertLanguages += (String.format(Literals.scriptLanguage, e.getCode(), e.getName()) + ", ");
	    		e.setCount(rootCategorias.getSafeJokes().stream().filter(x->x.getLang().equals(e.getCode())).findFirst().get().getCount());});
	    	int numLanguages = utilsPostgre.cargarBdd((insertLanguages.substring(0, insertLanguages.length() - 2)) + ";");
			System.out.println(Literals.addedLang + numLanguages);
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
			System.out.println(Literals.addedJoke + jokes_count);
			System.out.println(Literals.bdd_full);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    }
     
	/**
	 * MENU  - Método que vacía la base de datos y vuelve a cargar todos los datos desde la api.
	 * @throws IOException 
	 */
    protected static void resetearBdd() throws IOException{
    	//utilsPostgre.vaciarBdd();
		//cargaBdd();
		repetir();
    }

    /*
     * MENU 2 - Añadir chiste Statement
     * 	 * @throws IOException 
     */ 
    protected static void anyadirChisteStatement() throws IOException{
    	menuNuevoChiste();
    	repetir();
    }
    
	/*
	 * MENU 2 - Solictar datos usuario
	 */    
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
        	int new_category = ValidateUtils.isNum(teclado, categorias.size());
        	newJoke.setCategory(new_category);
    	//solicitar idioma
        	menu_count = 0;
	        System.out.println(Literals.new_joke_language);
	        idiomas.forEach(e-> {
	        	menu_count++;
	        	System.out.println(menu_count + "-. " + e.getName());
	        	});
        	int new_lang = ValidateUtils.isNum(teclado, idiomas.size());
        	newJoke.setLang(new_lang);
        //solicitar tipo
        	menu_count = 0;
	        System.out.println(Literals.new_joke_type);
	        types.forEach(e-> {
	        	menu_count++;
	        	System.out.println(menu_count + "-. " + e);
	        	});
        	int new_type = ValidateUtils.isNum(teclado, types.size());
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
                	chiste = br.readLine();
                	newJoke.setJoke(chiste);
    			} else {
        	        System.out.println(Literals.new_joke_setup);
        	        setup = br.readLine();
        	        newJoke.setSetup(setup);
        	        System.out.println(Literals.new_joke_delivery);
        	        delivery = br.readLine();
        	        newJoke.setDelivery(delivery);
    			}
			} while (new_type < 1 || new_type > 2);	
        //almacenar chiste
        	utilsPostgre.guardarChiste(newJoke);
        	System.out.println(Literals.jokeSt);
        	newFlags.clear();
        }catch (Exception e) {
        	System.out.println(Literals.error_no_num);
        }
    }
      
    /*
     * MENU 3 - Añadir chiste PreparedStatement
     */
    protected static void anyadirChistePreparedStatement() throws IOException{
        try {
    		con = null;
    		try {
    			con = DriverManager.getConnection(url, usuario, password);
    		} catch (SQLException e) {
    			e.printStackTrace();
    		} 
    		
        	PreparedStatement pstm = con.prepareStatement(Literals.scriptInsertNewJokePs);
	    //solicitar categoría
        	menu_count = 0;
	    	System.out.println(Literals.new_joke); 
	        System.out.println(Literals.new_joke_category);
	        categorias.forEach(e-> {
	        	menu_count++;
	        	System.out.println(menu_count + "-. " + e);
	        	});
	        int new_category = ValidateUtils.isNum(teclado, categorias.size());     
        	pstm.setInt(1, new_category);
    	//solicitar idioma
        	menu_count = 0;
	        System.out.println(Literals.new_joke_language);
	        idiomas.forEach(e-> {
	        	menu_count++;
	        	System.out.println(menu_count + "-. " + e.getName());
	        	});
	        int new_lang =  ValidateUtils.isNum(teclado, idiomas.size());
        	pstm.setInt(6, new_lang);
        //solicitar tipo
        	menu_count = 0;
	        System.out.println(Literals.new_joke_category);
	        types.forEach(e-> {
	        	menu_count++;
	        	System.out.println(menu_count + "-. " + e);
	        	});
	        int new_type = ValidateUtils.isNum(teclado, types.size());   
        	pstm.setInt(2, new_type);
        //solicitar flags     
	        flags.forEach(e -> {
		        System.out.println(String.format(Literals.new_flag_question,e));
		        System.out.println(Literals.menu_yes_no);
		        if (ValidateUtils.checkTrueFalse(teclado)) {newFlags.add(new Flag(e));}
	        });
        	
        //solicitar chiste
        	menu_count = 0;
        	String chiste;
        	String setup;
        	String delivery;
        	if (new_type == 1) {
    	        System.out.println(Literals.new_joke_joke);
            	chiste = br.readLine();
            	pstm.setString(3, chiste);
            	pstm.setString(4, null);
            	pstm.setString(5, null);
			} else {
            	pstm.setString(3, null);
    	        System.out.println(Literals.new_joke_setup);
    	        setup = br.readLine();
            	pstm.setString(4, setup);
    	        System.out.println(Literals.new_joke_delivery);
    	        delivery = br.readLine();
            	pstm.setString(5, delivery);
			}	
        //almacenar chiste
        	int i = pstm.executeUpdate();
        	if(i>0) {
        		PreparedStatement pstmFlags = con.prepareStatement(Literals.scriptInsertFlags);
            	if (newFlags.size()>0) {
	            	newFlags.forEach(e-> {
	            		try {
							pstmFlags.setString(1, e.getName());
							int j = pstmFlags.executeUpdate();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	            	});
	            	
				}
        		System.out.println(Literals.jokePs);
        	}
        	else
        		System.out.println(Literals.jokeNotAdded);        		

        	newFlags.clear();
        }catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        repetir();
    }
    
    /*
     * MENU 4 - Búsqueda de chistes que contengan la cadena introducida por el usuario (CallableStatement)
     */
    protected static void buscarPorTexto() throws IOException, SQLException{
        System.out.println(Literals.search_joke_by_text);
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String  texto = br.readLine();
		JdbcUtils.conexion(url, usuario, password);
		ResultSet rs = JdbcUtils.ejecutarCallableStatement(Literals.func_jokesContainss,texto);
		if(rs.next())
			System.out.println(String.format(Literals.jokeContains, rs.getInt(1), texto));
		//br.close();
		JdbcUtils.desconexion();
		repetir();
    }
    
    /*
     * MENU 5 - Búsqueda de chistes que no tengan flags activos (CallableStatement)
     */
    protected static void chistesSinFlags() throws IOException, SQLException{
		JdbcUtils.conexion(url, usuario, password);
		ResultSet rs = JdbcUtils.ejecutarCallableStatement(Literals.func_jokesNoFlags);
		rs.next();
		System.out.println(String.format(Literals.jokesNoFlags, rs.getInt(1)));
		JdbcUtils.desconexion();
    	repetir();                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
    }
}
