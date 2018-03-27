package server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    int port = 8080;

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("scenes/entryScreen.fxml"));
        stage.setTitle("SushiGo");
        stage.setScene(new Scene(root,300,500));
        stage.show();

        Network network = new Network(port);
        FXMLController.network = network;

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
