package server;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class GUI {
    public static GridPane serverGrid;
    public static GridPane lobbyViewerGrid;


    public static void UpdateLobbyGrid(GridPane grid, LobbyManager lm){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (grid.getChildren().size() > 1) {
                    grid.getChildren().remove(1, grid.getChildren().size());
                }
                int i = 0;
                for (Lobby l : lm.lobbyList) {
                    i++;
                    grid.addRow(i, new Label(i+". Name: "+l.name),
                            new Label("\t Player Count: "+l.playerCount));
                    if (grid == lobbyViewerGrid) {
                        Button join = new Button("Join");
                        grid.addRow(i,join);
                        join.setOnAction(event -> {
                            lm.network.sendRequest("joinLobby");
                            lm.network.sendRequest(l.name);
                        });
                    }
                }
            }
        });

    }
}
