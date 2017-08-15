/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comun.Validaciones;

import comun.BD.Conexion;
import comun.BD.OperacionBD;
import comun.BD.Parametro;
import comun.Objets.ObjGpoDetalle;
import comun.Recurso.Formato;
import comun.Recurso.Tipo;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Publicacion.AddPublicacion;
import main.Usuario.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author TOSHIBA
 */
public class ValidarUsersGrupoDetalle {
     private static Conexion poolDeConexion = null; 
     
    public static ObjGpoDetalle V_Grupo_Detalle(String idusuario, String idgrupo)
            throws NoSuchAlgorithmException, 
            SQLException{        
        Connection conn = null;
        PreparedStatement stmt = null; 
        ObjGpoDetalle gpodetalle=new ObjGpoDetalle();
        try{
            poolDeConexion=Conexion.getInstance();
            conn = poolDeConexion.conectar();
            String sql = " SELECT idgrupodetalle, idgrupo, idusuario, idrol "
                    + " FROM grupodetalle "
                    + " WHERE grupodetalle.idusuario=? AND grupodetalle.idgrupo=? ;";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString (1,idusuario);
            stmt.setString (2,idgrupo);
                                 
            ResultSet rs = stmt.executeQuery(); 
            while (rs.next()){  
                
                idusuario=rs.getString("idusuario");
                                             
                gpodetalle.setIdgrupo((Long) rs.getLong("idgrupo"));
                gpodetalle.setIdusuario(Long.parseLong(idusuario));
                
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
        return gpodetalle;
    } //Fin validar user Grupo Detalle
       
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
    
     public static boolean contarusrgrupo(String idgrupo)throws SQLException, Exception {
     try{
         int numuser=0;
         int max=3;
     String usuarios="";
     OperacionBD.iniciaroperacion();
              String sql ="select count(idusuario) from grupodetalle where idgrupo=?; ";    
      List<Parametro> parametros= new  ArrayList<>();
       parametros.add(new Parametro(1, idgrupo, Tipo.INTEGER));
       String json = OperacionBD.consulta(sql, parametros,Formato.JSON);
                  parametros.clear();
                  OperacionBD.confirmaroperacion();
             JSONArray jsonarray = new JSONArray(json);
             for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
               usuarios=(String) jsonobject.get("count(idusuario)");
                 numuser=Integer.valueOf(usuarios);
             } 
             if(numuser==max){
             return true;
             }
                             
               } catch (ParseException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      } 
             
    return false;
    }//--- fin getidlatuser 
    
}
