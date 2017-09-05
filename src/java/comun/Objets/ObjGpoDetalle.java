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
public class ObjGpoDetalle {
    private Long idgrupodetalle, idgrupo, idusuario, idrol;

    public ObjGpoDetalle(Long idgrupodetalle, Long idgrupo, Long idusuario, Long idrol) {
        this.idgrupodetalle = idgrupodetalle;
        this.idgrupo = idgrupo;
        this.idusuario = idusuario;
        this.idrol = idrol;
    }
   
    

    public ObjGpoDetalle() {
        
    }

    public void setIdgrupodetalle(Long idgrupodetalle) {
        this.idgrupodetalle = idgrupodetalle;
    }

    public void setIdgrupo(Long idgrupo) {
        this.idgrupo = idgrupo;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public void setIdrol(Long idrol) {
        this.idrol = idrol;
    }

    public Long getIdgrupodetalle() {
        return idgrupodetalle;
    }

    public Long getIdgrupo() {
        return idgrupo;
    }

    public Long getIdusuario() {
        return idusuario;
    }

    public Long getIdrol() {
        return idrol;
    }

    
    
}
