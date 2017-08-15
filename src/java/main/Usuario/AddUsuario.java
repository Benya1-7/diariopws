/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Usuario;

import main.Publicacion.AddPublicacion;
import comun.Recurso.Cifrado;
import comun.BD.OperacionBD;
import comun.BD.Parametro;
import comun.Recurso.Tipo;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import comun.BD.GetIDs;

/**
 *
 * @author TOSHIBA
 */
public class AddUsuario {
      
    
        static boolean insertusuario(String nombre, String apellidos, String genero, 
                      String cuenta, String password, String vigencia, String correo, 
                      String telefono, String institucion, String grupo, String estado, String foto) 
               throws NoSuchAlgorithmException, SQLException, ParseException, JSONException{
           String respuesta="";
       
      try {
         OperacionBD.iniciaroperacion();
           
      String sql="INSERT INTO usuario(nombre, apellidos, genero, cuenta, password, vigencia, correo, telefono"
                  + ", institucion, grupo, estado, foto)"  
                  + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";   
      password=Cifrado.sha2(password);
     
      List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,nombre,Tipo.VARCHAR));
     parametros.add(new Parametro(2,apellidos,Tipo.VARCHAR)); 
     parametros.add(new Parametro(3,genero,Tipo.VARCHAR));
     parametros.add(new Parametro(4,cuenta,Tipo.VARCHAR));
     parametros.add(new Parametro(5,password,Tipo.VARCHAR));
     parametros.add(new Parametro(6,vigencia,Tipo.DATE));
     parametros.add(new Parametro(7,correo,Tipo.VARCHAR));
     parametros.add(new Parametro(8,telefono,Tipo.VARCHAR));
     parametros.add(new Parametro(9,institucion,Tipo.VARCHAR));
     parametros.add(new Parametro(10,grupo,Tipo.VARCHAR));
     parametros.add(new Parametro(11,estado,Tipo.VARCHAR));
     parametros.add(new Parametro(12,foto,Tipo.VARCHAR));
     String json = OperacionBD.accion(sql,parametros);
     parametros.clear();
      
      OperacionBD.confirmaroperacion(); 
          
      } catch (ParseException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      } catch (JSONException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      }
      return true;
  } //End insertusuario
       
    static boolean addusuariorol() 
               throws NoSuchAlgorithmException, SQLException, ParseException, JSONException{
        String estatus="1";   
        String respuesta="";
        String idrol="1";
            try {
               String idusuario= GetIDs.getidlatuser();
        
       
      try {
         OperacionBD.iniciaroperacion();
           
      String sql="INSERT INTO usuariorol(idrol, idusuario, estatus)"  
                  + "VALUES (?,?,?);";   
     
     
      List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,idrol,Tipo.VARCHAR));
     parametros.add(new Parametro(2,idusuario,Tipo.VARCHAR)); 
     parametros.add(new Parametro(3,estatus,Tipo.VARCHAR));
    
     String json = OperacionBD.accion(sql,parametros);
     parametros.clear();
      
      OperacionBD.confirmaroperacion(); 
          
      } catch (ParseException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      
      catch (JSONException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      }
      } catch (Exception ex) {
                Logger.getLogger(AddUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
      return true;
  } //End insertusuario
        
        
        
}
