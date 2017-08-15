/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comun.Recurso;
import java.sql.ResultSet;  
import org.json.JSONArray;  
import org.json.JSONObject;  

/**
 *
 * @author cardoso
 */
public class Conversor {

 
    public static JSONArray convertirAJSON(ResultSet rs)
            throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (rs.next()) {
            int total_cols = rs.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_cols; i++) {
                obj.put(rs.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase(), rs.getObject(i + 1)==null?"":rs.getObject(i + 1).toString());                       
            }
            jsonArray.put(obj);
        }
        return jsonArray;
    }
   
    public static String convertirAXML(ResultSet rs)
            throws Exception {
        StringBuffer xmlArray = new StringBuffer("<filas>");
        while (rs.next()) {
            int total_cols = rs.getMetaData().getColumnCount();
            xmlArray.append("<fila ");
            for (int i = 0; i < total_cols; i++) {
                xmlArray.append(" " + rs.getMetaData().getColumnLabel(i + 1)
                .toLowerCase() + "='" + rs.getObject(i + 1)==null?"":rs.getObject(i + 1).toString() + "'"); 
            }
            xmlArray.append(" />");
        }
        xmlArray.append("</filas>");
        return xmlArray.toString();
    }

    public static Object convertirAJSON() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static Object convertirAJSON(String ok) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

