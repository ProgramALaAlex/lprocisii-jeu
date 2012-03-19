/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Loic
 */

 public final class Singleton {
     private static volatile Singleton instance = null;
 
     private Connection con;

     private Singleton() throws Exception {
         Class.forName("com.mysql.jdbc.Driver");
            
         String url = "jdbc:mysql://localhost:3306/unjeu";
         String user = "root";
         String password = null;
         con = DriverManager.getConnection( url, user, password ) ;
     }

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
 
     public Connection getConnection(){
         return con;
     }
 }