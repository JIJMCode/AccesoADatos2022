package com.jose.ejercicio_2_15_2022_Utilidades;

public class Literals {
	/*MENU*/
	public static String menu_title = "\nMenú (Selecione una opción)";
    public static String menu_1 = "1. Resetear base de datos";
    public static String menu_2 = "2. Añadir chistes (jokes) Statement.";
    public static String menu_3 = "3. Añadir chiste PreparedStatement.";
    public static String menu_4 = "4. Búsqueda de chistes por texto CallableStatement";
    public static String menu_5 = "5. Obtener chistes sin flags CallableStatement.";
    public static String menu_exit = "0. Salir"; 
    public static String menu_yes_no = "1-. Sí\n2-. No\n"; 
	public static String error_no_num = "\n***El valor introducido  no es un número";
	public static String choose_option = "\n***Debe seleccionar una de las opciones propuestas.";
	public static String repeat_title = "\n==============================================\n= Pulse C para volver al MENU o S para salir =\n==============================================\n";
	public static String continue_exit = "\nPulse C para continuar o S para salir\n";
	public static String app_closed = "\nAplicación cerrada. Hasta pronto!!!";
	public static String new_joke = "Insertar chiste nuevo\n";
	public static String new_joke_category = "Escriba el número de la categoría elegida:\n";	
	public static String new_joke_type = "Escriba el número del tipo elegido:\n";
	public static String new_joke_language = "Escriba el número del idioma elegido:\n";
	public static String new_joke_joke = "Escriba el chiste:\n";
	public static String new_joke_setup = "Escriba la primera parte del chiste:\n";
	public static String new_joke_delivery = "Escriba la segunda parte del chiste:\n";
	public static String new_flag_question = "¿El chiste se puede considerar de temática %s?\n";
	
	/*RESET BDD*/
	public static String vaciarBddSQL = "delete from public.jokes_flags;\n" + "delete from public.jokes;" + "delete from public.categories;\n" + "delete from public.types;\n" + "delete from public.language;\n" + "delete from public.flags;\n" + 
							            "alter sequence seq_categories restart;" + "alter sequence seq_flags restart;" + "alter sequence seq_types restart;" + "alter sequence seq_languages restart;" + "alter sequence seq_jokes restart;";
										//"alter sequence seq_jokes;"
	public static String bdd_empty = "\nBase de datos reseteada.";
	
	/*FILL BDD*/
	public static String url_get_categories = "https://v2.jokeapi.dev/info";
	public static String url_get_languages = "https://v2.jokeapi.dev/languages";
	public static String url_get_joke = "https://v2.jokeapi.dev/joke/Any?idRange=%s&lang=%s";
    public static String scriptInsertCategory = "insert into public.categories values ";
    public static String scriptCategory = "(nextval('seq_categories'),'%s')";
    public static String scriptInsertFlag = "insert into public.flags values ";
    public static String scriptFlag = "(nextval('seq_flags'),'%s')";
    public static String scriptInsertType = "insert into public.types values ";
    public static String scriptType = "(nextval('seq_types'),'%s')";
    public static String scriptInsertLanguage = "insert into public.language values ";
    public static String scriptLanguage = "(nextval('seq_languages'),'%s','%s')";
    public static String scriptInsertJoke = "insert into public.jokes values (nextval('seq_jokes')," //id
											+ "(select id from public.categories where category = '%s')," //category
											+ "(select id from public.types where type = '%s')," //type
											+ "'%s'," //joke
											+ "'%s'," //setup
											+ "'%s'," //delivery
											+ "(select id from public.language where code = '%s'));"; //language
    public static String scriptInsertNewJoke = "insert into jokes values (nextval('seq_jokes')," //id
											+ "%d," /*category*/ + "%d," /*type*/
											+ "'%s'," //joke
											+ "'%s'," //setup
											+ "'%s'," //delivery
											+ "%d);"; //language
    //public static String scriptNewJokeId = "select MAX(id) from public.jokes";
    public static String scriptNewJokeId = "select * from public.jokes order by id desc limit 1";
    public static String scriptInsertJokesFlags = "insert into public.jokes_flags values (%d,(select id from public.flags where flag = '%s'));"; 
	public static String bdd_full = "\nBase de datos cargada.";
}
