package View;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import Controller.Controller;
import Model.Aktien;
import Model.Model;

public class VIEW{
	private Aktien b;
	private Model m = new Model();
	private PasswordField passworteingabe=new PasswordField();
	private PasswordField passworteingabe1=new PasswordField();
	private GridPane grid2 = new GridPane();
	private Controller a;
	private Label label3, label4, label5, label6, DAX, Apple, VW;
	private GridPane grid;
	private ProgressIndicator pi;
	private Scene sceneAktienUebersicht;
	private Stage primaryStage;
	private Button auslogbutton=new Button("Logout");
	private AktienVIEW AktienView;
	
    public void wait(int z){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
   
    
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException, SQLException, InterruptedException{
        this.primaryStage = primaryStage;
    	primaryStage.setTitle("Login");
        
        b = new Aktien();
        a = new Controller();
        
        //a.start("DAX");
        
        //GridPane

        grid = new GridPane();
        grid.setPadding(new Insets(100,150,50,150));
        grid.setVgap(8);
        grid.setHgap(10);
        
        GridPane grid1 = new GridPane();
        grid1.setPadding(new Insets(100,150,50,150));
        grid1.setVgap(8);
        grid1.setHgap(10);
        
        
        grid2.setPadding(new Insets(100,150,50,150));
        grid2.setVgap(8);
        grid2.setHgap(10);


        //Layout 2
        VBox layout2= new VBox();
        Scene scene2=new Scene(layout2,870,500);
        
        
        //Layout register
        VBox layoutR = new VBox();
        //Name Label

        Label namelabel= new Label("Username");
        namelabel.setStyle("-fx-text-fill: aliceblue;");
        namelabel.setPrefWidth(70.0);
        GridPane.setConstraints(namelabel,0,0);

        //Name Eingabe

        TextField eingabe=new TextField();
        eingabe.setPromptText("Name");
        GridPane.setConstraints(eingabe,1,0);

        //Passwort Label

        Label passwortlabel = new Label("Passwort");
        passwortlabel.setStyle("-fx-text-fill: aliceblue;");
        GridPane.setConstraints(passwortlabel,0,1);
        
        Label passwortlabel2 = new Label("Passwort bestätigen");
        passwortlabel2.setStyle("-fx-text-fill: aliceblue;");
        GridPane.setConstraints(passwortlabel2,0,2);
     
        //Passwort Eingabe

        
        passworteingabe.setPromptText("passwort");
        GridPane.setConstraints(passworteingabe,1,1);
        
        passworteingabe1.setPromptText("Passwort bestätigen");
        GridPane.setConstraints(passworteingabe1,1,2);

        //Log in
        Button loginbutton=new Button("Log in");
        loginbutton.setStyle("-fx-background-color: #AB4642");
        loginbutton.setPrefWidth(95.0);
        GridPane.setConstraints(loginbutton,3,0);
        
        //register
        Button register = new Button("Registrieren");
        register.setStyle("-fx-background-color: #AB4642");
        register.setPrefWidth(95.0);
        GridPane.setConstraints(register,3,1);
        
      //register in register scene
        Button register2 = new Button("Registrieren");
        register2.setStyle("-fx-background-color: #AB4642");
        GridPane.setConstraints(register2,1,3);

        //Neben Labels
        Label label10 = new Label("Die Passwörter stimmen nicht überein!");
        label10.setStyle("-fx-text-fill: aliceblue;");
        GridPane.setConstraints(label10, 1,4);
        
        Label label11 = new Label("Bitte alle Felder ausfüllen!");
        label11.setStyle("-fx-text-fill: aliceblue;");
        GridPane.setConstraints(label11, 1,4);
        
        label3= new Label("Bitte den Username eingeben!");
        label3.setStyle("-fx-text-fill: aliceblue;");
        GridPane.setConstraints(label3,1,3);
        label4= new Label("Bitte das Passwort eingeben");
        label4.setStyle("-fx-text-fill: aliceblue;");
        GridPane.setConstraints(label4,1,3);
        label5= new Label("Erfolgreich eingeloggt");
        label5.setStyle("-fx-text-fill: aliceblue;");
        GridPane.setConstraints(label5,1,3);
        label6= new Label("Error");
        label6.setStyle("-fx-text-fill: aliceblue;");
        GridPane.setConstraints(label6,1,3);
        
        //Progressbar
        grid.getChildren().addAll(namelabel,eingabe,passwortlabel,passworteingabe,loginbutton, register);
        pi= new ProgressIndicator();
        GridPane.setConstraints(pi,1,3);


        Scene scene=new Scene(grid,600,300);
        scene.getStylesheets().add(getClass().getResource("NewFile.css").toExternalForm());
        Scene sceneR = new Scene(grid1, 600, 300);
        sceneAktienUebersicht = new Scene(grid2, 1280, 720);


        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        
        //TimeUnit.SECONDS.sleep(3);
		


        //Button Aktion

        loginbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (eingabe.getText().isEmpty()) {
                    grid.getChildren().removeAll(label4,label5,label6);
                    grid.getChildren().add(label3);

                }
                else if (passworteingabe.getText().isEmpty()) {
                    grid.getChildren().removeAll(label3,label5,label6);
                    grid.getChildren().add(label4);
                }
                else {
                    if(m.Anmelden1(eingabe.getText(), passworteingabe.getText()) == true) {
                    	String Benutzername = eingabe.getText();	
                    	
                    	sceneAktienUebersicht.getStylesheets().add(getClass().getResource("NewFile.css").toExternalForm());
                    	grid.getChildren().removeAll(label3, label4, label5, label6);
                    	grid.getChildren().add(pi);

                                    Task<LineChart> task = new Task<LineChart>() {
                                        protected LineChart call() throws Exception {
                                            for (int i = 0; i < 3; i++) {
                                                try {
                                                	AktienView = new AktienVIEW(Benutzername);
                                                    Thread.sleep(100);
                                                } catch (InterruptedException ex) {
                                                }
                                            }
                                            return new LineChart(new NumberAxis(), new NumberAxis());
                                        }
                                    };
                                    pi.progressProperty().bind(task.progressProperty());
                                    task.setOnSucceeded(evt -> {
                                    	
                                        try {
											AktienView.start(primaryStage);
										} catch (ClassNotFoundException | SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
                                        grid.getChildren().removeAll(pi);
                                    });
                                    new Thread(task).start();
                                    	try {
                							Thread.sleep(1000);
                						} catch (InterruptedException e) {
                							// TODO Auto-generated catch block
                							e.printStackTrace();
                						}
                                    }
                                    else {
                                    	grid.getChildren().add(pi);
                                    	 IntegerProperty seconds = new SimpleIntegerProperty();
                                         Timeline timeline = new Timeline(
                                                 new KeyFrame(Duration.ZERO, new KeyValue(seconds, 0)),
                                                 new KeyFrame(Duration.minutes(0.03), e-> {
                                                     grid.getChildren().remove(pi);
                                                     grid.getChildren().removeAll(label3,label4,label5);
                                                     grid.getChildren().add(label6);
                                                     passworteingabe.clear();

                                                 }, new KeyValue(seconds, 60))
                                         );
                                         timeline.play();
                                         try {
                                             Thread.sleep(1);
                                         } catch (InterruptedException e) {
                                             e.printStackTrace();
                                         }
                                    }
                                }
                                }
                        });
        
