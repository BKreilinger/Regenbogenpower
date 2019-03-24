package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import Model.Aktien;
import View.VIEW;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller extends Application{
	private VIEW view;
	private Aktien a;
	
	public Controller(){
		view = new VIEW();
		a = new Aktien();
	}
	public void wait(int z){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
	
	public static void main(String args[]) {
		launch(null);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		startup(primaryStage);
		
	}
	
	public void startup(Stage primaryStage) throws ClassNotFoundException, IOException, SQLException, InterruptedException {
		view.start(primaryStage);
		
	}
	
	public void login(String Benutzername) throws ClassNotFoundException, IOException, SQLException {
		a.start(Benutzername);
	}
	
	public double returnDAXStand() {
		return a.returnDAXStand();
	}
	public double returnAppleStand() {
		return a.returnAppleStand();
	}
	public double returnVWStand() {
		return a.returnVWStand();
	}
	
	
	public String returnDAXR() {
		return a.returnDAXR();
	}
	public String returnAppleR() {
		return a.returnAppleR();
	}
	public String returnVWR() {
		return a.returnVWR();
	}
	
	public int returnDAXZ() {
		return a.returnDAXZ();
	}
	public int returnAppleZ() {
		return a.returnAppleZ();
	}
	public int returnVWZ() {
		return a.returnVWZ();
	}
	
	public void getWebsiteData() {
		a.getWebsiteData();
	}
	
	public boolean returnKosten(String Aktien, double KontostandVorher, int AnzahlAktien) {
		boolean isTrue;
		
		if(a.returnAnzahlDAX() < AnzahlAktien) {
			isTrue = true;
		}
		else {
			isTrue = false;
		}
		return isTrue;
	}
	
	public double returnKontostand() {
		return a.returnKontoStand();
	}
	
	public double finallyKaufen(String Aktien, double KontostandVorher, int Anzahl) {
		return a.finallyKaufen(Aktien, KontostandVorher, Anzahl);
	}
}
