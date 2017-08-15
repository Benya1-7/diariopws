/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comun.Validaciones;

import comun.BD.Conexion;
import comun.Objets.ObjUsers;
import comun.Recurso.Cifrado;
import comun.Recurso.Cifrado;
import comun.BD.Conexion;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import comun.BD.OperacionBD;
import comun.Objets.ObjUsers;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
/**
 *
 * @author TOSHIBA
 */
public class ValRegistroUser {
         private static Conexion poolDeConexion = null; 
     
    public static ObjUsers validar_Usuario(String cuenta, String correo, String password)
            throws NoSuchAlgorithmException, 
            SQLException{        
        Connection conn = null;
        PreparedStatement stmt = null; 
        ObjUsers usr=new ObjUsers();
        try{
            poolDeConexion=Conexion.getInstance();
            conn = poolDeConexion.conectar();
            String sql = " SELECT idusuario, cuenta, correo, password "
                    + " FROM usuario "
                    + " WHERE cuenta=? OR correo=? OR password=?;";
            
            password=  Cifrado.sha2(password);  
            stmt = conn.prepareStatement(sql);
            stmt.setString (1,cuenta);
            stmt.setString (2,correo);
            stmt.setString (3, password);                        
            ResultSet rs = stmt.executeQuery(); 
            while (rs.next()){  
                cuenta=rs.getString("cuenta"); 
                correo=rs.getString("correo");                
                password = rs.getString("password");
                
                usr.setIdusuario((Long) rs.getLong("idusuario"));
                usr.setCuenta(cuenta);
                usr.setCorreo(correo);
                usr.setPassword(password);
                
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
        return usr;
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
            rs = stmt.executeQuery("SHOW TABLE STATUS WHERE Name = 'usuario'");
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
