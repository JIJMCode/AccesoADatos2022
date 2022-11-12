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
	public static String error_no_num = "\n***El valor introducido  no es un número";
	public static String choose_option = "\n***Debe seleccionar una de las opciones propuestas.";
	public static String repeat_title = "\n==============================================\n= Pulse C para volver al MENU o S para salir =\n==============================================\n";
	public static String continue_exit = "\nPulse C para continuar o S para salir\n";
	public static String app_closed = "\nAplicación cerrada. Hasta pronto!!!";
	
	/*RESET BDD*/
	public static String vaciarBddSQL = "delete from public.categories;\n" + "delete from public.types;\n" + "delete from public.language;\n" + "delete from public.flags;\n" + "delete from public.jokes_flags;\n" + "delete from public.jokes;" +
							            "alter sequence seq_categories restart;" + "alter sequence seq_flags restart;" + "alter sequence seq_language restart;" + "alter sequence seq_types restart;";
										//"alter sequence seq_jokes;"
	public static String bdd_empty = "\nBase de datos reseteada.";
	
	/*FILL BDD*/
	public static String url_get_categories = "https://v2.jokeapi.dev/info";
	public static String url_get_languages = "https://v2.jokeapi.dev/languages";
	public static String url_get_joke = "https://v2.jokeapi.dev/joke/Any?idRange= %s &lang= %s";
    public static String scriptInsertJoke = "insert into public.jokes values ('%i','%s','%s','%s','%s','%s','%s');";
    public static String scriptInsertCategory = "insert into public.categories values ('%i','%s');";
    public static String scriptInsertFlag = "insert into public.flags values ('%i','%s');";
    public static String scriptInsertType = "insert into public.types values ('%i','%s');";
    public static String scriptInsertLanguage = "insert into public.languages values ('%s','%s');";
	public static String bdd_full = "\nBase de datos cargada.";
}
