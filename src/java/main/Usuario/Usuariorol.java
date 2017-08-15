/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

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
 * @author Ani
 */
@Path("/usuariorol")
public class Usuariorol {
    
 @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String list()throws SQLException, Exception {
        
      OperacionBD.iniciaroperacion();
   
      String sql="SELECT * FROM usuariorol ";
      List<Parametro> parametros= new  ArrayList<>();
       
      String json = OperacionBD.consulta(sql, parametros, Formato.JSON);
      parametros.removeAll(parametros);     
      OperacionBD.confirmaroperacion();
      
      return json;
    }//--- fin listar grupopublicacion
    
    @Path("/insertar")     @POST @Produces(MediaType.APPLICATION_JSON)  
     public String insert(@FormParam("idrol")String idrol
                                  ,@FormParam("idusuario")String idusuario
                                  ,@FormParam("estatus")String estatus)
          throws SQLException, Exception {
       OperacionBD.iniciaroperacion();
      String sql="INSERT INTO usuariorol(idrol, idusuario, estatus)"
                  + "VALUES (?,?,?)";    
      List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,idrol,Tipo.INTEGER));
     parametros.add(new Parametro(2,idusuario,Tipo.INTEGER)); 
     parametros.add(new Parametro(2,estatus,Tipo.TINYINT)); 
     
      String json = OperacionBD.accion(sql, parametros);
      parametros.clear();
      OperacionBD.confirmaroperacion();
   
      
      return json;
     } //Fin de Metodo Insert grupodetalle   
}
