/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comun.Recurso;

import org.codehaus.jackson.annotate.JsonProperty;

public class Mensaje {

	private Integer codigo;
	private String mensaje;
	
	public Mensaje() {
	}

	@JsonProperty(value = "codigo")
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
        
	@JsonProperty(value = "mensaje")
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
        

}
