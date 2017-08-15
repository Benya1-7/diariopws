/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import comun.Recurso.Formato;
import comun.BD.OperacionBD;
import comun.BD.Parametro;
import comun.Recurso.Tipo;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * REST Web Service
 *
 * @author cardoso 
 * Fecha: 10-Marzo-2017
 */
@Path("/main")

public class Main  { 

  @Path("/status")
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String getStatus() {
    return "Web service diario pedagógico  en funcionamiento ...";
  }//--- fin getStatus
  
  @Path("/version")
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String getVersion() {
    return "1.0";
  } //--- fin GET Versión  
}//--- fin main