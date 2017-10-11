/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Usuario;

import comun.BD.OperacionBD;
import comun.BD.Parametro;
import comun.Recurso.Tipo;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Publicacion.AddPublicacion;
import org.json.JSONException;

/**
 *
 * @author TOSHIBA
 */
public class UpdateToken {
        public static boolean updatetoken(String token, String correo) 
                throws SQLException   {
           String respuesta="";
           
       
      try {
         OperacionBD.iniciaroperacion();
         
   
      String sql="UPDATE usuario SET token=? WHERE usuario.correo=?;";
      List<Parametro> parametros= new  ArrayList<>();
       parametros.add(new Parametro(1,token,Tipo.VARCHAR));
       parametros.add(new Parametro(2,correo,Tipo.VARCHAR));   
      
       String json = OperacionBD.accion(sql, parametros);
       parametros.clear();
     OperacionBD.confirmaroperacion();    
          
      } catch (ParseException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      } catch (JSONException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      }
       return true;
  } //End update token
    
}
