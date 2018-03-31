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

    @FXML    Label firstPlaceText = new Label();
    @FXML    Label secondPlaceText = new Label();


    Image cardBack = new Image("/Game/CardImages/Cardback.jpg");
    Image rotatedCardBack = new Image("/Game/CardImages/Cardback_Rotated.jpg");

    Player player = new Player("Jon", "123");
    Player topOpponent = new Player("dummy", "321");
    Vector<Player> playerList = new Vector<>();

    Deck deck = new Deck();
    int roundCount = 0;


    private void turn() {
        Turn turn = new Turn(player, playerCardImages, network);
        Thread turnHandler = new Thread(turn);
        turnHandler.start();
    }


    public void incrementRound(ActionEvent event) {

        player.getRotatingHand().setCards(deck.drawCards(10 - roundCount));
        populateImages(playerCardImages);
        populateCardBacks(topOpponentCardBacks, cardBack);
        roundCount++;
        if (roundCount > 0) {
            setHandImages(player, handCardImages);
            setHandImages(topOpponent, topOpponentHandCardImages);
            topOpponent.getHand().addCard(deck.drawCards(1).firstElement());
        }
        turn();
    }

    public void getHands(ActionEvent event) {
        GameDriver.calculatePoints(playerList, 0);
        updateScores(playerList);
        System.out.println(player.getName());
        System.out.println("\tRotating: " + player.getRotatingHand());
        System.out.println("\tSelected: " + player.getHand());
        System.out.println(topOpponent.getName());
        System.out.println("\tSelected: " + topOpponent.getHand());

    }


    public void initialize() {
        playerList.add(player);
        playerList.add(topOpponent);

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



        player.drawHand(deck, 2);


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
        playerCard08.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
                alert.setTitle("Confirm Card Pick");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    player.firstCardPicked = true;
                    player.setSelectedCard(player.getRotatingHand().getCard(9));
                }
            }
        });
        playerCard09.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
                alert.setTitle("Confirm Card Pick");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    player.firstCardPicked = true;
                    player.setSelectedCard(player.getRotatingHand().getCard(8));
                }
            }
        });
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
            reverseImageViews.get(i).setDisable(false);
            reverseImageViews.get(i).setImage(image);
        }

    }

    private void populateCardBacks(Vector<ImageView> oppCardsView, Image back) {
        for (ImageView iv : oppCardsView) {
            iv.setImage(null);
        }
        Vector<ImageView> reverseImageViews = (Vector) oppCardsView.clone();
        Collections.reverse(reverseImageViews);
        for (int i = 0; i < player.getRotatingHand().getCards().size(); i++) {
            reverseImageViews.get(i).setImage(back);
        }

    }

    private void setHandImages(Player player, Vector<ImageView> images) {
        for (ImageView img : images) {
            img.setImage(null);
        }

        for (int i = 0; i < player.getHand().getCards().size(); i++) {
            Image tmp = new Image(player.getHand().getCard(i).getImagePath());
            images.get(i).setImage(tmp);
        }

    }
    private void updateScores(Vector<Player> players){
        Vector<Player> clonePlayerList = (Vector) players.clone();
        clonePlayerList.sort(Comparator.comparingInt(Player::getTotalPoints));
        Collections.reverse(clonePlayerList);
        firstPlaceText.setText(clonePlayerList.get(0).getName() + "     " + clonePlayerList.get(0).getTotalPoints() + " Total Points");
        secondPlaceText.setText(clonePlayerList.get(1).getName() + "     " + clonePlayerList.get(1).getTotalPoints() + " Total Points");
    }


}
