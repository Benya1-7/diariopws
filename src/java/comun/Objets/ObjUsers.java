/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comun.Objets;

import java.util.Date;

/**
 *
 * @author TOSHIBA
 */
public class ObjUsers {
    private Long idusuario;
    private String nombre,apellidos, genero, cuenta, password, correo, telefono, institucion, grupo, foto;
    private Date vigencia;


    public ObjUsers(Long idusuario, String nombre, String apellidos, String genero,
            String cuenta, String password, String correo, String telefono,
            String institucion, String grupo, String foto, Date vigencia) {
        this.idusuario = idusuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.cuenta = cuenta;
        this.password = password;
        this.correo = correo;
        this.telefono = telefono;
        this.institucion = institucion;
        this.grupo = grupo;
        this.foto = foto;
        this.vigencia = vigencia;
    }
       public ObjUsers() {
       
           }

    public Long getIdusuario() {
        return this.idusuario;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCuenta() {
        return this.cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getInstitucion() {
        return this.institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getGrupo() {
        return this.grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getFoto() {
        return this.foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Date getVigencia() {
        return this.vigencia;
    }

    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
    }
    
    }// Fin de Clase
