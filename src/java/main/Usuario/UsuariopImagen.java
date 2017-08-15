/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import comun.Recurso.Cifrado;
import comun.Recurso.Formato;
import comun.BD.OperacionBD;
import comun.BD.Parametro;
import comun.Recurso.Tipo;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import comun.Multimedia.SaveDownloadFile;
import comun.Objets.ObjUsers;
import comun.Validaciones.ValRegistroUser;
import javax.ws.rs.core.Response;
import org.json.JSONObject;




/**
 *
 * @author TOSHIBA
 */
@Path("/usuario1")
public class UsuariopImagen {
    // public static final String RUTA =  "/Users/TOSHIBA/Documents/archivos/";
    String RUTA =SaveDownloadFile.RUTA;
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String list()throws SQLException, Exception {
        
      OperacionBD.iniciaroperacion();
   
      String sql="SELECT idusuario, nombre apellidos, genero, cuenta, vigencia, institucion, grupo, foto FROM usuario;";
      /* + " WHERE idusuario=? OR nombre=?, apellidos=? OR genero=?, cuenta=?, correo=?, telefono=? "*/
          
      List<Parametro> parametros2= new  ArrayList<>();
       
      String json = OperacionBD.consulta(sql, parametros2, Formato.JSON);
      parametros2.removeAll(parametros2);
     
      OperacionBD.confirmaroperacion();
      
      return json;
    }//--- fin listar usuarios
    
@Path("/insert")     
@POST @Produces (MediaType.APPLICATION_JSON)
@Consumes (MediaType.MULTIPART_FORM_DATA)

     public Response insert(@FormDataParam("nombre")String nombre,  
                                  @FormDataParam("apellidos")String apellidos,
                                  @FormDataParam("genero")String genero, 
                                  @FormDataParam("cuenta")String cuenta,
                                  @FormDataParam("password")String password,
                                  @FormDataParam("vigencia")String vigencia,
                                  @FormDataParam("correo")String correo ,
                                  @FormDataParam("telefono")String telefono,
                                  @FormDataParam("institucion")String institucion,
                                  @FormDataParam("grupo")String grupo,
                                 @FormDataParam("file")InputStream archivo,
                                 @FormDataParam("file") FormDataContentDisposition detallearchivo)
          throws SQLException, Exception {
         
          ObjUsers usr=new ObjUsers();
         String respuesta="";
         JSONObject jsonRegistrar = new JSONObject();
          usr=ValRegistroUser.validar_Usuario(cuenta, correo, password);
        if(usr.getCuenta()==null || usr.getCorreo()==null || usr.getPassword()==null){   
       OperacionBD.iniciaroperacion();
      String sql="INSERT INTO usuario(nombre, apellidos, genero, cuenta, password, vigencia, correo, telefono"
                  + ", institucion, grupo, foto)"  
                  + "VALUES (?,?,?,?,?,?,?,?,?,?,?);";      
     password=  Cifrado.sha2(password); 
         String rutaarchivo = RUTA + detallearchivo.getFileName();
              
         //String foto = detallearchivo.getFileName();
         String foto = rutaarchivo;
     
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
     parametros.add(new Parametro(11,foto,Tipo.VARCHAR));
      String json = OperacionBD.accion(sql,parametros);
      parametros.clear();
      SaveDownloadFile.crearCarpeta(RUTA);
      SaveDownloadFile.guardar(archivo, rutaarchivo);
      OperacionBD.confirmaroperacion();
      jsonRegistrar.put("Success","Hemos realizado tú registro con exito!!");
      
                 }
            else{
          
          jsonRegistrar.put("failed","Lo siento, El usuario ya se encuentra registrado!!");
             return Response.status(Response.Status.PRECONDITION_FAILED.getStatusCode())
                        .entity(jsonRegistrar.toString()).build();
      
           
          
             
            }//Fin else 
        
        // return Response.status(200).entity(jsonRegistrar.toString()).build();
         return Response.status(200).entity(jsonRegistrar.toString()).build();
      //return json;
     //  return Response.status(200).entity(json.toString()).build();  
     } //Fin de Metodo Insert usuario
     
     
     @Path("/update") 
     @POST 
     @Produces(MediaType.APPLICATION_JSON)  
     public String update(@FormParam("idusuario")String idusuario
                                    ,@FormParam("nombre")String nombre
                                    ,@FormParam("apellidos")String apellidos
                                    ,@FormParam("genero")String genero
                                    ,@FormParam("cuenta")String cuenta
                                    ,@FormParam("password")String password
                                    ,@FormParam("vigencia")String vigencia
                                    ,@FormParam("correo")String correo 
                                    ,@FormParam("telefono")String telefono
                                    ,@FormParam("institucion")String institucion
                                    ,@FormParam("grupo")String grupo
                                    ,@FormParam("foto")String foto )
          throws SQLException, Exception {
       OperacionBD.iniciaroperacion();
       
      String sql="UPDATE usuario SET nombre=?, apellidos=?, genero=?, cuenta=?"
              + ", password=?, vigencia=?, correo=?, telefono=?, institucion=?, grupo=?, foto=?"
              + " WHERE usuario.idusuario=?;";      
     password=  Cifrado.sha2(password);  
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
     parametros.add(new Parametro(11,foto,Tipo.VARCHAR));
     parametros.add(new Parametro(12,idusuario,Tipo.INTEGER));
     String json = OperacionBD.accion(sql, parametros);
         
      parametros.clear();
      OperacionBD.confirmaroperacion();
   
      
      return json;
     } //Fin de Metodo update usuario 
     
     @Path("/delete") 
     @POST 
     @Produces(MediaType.APPLICATION_JSON)  
     public String delete(@FormParam("idusuario")String idusuario
                                     )
          throws SQLException, Exception {
       OperacionBD.iniciaroperacion();
        String sql=" DELETE FROM usuario WHERE usuario.idusuario=?;";      
      
      List<Parametro> parametros= new  ArrayList<>();
      parametros.add(new Parametro(1,idusuario,Tipo.INTEGER));
      String json = OperacionBD.accion(sql, parametros);
      parametros.clear();
      OperacionBD.confirmaroperacion();
   
      
      return json;
     } //Fin de Metodo DELETE  
}
