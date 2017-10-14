/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Usuario;

import comun.Recurso.Cifrado;
import comun.Recurso.Formato;
import comun.Recurso.Mensaje;
import comun.BD.OperacionBD;
import comun.BD.Parametro;
import comun.Recurso.Tipo;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import main.Publicacion.AddPublicacion;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author TOSHIBA
 */
public class CambiarPassw {
      /*    public static boolean cambiarpassw(String correo, String correo) 
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
  } //End update token*/
}
