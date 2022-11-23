package com.jose.ejercicio_3_10_2022;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ManejadorBaseDatos {
	Scanner sc = new Scanner(System.in);
	String url = "jdbc:postgresql://localhost:5432/gestionpersonal";
	String usuario = "postgres";
	String password = "postgre";
	String selectPers = "select * from personas;";
	String selectCli = "select * from clientes;";
	String selectFunc = "select * from funcionarios;";
	String insertPersonas = "insert into personas values (nextval('seq_personas'),";
	String insertClientes = "insert into personas values (nextval('seq_personas'),";
	String insertFuncionarios = "insert into personas values (nextval('seq_personas'),";
	
	public ManejadorBaseDatos() 
		throws ClassNotFoundException, SQLException
	{
		checkDataBaseIsCreated();
	}
	
	private void checkDataBaseIsCreated() throws 
		ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost:5432/gestionpersonal";
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
		String sentenciaSQL = "CREATE TYPE estadotype AS ENUM ('activo', 'pendiente', 'inactivo');"; 
		sentenciaSQL += "CREATE TYPE tipoclientetype AS ENUM ('normal', 'premium');";
		sentenciaSQL += "CREATE TYPE grupotype AS ENUM ('A1', 'A2', 'C1', 'C2', 'AP');";
		sentenciaSQL += "CREATE TYPE cargotype AS (cargo grupotype,codigo character varying(5));";
		sentenciaSQL += "CREATE TABLE public.personas (numero integer NOT NULL,nombre character varying,"
				+ "apellidos character varying,direccion character varying,telefono character varying(9),\r\n"
				+ "fecha_nacim date,PRIMARY KEY (numero));";
		sentenciaSQL += "CREATE TABLE public.clientes (nroCuenta character varying,estado estadotype,"
				+ "tipoCliente tipoclientetype) INHERITS (public.personas);";
		sentenciaSQL += "CREATE TABLE public.funcionarios (cargo cargotype,departamento character varying,"
				+ "fecha_ingreso date) INHERITS (public.personas);";
		sentenciaSQL += "CREATE SEQUENCE public.seq_personas INCREMENT 1 START 1 MINVALUE 1 OWNED BY personas.numero;";
		sentenciaSQL += "CREATE SEQUENCE public.seq_clientes INCREMENT 1 START 1 MINVALUE 1 OWNED BY clientes.numero;";
		sentenciaSQL += "CREATE SEQUENCE public.seq_funcionarios INCREMENT 1 START 1 MINVALUE 1 OWNED BY funcionarios.numero;";

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
		String pruebaCliente = "INSERT INTO public.clientes (numero, nombre, apellidos, direccion, telefono, fecha_nacim, \"nroCuenta\", estado, \"tipoCliente\")"
				+ "	VALUES (nextval('seq_clientes'), 'Raúl', 'Montero Tarradellas', 'Calle Olvido 4, bajo D', '111555777', '30/12/1968', 'ES0512345678901234567890', 'activo', 'premium'),"
				+ "(nextval('seq_clientes'), 'Raúl', 'Montero Tarradellas', 'Calle Olvido 4, bajo D', '111555777', '30/12/1968', 'ES0512345678901234567890', 'pendiente', 'normal'),"
				+ "(nextval('seq_clientes'), 'Raúl', 'Montero Tarradellas', 'Calle Olvido 4, bajo D', '111555777', '30/12/1968', 'ES0512345678901234567890', 'inactivo', 'normal');";
		String pruebaFuncionario = "INSERT INTO public.funcionarios(numero, nombre, apellidos, direccion, telefono, fecha_nacim, cargo, departamento, fecha_ingreso)\r\n"
				+ "VALUES (nextval('seq_funcionarios'), 'Victor', 'Coloma Aniorte', 'Calle Nueva 14, 6D', '444666222', '30/12/1968', ('A2','1234'), 'facturacion', '05/04/2015'),"
				+ "(nextval('seq_funcionarios'), 'Roberto', 'Garcia Lillo', 'Calle Gerona 30, 9I', '888999333', '30/12/1968', ('C2','4567'), 'ventas', '16/01/2018'),"
				+ "(nextval('seq_funcionarios'), 'Elisa', 'Cabrera Ortiz', 'Calle Princesa 6, 2A', '777111666', '30/12/1968', ('AP','3247'), 'almacén', '23/09/2013');";
		
		update(pruebaPersonas);
		update(pruebaCliente);
		update(pruebaFuncionario);
	}

	private void createDataBase() throws 
	ClassNotFoundException, SQLException 
	{
		System.out.println("Creando base de datos GestionPersonal");
		
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost:5432/";
		String usuario = "postgres";
		String password = "postgres";
		
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
                           
                           
  