/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Usuario;

//import org.apache.tomcat.jni.File;

import comun.BD.OperacionBD;
import comun.BD.Parametro;
import comun.Multimedia.SaveDownloadFile;
import comun.Recurso.Formato;
import comun.Recurso.Tipo;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Publicacion.AddPublicacion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author Benya
 */
public class EliminarFile {
    static boolean deletefile(String file){
     String rutaarchivo = SaveDownloadFile.RUTA+file;
     File fichero = new File(rutaarchivo);
    if(fichero.delete()){
        
    
     }      
     return true;

} 
 static boolean delete_imageperfil(String idusuario)throws SQLException, Exception {
     try{ 
     String foto="";
      OperacionBD.iniciaroperacion();
              String sql ="SELECT u.foto from usuario u WHERE u.idusuario=?; ";    
      List<Parametro> parametros= new  ArrayList<>();
       parametros.add(new Parametro(1, idusuario, Tipo.INTEGER));
       String json = OperacionBD.consulta(sql, parametros,Formato.JSON);
                  parametros.clear();
                  OperacionBD.confirmaroperacion();
             JSONArray jsonarray = new JSONArray(json);
             for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
               foto=(String) jsonobject.get("foto");
                 deletefile(foto);
             } 
               } catch (ParseException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      } 
             
    return true ;
    }//--- fin getidlatuser 
  static boolean delete_imageportada(String idusuario)throws SQLException, Exception {
     try{ 
     String foto="";
      OperacionBD.iniciaroperacion();
              String sql ="SELECT u.fportada from usuario u WHERE u.idusuario=?; ";    
      List<Parametro> parametros= new  ArrayList<>();
       parametros.add(new Parametro(1, idusuario, Tipo.INTEGER));
       String json = OperacionBD.consulta(sql, parametros,Formato.JSON);
                  parametros.clear();
                  OperacionBD.confirmaroperacion();
             JSONArray jsonarray = new JSONArray(json);
             for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
               foto=(String) jsonobject.get("fportada");
                 deletefile(foto);
             } 
               } catch (ParseException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      } 
             
    return true ;
    }//--- fin getidlatuser 
    
 /*
   List<Parametro> parametros= new  ArrayList<>();
      parametros.add(new Parametro(1, idpublicacion, Tipo.INTEGER));
       
      String json = OperacionBD.consulta(sql, parametros, Formato.JSON);
      parametros.removeAll(parametros);
     
      OperacionBD.confirmaroperacion();
 */
}
