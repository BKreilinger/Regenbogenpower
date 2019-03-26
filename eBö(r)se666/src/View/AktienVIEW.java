package View;

import java.io.IOException;
import java.sql.SQLException;

import Model.Aktien;
import Model.SceneSafer;
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
	private HBox hbox3;
	private Label Kontostand;
	
	public AktienVIEW(String Benutzername) {
		b.getWebsiteData();
		try {
			b.start(Benutzername);
		} catch (ClassNotFoundException | IOException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void start(Stage primaryStage) {
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
		back.setPrefSize(80, 35);
		
		Button previous = new Button("Previous");
		previous.setPrefSize(80, 35);
		previous.setStyle("-fx-background-color: #AB4642");
		
		rootPane.getChildren().addAll(hbox, hbox1, hbox3, grid2, grid);
		
		hbox.getChildren().addAll(back);
		hbox1.getChildren().addAll(previous);
		rootPane.setLeftAnchor(hbox, 0.0);
		rootPane.setLeftAnchor(hbox1, 100.0);
		
		rootPane.setTopAnchor(grid2, 100.0);
		rootPane.setRightAnchor(grid2, 50.0);
		
		rootPane.setLeftAnchor(grid, 0.0);
		rootPane.setTopAnchor(grid, 100.0);
		
		rootPane.setRightAnchor(hbox3, 70.0);
		rootPane.setTopAnchor(hbox3, 10.0);
		getAktienInfo();
		
		
		previous.setOnAction(event -> {
            goToDAX();
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
	    	if(b.Kostenberechnen(Aktien, KontostandVorher, Anzahl, Stand) > KontostandVorher || AktienStandGeben(Aktien) < Anzahl) {
	    		//Error
	    		System.out.println(AktienStandGeben(Aktien));
	    		System.out.println(b.Kostenberechnen(Aktien, KontostandVorher, Anzahl, Stand));
	    		System.out.println(KontostandVorher);
	    		System.out.println("Error");
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
		
    	Label  Standa= new Label("" + b.returnDAXStand() + " €" );
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
        
        Kontostand = new Label("Kontostand= " + Math.round(b.returnKontoStand()*100)/100.00 + "€");
        Kontostand.setStyle("-fx-text-fill: aliceblue;-fx-font-size: 16px;");
        GridPane.setConstraints(Kontostand, 7, 1);

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
		
		Graph.setPrefSize(870, 500);
		Graph.setPadding(new Insets(15,12,15,12));
		Graph.setSpacing(10);
		Graph.setId("DAXdaily");
		
		TB.setPrefSize(870, 500);
		TB.setPadding(new Insets(15,12,15,12));
		TB.setSpacing(10);
		
		ApplePane.setLeftAnchor(TB, 400.0);
		ApplePane.setTopAnchor(TB, 0.0);
		
		ApplePane.setLeftAnchor(back, 10.0);
		ApplePane.setLeftAnchor(previous, 110.0);
		ApplePane.setLeftAnchor(Graph, 50.0);
		ApplePane.setTopAnchor(Graph, 100.0);
		
		ToggleButton tb1 = new ToggleButton("Daily/Weekly");
		tb1.setPrefSize(100, 35);
		tb1.setStyle("-fx-background-color: #AB4642");
		TB.getChildren().add(tb1);
		
		tb1.setOnAction(event -> {
			if(tb1.isSelected()==false) {
				tb1.setStyle("-fx-background-color: #AB4642");
				Graph.setId("DAXdaily");
			}
			else if(tb1.isSelected()) {
			tb1.setStyle("-fx-background-color: #ffffff");
			Graph.setId("DAXweekly");
			}
			else {
				
			}
			
        });
		
		if(tb1.isSelected()==false) {
			
		}
		else {
			
			primaryStage.setScene(sceneApple);
			tb1.setSelected(true);
			tb1.setStyle("-fx-background-color: #000000");
		}
		
		Button backB = new Button("Back");
		backB.setPrefSize(80, 35);
		backB.setStyle("-fx-background-color: #AB4642");
		
		backB.setOnAction(event -> {
            primaryStage.setScene(scene);
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
		
		ApplePane.getChildren().addAll(back, previous, Graph, this.hbox3, TB);
		
		sceneApple = new Scene(ApplePane, 1280, 720);
		sceneApple.getStylesheets().addAll(this.getClass().getResource("NewFile.css").toExternalForm());
		

		primaryStage.setScene(sceneApple);
	}
	
	public void goToApple() {
		AnchorPane ApplePane = new AnchorPane();
		HBox back = new HBox();
		HBox previous = new HBox();
		HBox Graph = new HBox();
		HBox TB = new HBox();
		
		Graph.setPrefSize(870, 500);
		Graph.setPadding(new Insets(15,12,15,12));
		Graph.setSpacing(10);
		Graph.setId("appledaily");
		
		TB.setPrefSize(870, 500);
		TB.setPadding(new Insets(15,12,15,12));
		TB.setSpacing(10);
		
		ApplePane.setLeftAnchor(TB, 400.0);
		ApplePane.setTopAnchor(TB, 0.0);
		
		ApplePane.setLeftAnchor(back, 10.0);
		ApplePane.setLeftAnchor(previous, 110.0);
		ApplePane.setLeftAnchor(Graph, 50.0);
		ApplePane.setTopAnchor(Graph, 100.0);
		
		ToggleButton tb1 = new ToggleButton("Daily/Weekly");
		tb1.setPrefSize(100, 35);
		tb1.setStyle("-fx-background-color: #AB4642");
		TB.getChildren().add(tb1);
		
		tb1.setOnAction(event -> {
			if(tb1.isSelected()==false) {
				tb1.setStyle("-fx-background-color: #AB4642");
				Graph.setId("appledaily");
			}
			else if(tb1.isSelected()) {
			tb1.setStyle("-fx-background-color: #ffffff");
			Graph.setId("appleweekly");
			}
			else {
				
			}
			
        });
		
		if(tb1.isSelected()==false) {
			
		}
		else {
			
			primaryStage.setScene(sceneApple);
			tb1.setSelected(true);
			tb1.setStyle("-fx-background-color: #000000");
		}
		
		Button backB = new Button("Back");
		backB.setPrefSize(80, 35);
		backB.setStyle("-fx-background-color: #AB4642");
		
		backB.setOnAction(event -> {
            primaryStage.setScene(scene);
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
		
		ApplePane.getChildren().addAll(back, previous, Graph, this.hbox3, TB);
		
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
		
		Graph.setPrefSize(870, 500);
		Graph.setPadding(new Insets(15,12,15,12));
		Graph.setSpacing(10);
		Graph.setId("vwdaily");
		
		TB.setPrefSize(870, 500);
		TB.setPadding(new Insets(15,12,15,12));
		TB.setSpacing(10);
		
		ApplePane.setLeftAnchor(TB, 400.0);
		ApplePane.setTopAnchor(TB, 0.0);
		
		ApplePane.setLeftAnchor(back, 10.0);
		ApplePane.setLeftAnchor(previous, 110.0);
		ApplePane.setLeftAnchor(Graph, 50.0);
		ApplePane.setTopAnchor(Graph, 100.0);
		
		ToggleButton tb1 = new ToggleButton("Daily/Weekly");
		tb1.setPrefSize(100, 35);
		tb1.setStyle("-fx-background-color: #AB4642");
		TB.getChildren().add(tb1);
		
		tb1.setOnAction(event -> {
			if(tb1.isSelected()==false) {
				tb1.setStyle("-fx-background-color: #AB4642");
				Graph.setId("vwdaily");
			}
			else if(tb1.isSelected()) {
			tb1.setStyle("-fx-background-color: #ffffff");
			Graph.setId("vwweekly");
			}
			else {
				
			}
			
        });
		
		if(tb1.isSelected()==false) {
			
		}
		else {
			
			primaryStage.setScene(sceneApple);
			tb1.setSelected(true);
			tb1.setStyle("-fx-background-color: #000000");
		}
		
		Button backB = new Button("Back");
		backB.setPrefSize(80, 35);
		backB.setStyle("-fx-background-color: #AB4642");
		
		backB.setOnAction(event -> {
            primaryStage.setScene(scene);
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
		
		ApplePane.getChildren().addAll(back, previous, Graph, this.hbox3, TB);
		
		sceneApple = new Scene(ApplePane, 1280, 720);
		sceneApple.getStylesheets().addAll(this.getClass().getResource("NewFile.css").toExternalForm());
		

		primaryStage.setScene(sceneApple);
	}
	
	public void Ausloggen() throws ClassNotFoundException, IOException, SQLException, InterruptedException {
		v = new VIEW();
		v.start(primaryStage);
		primaryStage.centerOnScreen();
	}
	
}
