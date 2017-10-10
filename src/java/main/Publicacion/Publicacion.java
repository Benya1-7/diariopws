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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import comun.BD.GetIDs;
import comun.Multimedia.SaveDownloadFile;
import static comun.Multimedia.SaveDownloadFile.crearCarpeta;
import static comun.Multimedia.SaveDownloadFile.guardar;
import comun.Objets.ObjPublicacion;
import comun.Validaciones.ValUpdatePublicacion;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import org.json.JSONArray;
import java.sql.CallableStatement; 
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import main.Publicacion.Archivo.*;
import org.json.JSONException;

/**
 *
 * @author TOSHIBA
 */
@Path("/publicacion")
public class Publicacion {

   
 @Path("/list")
    @GET
    @Produces("application/json;charset=UTF-8")
   
 public String list()throws SQLException, Exception {
       
      OperacionBD.iniciaroperacion();
String sql=" SELECT p.idpublicacion, p.idusuario, p.fecha, p.sentimiento, p.evaluacion, p.analisis, p.conclusion, p.planaccion, p.titulo, p.observaciones, p.padre, u.idusuario, u.nombre, u.cuenta, u.foto, u.token from publicacion p, usuario u WHERE p.padre=0 and p.idusuario=u.idusuario  ORDER BY p.idpublicacion DESC;";   

//String sql="SELECT p.idpublicacion, p.fecha, p.sentimiento, p.evaluacion, p.analisis, p.conclusion, p.planaccion, p.titulo, p.observaciones, p.padre, u.idusuario, u.nombre, u.cuenta, u.foto, u.token FROM grupodetalle gd, grupo g, usuario u, grupopublicacion gp, publicacion p WHERE g.idgrupo=gd.idgrupo AND u.idusuario=gd.idusuario AND gd.idusuario=u.idusuario AND gp.idgrupo=gd.idgrupo AND p.idpublicacion=gp.idpublicacion AND p.padre=0 AND u.idusuario=? ORDER BY p.idpublicacion DESC;";  
List<Parametro> parametros= new  ArrayList<>();
       
      String json = OperacionBD.consulta(sql, parametros, Formato.JSON);
      parametros.removeAll(parametros);
     
      OperacionBD.confirmaroperacion();
      
          return json;
    }//--- fin list
 
 ////////////////Listar republicaciones por id de publicación original
 
    
 @Path("/listrepublication")
    @GET
    @Produces("application/json;charset=UTF-8")
   
 public String listrepublication(@HeaderParam("idpublicacion") String idpublicacion)throws SQLException, Exception {

     
      OperacionBD.iniciaroperacion();
  
 //  String sql="SELECT p.idpublicacion, p.idusuario, p.titulo, p.padre, ph.idpublicacion,  ph.idusuario, ph.titulo, ph.observaciones, ph.padre, u.idusuario, u.nombre, u.foto FROM publicacion p, publicacion ph, usuario u where p.idpublicacion=ph.padre and p.idpublicacion=? and u.idusuario=ph.idusuario;";
 String sql="SELECT p.idpublicacion, p.idusuario, p.titulo,  p.sentimiento, p.evaluacion, p.analisis, p.conclusion, p.planaccion, p.padre, ph.idpublicacion, ph.idusuario, ph.titulo, ph.observaciones, ph.padre, u.idusuario, u.nombre, u.foto, u.token FROM publicacion p, publicacion ph, usuario u where p.idpublicacion=ph.padre and p.idpublicacion=? and u.idusuario=ph.idusuario;";   
          
      List<Parametro> parametros= new  ArrayList<>();
       parametros.add(new Parametro(1, idpublicacion, Tipo.INTEGER));
      String json = OperacionBD.consulta(sql, parametros, Formato.JSON);
      parametros.removeAll(parametros);
     
      OperacionBD.confirmaroperacion();
      
          return json;
    }//--- fin list republicacion
 
 
 
