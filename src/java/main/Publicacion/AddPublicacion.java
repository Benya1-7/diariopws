/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Publicacion;

import comun.Recurso.Formato;
import comun.BD.OperacionBD;
import comun.BD.Parametro;
import comun.Multimedia.SaveDownloadFile;
import comun.Recurso.Tipo;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author TOSHIBA
 */
public class AddPublicacion {
      //String sentimiento, String evaluacion, String analisis, String conclusion, String planaccion
  static boolean addpublicacion(String idusuario, String titulo, String sentimiento, String evaluacion, String analisis, String conclusion, String planaccion, String observaciones) 
                throws SQLException   {
           String respuesta="";
       
      try {
           String padre ="0";
            Timestamp currentfecha = new Timestamp(System.currentTimeMillis());
         String fecha = currentfecha.toString();
          OperacionBD.iniciaroperacion();
          String sql="INSERT INTO publicacion(idusuario, fecha, titulo, sentimiento, evaluacion, analisis, conclusion, planaccion, observaciones, padre)"
                        + "VALUES (?,?,?,?,?);";
          List<Parametro> parametros= new  ArrayList<>();
          parametros.add(new Parametro(1,idusuario,Tipo.INTEGER));
          parametros.add(new Parametro(2,fecha,Tipo.TIMESTAMP));
          parametros.add(new Parametro(3,titulo,Tipo.VARCHAR));
          parametros.add(new Parametro(4,sentimiento,Tipo.VARCHAR));
          parametros.add(new Parametro(5,evaluacion,Tipo.VARCHAR));
          parametros.add(new Parametro(6,analisis,Tipo.VARCHAR));
          parametros.add(new Parametro(7,conclusion,Tipo.VARCHAR));
          parametros.add(new Parametro(8,planaccion,Tipo.VARCHAR));
          parametros.add(new Parametro(9,observaciones,Tipo.VARCHAR));
          parametros.add(new Parametro(10,padre,Tipo.INTEGER));
          String json = OperacionBD.accion(sql,parametros);
            parametros.clear();
            OperacionBD.confirmaroperacion();
           respuesta=json.toString();
          
      } catch (ParseException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      } catch (JSONException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      }
      return true;
  }
  
  static boolean insertDpublicacion(String idpublicacion, String ruta, String tipo, String formato, String descripcion) 
                throws SQLException   {
      
      String respuesta="";
       
      try {
          idpublicacion = listidpublicacion().toString();
      } catch (Exception ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      }
      try {
          OperacionBD.iniciaroperacion();
         //  String ruta=SaveDownloadFile.RUTA;  //// comentado 10-08-17
         String sql="INSERT INTO detallepublicacion(idpublicacion, ruta, tipo, formato, descripcion)"  
                                + "VALUES (?,?,?,?,?);"; 
                 List<Parametro> parametros= new  ArrayList<>();
      parametros.add(new Parametro(1,idpublicacion,Tipo.INTEGER));
      parametros.add(new Parametro(2,ruta,Tipo.VARCHAR));
      parametros.add(new Parametro(3,tipo,Tipo.VARCHAR));
      parametros.add(new Parametro(4,formato,Tipo.VARCHAR));
      parametros.add(new Parametro(5,descripcion,Tipo.VARCHAR));
      String json = OperacionBD.accion(sql, parametros);
       parametros.clear();
            OperacionBD.confirmaroperacion();
           respuesta=json.toString();
      } catch (ParseException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      } catch (JSONException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      }
      return true;
  }
   static boolean grupopublicacion(String idgrupo) 
                throws SQLException   {
        String idpublicacion="";
      try {
          idpublicacion = listidpublicacion().toString();
      } catch (Exception ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      }
      try {
          String respuesta="";
          OperacionBD.iniciaroperacion();
          String sql ="INSERT INTO grupopublicacion(idpublicacion, idgrupo) VALUES(?,?);";
          List<Parametro> parametros= new  ArrayList<>();
          parametros.add(new Parametro(1,idpublicacion,Tipo.INTEGER));
          parametros.add(new Parametro(2,idgrupo,Tipo.INTEGER));
          String json = OperacionBD.accion(sql, parametros);
          parametros.clear();
          
          OperacionBD.confirmaroperacion();
          respuesta=json.toString();
          return true;
      } catch (ParseException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      } catch (JSONException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      }
      return true;
   }
   
       private static String listidpublicacion()throws SQLException, Exception {
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
    
  return ids;
    }//--- fin listidpublicacion
       
    
    
}
