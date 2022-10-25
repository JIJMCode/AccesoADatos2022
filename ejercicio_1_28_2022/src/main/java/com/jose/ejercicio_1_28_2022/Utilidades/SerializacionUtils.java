package com.jose.ejercicio_1_28_2022.Utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.jose.ejercicio_1_28_2022.Entidades.HistoricoBusqueda;
import com.jose.ejercicio_1_28_2022.Entidades.Fran.Persona;

public class SerializacionUtils {
	
	public static boolean serializarPersona(String directorio, String nombreArchivo, Persona p) {
		
		File fichero = new File(directorio + "/" + nombreArchivo);
		try {
			FileOutputStream ficheroSalida = new FileOutputStream(fichero);
			ObjectOutputStream ficheroObjetos = new ObjectOutputStream(ficheroSalida);
			ficheroObjetos.writeObject(p);  // Serializa
			ficheroObjetos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static <T> boolean serializarObjeto(String directorio, String nombreArchivo, T p) {
		
		File fichero = new File(directorio + "/" + nombreArchivo);
		try {
			FileOutputStream ficheroSalida = new FileOutputStream(fichero);
			ObjectOutputStream ficheroObjetos = new ObjectOutputStream(ficheroSalida);
			ficheroObjetos.writeObject(p);  // Serializa
			ficheroObjetos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean serializarListaPersonas(String directorio, String nombreArchivo, List<Persona> personas) {
		
		File fichero = new File(directorio + "/" + nombreArchivo);
		try {
			ObjectOutputStream ficheroObjetos = new ObjectOutputStream(new FileOutputStream(fichero));
			ficheroObjetos.writeObject(personas);  // Serializa
			ficheroObjetos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean serializarListaBusquedas(String directorio, String nombreArchivo, List<HistoricoBusqueda> busquedas) {
		
		File fichero = new File(directorio + "/" + nombreArchivo);
		try {
			ObjectOutputStream ficheroObjetos = new ObjectOutputStream(new FileOutputStream(fichero));
			ficheroObjetos.writeObject(busquedas.toString());  // Serializa
			ficheroObjetos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static <T> void serializarListaObjetos(String nombreFichero, List<T> objetos) {
		File fichero = new File(nombreFichero);
		try {
			FileOutputStream ficheroSalida = new FileOutputStream(fichero);
			ObjectOutputStream ficheroObjetos = new ObjectOutputStream(ficheroSalida);
			ficheroObjetos.writeObject(objetos);
			ficheroObjetos.close();
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("otra excepcion");
			e.printStackTrace();
		}
	}
	
//	public static <T> boolean serializarListaObjetos(String directorio, String nombreArchivo, List<T> objetos) {
//		
//		File fichero = new File(directorio + "/" + nombreArchivo);
//		try {
//			ObjectOutputStream ficheroObjetos = new ObjectOutputStream(new FileOutputStream(fichero));
//			ficheroObjetos.writeObject(objetos);  // Serializa
//			ficheroObjetos.close();
//			return true;
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
	
	public static Persona desSerializarPersona(String directorio, String nombreArchivo) {
		
		File fichero = new File(directorio + "/" + nombreArchivo);
		try {
			FileInputStream ficheroSalida = new FileInputStream(fichero);
			ObjectInputStream ficheroObjetos = new ObjectInputStream(ficheroSalida);
			Persona p = (Persona) ficheroObjetos.readObject();  // DesSerializa
			ficheroObjetos.close();
			return p;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public static <T> T desSerializarObjeto(String directorio, String nombreArchivo) {
		
		File fichero = new File(directorio + "/" + nombreArchivo);
		try {
			FileInputStream ficheroSalida = new FileInputStream(fichero);
			ObjectInputStream ficheroObjetos = new ObjectInputStream(ficheroSalida);
			T objeto = (T) ficheroObjetos.readObject();  // DesSerializa
			ficheroObjetos.close();
			return objeto;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public static List<Persona> desSerializarListaPersonas(String directorio, String nombreArchivo) {
		
		File fichero = new File(directorio + "/" + nombreArchivo);
		try {
			FileInputStream ficheroSalida = new FileInputStream(fichero);
			ObjectInputStream ficheroObjetos = new ObjectInputStream(ficheroSalida);
			List<Persona> p = (List<Persona>) ficheroObjetos.readObject();  // DesSerializa
			ficheroObjetos.close();
			return p;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public static List<HistoricoBusqueda> desSerializarListaBusquedas(String directorio, String nombreArchivo) {
		
		File fichero = new File(directorio + "/" + nombreArchivo);
		try {
			FileInputStream ficheroSalida = new FileInputStream(fichero);
			ObjectInputStream ficheroObjetos = new ObjectInputStream(ficheroSalida);
			List<HistoricoBusqueda> p = (List<HistoricoBusqueda>) ficheroObjetos.readObject();  // DesSerializa
			ficheroObjetos.close();
			return p;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return null;
	}

//	public static <T> List<T> desSerializarListaObjetos(String directorio, String nombreArchivo) {
//	
//		File fichero = new File(directorio + "/" + nombreArchivo);
//		try {
//			FileInputStream ficheroSalida = new FileInputStream(fichero);
//			ObjectInputStream ficheroObjetos = new ObjectInputStream(ficheroSalida);
//			List<T> objetos = (List<T>) ficheroObjetos.readObject();  // DesSerializa
//			ficheroObjetos.close();
//			return objetos;
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} 
//		return null;
//	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> desSerializarListaObjetos(String nombreFichero) {
		File fichero = new File(nombreFichero);
		List<T> objetos = new ArrayList<T>();
		try {
			FileInputStream ficheroSalida = new FileInputStream(fichero);
			ObjectInputStream ficheroObjetos = new ObjectInputStream(ficheroSalida);
			objetos = (ArrayList<T>) ficheroObjetos.readObject();
			ficheroObjetos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objetos;
	}

}
