/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.isil.javacrudmvc.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author maria
 */
public class ConexionDB {

    private Connection conexion;
    private static ConexionDB instancia;
    private String url;

    //constructor
    private ConexionDB() {

        try {
              //PASO 01: Definir los parametros de la conexion
            String username ="root";
            String password ="root";
            String host ="localhost";
            String database ="bd_ejemplo";
            String puerto= "3306";
            
            //si quiero otra como ysql o oravle ,cambio el driver y la url
            //PASO 02:cargar el driver o controlador o dependencia MYSQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            //PASO 03:Conectarnos a la BD
            String url = "jdbc:mysql://"+host + ":" + puerto +  "/" + 
                    database + "?useSSL=false&serverTimezone=America/Lima";
            
            this.conexion = DriverManager.getConnection(url, username , password);
            System.out.println("Conexion a db exitosa!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
        
    public Connection getConexion() {
        return conexion;
    }

    public static ConexionDB getInstancia() {

        if (ConexionDB.instancia != null) {
            return ConexionDB.instancia;
        }
        ConexionDB.instancia = new ConexionDB();
        return ConexionDB.instancia;
    }

    public void close() {
        try {
            this.conexion.close();
            instancia = null;
            if (this.conexion.isClosed()) {
                System.out.println("Se cerro la conexión a la db");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}

