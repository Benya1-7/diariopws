/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Grupo;

/**
 *
 * @author Ani
 */
import comun.Recurso.Formato;
import comun.Recurso.Mensaje;
import comun.BD.OperacionBD;
import comun.BD.Parametro;
import comun.Recurso.Tipo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import comun.Validaciones.ValidarUsersGrupoDetalle;
import comun.Objets.ObjGpoDetalle;

@Path("/grupodetalle")
public class Grupodetalle {
      String respuesta="";
     
@Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String list()throws SQLException, Exception {
        
      OperacionBD.iniciaroperacion();
   
      String sql="SELECT * FROM grupodetalle;";
      List<Parametro> parametros= new  ArrayList<>();
       
      String json = OperacionBD.consulta(sql, parametros, Formato.JSON);
      parametros.removeAll(parametros);
     
      OperacionBD.confirmaroperacion();
      
      return json;
    }//--- fin listar grupodetalle
    
    @Path("/listusersgpo")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listusersgpo(@HeaderParam("idgrupo")String idgrupo)throws SQLException, Exception {
        
      OperacionBD.iniciaroperacion();
   
      String sql="select  u.idusuario, u.cuenta, gd.idgrupodetalle, gd.idgrupo, gd.idusuario from usuario u, grupodetalle gd where idgrupo=? and u.idusuario=gd.idusuario;";
      List<Parametro> parametros= new  ArrayList<>();
       parametros.add(new Parametro(1,idgrupo,Tipo.INTEGER));
      String json = OperacionBD.consulta(sql, parametros, Formato.JSON);
      parametros.removeAll(parametros);
     
      OperacionBD.confirmaroperacion();
      
      return json;
    }//--- fin listar grupodetalle
    
    @Path("/addparticipante") 
    @POST @Produces({MediaType.APPLICATION_JSON,"application/json;charset=UTF-8"})  
     public String insert(@FormParam("idgrupo")String idgrupo
                         ,@FormParam("idusuario")String idusuario
                          ,@FormParam("idrol")String idrol)
          throws SQLException, Exception {
         ObjGpoDetalle objgpodetalle=new ObjGpoDetalle();
       JSONObject jsonaddparticipante = new JSONObject();  
    
        try{
            try {
                 if(ValidarUsersGrupoDetalle.contarusrgrupo(idgrupo)==false){
                     
                                    
          objgpodetalle=ValidarUsersGrupoDetalle.V_Grupo_Detalle(idusuario, idgrupo);
       if(objgpodetalle.getIdusuario()==null){
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
      respuesta="S";
         
       }//Fin if
       else {
        respuesta="F";
       }
      
        }
                 else{
                 respuesta=jsonaddparticipante.put("lleno","Solo puedes tener a dos colaboradores por grupo" ).toString();
                     
                 }
                 
                 
        }
         catch (SQLException e){
        
         respuesta=jsonaddparticipante.put("Failed","Ocurrio un Error !!!" ).toString()+ e;
        
    }//Fin catch
     } catch (Exception e) {
                respuesta=jsonaddparticipante.put("Lo sentimos","Solo puedes agreagr a dos colaboradores al grupo" ).toString();
            }
         
      return respuesta;
     } //Fin de Metodo Insert grupodetalle  
     
     @Path("/update") 
     @PUT @Produces(MediaType.APPLICATION_JSON)  
     public String update(@FormParam("idgrupodetalle")String idgrupodetalle
                                    ,@FormParam("idgrupo")String idgrupo
                                    ,@FormParam("idusuario")String idusuario
                                    ,@FormParam("idrol")String idrol)
          throws SQLException, Exception {
           JSONObject jsonupdate = new JSONObject();
         try{
       OperacionBD.iniciaroperacion();
      String sql="UPDATE grupodetalle SET idgrupo=?, idusuario=?, idrol=?"
              + " WHERE grupodetalle.idgrupodetalle=? "; 
      List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,idgrupo,Tipo.INTEGER));
     parametros.add(new Parametro(2,idusuario,Tipo.INTEGER)); 
     parametros.add(new Parametro(3,idrol,Tipo.INTEGER));
     parametros.add(new Parametro(4,idgrupodetalle,Tipo.INTEGER));
      String json = OperacionBD.accion(sql, parametros);
      parametros.clear();
      OperacionBD.confirmaroperacion();
       respuesta=json.toString();
         if (json.contains("1")) {
        respuesta=jsonupdate.put("Ok","Actualización satisfactoria!!!" ).toString();
         
         }
        
        }
         catch (SQLException e){
        
         respuesta=jsonupdate.put("Failed","Ocurrio un Error !!!" ).toString();
        
    }//Fin catch
      
     return respuesta;
     } //Fin de Metodo update grupodetalle 
     
     @Path("/delete") @POST @Produces(MediaType.APPLICATION_JSON)  
     public String delete(@FormParam("idgrupodetalle")String idgrupodetalle,
                          @FormParam("idusuario")String idusuario
                                     )
          throws SQLException, Exception {
       OperacionBD.iniciaroperacion();
        String sql=" DELETE FROM grupodetalle WHERE grupodetalle.idgrupo=? AND grupodetalle.idusuario=?;";      
      
      List<Parametro> parametros= new  ArrayList<>();
      parametros.add(new Parametro(1,idgrupodetalle,Tipo.INTEGER));
      parametros.add(new Parametro(2,idusuario,Tipo.INTEGER));
      
      String json = OperacionBD.accion(sql, parametros);
      parametros.clear();
      OperacionBD.confirmaroperacion();
   
      
      return json;
     } //Fin de Metodo DELETE
}
