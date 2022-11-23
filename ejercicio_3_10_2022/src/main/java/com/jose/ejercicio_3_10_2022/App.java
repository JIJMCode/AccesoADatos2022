package com.jose.ejercicio_3_10_2022;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
	public static Scanner sc = new Scanner(System.in);
	static String consultaCompleta = "";
	static String consultaSQL = "";
	
	public static void main (String[] args) throws ClassNotFoundException, SQLException{
		ManejadorBaseDatos manejador = new ManejadorBaseDatos();
		boolean exit = false;
		
		do
		{
			ShowMenu();
			switch (getOption()) {
			case 1:
				addPersona(manejador);
				break;
			case 2:
				addCliente(manejador);		
				break;
			case 3:
				addFuncionario(manejador);			
				break;
			case 4:
				modifyPersona(manejador);			
				break;
			case 5:
				modifyCliente(manejador);			
				break;
			case 6:
				modifyFuncionario(manejador);			
				break;
			case 7:
				showPersonas(manejador);			
				break;
			case 8:
				showClientes(manejador);			
				break;
			case 9:
				showFuncionarios(manejador);			
				break;
			case 0:
				exit = true;
				sc.close();
				break;
			default:
				break;
			}
		}
		while(!exit);
	}
	
	private static int getOption() {
		System.out.println("Option:");
		int option = sc.nextInt();
		sc.nextLine();
		return option;
	}

	private static void ShowMenu()
	{
		System.out.println("1.-Añadir persona");
		System.out.println("2.-Añadir cliente");
		System.out.println("3.-Añadir funcionario");
		System.out.println("4.-Modificar persona");
		System.out.println("5.-Modificar cliente");
		System.out.println("6.-Modificar funcionario");
		System.out.println("7.-Ver personas");
		System.out.println("8.-Ver clientes");
		System.out.println("9.-Ver funcionarios");
		System.out.println("0.-Salir");
	}
	
	private static String pedirDatosUsuario (ManejadorBaseDatos manejador, String tipoPersona) {
		System.out.println("Introducir nombre:");
		String nombre = sc.nextLine();
		System.out.println("Introducir apellidos:");
		String apellidos = sc.nextLine();
		System.out.println("Introducir dirección:");
		String direccion = sc.nextLine();
		System.out.println("Introducir teléfono:");
		String telefono = sc.nextLine();
		System.out.println("Introducir fecha de nacimiento:");
		System.out.println("Introducir dia de nacimiento:");
		String diaNac = sc.nextLine();
		System.out.println("Introducir mes de nacimiento");
		String mesNac = sc.nextLine();
		System.out.println("Introducir año de nacimiento:");
		String anyoNac = sc.nextLine();
		consultaSQL += nombre + ",";
		consultaSQL += apellidos + ",";
		consultaSQL += direccion + ",";
		consultaSQL += telefono + ",";
		consultaSQL += diaNac + "/" + mesNac + "/" + anyoNac;
		
		if (tipoPersona.equals("cliente")) {
			System.out.println("Introducir número de cuenta:");
			String nrocuenta = sc.nextLine();
			System.out.println("Introducir estado:");
			String estado = sc.nextLine();
			System.out.println("Introducir tipo de cliente:");
			String tipoCliente = sc.nextLine();
			consultaSQL += "," + nrocuenta + ",";
			consultaSQL += estado + ",";
			consultaSQL += tipoCliente;
			consultaCompleta = manejador.insertClientes + consultaSQL;
		}else if (tipoPersona.equals("funcionario")) {
			System.out.println("Introducir cargo:");
			String cargo = sc.nextLine();
			System.out.println("Introducir departamento:");
			String departamento = sc.nextLine();
			System.out.println("Introducir fecha de ingreso:");
			System.out.println("Introducir dia de ingreso:");
			String diaIng = sc.nextLine();
			System.out.println("Introducir mes de ingreso");
			String mesIng = sc.nextLine();
			System.out.println("Introducir año de ingreso:");;
			String anyoIng = sc.nextLine();
			consultaSQL += "," + cargo + ",";
			consultaSQL += telefono + ",";
			consultaSQL += diaIng + "/" + mesIng + "/" + anyoIng;
			consultaCompleta = manejador.insertFuncionarios + consultaSQL;
		} else {
			consultaCompleta = manejador.insertPersonas + consultaSQL;
		}
		
		return consultaCompleta += ");";
	}
	
	private static void addPersona(ManejadorBaseDatos manejador) 
			throws ClassNotFoundException, SQLException 
	{
		try {
			manejador.update( pedirDatosUsuario(manejador, "persona"));
			System.out.println("Persona insertada con éxito");
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error, vuelva a intentarlo");
			System.out.println(e.getMessage());
		}
	}

	private static void addCliente(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{
		try {
			manejador.update( pedirDatosUsuario(manejador, "cliente"));
			System.out.println("Cliente insertado con éxito");
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error, vuelva a intentarlo");
			System.out.println(e.getMessage());
		}
	}
	
	private static void addFuncionario(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{
		try {
			manejador.update( pedirDatosUsuario(manejador, "funcionario"));
			System.out.println("Funcionaro insertado con éxito");
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error, vuelva a intentarlo");
			System.out.println(e.getMessage());
		}
	}
	
	private static void modifyPersona(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{

	}
	
	private static void modifyCliente(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{

	}
	
	private static void modifyFuncionario(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{

	}
	
	private static void showPersonas(ManejadorBaseDatos manejador) 
		throws SQLException, ClassNotFoundException 
	{
		try { 
			ResultSet rsPersonas = manejador.select(manejador.selectPers);
			int cont = 0;
			if (rsPersonas.next()) {
				System.out.println("Personas:");
				do {
					System.out.println("\n" + rsPersonas.getInt("numero") + "- "
										+ rsPersonas.getString("nombre") + " "
										+ rsPersonas.getString("apellidos"));
					cont++;
				} while(rsPersonas.next());
				System.out.println("Se han listado " + cont + " personas.\n");
			} else {
				System.out.println("No se ha encontrado ningún resultado.");
			}
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error, vuelva a intentarlo");
			System.out.println(e.getMessage());
		}
	}
	
	private static void showClientes(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{
		try { 
			ResultSet rsClientes = manejador.select(manejador.selectCli);
			int cont = 0;
			if (rsClientes.next()) {
				System.out.println("Clientes:");
				do {
					System.out.println("\n" + rsClientes.getInt("numero") + "- "
										+ rsClientes.getString("nombre") + " "
										+ rsClientes.getString("apellidos")
										+ "\nNum. de cuenta: " + rsClientes.getString("nrocuenta")
										+ "\nEstado: " + rsClientes.getString("estado") 
										+ "\nTipo de cliente: " + rsClientes.getString("tipocliente"));
					System.out.println("¿Es correcto el número de cuenta?");
					String respuesta = sc.nextLine();
					
					cont++;
				} while(rsClientes.next());
				System.out.println("Se han listado " + cont + " clientes.\n");
			} else {
				System.out.println("No se ha encontrado ningún resultado.");
			}
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error, vuelva a intentarlo");
			System.out.println(e.getMessage());
		}
	}
	
	private static void showFuncionarios(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{
		try { 
			ResultSet rsFuncionarios = manejador.select(manejador.selectFunc);
			int cont = 0;
			if (rsFuncionarios.next()) {
				System.out.println("Funcionarios:");
				do {
					System.out.println("\n" + rsFuncionarios.getInt("numero") + "- "
										+ rsFuncionarios.getString("nombre") + " "
										+ rsFuncionarios.getString("apellidos")
										+ "\nCargo: " + rsFuncionarios.getString("cargo")
										+ "\nDepartamento: " + rsFuncionarios.getString("departamento") 
										+ "\nFecha de ingreso: " + rsFuncionarios.getString("fecha_ingreso"));
					cont++;
				} while(rsFuncionarios.next());
				System.out.println("Se han listado " + cont + " funcionarios.\n");
			} else {
				System.out.println("No se ha encontrado ningún resultado.");
			}
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error, vuelva a intentarlo");
			System.out.println(e.getMessage());
		}
	}

}
