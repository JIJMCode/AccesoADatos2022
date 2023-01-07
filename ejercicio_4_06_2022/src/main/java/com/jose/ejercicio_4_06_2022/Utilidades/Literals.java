package com.jose.ejercicio_4_06_2022.Utilidades;

public class Literals {
	public static String url = "jdbc:postgresql://localhost:5432/jokes";
    public static String usuario = "postgres";
    public static String password = "postgre";
	/*MENU*/
	public static String menu_title = "\nMenú (Selecione una opción)";
    public static String menu_1 = "1. Gestión de Jokes";
    public static String menu_2 = "2. Gestión de categories";
    public static String menu_3 = "3. Gestión de leguajes";
    public static String menu_4 = "4. Gestion de flags";
    public static String menu_exit = "0. Salir"; 
	public static String subMenu_title = "\nOpciones para ";
    public static String subMenu_1 = "1. Consultas";
    public static String subMenu_2 = "2. Insertar ";
    public static String subMenu_3 = "3. Modificar ";
    public static String subMenu_4 = "4. Borrar ";
    public static String menu_yes_no = "1-. Sí\n2-. No\n"; 
	public static String error_no_num = "\n***El valor introducido  no es un número";
	public static String choose_option = "\n***Debe seleccionar una de las opciones propuestas.";
	public static String repeat_title = "\n==============================================\n= Pulse C para volver al MENU o S para salir =\n==============================================\n";
	public static String continue_exit = "\nPulse C para continuar o S para salir\n";
	public static String app_closed = "\nAplicación cerrada. Hasta pronto!!!";
	public static String tipos_consulta = "Elegir tipo de consulta:";
	public static String joke_by_text = "1. Buscar Joke por texto";
	public static String joke_search_text = "Introducir texto a buscar: ";
	public static String joke_without_flags = "2. Buscar Joke sin flags";
	public static String category_by_text = "1. Buscar Categoría por texto";
	public static String show_all = "2. Mostrar todo";
	public static String categories_result = "Categorías que contienen ";
	public static String flag_by_text = "1. Buscar Flag por texto";
	public static String flags_without_jokes = "2. Buscar flags sin chistes";
	public static String laguage_by_text = "1. Buscar Idiomas por texto";
	public static String laguage_without_jokes = "2. Buscar Idiomas sin chistes";
	public static String new_joke = "Insertar chiste nuevo\n";
	public static String new_category = "Insertar nueva categoría: ";
	public static String new_joke_category = "Escriba el número de la categoría elegida:";	
	public static String new_joke_type = "Escriba el número del tipo elegido:";
	public static String new_joke_language = "Escriba el número del idioma elegido:";
	public static String new_joke_joke = "Escriba el chiste:";
	public static String new_joke_setup = "Escriba la primera parte del chiste:";
	public static String new_joke_delivery = "Escriba la segunda parte del chiste:";
	public static String search_joke_by_text = "Escriba el texto que debe contener el chiste:";
	public static String new_flag_question = "¿El chiste se puede considerar de temática %s?";
		

    public static String jokeSt = "Chiste añadido correctamente.";
    public static String addedCat = "Categoría añadida.";
    public static String addedLang = "Idioma añadido.";
    public static String addedTypes = "Tipo añadido.";
    public static String addedFlags = "Flag añadido.";
    public static String jokeNotAdded = "No se ha podido añadir el chiste.";
    public static String categoryNotAdded = "No se ha podido añadir la categoría.";
    public static String languageNotAdded = "No se ha podido añadir la categoría.";
    public static String flagNotAdded = "No se ha podido añadir el flag.";
    public static String scriptNewJokeId = "select * from public.jokes order by id desc limit 1";
    public static String scriptInsertJokesFlags = "insert into public.jokes_flags values (%d,(select id from public.flags where flag = '%s'));"; 
    public static String scriptInsertFlags = "insert into public.jokes_flags values ((select MAX(id) from jokes),(select id from public.flags where flag = ?));"; 
	public static String bdd_full = "\nBase de datos cargada.";
	public static String jokeContains = "\nHay %d chistes que contienen la cadena '%s'.";
	public static String jokesNoFlags = "\nHay %d chistes sin Flags.";
	public static String func_jokesContainss = "JokesContains(?::varchar)";
	public static String func_jokesNoFlags = "JokesNoFlags2()";

}