     ////Listar publicaciones por id de usuario
 @Path("/listxiduser")
    @GET
    @Produces("application/json;charset=UTF-8")
 
   
 public String listxiduser(@HeaderParam("idusuario") String idusuario)
         throws SQLException, Exception {
              
      OperacionBD.iniciaroperacion();
       
      /* String sql="SELECT publicacion.idpublicacion, publicacion.idusuario, publicacion.fecha, "
                 + "publicacion.titulo, publicacion.observaciones, publicacion.padre, "
                 + "grupodetalle.idusuario, grupodetalle.idrol, grupo.idgrupo, grupo.nombregrupo, "
                 + "detallepublicacion.iddetallepublicacion, detallepublicacion.idpublicacion, "
                 + "detallepublicacion.ruta, detallepublicacion.tipo, detallepublicacion.formato, "
                 + "detallepublicacion.descripcion, usuario.nombre, foto from publicacion, grupodetalle, "
                 + "grupo, detallepublicacion, usuario WHERE publicacion.idusuario=usuario.idusuario AND "
                 + "grupodetalle.idusuario=publicacion.idusuario AND grupo.idgrupo=grupodetalle.idgrupo AND "
                 + "detallepublicacion.idpublicacion=publicacion.idpublicacion "
                 + "AND usuario.idusuario= ? ORDER BY publicacion.idpublicacion DESC;";*/
      String sql= "SELECT p.idpublicacion, p.idusuario, p.fecha, p.sentimiento, p.evaluacion, p.analisis, p.conclusion, p.planaccion, p.titulo, p.observaciones, p.padre, u.idusuario, u.nombre,u.cuenta, u.foto, u.token from publicacion p, usuario u WHERE p.padre=0 and p.idusuario=u.idusuario and u.idusuario=? ORDER BY p.idpublicacion DESC;";
     /* String sql= "SELECT gd.idusuario, u.nombre, u.idusuario AS OMITIR, gd.idgrupo, g.idgrupo, g.nombregrupo, "
                    + "gp.idpublicacion AS IDPUBGP,p.idpublicacion, p.titulo, p.idusuario FROM grupodetalle gd, "
                    + "grupo g, usuario u, grupopublicacion gp, publicacion p WHERE g.idgrupo=gd.idgrupo "
                    + "AND u.idusuario=gd.idusuario  AND gd.idusuario=u.idusuario  AND gp.idgrupo=gd.idgrupo "
                    + "AND p.idpublicacion=gp.idpublicacion AND p.padre=0 AND u.idusuario=? ORDER BY p.idpublicacion DESC;";*/
/*Correcta an en elservidir */ //String sql= "SELECT u.idusuario, u.nombre,u.cuenta, u.foto, u.token, g.idgrupo, g.nombregrupo, p.idpublicacion, p.idusuario, p.fecha, p.sentimiento, p.evaluacion, p.analisis, p.conclusion, p.planaccion, p.titulo, p.observaciones, p.padre FROM grupodetalle gd, grupo g, usuario u, grupopublicacion gp, publicacion p WHERE g.idgrupo=gd.idgrupo AND u.idusuario=gd.idusuario AND gd.idusuario=u.idusuario AND gp.idgrupo=gd.idgrupo AND p.idpublicacion=gp.idpublicacion AND p.padre=0 AND u.idusuario=? ORDER BY p.idpublicacion DESC;";
//  String sql="SELECT u.idusuario, u.nombre,u.cuenta, u.foto, u.token, g.idgrupo, g.nombregrupo, p.idpublicacion, p.idusuario AS PARABUSCARUSER, p.fecha, p.sentimiento, p.evaluacion, p.analisis, p.conclusion, p.planaccion, p.titulo, p.observaciones, p.padre FROM grupodetalle gd, grupo g, usuario u, grupopublicacion gp, publicacion p WHERE g.idgrupo=gd.idgrupo AND u.idusuario=gd.idusuario AND gd.idusuario=u.idusuario AND gp.idgrupo=gd.idgrupo AND p.idpublicacion=gp.idpublicacion AND p.padre=0 AND u.idusuario=? ORDER BY p.idpublicacion DESC;";      

  List<Parametro> parametros= new  ArrayList<>();
      parametros.add(new Parametro(1, idusuario, Tipo.INTEGER));
       
      String json = OperacionBD.consulta(sql, parametros, Formato.JSON);
      parametros.removeAll(parametros);
     
      OperacionBD.confirmaroperacion();
      
          return json;
    }//--- fin listxiduser
 
