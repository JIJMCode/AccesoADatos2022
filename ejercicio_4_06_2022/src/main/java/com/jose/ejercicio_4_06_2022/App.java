package com.jose.ejercicio_4_06_2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import org.hibernate.query.Query;

import com.jose.ejercicio_4_06_2022.Entidades.Categories;
import com.jose.ejercicio_4_06_2022.Entidades.Jokes;
import com.jose.ejercicio_4_06_2022.Utilidades.HibernateUtils;
import com.jose.ejercicio_4_06_2022.Utilidades.Literals;

public class App 
{
    static Scanner teclado = new Scanner(System.in);
    static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static int opcion;
    
	public static void main( String[] args )
    {
    	java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);
    	//probarConexion();
    	//listarCategorias();
    	try {
			mostrarMenuPrincipal();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private static void mostrarMenuPrincipal() throws IOException {
        System.out.println( Literals.menu_title );
        System.out.println( Literals.menu_1 );
        System.out.println( Literals.menu_2 );
        System.out.println( Literals.menu_3 );
        System.out.println( Literals.menu_4 );
        System.out.println( Literals.menu_exit ); 
        
        try {
        	opcion = Integer.parseInt(teclado.next());
        }catch (Exception e) {
        	System.out.println(Literals.error_no_num);
        }
                        
        switch (opcion) {
	        case 1:
	        	mostrarSubmenu1("joke");
	    		break;
	    	case 2:
	    		mostrarSubmenu1("categoría");
	    		break;
	    	case 3:
	    		mostrarSubmenu1("lenguaje");
	    		break;
	    	case 4:
	    		mostrarSubmenu1("flag");
	    		break;
	    	default:
	            System.out.println(Literals.choose_option);
	            mostrarMenuPrincipal();
        }
    }
    
    private static void mostrarSubmenu1(String tipo) throws IOException {
        System.out.println( Literals.subMenu_title + tipo);
        System.out.println( Literals.subMenu_1 + tipo);
        System.out.println( Literals.subMenu_2 + tipo);
        System.out.println( Literals.subMenu_3 + tipo);
        System.out.println( Literals.subMenu_4 + tipo);
        System.out.println( Literals.menu_exit );
        
        try {
        	opcion = Integer.parseInt(teclado.next());
        }catch (Exception e) {
        	System.out.println(Literals.error_no_num);
        }
                        
        switch (opcion) {
	        case 1:
	        	if (tipo.equals("joke"))
	        		consultasJoke();
	        	if (tipo.equals("categoría"))
	        		consultasCategorias();
	        	if (tipo.equals("lenguaje"))
	        		consultasLenguajes();
	        	if (tipo.equals("flag"))
	        		consultasFlags();
	    		break;
	    	case 2:
	    		//mostrarsubmenu2("joke");
	    		break;
	    	case 3:
	    		//mostrarsubmenu2("joke");
	    		break;
	    	case 4:
	    		//mostrarsubmenu2("joke");
	    		break;
	    	default:
	            System.out.println(Literals.choose_option);
	            mostrarSubmenu1(tipo);
        }
    }
    
    @SuppressWarnings({ "unchecked", "deprecation" }) 
    private static void consultasJoke() throws IOException {
    	System.out.println( Literals.tipos_consulta);
    	System.out.println( Literals.joke_by_text);
        System.out.println( Literals.joke_whitout_flags);
        System.out.println( Literals.menu_exit );
        
        try {
        	opcion = Integer.parseInt(teclado.next());       	
            HibernateUtils.abrirConexion();
            List<Jokes> jokesList;
            switch (opcion) {
    	        case 1:
    	        	System.out.println( Literals.joke_search_text);
    	        	String text = br.readLine().toLowerCase();
    	            Query<Jokes> jokesTextquery = HibernateUtils.session
    	            	.createQuery("FROM Jokes WHERE LOWER(text1) LIKE '%" + text + "%' OR LOWER(text2) LIKE '%" + text + "%'");
    	            jokesList = jokesTextquery.list();
    	            jokesList.stream().forEach(e->{System.out.println(e.toString());});
    	    		break;
    	    	case 2:
    	            Query<Jokes> jokesSinFlagsquery = HibernateUtils.session
	            	.createQuery("FROM Jokes");
		            jokesList = jokesSinFlagsquery.list();
		            jokesList.stream()
		            	.filter(e->!e.getFlagses().isEmpty())
			            .forEach(e->{System.out.println(e.toString());});
		            System.out.println("Total chistes sin flags = " + jokesList.size());
    	    		break;
    	    	case 0:
    	    		mostrarSubmenu1("joke");
    	    		break;
    	    	default:
    	            System.out.println(Literals.choose_option);
    	            consultasJoke();
            }
        }catch (Exception e) {
        	System.out.println(Literals.error_no_num);
        	System.out.println(e.getMessage());
        }
               
    }
    
    private static void consultasCategorias() throws IOException {
    	
    }
    
    private static void consultasLenguajes() throws IOException {
    	
    }
    
    private static void consultasFlags() throws IOException {
    	
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
				mostrarMenuPrincipal();
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

    
	public static void probarConexion() {
		if(HibernateUtils.abrirConexion()) {
			System.out.println("Conexión establecida correctamente");
			HibernateUtils.cerrarConexion();
		} else {
			System.out.println("Fallo al establecer la conexión");
		}
	}
	
	/**
	 * Ejemplo de como acceder a la lista de elementos de una clase
	 * En el ejemplo categories y recorrerla
	 */
	public static void listarCategorias() {
		HibernateUtils.abrirConexion();
		HibernateUtils.devolverListaObjetos(Categories.class)
			.forEach(c->System.out.println(c));
		HibernateUtils.cerrarConexion();
	}
}
