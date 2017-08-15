/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comun.BD;

import comun.Recurso.Tipo;
import java.io.InputStream;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

/**
 *
 * @author cardoso
 */
public class Parametro {
    public Integer posicion;
    public String valor;
    public Tipo tipodato;
    
    public Parametro (Integer posicion, String valor,  Tipo tipodato){
        this.posicion=posicion;
        this.valor=valor;
        this.tipodato=tipodato;
    }

    public Parametro(int i, InputStream foto, Tipo tipo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Parametro(int i, Tipo tipo, Tipo tipo0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Parametro(int i, FormDataContentDisposition foto, Tipo tipo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
