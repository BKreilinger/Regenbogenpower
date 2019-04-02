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
	private double StandDAX = -1;
	private double StandApple = -1;
	private double StandVW = -1;
	private String Benutzername;
	
	private int InAktienZahlDAX, InAktienZahlApple, InAktienZahlVW;
	
	public void start (String Benutzername) throws IOException, ClassNotFoundException, SQLException {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Daheim
			//Connection con = DriverManager.getConnection("jdbc:mysql://192.168.178.74/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "idonno", "idonno");
			
			//Schule
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		Statement stmnt1 = con.createStatement();
		
		String sql1 = "Select * from benutzer where Benutzername ='" + Benutzername+ "'";
		ResultSet result1 = stmnt1.executeQuery(sql1);
		result1.next();
		
		this.Benutzername = ((String) result1.getObject(1).toString());
		AnzahlDax = ((Number) result1.getObject(4)).intValue();
		AnzahlApple = ((Number) result1.getObject(5)).intValue();
		AnzahlVolkswagen = ((Number) result1.getObject(6)).intValue();
		this.Stand = new Double((double) result1.getObject(3));
	}
	
	public void getAktienZahl() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		//Connection con = DriverManager.getConnection("jdbc:mysql://192.168.178.74/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "idonno", "idonno");
		
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		
		Statement stmnt1 = con.createStatement();
		String sql1 = "Select * from aktie where Name = 'DAX'";
		ResultSet result1 = stmnt1.executeQuery(sql1);
		result1.next();
		// 																							ZahlDAX
		InAktienZahlDAX = ((Number) result1.getObject(2)).intValue();
		
		Statement stmntAktien = con.createStatement();
		String sqlAktien = "Select * from aktie WHERE Name = 'Apple'";
		ResultSet resultAktien = stmntAktien.executeQuery(sqlAktien);
		resultAktien.next();
		//																							ZahlApple
		InAktienZahlApple = ((Number) resultAktien.getObject(2)).intValue();
		
		Statement stmntAktienVW = con.createStatement();
		String sqlAktienVW = "Select * from aktie WHERE Name = 'VW'";
		ResultSet resultAktienVW = stmntAktienVW.executeQuery(sqlAktienVW);
		resultAktienVW.next();
		//																							ZahlVW
		InAktienZahlVW = ((Number) resultAktienVW.getObject(2)).intValue();
		
		
	}
	
	public void getWebsiteData() {
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
			    
			    String p = arr2[0];
			    String pNew = p.replace(".", "");
			    String pNew1 = pNew.replace(",", ".");
			    this.StandDAX = new Double(pNew1);
			    this.StandDAX = StandDAX / 100.0;
			    //System.out.println(StandDAX);
			    
			}
			catch (Exception e){
				e.printStackTrace();
				System.out.println("RIP");
			}
		
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
			    
			    String p = arr2[0];
			    String pNew = p.replace(".", "");
			    String pNew1 = pNew.replace(",", ".");
			    StandApple = new Double(pNew1);
			}
			catch (Exception e){
				e.printStackTrace();
				System.out.println("RIP");
			
	}
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
			    				    
			    String p = arr2[0];
			    String pNew = p.replace(".", "");
			    String pNew1 = pNew.replace(",", ".");
			    StandVW = new Double(pNew1);
			    
			}
			catch (Exception e){
				e.printStackTrace();
				System.out.println("RIP");
			}
	}
	
	
	

	public void update(int Minus) {
		
	}
	
	public double Kostenberechnen(String Aktien, double KontostandVorher, int AnzahlAktien, double AktienStand) {
		double Kosten = -1;
		if(Aktien == "DAX") {
			Kosten = AktienStand * AnzahlAktien;
		}
		else if(Aktien == "Apple") {
			Kosten = AktienStand * AnzahlAktien;
		}
		else if(Aktien == "VW") {
			Kosten = AktienStand * AnzahlAktien;
		}
		else {
			//Error
		}
		
		return Kosten;
	}
	
	public void finallyKaufen(String Aktien, double KontostandVorher, int AnzahlAktien, double AktienStand) throws ClassNotFoundException, SQLException {
		double updateKonto = -1;
		int ZahlDAXBenutzer = -1;
		Class.forName("com.mysql.cj.jdbc.Driver");
		//Connection con1 = DriverManager.getConnection("jdbc:mysql://192.168.178.74/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "idonno", "idonno");
		Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		Statement stmnt = con1.createStatement();
		
	
		if(Aktien == "DAX") {
			if(Kostenberechnen("Aktien", KontostandVorher, AnzahlAktien, AktienStand) > KontostandVorher || InAktienZahlDAX < AnzahlAktien) {
				
			}
			else {
			updateKonto = KontostandVorher - Kostenberechnen("DAX", KontostandVorher, AnzahlAktien, AktienStand);
			AnzahlDax = AnzahlDax + AnzahlAktien;
			InAktienZahlDAX = InAktienZahlDAX - AnzahlAktien;
			
			String sqlA = "UPDATE aktie SET AnzahlPakete='" + InAktienZahlDAX + "' WHERE Name = 'DAX'";
			stmnt.executeUpdate(sqlA);
			
			
			String sql = "UPDATE benutzer SET Kontostand='" + updateKonto + "', DAXZ='" + AnzahlDax + "' WHERE Benutzername ='" + this.Benutzername + "'";
			stmnt.executeUpdate(sql);
			}
		}
		else if(Aktien == "Apple") {
			updateKonto = KontostandVorher - Kostenberechnen("Apple", KontostandVorher, AnzahlAktien,AktienStand);
			this.AnzahlApple = this.AnzahlApple + AnzahlAktien;
			System.out.println(this.AnzahlApple);
			InAktienZahlApple = InAktienZahlApple - AnzahlAktien;
			
			String sqlA = "UPDATE aktie SET AnzahlPakete='" + InAktienZahlApple + "' WHERE Name = 'Apple'";
			stmnt.executeUpdate(sqlA);
			
			String sql = "UPDATE benutzer SET Kontostand='" + updateKonto + "', AppleZ='" + AnzahlApple + "' WHERE Benutzername ='" + this.Benutzername + "'";
			stmnt.executeUpdate(sql);
			
		}
		else if(Aktien == "VW") {
			if(Kostenberechnen("Aktien", KontostandVorher, AnzahlAktien, AktienStand) > KontostandVorher || InAktienZahlVW < AnzahlAktien) {
				
			}
			else {
			updateKonto = KontostandVorher - Kostenberechnen("VW", KontostandVorher, AnzahlAktien, AktienStand);
			AnzahlVolkswagen = AnzahlVolkswagen + AnzahlAktien;
			InAktienZahlVW = InAktienZahlVW - AnzahlAktien;
			
			String sqlA = "UPDATE aktie SET AnzahlPakete='" + InAktienZahlVW + "' WHERE Name = 'VW'";
			stmnt.executeUpdate(sqlA);
			
			String sql = "UPDATE benutzer SET Kontostand='" + updateKonto + "', VolkswagenZ='" + AnzahlVolkswagen + "' WHERE Benutzername ='" + this.Benutzername + "'";
			stmnt.executeUpdate(sql);
			}
		}
		else {
			//Error
		}
		
		this.Stand = updateKonto;
	}
	
	public void Verkaufen(String Aktie, double KontostandVorher, int Anzahl, double Stand) throws SQLException, ClassNotFoundException {
		
		double updateKonto = -1;
		Class.forName("com.mysql.cj.jdbc.Driver");
		//Connection con1 = DriverManager.getConnection("jdbc:mysql://192.168.178.74/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "idonno", "idonno");
		Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		Statement stmnt = con1.createStatement();
		
	
		if(Aktie == "DAX") {
			updateKonto = KontostandVorher + Kostenberechnen("DAX", KontostandVorher, Anzahl, Stand);
			AnzahlDax = AnzahlDax - Anzahl;
			InAktienZahlDAX = InAktienZahlDAX + Anzahl;
			
			String sqlA = "UPDATE aktie SET AnzahlPakete='" + InAktienZahlDAX + "' WHERE Name = 'DAX'";
			stmnt.executeUpdate(sqlA);
			
			
			String sql = "UPDATE benutzer SET Kontostand='" + updateKonto + "', DAXZ='" + AnzahlDax + "' WHERE Benutzername ='" + this.Benutzername + "'";
			stmnt.executeUpdate(sql);
			
		}
		else if(Aktie == "Apple") {
			updateKonto = KontostandVorher + Kostenberechnen("Apple", KontostandVorher, Anzahl,Stand);
			this.AnzahlApple = this.AnzahlApple - Anzahl;
			InAktienZahlApple = InAktienZahlApple + Anzahl;
			
			String sqlA = "UPDATE aktie SET AnzahlPakete='" + InAktienZahlApple + "' WHERE Name = 'Apple'";
			stmnt.executeUpdate(sqlA);
			
			String sql = "UPDATE benutzer SET Kontostand='" + updateKonto + "', AppleZ='" + AnzahlApple + "' WHERE Benutzername ='" + this.Benutzername + "'";
			stmnt.executeUpdate(sql);
			
		}
		else if(Aktie == "VW") {

			updateKonto = KontostandVorher + Kostenberechnen("VW", KontostandVorher, Anzahl, Stand);
			AnzahlVolkswagen = AnzahlVolkswagen - Anzahl;
			InAktienZahlVW = InAktienZahlVW + Anzahl;
			
			String sqlA = "UPDATE aktie SET AnzahlPakete='" + InAktienZahlVW + "' WHERE Name = 'VW'";
			stmnt.executeUpdate(sqlA);
			
			String sql = "UPDATE benutzer SET Kontostand='" + updateKonto + "', VolkswagenZ='" + AnzahlVolkswagen + "' WHERE Benutzername ='" + this.Benutzername + "'";
			stmnt.executeUpdate(sql);
			
		}
		else {
			//Error
		}
		
		this.Stand = updateKonto;
	}
	
	
	public double returnDAXStand() {
		return StandDAX;
	}

	public double returnAppleStand() {
		return StandApple;
	}

	public double returnVWStand() {
		return StandVW;
	}
	
	public String returnDAXR() {
		return DAX;
	}
	
	public String returnAppleR() {
		//System.out.println(Apple);
		return Apple;
	}
	
	public String returnVWR() {
		return Volkswagen;
	}
	
	public int returnDAXZ() {
		return AnzahlDax;
	}
	
	public int returnAppleZ() {
		return AnzahlApple;
	}
	
	public int returnVWZ() {
		return AnzahlVolkswagen;
	}
	
	public void AktienBerechnen(double Aktie, int Anzahl) {
		
	}
	
	public void AppleK() {
		
	}
	
	public void VWK() {
		
	}
	
	public void updateAktienPakete() {
		
	}
	
	public String getBenutzername() {
		return this.Username;
	}
	
	public double returnKontoStand() {
		return this.Stand;
	}
	
	public int returnAnzahlDAX() {
		return this.AnzahlDax;
	}
	
	public int returnAnzahlApple() {
		return this.AnzahlApple;
	}
	
	public int returnAnzahlVW() {
		return this.AnzahlVolkswagen;
	}
	
	public int returnInDAX() {
		return this.InAktienZahlDAX;
	}
	
	public int returnInApple() {
		return this.InAktienZahlApple;
	}
	
	public int returnInVW() {
		return this.InAktienZahlVW;
	}
}
