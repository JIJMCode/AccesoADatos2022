package com.jose.ejercicio_2_15_2022_Utilidades;

public class Literals {
	public static String bdd_empty = "\nBase de datos reseteada.";
	public static String bdd_full = "\nBase de datos cargada.";
	public static String repeat_title = "\n==============================================\n= Pulse C para volver al MENU o S para salir =\n==============================================\n";
	public static String url_get_categories = "https://v2.jokeapi.dev/categories";
	public static String url_get_languages = "https://v2.jokeapi.dev/languages";
//	public static String errorLongitud = "\nLa longitud debe ser un número entre -180 y 180, ambos inclusive.";
//	public static String deleting = "Eliminando duplicado";
//	public static String deleted = "Eliminación completada.";
//	public static String nothing_serialize = "No hay búsquedas para serializar.";
//	public static String serializing = "Serializando...";
//	public static String serialize_ok = "Búsquedas recientes serializadas con éxito.";
//	public static String error = "ERROR";
//	public static String history_list = "Lista de búsquedas almacenadas:";
//	public static String total_goals = "Goles totales del %s = %d";
//	public static String insert_local = "\nIntroduzca el nombre del equipo local";
//	public static String insert_visitant = "\nIntroduzca el nombre del equipo visitante";
//	public static String insert_start_date = "\nIntroduzca la fecha inicial con formato dd/mm/yyyy:";
//	public static String insert_end_date = "\nIntroduzca la fecha final con formato dd/mm/yyyy:";
//	public static String history_count = "Total de Registros: %s";
	public static String menu_title = "\nMenú (Selecione una opción)";
    public static String menu_1 = "1. Resetear base de datos";
    public static String menu_2 = "2. Añadir chistes (jokes) Statement.";
    public static String menu_3 = "3. Añadir chiste PreparedStatement.";
    public static String menu_4 = "4. Búsqueda de chistes por texto CallableStatement";
    public static String menu_5 = "5. Obtener chistes sin flags CallableStatement.";
    public static String menu_exit = "0. Salir"; 
	public static String error_no_num = "\n***El valor introducido  no es un número";
	public static String choose_option = "\n***Debe seleccionar una de las opciones propuestas.";
	public static String continue_exit = "\nPulse C para continuar o S para salir\n";
	public static String app_closed = "\nAplicación cerrada. Hasta pronto!!!";
	public static String vaciarBddSQL = "delete from public.categories;\n" + "delete from public.types;\n" + "delete from public.language;\n" + 
										"delete from public.flags;\n" + "delete from public.jokes_flags;\n" + "delete from public.jokes;" +
							            "alter sequence seq_categories\r\nstart with 0\r\nincrement by 1\r\nmaxvalue 9223372036854775807\r\nminvalue 0\r\ncycle;" +
							            "alter sequence seq_flags\r\nstart with 0\r\nincrement by 1\r\nmaxvalue 9223372036854775807\r\nminvalue 0\r\ncycle;" +
							            "alter sequence seq_language\r\nstart with 0\r\nincrement by 1\r\nmaxvalue 9223372036854775807\r\nminvalue 0\r\ncycle;" +
							            "alter sequence seq_types\r\nstart with 0\r\nincrement by 1\r\nmaxvalue 9223372036854775807\r\nminvalue 0\r\ncycle;" +
							            //"alter sequence seq_jokes_flags\r\nstart with 0\r\nincrement by 1\r\nmaxvalue 9223372036854775807\r\nminvalue 0\r\ncycle;" +
										"alter sequence seq_jokes\r\nstart with 0\r\nincrement by 1\r\nmaxvalue 9223372036854775807\r\nminvalue 0\r\ncycle;";
	public static String cargarBddSQL = "";
}
