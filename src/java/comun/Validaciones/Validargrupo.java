/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comun.Validaciones;

import comun.BD.Conexion;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import comun.Objets.ObjGrupo;
import comun.Objets.ObjGrupo;

/**
 *
 * @author TOSHIBA
 */
public class Validargrupo {
             private static Conexion poolDeConexion = null; 
     
    public static ObjGrupo validar_Grupo(String nombregrupo)
            throws NoSuchAlgorithmException, 
            SQLException{        
        Connection conn = null;
        PreparedStatement stmt = null; 
        ObjGrupo gpo=new ObjGrupo();
        try{
            poolDeConexion=Conexion.getInstance();
            conn = poolDeConexion.conectar();
            String sql = " SELECT idgrupo, nombregrupo, usuarioalumno, nombreusuario "
                    + " FROM grupo "
                    + " WHERE nombregrupo=?;";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString (1,nombregrupo);
                                 
            ResultSet rs = stmt.executeQuery(); 
            while (rs.next()){  
                
                nombregrupo=rs.getString("nombregrupo");
                              
                gpo.setIdgrupo((Long) rs.getLong("idgrupo"));
                gpo.setNombregrupo(nombregrupo);
                
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
        return gpo;
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
            rs = stmt.executeQuery("SHOW TABLE STATUS WHERE Name = 'grupo'");
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
