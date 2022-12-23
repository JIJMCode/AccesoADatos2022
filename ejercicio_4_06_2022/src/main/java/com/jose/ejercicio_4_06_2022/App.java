package com.jose.ejercicio_4_06_2022;

import com.jose.ejercicio_4_06_2022.Utilidades.HibernateUtils;

public class App 
{
    public static void main( String[] args )
    {
        if (HibernateUtils.abrirConexion()) {
			System.out.println("Conexión correcta");
			HibernateUtils.cerrarConexion();
		} else {
			System.out.println("Error al conectar");
		}
    }
    
	public static void probarConexion() {
		if(HibernateUtils.abrirConexion()) {
			System.out.println("Conexión establecida correctamente");
			HibernateUtils.cerrarConexion();
		} else {
			System.out.println("Fallo al establecer la conexión");
		}
	}
	
	/**
	 * Ejemplo de como acceder a la lista de elementos de una clase
	 * En el ejemplo categories y recorrerla
	 */
	/*public static void listarCategorias() {
		HibernateUtils.abrirConexion();
		HibernateUtils.devolverListaObjetos(Categories.class)
			.forEach(c->System.out.println(c));
		HibernateUtils.cerrarConexion();
	}*/
}
