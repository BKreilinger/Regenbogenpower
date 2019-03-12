package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Model {
	private String Benutzername;
	private String Pwd;
	private String message;
	private Boolean b, l;
	
	public boolean Anmelden1(String Benutzername, String Passwort) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
			Statement stmnt = con.createStatement();
			String sql = "Select * from benutzer where Benutzername ='" + Benutzername + "' and Passwort ='" + Passwort + "'";
			ResultSet result = stmnt.executeQuery(sql);
			if(result.next()) {
				System.out.println("Eingeloggt!");
				//this.Benutzername = ((String) result.getObject(1));
				b = true;
				//this.Benutzername = Benutzername;
				//System.out.println(Benutzername);
			}
			else {
				System.out.println("Daten sind falsch!");
				b = false;
			}
		}
		catch (Exception e){
			System.out.print(e);
		}
		return b;
	}
	
	public void regestrieren(String Name, String Passwort) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
			Statement stmnt = con.createStatement();
			String sql = "insert into benutzer "+ "values ('" + Name + "', '" + Passwort + "', 20000, 0, 0, 0 )";
			stmnt.executeUpdate(sql);
			System.out.println("Erfolgreich regestriert!");
			
			
		}
		
		catch (Exception e){
			System.out.print(e);
		}
	}
	
	public boolean löschen(String Name) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		Statement stmnt = con.createStatement();
		
		String sql = "Select * from benutzer where Benutzername ='" + Name + "'";
		ResultSet result = stmnt.executeQuery(sql);
		if(result.next()) {
			System.out.println("Benutzer gefunden!");
			l = true;
		
		try {
			
			String sql1 = "delete from benutzer "+ "where Benutzername ='" + Name+ "'";
			stmnt.executeUpdate(sql1);
		}
		
		catch (Exception e){
			System.out.print(e);
			l = false;
		}
		}
		else {
			l = false;
		}
		return l;
	}
	
	public String returnBenutzername() {
		return this.Benutzername;
	}
}
