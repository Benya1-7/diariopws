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
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONObject;
import comun.Multimedia.SaveDownloadFile;
import java.io.File;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
        

/**
 *
 * @author TOSHIBA
 */
@Path("/userfoto")
public class Fotousuario extends HttpServlet{
     String RUTA =SaveDownloadFile.RUTA;

    @POST
    //@Path("/descargar/{archivo}")
     @Path("/downloadfoto")    
    @Produces({MediaType.MULTIPART_FORM_DATA, MediaType.TEXT_PLAIN,MediaType.APPLICATION_JSON})
   public Response descargar(@FormDataParam("idusuario") String idusuario,
                             @FormDataParam("foto") String foto) 
   throws SQLException, Exception {
             JSONObject jsondownload = new JSONObject();
       /* String sql="SELECT foto FROM usuario WHERE usuario.idusuario=?;";
        List<Parametro> parametros= new  ArrayList<>();
        parametros.add(new Parametro(1,idusuario,Tipo.INTEGER));
  
        String json = OperacionBD.consulta(sql,parametros, Formato.JSON);
      parametros.removeAll(parametros);
      OperacionBD.confirmaroperacion();*/
     
      String Archivo=foto;
       String rutaarchivo=SaveDownloadFile.RUTA+Archivo;
       File file = new File(rutaarchivo);
        if (!file.exists())            
         return Response.status(400).entity("Recurso no disponible").build();
           // jsondownload.put("error", "recurso no disponible");
           
        Response.ResponseBuilder responseBuilder = Response.ok((Object) file);
             
      responseBuilder.header("Content-Disposition", "attachment; filename='" + Archivo + "'");
       jsondownload.put("succes:", responseBuilder.build());  
     return responseBuilder.build();
    //return jsondownload.toString();
     // return Response.status(200).entity(json.toString()).build();  
    // return json;
     
        
       // System.out.println("ARCHIVO: " + rutaarchivo);
       
       
    }
    
}//Fin clase
