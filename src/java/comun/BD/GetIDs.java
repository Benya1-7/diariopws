/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comun.BD;

import comun.Recurso.Formato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import comun.BD.OperacionBD;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author TOSHIBA
 */
public class GetIDs {
      
    public String listidpublicacion()throws SQLException, Exception {
       String ids="";
      OperacionBD.iniciaroperacion();
              String sql ="SELECT MAX(idpublicacion) AS id FROM publicacion;";    
      List<Parametro> parametros= new  ArrayList<>();
       String json = OperacionBD.consulta(sql, parametros,Formato.JSON);
                  parametros.removeAll(parametros);     
                  OperacionBD.confirmaroperacion();
             JSONArray jsonarray = new JSONArray(json);
             for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
               ids=(String) jsonobject.get("id");
             }      
    
      return ids ;
    }//--- fin listar usuarios
    
    public static String getidlatuser()throws SQLException, Exception {
       String idusr="";
      OperacionBD.iniciaroperacion();
              String sql ="SELECT MAX(idusuario) AS idusr FROM usuario;";    
      List<Parametro> parametros= new  ArrayList<>();
       String json = OperacionBD.consulta(sql, parametros,Formato.JSON);
                  parametros.clear();
                  OperacionBD.confirmaroperacion();
             JSONArray jsonarray = new JSONArray(json);
             for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
               idusr=(String) jsonobject.get("idusr");
             }      
    
      return idusr ;
    }//--- fin getidlatuser
    
        public static String getLastidgrupo()throws SQLException, Exception {
       String idusr="";
      OperacionBD.iniciaroperacion();
              String sql ="SELECT MAX(idgrupo) AS idgrupo FROM grupo;";    
      List<Parametro> parametros= new  ArrayList<>();
       String json = OperacionBD.consulta(sql, parametros,Formato.JSON);
                  parametros.clear();
                  OperacionBD.confirmaroperacion();
             JSONArray jsonarray = new JSONArray(json);
             for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
               idusr=(String) jsonobject.get("idgrupo");
             }      
    
      return idusr ;
    }//--- fin getidlatuser
      
}