      ////Listar publicaciones de los grupos donde participa el usuario
 @Path("/listpublicxgpo")
    @GET
    @Produces("application/json;charset=UTF-8")
 
   
 public String listpublicxgpo(@HeaderParam("idusuario") String idusuario)
         throws SQLException, Exception {
              
      OperacionBD.iniciaroperacion();       
      String sql="SELECT u.idusuario, u.nombre,u.cuenta, u.foto, u.token, g.idgrupo, g.nombregrupo, p.idpublicacion, p.idusuario AS PARABUSCARUSER, p.fecha, p.sentimiento, p.evaluacion, p.analisis, p.conclusion, p.planaccion, p.titulo, p.observaciones, p.padre FROM grupodetalle gd, grupo g, usuario u, grupopublicacion gp, publicacion p WHERE g.idgrupo=gd.idgrupo AND u.idusuario=gd.idusuario AND gd.idusuario=u.idusuario AND gp.idgrupo=gd.idgrupo AND p.idpublicacion=gp.idpublicacion AND p.padre=0 AND u.idusuario=? ORDER BY p.idpublicacion DESC;";      

  List<Parametro> parametros= new  ArrayList<>();
      parametros.add(new Parametro(1, idusuario, Tipo.INTEGER));
       
      String json = OperacionBD.consulta(sql, parametros, Formato.JSON);
      parametros.removeAll(parametros);
     
      OperacionBD.confirmaroperacion();
      
          return json;
    }//--- fin Listar publicaciones de los grupos donde participa en usuario
 
 
 
 
 ////Listar publicacion por grupo 
  @Path("/listxidgrupo")
  @GET
  @Produces("application/json;charset=UTF-8")
   
 public String listxidgrupo(@HeaderParam("idgrupo") String idgrupo)
         throws SQLException, Exception {
              String respuesta="";
               JSONObject jsonrespuesta = new JSONObject();
      OperacionBD.iniciaroperacion();
       

      String sql="SELECT grupo.idgrupo, grupo.nombregrupo, grupodetalle.idusuario , "
                    + "usuario.nombre, usuario.foto, usuario.token, publicacion.fecha , "
                    + "publicacion.titulo, publicacion.observaciones, publicacion.padre , "
                    + "detallepublicacion.ruta, detallepublicacion.tipo, detallepublicacion.formato, "
                    + "detallepublicacion.descripcion "
                    + "FROM grupo, grupodetalle, usuario, publicacion, "
                    + "detallepublicacion, grupopublicacion "
                    + "WHERE grupodetalle.idgrupo = grupo.idgrupo "
                    + "AND detallepublicacion.idpublicacion = publicacion.idpublicacion "
                    + "AND publicacion.idusuario = usuario.idusuario "
                    + "AND grupopublicacion.idpublicacion = publicacion.idpublicacion "
                    + "AND grupopublicacion.idpublicacion = detallepublicacion.iddetallepublicacion "
                    + "AND grupopublicacion.idpublicacion = publicacion.idpublicacion "
                    + "AND grupodetalle.idusuario = publicacion.idusuario "
                    + "AND grupo.idgrupo = ? ORDER BY publicacion.idpublicacion DESC;";
      List<Parametro> parametros= new  ArrayList<>();
      parametros.add(new Parametro(1, idgrupo, Tipo.INTEGER));
       
      String json = OperacionBD.consulta(sql, parametros, Formato.JSON);
      parametros.removeAll(parametros);
     
      OperacionBD.confirmaroperacion();
      jsonrespuesta.put("Success",json.toString());
      
          return json;
    }//--- fin listxidusuario
     
 /////Metodo agregado 10-08-2017
   @Path("/listdetpublication")
  @GET
  @Produces("application/json;charset=UTF-8")
 public String listdetpublication(@HeaderParam("idpublicacion") String idpublicacion)
         throws SQLException, Exception {
              
      OperacionBD.iniciaroperacion();
       
       String sql="SELECT dp.iddetallepublicacion, dp.idpublicacion, dp.ruta, dp.tipo, dp.formato, dp.descripcion FROM detallepublicacion dp WHERE dp.idpublicacion=?;";
      List<Parametro> parametros= new  ArrayList<>();
      parametros.add(new Parametro(1, idpublicacion, Tipo.INTEGER));
       
      String json = OperacionBD.consulta(sql, parametros, Formato.JSON);
      parametros.removeAll(parametros);
     
      OperacionBD.confirmaroperacion();
      
          return json;
    }//--- fin listar detalles de publicacion  /////////////////////////////////////////////////////////////////////////
 
 
    
