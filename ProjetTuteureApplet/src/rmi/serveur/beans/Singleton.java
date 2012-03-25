/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.serveur.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Loic
 */

 public final class Singleton {
     private static volatile Singleton instance = null;
 
     public final static Singleton getInstance() throws Exception{
         if (Singleton.instance == null) {
            synchronized(Singleton.class) {
              if (Singleton.instance == null) {
                Singleton.instance = new Singleton();
              }
            }
         }
         return Singleton.instance;
     }

     private Connection con;

     private Singleton() throws Exception {
         Class.forName("com.mysql.jdbc.Driver");
            
         String url = "jdbc:mysql://localhost:3306/unjeu";
         String user = "root";
         String password = null;
         con = DriverManager.getConnection( url, user, password ) ;
     }
 
     public Connection getConnection(){
         return con;
     }
 }