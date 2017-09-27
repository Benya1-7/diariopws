/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Usuario;

import main.Usuario.Usuario;
import com.mysql.jdbc.PreparedStatement;
import comun.Recurso.Cifrado;
import comun.Recurso.Formato;
import comun.Recurso.Mensaje;
import comun.BD.OperacionBD;
import comun.BD.Parametro;
import comun.Multimedia.SaveDownloadFile;
import comun.Recurso.Tipo;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
import comun.Validaciones.ValRegistroUser;
import comun.Objets.ObjUsers;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import main.AddRol;
import comun.BD.GetIDs;
import static comun.Multimedia.SaveDownloadFile.crearCarpeta;
import static comun.Multimedia.SaveDownloadFile.guardar;
import java.io.File;
import java.io.OutputStream;
 
import org.apache.commons.codec.binary.Base64;


/**
 *
 * @author TOSHIBA
 */
@Path("/usuario")
public class Usuario {
     String RUTA =SaveDownloadFile.RUTA;
    @Path("/list")
    @GET
    @Produces("application/json;charset=UTF-8")
    public String list()throws SQLException, Exception {
        
      OperacionBD.iniciaroperacion();
   
      String sql="SELECT usuario.idusuario, nombre, apellidos, genero, cuenta, vigencia, institucion, grupo, estado, foto, fportada, token FROM usuario ORDER BY usuario.idusuario ASC;";
         
      List<Parametro> parametros2= new  ArrayList<>();
       
      String json = OperacionBD.consulta(sql, parametros2, Formato.JSON);
      parametros2.removeAll(parametros2);
     
      OperacionBD.confirmaroperacion();
      
      return json;
    }//--- fin listar usuarios
    
