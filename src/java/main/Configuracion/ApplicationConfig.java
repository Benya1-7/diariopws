/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Configuracion;

import comun.BD.Conexion;
import java.util.Set;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Application;

/**
 *
 * @author cardoso
 */
@javax.ws.rs.ApplicationPath("/api/1.0")
public class ApplicationConfig extends Application {
    
   public ApplicationConfig(){
        try{
            Context init = new InitialContext();
            Context ctx = (Context) init.lookup("java:comp/env");
            Conexion.setContext(ctx);
            
            
          }catch(NamingException e){ System.out.println("ERROR: " + e.getMessage());
                  
          }
    }
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(main.Grupo.Grupo.class);
        resources.add(main.Grupo.Grupodetalle.class);
        resources.add(main.Login.class);
        resources.add(main.Main.class);
        resources.add(main.Publicacion.Archivo.class);
        resources.add(main.Publicacion.Detallepublicacion.class);
        resources.add(main.Publicacion.Grupopublicacion.class);
        resources.add(main.Publicacion.Publicacion.class);
        resources.add(main.Usuario.Fotousuario.class);
        resources.add(main.Usuario.Rol.class);
        resources.add(main.Usuario.Usuario.class);
        resources.add(main.UsuariopImagen.class);
        resources.add(main.Usuariorol.class);
        
        
    }
    
}
