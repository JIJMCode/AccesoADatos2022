package com.jose.ejercicio_3_10_2022;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
	public static Scanner sc = new Scanner(System.in);
	static String consultaCompleta = "";
	static String consultaSQL = "";
	static int numeroPersona = 0;
	
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
	
	private static String pedirDatosUsuario (ManejadorBaseDatos manejador, String tipoPersona) throws ClassNotFoundException, SQLException {
		consultaSQL = "";
		System.out.println("Introducir nombre:");
		sc.nextLine();
		String nombre = sc.nextLine();
		System.out.println("Introducir apellidos:");
		sc.nextLine();
		String apellidos = sc.nextLine();
		System.out.println("Introducir dirección:");
		sc.nextLine();
		String direccion = sc.nextLine();
		System.out.println("Introducir teléfono:");
		sc.nextLine();
		String telefono = manejador.validarTelefono();
		System.out.println("Introducir fecha de nacimiento en formato dia/mes/año:");
		sc.nextLine();
		String fechaNac = manejador.validarFecha("nacimiento");
		consultaSQL += "'" + nombre + "'";
		consultaSQL += ",'" + apellidos + "'";
		consultaSQL += ",'" + direccion + "'";
		consultaSQL += ",'" + telefono + "'";
		consultaSQL += ",'" + fechaNac + "'";
		
		if (tipoPersona.equals("cliente")) {
			System.out.println("Introducir número de cuenta:");
			sc.nextLine();
			String nrocuenta = sc.nextLine();
			System.out.println("Introducir un estado:");
			sc.nextLine();
			String estado = manejador.validarOpcion("estado");
			System.out.println("Introducir tipo de cliente:");
			sc.nextLine();
			String tipocliente = manejador.validarOpcion("tipocliente");
			String consultaSQLcli = ",'" + nrocuenta + "'";
			consultaSQLcli += ",'" + estado + "'";
			consultaSQLcli += ",'" + tipocliente + "'";
			consultaCompleta = manejador.insertClientes + consultaSQL + consultaSQLcli;
		}else if (tipoPersona.equals("funcionario")) {
			System.out.println("Introducir cargo:");
			sc.nextLine();
			String grupo = manejador.validarOpcion("grupo");
			System.out.println("Introducir departamento:");
			sc.nextLine();
			String cuerpo = manejador.validarCodigoCuerpo();
			String cargo = "(" + grupo + "," + cuerpo + ")";
			System.out.println("Introducir departamento:");
			sc.nextLine();
			String departamento = sc.nextLine();
			System.out.println("Introducir fecha de ingreso:");
			sc.nextLine();
			String fecha = manejador.validarFecha("ingreso");
			String consultaSQLFunc = ",'" + cargo + "'";
			consultaSQLFunc += ",'" + departamento + "'";
			consultaSQLFunc += ",'" + fecha + "'";
			consultaCompleta = manejador.insertFuncionarios + consultaSQL + consultaSQLFunc;
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
			System.out.println("\n*********************************************");
			System.out.println("****** Persona insertada con éxito. *********");
			System.out.println("*********************************************\n");
		} catch (Exception e) {
			System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			System.out.println("Ha ocurrido un error, vuelva a intentarlo");
			System.out.println(e.getMessage());
		}
	}
	
	private static void addCliente(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{
		try {
			manejador.update(pedirDatosUsuario(manejador, "cliente"));
			System.out.println("\n*********************************************");
			System.out.println("****** Cliente insertado con éxito. ********");
			System.out.println("*********************************************\n");
		} catch (Exception e) {
			System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			System.out.println("Ha ocurrido un error, vuelva a intentarlo");
			System.out.println(e.getMessage());
		}
	}
	
	private static void addFuncionario(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{
		try {
			manejador.update(pedirDatosUsuario(manejador, "funcionario"));
			System.out.println("\n*********************************************");
			System.out.println("***** Funcionario insertado con éxito. ******");
			System.out.println("*********************************************\n");
		} catch (Exception e) {
			System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			System.out.println("Ha ocurrido un error, vuelva a intentarlo");
			System.out.println(e.getMessage());
		}
	}
	
	private static String pedirDatosModificar(ManejadorBaseDatos manejador,  String tipoPersona) 
			throws ClassNotFoundException, SQLException
	{
		consultaSQL = "";
		boolean modified = false;
		System.out.println("¿Desea modificar el nombre? Responda \"si\" o \"no\"");
		String nombre;
		if(manejador.checkYesNo()) {
			System.out.println("Introducir nombre:");
			sc.nextLine();
			nombre = sc.nextLine();
			consultaSQL += "nombre = '" + nombre + "',";
			modified = true;
		}
		System.out.println("¿Desea modificar los apellidos? Responda \"si\" o \"no\"");
		String apellidos = "";
		if(manejador.checkYesNo()) {
			System.out.println("Introducir apellidos:");
			sc.nextLine();
			apellidos = sc.nextLine();
			consultaSQL += "apellidos = '" + apellidos + "',";
			modified = true;
		}
		String direccion;
		System.out.println("¿Desea modificar la direccion? Responda \"si\" o \"no\"");
		if(manejador.checkYesNo()) {
			System.out.println("Introducir dirección:");
			sc.nextLine();
			direccion = sc.nextLine();
			consultaSQL += "direccion = '" + direccion + "',";
			modified = true;
		}
		System.out.println("¿Desea modificar el telefono? Responda \"si\" o \"no\"");
		String telefono;
		if(manejador.checkYesNo()) {
			System.out.println("Introducir telefono:");
			sc.nextLine();
			telefono = manejador.validarTelefono();
			consultaSQL += "telefono = '" + telefono + "',";
			modified = true;
		}
		System.out.println("¿Desea modificar la fecha de nacimiento? Responda \"si\" o \"no\"");
		String fechaNac;
		if(manejador.checkYesNo()) {
			//System.out.println("Introducir fecha de nacimiento en formato dia/mes/año:");
			//sc.nextLine();
			fechaNac = manejador.validarFecha("nacimiento");
			consultaSQL += "fecha_nacim = '" + fechaNac + "',";
			modified = true;
		}
		
		if(modified) {
			consultaSQL = consultaSQL.substring(0, consultaSQL.length() - 1);
			//consultaCompleta = manejador.updatePersonas + consultaSQL;
		}
		
		if (tipoPersona.equals("cliente")) {
			String consultaSQLcli = "";
			System.out.println("¿Desea modificar el número de cuenta? Responda \"si\" o \"no\"");
			String nrocuenta;
			if(manejador.checkYesNo()) {
				System.out.println("Introducir número de cuenta:");
				sc.nextLine();
				nrocuenta = sc.nextLine();
				consultaSQLcli += "nrocuenta = '" + nrocuenta + "',";
				modified = true;
			}
			System.out.println("¿Desea modificar el estado? Responda \"si\" o \"no\"");
			String estado;
			if(manejador.checkYesNo()) {
				System.out.println("Introducir un estado:");
				sc.nextLine();
				estado = manejador.validarOpcion("estado");
				consultaSQLcli += "estado = '" + estado + "',";
				modified = true;
			}
			System.out.println("¿Desea modificar el tipo de cliente? Responda \"si\" o \"no\"");
			String tipocliente;
			if(manejador.checkYesNo()) {
				System.out.println("Introducir tipo de cliente:");
				sc.nextLine();
				tipocliente = manejador.validarOpcion("tipocliente");
				consultaSQLcli += "tipocliente = '" + tipocliente + "',";
				modified = true;
			}
			
			if(modified && !consultaSQLcli.isBlank())
				consultaSQLcli = consultaSQLcli.substring(0, consultaSQLcli.length() - 1);
			
			consultaCompleta = manejador.updateClientes + consultaSQL + consultaSQLcli;
		}else if (tipoPersona.equals("funcionario")) {
			String consultaSQLFunc = "";
			System.out.println("¿Desea modificar el cargo? Responda \"si\" o \"no\"");
			String tipocliente;
			if(manejador.checkYesNo()) {
				System.out.println("Introducir grupo:");
				sc.nextLine();
				String grupo = manejador.validarOpcion("grupo");
				System.out.println("Introducir código del cuerpo:");
				sc.nextLine();
				String cuerpo = manejador.validarCodigoCuerpo();
				String cargo = "('" + grupo + "','" + cuerpo + "')";
				consultaSQLFunc += "cargo = " + cargo + ",";
				modified = true;
			}

			System.out.println("¿Desea modificar el departamento? Responda \"si\" o \"no\"");
			String departamento;
			if(manejador.checkYesNo()) {
				System.out.println("Introducir departamento:");
				sc.nextLine();
				departamento = sc.next();
				consultaSQLFunc += "departamento = '" + departamento + "',";
				modified = true;
			}
	
			System.out.println("¿Desea modificar la fecha de ingreso? Responda \"si\" o \"no\"");
			String fecha;
			if(manejador.checkYesNo()) {
				System.out.println("Introducir fecha de ingreso:");
				sc.nextLine();
				fecha = manejador.validarFecha("ingreso");
				consultaSQLFunc += "tipocliente = '" + fecha + "',";
				modified = true;
			}
			
			if(modified && !consultaSQLFunc.isBlank())
				consultaSQLFunc = consultaSQLFunc.substring(0, consultaSQLFunc.length() - 1);
			
			consultaCompleta = manejador.updateFuncionarios + consultaSQL + consultaSQLFunc;
		}

		if (modified) {
			return consultaCompleta;
		} else {
			return "";
		}
	}
	
	private static void modifyPersona(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{
		ResultSet rsPersona = manejador.validarId("persona");
		
		if(rsPersona.next()) {
			String consultaUpdate = pedirDatosModificar(manejador, "persona");
			
			if(consultaUpdate.isBlank())
				System.out.println("No se ha realizado ningún cambio. No procede actualización.");
			else {
				consultaUpdate += " where numero = " + rsPersona.getInt(1);
				manejador.update(consultaUpdate);
				System.out.println("Registro actualizado correctamente.");
			}
		}else {
			System.out.println("No existen personas que coincidan con los datos aportados.");
		}
	}
	
	private static void modifyCliente(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{
		ResultSet rsPersona = manejador.validarId("cliente");
		
		if(rsPersona.next()) {
			String consultaUpdate = pedirDatosModificar(manejador, "cliente");
			
			if(consultaUpdate.isBlank())
				System.out.println("No se ha realizado ningún cambio. No procede actualización.");
			else {
				consultaUpdate += " where numero = " + rsPersona.getInt(1);
				manejador.update(consultaUpdate);
				System.out.println("Registro actualizado correctamente.");
			}
		}else {
			System.out.println("No existen clientes que coincidan con los datos aportados.");
		}
	}
	
	private static void modifyFuncionario(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{
		ResultSet rsPersona = manejador.validarId("funcionario");
		
		if(rsPersona.next()) {
			String consultaUpdate = pedirDatosModificar(manejador, "funcionario");
			
			if(consultaUpdate.isBlank())
				System.out.println("No se ha realizado ningún cambio. No procede actualización.");
			else {
				consultaUpdate += " where numero = " + rsPersona.getInt(1);
				manejador.update(consultaUpdate);
				System.out.println("Registro actualizado correctamente.");
			}
		}else {
			System.out.println("No existen funcionarios que coincidan con los datos aportados.");
		}
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
