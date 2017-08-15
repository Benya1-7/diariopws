/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comun.BD;

/**
 *
 * @author cardoso
 */
import java.sql.*;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;


public class Conexion implements java.io.Serializable
{
      private DataSource dataSource;
      private static Context ctx;
      private static Conexion conn ;
    /** Constructor default*/
    private  Conexion() 
        throws ServletException, NamingException
    {
          dataSource = null;
          this.dataSource = (DataSource) ctx.lookup("jdbc/diariopedagogico");
    }
  
  
  public static synchronized Conexion getInstance()
  {
      if (conn == null)
      {  
          try
          {
              conn = new Conexion();
          }catch (Exception se)
          {
              System.out.println("Error al crear la conexion: "+ se.getMessage());
          }
      }
       
      return conn;           
  }
  
  public static void setContext (Context context)
  {
      ctx = context;
  }
    
  /*Metodo que crea una nueva conexion usando el controlador JNDI y la base de datos
   *especificada
   @exception SQLException si falla la conexion o ya existe*/ 
  
  public synchronized Connection conectar()
        throws SQLException
  {
     return dataSource.getConnection();
  }
}