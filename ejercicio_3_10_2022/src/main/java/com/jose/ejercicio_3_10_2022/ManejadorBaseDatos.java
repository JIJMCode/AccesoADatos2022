package com.jose.ejercicio_3_10_2022;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ManejadorBaseDatos {
	Scanner sc = new Scanner(System.in);
	String url = "jdbc:postgresql://localhost:5432/gestionpersonal";
	String usuario = "postgres";
	String password = "postgre";
	String selectPers = "select * from personas;";
	String selectPersById = "select * from personas where numero = ";
	String selectCli = "select * from clientes;";
	String selectCliById = "select * from clientes where numero = ";
	String selectFunc = "select * from funcionarios;";
	String selectFuncById = "select * from funconarios where numero = ";
	String selectBuscar = "select numero,nombre,apellidos from personas;";
	String selectGetId = "select MAX(numero) from personas";
	String insertPersonas = "insert into personas values (nextval('seq_personas'),";
	String insertClientes = "insert into clientes values (nextval('seq_personas'),";
	String insertFuncionarios = "insert into funcionarios values (nextval('seq_personas'),";
	String updatePersonas = "update personas set ";
	String updateClientes = "update clientes set ";
	String updateFuncionarios = "update funcionarios set ";
	
	public ManejadorBaseDatos() 
		throws ClassNotFoundException, SQLException
	{
		checkDataBaseIsCreated();
	}
	
	private void checkDataBaseIsCreated() throws 
		ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost:5432/";
		String usuario = "postgres";
		String password = "postgre";
		
		Connection con = DriverManager.getConnection(url, usuario, password);
		Statement statement = con.createStatement();
		
		String sentenciaSQL = 
				"SELECT * FROM pg_database WHERE datname LIKE 'gestionpersonal';";
		
		ResultSet rs = statement.executeQuery(sentenciaSQL);
		
		if (!rs.next()) {
			boolean exit = false;
			System.out.println("No se ha encontrado base de datos ");
			do
			{
				System.out.println("Crear base de datos automaticamente? (si/no)");
				String answer = sc.nextLine();
				
				if (answer.equals("si")) 
				{
					createDataBase();
					exit = true;
				}
				else if(answer.equals("no"))
				{
					System.out.println("No se creó la base de datos");
					System.exit(0);
				}
				else
				{
					System.out.println("Error de escritura prueba con (si/no)");
				}
			}
			while(!exit);
		}
		else
		{
			System.out.println("Se ha encontrado la base de datos ");
			boolean exit = false;
			do
			{
				System.out.println("Borrar y crear un nueva base de datos "
						+ "automaticamente? (si/no)");
				String answer = sc.nextLine();
				
				if (answer.equals("si")) 
				{
					createDataBase();
					exit = true;
				}
				else if(answer.equals("no"))
				{
					checkTablesAreCreated();
					exit = true;
				}
				else
				{
					System.out.println("Error de escritura prueba con (si/no)");
				}
			}
			while(!exit);	
		}
	}
	
	public boolean checkYesNo() {
		boolean result = false;
		String answer;
		do
		{
			answer = sc.nextLine().toLowerCase();
			
			if (answer.equals("si")) 
			{
				result = true;
			}
			else if(answer.equals("no"))
			{
				result = false;
			}
			else
			{
				System.out.println("Error de escritura prueba con (si/no)");
			}
		}
		while(!answer.equals("si") && !answer.equals("no"));
		
		return result;
	}
	
	private void checkTablesAreCreated() 
		throws ClassNotFoundException, SQLException {
		Connection con = conection();
		Statement statement = con.createStatement();
		
		String sentenciaSQL = 
				"SELECT count(*) " + 
				"FROM information_schema.tables " + 
				"WHERE table_type = 'BASE TABLE' " + 
				"AND table_name = 'personas' " + 
				"OR table_name = 'clientes' " + 
				"OR table_name = 'funcionarios';";
		
		ResultSet rs = statement.executeQuery(sentenciaSQL);
		rs.next();
		if(rs.getInt(1) != 3)
		{
			System.out.println("No se ha encontrado tablas ");
			System.out.println("Crear tablas automaticamente? (si/no)");
			String answer = sc.nextLine();
			
			if (answer.equals("si")) {
				createTables();
			}
			else
			{
				System.out.println("No se crearon las tablas");
				System.exit(0);
			}
		}
		else {
			System.out.println("Tablas cargadas con exito");
		}
		
	}
	
	public void createTables() 
			throws ClassNotFoundException, SQLException
	{
		Connection con = conection();
		Statement statement = con.createStatement();
		
		/* En el siguiente String tendremos que concatenar
		 * las diferentes instrucciones de borrado de tablas y su creación
		 * los tipos de datos que queramos hacer y las tablas heredadas
		 * Es el Script de creación de nuestra base de datos
		 */
		String sentenciaSQL = "CREATE TYPE estadotype AS ENUM ('activo', 'pendiente', 'inactivo'); "; 
		sentenciaSQL += "CREATE TYPE tipoclientetype AS ENUM ('normal', 'premium'); ";
		sentenciaSQL += "CREATE TYPE grupotype AS ENUM ('A1', 'A2', 'C1', 'C2', 'AP'); ";
		sentenciaSQL += "CREATE TYPE cargotype AS (cargo grupotype,codigo character varying(5)); ";
		sentenciaSQL += "CREATE TABLE public.personas (numero integer NOT NULL,nombre character varying,"
				+ "apellidos character varying,direccion character varying,telefono character varying(9), "
				+ "fecha_nacim date,PRIMARY KEY (numero)); ";
		sentenciaSQL += "CREATE TABLE public.clientes (nrocuenta character varying,estado estadotype,"
				+ "tipocliente tipoclientetype) INHERITS (public.personas); ";
		sentenciaSQL += "CREATE TABLE public.funcionarios (cargo cargotype,departamento character varying,"
				+ "fecha_ingreso date) INHERITS (public.personas); ";
		sentenciaSQL += "CREATE SEQUENCE public.seq_personas INCREMENT 1 START 1 MINVALUE 1 OWNED BY personas.numero; ";

		try {
			statement.executeUpdate(sentenciaSQL);
			
		} catch (Exception e) {
			System.out.println("Problemas creando las tablas");
		} finally {
			con.close();
		}
		
		/*
		 * Aquí crearemos los datos de prueba iniciales que queramos insertar
		 * en nuestra base de datos
		 */
		String pruebaPersonas = "INSERT INTO public.personas(numero, nombre, apellidos, direccion, telefono, fecha_nacim)"
				+ "	VALUES (nextval('seq_personas'), 'Jose', 'López Ponce', 'Calle Mayor 13, 1A', '111222333', '01/05/1970'),"
				+ " (nextval('seq_personas'), 'Julia', 'Martínez Lugo', 'Calle Pintor Baeza 25, 5H', '444555666', '08/09/1975'),"
				+ "	(nextval('seq_personas'), 'Lorena', 'Ruiz Esteve', 'Avenida Diagonal 140, 10D', '555666777', '25/11/180');";
		String pruebaCliente = "INSERT INTO public.clientes (numero, nombre, apellidos, direccion, telefono, fecha_nacim, \"nrocuenta\", estado, \"tipocliente\")"
				+ "	VALUES (nextval('seq_personas'), 'Raúl', 'Montero Tarradellas', 'Calle Olvido 4, bajo D', '111555777', '30/12/1968', 'ES0512345678901234567890', 'activo', 'premium'),"
				+ "(nextval('seq_personas'), 'Luis', 'Lopez Haro', 'Calle Olvido 4, bajo D', '111555777', '30/12/1968', 'ES0512345678901234567890', 'pendiente', 'normal'),"
				+ "(nextval('seq_personas'), 'Verónica', 'Ruiz Sanchez', 'Calle Olvido 4, bajo D', '111555777', '30/12/1968', 'ES0512345678901234567890', 'inactivo', 'normal');";
		String pruebaFuncionario = "INSERT INTO public.funcionarios(numero, nombre, apellidos, direccion, telefono, fecha_nacim, cargo, departamento, fecha_ingreso)\r\n"
				+ "VALUES (nextval('seq_personas'), 'Victor', 'Coloma Aniorte', 'Calle Nueva 14, 6D', '444666222', '30/12/1968', ('A2','DT234'), 'facturacion', '05/04/2015'),"
				+ "(nextval('seq_personas'), 'Roberto', 'Garcia Lillo', 'Calle Gerona 30, 9I', '888999333', '30/12/1968', ('C2','AM567'), 'ventas', '16/01/2018'),"
				+ "(nextval('seq_personas'), 'Elisa', 'Cabrera Ortiz', 'Calle Princesa 6, 2A', '777111666', '30/12/1968', ('AP','ZZ247'), 'almacén', '23/09/2013');";
		
		update(pruebaPersonas);
		update(pruebaCliente);
		update(pruebaFuncionario);
	}

	private List<String> opcionesEnum(String nombreEnum) throws SQLException, ClassNotFoundException {
		Connection con = conection();
		Statement statement = con.createStatement();	
		
		List<String> enumOptions = new ArrayList<>();
		
		String sentenciaSQL = "select enumlabel from pg_enum where enumtypid = ";
		
		switch (nombreEnum) {
		case "grupo":
			sentenciaSQL += "25610;";
			break;
		case "tipocliente":
			sentenciaSQL += "25604;";
			break;
		case "estado":
			sentenciaSQL += "25596;";
			break;
		default:
			break;
		}
			
		try {
			ResultSet rs = statement.executeQuery(sentenciaSQL);
			
			while (rs.next()) {
				enumOptions.add(rs.getString(1));
			}
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error;");
			System.out.println(e.getMessage());
		} finally {
			con.close();
		}

		return enumOptions;
	}
	
	public String validarCodigoCuerpo() {
		String codigoCuerpo = "";
		boolean bodyOk = false;
		
		while (!bodyOk) {
			try {
				System.out.println("El código de departamento debe estar compuesto por 5 caracteres.");
				codigoCuerpo = App.sc.next();
				if(codigoCuerpo.length() == 5) {
					bodyOk = true;
				}
			}catch (Exception e) {
				System.out.println("El código no es correcto, vuelva a intentarlo.");
			}
		}

		return codigoCuerpo;
	}
	
	public String validarTelefono() {
		String telefono = "";
		boolean telefonoOk = false;
		
		while (!telefonoOk) {
			try {
				System.out.println("El teléfono debe estar compuesto por 9 caracteres.");
				telefono = App.sc.next();
				if(telefono.length() == 9) {
					telefonoOk = true;
				}
			}catch (Exception e) {
				System.out.println("El código no es correcto, vuelva a intentarlo.");
			}
		}

		return telefono;
	}
	
	public String validarFecha(String tipoFecha) {
		String result = "";
		boolean fechaOk = false;
		String error = "";
		while (!fechaOk) {

	        try{
				System.out.println("Introducir fecha de " + tipoFecha + " en formato dia/mes/año:\"");

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String fecha = sc.nextLine();
				LocalDate localDate = LocalDate.parse(fecha, formatter);
				
	            fechaOk = true;
	            result += fecha;
	        } catch(DateTimeException e) {
	        	fechaOk = false;
	        	System.out.println("Formato de fecha incorrecto o fecha no válida.");
	        }
		}
		
		return result;
	}
	
	public ResultSet validarId(String tipoPers) throws ClassNotFoundException, SQLException {
		int id = 0;
		boolean numOk = false;
		
		ResultSet rs = select(selectGetId);
		
		if(rs.next()) {
			int maxId = rs.getInt(1); 
			
			while (!numOk) {
				try {
					System.out.println("Introduzca el código de la persona a modificar:");
					id = App.sc.nextInt();
					if(id>0 && id<=maxId) {
						numOk = true;
						switch (tipoPers) {
						case "persona":
							rs = select(selectPersById + id);
							break;
						case "cliente":
							rs = select(String.format(selectCliById,id));
							break;
						case "funcionario":
							rs = select(String.format(selectFuncById,id));
							break;
						default:
							break;
						}
					}
				}catch (Exception e) {
					System.out.println("El código no es correcto.");
					System.out.println("El código debe ser númerico, mayor que 0 y menor o igual a " + maxId + ".");
				}
			}
		}

		return rs;	
	}
	
	public String validarOpcion(String nombreEnum) throws ClassNotFoundException, SQLException {
		String result = "";
		List<String> opcionesEnum = opcionesEnum(nombreEnum);
		System.out.println("Elija una de las siguientes opciones");
		opcionesEnum.forEach(x-> {System.out.println("- " + x);});
		result = App.sc.nextLine().toUpperCase();
		while (!opcionesEnum.contains(result)) {
			System.out.println("El valor introducido no es correcto.");
			System.out.println("Elija una de las siguientes opciones");
			opcionesEnum.forEach(x-> {System.out.println("- " + x);});
			result = App.sc.nextLine();
		}
		
		return result;
	}
	
	private void createDataBase() throws 
	ClassNotFoundException, SQLException 
	{
		System.out.println("Creando base de datos GestionPersonal");
		
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost:5432/";
		String usuario = "postgres";
		String password = "postgre";
		
		Connection con = DriverManager.getConnection(url, usuario, password);
		Statement statement = con.createStatement();
		
		String sentenciaSQL = "DROP DATABASE IF EXISTS gestionpersonal; "
				+ "CREATE DATABASE gestionpersonal;";
		
		try {
			int errorCode = statement.executeUpdate(sentenciaSQL);
			
			if (errorCode == 0) {
				System.out.println("Se ha creado con exito la base de datos");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			con.close();
		}
		
		createTables();
	}
		
	private Connection conection() throws
	ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection(url, usuario, password);
	}
		
	public void update(String sentenceSQL) 
		throws ClassNotFoundException, SQLException
	{
		Connection con = conection();
		Statement statement = con.createStatement();
		
		try {
			statement.executeUpdate(sentenceSQL);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			con.close();
		}
	}
	
	public ResultSet select(String sentenciaSQL) 
		throws ClassNotFoundException, SQLException
	{
		Connection con = conection();
		Statement statement = con.createStatement();
		
		ResultSet rs = statement.executeQuery(sentenciaSQL);
		
		con.close();
		
		return rs;
	}
}
                           
                           
  