package Allnick;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;



public class ConfirmBox 
{
	static boolean resp;
	
	public static boolean display(String title, String message) 
	{
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		Label label = new Label();
		label.setText(message);
		
		//buttons
		Button yes = new Button("Yes");
		Button no = new Button("No");
		
		yes.setOnAction(e ->{
			resp = true;
			window.close();
		});
		no.setOnAction(e ->{
			resp = false;
			window.close();
		});
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label,yes,no);
		layout.setAlignment(Pos.CENTER);
		Scene sc = new Scene(layout);
		window.setScene(sc);
		
		return resp;
		
	}
	

}
