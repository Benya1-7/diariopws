/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comun.Objets;

/**
 *
 * @author TOSHIBA
 */
public class ObjPublicacion {
private Long idpublicacion, idusuario;

    public ObjPublicacion(Long idpublicacion, Long idusuario) {
        this.idpublicacion = idpublicacion;
        this.idusuario = idusuario;
    }
    
public ObjPublicacion(){}

    public Long getIdpublicacion() {
        return idpublicacion;
    }

    public void setIdpublicacion(Long idpublicacion) {
        this.idpublicacion = idpublicacion;
    }

    public Long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    
}
