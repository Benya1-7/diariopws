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
public class ObjGrupo {
    private Long idgrupo, usuarioalumno;
    private String nombregrupo,nombreusuario;

    public ObjGrupo(Long idgrupo, Long usuarioalumno, String nombregrupo, String nombreusuario) {
        this.idgrupo = idgrupo;
        this.usuarioalumno = usuarioalumno;
        this.nombregrupo = nombregrupo;
        this.nombreusuario = nombreusuario;
    }

    public ObjGrupo() {
        
    }

    public void setIdgrupo(Long idgrupo) {
        this.idgrupo = idgrupo;
    }

    public void setUsuarioalumno(Long usuarioalumno) {
        this.usuarioalumno = usuarioalumno;
    }

    public void setNombregrupo(String nombregrupo) {
        this.nombregrupo = nombregrupo;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public Long getIdgrupo() {
        return this.idgrupo;
    }

    public Long getUsuarioalumno() {
        return this.usuarioalumno;
    }

    public String getNombregrupo() {
        return this.nombregrupo;
    }

    public String getNombreusuario() {
        return this.nombreusuario;
    }
    

    
}
