/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Publicacion;

/**
 *
 * @author TOSHIBA
 */

import comun.BD.OperacionBD;
import comun.BD.Parametro;
import comun.Recurso.Tipo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import comun.Multimedia.SaveDownloadFile;
import comun.Recurso.Formato;
import java.sql.Timestamp;
import org.apache.commons.codec.binary.Base64;
        

import javax.servlet.http.HttpServlet;
import javax.ws.rs.FormParam;
import org.json.JSONObject;
//@WebServlet("/UploadServlet")
@Path("/archivos")
public class Archivo extends HttpServlet{
String RUTA=SaveDownloadFile.RUTA;
   

@GET
@Path("/descargar/{archivo}")
    // @Path("/descargar")
    @Produces({MediaType.MULTIPART_FORM_DATA, MediaType.TEXT_PLAIN,MediaType.APPLICATION_JSON})
   public Response descargar(@PathParam("archivo") String archivo) {
        String rutaarchivo=RUTA + archivo;
        System.out.println("ARCHIVO: " + rutaarchivo);
        File file = new File(rutaarchivo);
        if (!file.exists())            
            return Response.status(400).entity("Recurso no disponible").build();
               
        ResponseBuilder responseBuilder = Response.ok((Object) file);
        responseBuilder.header("Content-Disposition", "attachment; filename='" + archivo + "'");
        String imgurl = responseBuilder.toString();
        
        
        return responseBuilder.build();
    }

   
   /*/Nuevo metodo
   @POST
    @Path("/upload")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String uploadImage(@FormParam("foto") String foto) {
        String result="false";
        
        //decode Base64 String to image
        try{           
            FileOutputStream fos = new FileOutputStream(RUTA+"foto.jpg");
            byte byteArray[] = Base64.decodeBase64(foto);
            fos.write(byteArray);
             
            result="true";
            fos.close();        
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
   //Fin nuevometodo de subir archivo*/
   
} //Fin clase