    @Path("/createpublicacionpadre")
    @POST
    @Produces("application/json;charset=UTF-8")
    
     public Response createpublicacionpadre(@FormParam("idusuario")String idusuario
                          ,@FormParam("titulo")String titulo
                           ,@FormParam("sentimiento")String sentimiento
                            ,@FormParam("evaluacion")String evaluacion
                             ,@FormParam("analisis")String analisis
                              ,@FormParam("conclusion")String conclusion
                               ,@FormParam("planaccion")String planaccion
                          ,@FormParam("observaciones")String observaciones
                           ,@FormParam("idgrupo")String idgrupo
                          ,@FormParam("formato")String formatletra
                            )
           {
         JSONObject jsoninsetpublicacion = new JSONObject();     
     try {
                
         String tipo="";
         String formato =formatletra;
         String descripcion="";  //Breve descripcion del archivo
          AddPublicacion.addpublicacion(idusuario, titulo, sentimiento, evaluacion, analisis, conclusion, planaccion, observaciones);
         // AddPublicacion.insertDpublicacion(tipo, formato, descripcion);            comentado 10-08-2017
          AddPublicacion.grupopublicacion(idgrupo);
          jsoninsetpublicacion.put("ok", "Publicacion registrada");      
             
            
     } 
     catch (Exception ex) {
         Logger.getLogger(Publicacion.class.getName()).log(Level.SEVERE, null, ex);
         
     }
      return Response.status(200).entity(jsoninsetpublicacion.toString()).build();
     }//Fin createpublicacionpadre
     
     ////Update publicacion padre
    @Path("/updatepublicacionpadre")
    @PUT
    @Produces("application/json;charset=UTF-8")
    
     public Response updatepublicacionpadre(
                         @FormParam("idpublicacion")String idpublicacion
                          ,@FormParam("titulo")String titulo
                           ,@FormParam("sentimiento")String sentimiento
                            ,@FormParam("evaluacion")String evaluacion
                             ,@FormParam("analisis")String analisis
                              ,@FormParam("conclusion")String conclusion
                               ,@FormParam("planaccion")String planaccion
                          ,@FormParam("observaciones")String observaciones
                          
                            )
           {
         JSONObject jsoninsetpublicacion = new JSONObject();     
     try {
          AddPublicacion.updatepublicacion(idpublicacion, titulo, sentimiento, evaluacion, analisis, conclusion, planaccion, observaciones);
          jsoninsetpublicacion.put("ok", "Publicacion Actualizada");      
             
            
     } 
     catch (Exception ex) {
         Logger.getLogger(Publicacion.class.getName()).log(Level.SEVERE, null, ex);        
     }
      return Response.status(200).entity(jsoninsetpublicacion.toString()).build();
     }//Fin update publicacion padre
     
     
     @Path("/updatepublicacionpadre1")
    @PUT
    @Produces("application/json;charset=UTF-8")
     public Response updatepublicacionpadre1(@FormParam("idpublicacion")String idpublicacion
                         ,@FormParam("idusuario")String idusuario       
                          ,@FormParam("titulo")String titulo
                           ,@FormParam("sentimiento")String sentimiento
                            ,@FormParam("evaluacion")String evaluacion
                             ,@FormParam("analisis")String analisis
                              ,@FormParam("conclusion")String conclusion
                               ,@FormParam("planaccion")String planaccion
                          ,@FormParam("observaciones")String observaciones
                          
                            )  throws NoSuchAlgorithmException, SQLException, ParseException, JSONException
           {
               String respuesta="";
                ObjPublicacion publicacion=new ObjPublicacion();
             publicacion=ValUpdatePublicacion.verificarpublicacion(idpublicacion,idusuario);    
             if(publicacion.getIdpublicacion()!=null && publicacion.getIdusuario()!=null){
            AddPublicacion.updatepublicacion(idpublicacion, titulo, sentimiento, evaluacion, analisis, conclusion, planaccion, observaciones);
                  respuesta="true";
            
      }
             else{
              //   Response.status(Response.Status.NOT_ACCEPTABLE).build();
            respuesta="Usted no puede modificar la publicacin";
             }
           return  Response.ok(respuesta).build();
           }
     
