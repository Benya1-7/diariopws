/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Publicacion;

import comun.Recurso.Formato;
import comun.BD.OperacionBD;
import comun.BD.Parametro;
import comun.Recurso.Tipo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author TOSHIBA
 */
@Path("/detallepublicacion")
public class Detallepublicacion {
        @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String list()throws SQLException, Exception {
        
      OperacionBD.iniciaroperacion();
      String sql="SELECT * FROM detallepublicacion;";
      
          
      List<Parametro> parametros2= new  ArrayList<>();
      
      String json = OperacionBD.consulta(sql, parametros2, Formato.JSON);
      parametros2.removeAll(parametros2);
     
      OperacionBD.confirmaroperacion();
      
      return json;
    }//--- fin listarrol  
    
    @Path("/insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    
     public String insert(@FormParam("idpublicacion")String idpublicacion
                          ,@FormParam("ruta")String ruta
                          ,@FormParam("tipo")String tipo
                          ,@FormParam("formato")String formato
                          ,@FormParam("descripcion")String descripcion
     )
          throws SQLException, Exception {
       OperacionBD.iniciaroperacion();
      String sql="INSERT INTO detallepublicacion" +
                    "(idpublicacion, ruta, tipo, formato, descripcion);" + 
                    "VALUES (?,?,?,?,?);";
      
          
      List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,idpublicacion,Tipo.VARCHAR));
     parametros.add(new Parametro(2,ruta,Tipo.VARCHAR));
     parametros.add(new Parametro(3,tipo,Tipo.VARCHAR));
     parametros.add(new Parametro(4,formato,Tipo.VARCHAR));
     parametros.add(new Parametro(5,descripcion,Tipo.VARCHAR));
     
      //String json = OperacionBD.accion(sql, parametros, Formato.JSON);
      String json = OperacionBD.accion(sql, parametros);
      parametros.clear();
      OperacionBD.confirmaroperacion();
   
        System.out.println("ok"+json);
      return json;
     }//Fin Insert
     
     @Path("/update") 
     @POST 
     @Produces(MediaType.APPLICATION_JSON)  
     public String update(@FormParam("iddetallepublicacion")String iddetallepublicacion
                                    ,@FormParam("idpublicacion")String 	idpublicacion
                                    ,@FormParam("ruta")String ruta
                                    ,@FormParam("tipo")String tipo
                                    ,@FormParam("formato")String formato
                                    ,@FormParam("descripcion")String descripcion)
          throws SQLException, Exception {
       OperacionBD.iniciaroperacion();
      String sql="UPDATE detallepublicacion SET idpublicacion=?, ruta=?, tipo=?, formato=?, descripcion=?"
              + " WHERE detallepublicacion.iddetallepublicacion=? ";      
       
      List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,iddetallepublicacion,Tipo.INTEGER));
     parametros.add(new Parametro(2,idpublicacion,Tipo.INTEGER)); 
     parametros.add(new Parametro(3,ruta,Tipo.VARCHAR));
     parametros.add(new Parametro(4,tipo,Tipo.VARCHAR));
     parametros.add(new Parametro(5,formato,Tipo.VARCHAR));
     parametros.add(new Parametro(6,descripcion,Tipo.VARCHAR));
     
      String json = OperacionBD.accion(sql, parametros);
      parametros.clear();
      OperacionBD.confirmaroperacion();
   
      
      return json;
     } //Fin de Metodo update usuario 
     
     @Path("/delete") 
     @POST 
     @Produces(MediaType.APPLICATION_JSON)  
     public String delete(@FormParam("iddetallepublicacion")String iddetallepublicacion
                                     )
          throws SQLException, Exception {
       OperacionBD.iniciaroperacion();
        String sql=" DELETE FROM detallepublicacion WHERE detallepublicacion . iddetallepublicacion=? ";      
      
      List<Parametro> parametros= new  ArrayList<>();
      parametros.add(new Parametro(1,iddetallepublicacion,Tipo.INTEGER));
      String json = OperacionBD.accion(sql, parametros);
      parametros.clear();
      OperacionBD.confirmaroperacion();
   
      
      return json;
     } //Fin de Metodo DELETE  
    
     
     
    
    
}
