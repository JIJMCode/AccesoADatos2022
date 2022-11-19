package com.jose.ejercicio_2_15_2022_Utilidades;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.jose.ejercicio_2_15_2022_Entidades.Joke;
import com.jose.ejercicio_2_15_2022_Entidades.NewJoke;
import com.jose.ejercicio_2_15_2022_Utilidades.Literals;

public class utilsPostgre {
	private static final String url = "jdbc:postgresql://localhost:5432/swapi";
	private static final String usuario = "postgres";
	private static final String password = "postgre";

	private static Connection con = null;
	private static Statement statement = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static CallableStatement cs = null;
	
	public static void conexion() {
		try {
			con = DriverManager.getConnection(url, usuario, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
//	
//	public static void desconexion() {
//		try {
//			//con.close();
//			DbUtils.closeQuietly(rs);
//			DbUtils.closeQuietly(con);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void ejecutarConsultaBdd(String consulta) {
    	Connection con = null;
    	Statement statement = null;
    	ResultSet rs = null;
    	try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/jokes";
            String usuario = "postgres";
            String password = "postgre";
            con = DriverManager.getConnection(url, usuario, password);
            statement = con.createStatement();

            //String sentenciaSQL = consulta;
            rs = statement.executeQuery(consulta);
        }
    	catch(Exception e)
    	{
        	System.out.println(e.getMessage());
        }
    	finally 
    	{
    		DbUtils.closeQuietly(rs);
    		DbUtils.closeQuietly(con);
    		DbUtils.closeQuietly(statement);
    	}
    }
	
	static String insertNewFlags = "";
	
    public static void guardarChiste(NewJoke newJoke) {
    	try {
        	String insertNewJoke = String.format(Literals.scriptInsertNewJoke,newJoke.getCategory(),newJoke.getType(),newJoke.getJoke(),
        										newJoke.getSetup(),newJoke.getDelivery(),newJoke.getLang());       	
        	
        	ejecutarConsultaBdd(insertNewJoke);
        	int newJokeId = JdbcUtils.devolverId(Literals.scriptNewJokeId);
        	if (newJokeId>=0) {
            	newJoke.getFlags().forEach(e -> {insertNewFlags += String.format(Literals.scriptInsertJokesFlags, newJokeId, e.getName());});
            	ejecutarConsultaBdd(insertNewFlags);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			JdbcUtils.desconexion();
		}	
    }
	
	public static int cargarBdd(String consulta) {
    	Connection con = null;
    	Statement statement = null;
    	int rs = 0;
    	try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/jokes";
            String usuario = "postgres";
            String password = "postgre";
            con = DriverManager.getConnection(url, usuario, password);
            statement = con.createStatement();

            //String sentenciaSQL = consulta;
            rs = statement.executeUpdate(consulta);
        }
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
        }
    	finally 
    	{
    		DbUtils.closeQuietly(con);
    		DbUtils.closeQuietly(statement);
    	}
    	return rs;
    }
    
//	public static List<FilmsByName> devolverSelectFilms(String sql) {
//		conexion();
//		try {
//			statement = con.createStatement();
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//		ResultSet rs = null;
//		List<FilmsByName> resultado = new ArrayList<FilmsByName>();
//		try {
//			rs = statement.executeQuery(sql);
//			while (rs.next()) {
//				resultado.add(new FilmsByName(rs.getInt("codigo"), rs.getString("title"), rs.getString("director")));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//    	finally 
//    	{
//    		DbUtils.closeQuietly(rs);
//    		DbUtils.closeQuietly(con);
//    		DbUtils.closeQuietly(statement);
//    	}
//		return resultado;
//	}
    
	/**
	 * Método genérico que llama a un CallableStatement y devuelve el resultado que nos envía la base de datos en un ResultSet
	 * @param metodo Nombre del procedimiento almacenado en la base de datos junto con sus parámetros indicados por '?'
	 * @param peliculas_id Lista con los parámetros a cambiar por las '?'
	 * @return ResultSet que nos devuelve el Procedimiento almacenado de la Base de datos
	 */
//	public static ResultSet ejecutarCallableStatement(String metodo, List<FilmsByName> peliculas_id) {
//		conexion();
//		try {
//			cs = con.prepareCall("{call " + metodo + "}"); 													
//			for(int i=1;i<=StringUtils.countMatches(metodo, '?');i++) {
//				int prueba = peliculas_id.get(i-1).getCodigo();
//				cs.setInt(i, peliculas_id.get(i-1).getCodigo());
//			}			
//			return cs.executeQuery();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	public static ResultSet ejecutarCSpeopleSinStarships(String metodo) {
		conexion();
		try {
			cs = con.prepareCall("{call " + metodo + "}"); 													
			return cs.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
//    
//    public static List<Integer> obtenerListaIdBdd(String consulta) {
//    	List<Integer> lista = new ArrayList<Integer>();
//    	try {
//            conexion();
//    		Class.forName("org.postgresql.Driver");
//
//            Statement statement = con.createStatement();
//            
//            String sentenciaSQL = consulta;
//            rs = statement.executeQuery(sentenciaSQL);
//            
//            while (rs.next()) {
//            	lista.add(rs.getInt("codigo"));
//            }
//            rs.close();
//            con.close();
//        }catch(Exception e) {System.out.println(e.getMessage());}
//    	
//    	return lista;
//    }
    
//    public static Integer obtenerIdBdd(String consulta) {
//    	Integer id = -1;
//    	try {
//            Class.forName("org.postgresql.Driver");
////            String url = "jdbc:postgresql://localhost:5432/swapi";
////            String usuario = "postgres";
////            String password = "postgre";
//            Connection con = DriverManager.getConnection(url, usuario, password);
//            Statement statement = con.createStatement();
//
//            String sentenciaSQL = consulta;
//            ResultSet rs = statement.executeQuery(sentenciaSQL);
//            
//            while (rs.next()) {
//            	id = rs.getInt("id");
//            }
//            rs.close();
//            con.close();
//        }catch(Exception e) {System.out.println(e.getMessage());}
//    	
//    	return id;
//    }
  
    public static void vaciarBdd() {
    	try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/jokes";
            String usuario = "postgres";
            String password = "postgre";
            Connection con = DriverManager.getConnection(url, usuario, password);
            Statement statement = con.createStatement();
            
            ResultSet rs = statement.executeQuery(Literals.vaciarBddSQL);
            //ResultSet rs = null;
            
            System.out.println(Literals.bdd_empty);
            rs.close();
            con.close();
        }catch(Exception e) {System.out.println(e.getMessage());}
    }
    
}
