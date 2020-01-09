package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	private double xOffset = 0; 
	private double yOffset = 0;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setAlwaysOnTop(true);
		DigitalClockLabel root= new DigitalClockLabel();
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
		stage.setScene(new Scene(root, 50, 18));
		stage.show();
	}
}


