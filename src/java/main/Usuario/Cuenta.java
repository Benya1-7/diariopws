/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Usuario;

import comun.BD.OperacionBD;
import comun.BD.Parametro;
import comun.Recurso.Tipo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

/**
 *
 * @author TOSHIBA
 */
@Path("/acount")
public class Cuenta {
  /*       @Path("/updatep") 
     @PUT 
     @Produces(MediaType.APPLICATION_JSON)  
     public String updateinfPersonal(@FormParam("correo")String correo
                                      ,@FormParam("cuenta")String cuenta
                                                      
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
     } //Fin de Metodo update inf/personal */
}
