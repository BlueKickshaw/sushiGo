package server;

import Game.FourPlayerController;
import Game.ThreePlayerController;
import Game.TwoPlayerController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    int port = 8080;

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("serverScenes/entryScreen.fxml"));
        stage.setTitle("SushiGo");
        stage.setScene(new Scene(root,300,500));
        stage.show();

        Network network = new Network(port);
        // Jon, I hate how many statics/controllers you're making me use you bastard
        FXMLController.network = network;
        TwoPlayerController.network = network;
        ThreePlayerController.network = network;
        FourPlayerController.network = network;

        // The program still runs background threads; on window close we want to close everything
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
