package com.jose.ejercicio_4_06_2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.jose.ejercicio_4_06_2022.Entidades.Categories;
import com.jose.ejercicio_4_06_2022.Entidades.Flags;
import com.jose.ejercicio_4_06_2022.Entidades.Jokes;
import com.jose.ejercicio_4_06_2022.Entidades.Language;
import com.jose.ejercicio_4_06_2022.Entidades.Types;
import com.jose.ejercicio_4_06_2022.Utilidades.HibernateUtils;
import com.jose.ejercicio_4_06_2022.Utilidades.Literals;
import com.jose.ejercicio_4_06_2022.Utilidades.ValidateUtils;

public class App 
{
    static Scanner teclado = new Scanner(System.in);
    static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static int opcion;
    static int menu_count;
    static List<Categories> categorias = new ArrayList<>();
    static List<Flags> flags = new ArrayList<>();
    static List<Types> types = new ArrayList<>();
    static List<Language> idiomas = new ArrayList<>();
    static Jokes newJoke;
    static List<Categories> catList;
    static String result;
    
	public static void main( String[] args )
    {
    	java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);
    	//probarConexion();
    	//listarCategorias();
    	try {
			mostrarMenuPrincipal();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	/**
	 * Método que muestra el menú principal
	 * @throws IOException 
	 */
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
	    		mostrarSubmenu1("categoria");
	    		break;
	    	case 3:
	    		mostrarSubmenu1("lenguaje");
	    		break;
	    	case 4:
	    		mostrarSubmenu1("flag");
	    		break;
	    	case 0:
	    		System.out.println(Literals.app_closed);
	    		System.exit(0);
	    		break;
	    	default:
	            System.out.println(Literals.choose_option);
	            mostrarMenuPrincipal();
        }
    }
    
	/**
	 * Método que muestra el submenú en función de la opción seleccionada
	 * en el menú principal
	 * @throws IOException 
	 */
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
	        	if (tipo.equals("categoria"))
	        		consultasCategorias();
	        	if (tipo.equals("lenguaje"))
	        		consultasLenguajes();
	        	if (tipo.equals("flag"))
	        		consultasFlags();
	    		break;
	    	case 2:
	        	if (tipo.equals("joke"))
	        		insertarJoke();
	        	if (tipo.equals("categoria"))
	        		insertarCategoria();
	        	if (tipo.equals("lenguaje"))
	        		insertarLenguaje();
	        	if (tipo.equals("flag"))
	        		insertarFlag();
	    		break;
	    	case 3:
	        	if (tipo.equals("joke"))
	        		modificarJoke();
	        	if (tipo.equals("categoria"))
	        		modificarcategoria();
	        	if (tipo.equals("lenguaje"))
	        		modificarLenguaje();
	        	if (tipo.equals("flag"))
	        		modificarFlag();
	    		break;
	    	case 4:
	        	if (tipo.equals("joke"))
	        		borrarJoke();
	        	if (tipo.equals("categoria"))
	        		borrarCategories();
	        	if (tipo.equals("lenguaje"))
	        		borrarLanguage();
	        	if (tipo.equals("flag"))
	        		borrarFlags();
	    		break;
	    	case 0:
	    		mostrarMenuPrincipal();
	    		break;
	    	default:
	    		volver(tipo);
        }
    }
    
	/**
	 * Método que vuelve al submenú del tipo tratado
	 */
    private static void volver(String tipo) throws IOException {
        System.out.println(Literals.choose_option);
        mostrarSubmenu1(tipo);
    }
    
	/**
	 * Método que rellena las listas de flags, idiomjas, categorias y tipos
	 * en caso de que estén vacías
	 */
    @SuppressWarnings("unchecked")
	private static void cargarOpciones() {
        try {
        	HibernateUtils.abrirConexion();
        	if (categorias.size()==0)	
        		categorias = (List<Categories>) HibernateUtils.devolverListaObjetos("Categories");
        	
        	if (flags.size()==0)
        		flags = (List<Flags>) HibernateUtils.devolverListaObjetos("Flags");
        	
        	if (types.size()==0)
        		types = (List<Types>) HibernateUtils.devolverListaObjetos("Types");
        	
        	if (idiomas.size()==0)
        		idiomas = (List<Language>)HibernateUtils.allLanguagesNamedQuery();

        	HibernateUtils.cerrarConexion();
		} catch (Exception e) {
			System.out.println("No se han podido cargar las listas.");
			HibernateUtils.cerrarConexion();
		}
    }
       
	/**
	 * Método que pide datos usuario para intertar o modificar chiste
	 */
    private static Jokes pedirDatosJoke(Jokes oldJoke) {
    	boolean modify = true;
    	try {
    		newJoke = new Jokes();
        	//solicitar categoria
    		if (oldJoke != null) {
    			newJoke = oldJoke;
    			System.out.println("categoria actual: " + oldJoke.getCategories().getCategory());
    			System.out.println("¿Quiere modificar la categoria?");
    			modify = ValidateUtils.checkTrueFalse(teclado);
			}
    		
    		if (modify) {
	         	menu_count = 0;
    	        System.out.println(Literals.new_joke_category);
    	        categorias.forEach(e-> {
    	        	menu_count++;
    	        	System.out.println(menu_count + "-. " + e.getCategory());
    	        	});
	         	int new_category = ValidateUtils.isNum(teclado, categorias.size());
	         	newJoke.setCategories(categorias.get(new_category-1));
			} else {
				newJoke.setCategories(oldJoke.getCategories());
			}
     	//Solicitar idioma
    		modify = true;
    		if (oldJoke != null) {
    			System.out.println("Idioma actual: " + oldJoke.getLanguage().getLanguage());
    			System.out.println("¿Quiere modificar el idioma?");
    			modify = ValidateUtils.checkTrueFalse(teclado);
			}
    		
    		if (modify) {
	         	menu_count = 0;
		        System.out.println(Literals.new_joke_language);
		        idiomas.forEach(e-> {
		        	menu_count++;
		        	System.out.println(menu_count + "-. " + e.getLanguage());
		        });
	         	int new_lang = ValidateUtils.isNum(teclado, idiomas.size());
	         	newJoke.setLanguage(idiomas.get(new_lang-1));
    		} else {
				newJoke.setLanguage(oldJoke.getLanguage());
			}
         //solicitar flags     
	        flags.forEach(e -> {
		        System.out.println(String.format(Literals.new_flag_question,e));
		        //System.out.println(Literals.menu_yes_no);
		        if (ValidateUtils.checkTrueFalse(teclado)) {newJoke.getFlagses().add(e);}
	        });
         //Solicitar tipo
    		modify = true;
    		int new_type = 0;
    		if (oldJoke != null) {
    			System.out.println("Tipo actual: " + oldJoke.getTypes().getType());
    			System.out.println("¿Quiere modificar el tipo?");
    			modify = ValidateUtils.checkTrueFalse(teclado);
			}
    		
    		if (modify) {
	         	menu_count = 0;
		        System.out.println(Literals.new_joke_type);
		        types.forEach(e-> {
		        	menu_count++;
		        	System.out.println(menu_count + "-. " + e.getType());
		        });
	         	do {
	         		new_type = ValidateUtils.isNum(teclado, types.size());
	         	} while (new_type < 1 || new_type > 2);	
		        
	         	newJoke.setTypes(types.get(new_type-1));
    		} else {
    			newJoke.setTypes(oldJoke.getTypes());
    		}
         	
         //solicitar chiste
    		modify = true;
    		menu_count = 0;
         	String chiste = "";
         	String setup = "";
         	String delivery = "";
         	
    		if (oldJoke != null) {
    			System.out.println("Chiste actual: " );
    			String part1 = oldJoke.getText1() != null ? oldJoke.getText1() : "";
    			String part2 = oldJoke.getText2() != null ? oldJoke.getText2() : "";
    			if(!part1.equals(""))
    				System.out.println(part1);
    			if(!part2.equals(""))
    				System.out.println(part2);
    			if(part1.equals("") && part2.equals(""))
    				System.out.println("chiste vacío");
    			System.out.println("¿Quiere modificar el chiste?");
    			modify = ValidateUtils.checkTrueFalse(teclado);
			}
    		
    		if (modify) {
             	if (new_type == 1) {
         	        System.out.println(Literals.new_joke_joke);
                 	chiste = br.readLine();
                 	newJoke.setText1(chiste);
     			} else {
         	        System.out.println(Literals.new_joke_setup);
         	        setup = br.readLine();
         	        newJoke.setText1(setup);
         	        System.out.println(Literals.new_joke_delivery);
         	        delivery = br.readLine();
         	        newJoke.setText2(delivery);
     			}
    		} else {
             	if (new_type == 1) {
        			newJoke.setText1(chiste);
     			} else {
     				newJoke.setText1(setup);
         	        newJoke.setText2(delivery);
     			}
    		}
    		return newJoke;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
    }
     
	/**
	 * Método ofrece la posibilidad de buscar jokes sin flags o
	 * jokes que contengan un texto determinado por el usuario
	 * @throws IOException 
	 */
    @SuppressWarnings({ "unchecked", "deprecation" }) 
    private static void consultasJoke() throws IOException {
    	System.out.println( Literals.tipos_consulta);
    	System.out.println( Literals.joke_by_text);
        System.out.println( Literals.joke_without_flags);
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
            HibernateUtils.cerrarConexion();
            volver("joke");
        }catch (Exception e) {
        	System.out.println(Literals.error_no_num);
        	System.out.println(e.getMessage());
        	HibernateUtils.cerrarConexion();
        	volver("joke");
        }               
    }
    
	/**
	 * Método ofrece la posibilidad de insertar un nuevo chiste
	 */
    private static void insertarJoke() {
    	 try {
    		cargarOpciones();
    		Jokes newJoke = pedirDatosJoke(null);
         //almacenar chiste
			if(HibernateUtils.save(newJoke))
				System.out.println(Literals.jokeSt);
			else
				System.out.println(Literals.jokeNotAdded);
	
			HibernateUtils.cerrarConexion();
			volver("joke");
         }catch (Exception e) {
         	System.out.println(e.getMessage());
         }  	
	}
  
	/**
	 * Método ofrece la posibilidad de modificar un chiste existente
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	private static void modificarJoke() {
    	Transaction transaction = null;
    	try {
    		HibernateUtils.abrirConexion();
    		cargarOpciones();
    		List<Jokes> chistes = (List<Jokes>) HibernateUtils.devolverListaObjetos("Jokes");
    		// Pedir al usuario id del elemento a actualizar
    		System.out.println(Literals.jokesList);
    		chistes.stream()
    		.sorted(Comparator.comparingDouble(Jokes::getId))
    		.forEach(e->{System.out.println(e.toString());});
    		System.out.println(Literals.selectJoke);
    		int idToUpdate = teclado.nextInt();
    		// Buscar el chiste seleccionado
			Query<Categories> consulta = HibernateUtils.session.createQuery("from Jokes where id=" + idToUpdate);
			List<?> lista = consulta.list();
			if (lista.size()>0) {			
				transaction = HibernateUtils.session.beginTransaction();
	    		// Seleccionar el objeto a modificar
				Jokes joke = (Jokes) lista.get(0);
	    		// Modificación objeto
				joke = pedirDatosJoke(joke);
	    		// Actualizar el objeto
	    		HibernateUtils.session.update(joke);
	    		// Confirmación del cambio en la base de datos
	    		transaction.commit();
				System.out.println(Literals.itemUpdated);
			} else {
				System.out.println(Literals.wrongId);
			}
			HibernateUtils.cerrarConexion();
			mostrarSubmenu1("joke");   		
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}	
	}
    
	/**
	 * Método que que permite eliminar un chiste
	 */
    @SuppressWarnings({ "unchecked", "deprecation" })
	private static void borrarJoke() throws IOException {
    	Transaction transaction = null; 	
    	try {
        	HibernateUtils.abrirConexion();
    		List<Jokes> chistes = (List<Jokes>) HibernateUtils.devolverListaObjetos("Jokes");
    		// Pedir al usuario id del elemento a actualizar
    		System.out.println(Literals.jokesList);
    		chistes.stream()
    			.sorted(Comparator.comparingDouble(Jokes::getId))
    			.forEach(e->{System.out.println(e.toString());});
    		System.out.println(Literals.selectJoke);
    		int idToUpdate = teclado.nextInt();   		
    		Query<Jokes> consulta = HibernateUtils.session.createQuery("from Jokes where id=" + idToUpdate); // Obtiene el dato
    		List<Jokes> resultados = consulta.list();    		
    		if (resultados.size()>0) {
    			transaction = HibernateUtils.session.beginTransaction();
    			Jokes joke = resultados.get(0);
    			
    			if (joke.getFlagses().size()>0) {
    				System.out.println(String.format(Literals.deleteFlagsConfirm, joke.getFlagses().size()));
    				boolean borrar = ValidateUtils.checkTrueFalse(teclado);
    				if (borrar) {
    					joke.getFlagses().clear();
    					HibernateUtils.session.update(joke);
    					HibernateUtils.session.delete(joke);
    					System.out.println(Literals.itemDeleted);
    				} else {
    					transaction.rollback(); // Deshago los cambios en la base de datos
    					System.out.println(Literals.deleteCanceled);
    				}
    			} else {
					HibernateUtils.session.delete(joke);
    			}
				transaction.commit(); // Confirmo el cambio en la base de datos
				volver("Joke");
    		} else {
    			System.out.println(Literals.wrongId);
        		volver("Joke");
    		}
    		HibernateUtils.cerrarConexion();
		} catch (Exception e2) {
			transaction.rollback(); // Deshago los cambios en la base de datos
			HibernateUtils.cerrarConexion();
			System.out.println(e2.getMessage());
			System.out.println(Literals.deleteError);
		}		
	}
     
	/**
	 * Método ofrece la posibilidad de buscar todas las categorias o
	 * caegorías que contengan un texto determinado por el usuario
	 * @throws IOException 
	 */
    private static void consultasCategorias() throws IOException {
    	System.out.println( Literals.tipos_consulta);
    	System.out.println( Literals.category_by_text);
        System.out.println( Literals.show_all);
        System.out.println( Literals.menu_exit );
        
        try {
        	opcion = Integer.parseInt(teclado.next());       	
            HibernateUtils.abrirConexion();
            switch (opcion) {
    	        case 1:
    	        	System.out.println( Literals.joke_search_text);
    	        	String text = br.readLine().toLowerCase();
    	        	categorias = HibernateUtils.searchCategoriesNativeQuery(text);
    	        	System.out.println( Literals.categories_result + text + ":\n");
    	        	categorias.stream().forEach(e->{System.out.println(e.toString());});
    	        	categorias.clear();
    	    		break;
    	    	case 2:
    	    		categorias = (List<Categories>) HibernateUtils.searchAllCategoriesNativeQuery();
    	    		catList = new ArrayList<Categories>();
    	    		catList.add(new Categories());
    	    		categorias.stream()
		            	.filter(e->!e.getJokeses().isEmpty())
			            .forEach(e->{
			            	if (e.getJokeses().size()>catList.get(0).getJokeses().size()) {
			            		catList.clear();
			            		catList.add(e);
							} else if (e.getJokeses().size()==catList.get(0).getJokeses().size()) {
								catList.add(e);
							}
			            });
    	    		if (catList.size()==1) {
    	    			System.out.println("La categoría " + Literals.one_category_max + catList.get(0).getCategory() +
    	    								Literals.one_repetition + catList.get(0).getJokeses().size() + " chistes.");
					} else if (catList.size()>1) {
						result = "Las categorías " + Literals.various_categories_max;
						catList.forEach(e-> {
							result += e.getCategory() + ",";
						});
						result += catList.get(0).getCategory() +",\naparecen en un total de " + catList.get(0).getJokeses().size() + " chistes.";
					} else {
						System.out.println(Literals.no_used_categories);
					}
    	    		
    	    		break;
    	    	case 0:
    	    		mostrarSubmenu1("categoria");
    	    		break;
    	    	default:
    	            System.out.println(Literals.choose_option);
    	            consultasCategorias();
            }
            HibernateUtils.cerrarConexion();
            volver("categoria");
        }catch (Exception e) {
        	System.out.println(e.getMessage());
        	HibernateUtils.cerrarConexion();
        	volver("categoria");
        }
    }
    
	/**
	 * Método ofrece la posibilidad de insertar un nuevo chiste
	 * @throws IOException 
	 */
    private static void insertarCategoria() throws IOException {
    	try {
			HibernateUtils.abrirConexion();
			Categories newCategory = new Categories();
		 	menu_count = 0;
			System.out.println(Literals.new_category); 
		 	String new_category = br.readLine();
		 	newCategory.setCategory(new_category);
		//almacenar categoria
			if(HibernateUtils.save(newCategory))
				System.out.println(Literals.addedCat);
			else
				System.out.println(Literals.categoryNotAdded);
			
			HibernateUtils.cerrarConexion();
         }catch (Exception e) {
        	HibernateUtils.cerrarConexion();
         	System.out.println(e.getMessage());
         }  
    	volver("categoria");
	}
   
	/**
	 * Método que que permite modificar la descripción de una categoria
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	private static void modificarcategoria() {
    	Transaction transaction = null;
    	
    	try {
    		HibernateUtils.abrirConexion();

    		if (categorias.size()==0) {
    			categorias = (List<Categories>) HibernateUtils.devolverListaObjetos("Categories");
    		}
    		System.out.println(Literals.selectFlag);
    		categorias.stream()
    		.sorted(Comparator.comparingDouble(Categories::getId))
    		.forEach(e->{System.out.println(e.toString());});
    		// Pedir al usuario id del elemento a actualizar
    		System.out.println(Literals.selectCategories);
    		int idToUpdate = teclado.nextInt();
			Query<Categories> consulta = HibernateUtils.session.createQuery("from Categories where id=" + idToUpdate);
			List<?> lista = consulta.list();
			if (lista.size()>0) {			
				// Pedir al usuario nueva descricpión
				System.out.println(Literals.newDescription);
				String newDescription = teclado.next();
				transaction = HibernateUtils.session.beginTransaction();
	    		// Seleccionar el objeto a modificar
				Categories cat = (Categories) lista.get(0);
	    		// Modificación objeto
				cat.setCategory(newDescription);
	    		 // Actualizar el objeto
	    		HibernateUtils.session.update(cat);
	    		// Confirmación del cambio en la base de datos
	    		transaction.commit();
				System.out.println(Literals.itemUpdated);
			} else {
				System.out.println(Literals.wrongId);
			}
			HibernateUtils.cerrarConexion();
			mostrarSubmenu1("categoria");    		
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
	}
    
	/**
	 * Método que que permite eliminar una categoria
	 */
    @SuppressWarnings({ "unchecked", "deprecation" })
    private static void borrarCategories() throws IOException {
    	Transaction transaction = null;
    	String clase = "Categories";
    	String tipo = "categoria";
    	try {
    		HibernateUtils.abrirConexion();
    		List<Categories> registros = (List<Categories>) HibernateUtils.devolverListaObjetos(clase);
    		// Pedir al usuario id del elemento a actualizar
    		System.out.println(Literals.categoriesList);
    		registros.stream()
    		.forEach(e->{System.out.println(e.toString());});
    		System.out.println(String.format(Literals.selectGeneric,tipo));
    		int idToUpdate = teclado.nextInt();  		
    		Query<Categories> consulta = HibernateUtils.session.createQuery("from " + clase + " where id=" + idToUpdate); // Obtiene el dato
    		List<Categories> resultados = consulta.list();
    		if (resultados.size()>0) {
    			transaction = HibernateUtils.session.beginTransaction();
    			Categories element = resultados.get(0);
    			
    			if (element.getJokeses().size()>0) {
    				List<Jokes> chistes = new ArrayList<Jokes>(element.getJokeses());
    				System.out.println(String.format(Literals.deleteJokesConfirm, element.getJokeses().size(), tipo, tipo));
    				boolean borrar = ValidateUtils.checkTrueFalse(teclado);
    				if (borrar) {
    					chistes.forEach(e -> {
    	        			e.setCategories(null);
    						HibernateUtils.session.update(e);
    					});
    					HibernateUtils.session.delete(element);
    					System.out.println(Literals.itemDeleted);
    				} else {
    					transaction.rollback(); // Deshago los cambios en la base de datos
    					System.out.println(Literals.deleteCanceled);
    				}
    			} else {
    				HibernateUtils.session.delete(element);
    			}
    			transaction.commit(); // Confirmo el cambio en la base de datos
    		} else {
    			System.out.println(Literals.wrongId);
    		}
    		HibernateUtils.cerrarConexion();
    		volver(tipo);
    	} catch (Exception e2) {
    		transaction.rollback(); // Deshago los cambios en la base de datos
    		HibernateUtils.cerrarConexion();
    		System.out.println(e2.getMessage());
    		System.out.println(Literals.deleteError);
    		volver(tipo);
    	}		
    }
    
	/**
	 * Método ofrece la posibilidad de buscar todos los idiomas o aquellos
	 * idomas que contengan un texto determinado por el usuario
	 * @throws IOException 
	 */
    private static void consultasLenguajes() throws IOException {
    	System.out.println( Literals.tipos_consulta);
    	System.out.println( Literals.laguage_by_text);
        System.out.println( Literals.laguage_without_jokes);
        System.out.println( Literals.menu_exit );
        
        try {
        	opcion = Integer.parseInt(teclado.next());       	
            HibernateUtils.abrirConexion();
            if (idiomas.size()==0)
            	idiomas = HibernateUtils.allLanguagesNamedQuery();
            switch (opcion) {
    	        case 1:
    	        	System.out.println( Literals.joke_search_text);
    	        	String text = br.readLine().toLowerCase();
    	        	idiomas.stream()
    	        		.filter(e->e.getLanguage().contains(text))
    	        		.forEach(e->{System.out.println(e.toString());});
    	        	idiomas.clear();
    	    		break;
    	    	case 2:
    	    		idiomas.stream()
		            	.filter(e->e.getJokeses().isEmpty())
			            .forEach(e->{System.out.println(e.toString());});
		            System.out.println("Total idiomas sin chistes = " + idiomas.size());
    	    		break;
    	    	case 0:
    	    		mostrarSubmenu1("lenguaje");
    	    		break;
    	    	default:
    	            System.out.println(Literals.choose_option);
    	            consultasLenguajes();
            }
            HibernateUtils.cerrarConexion();
            volver("lenguaje");
        }catch (Exception e) {
        	System.out.println(Literals.error_no_num);
        	System.out.println(e.getMessage());
        	HibernateUtils.cerrarConexion();
        	volver("lenguaje");
        }
    }
    
	/**
	 * Método ofrece la posibilidad de insertar un nuevo chiste
	 * @throws IOException 
	 */
    private static void insertarLenguaje() throws IOException {
    	try {
			HibernateUtils.abrirConexion();
			Language newLanguage = new Language();
		 	menu_count = 0;
			System.out.println(Literals.new_language); 
		 	String new_language = br.readLine();
		 	newLanguage.setLanguage(new_language);
			System.out.println(Literals.new_language_code); 
		 	String new_code_language = br.readLine();
		 	newLanguage.setCode(new_code_language);
		//almacenar idioma
			if(HibernateUtils.save(newLanguage))
				System.out.println(Literals.addedLang);
			else
				System.out.println(Literals.languageNotAdded);
			
			HibernateUtils.cerrarConexion();
         }catch (Exception e) {
 			HibernateUtils.cerrarConexion();
         	System.out.println(e.getMessage());
         }
    	volver("lenguaje");
	}

	/**
	 * Método que que permite modificar la descripción de un idioma
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	private static void modificarLenguaje() {
    	Transaction transaction = null;
    	
    	try {
    		HibernateUtils.abrirConexion();

    		if (idiomas.size()==0) {
    			idiomas = (List<Language>) HibernateUtils.devolverListaObjetos("Language");
    		}
    		System.out.println(Literals.selectLanguage);
    		idiomas.stream()
    		.sorted(Comparator.comparingDouble(Language::getId))
    		.forEach(e->{System.out.println(e.toString());});
    		// Pedir al usuario id del elemento a actualizar
    		System.out.println(Literals.selectFlag);
    		int idToUpdate = teclado.nextInt();
			Query<Language> consulta = HibernateUtils.session.createQuery("from Language where id=" + idToUpdate);
			List<?> lista = consulta.list();
			if (lista.size()>0) {			
				// Pedir al usuario nueva descricpión
				System.out.println(Literals.newDescription);
				String newDescription = teclado.next();
				transaction = HibernateUtils.session.beginTransaction();
	    		// Seleccionar el objeto a modificar
				Language lang = (Language) lista.get(0);
	    		// Modificación objeto
				lang.setLanguage(newDescription);
	    		 // Actualizar el objeto
	    		HibernateUtils.session.update(lang);
	    		// Confirmación del cambio en la base de datos
	    		transaction.commit();
				System.out.println(Literals.itemUpdated);
			} else {
				System.out.println(Literals.wrongId);
			}
			HibernateUtils.cerrarConexion();
			mostrarSubmenu1("lenguaje");
    		
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
	}

	/**
	 * Método que que permite eliminar un idioma
	 */
    @SuppressWarnings({ "unchecked", "deprecation" })
    private static void borrarLanguage() throws IOException {
    	Transaction transaction = null;
    	String clase = "Language";
    	String tipo = "lenguaje";
    	try {
    		HibernateUtils.abrirConexion();
    		List<Language> registros = (List<Language>) HibernateUtils.devolverListaObjetos(clase);
    		// Pedir al usuario id del elemento a actualizar
    		System.out.println(Literals.languagesList);
    		registros.stream()
    		.forEach(e->{System.out.println(e.toString());});
    		System.out.println(String.format(Literals.selectGeneric,tipo));
    		int idToUpdate = teclado.nextInt();  		
    		Query<Language> consulta = HibernateUtils.session.createQuery("from " + clase + " where id=" + idToUpdate); // Obtiene el dato
    		List<Language> resultados = consulta.list();
    		if (resultados.size()>0) {
    			transaction = HibernateUtils.session.beginTransaction();
    			Language element = resultados.get(0);
    			List<Jokes> chistes = new ArrayList<Jokes>(element.getJokeses());
    			if (element.getJokeses().size()>0) {
    				System.out.println(String.format(Literals.deleteJokesConfirm, element.getJokeses().size(), tipo, tipo));
    				boolean borrar = ValidateUtils.checkTrueFalse(teclado);
    				if (borrar) {
    					chistes.forEach(e -> {
    	        			e.setLanguage(null);
    						HibernateUtils.session.update(e);
    					});
    					HibernateUtils.session.delete(element);
    					System.out.println(Literals.itemDeleted);
    				} else {
    					transaction.rollback(); // Deshago los cambios en la base de datos
    					System.out.println(Literals.deleteCanceled);
    				}
    			} else {
    				HibernateUtils.session.delete(element);
    			}
    			transaction.commit(); // Confirmo el cambio en la base de datos
    		} else {
    			System.out.println(Literals.wrongId);
    		}
    		HibernateUtils.cerrarConexion();
    		volver(tipo);
    	} catch (Exception e2) {
    		transaction.rollback(); // Deshago los cambios en la base de datos
    		HibernateUtils.cerrarConexion();
    		System.out.println(e2.getMessage());
    		System.out.println(Literals.deleteError);
    		volver(tipo);
    	}		
    }
	
	/**
	 * Método ofrece la posibilidad de buscar todas los flags o
	 * flags que contengan un texto determinado por el usuario
	 * @throws IOException 
	 */
    @SuppressWarnings({ "unchecked", "deprecation" })
	private static void consultasFlags() throws IOException {
    	System.out.println( Literals.tipos_consulta);
    	System.out.println( Literals.flag_by_text);
        System.out.println( Literals.flags_more_used);
        System.out.println( Literals.menu_exit );
        
        try {
        	opcion = Integer.parseInt(teclado.next());       	
            HibernateUtils.abrirConexion();
            List<Flags> flagsList;
            switch (opcion) {
    	        case 1:
    	        	System.out.println( Literals.joke_search_text);
    	        	String text = br.readLine().toLowerCase();
    	            Query<Flags> flagsTextquery = HibernateUtils.session
    	            	.createQuery("FROM Flags WHERE LOWER(flag) LIKE '%" + text + "%' OR LOWER(text2) LIKE '%" + text + "%'");
    	            flagsList = flagsTextquery.list();
    	            flagsList.stream().forEach(e->{System.out.println(e.toString());});
    	    		break;
    	    	case 2:
    	    		flags = (List<Flags>) HibernateUtils.devolverListaObjetos("Flags");
    	    		flagsList = new ArrayList<Flags>();
    	    		flagsList.add(new Flags());
    	    		flags.stream()
		            	.filter(e->!e.getJokeses().isEmpty())
			            .forEach(e->{
			            	if (e.getJokeses().size()>flagsList.get(0).getJokeses().size()) {
			            		flagsList.clear();
			            		flagsList.add(e);
							} else if (e.getJokeses().size()==flagsList.get(0).getJokeses().size()) {
								flagsList.add(e);
							}
			            });
    	    		if (flagsList.size()==1) {
    	    			System.out.println(Literals.one_flag_max + flagsList.get(0).getFlag() +
    	    					Literals.one_repetition + flagsList.get(0).getJokeses().size() + " chistes.");
					} else if (flagsList.size()>1) {
						result = Literals.various_flag_max;
						flagsList.forEach(e-> {
							result += e.getFlag() + ",";
						});
						result += flagsList.get(0).getFlag() + Literals.various_repetition + flagsList.get(0).getJokeses().size() + " chistes.";
					} else {
						System.out.println(Literals.no_used_flags);
					}
    	    		break;
    	    	case 0:
    	    		volver("flag");
    	    		break;
    	    	default:
    	            System.out.println(Literals.choose_option);
    	            consultasFlags();
            }
            HibernateUtils.cerrarConexion();
            volver("flag");
        }catch (Exception e) {
        	System.out.println(Literals.error_no_num);
        	System.out.println(e.getMessage());
        	HibernateUtils.cerrarConexion();
        	volver("flag");
        } 
    }
    
	/**
	 * Método ofrece la posibilidad de insertar un nuevo chiste
	 * @throws IOException 
	 */
    private static void insertarFlag() throws IOException {
    	try {
			HibernateUtils.abrirConexion();
			Flags newFlag = new Flags();
		 	menu_count = 0;
			System.out.println(Literals.new_category); 
		 	String new_flag = br.readLine();
		 	newFlag.setFlag(new_flag);
		//almacenar idioma
			if(HibernateUtils.save(newFlag))
				System.out.println(Literals.addedFlags);
			else
				System.out.println(Literals.flagNotAdded);
			
			HibernateUtils.cerrarConexion();

         }catch (Exception e) {
        	 HibernateUtils.cerrarConexion();
         	System.out.println(e.getMessage());
         }
    	volver("flag");
	}
    
	/**
	 * Método que que permite modificar la descripción de un flag
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	private static void modificarFlag() {
    	Transaction transaction = null;
    	try {
    		HibernateUtils.abrirConexion();

    		if (flags.size()==0) {
    			flags = (List<Flags>) HibernateUtils.devolverListaObjetos("Flags");
    		}
    		System.out.println(Literals.selectFlag);
    		flags.stream()
    		.sorted(Comparator.comparingDouble(Flags::getId))
    		.forEach(e->{System.out.println(e.toString());});
    		// Pedir al usuario id del elemento a actualizar
    		System.out.println(Literals.selectFlag);
    		int flagToUpdate = teclado.nextInt();
			Query<Flags> consulta = HibernateUtils.session.createQuery("from Flags where id=" + flagToUpdate);
			List<?> lista = consulta.list();
			if (lista.size()>0) {			
				// Pedir al usuario nueva descricpión
				System.out.println(Literals.newDescription);
				String newDescription = teclado.next();
				transaction = HibernateUtils.session.beginTransaction();
	    		// Seleccionar el objeto a modificar
				Flags flag = (Flags) lista.get(0);
	    		// Modificación objeto
				flag.setFlag(newDescription);
	    		 // Actualizar el objeto
	    		HibernateUtils.session.update(flag);
	    		// Confirmación del cambio en la base de datos
	    		transaction.commit();
				System.out.println(Literals.itemUpdated);
			} else {
				System.out.println(Literals.wrongId);
			}
			HibernateUtils.cerrarConexion();
			mostrarSubmenu1("flag");
    		
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
    }
    
	/**
	 * Método que que permite eliminar un flag
	 */
    @SuppressWarnings({ "unchecked", "deprecation" })
	private static void borrarFlags() throws IOException {
    	Transaction transaction = null;
    	String clase = "Flags";
    	String tipo = "flag";
    	try {
        	HibernateUtils.abrirConexion();
    		List<Flags> registros = (List<Flags>) HibernateUtils.devolverListaObjetos(clase);
    		// Pedir al usuario id del elemento a actualizar
    		System.out.println(Literals.flagsList);
    		registros.stream()
    			.forEach(e->{System.out.println(e.toString());});
    		System.out.println(String.format(Literals.selectGeneric,"Flags"));
    		int idToUpdate = teclado.nextInt();   		
    		Query<Flags> consulta = HibernateUtils.session.createQuery("from " + clase + " where id=" + idToUpdate); // Obtiene el dato
    		List<Flags> resultados = consulta.list();
    		Flags element = resultados.get(0);
    		if (resultados.size()>0) {
    			transaction = HibernateUtils.session.beginTransaction();
    			if (element.getJokeses().size()>0) {
    				System.out.println(String.format(Literals.deleteFlagsConfirm, element.getJokeses().size(), tipo, tipo));
    				boolean borrar = ValidateUtils.checkTrueFalse(teclado);
    				if (borrar) {
						element.getJokeses().clear();
						HibernateUtils.session.update(element);
						HibernateUtils.session.delete(element);
						System.out.println(Literals.itemDeleted);
    				} else {
					transaction.rollback(); // Deshago los cambios en la base de datos
					System.out.println(Literals.deleteCanceled);
    				}
				} else {
					transaction.rollback(); // Deshago los cambios en la base de datos
					System.out.println(Literals.deleteCanceled);
				}
			} else {
				HibernateUtils.session.delete(element);
			}
			transaction.commit(); // Confirmo el cambio en la base de datos
    		HibernateUtils.cerrarConexion();
			volver(tipo);
		} catch (Exception e2) {
			transaction.rollback(); // Deshago los cambios en la base de datos
			HibernateUtils.cerrarConexion();
			System.out.println(e2.getMessage());
			System.out.println(Literals.deleteError);
			volver(tipo);
		}		
	}
}
