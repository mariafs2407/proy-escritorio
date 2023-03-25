/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.isil.javacrudmvc.DAO;

import java.sql.PreparedStatement;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pe.isil.javacrudmvc.modelo.ConexionDB;
import pe.isil.javacrudmvc.modelo.Persona;

/**
 *
 * @author maria
 */
public class AD_Persona {
    
    private PreparedStatement pst = null;
    private ResultSet rst;
    
    public   List getAll(){
        List<Persona> listado= new ArrayList<Persona>();
        Persona persona;
        Connection Conexion=null;
        
        try {
            Conexion =ConexionDB.getInstancia().getConexion();
            if(Conexion !=null){
                String SQL= "SELECT*FROM persona";
                pst =Conexion.prepareStatement(SQL);
                
                rst = pst.executeQuery();
                while(rst.next()){
                   persona = new Persona();
                   persona.setId(rst.getInt("id"));
                   persona.setNombres(rst.getString("nombres"));
                   persona.setCorreo(rst.getString("correo"));
                   persona.setTelefono(rst.getInt("telefono"));
                   
                   listado.add(persona);
                }
                
            }else{
                System.out.println("Error en la conexion ala bd");
            }
        } catch (SQLException e) {
             System.out.println(e.getMessage());//mostrar mensaje de error       
        } finally {
             ConexionDB.getInstancia().close();
        }
        return listado;
    }
    
    public boolean Insertar(Persona p){
        boolean resultado= false;
        Connection Conexion= null;
        try {
           //1.ABRIR Conexion           
           //Conexion = ConexionDB.getInstancia().getConexion();
           ConexionDB conexionDB =ConexionDB.getInstancia();
           Conexion =  conexionDB.getConexion();
           //validamos q la conexion es diferente de nulo
           if(Conexion != null){
               //2.sql insert
               String SQL="INSERT INTO persona(nombres,correo,telefono) VALUES(?,?,?)";
               
               //3.Asignar el valor a los parametros,clase pst(parepareStament)
               //creando un objeto pst, de la clase preparedstament 
               //prepara el codigo de la consulta sql,con parametros y lo ejecuta
               PreparedStatement pst; 
               pst = Conexion.prepareStatement(SQL);
               //set(segun el tipo double,inter)
               pst.setString(1, p.getNombres());
               pst.setString(2, p.getCorreo());
               pst.setInt(3, p.getTelefono());
               //4.Ejecutamos la insercion
               int res;
               //ejecutamos la consulta SQL en la baase de datos
               //devuelve dos valores 1  y 0
               res= pst.executeUpdate();
               
               if(res>0){
                   resultado = true;//exito en la insercion
                  
               }else{
                   resultado = false;//fracaso en la insercion
                 
               }
                                  
           }else{
               System.out.println("Error en la conexion ala bd");
           }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());//mostrar mensaje de error
        } finally {
            ConexionDB.getInstancia().close();//cerramos la base de datos
        }
        return  resultado;
       
    }
    
    public boolean Actualizar(Persona p){
         boolean resultado= false;
         Connection Conexion= null;
        try {
           //1.ABRIR Conexion           
           //Conexion = ConexionDB.getInstancia().getConexion();
           ConexionDB conexionDB =ConexionDB.getInstancia();
           Conexion =  conexionDB.getConexion();
           //validamos q la conexion es diferente de nulo
           if(Conexion != null){
               //2.sql insert
               String SQL="UPDATE persona SET nombres=? ,correo=? ,telefono=?  WHERE id=? ";
               
               //3.Asignar el valor a los parametros,clase pst(parepareStament)
               //creando un objeto pst, de la clase preparedstament 
               //prepara el codigo de la consulta sql,con parametros y lo ejecuta
               PreparedStatement pst; 
               pst = Conexion.prepareStatement(SQL);
               //set(segun el tipo double,inter)
               pst.setString(1, p.getNombres());
               pst.setString(2, p.getCorreo());
               pst.setInt(3, p.getTelefono());
               pst.setInt(4, p.getId());
               //4.Ejecutamos la insercion
               int res;
               //ejecutamos la consulta SQL en la baase de datos
               //devuelve dos valores 1  y 0
               res= pst.executeUpdate();
               
               if(res>0){
                   resultado = true;//exito en la insercion
                  
               }else{
                   resultado = false;//fracaso en la insercion
                 
               }
                                  
           }else{
               System.out.println("Error en la conexion ala bd");
           }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());//mostrar mensaje de error
        } finally {
            ConexionDB.getInstancia().close();//cerramos la base de datos
        }
        return  resultado;
    }
}
