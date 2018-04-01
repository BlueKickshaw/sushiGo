package Game;

import Cards.Card;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import server.Network;

import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.Vector;


public class TwoPlayerController {
    public static Network network;
    Player player;

    GameDriver driver;
    @FXML    GridPane primaryPlayerGrid;
    @FXML    GridPane topOpponentCardBacksGrid;
    @FXML    GridPane handCardImagesGrid;
    @FXML    GridPane topOpponentHandCardImagesGrid;
    @FXML    ImageView playerCard00 = new ImageView();
    @FXML    ImageView playerCard01 = new ImageView();
    @FXML    ImageView playerCard02 = new ImageView();
    @FXML    ImageView playerCard03 = new ImageView();
    @FXML    ImageView playerCard04 = new ImageView();
    @FXML    ImageView playerCard05 = new ImageView();
    @FXML    ImageView playerCard06 = new ImageView();
    @FXML    ImageView playerCard07 = new ImageView();
    @FXML    ImageView playerCard08 = new ImageView();
    @FXML    ImageView playerCard09 = new ImageView();

    static Vector<ImageView> playerCardImages = new Vector<>();
    @FXML    ImageView playerHandCard00 = new ImageView();
    @FXML    ImageView playerHandCard01 = new ImageView();
    @FXML    ImageView playerHandCard02 = new ImageView();
    @FXML    ImageView playerHandCard03 = new ImageView();
    @FXML    ImageView playerHandCard04 = new ImageView();
    @FXML    ImageView playerHandCard05 = new ImageView();
    @FXML    ImageView playerHandCard06 = new ImageView();
    @FXML    ImageView playerHandCard07 = new ImageView();
    @FXML    ImageView playerHandCard08 = new ImageView();
    @FXML    ImageView playerHandCard09 = new ImageView();
    Vector<ImageView> handCardImages = new Vector<>();

    @FXML    ImageView topOpponentCard00 = new ImageView();
    @FXML    ImageView topOpponentCard01 = new ImageView();
    @FXML    ImageView topOpponentCard02 = new ImageView();
    @FXML    ImageView topOpponentCard03 = new ImageView();
    @FXML    ImageView topOpponentCard04 = new ImageView();
    @FXML    ImageView topOpponentCard05 = new ImageView();
    @FXML    ImageView topOpponentCard06 = new ImageView();
    @FXML    ImageView topOpponentCard07 = new ImageView();
    @FXML    ImageView topOpponentCard08 = new ImageView();
    @FXML    ImageView topOpponentCard09 = new ImageView();
    Vector<ImageView> topOpponentCardBacks = new Vector<>();
    @FXML    ImageView topPlayerHandCard00 = new ImageView();
    @FXML    ImageView topPlayerHandCard01 = new ImageView();
    @FXML    ImageView topPlayerHandCard02 = new ImageView();
    @FXML    ImageView topPlayerHandCard03 = new ImageView();
    @FXML    ImageView topPlayerHandCard04 = new ImageView();
    @FXML    ImageView topPlayerHandCard05 = new ImageView();
    @FXML    ImageView topPlayerHandCard06 = new ImageView();
    @FXML    ImageView topPlayerHandCard07 = new ImageView();
    @FXML    ImageView topPlayerHandCard08 = new ImageView();
    @FXML    ImageView topPlayerHandCard09 = new ImageView();
    Vector<ImageView> topOpponentHandCardImages = new Vector<>();
    Vector<Vector<ImageView>> rotatingImages = new Vector<>();
    Vector<Vector<ImageView>> handImages = new Vector<>();

    @FXML    Label firstPlaceText = new Label();
    @FXML    Label secondPlaceText = new Label();
    Vector<Label> scoreLabels = new Vector<>();

    Image cardBack = new Image("/Game/CardImages/Cardback.jpg");
    Image rotatedCardBack = new Image("/Game/CardImages/Cardback_Rotated.jpg");

    Vector<Player> playerList = new Vector<>();

    //GameDriver driver = new GameDriver(playerList);
    Deck deck = new Deck();
    int roundCount = 0;


    private void turn() {
    }


    public void incrementRound(ActionEvent event) {
    }

    public void getHands(ActionEvent event) {
        Thread gameHandler = new Thread(driver);
        gameHandler.start();
    }


    public void initialize() {
        scoreLabels.add(firstPlaceText);
        scoreLabels.add(secondPlaceText);

        int[] populationOrder = new int[]{0, 9, 1, 8, 2, 7, 3, 6, 4, 5};
        for (int i=0; i<10;i++){
            ImageView temp = new ImageView();
            //temp.setPreserveRatio(true);
            temp.setFitHeight(primaryPlayerGrid.getPrefHeight());
            temp.setFitWidth(primaryPlayerGrid.getPrefWidth()/10);
            playerCardImages.add(temp);
            primaryPlayerGrid.add(temp, populationOrder[i] ,1);


            temp = new ImageView();
            //temp.setPreserveRatio(true);
            temp.setFitHeight(topOpponentCardBacksGrid.getPrefHeight());
            temp.setFitWidth(topOpponentCardBacksGrid.getPrefWidth()/10);
            topOpponentCardBacks.add(temp);
            topOpponentCardBacksGrid.add(temp, populationOrder[i] ,1);


            temp = new ImageView();
            //temp.setPreserveRatio(true);
            temp.setFitHeight(handCardImagesGrid.getPrefHeight());
            temp.setFitWidth(handCardImagesGrid.getPrefWidth()/10);
            handCardImages.add(temp);
            handCardImagesGrid.add(temp, i ,1);


            temp = new ImageView();
            //temp.setPreserveRatio(true);
            temp.setFitHeight(topOpponentHandCardImagesGrid.getPrefHeight());
            temp.setFitWidth(topOpponentHandCardImagesGrid.getPrefWidth()/10);
            topOpponentHandCardImages.add(temp);
            topOpponentHandCardImagesGrid.add(temp, i ,1);
        }


        rotatingImages.add(playerCardImages);
        rotatingImages.add(topOpponentCardBacks);
        handImages.add(handCardImages);
        handImages.add(topOpponentHandCardImages);



        int[] populationOrderButtons = new int[]{7, 5, 2, 0, 1, 3, 4, 6, 9, 8};

        for (int i = 0; i < playerCardImages.size(); i++) {
            int finalI = i;
            int[] finalPopulationOrder = populationOrderButtons;
            playerCardImages.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
                    alert.setTitle("Confirm Card Pick");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {

                        player.isFirstCardPicked = true;
                        player.setSelectedCard(player.getRotatingHand().getCard(finalPopulationOrder[finalI]));
                    }
                }
            });
        }








        for (int i = 0; i < network.client.getLobby().playerCount; i++) {
            playerList.add(new Player(network.client.getLobby().playerNames.get(i)));
        }

        for (Player p : playerList) {
            if(network!=null && p.getName().equals(network.username)){
                player = p;
            }
        }

        //driver = new GameDriver(playerList, rotatingImages, handImages, network);
        driver = new GameDriver(playerList, rotatingImages, handImages, network, scoreLabels);



    }

    private void updateScores(Vector<Player> players){
        Vector<Player> clonePlayerList = (Vector) players.clone();
        clonePlayerList.sort(Comparator.comparingInt(Player::getTotalPoints));
        Collections.reverse(clonePlayerList);
        firstPlaceText.setText(clonePlayerList.get(0).getName() + "     " + clonePlayerList.get(0).getTotalPoints() + " Total Points");
        secondPlaceText.setText(clonePlayerList.get(1).getName() + "     " + clonePlayerList.get(1).getTotalPoints() + " Total Points");
    }


}