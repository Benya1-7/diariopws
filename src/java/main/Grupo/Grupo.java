/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Grupo;


import main.Publicacion.Publicacion;
import static com.sun.research.ws.wadl.HTTPMethods.POST;
import comun.Recurso.Formato;
import comun.Recurso.Mensaje;
import comun.BD.OperacionBD;
import comun.BD.Parametro;
import comun.Recurso.Tipo;
import comun.Objets.ObjUsers;
import comun.Validaciones.ValRegistroUser;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.text.html.FormSubmitEvent.MethodType.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import static javax.ws.rs.HttpMethod.DELETE;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.apache.catalina.WebResourceRoot.ResourceSetType.POST;
import static org.apache.coyote.http11.Constants.POST;
import static org.eclipse.persistence.sdo.helper.extension.Token.POST;
import org.json.JSONObject;
import comun.Validaciones.Validargrupo;
import comun.Objets.ObjGrupo;

/**
 *
 * @author TOSHIBA
 */
@Path("/grupo")
public class Grupo {
     String respuesta="";
     @Path("/list")
    @GET
    @Produces("application/json;charset=UTF-8")
    public String list(@HeaderParam ("idusuario") String idusuario)throws SQLException, Exception {
        
      OperacionBD.iniciaroperacion();
   
  //   String sql="SELECT grupo.idgrupo, grupo.nombregrupo, grupo.nombreusuario FROM grupo ";
     /*String sql="SELECT grupo.idgrupo, grupo.nombregrupo, "
                   + "grupo.usuarioalumno, grupo.nombreusuario, "
                   + "usuario.idusuario, usuariorol.idrol, "
                   + "usuariorol.idusuario "
                   + "FROM grupo, usuario, usuariorol "
                   + "WHERE grupo.usuarioalumno=usuariorol.idusuario "
                   + "AND usuariorol.idusuario=usuario.idusuario;";*/
    //String sql="select g.idgrupo, g.nombregrupo, gd.idusuario, gd.idrol from grupodetalle gd, grupo g where gd.idusuario=? and g.idgrupo=gd.idgrupo;";   
   String sql="select g.nombreusuario, g.usuarioalumno, g.nombregrupo, g.idgrupo, gd.idusuario, gd.idrol, u.idusuario, ur.idusuario from grupodetalle gd, grupo g, usuario u, usuariorol ur where gd.idusuario=? and g.idgrupo=gd.idgrupo and g.usuarioalumno=u.idusuario and u.idusuario=ur.idusuario;";  
    List<Parametro> parametros= new  ArrayList<>();
      parametros.add(new Parametro(1,idusuario,Tipo.INTEGER));   
      String json = OperacionBD.consulta(sql, parametros, Formato.JSON);
      parametros.removeAll(parametros);
     
      OperacionBD.confirmaroperacion();
      
      return json;
    }//--- fin listar Grupos
    
