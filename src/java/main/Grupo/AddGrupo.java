/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Grupo;

import main.Publicacion.AddPublicacion;
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
import org.json.JSONException;
import comun.BD.GetIDs;

/**
 *
 * @author TOSHIBA
 */
public class AddGrupo {
    static boolean insertgpo(String usuarioalumno, String nombregrupo, String nombreusuario) 
                throws SQLException   {
           String respuesta="";
       
      try {
         OperacionBD.iniciaroperacion();
   
      String sql="INSERT INTO grupo( nombregrupo, usuarioalumno, nombreusuario, fecha, vigenciahasta )"
                    + "VALUES (?,?,?,?,?);";
      // String sql ="UPDATE grupo SET nombregrupo=?, usuarioalumno=?, fecha=?, vigenciahasta=? WHERE grupo.idgrupo=? ;";
                Timestamp currentfecha = new Timestamp(System.currentTimeMillis());
                Timestamp currentfecha1 = new Timestamp(System.currentTimeMillis());
           String fecha = currentfecha.toString();
           String vigenciahasta=currentfecha1.toString();
          // String idgrupo="3";
      List<Parametro> parametros= new  ArrayList<>();
       parametros.add(new Parametro(1,nombregrupo,Tipo.VARCHAR));
       parametros.add(new Parametro(2,usuarioalumno,Tipo.INTEGER));
       parametros.add(new Parametro(3,nombreusuario,Tipo.VARCHAR));       
       parametros.add(new Parametro(4,fecha,Tipo.TIMESTAMP));
       parametros.add(new Parametro(5,vigenciahasta,Tipo.TIMESTAMP));
       String json = OperacionBD.accion(sql, parametros);
       parametros.clear();
     OperacionBD.confirmaroperacion();    
          
      } catch (ParseException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      } catch (JSONException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      }
      return true;
  } //End insertgpo
    
      static boolean insertDetallegpo(String usuarioalumno, String idrol) 
                throws SQLException   {
           String respuesta="";
          String idusuario= usuarioalumno;
          String idgrupo;
        try {
            idgrupo = GetIDs.getLastidgrupo().toString();
        
       
      try {
      OperacionBD.iniciaroperacion();
      String sql="INSERT INTO grupodetalle(idgrupo, idusuario, idrol)"
                  + "VALUES (?,?,?)";    
      List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,idgrupo,Tipo.INTEGER));
     parametros.add(new Parametro(2,idusuario,Tipo.INTEGER)); 
     parametros.add(new Parametro(3,idrol,Tipo.INTEGER));
     
      String json = OperacionBD.accion(sql, parametros);
      
      parametros.clear();
      OperacionBD.confirmaroperacion();
      respuesta=json.toString();
      } catch (ParseException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      } catch (JSONException ex) {
          Logger.getLogger(AddPublicacion.class.getName()).log(Level.SEVERE, null, ex);
      }
      } catch (Exception ex) {
            Logger.getLogger(AddGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
      return true;
  } //End insertDetallegpo
   
  
      
    
    
    
}
