/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Usuario
 */
public class clsConectaBD {
    
    private String atrUser, atrPass, atrUrlConectar, atrDriver;
    public Connection atrConexion;
    private java.sql.Statement atrStatement;
    
    /**
    * Constructor por defecto de la clase clsConectaBD no parametrizado
    */
    public clsConectaBD(){
        
    }
    
    public void Conectar(){
        atrUser = "postgres";
        atrPass = "postgres";
        atrDriver = "org.postgresql.Driver";
        atrUrlConectar = "jdbc:postgresql://localhost/Prueba";
        try {
            Class.forName(atrDriver);
            atrConexion = DriverManager.getConnection(atrUrlConectar, atrUser, atrPass);
            System.out.println("La conexión se ha realizado con éxito.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("La Conexión ha fallado.");
        }
    }
    
    public void Desconectar(){
        try {
            atrConexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet RetornarConsulta(String prmCadConsulta){
        ResultSet varResultado = null;
        try {
            atrStatement = atrConexion.createStatement();
            varResultado = atrStatement.executeQuery(prmCadConsulta);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return varResultado;
    }
    
    public void CerrarResultado(ResultSet prmResultado){
        try {
            prmResultado.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean Transaccion(String prmCadSql){
        try {
            atrStatement = atrConexion.createStatement();
            atrStatement.execute(prmCadSql);
            atrStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    	public static void main(String args[]) {
		clsConectaBD cc = new clsConectaBD();
		cc.Conectar();
		if (cc.Transaccion("insert into abogado values (1234, 'Juan Perez', 'downtown', 123123, 'working');")) {
			System.out.println("Inserción exitosa");
		}
		else {
			System.out.println("Inserción No exitosa");
		}
	}
}