    //////////////////////////////publicar archivo ///////////////////////
     @POST
    @Path("/publicarArchivo") 
    @Consumes ({MediaType.MULTIPART_FORM_DATA,"application/json;charset=UTF-8"})
    @Produces ("application/json;charset=UTF-8")  
    public Response subir(
            @FormDataParam("archivo") InputStream archivo
            ,@FormDataParam("archivo") FormDataContentDisposition detallearchivo
           // ,@FormDataParam("titulo")String titulo ///
           // ,@FormDataParam("observaciones")String observaciones                  
            //,@FormDataParam("idusuario")String idusuario
             ,@FormDataParam("idpublicacion")String   idpublicacion
             ,@FormDataParam("ruta")String   ruta 
            ,@FormDataParam("tipoarchivo")String   tipoarchivo
            ,@FormDataParam("Farchivo")String   Farchivo                  
            ,@FormDataParam("idgrupo")String idgrupo                 
                
    )
    throws SQLException, Exception{
         JSONObject jsonresp_upload = new JSONObject();
	if (archivo == null || detallearchivo == null)
                    return Response.status(400).entity("Formato no válido").build();
                  JSONObject jsoninsetpublicacion = new JSONObject();   
	try {
	crearCarpeta(SaveDownloadFile.RUTA);
	 } catch (SecurityException se) {
	     return Response.status(500)
	.entity("Hay un error de permisos en el servidor")
	.build();
	}                
                String rutaarchivo = SaveDownloadFile.RUTA + detallearchivo.getFileName();
                String descrip=detallearchivo.getFileName();
                

                try {
                    guardar(archivo, rutaarchivo);
                    
         String tipo=tipoarchivo; // tipo de archvivo 
         String formato = Farchivo; //se obtiene el tipo de archivo que es "jpg, pdf, mp3, mp4, etc"
         String descripcion=descrip;  //Breve descripcion del archivo "se retoma el titulo del archivo publicado"
         //  AddPublicacion.addpublicacion(idusuario, titulo, observaciones);
          AddPublicacion.insertDpublicacion(idpublicacion, ruta, tipo, formato, descripcion);
          AddPublicacion.grupopublicacion(idgrupo);      
                       
		} catch (IOException e) {
			return Response.status(500).entity("No se pudo guardar el archivo").build();
		}
                //return Responsme.status(200)
		//		.entity(detallearchivo.getFileName() + " ha sido guardado satisfactoriamente... " ).build();
	 jsonresp_upload.put("succes:", detallearchivo.getFileName()); 
         return Response.status(200).entity(jsonresp_upload.toString()).build(); 
    }
     /////////////////////////////fin  publicar archivo//////////////////
     
     
     
    public String listidpublicacion()throws SQLException, Exception {
       String ids="";
      OperacionBD.iniciaroperacion();
              String sql ="SELECT MAX(idpublicacion+1) AS id FROM publicacion;";    
      List<Parametro> parametros= new  ArrayList<>();
       String json = OperacionBD.consulta(sql, parametros,Formato.JSON);
                  parametros.removeAll(parametros);     
                  OperacionBD.confirmaroperacion();
             JSONArray jsonarray = new JSONArray(json);
             for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
               ids=(String) jsonobject.get("id");
             }    
    
