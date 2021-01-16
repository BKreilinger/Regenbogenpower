package Controller;

import java.sql.*;

public class Test {


    public static Connection connect() {
        String url = "jdbc:sqlite:C:/Users/Benny/IdeaProjects/Regenbogenpower/databases/stocks.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void main(String[] arg){
        String Benutzername = "ZRO";
        String Passwort="test";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = connect();
            Statement stmnt = con.createStatement();
            String sql = "Select * from benutzer where Benutzername ='" + Benutzername + "' and Passwort ='" + Passwort + "'";
            ResultSet result = stmnt.executeQuery(sql);
            if(result.next()) {
                System.out.println("Eingeloggt!");

            }
            else {
                System.out.println("Daten sind falsch!");

            }
        }
        catch (Exception e){
            System.out.print(e);
        }

    }
}
