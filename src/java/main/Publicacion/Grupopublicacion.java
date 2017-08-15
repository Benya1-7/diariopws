/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Publicacion;

/**
 *
 * @author Ani
 */
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

@Path("/grupopublicacion")
public class Grupopublicacion {
@Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String list()throws SQLException, Exception {
        
      OperacionBD.iniciaroperacion();
   
      String sql="SELECT * FROM grupopublicacion ";
      List<Parametro> parametros= new  ArrayList<>();
       
      String json = OperacionBD.consulta(sql, parametros, Formato.JSON);
      parametros.removeAll(parametros);     
      OperacionBD.confirmaroperacion();
      
      return json;
    }//--- fin listar grupopublicacion
    
    @Path("/insertar")     @POST @Produces(MediaType.APPLICATION_JSON)  
     public String insert(@FormParam("idpublicacion")String idpublicacion
                                  ,@FormParam("idgrupo")String idgrupo)
          throws SQLException, Exception {
       OperacionBD.iniciaroperacion();
      String sql="INSERT INTO grupopublicacion(idpublicacion, idgrupo)"
                  + "VALUES (?,?,?)";    
      List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,idpublicacion,Tipo.INTEGER));
     parametros.add(new Parametro(2,idgrupo,Tipo.INTEGER)); 
     
      String json = OperacionBD.accion(sql, parametros);
      parametros.clear();
      OperacionBD.confirmaroperacion();
   
      
      return json;
     } //Fin de Metodo Insert grupodetalle  
     
     @Path("/update") @POST @Produces(MediaType.APPLICATION_JSON)  
     public String update(@FormParam("idgrupopublicacion")String idgrupopublicacion
                                    ,@FormParam("idpublicacion")String idpublicacion
                                    ,@FormParam("idgrupo")String idgrupo)
          throws SQLException, Exception {
       OperacionBD.iniciaroperacion();
      String sql="UPDATE grupopublicacion SET idpublicacion=?, idgrupo=?"
              + " WHERE grupopublicacion.idgrupopublicacion=? "; 
      List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,idgrupopublicacion,Tipo.INTEGER));
     parametros.add(new Parametro(2,idpublicacion,Tipo.INTEGER)); 
     parametros.add(new Parametro(3,idgrupo,Tipo.INTEGER));
     
      String json = OperacionBD.accion(sql, parametros);
      parametros.clear();
      OperacionBD.confirmaroperacion();
     return json;
     } //Fin de Metodo update grupopublicacion
     
     @Path("/delete") @POST @Produces(MediaType.APPLICATION_JSON)  
     public String delete(@FormParam("idgrupopublicacion")String idgrupopublicacion
                                     )
          throws SQLException, Exception {
       OperacionBD.iniciaroperacion();
        String sql=" DELETE FROM grupopublicacion WHERE grupopublicacion.idgrupopublicacion=? ";      
      
      List<Parametro> parametros= new  ArrayList<>();
      parametros.add(new Parametro(1,idgrupopublicacion,Tipo.INTEGER));
      String json = OperacionBD.accion(sql, parametros);
      parametros.clear();
      OperacionBD.confirmaroperacion();
   
      
      return json;
     } //Fin de Metodo DELETE
}
