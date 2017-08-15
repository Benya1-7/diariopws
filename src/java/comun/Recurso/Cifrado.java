/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comun.Recurso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author cardoso 
 */
public class Cifrado {
    
    public static String sha2(String password) throws NoSuchAlgorithmException{
           MessageDigest md = MessageDigest.getInstance("SHA-256");
           md.update(password.getBytes());
           byte datos[]=md.digest();
           int longitud=datos.length;
           StringBuffer sb= new StringBuffer();
           for (int i=0; i<longitud; i++){
               sb.append(Integer.toString((datos[i]& 0xff)+ 0x100,16).substring(1));
           }
           return (sb.toString());
    }
}