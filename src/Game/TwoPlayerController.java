package Game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import server.Network;

import java.util.Optional;
import java.util.Vector;


public class TwoPlayerController {
    public static Network network;
    Player player;

    GameDriver driver;
    @FXML    GridPane primaryPlayerGrid;
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

    Vector<Player> playerList = new Vector<>();


    // called at the end of initialize to start the game for players
    public void getHands(ActionEvent event) {
        Thread gameHandler = new Thread(driver);
        System.out.println("Getting starting hands");
        gameHandler.start();
    }


    public void initialize() {
        scoreLabels.add(firstPlaceText);
        scoreLabels.add(secondPlaceText);

        playerCardImages.add(playerCard08);
        playerCardImages.add(playerCard09);
        playerCardImages.add(playerCard00);
        playerCardImages.add(playerCard07);
        playerCardImages.add(playerCard01);
        playerCardImages.add(playerCard06);
        playerCardImages.add(playerCard05);
        playerCardImages.add(playerCard02);
        playerCardImages.add(playerCard04);
        playerCardImages.add(playerCard03);
        topOpponentCardBacks.add(topOpponentCard08);
        topOpponentCardBacks.add(topOpponentCard09);
        topOpponentCardBacks.add(topOpponentCard00);
        topOpponentCardBacks.add(topOpponentCard07);
        topOpponentCardBacks.add(topOpponentCard01);
        topOpponentCardBacks.add(topOpponentCard06);
        topOpponentCardBacks.add(topOpponentCard05);
        topOpponentCardBacks.add(topOpponentCard02);
        topOpponentCardBacks.add(topOpponentCard04);
        topOpponentCardBacks.add(topOpponentCard03);


        handCardImages.add(playerHandCard00);
        handCardImages.add(playerHandCard01);
        handCardImages.add(playerHandCard02);
        handCardImages.add(playerHandCard03);
        handCardImages.add(playerHandCard04);
        handCardImages.add(playerHandCard05);
        handCardImages.add(playerHandCard06);
        handCardImages.add(playerHandCard07);
        handCardImages.add(playerHandCard08);
        handCardImages.add(playerHandCard09);

        topOpponentHandCardImages.add(topPlayerHandCard00);
        topOpponentHandCardImages.add(topPlayerHandCard01);
        topOpponentHandCardImages.add(topPlayerHandCard02);
        topOpponentHandCardImages.add(topPlayerHandCard03);
        topOpponentHandCardImages.add(topPlayerHandCard04);
        topOpponentHandCardImages.add(topPlayerHandCard05);
        topOpponentHandCardImages.add(topPlayerHandCard06);
        topOpponentHandCardImages.add(topPlayerHandCard07);
        topOpponentHandCardImages.add(topPlayerHandCard08);
        topOpponentHandCardImages.add(topPlayerHandCard09);

        rotatingImages.add(playerCardImages);
        rotatingImages.add(topOpponentCardBacks);
        handImages.add(handCardImages);
        handImages.add(topOpponentHandCardImages);

//        The reason the buttons set a different selected card than their corresponding number is because how the cards
//        are displayed in the UI doesn't match their indices in their actual hand. It was done this way
//        so that it looked nice that when a player has a card removed/chosen from their hand, there are not blanks on
//        the UI but rather the cards are in the center of the screen for the client player. It can be understood that
//        the button number corresponds to the ith card in their hand. The buttons are disabled at the end of the round,
//        and 1 less is enabled each subsequent round.
        playerCard00.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
                alert.setTitle("Confirm Card Pick");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    player.setSelectedCard(player.getRotatingHand().getCard(7));
                    player.isFirstCardPicked = true;
                }
            }
        });
        playerCard01.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
                alert.setTitle("Confirm Card Pick");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    player.setSelectedCard(player.getRotatingHand().getCard(5));
                    player.isFirstCardPicked = true;
                }
            }
        });
        playerCard02.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
                alert.setTitle("Confirm Card Pick");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    player.setSelectedCard(player.getRotatingHand().getCard(2));
                    player.isFirstCardPicked = true;
                }
            }
        });
        playerCard03.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
                alert.setTitle("Confirm Card Pick");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    player.setSelectedCard(player.getRotatingHand().getCard(0));
                    player.isFirstCardPicked = true;
                }
            }
        });
        playerCard04.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
                alert.setTitle("Confirm Card Pick");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    player.setSelectedCard(player.getRotatingHand().getCard(1));
                    player.isFirstCardPicked = true;
                }
            }
        });
        playerCard05.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
                alert.setTitle("Confirm Card Pick");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    player.setSelectedCard(player.getRotatingHand().getCard(3));
                    player.isFirstCardPicked = true;
                }
            }
        });
        playerCard06.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
                alert.setTitle("Confirm Card Pick");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    player.setSelectedCard(player.getRotatingHand().getCard(4));
                    player.isFirstCardPicked = true;
                }
            }
        });
        playerCard07.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
                alert.setTitle("Confirm Card Pick");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    player.setSelectedCard(player.getRotatingHand().getCard(6));
                    player.isFirstCardPicked = true;
                }
            }
        });
        playerCard08.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
                alert.setTitle("Confirm Card Pick");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    player.setSelectedCard(player.getRotatingHand().getCard(9));
                    player.isFirstCardPicked = true;
                }
            }
        });
        playerCard09.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
                Optional<ButtonType> result = alert.showAndWait();
                alert.setTitle("Confirm Card Pick");
                if (result.get() == ButtonType.OK) {

                    player.setSelectedCard(player.getRotatingHand().getCard(8));
                    player.isFirstCardPicked = true;
                }
            }
        });






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


        getHands(new ActionEvent());
    }

}