  return ids;
    }//--- fin listidpublicacion
     
      
         @Path("/Insertxgrupo")
    @POST
    @Produces("application/json;charset=UTF-8")
    
     public Response Insertxgrupo(@FormParam("idusuario")String idusuario
                          ,@FormParam("titulo")String titulo
                          ,@FormParam("observaciones")String observaciones
                          )
          throws SQLException, Exception {
         String respuesta="";
         String padre ="0";
          
        JSONObject jsonrespuesta = new JSONObject();
         
       OperacionBD.iniciaroperacion();
     String sql="INSERT INTO publicacion(idusuario, fecha, titulo, observaciones, padre)"  
                                + "VALUES (?,?,?,?,?);";
      
         
           Timestamp currentfecha = new Timestamp(System.currentTimeMillis());
          //String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp());
            String fecha = currentfecha.toString();
      List<Parametro> parametros= new  ArrayList<>();
      parametros.add(new Parametro(1,idusuario,Tipo.INTEGER));
      parametros.add(new Parametro(2,fecha,Tipo.TIMESTAMP));
      parametros.add(new Parametro(3,titulo,Tipo.VARCHAR));
      parametros.add(new Parametro(4,observaciones,Tipo.VARCHAR));
      parametros.add(new Parametro(5,padre,Tipo.INTEGER));
    
     String json = OperacionBD.accion(sql, parametros);
      parametros.clear();
      OperacionBD.confirmaroperacion();
      jsonrespuesta.put("Success","Publicacion Registrada con exito!!");
   
       return Response.status(200).entity(jsonrespuesta.toString()).build();
     }//Fin Insert_publicacion_x_id_grupo
     
     
         @Path("/republication")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    
     public Response republication(@FormParam("idusuario")String idusuario
                                  ,@FormParam("titulo")String titulo
                                  ,@FormParam("observaciones")String observaciones
                                  ,@FormParam("idpublicacion")String idpublicacion
                                  ,@FormParam("idgrupo")String idgrupo
                                                    
     )
          throws SQLException, Exception {
         String respuesta="";
         String padre=idpublicacion;
        JSONObject jsonrespuesta = new JSONObject();
             try {
                
         String tipo=" ";
         String formato =" ";
         String descripcion="  ";  //Breve descripcion del archivo
          AddRepublication.insertRpublicacion(idpublicacion, idusuario, titulo, observaciones);
         //AddRepublication.insertDpublicacion(idpublicacion, tipo, formato, descripcion);        //Comentado 10 -08 -2017
         AddRepublication.grupopublicacion(idpublicacion, idgrupo);
          jsonrespuesta.put("ok", "RePublicacion registrada con Exito!!!");      
             
            
     } 
     catch (Exception ex) {
         Logger.getLogger(Publicacion.class.getName()).log(Level.SEVERE, null, ex);
     }
   
       return Response.status(200).entity(jsonrespuesta.toString()).build();
     }//Fin insert republication
     
     /////update inf complementaria//////////////////////////////////////////////
          
      @Path("/updatesentimiento") 
     @PUT 
     @Produces(MediaType.APPLICATION_JSON)  
     public String updatesentimiento(@FormParam("idpublicacion")String idpublicacion
                                      ,@FormParam("sentimiento")String sentimiento
                                     
                                                                           
                                      )
          throws SQLException, Exception {
          String respuesta="";
        JSONObject jsonupdate = new JSONObject();
        try {
              
          
       OperacionBD.iniciaroperacion();

      String sql="UPDATE publicacion SET sentimiento=? WHERE publicacion.idpublicacion=?;";   
         
     List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,sentimiento,Tipo.VARCHAR));
     parametros.add(new Parametro(2,idpublicacion,Tipo.VARCHAR));
     String json = OperacionBD.accion(sql, parametros);
   
        parametros.clear();
      OperacionBD.confirmaroperacion();
      jsonupdate.put("Success","información actualizada con exito!!"+json);
      } catch (Exception e) {
         
       }
        return jsonupdate.toString();
     } //Fin de Metodo update updatesentimiento
     
      @Path("/updateevaluacion") 
     @PUT 
     @Produces(MediaType.APPLICATION_JSON)  
     public String updateevaluacion(@FormParam("idpublicacion")String idpublicacion
                                      ,@FormParam("evaluacion")String evaluacion
                                     
                                                                           
                                      )
          throws SQLException, Exception {
          String respuesta="";
        JSONObject jsonupdate = new JSONObject();
        try {
              
          
       OperacionBD.iniciaroperacion();
      
      String sql="UPDATE publicacion SET evaluacion=? WHERE publicacion.idpublicacion=?;";   
         
     List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,evaluacion,Tipo.VARCHAR));
     parametros.add(new Parametro(2,idpublicacion,Tipo.VARCHAR));
     String json = OperacionBD.accion(sql, parametros);
   
        parametros.clear();
      OperacionBD.confirmaroperacion();
      jsonupdate.put("Success","información actualizada con exito!!"+json);
      } catch (Exception e) {
         
       }
        return jsonupdate.toString();
     } //Fin de Metodo update updateevaluacion
     
      @Path("/updateanalisis") 
     @PUT 
     @Produces(MediaType.APPLICATION_JSON)  
     public String updateanalisis(@FormParam("idpublicacion")String idpublicacion
                                      ,@FormParam("analisis")String analisis
                                     
                                                                           
                                      )
          throws SQLException, Exception {
          String respuesta="";
        JSONObject jsonupdate = new JSONObject();
        try {
              
          
       OperacionBD.iniciaroperacion();
      
      String sql="UPDATE publicacion SET analisis=? WHERE publicacion.idpublicacion=?;";   
         
     List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,analisis,Tipo.VARCHAR));
     parametros.add(new Parametro(2,idpublicacion,Tipo.VARCHAR));
     String json = OperacionBD.accion(sql, parametros);
   
        parametros.clear();
      OperacionBD.confirmaroperacion();
      jsonupdate.put("Success","analisis actualizada con exito!!"+json);
      } catch (Exception e) {
         
       }
        return jsonupdate.toString();
     } //Fin de Metodo update updateanalisis
     
          
      @Path("/updateconclusion") 
     @PUT 
     @Produces(MediaType.APPLICATION_JSON)  
     public String updateconclusion(@FormParam("idpublicacion")String idpublicacion
                                      ,@FormParam("conclusion")String conclusion
                                     
                                                                           
                                      )
          throws SQLException, Exception {
          String respuesta="";
        JSONObject jsonupdate = new JSONObject();
        try {
              
          
       OperacionBD.iniciaroperacion();
      
      String sql="UPDATE publicacion SET conclusion=? WHERE publicacion.idpublicacion=?;";   
         
     List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,conclusion,Tipo.VARCHAR));
     parametros.add(new Parametro(2,idpublicacion,Tipo.VARCHAR));
     String json = OperacionBD.accion(sql, parametros);
   
        parametros.clear();
      OperacionBD.confirmaroperacion();
      jsonupdate.put("Success","conclusion actualizada con exito!!"+json);
      } catch (Exception e) {
         
       }
        return jsonupdate.toString();
     } //Fin de Metodo update updateconclusion
     
           @Path("/updateplanaccion") 
     @PUT 
     @Produces(MediaType.APPLICATION_JSON)  
     public String updateplanaccion(@FormParam("idpublicacion")String idpublicacion
                                      ,@FormParam("planaccion")String planaccion
                                     
                                                                           
                                      )
          throws SQLException, Exception {
          String respuesta="";
        JSONObject jsonupdate = new JSONObject();
        try {
              
          
       OperacionBD.iniciaroperacion();
      
      String sql="UPDATE publicacion SET planaccion=? WHERE publicacion.idpublicacion=?;";   
         
     List<Parametro> parametros= new  ArrayList<>();
     parametros.add(new Parametro(1,planaccion,Tipo.VARCHAR));
     parametros.add(new Parametro(2,idpublicacion,Tipo.VARCHAR));
     String json = OperacionBD.accion(sql, parametros);
   
        parametros.clear();
      OperacionBD.confirmaroperacion();
      jsonupdate.put("Success","plan de accion actualizado con exito!!"+json);
      } catch (Exception e) {
         
       }
        return jsonupdate.toString();
     }
     /////update inf complementaria//////////////////////////////////////////
     
     
      @Path("/delete") 
     @POST 
     @Produces(MediaType.APPLICATION_JSON)  
     public String delete(@FormParam("idpublicacion")String idpublicacion
                                     )
          throws SQLException, Exception {
       OperacionBD.iniciaroperacion();
        String sql=" DELETE FROM publicacion WHERE publicacion.idpublicacion=? ";      
      
      List<Parametro> parametros= new  ArrayList<>();
      parametros.add(new Parametro(1,idpublicacion,Tipo.INTEGER));
      String json = OperacionBD.accion(sql, parametros);
      parametros.clear();
      OperacionBD.confirmaroperacion();
   
      
      return json;
     } //Fin de Metodo DELETE  
     
      public static String Fechaactual() {
          
        Date fecha=new Date();
          SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
         
     return formatofecha.format(fecha);
        
    }
    
        
     
    
  
}
