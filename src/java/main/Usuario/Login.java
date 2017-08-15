/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import comun.Recurso.Cifrado;
import comun.Recurso.Formato;
import comun.Recurso.Mensaje;
import comun.BD.OperacionBD;
import comun.BD.Parametro;
import comun.Recurso.Tipo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

/**
 *
 * @author TOSHIBA
 */
@Path("/user")

public class Login {
       String respuesta;
    
    @Path("/autenticar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response autenticar (
            @HeaderParam("correo")   String correo,//se recibe por header el correo para autenticación y contraseña
            @HeaderParam("password") String password)
            throws SQLException, Exception {
       JSONObject jsonlogin = new JSONObject();
        try{
           if (correo == null || correo.trim().equals("")) {
                Mensaje msg = new Mensaje();
                msg.setCodigo(Response.Status.UNAUTHORIZED.getStatusCode());
                msg.setMensaje("Falta el Correo!!!");
                return Response.status(Response.Status.PRECONDITION_FAILED.getStatusCode())
                        .entity(msg).build();
            }
            if (password == null || password.trim().equals("")) {
                Mensaje msg = new Mensaje();
                msg.setCodigo(Response.Status.PRECONDITION_FAILED.getStatusCode());
                msg.setMensaje("Falta la contraseña!!!");
                return Response.status(Response.Status.PRECONDITION_FAILED.getStatusCode())
                        .entity(msg).build();
            }
            OperacionBD.iniciaroperacion();
      String sql = " SELECT idusuario, cuenta, correo, telefono, foto "
                    + " FROM usuario "
                    + " WHERE correo=? AND password=?;";
      password=Cifrado.sha2(password);
      List<Parametro> parametros= new  ArrayList<>();
      parametros.add(new Parametro(1,correo,Tipo.VARCHAR));
      parametros.add(new Parametro(2,password,Tipo.VARCHAR));
      
     
      String json = OperacionBD.consulta(sql, parametros, Formato.JSON);
      parametros.clear();
     
      OperacionBD.confirmaroperacion();
   //   jsonlogin.put("Success",json.toString());
     if (json!=null) {
         jsonlogin.put("Success",json.toString());
        // return Response.status(200).entity(json.toString()).build();
            
        } else {
         jsonlogin.put("failed","ooopss!! Por favor verifique su usuario y Contraseña!!");
        // return Response.status(204).entity(json.toString()).build();
        }
    //  return Response.status(200).entity(json.toString()).build();
    } catch (SQLException e){
        Mensaje msg = new Mensaje();
                msg.setCodigo(Response.Status.UNAUTHORIZED.getStatusCode());
                msg.setMensaje("!!!");
    }     
        
    return Response.status(200).entity(jsonlogin.toString()).build();            
   //  return Response.status(200).entity().build();
    }
     
}
