/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comun.Validaciones;

import comun.BD.Conexion;
import comun.Objets.ObjPublicacion;
import comun.Objets.ObjUsers;
import comun.Recurso.Cifrado;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author TOSHIBA
 */
public class ValUpdatePublicacion {
             private static Conexion poolDeConexion = null; 
     
    public static ObjPublicacion verificarpublicacion(String idpublicacion, String idusuario)
            throws NoSuchAlgorithmException, 
            SQLException{        
        Connection conn = null;
        PreparedStatement stmt = null; 
        ObjPublicacion publicacion=new ObjPublicacion();
        try{
            poolDeConexion=Conexion.getInstance();
            conn = poolDeConexion.conectar();
            String sql = " SELECT p.idpublicacion, p.idusuario"
                    + " FROM publicacion p"
                    + " WHERE p.idpublicacion=? && p.idusuario=?;";
            
           
            stmt = conn.prepareStatement(sql);
            stmt.setString (1,idpublicacion);
            stmt.setString (2,idusuario);
            ResultSet rs = stmt.executeQuery(); 
            while (rs.next()){  
                idpublicacion=rs.getString("idpublicacion"); 
                idusuario=rs.getString("idusuario");                
               
                
                publicacion.setIdpublicacion((Long) rs.getLong("idpublicacion"));
                publicacion.setIdusuario(Long.valueOf(idusuario));
               
                
                
            }
            
            rs.close(); 
        }
        catch (SQLException e) {throw e;}
        finally{
            try {
                if (conn!= null && !conn.isClosed()) {
                    if (stmt!=null )stmt.close();  
                    conn.close();
                 }                
            } 
            catch(SQLException e) {
            
                System.out.println("Ocurrió un error al cerrar la conexión de oracle:"+e.getMessage());
            }
        }
        return publicacion;
    } //Fin validar user
       
     public static Long getNextId() throws SQLException{
        Connection conn = null;
        PreparedStatement pstIns = null; 
        ResultSet rs = null;
        String sql="";
        Long nextId= new  Long(0);
         try{
          poolDeConexion=Conexion.getInstance();
          conn=poolDeConexion.conectar();
            
           Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SHOW TABLE STATUS WHERE Name = 'publicacion'");
            rs.next();
            nextId = Long.parseLong(rs.getString("Auto_increment"));
          
        } catch (SQLException esql){System.out.println("ERROR: " + esql.getMessage());}

        finally{ try {
                if (conn!= null && !conn.isClosed()) {
                    if (pstIns!=null) pstIns.close(); 
                    conn.close();
                 }
            } 
            catch(SQLException e) {
            
                System.out.println("Ocurrió un error al cerrar la conexión de oracle:"+e.getMessage());
            }
      
        }
        return nextId;
    }//--- fin get next id
    
  
    
    
}
