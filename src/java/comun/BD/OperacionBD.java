/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comun.BD;


import comun.Recurso.Conversor;
import comun.Recurso.Formato;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
/**
 *
 * @author cardoso
 */
public class OperacionBD {
    private static Conexion poolDeConexion = null; 
    private static  Connection conn = null;
    //---
      
    public static void iniciaroperacion() throws SQLException {
        poolDeConexion=Conexion.getInstance();
        conn =poolDeConexion.conectar();
        //conn.setAutoCommit(false);
         }
   
    public static void confirmaroperacion() throws SQLException{
        try{
            conn.commit(); 
            conn.close();
        }catch(SQLException e)
            {
                deshaceroperacion();
                throw e;
            }
        finally{
        try {
                if (conn!= null && !conn.isClosed()) {
                    conn.close();
                 }                
            } 
            catch(SQLException e) {
            
                System.out.println("Ocurrió un error al cerrar la conexión de la Base de Datos:"+e.getMessage());
            }
        }
    }
    
     public static void deshaceroperacion() throws SQLException{
              try{
                    if (conn!=null)    
                        conn.rollback(); 
        }catch(SQLException e)
            {
                throw e;
            }
        finally{
        try {
                if (conn!= null && !conn.isClosed()) {
                    conn.close();
                 }                
            } 
            catch(SQLException e) {
            
                System.out.println("Ocurrió un error al cerrar la conexión de la Base de Datos:"+e.getMessage());
            }
        }
    }
    public static String consulta (String sql, List<Parametro> parametros, Formato formato) 
            throws SQLException, ParseException, Exception{
         JSONObject json = new JSONObject();
        
      
       
        PreparedStatement stmt = null;
        ResultSet rs=null;
        String respuesta="";
        try{
            
           stmt = conn.prepareStatement(sql);
           for (Parametro p: parametros ){
               asignartipo(stmt,p );
           }
           rs= stmt.executeQuery();
           
          // if (formato==Formato.JSON) json.put("json",respuesta= Conversor.convertirAJSON(rs).toString());
                              
          if (formato==Formato.JSON) respuesta= Conversor.convertirAJSON(rs).toString();
            
           else if (formato==Formato.XML)   respuesta= Conversor.convertirAXML(rs).toString();
         rs.close();
            
         }catch(SQLException e)
            {
                throw e;
            }
        finally{ 
             if (rs!=null )rs.close(); 
             if (stmt!=null )stmt.close();  
        }
        return respuesta;
    } //--- fin consulta
    
       public static String accion(String sql, List<Parametro> parametros) 
               throws ParseException, SQLException, JSONException{
           String resultado="";
         PreparedStatement stmt = null;
        Integer respuesta=-1;
        //resultado=respuesta.toString();
            try{
            
           stmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
           ResultSet rs = stmt.getGeneratedKeys();
if (rs != null && rs.next()) {
  long llave = rs.getLong(1);
  resultado=String.valueOf(llave);
  
}
           for (Parametro p: parametros ){
               asignartipo(stmt,p );
           }
                     ///
           

           ///
           respuesta= stmt.executeUpdate();
         // resultado=respuesta.toString();
 
            
         }catch(SQLException e)
            {
                
                deshaceroperacion();
            respuesta=0; 
           // resultado=respuesta.toString();
                          
                throw e;
               
            }
                
        finally{ 
               if (stmt!=null )stmt.close();  
        }
        //return respuesta.toString();
        return resultado.toString();
    }
     
    public static void asignartipo(PreparedStatement stmt, Parametro parametro ) throws SQLException, ParseException{
        switch (parametro.tipodato){
            case  VARCHAR:  case CHAR: case LONGVARCHAR:
                stmt.setString(parametro.posicion, parametro.valor);
                break;
            case BIT:
                stmt.setBoolean(parametro.posicion, Boolean.parseBoolean(parametro.valor));
                break;
            case NUMERIC: case DECIMAL:
                stmt.setBigDecimal(parametro.posicion,  new BigDecimal(parametro.valor) );
                break;
            case TINYINT:
                stmt.setByte(parametro.posicion, Byte.parseByte(parametro.valor));
                break;
            case SMALLINT:
                stmt.setShort(parametro.posicion, Short.parseShort(parametro.valor));
                break;
            case INTEGER:
                stmt.setInt(parametro.posicion, Integer.parseInt(parametro.valor) );
                break;
            case BIGINT: case LONG:
                stmt.setLong(parametro.posicion, Long.parseLong(parametro.valor));
                break;
            case REAL: case FLOAT:
                stmt.setFloat(parametro.posicion, Float.parseFloat(parametro.valor));
                break;
            case DOUBLE:
                stmt.setDouble(parametro.posicion, Double.parseDouble(parametro.valor) );
                break;
            case VARBINARY:  case BINARY:
                 byte[] bytes = parametro.valor.getBytes();
                stmt.setBytes(parametro.posicion,  bytes);
                break;
            case DATE:
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date fecha = sdf.parse(parametro.valor);
                java.sql.Date sqlFecha = new Date(fecha.getTime());
                stmt.setDate(parametro.posicion, sqlFecha);
                break;
            case TIME:
                SimpleDateFormat sdh = new SimpleDateFormat("hh:mm:ss");
                java.util.Date hora = sdh.parse(parametro.valor);
                java.sql.Time sqlHora = new java.sql.Time (hora.getTime());
                stmt.setTime(parametro.posicion, sqlHora);
                break;
            case TIMESTAMP:
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                java.util.Date fechats = formato.parse(parametro.valor);
                java.sql.Timestamp timestamp = new java.sql.Timestamp(fechats.getTime());
                stmt.setTimestamp(parametro.posicion, timestamp);
                break;
            case CLOB: case BLOB:  break;
            case ARRAY: break;
            case REF: break;
            case STRUCT: break;
            //--- Estos   ultimos aun faltan por implementar
                
        }
        
    }
}