    @Path("/filtrousuarioXnombre")
    @GET
   @Produces("application/json;charset=UTF-8")
    public Response filtroxnombre(@HeaderParam("nombre")String nombre)
            throws SQLException, Exception {
        String respuesta="";
         JSONObject jsonuserxid = new JSONObject();
        
      try{
      OperacionBD.iniciaroperacion();
  /*  String sql="SELECT usuario.idusuario, usuario.nombre, usuario.apellidos, "
                   + "usuario.foto, usuario.cuenta FROM usuario WHERE usuario.nombre like '%"+nombre+"%';";
       */ 
 String sql="SELECT usuario.idusuario, usuario.nombre, usuario.apellidos, "
                   + "usuario.foto, usuario.fportada, usuario.cuenta, usuario.token, usuariorol.idrol, usuariorol.idusuario "
               + "FROM usuario, usuariorol WHERE usuario.nombre like '"+nombre+"%'"
               + "AND usuariorol.idusuario=usuario.idusuario;";
        
     List<Parametro> parametros= new  ArrayList<>();
      String json = OperacionBD.consulta(sql, parametros, Formato.JSON);
           parametros.clear();
      OperacionBD.confirmaroperacion();
       respuesta=json.toString();
      if (json.length()>0) {
         respuesta=json;
                 
        } else {
        
         jsonuserxid.put("Failed","No se econtro ningun resultado!!" );
          respuesta=jsonuserxid.toString();
         }
      } catch (SQLException e){
        Mensaje msg = new Mensaje();
                msg.setCodigo(Response.Status.UNAUTHORIZED.getStatusCode());
                msg.setMensaje("Error!!!");
    }     
        
    return Response.status(200).entity(respuesta.toString()).build();            
       
     // return json;
    }//--- fin listar usuarios
    @Path("/filtrousuarioXid")
    @GET
   @Produces("application/json;charset=UTF-8")
    public Response filtrousuarioXid(@HeaderParam("idusuario") String idusuario)
            throws SQLException, Exception {
        String respuesta="";
      JSONObject jsonuserxid = new JSONObject();  
      try{
      OperacionBD.iniciaroperacion();
   
      String sql="SELECT usuario.idusuario, usuario.nombre, usuario.apellidos, "
                    + "usuario.fnacimiento, usuario.genero, usuario.cuenta, "
                    + "usuario.vigencia, usuario.correo, usuario.telefono, "
                    + "usuario.institucion, usuario.grupo, usuario.estado, usuario.foto, usuario.fportada, usuario.token "
                    + "FROM usuario" +
                " WHERE usuario.idusuario=?;";
         
      List<Parametro> parametros= new  ArrayList<>();
       parametros.add(new Parametro(1,idusuario,Tipo.VARCHAR));
      String json = OperacionBD.consulta(sql, parametros, Formato.JSON);
           parametros.clear();
      OperacionBD.confirmaroperacion();
      
       if (json.length()>4) {
         respuesta=json;
                 
        } else {
        
         jsonuserxid.put("Failed","No se econtro ningun resultado!!" );
          respuesta=jsonuserxid.toString();
         }
      } catch (SQLException e){
        Mensaje msg = new Mensaje();
                msg.setCodigo(Response.Status.UNAUTHORIZED.getStatusCode());
                msg.setMensaje("!!!");
    }     
        
    return Response.status(200).entity(respuesta.toString()).build();            
    // return json;
    }//--- fin listar usuario x id
    
    
       //Registrar nuevo usuario
@Path("/insert")     
@POST @Produces
(MediaType.APPLICATION_JSON)  
     public Response insert(@FormParam("nombre")String nombre,  
                                  @FormParam("apellidos")String apellidos,
                                  @FormParam("genero")String genero, 
                                  @FormParam("cuenta")String cuenta,
                                  @FormParam("password")String password,
                                  @FormParam("vigencia")String vigencia,
                                  @FormParam("correo")String correo ,
                                  @FormParam("telefono")String telefono,
                                  @FormParam("institucion")String institucion,
                                  @FormParam("grupo")String grupo,
                                  @FormParam("estado") String estado,
                                 @FormParam("foto")String foto,
                                 @FormParam("token")String token
                                 ) 
             throws NoSuchAlgorithmException, SQLException, ParseException, JSONException
          {
              
               ObjUsers usr=new ObjUsers();
           String respuesta="";
        JSONObject jsonRegistrar = new JSONObject();
        
        
      usr=ValRegistroUser.validar_Usuario(cuenta, correo, password);
      
     
      if(usr.getCuenta()==null || usr.getCorreo()==null || usr.getPassword()==null){
          AddUsuario.insertusuario(nombre, apellidos, genero, cuenta, password, vigencia, 
                        correo, telefono, institucion, grupo, estado, foto, token);
          AddUsuario.addusuariorol();
            jsonRegistrar.put("Success","Hemos realizado tú registro con exito!!");
          
      
      }
            else{
          
          jsonRegistrar.put("failed","Lo siento, El usuario ya se encuentra registrado!!");
             return Response.status(Response.Status.PRECONDITION_FAILED.getStatusCode())
                        .entity(jsonRegistrar.toString()).build();
               
            }//Fin else 
        
        // return Response.status(200).entity(jsonRegistrar.toString()).build();
         return Response.status(200).entity(jsonRegistrar.toString()).build();
         
     } //Fin de Metodo Insert usuario
     
     
     @Path("/update") 
     @PUT 
     @Produces(MediaType.APPLICATION_JSON)  
     public String update(@FormDataParam("idusuario")String idusuario
                                    ,@FormDataParam("nombre")String nombre
                                    ,@FormDataParam("apellidos")String apellidos
                                    ,@FormDataParam("genero")String genero
                                    ,@FormDataParam("cuenta")String cuenta
                                    ,@FormDataParam("password")String password
                                    ,@FormDataParam("vigencia")String vigencia
                                    ,@FormDataParam("correo")String correo 
                                    ,@FormDataParam("telefono")String telefono
                                    ,@FormDataParam("institucion")String institucion
                                    ,@FormDataParam("grupo")String grupo
                                    ,@FormDataParam("estado")String estado
                                    ,@FormDataParam("foto")String foto )
          throws SQLException, Exception {
       OperacionBD.iniciaroperacion();
       
      String sql="UPDATE usuario SET nombre=?, apellidos=?, genero=?, cuenta=?"
              + ", password=?, vigencia=?, correo=?, telefono=?, institucion=?, grupo=?, estado=?, foto=?"
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
     parametros.add(new Parametro(11,estado,Tipo.VARCHAR));
     parametros.add(new Parametro(12,foto,Tipo.VARCHAR));
     parametros.add(new Parametro(13,idusuario,Tipo.INTEGER));
     String json = OperacionBD.accion(sql, parametros);
         
      parametros.clear();
      OperacionBD.confirmaroperacion();
      
       return json;
     } //Fin de Metodo update usuario 
     
