 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comun.Multimedia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author TOSHIBA
 */
public class SaveDownloadFile {
 // public static final String RUTA =  "/Users/TOSHIBA/Documents/archivos/";   //Ruta de almacenamiento local benja
  // public static final String RUTA =  "/Users/Any/Documents/archivos/prueba/";  //Ruta de almacenamiento local any
    
// public static final String RUTA =  "/home/diario/tomcat/documentsapp/";     //Ruta de almacenamiento en el servidor
 // public static final String RUTA = "/Users/Eduardo/Documents/Developer/Server/tomcat/archivosapp/";  // Ruta de almacenamiento local lalo
  //  public static final String RUTA = "/Users/Eduardo/Documents/Developer/Server/archivosapp/";  // Ruta de almacenamiento local lalo
  // public static final String RUTA = "/Users/un_an/OneDrive/Documentos/Developers/tomcat/archivosapp/";
   public static final String RUTA = "/archivosapp/";
  /**
     *
     * @param inputStream
     * @param fileName
     * @throws IOException
     */
   public static String guardar(InputStream archivo, String nombrearchivo) 
            throws IOException {
 
        OutputStream os = null;
        String rutaarchivo =  nombrearchivo;
 
        try {
            os = new FileOutputStream(new File(rutaarchivo));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = archivo.read(bytes)) != -1) {
                os.write(bytes, 0, read);
            }
            os.flush();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        finally{
            os.close();
        }
        return rutaarchivo;
    }

    /**
     *
     * @param multivaluedMap
     * @return
     */
   public static String crearCarpeta(String carpeta)
			throws SecurityException {
		File d = new File(carpeta);
		if (!d.exists()) {
			d.mkdir();
		}
       return carpeta;
	}  
 
   
    
}//Fin clase
