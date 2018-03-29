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
    static Vector<ImageView> playerCardImages = new Vector<>();
    @FXML
    ImageView opponent0Card00 = new ImageView();
    @FXML
    ImageView opponent0Card01 = new ImageView();
    @FXML
    ImageView opponent0Card02 = new ImageView();
    @FXML
    ImageView opponent0Card03 = new ImageView();
    @FXML
    ImageView opponent0Card04  = new ImageView();
    @FXML
    ImageView opponent0Card05  = new ImageView();
    @FXML
    ImageView opponent0Card06  = new ImageView();
    @FXML
    ImageView opponent0Card07  = new ImageView();
    Vector<ImageView> opponent0CardBacks = new Vector<>();
    @FXML
    ImageView leftOpponent0Card00 = new ImageView();
    @FXML
    ImageView leftOpponent0Card01 = new ImageView();
    @FXML
    ImageView leftOpponent0Card02 = new ImageView();
    @FXML
    ImageView leftOpponent0Card03 = new ImageView();
    @FXML
    ImageView leftOpponent0Card04  = new ImageView();
    @FXML
    ImageView leftOpponent0Card05  = new ImageView();
    @FXML
    ImageView leftOpponent0Card06  = new ImageView();
    @FXML
    ImageView leftOpponent0Card07  = new ImageView();
    Vector<ImageView> leftOpponent0CardBacks = new Vector<>();
    @FXML
    ImageView rightOpponent0Card00 = new ImageView();
    @FXML
    ImageView rightOpponent0Card01 = new ImageView();
    @FXML
    ImageView rightOpponent0Card02 = new ImageView();
    @FXML
    ImageView rightOpponent0Card03 = new ImageView();
    @FXML
    ImageView rightOpponent0Card04  = new ImageView();
    @FXML
    ImageView rightOpponent0Card05  = new ImageView();
    @FXML
    ImageView rightOpponent0Card06  = new ImageView();
    @FXML
    ImageView rightOpponent0Card07  = new ImageView();
    Vector<ImageView> rightOpponent0CardBacks = new Vector<>();


    Image cardBack = new Image("/Game/CardImages/Cardback.jpg");
    Image rotatedCardBack = new Image ("/Game/CardImages/Cardback_Rotated.jpg");

    Player player = new Player("Jon", "123");
    Deck deck = new Deck();
    int roundCount = 0;


    private void turn() {
        Turn turn = new Turn(player);
        Thread turnHandler = new Thread(turn);
        turnHandler.start();
    }


    public void incrementRound(ActionEvent event) {

        player.getRotatingHand().setCards(deck.drawCards(8 - roundCount));
        populateImages(playerCardImages);
        populateCardBacks(opponent0CardBacks, cardBack);
        populateCardBacks(leftOpponent0CardBacks, rotatedCardBack);
        populateCardBacks(rightOpponent0CardBacks, rotatedCardBack);
//        leftOpponent0CardBacks.get(1).setImage(cardBack);
        // re-enable all buttons to that will be populated by card images this round
        enableAppropriateButtons();
        roundCount++;
        turn();
    }

    public void getHands(ActionEvent event) {
        System.out.println("Rotating: " + player.getRotatingHand());
        System.out.println("Selected: " + player.getHand());

    }


    public void initialize() {

        playerCardImages.add(playerCard00);
        playerCardImages.add(playerCard07);
        playerCardImages.add(playerCard01);
        playerCardImages.add(playerCard06);
        playerCardImages.add(playerCard05);
        playerCardImages.add(playerCard02);
        playerCardImages.add(playerCard04);
        playerCardImages.add(playerCard03);
        opponent0CardBacks.add(opponent0Card00);
        opponent0CardBacks.add(opponent0Card07);
        opponent0CardBacks.add(opponent0Card01);
        opponent0CardBacks.add(opponent0Card06);
        opponent0CardBacks.add(opponent0Card05);
        opponent0CardBacks.add(opponent0Card02);
        opponent0CardBacks.add(opponent0Card04);
        opponent0CardBacks.add(opponent0Card03);
        leftOpponent0CardBacks.add(leftOpponent0Card00);
        leftOpponent0CardBacks.add(leftOpponent0Card07);
        leftOpponent0CardBacks.add(leftOpponent0Card01);
        leftOpponent0CardBacks.add(leftOpponent0Card06);
        leftOpponent0CardBacks.add(leftOpponent0Card05);
        leftOpponent0CardBacks.add(leftOpponent0Card02);
        leftOpponent0CardBacks.add(leftOpponent0Card04);
        leftOpponent0CardBacks.add(leftOpponent0Card03);
        rightOpponent0CardBacks.add(rightOpponent0Card00);
        rightOpponent0CardBacks.add(rightOpponent0Card07);
        rightOpponent0CardBacks.add(rightOpponent0Card01);
        rightOpponent0CardBacks.add(rightOpponent0Card06);
        rightOpponent0CardBacks.add(rightOpponent0Card05);
        rightOpponent0CardBacks.add(rightOpponent0Card02);
        rightOpponent0CardBacks.add(rightOpponent0Card04);
        rightOpponent0CardBacks.add(rightOpponent0Card03);


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
        for (ImageView buttonClick : playerCardImages) {
            buttonClick.setDisable(true);
        }
    }

    private void enableAppropriateButtons() {
        for (int i = roundCount; i < playerCardImages.size(); i++) {
            playerCardImages.get(i).setDisable(false);
        }
    }

    private void populateImages(Vector<ImageView> images) {
        for (ImageView iv : images) {
            iv.setImage(null);
        }

        Vector<ImageView> reverseImageViews = (Vector) images.clone();
        Collections.reverse(reverseImageViews);
        for (int i = 0; i < player.getRotatingHand().getCards().size(); i++) {
            Card tmp = player.getRotatingHand().getCard(i);
            Image image = new Image(tmp.getImagePath());
            reverseImageViews.get(i).setImage(image);
        }

    }

    private void populateCardBacks(Vector<ImageView> oppCardsView, Image back){
        for (ImageView iv : oppCardsView) {
            iv.setImage(null);
        }
        Vector<ImageView> reverseImageViews = (Vector) oppCardsView.clone();
        Collections.reverse(reverseImageViews);
        for (int i = 0; i < player.getRotatingHand().getCards().size(); i++) {
            reverseImageViews.get(i).setImage(back);
        }

    }


}
