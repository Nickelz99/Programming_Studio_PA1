package Allnick;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AlertBox {
	public static void display(String title, String Message) 
	{
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		
		Label label = new Label();
		label.setText(Message);
		label.setWrapText(true);
		label.setTextAlignment(TextAlignment.CENTER);
		Button close = new Button("Ok");
		close.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label,close);
		layout.setAlignment(Pos.CENTER);
		
		Scene sc = new Scene(layout);
		window.setScene(sc);
		window.showAndWait();
		
	}

}
