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
import comun.Validaciones.ValRegistroUser;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import comun.BD.GetIDs;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TOSHIBA
 */
public class AddRol {

    
    
    public static String agregarrol(String idusuario) {
        String estatus="1";
            String idrol="1";
            String respuesta="";
            
        try {
             
            OperacionBD.iniciaroperacion();
            String sql ="INSERT INTO usuariorol (idrol, idusuario, estatus)"
                          + "VALUES (?,?,?);";
            List<Parametro> parametros= new  ArrayList<>();
            parametros.add(new Parametro(1,idrol,Tipo.INTEGER));
            parametros.add(new Parametro(2,idusuario,Tipo.INTEGER));
            parametros.add(new Parametro(3,estatus,Tipo.INTEGER));
            String json = OperacionBD.consulta(sql, parametros,Formato.JSON);
            parametros.clear();
            OperacionBD.confirmaroperacion();
            respuesta=json.toString();
        } //--- fin listidpublicacion
        catch (ParseException ex) {
            Logger.getLogger(AddRol.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AddRol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }
    
    
    
    
    
    
    
    
    
}