     @Path("/updateinfPersonal") 
     @PUT 
     @Produces(MediaType.APPLICATION_JSON)  
     public String updateinfPersonal(@FormParam("idusuario")String idusuario
                                      ,@FormParam("nombre")String nombre
                                      ,@FormParam("apellidos")String apellidos
                                      ,@FormParam("genero")String genero
                                      ,@FormParam("fnacimiento")String fnacimiento
                                      ,@FormParam("telefono")String telefono
                   
                                      )
          throws SQLException, Exception {
          String respuesta="";
        JSONObject jsonupdate = new JSONObject();
       OperacionBD.iniciaroperacion();
       
      String sql="UPDATE usuario SET nombre=?, apellidos=?, genero=?, fnacimiento=?, telefono=? "
              + " WHERE usuario.idusuario=?;";      
     List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,nombre,Tipo.VARCHAR));
     parametros.add(new Parametro(2,apellidos,Tipo.VARCHAR));
     parametros.add(new Parametro(3,genero,Tipo.VARCHAR));
     parametros.add(new Parametro(4,fnacimiento,Tipo.DATE));
     parametros.add(new Parametro(5,telefono,Tipo.VARCHAR));
     parametros.add(new Parametro(6,idusuario,Tipo.INTEGER));

     String json = OperacionBD.accion(sql, parametros);
        parametros.clear();
      OperacionBD.confirmaroperacion();
        if(json.contains("1")){
            jsonupdate.put("Success","Información actualizada cone exito!! ");
        }
        else {
            jsonupdate.put("Failed","No se logro actualizar su información");
        }
       return jsonupdate.toString();
     } //Fin de Metodo update inf/personal
     
      @Path("/updateinfacademica") 
     @PUT 
     @Produces(MediaType.APPLICATION_JSON)  
     public String updateinfacademica(@FormParam("idusuario")String idusuario
                                      ,@FormParam("institucion")String institucion
                                      ,@FormParam("grupo")String grupo
                                      ,@FormParam("idrol")String idrol
                                      
                                      )
          throws SQLException, Exception {
          String respuesta="";
        JSONObject jsonupdate = new JSONObject();
       OperacionBD.iniciaroperacion();
        
      String sql="UPDATE usuario SET institucion=?, grupo=? "
              + " WHERE usuario.idusuario=?;";   
     
      String sqlusuariorol ="UPDATE usuariorol SET idrol=? WHERE usuariorol.idusuario=?;"; 
     
     List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,institucion,Tipo.VARCHAR));
     parametros.add(new Parametro(2,grupo,Tipo.VARCHAR));
     parametros.add(new Parametro(3,idusuario,Tipo.INTEGER));
     String json = OperacionBD.accion(sql, parametros);
     
     List<Parametro> parametros2= new  ArrayList<>();
     parametros2.add(new Parametro(1,idrol,Tipo.INTEGER));
      parametros2.add(new Parametro(2,idusuario,Tipo.INTEGER));
      String json2 = OperacionBD.accion(sqlusuariorol, parametros2);
        parametros.clear();
      OperacionBD.confirmaroperacion();
        if(json.contains("1")&& json2.contains("1")){
            jsonupdate.put("Success","Información actualizada con exito!! ");
        }
        else {
            jsonupdate.put("Failed","No se logro actualizar su información");
        }
       return jsonupdate.toString();
     } //Fin de Metodo update inf/academica
     
      @Path("/updateinfsocial") 
     @PUT 
     @Produces(MediaType.APPLICATION_JSON)  
     public String updateinfsocial(@FormParam("idusuario")String idusuario
                                      ,@FormParam("cuenta")String cuenta
                                      ,@FormParam("correo")String correo
                                      ,@FormParam("vigencia")String vigencia
                                      )
          throws SQLException, Exception {
          String respuesta="";
        JSONObject jsonupdate = new JSONObject();
       OperacionBD.iniciaroperacion();
       
      String sql="UPDATE usuario SET cuenta=?, correo=?, vigencia=?"
              + " WHERE usuario.idusuario=?;";      
     List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,cuenta,Tipo.VARCHAR));
     parametros.add(new Parametro(2,correo,Tipo.VARCHAR));
     parametros.add(new Parametro(3,vigencia,Tipo.VARCHAR));
     parametros.add(new Parametro(4,idusuario,Tipo.INTEGER));

     String json = OperacionBD.accion(sql, parametros);
        parametros.clear();
      OperacionBD.confirmaroperacion();
        if(json.contains("1")){
            jsonupdate.put("Success","Información actualizada con exito!! ");
        }
        else {
            jsonupdate.put("Failed","No se logro actualizar su información");
        }
       return jsonupdate.toString();
     } //Fin de Metodo update inf/personal
       
     @Path("/updateestado") 
     @PUT 
     @Produces(MediaType.APPLICATION_JSON)  
     public String updateestado(@FormParam("idusuario")String idusuario
                                      ,@FormParam("estado")String estado                                      ,@FormParam("apellidos")String apellidos
                                       )
          throws SQLException, Exception {
          String respuesta="";
        JSONObject jsonupdate = new JSONObject();
       OperacionBD.iniciaroperacion();
       
      String sql="UPDATE usuario SET estado=?"
              + " WHERE usuario.idusuario=?;";      
     List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,estado,Tipo.VARCHAR));
     parametros.add(new Parametro(2,idusuario,Tipo.INTEGER));

     String json = OperacionBD.accion(sql, parametros);
        parametros.clear();
      OperacionBD.confirmaroperacion();
        if(json.contains("1")){
            jsonupdate.put("Success","Información actualizada con exito!! ");
        }
        else {
            jsonupdate.put("Failed","No se logro actualizar su información");
        }
       return jsonupdate.toString();
     } //Fin de Metodo update estado
     
      
     
         @POST
    @Path("/upload")     /*Metodo para actualizar imagen de perfil de usuario*/
    @Consumes ({MediaType.MULTIPART_FORM_DATA,MediaType.APPLICATION_FORM_URLENCODED})
    @Produces ({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})  
    public Response subir(
            @FormDataParam("image") InputStream archivo,
            @FormDataParam("image") FormDataContentDisposition detallearchivo
            ,@FormDataParam("idusuario")String idusuario)
    throws SQLException, Exception{
         JSONObject jsonresp_upload = new JSONObject();
		//--- revisar parametros
		if (archivo == null || detallearchivo == null)

			return Response.status(400).entity("Formato no válido").build();
		
		try {
	        crearCarpeta(SaveDownloadFile.RUTA);
                        
                                   /* try {
                                       EliminarFile.delete_imageperfil(idusuario);
                                        
                                    } catch (Exception e) {
                                        
                                    }*/
                                
		} catch (SecurityException se) {
			return Response.status(500)
		.entity("Hay un error de permisos en el servidor").build();
		}
                 String rutaarchivo = SaveDownloadFile.RUTA + detallearchivo.getFileName();
                 String foto=detallearchivo.getFileName();

                try {
                     guardar(archivo, rutaarchivo);
                     OperacionBD.iniciaroperacion();
                  String sql="UPDATE usuario SET foto=? WHERE usuario.idusuario=?;"; 
                       List<Parametro> parametros= new  ArrayList<>();
                       parametros.add(new Parametro(1,foto,Tipo.VARCHAR));
                       parametros.add(new Parametro(2,idusuario,Tipo.INTEGER));
                       String json = OperacionBD.accion(sql, parametros);
                       parametros.clear();
                       OperacionBD.confirmaroperacion();
                    jsonresp_upload.put("succes:", detallearchivo.getFileName());
                       
		} catch (IOException e) {
			return Response.status(500).entity("No se pudo guardar el archivo").build();
		}
                
              
         return Response.status(200).entity(jsonresp_upload.toString()).build();  
      
}//Fin Upload
   
          @POST
    @Path("/uploadfPortada") /*Metodo para actualizar imagen de portada de usuario*/
    @Consumes ({MediaType.MULTIPART_FORM_DATA,MediaType.APPLICATION_FORM_URLENCODED})
    @Produces ({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})  
    public Response uploadfPortada(
            @FormDataParam("image") InputStream archivo,
            @FormDataParam("image") FormDataContentDisposition detallearchivo
            ,@FormDataParam("idusuario")String idusuario)
    throws SQLException, Exception{
         JSONObject jsonresp_upload = new JSONObject();
          String respuesta="";
          
		//--- revisar parametros
		if (archivo == null || detallearchivo == null)
 return Response.status(400).entity("Formato no válido").build();
                                respuesta="no soportado";
		
		try {
	        crearCarpeta(SaveDownloadFile.RUTA);
                          try {
                                       EliminarFile.delete_imageportada(idusuario);
                                        
                                    } catch (Exception e) {
                                        
                                    }
		} catch (SecurityException se) {
			return Response.status(500)
		.entity("Hay un error de permisos en el servidor").build();
		}
                 String rutaarchivo = SaveDownloadFile.RUTA + detallearchivo.getFileName();
                 String foto=detallearchivo.getFileName();
                 String fportada=foto;

                try {
                     guardar(archivo, rutaarchivo);
                                   OperacionBD.iniciaroperacion();
                  String sql="UPDATE usuario SET fportada=? WHERE usuario.idusuario=?;"; 
                       List<Parametro> parametros= new  ArrayList<>();
                       parametros.add(new Parametro(1,fportada,Tipo.VARCHAR));
                       parametros.add(new Parametro(2,idusuario,Tipo.INTEGER));
                       String json = OperacionBD.accion(sql, parametros);
                       parametros.clear();
                       
                       OperacionBD.confirmaroperacion();
                    jsonresp_upload.put("succes:", detallearchivo.getFileName());
                     respuesta="true";   
		} catch (IOException e) {
			return Response.status(500).entity("No se pudo guardar el archivo").build();
		}
              
         return Response.status(200).entity(jsonresp_upload.toString()).build();  
      
}//Fin uploadfPortada
       
     
          
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
