package Game;

import Cards.Card;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Collections;
import java.util.Optional;
import java.util.Vector;


public class FourPlayerController {

    @FXML
    GridPane primaryPlayerGrid;
    @FXML
    ImageView playerCard00 = new ImageView();
    @FXML
    ImageView playerCard01 = new ImageView();
    @FXML
    ImageView playerCard02 = new ImageView();
    @FXML
    ImageView playerCard03 = new ImageView();
    @FXML
    ImageView playerCard04 = new ImageView();
    @FXML
    ImageView playerCard05 = new ImageView();
    @FXML
    ImageView playerCard06 = new ImageView();
    @FXML
    ImageView playerCard07 = new ImageView();
    @FXML
    static Vector<ImageView> imageViews = new Vector<>();
    Player player = new Player("Jon", "123");
    Deck deck = new Deck();
    int roundCount = 0;


    private void turn() {
        Turn turn = new Turn(player);
        Thread turnHandler = new Thread(turn);
        turnHandler.start();
    }

    public void startTurn(ActionEvent event) {
        turn();
    }

    public void incrementRound(ActionEvent event) {

        player.getRotatingHand().setCards(deck.drawCards(8 - roundCount));
        populateImages();

        // re-enable all buttons to that will be populated by card images this round
        enableAppropriateButtons();
        roundCount++;
    }

    public void getHands(ActionEvent event) {
        System.out.println("Rotating: " + player.getRotatingHand());
        System.out.println("Selected: " + player.getHand());

    }


    public void initialize() {

        imageViews.add(playerCard00);
        imageViews.add(playerCard07);
        imageViews.add(playerCard01);
        imageViews.add(playerCard06);
        imageViews.add(playerCard05);
        imageViews.add(playerCard02);
        imageViews.add(playerCard04);
        imageViews.add(playerCard03);
        player.drawHand(deck, 4);


        playerCard00.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
                alert.setTitle("Confirm Card Pick");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    player.firstCardPicked = true;
                    player.setSelectedCard(player.getRotatingHand().getCard(7));
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

                    player.firstCardPicked = true;
                    player.setSelectedCard(player.getRotatingHand().getCard(5));
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

                    player.firstCardPicked = true;
                    player.setSelectedCard(player.getRotatingHand().getCard(2));
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

                    player.firstCardPicked = true;
                    player.setSelectedCard(player.getRotatingHand().getCard(0));
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

                    player.firstCardPicked = true;
                    player.setSelectedCard(player.getRotatingHand().getCard(1));
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

                    player.firstCardPicked = true;
                    player.setSelectedCard(player.getRotatingHand().getCard(3));
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

                    player.firstCardPicked = true;
                    player.setSelectedCard(player.getRotatingHand().getCard(4));
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

                    player.firstCardPicked = true;
                    player.setSelectedCard(player.getRotatingHand().getCard(6));
                }
            }
        });
    }


    public static void disableButtons() {
        for (ImageView buttonClick : imageViews) {
            buttonClick.setDisable(true);
        }
    }

    private void enableAppropriateButtons() {
        for (int i = roundCount; i < imageViews.size(); i++) {
            imageViews.get(i).setDisable(false);
        }
    }

    private void populateImages() {
        for (ImageView iv : imageViews) {
            iv.setImage(null);
        }

        Vector<ImageView> reverseImageViews = (Vector) imageViews.clone();
        Collections.reverse(reverseImageViews);
        for (int i = 0; i < player.getRotatingHand().getCards().size(); i++) {
            Card tmp = player.getRotatingHand().getCard(i);
            Image image = new Image(tmp.getImagePath());
            reverseImageViews.get(i).setImage(image);
        }

    }


}
