package View;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;

import Model.Aktien;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class TestView extends Application{
	private Aktien a = new Aktien();
	private Stage primaryStage = new Stage();

	public void test() {
		
	}
	
	@Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException, SQLException{
        primaryStage.setTitle("Login");
        GridPane grid = new GridPane();
	     grid.setPadding(new Insets(100,150,50,150));
	     grid.setVgap(8);
	     grid.setHgap(10);	     
	     
	     Scene scene=new Scene(grid,600,300);
	     primaryStage.setScene(scene);
	     
	     Button button =new Button("Log in");
	     button.setStyle("-fx-background-color: #AB4642");
	     GridPane.setConstraints(button,1,2);
	        
	     grid.getChildren().add(button);
	     button.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	try {
						a.DAXK(10);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	     });
	}
	public void main(String args[]) {
		launch(args);
	}
	

}
