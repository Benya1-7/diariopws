/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Usuario;


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
@Path("/rol")
public class Rol {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String list()throws SQLException, Exception {
        
      OperacionBD.iniciaroperacion();
      String sql="SELECT * FROM rol;" ;
      
          
      List<Parametro> parametros2= new  ArrayList<>();
     // parametros2.add(new Parametro(1,"1",Tipo.INTEGER));   
     // parametros2.add(new Parametro(2,"Asesor",Tipo.VARCHAR));
     
      String json = OperacionBD.consulta(sql, parametros2, Formato.JSON);
      parametros2.removeAll(parametros2);
     
      OperacionBD.confirmaroperacion();
      
      return json;
    }//--- fin listarrol  
    
    @Path("/insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    
     public String insert(@FormParam("rol")String rol)
          throws SQLException, Exception {
       OperacionBD.iniciaroperacion();
      String sql="INSERT INTO rol" +
                    "(rol)" + 
                    "VALUES (?);";
      
          
      List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,rol,Tipo.VARCHAR));
     
      String json = OperacionBD.accion(sql, parametros);
      parametros.removeAll(parametros);
      OperacionBD.confirmaroperacion();
   
      
      return json;
     }//Fin Insert
     
     @Path("/update") 
     @POST 
     @Produces(MediaType.APPLICATION_JSON)  
     public String update(@FormParam("idrol")String idrol
                                    ,@FormParam("rol")String rol
                                   )
          throws SQLException, Exception {
       OperacionBD.iniciaroperacion();
      String sql="UPDATE rol SET rol=? "
              + " WHERE rol.idrol=?;";      
        // rol=ucFirst(rol);
      List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,rol,Tipo.VARCHAR));
     parametros.add(new Parametro(2,idrol,Tipo.INTEGER));
     
     
      String json = OperacionBD.accion(sql, parametros);
      parametros.clear();
      OperacionBD.confirmaroperacion();
        return json;
     } //Fin de Metodo update 
     
     @Path("/delete") 
     @POST 
     @Produces(MediaType.APPLICATION_JSON)  
     public String delete(@FormParam("idrol")String idrol
                                     )
          throws SQLException, Exception {
       OperacionBD.iniciaroperacion();
        String sql=" DELETE FROM rol WHERE rol.idrol=?;";      
      
      List<Parametro> parametros= new  ArrayList<>();
      parametros.add(new Parametro(1,idrol,Tipo.INTEGER));
      String json = OperacionBD.accion(sql, parametros);
      parametros.clear();
      OperacionBD.confirmaroperacion();
   
      
      return json;
     } //Fin de Metodo DELETE  
     
     
}