        register.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				primaryStage.setScene(sceneR);
				sceneR.getStylesheets().add(getClass().getResource("NewFile.css").toExternalForm());
				grid1.getChildren().add(eingabe);
				grid1.getChildren().add(passworteingabe);
				grid1.getChildren().add(passworteingabe1);
				grid1.getChildren().add(namelabel);
				grid1.getChildren().add(passwortlabel);
				grid1.getChildren().add(passwortlabel2);
				grid1.getChildren().add(register2);
				primaryStage.setTitle("Registrierung");
			}
        	
        });
        
        register2.setOnAction(arg0 -> {
            if(eingabe.getText().isEmpty() || passworteingabe1.getText().isEmpty() || passworteingabe.getText().isEmpty()) {
                grid1.getChildren().removeAll(label10, label11);
                grid1.getChildren().add(label11);
            }
            else if(!passworteingabe.getText().equals(passworteingabe1.getText()) ) {
                grid1.getChildren().removeAll(label10, label11);
                grid1.getChildren().add(label10);
            }
            else {
                regestrieren(eingabe.getText(), passworteingabe.getText(), passworteingabe1.getText());
                primaryStage.setScene(scene);
                grid.getChildren().addAll(namelabel,eingabe,passwortlabel,passworteingabe);
            }
        });
        
        auslogbutton.setOnAction(event -> {
            primaryStage.setScene(scene);
        });
    }
    public void regestrieren(String name, String Passwort1, String Passwort) {
    	if(!Passwort.equals(Passwort1)) {
            //Passwörter sind nicht gleich!
    		System.out.println("Die Passwörter stimmen nicht überein!");
    	}
    	
    	else if(Passwort.equals(Passwort1)) {
    		m.regestrieren(name, Passwort);
    	}
    	else {
    		//Bitte alle Felder ausfüllen
    	}
    }
    
    public void löschen(String Name) throws ClassNotFoundException, SQLException {
    	if(m.löschen(Name) == true ) {
    		System.out.println("Ihr Konto wurde erfolgreich gelöscht!");
    	}
    	else {
    		System.out.println("Es konnte kein Konto mit diesem Namen gefunden werden!");
    	}
    }
    
   
    
    
    
    
    
    
}
