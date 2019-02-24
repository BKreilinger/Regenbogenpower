package Model;

import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Aktien {
	private double Stand;
	private String Aktie;
	private String Apple, DAX, Volkswagen, Username;
	private int DAXP, AppleP, VolkswagenP;
	private Model m;
	private int AnzahlDax, AnzahlApple, AnzahlVolkswagen;
	
	public void start (String Aktie, String Benutzername) throws IOException, ClassNotFoundException, SQLException {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		Statement stmnt1 = con.createStatement();
		
		String sql1 = "Select * from benutzer where Benutzername ='" + Benutzername+ "'";
		ResultSet result1 = stmnt1.executeQuery(sql1);
		result1.next();
		AnzahlDax = ((Number) result1.getObject(4)).intValue();
		//AnzahlApple = ((Number) result1.getObject(5)).intValue();
		//AnzahlVolkswagen = ((Number) result1.getObject(6)).intValue();
		
		System.out.println(AnzahlDax);
		if(Aktie == "DAX") {
		try {
		String url = "https://www.finanzen.net/index/DAX";
		Document document = Jsoup.connect(url).get();
		Elements text = document.select("td[colspan=4]");
		String t1 = text.text();
		int size = 17;
		
		    if(t1 == null || size <= 0)
		        System.out.println("Nichts gefunden!");
		    int chunks = t1.length() / size + ((t1.length() % size > 0) ? 1 : 0);
		    String[] arr = new String[chunks];
		    for(int i = 0, j = 0, l = t1.length(); i < l; i += size, j++)
		        arr[j] = t1.substring(i, Math.min(l, i + size));
		    String u = arr[0];
		    int size1 = 9;
		    
		    if(u == null || size1 <= 0)
		        System.out.println("Nichts gefunden!");
		    int chunks1 = u.length() / size1 + ((u.length() % size1 > 0) ? 1 : 0);
		    String[] arr2 = new String[chunks1];
		    for(int i = 0, j = 0, l = u.length(); i < l; i += size1, j++)
		        arr2[j] = u.substring(i, Math.min(l, i + size1));
		    
		    DAX = arr2[1];
		    System.out.println(DAX);
		    
		    String p = arr2[0];
		    String pNew = p.replace(".", "");
		    String pNew1 = pNew.replace(",", ".");
		    
		    // p= Kurs in €
		    Stand = new Double(pNew1);
		    System.out.println(Stand);
		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println("RIP");
		}
		}
		else if(Aktie == "Apple") {
			try {
				String url = "https://www.finanzen.net/aktien/Apple-Aktie";
				Document document = Jsoup.connect(url).get();
				Elements text = document.select("td[colspan=4]");
				
				String t1 = text.text();
				int size = 10;
				
				    if(t1 == null || size <= 0)
				        System.out.println("Nichts gefunden!");
				    int chunks = t1.length() / size + ((t1.length() % size > 0) ? 1 : 0);
				    String[] arr = new String[chunks];
				    for(int i = 0, j = 0, l = t1.length(); i < l; i += size, j++)
				        arr[j] = t1.substring(i, Math.min(l, i + size));
				    String z = arr[2];				    
				    String u = arr[0];
				    
				    int size1 = 7;
				    if(u == null || size1 <= 0)
				        System.out.println("Nichts gefunden!");
				    int chunks1 = u.length() / size1 + ((u.length() % size1 > 0) ? 1 : 0);
				    String[] arr2 = new String[chunks1];
				    for(int i = 0, j = 0, l = u.length(); i < l; i += size1, j++)
				        arr2[j] = u.substring(i, Math.min(l, i + size1));
				    
				    
				    
				    int size2 = 8;
				    if(z == null || size2 <= 0)
				        System.out.println("Nichts gefunden!");
				    int chunks2 = z.length() / size2 + ((z.length() % size2 > 0) ? 1 : 0);
				    String[] arr3 = new String[chunks2];
				    for(int i = 0, j = 0, l = z.length(); i < l; i += size2, j++)
				        arr3[j] = z.substring(i, Math.min(l, i + size1));
				    
				    Apple = arr3[0];
				    System.out.println(Apple);				   
				    
				    String p = arr2[0];
				    String pNew = p.replace(".", "");
				    String pNew1 = pNew.replace(",", ".");
				    Double StandApple = new Double(pNew1);
				    System.out.println(StandApple);
				}
				catch (Exception e){
					e.printStackTrace();
					System.out.println("RIP");
				}
		}
		
		else if(Aktie == "Volkswagen") {
			try {
				String url = "https://www.finanzen.net/aktien/Volkswagen-Aktie";
				Document document = Jsoup.connect(url).get();
				Elements text = document.select("td[colspan=4]");
				
				String t1 = text.text();
				int size = 10;
				
				    if(t1 == null || size <= 0)
				        System.out.println("Nichts gefunden!");
				    int chunks = t1.length() / size + ((t1.length() % size > 0) ? 1 : 0);
				    String[] arr = new String[chunks];
				    for(int i = 0, j = 0, l = t1.length(); i < l; i += size, j++)
				        arr[j] = t1.substring(i, Math.min(l, i + size));
				    String z = arr[2];				    
				    String u = arr[0];
				    int size1 = 6;
				    
				    if(u == null || size1 <= 0)
				        System.out.println("Nichts gefunden!");
				    int chunks1 = u.length() / size1 + ((u.length() % size1 > 0) ? 1 : 0);
				    String[] arr2 = new String[chunks1];
				    for(int i = 0, j = 0, l = u.length(); i < l; i += size1, j++)
				        arr2[j] = u.substring(i, Math.min(l, i + size1));
				    
				    
				    
				    int size2 = 7;
				    if(z == null || size2 <= 0)
				        System.out.println("Nichts gefunden!");
				    int chunks2 = z.length() / size2 + ((z.length() % size2 > 0) ? 1 : 0);
				    String[] arr3 = new String[chunks2];
				    for(int i = 0, j = 0, l = z.length(); i < l; i += size2, j++)
				        arr3[j] = z.substring(i, Math.min(l, i + size1));
				    
				    Volkswagen = arr3[0];
				    System.out.println(Volkswagen);				   
				    
				    String p = arr2[0];
				    String pNew = p.replace(".", "");
				    String pNew1 = pNew.replace(",", ".");
				    Double StandVW = new Double(pNew1);
				    System.out.println(StandVW);
				}
				catch (Exception e){
					e.printStackTrace();
					System.out.println("RIP");
				}
		}
		
		
		else {
			System.out.println("RIIIP");
		}
	}
	
	public void update(int Minus) {
		
	}
	
	public void berechnen(int Aktien, int KontostandVorher) {
		
	}
	
	public void AktienBerechnen(double Aktie, int Anzahl) {
		
	}
	
	public void kaufen(String Aktie, int Anzahl) throws SQLException, ClassNotFoundException {
		if(Aktie == "DAX") {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
			Statement stmnt = con.createStatement();
			
			String sql = "Select PaketZahl from aktie where Name ='" + Aktie+ "'";
			ResultSet result = stmnt.executeQuery(sql);
			result.next();
			int r = ((Number) result.getObject(1)).intValue();
			//System.out.println(r);
			
			
			if(r < Anzahl) {
				System.out.println("Es gibt nicht genügend Aktienpakete zum Kaufen!");
			}
			else {
				//Update Datenbank
				DAXP = DAXP + Anzahl;
				String sql2 = "UPDATE benutzer SET Kontostand=, DAXZ =, AppleZ=, VolkswagenZ = WHERE Benutzername ='" + Username + "'";
			}
			
		}
		else if(Aktie == "Apple") {
			AppleP = AppleP + Anzahl;
		}
		else if(Aktie == "Volkswagen") {
			VolkswagenP = VolkswagenP + Anzahl;
		}
		
	}
	
	public String getBenutzername() {
		return this.Username;
	}
}
