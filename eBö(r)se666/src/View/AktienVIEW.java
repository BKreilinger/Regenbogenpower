package View;

import java.io.IOException;
import java.sql.SQLException;

import Model.Aktien;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AktienVIEW{
	private Stage primaryStage;
	private Aktien b = new Aktien();
	private GridPane grid2, grid;
	private VIEW v;
	private Scene scene, sceneDAX, sceneApple, sceneVW;
	private String Benutzername;
	private HBox hbox3;
	
	public AktienVIEW(String Benutzername) {
		b.getWebsiteData();
		this.Benutzername = Benutzername;
		try {
			b.start(Benutzername);
		} catch (ClassNotFoundException | IOException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void start(Stage primaryStage) throws ClassNotFoundException, SQLException {
		this.primaryStage = primaryStage;
		AnchorPane rootPane = new AnchorPane();
		
		HBox hbox = new HBox();
		HBox hbox1 = new HBox();
		hbox3 = new HBox();
		hbox.setPadding(new Insets(15,12,15,12));
		hbox.setSpacing(10);
		hbox1.setPadding(new Insets(15,12,15,12));
		hbox1.setSpacing(10);
		hbox3.setPadding(new Insets(15,12,15,12));
		hbox3.setSpacing(10);
		
		HBox profile = new HBox();
		profile.setPadding(new Insets(15,12,15,12));
		profile.setSpacing(10);
		
		grid2 = new GridPane();
		grid2.setPadding(new Insets(100,150,50,150));
        grid2.setVgap(15);
        grid2.setHgap(50);
        grid2.setAlignment(Pos.CENTER);
        
        grid = new GridPane();
		grid.setPadding(new Insets(100,150,50,150));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
		
		scene = new Scene(rootPane, 1280,720);
		scene.getStylesheets().add(getClass().getResource("NewFile.css").toExternalForm());
		
		
		Button back = new Button("Ausloggen");
		back.setPrefSize(100, 35);
		
		Button previous = new Button("Verlassen");
		previous.setPrefSize(100, 35);
		previous.setStyle("-fx-background-color: #AB4642");
		
		Button profileB = new Button("Profile");
		profileB.setPrefSize(100, 35);
		profileB.setStyle("-fx-background-color: #AB4642");
		profile.getChildren().add(profileB);
		
		profileB.setOnAction(event ->{
			profile();
		});
		
		rootPane.getChildren().addAll(hbox, hbox1, hbox3, grid2, grid, profile);
		
		hbox.getChildren().addAll(back);
		hbox1.getChildren().addAll(previous);
		rootPane.setLeftAnchor(hbox, 0.0);
		rootPane.setLeftAnchor(hbox1, 115.0);
		
		rootPane.setTopAnchor(grid2, 100.0);
		rootPane.setRightAnchor(grid2, 50.0);
		
		rootPane.setLeftAnchor(grid, 0.0);
		rootPane.setTopAnchor(grid, 100.0);
		
		rootPane.setRightAnchor(hbox3, 35.0);
		rootPane.setTopAnchor(hbox3, 15.0);
		
		rootPane.setLeftAnchor(profile, 230.0);
		rootPane.setTopAnchor(profile, 0.0);
		getAktienInfo();
		
		
		previous.setOnAction(event -> {
            primaryStage.close();
        });
		
		back.setOnAction(event -> {
				try {
					Ausloggen();
				} catch (ClassNotFoundException | IOException | SQLException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        });
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}
	
	public int AktienStandGeben(String Aktie) throws ClassNotFoundException, SQLException {
    	int returnStandA = -1;
    	b.getAktienZahl();
    	if(Aktie == "DAX") {
    		returnStandA = b.returnInDAX();
    	}
    	else if(Aktie == "Apple"){
    		returnStandA = b.returnInApple();
    	}
    	else {
    		returnStandA = b.returnInVW();
    	}
    	return returnStandA;
    }
	
	 public void Kaufen(String Aktien, double KontostandVorher, int Anzahl, double Stand ) throws ClassNotFoundException, SQLException {
	    	if(b.Kostenberechnen(Aktien, KontostandVorher, Anzahl, Stand) > KontostandVorher) {
	    		//Error
	    		System.out.println(AktienStandGeben(Aktien));
	    		System.out.println(b.Kostenberechnen(Aktien, KontostandVorher, Anzahl, Stand));
	    		System.out.println(KontostandVorher);
	    		System.out.println("Error Nicht genug Geld");
	    	}
	    	else if(AktienStandGeben(Aktien) < Anzahl){
	    		System.out.println("Error Nicht genügend Aktien vorhanden zum Kaufen!");
	    	}
	    	else {
	    		b.finallyKaufen(Aktien, KontostandVorher, Anzahl, Stand);
	    	}
	    }
	
	public void Verkaufen(String Aktien, double Kontostand, int Anzahl, double Stand) throws ClassNotFoundException, SQLException {
    	if(Anzahl > AktienStandGeben(Aktien)){
    		System.out.println("Error");
    	}
    	else {
    		b.Verkaufen(Aktien, Kontostand, Anzahl, Stand);
    	}
    }
	
	
	
	public void getAktienInfo() {
		Label Aktie = new Label("Aktienname");
		Aktie.setStyle("-fx-text-fill: aliceblue; -fx-font-size: 16px;-fx-underline: true;");
        Aktie.setTextFill(Color.RED);
        GridPane.setConstraints(Aktie,0,0);
        
        Label Aktienstand = new Label("Aktienstand");
		Aktienstand.setStyle("-fx-text-fill: aliceblue; -fx-font-size: 16px;-fx-underline: true;-fx-stroke: black;");
        Aktienstand.setTextFill(Color.RED);
        GridPane.setConstraints(Aktienstand,0,0);
        
        
        Label Aktienrate = new Label("Veränderungsrate");
		Aktienrate.setStyle("-fx-text-fill: aliceblue; -fx-font-size: 16px;-fx-underline: true;-fx-bold: true;");
        Aktienrate.setTextFill(Color.RED);
        GridPane.setConstraints(Aktienrate,1,0);
        
        Label Aktienzahl = new Label("Anzahl ihrer Aktienpakete");
		Aktienzahl.setStyle("-fx-text-fill: aliceblue; -fx-font-size: 16px;-fx-underline: true;");
        Aktienzahl.setTextFill(Color.RED);
        GridPane.setConstraints(Aktienzahl, 2,0);
		
		Hyperlink DAX = new Hyperlink("DAX");
        DAX.setStyle("-fx-text-fill: aliceblue; -fx-font-size: 16px;");
        DAX.setTextFill(Color.ALICEBLUE);
        GridPane.setConstraints(DAX,0,1);
        
        DAX.setOnAction(event -> {
            goToDAX();
        });
        
        Hyperlink Apple = new Hyperlink("Apple");
        Apple.setStyle("-fx-text-fill: aliceblue; -fx-font-size: 16px;");
        DAX.setTextFill(Color.ALICEBLUE);
        GridPane.setConstraints(Apple,0,2);
        
        Apple.setOnAction(event -> {
            goToApple();
        });

        Hyperlink VW = new Hyperlink("Volkswagen AG");
        VW.setStyle("-fx-text-fill: aliceblue; -fx-font-size: 16px;");
        DAX.setTextFill(Color.ALICEBLUE);
        GridPane.setConstraints(VW,0,3);
        
        VW.setOnAction(event -> {
            goToVW();
        });
		
    	Label  Standa= new Label("" + Math.round(b.returnDAXStand()*100)/100.00 + " €" );
        Standa.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px;");
        GridPane.setConstraints(Standa,0,1);
        GridPane.setHalignment(Standa, HPos.RIGHT);
        
        Label  Va= new Label("" + b.returnDAXR());
        Va.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px;");
        GridPane.setConstraints(Va,1,1);
        GridPane.setHalignment(Va, HPos.RIGHT);
        
        Label  Za= new Label("" + b.returnDAXZ());
        Za.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px;");
        GridPane.setConstraints(Za,2,1);
        GridPane.setHalignment(Za, HPos.RIGHT);
        
        
        Label  Standb= new Label("" + b.returnAppleStand()+ " €");
        Standb.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px;");
        GridPane.setConstraints(Standb,0,2);
        GridPane.setHalignment(Standb, HPos.RIGHT);
        
        Label  Vb= new Label("" + b.returnAppleR());
        Vb.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px;");
        GridPane.setConstraints(Vb,1,2);
        GridPane.setHalignment(Vb, HPos.RIGHT);
        
        Label  Zb= new Label("" + b.returnAppleZ());
        Zb.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px;");
        GridPane.setConstraints(Zb,2,2);
        GridPane.setHalignment(Zb, HPos.RIGHT);
        
        Label  Standc= new Label("" + b.returnVWStand() + " €");
        Standc.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px;");
        GridPane.setConstraints(Standc,0,3);
        GridPane.setHalignment(Standc, HPos.RIGHT);
        
        Label  Vc= new Label("" + b.returnVWR()+"%");
        Vc.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px;");
        GridPane.setConstraints(Vc,1,3);
        GridPane.setHalignment(Vc, HPos.RIGHT);
        
        Label  Zc= new Label("" + b.returnVWZ());
        Zc.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px;");
        GridPane.setConstraints(Zc,2,3);
        GridPane.setHalignment(Zc, HPos.RIGHT);
        
        Label Kontostand = new Label("Kontostand= " + Math.round(b.returnKontoStand()*100)/100.00 + "€");
        Kontostand.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 18px;");

        grid2.getChildren().addAll(Aktienstand, Aktienrate, Aktienzahl, Standa, Va, Za, Standb, Vb, Zb, Standc, Vc, Zc);
        grid.getChildren().addAll(DAX, Apple, VW, Aktie);
        hbox3.getChildren().add(Kontostand);
    }
	
	public void goToDAX() {
		AnchorPane ApplePane = new AnchorPane();
		HBox back = new HBox();
		HBox previous = new HBox();
		HBox Graph = new HBox();
		HBox TB = new HBox();
		HBox KS = new HBox();
		HBox week = new HBox();
		
		Graph.setPrefSize(700, 330);
		//Graph.setPadding(new Insets(15,12,15,12));
		//Graph.setSpacing(10);
		Graph.setId("DAXdaily");
		
		TB.setPrefSize(870, 500);
		//TB.setPadding(new Insets(15,12,15,12));
		//TB.setSpacing(10);
		
		ApplePane.setLeftAnchor(TB, 80.0);
		ApplePane.setTopAnchor(TB, 87.0);
		
		ApplePane.setLeftAnchor(week, 200.0);
		ApplePane.setTopAnchor(week, 87.0);
		
		ApplePane.setLeftAnchor(back, 0.0);
		ApplePane.setLeftAnchor(previous, 100.0);
		ApplePane.setLeftAnchor(Graph, 80.0);
		ApplePane.setTopAnchor(Graph, 140.0);
		
		ToggleButton tb1 = new ToggleButton("Daily");
		tb1.setPrefSize(100, 35);
		tb1.setStyle("-fx-background-color: #AB4642");
		TB.getChildren().add(tb1);
		
		ToggleButton tb2 = new ToggleButton("Weekly");
		tb2.setPrefSize(100, 35);
		tb2.setStyle("-fx-background-color: #ffffff");
		tb2.setSelected(false);
		week.getChildren().add(tb2);
		
		tb1.setOnAction(event -> {
			if(tb1.isSelected()==true) {
				tb1.setStyle("-fx-background-color: #AB4642");
				tb2.setStyle("-fx-background-color: #ffffff");
				tb2.setSelected(false);
				Graph.setId("DAXdaily");
			}
			else {
				
			}
			
        });
		
		tb2.setOnAction(event -> {
			if(tb2.isSelected()==true) {
				tb2.setStyle("-fx-background-color: #AB4642");
				tb1.setStyle("-fx-background-color: #ffffff");
				tb1.setSelected(false);
				Graph.setId("DAXweekly");
			}
			else {
				
			}
			
        });
		
		Label Ks = new Label("Kontostand= " + Math.round(b.returnKontoStand()*100)/100.00 + "€");
        Ks.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 18px;");
        
        KS.getChildren().add(Ks);
		
		Button backB = new Button("Back");
		backB.setPrefSize(80, 35);
		backB.setStyle("-fx-background-color: #AB4642");
		
		backB.setOnAction(event -> {
			try {
				start(this.primaryStage);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
		
		Button previousB = new Button("Previous");
		previousB.setPrefSize(80, 35);
		previousB.setStyle("-fx-background-color: #AB4642");
		
		Label AktienStand = new Label("Aktien Stand: " + Math.round(b.returnDAXStand()*100)/100.00 + "€");
		AktienStand.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 18px;");
		GridPane.setConstraints(AktienStand, 0,0);
		
		Label AktienRate = new Label("Aktien Stand: " + b.returnDAXR());
		AktienRate.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 18px;");
		GridPane.setConstraints(AktienRate, 0,1);
		
		GridPane gridPane = new GridPane();
		gridPane.getChildren().addAll(AktienStand, AktienRate);
		gridPane.setHgap(20);
		gridPane.setVgap(20);
		
		
		
		back.setPadding(new Insets(15,12,15,12));
		back.setSpacing(10);
		back.getChildren().add(backB);
		
		previous.setPadding(new Insets(15,12,15,12));
		previous.setSpacing(10);
		previous.getChildren().add(previousB);
		
		Button KaufMenue = new Button("Kaufen/ Verkaufen");
		KaufMenue.setPrefSize(110, 30);
		KaufMenue.setStyle("-fx-background-color: #AB4642");
		KaufMenue.setOnAction(event -> {
            try {
				initBuyMenu(primaryStage.getScene());
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        
        HBox Kaufmenue = new HBox();
        Kaufmenue.setSpacing(10);
        Kaufmenue.getChildren().add(KaufMenue);
        
        ApplePane.setRightAnchor(Kaufmenue, 20.0);
        ApplePane.setBottomAnchor(Kaufmenue, 20.0);
        ApplePane.setRightAnchor(KS, 35.0);
        ApplePane.setTopAnchor(KS, 15.0);
        
		ApplePane.getChildren().addAll(back, previous, Graph, KS, TB, Kaufmenue, week, gridPane);
		ApplePane.setRightAnchor(gridPane, 200.0);
        ApplePane.setTopAnchor(gridPane, 140.0);
		
		sceneDAX = new Scene(ApplePane, 1280, 720);
		sceneDAX.getStylesheets().addAll(this.getClass().getResource("NewFile.css").toExternalForm());
		

		primaryStage.setScene(sceneDAX);
	}
	
	public void goToApple() {
		AnchorPane ApplePane = new AnchorPane();
		HBox back = new HBox();
		HBox previous = new HBox();
		HBox Graph2 = new HBox();
		HBox TB = new HBox();
		HBox KS = new HBox();
		HBox week = new HBox();
		
		Graph2.setPrefSize(700, 330);
		//Graph.setPadding(new Insets(0,0,0,0));
		//Graph.setSpacing(10);
		Graph2.setId("appledaily");
		
		TB.setPrefSize(100, 35);
		//TB.setPadding(new Insets(0,0,0,0));
		//TB.setSpacing(10);
		
		ApplePane.setLeftAnchor(TB, 80.0);
		ApplePane.setTopAnchor(TB, 87.0);
		
		ApplePane.setLeftAnchor(week, 200.0);
		ApplePane.setTopAnchor(week, 87.0);
		
		ApplePane.setLeftAnchor(back, 00.0);
		ApplePane.setLeftAnchor(previous, 100.0);
		ApplePane.setLeftAnchor(Graph2, 80.0);
		ApplePane.setTopAnchor(Graph2, 140.0);
		
		ToggleButton tb1 = new ToggleButton("Daily");
		tb1.setPrefSize(100, 35);
		tb1.setStyle("-fx-background-color: #AB4642");
		TB.getChildren().add(tb1);
		
		ToggleButton tb2 = new ToggleButton("Weekly");
		tb2.setPrefSize(100, 35);
		tb2.setStyle("-fx-background-color: #ffffff");
		tb2.setSelected(false);
		week.getChildren().add(tb2);
		
		tb1.setOnAction(event -> {
			if(tb1.isSelected()==true) {
				tb1.setStyle("-fx-background-color: #AB4642");
				tb2.setStyle("-fx-background-color: #ffffff");
				tb2.setSelected(false);
				Graph2.setId("appledaily");
			}
			else {
				
			}
			
        });
		
		tb2.setOnAction(event -> {
			if(tb2.isSelected()==true) {
				tb2.setStyle("-fx-background-color: #AB4642");
				tb1.setStyle("-fx-background-color: #ffffff");
				tb1.setSelected(false);
				Graph2.setId("appleweekly");
			}
			else {
				
			}
			
        });
		
		Label Ks = new Label("Kontostand= " + Math.round(b.returnKontoStand()*100)/100.00 + "€");
        Ks.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 18px;");
        
        KS.getChildren().add(Ks);
		
		Button backB = new Button("Back");
		backB.setPrefSize(80, 35);
		backB.setStyle("-fx-background-color: #AB4642");
		
		backB.setOnAction(event -> {
			try {
				start(this.primaryStage);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
		
		Button previousB = new Button("Previous");
		previousB.setPrefSize(80, 35);
		previousB.setStyle("-fx-background-color: #AB4642");
		
		Label AktienStand = new Label("Aktien Stand: " + b.returnAppleStand() + "€");
		AktienStand.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 18px;");
		GridPane.setConstraints(AktienStand, 0,0);
		
		Label AktienRate = new Label("Aktien Stand: " + b.returnAppleR());
		AktienRate.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 18px;");
		GridPane.setConstraints(AktienRate, 0,1);
		
		GridPane gridPane = new GridPane();
		gridPane.getChildren().addAll(AktienStand, AktienRate);
		gridPane.setHgap(20);
		gridPane.setVgap(20);
		
		
		
		back.setPadding(new Insets(15,12,15,12));
		back.setSpacing(10);
		back.getChildren().add(backB);
		
		previous.setPadding(new Insets(15,12,15,12));
		previous.setSpacing(10);
		previous.getChildren().add(previousB);
		
		Button KaufMenue = new Button("Kaufen/ Verkaufen");
		KaufMenue.setPrefSize(110, 30);
		KaufMenue.setStyle("-fx-background-color: #AB4642");
		KaufMenue.setOnAction(event -> {
            try {
				initBuyMenu(primaryStage.getScene());
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        
        HBox Kaufmenue = new HBox();
        Kaufmenue.setSpacing(10);
        Kaufmenue.getChildren().add(KaufMenue);
        
        ApplePane.setRightAnchor(Kaufmenue, 20.0);
        ApplePane.setBottomAnchor(Kaufmenue, 20.0);
        ApplePane.setRightAnchor(KS, 35.0);
        ApplePane.setTopAnchor(KS, 15.0);
        
        ApplePane.setRightAnchor(gridPane, 40.0);
        ApplePane.setTopAnchor(gridPane, 80.0);
		
		ApplePane.getChildren().addAll(back, previous, Graph2, KS, TB, Kaufmenue, week, gridPane);
		
		sceneApple = new Scene(ApplePane, 1280, 720);
		sceneApple.getStylesheets().addAll(this.getClass().getResource("NewFile.css").toExternalForm());
		

		primaryStage.setScene(sceneApple);
	}
	
	public void goToVW() {
		AnchorPane ApplePane = new AnchorPane();
		HBox back = new HBox();
		HBox previous = new HBox();
		HBox Graph = new HBox();
		HBox TB = new HBox();
		HBox KS = new HBox();
		HBox week = new HBox();
		
		Graph.setPrefSize(700, 330);
		//Graph.setPadding(new Insets(15,12,15,12));
		//Graph.setSpacing(10);
		Graph.setId("vwdaily");
		
		TB.setPrefSize(100, 35);
		week.setPrefSize(100, 35);
		//TB.setPadding(new Insets(15,12,15,12));
		//TB.setSpacing(10);
		
		ApplePane.setLeftAnchor(TB, 80.0);
		ApplePane.setTopAnchor(TB, 87.0);
		
		ApplePane.setLeftAnchor(week, 200.0);
		ApplePane.setTopAnchor(week, 87.0);
		
		ApplePane.setLeftAnchor(back, 0.0);
		ApplePane.setLeftAnchor(previous, 100.0);
		ApplePane.setLeftAnchor(Graph, 80.0);
		ApplePane.setTopAnchor(Graph, 140.0);
		
		ToggleButton tb1 = new ToggleButton("Daily");
		tb1.setPrefSize(100, 35);
		tb1.setStyle("-fx-background-color: #AB4642");
		TB.getChildren().add(tb1);
		
		ToggleButton tb2 = new ToggleButton("Weekly");
		tb2.setPrefSize(100, 35);
		tb2.setStyle("-fx-background-color: #ffffff");
		tb2.setSelected(false);
		week.getChildren().add(tb2);
		
		Label AktienStand = new Label("Aktien Stand: " + b.returnVWStand() + "€");
		AktienStand.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 18px;");
		GridPane.setConstraints(AktienStand, 0,0);
		
		Label AktienRate = new Label("Aktien Stand: " + b.returnVWR());
		AktienRate.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 18px;");
		GridPane.setConstraints(AktienRate, 0,1);
		
		GridPane gridPane = new GridPane();
		gridPane.getChildren().addAll(AktienStand, AktienRate);
		gridPane.setHgap(20);
		gridPane.setVgap(20);
		
		
		tb1.setOnAction(event -> {
			if(tb1.isSelected()==true) {
				tb1.setStyle("-fx-background-color: #AB4642");
				tb2.setStyle("-fx-background-color: #ffffff");
				tb2.setSelected(false);
				Graph.setId("vwdaily");
			}
			else {
				
			}
			
        });
		
		tb2.setOnAction(event -> {
			if(tb2.isSelected()==true) {
				tb2.setStyle("-fx-background-color: #AB4642");
				tb1.setStyle("-fx-background-color: #ffffff");
				tb1.setSelected(false);
				Graph.setId("vwweekly");
			}
			else {
				
			}
			
        });
		
		
		
		Label Ks = new Label("Kontostand= " + Math.round(b.returnKontoStand()*100)/100.00 + "€");
        Ks.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 18px;");
        
        KS.getChildren().add(Ks);
		
		
		
		
		Button backB = new Button("Back");
		backB.setPrefSize(80, 35);
		backB.setStyle("-fx-background-color: #AB4642");
		
		backB.setOnAction(event -> {
			try {
				start(this.primaryStage);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
		
		Button previousB = new Button("Previous");
		previousB.setPrefSize(80, 35);
		previousB.setStyle("-fx-background-color: #AB4642");
		
		
		
		back.setPadding(new Insets(15,12,15,12));
		back.setSpacing(10);
		back.getChildren().add(backB);
		
		previous.setPadding(new Insets(15,12,15,12));
		previous.setSpacing(10);
		previous.getChildren().add(previousB);
		
		Button KaufMenue = new Button("Kaufen/ Verkaufen");
		KaufMenue.setPrefSize(110, 30);
		KaufMenue.setStyle("-fx-background-color: #AB4642");
		
		KaufMenue.setOnAction(event -> {
            try {
				initBuyMenu(primaryStage.getScene());
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        
        HBox Kaufmenue = new HBox();
        Kaufmenue.setSpacing(10);
        Kaufmenue.getChildren().add(KaufMenue);
        
        ApplePane.setRightAnchor(Kaufmenue, 20.0);
        ApplePane.setBottomAnchor(Kaufmenue, 20.0);
        
        ApplePane.setRightAnchor(KS, 35.0);
        ApplePane.setTopAnchor(KS, 15.0);
        
        ApplePane.setRightAnchor(gridPane, 40.0);
        ApplePane.setTopAnchor(gridPane, 80.0);
        
		
		ApplePane.getChildren().addAll(back, previous, Graph, KS, TB, Kaufmenue, week, gridPane);
		
		sceneVW = new Scene(ApplePane, 1280, 720);
		sceneVW.getStylesheets().addAll(this.getClass().getResource("NewFile.css").toExternalForm());
		

		primaryStage.setScene(sceneVW);
	}
	
	public void Ausloggen() throws ClassNotFoundException, IOException, SQLException, InterruptedException {
		v = new VIEW();
		v.start(primaryStage);
		primaryStage.centerOnScreen();
	}
	
	public double returnAktienKurs(String Aktie) {
		double AktienStand = -1;
		if(Aktie == "DAX") {
			AktienStand = b.returnDAXStand();
		}
		else if(Aktie == "Apple"){
			AktienStand = b.returnAppleStand();
		}
		else if(Aktie == "VW") {
			AktienStand = b.returnVWStand();
		}
		else {
			
		}
		return AktienStand;
	}
	public void initBuyMenu(Scene scene) throws ClassNotFoundException, SQLException {
		Stage buyStage = new Stage();
		
		Button BVerkaufen = new Button("Verkaufen");
		BVerkaufen.setStyle("-fx-background-color: #292424; -fx-text-fill: #aca0a0;");
		BVerkaufen.setPrefSize(80, 30);
		
		Button BKaufen = new Button("Kaufen");
		BKaufen.setStyle("-fx-background-color: #AB4642");
		BKaufen.setPrefSize(80, 30);
		
		Button BAbbrechen = new Button("Abbrechen");
		BAbbrechen.setStyle("-fx-background-color: #6c5e5e");
		BAbbrechen.setPrefSize(80, 30);
		
		TextField Entry = new TextField();
		Entry.setPrefSize(30, 15);
		
		Label Konto = new Label("Kontostand= " + Math.round(b.returnKontoStand()*100)/100.00 + "€");
        Konto.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px;");
		
		Label eigeneAktien = new Label(); 
		Label A = new Label("Aktie");
		Label verfuegbarA = new Label("Verfügbare Aktienpakete");
		
		String Aktie;
		Label AktieK = new Label();
		
		if(scene == this.sceneDAX) {
			eigeneAktien = new Label("" + b.returnDAXZ());
			Aktie = "DAX";
			AktieK = new Label("DAX");
		}
		else if(scene == this.sceneApple) {
			eigeneAktien = new Label("" + b.returnAppleZ());
			Aktie = "Apple";
			AktieK = new Label("Apple");
		}
		else {
			eigeneAktien = new Label("" + b.returnVWZ());
			Aktie = "VW";
			AktieK = new Label("VW");
		}
		AktieK.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px;");
		eigeneAktien.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px;");
		A.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px;-fx-underline: true;");
		verfuegbarA.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px;-fx-underline: true;");
		
		GridPane grid5 = new GridPane();
		grid5.setPadding(new Insets(100,150,50,150));
        grid5.setVgap(15);
        grid5.setHgap(50);

		Label availablePacks = new Label("" + AktienStandGeben(Aktie));
		availablePacks.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px;");
		
		Label AnzahlIhreAktien = new Label("Anzahl ihrer Pakete");
		AnzahlIhreAktien.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px; -fx-underline: true;");
		
		Label AnzahlKV = new Label("Anzahl Kaufen/ Verkaufen");
		AnzahlKV.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px; -fx-underline: true;");
		
		grid5.getChildren().addAll(eigeneAktien, AktieK, AnzahlIhreAktien, availablePacks, verfuegbarA, A, Entry, AnzahlKV);
		
		GridPane.setConstraints(eigeneAktien,2,1);
		GridPane.setConstraints(availablePacks, 1,1);
		GridPane.setConstraints(AktieK,0,1);
		GridPane.setConstraints(AnzahlIhreAktien,2,0);
		GridPane.setConstraints(A, 0,0);
		GridPane.setConstraints(Entry, 2,2);
		GridPane.setConstraints(AnzahlKV, 1,2);
		GridPane.setConstraints(verfuegbarA, 1,0);
		
		GridPane.setHalignment(eigeneAktien, HPos.RIGHT);
		GridPane.setHalignment(availablePacks, HPos.RIGHT);
		
		HBox abbrechen = new HBox();
		abbrechen.setPrefSize(100, 50);
		abbrechen.setSpacing(8.0);
		abbrechen.getChildren().add(BAbbrechen);
		
		HBox Hkaufen = new HBox();
		Hkaufen.setSpacing(8.0);
		Hkaufen.setPrefSize(100, 50);
		Hkaufen.getChildren().add(BKaufen);
		
		HBox Hverkaufen = new HBox();
		Hverkaufen.setSpacing(8.0);
		Hverkaufen.setPrefSize(100, 50);
		Hverkaufen.getChildren().add(BVerkaufen);
		
		HBox Hkontostand = new HBox();
		Hkontostand.setSpacing(8.0);
		Hkontostand.setPrefSize(200, 50);
		Hkontostand.getChildren().add(Konto);
		
		AnchorPane LUL = new AnchorPane();
		LUL.getChildren().addAll(abbrechen, Hkaufen, Hverkaufen, Hkontostand, grid5);
		LUL.setRightAnchor(Hkontostand, 30.0);
		LUL.setTopAnchor(Hkontostand, 10.0);
		
		LUL.setRightAnchor(abbrechen, 15.0);
		LUL.setBottomAnchor(abbrechen, 8.0);
		
		LUL.setRightAnchor(Hkaufen, 105.0);
		LUL.setBottomAnchor(Hkaufen, 8.0);
		
		LUL.setRightAnchor(Hverkaufen, 195.0);
		LUL.setBottomAnchor(Hverkaufen, 8.0);
		
		LUL.setRightAnchor(grid5, 30.0);
		LUL.setTopAnchor(grid5, 40.0);
		
		Scene buyMenu = new Scene(LUL, 750, 400);
		buyMenu.getStylesheets().addAll(this.getClass().getResource("NewFile.css").toExternalForm());
		
		
		
		BKaufen.setOnAction(event -> {
			int i = Integer.parseInt(Entry.getText());
			System.out.println(i);
            try {
				Kaufen(Aktie, b.returnKontoStand(), i, returnAktienKurs(Aktie));
				try {
					b.start(this.Benutzername);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				buyStage.close();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        });
		
		BAbbrechen.setOnAction(event ->{
			buyStage.close();
		});
		
		BVerkaufen.setOnAction(event ->{
			int i = Integer.parseInt(Entry.getText());
			System.out.println(i);
            try {
				Verkaufen(Aktie, b.returnKontoStand(), i, returnAktienKurs(Aktie));
				try {
					b.start(this.Benutzername);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				buyStage.close();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        });

		
		buyStage.setScene(buyMenu);
		buyStage.show();
	}
	
	public void refreshBalance(Label bal) {
		Label l = new Label("Kontostand= " + Math.round(b.returnKontoStand()*100)/100.00 + "€");
		bal = l;
	}
	
	public void profile() {
		AnchorPane pro = new AnchorPane();
		Scene profileSc = new Scene(pro, 1280, 720);
		profileSc.getStylesheets().add(getClass().getResource("NewFile.css").toExternalForm());
		
		
		Label Username = new Label("Benutzername: " + this.Benutzername);
		Label Gesamtwert = new Label("Gesamtwert ihrer Aktien: " + gesamtBerechnen());
		Gesamtwert.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 18px;");
		Username.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 18px;");
		HBox Info = new HBox();
		Info.getChildren().add(Username);
		
		HBox GW = new HBox();
		GW.getChildren().add(Gesamtwert);
		
		Button back = new Button("Back");
		back.setPrefSize(100, 35);
		back.setStyle("-fx-background-color: #AB4642");
		
		Button exit = new Button("Exit");
		exit.setPrefSize(100, 35);
		exit.setStyle("-fx-background-color: #AB4642");
		
		Button editProfile = new Button("Edit Profile");
		editProfile.setPrefSize(100, 35);
		editProfile.setStyle("-fx-background-color: #AB4642");
		
		exit.setOnAction(event ->{
			primaryStage.close();
		});
		
		back.setOnAction(event ->{
			primaryStage.setScene(this.scene);
			primaryStage.show();
		});
		
		editProfile.setOnAction(event ->{
			editProfile();
		});
		
		
		
		HBox backH = new HBox();
		backH.getChildren().add(back);
		
		HBox exitH = new HBox();
		exitH.getChildren().add(exit);
		
		HBox editP = new HBox();
		editP.getChildren().add(editProfile);
		
		pro.getChildren().addAll(backH, exitH, Info, GW, editP);
		pro.setLeftAnchor(backH, 12.0);
		pro.setTopAnchor(backH, 15.0);
		
		pro.setLeftAnchor(exitH, 127.0);
		pro.setTopAnchor(exitH, 15.0);
		
		pro.setLeftAnchor(editP, 242.0);
		pro.setTopAnchor(editP, 15.0);
		
		pro.setTopAnchor(Info, 100.0);
		
		pro.setLeftAnchor(Info, 100.0);
		
		pro.setLeftAnchor(GW, 100.0);
		pro.setTopAnchor(GW, 150.0);

		primaryStage.setScene(profileSc);
	}
	
	public double gesamtBerechnen() {
		double Summe = -1.0;
		Summe = b.returnAnzahlApple() * returnAktienKurs("Apple");
		Summe = Summe + b.returnAnzahlDAX() * returnAktienKurs("DAX");
		Summe = Summe + b.returnAnzahlVW() * returnAktienKurs("VW");
		
		return Summe;
	}
	
	public void editProfile() {
		Stage edit = new Stage();
		
		Button back = new Button();
		HBox backH = new HBox();
		backH.setPrefSize(100, 35);
		
		
		AnchorPane editPane = new AnchorPane();
		editPane.getChildren().addAll(backH);
		Scene editS = new Scene(editPane, 750, 400);
		editS.getStylesheets().addAll(this.getClass().getResource("NewFile.css").toExternalForm());
		
		edit.setScene(editS);
		edit.show();
	}
	
}