     @Path("/listxIdusuario")
    @GET
    @Produces("application/json;charset=UTF-8")
    public String listxIdusuario(@HeaderParam("idusuario")String idusuario)throws SQLException, Exception {
        
      OperacionBD.iniciaroperacion();
       String usuarioalumno=idusuario;
  //   String sql="SELECT grupo.idgrupo, grupo.nombregrupo, grupo.nombreusuario FROM grupo ";
   /*"SELECT grupo.idgrupo, grupo.nombregrupo, "
                   + "grupo.usuarioalumno, grupo.nombreusuario, "
                   + "usuario.idusuario, usuariorol.idrol, "
                   + "usuariorol.idusuario "
                   + "FROM grupo, usuario, usuariorol "
                   + "WHERE grupo.usuarioalumno=usuariorol.idusuario "
                   + "AND usuariorol.idusuario=usuario.idusuario;"  */ 
  String sql="SELECT grupo.idgrupo, grupo.nombregrupo, grupo.usuarioalumno, "
                + "grupo.nombreusuario, usuario.idusuario, usuariorol.idrol, usuariorol.idusuario   FROM grupo, usuario, usuariorol "
                   + "WHERE grupo.usuarioalumno=? and grupo.usuarioalumno=usuario.idusuario and usuariorol.idusuario=usuario.idusuario  ;";
     List<Parametro> parametros= new  ArrayList<>();
       parametros.add(new Parametro(1,usuarioalumno,Tipo.INTEGER)); 
      String json = OperacionBD.consulta(sql, parametros, Formato.JSON);
      parametros.removeAll(parametros);
     
      OperacionBD.confirmaroperacion();
      
      return json;
    }//--- fin listar Grupos por Idusuario
    
        
     @Path("/listgpoxid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listgpoxid(@HeaderParam("idgrupo")String idgrupo)throws SQLException, Exception {
        
      OperacionBD.iniciaroperacion();
   
      String sql="SELECT * FROM grupo "
                    + " WHERE grupo.idgrupo=?;";
      
      List<Parametro> parametros= new  ArrayList<>();
       parametros.add(new Parametro(1,idgrupo,Tipo.INTEGER));
       String json = OperacionBD.consulta(sql, parametros,Formato.JSON);
      parametros.clear();
     
      OperacionBD.confirmaroperacion();
      
      return json;
    }//--- fin listar grupo por id
    
    @Path("/create")
    @POST
    @Produces("application/json;charset=UTF-8")
    public Response creategrupo(@FormParam("nombregrupo")String nombregrupo,
                              @FormParam("idusuario")String usuarioalumno,
                              @FormParam("idrol")String idrol,
                              @FormParam("nombreusuario")String nombreusuario
                                       )
                  throws SQLException, Exception {
         ObjGrupo objgpo =new ObjGrupo();
        JSONObject jsoncreategrupo = new JSONObject();  
       
        objgpo=Validargrupo.validar_Grupo(nombregrupo);
         if(objgpo.getNombregrupo()==null){
        try {
          AddGrupo.insertgpo(usuarioalumno, nombregrupo, nombreusuario);
          AddGrupo.insertDetallegpo(usuarioalumno, idrol);
          jsoncreategrupo.put("ok", "Grupo creado con Exito!!"); 
     } 
     catch (Exception ex) {
         Logger.getLogger(Publicacion.class.getName()).log(Level.SEVERE, null, ex);
     }
    }//End if
          else{
          
          jsoncreategrupo.put("Upss!!","Ya Exixte un grupo con el nombre: "+nombregrupo);
             return Response.status(Response.Status.PRECONDITION_FAILED.getStatusCode())
                        .entity(jsoncreategrupo.toString()).build();
               
            }//Fin else 
         
      return Response.status(200).entity(jsoncreategrupo.toString()).build();
    
    }//--- fin create grupo
    
    
       @Path("/delete") 
     @DELETE 
     @Produces(MediaType.APPLICATION_JSON)  
     public String delete(@HeaderParam("idgrupo")String idgrupo
                                     )
          throws SQLException, Exception {
         JSONObject jsonrespuesta = new JSONObject();
       OperacionBD.iniciaroperacion();
        String sql=" DELETE FROM grupo WHERE grupo.idgrupo=?;";      
      
      List<Parametro> parametros= new  ArrayList<>();
      parametros.add(new Parametro(1,idgrupo,Tipo.INTEGER));
      String json = OperacionBD.accion(sql, parametros);
      parametros.clear();
      OperacionBD.confirmaroperacion();
      if(json.equals("1")){
         respuesta=jsonrespuesta.put("Ok","Grupo eliminado con exito!!").toString();
            }
      else{
      respuesta=jsonrespuesta.put("Failed","El grupo no se pudo eliminar!!").toString();
      
      }
   
      return respuesta;
     } //Fin de Metodo DELETE  
    

    
}
