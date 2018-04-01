package Game;

import Cards.Card;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import server.Network;

import java.util.*;


public class FourPlayerController {
    public static Network network;
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
    Vector<ImageView> playerCardImages = new Vector<>();

    @FXML    ImageView playerHandCard00 = new ImageView();
    @FXML    ImageView playerHandCard01 = new ImageView();
    @FXML    ImageView playerHandCard02 = new ImageView();
    @FXML    ImageView playerHandCard03 = new ImageView();
    @FXML    ImageView playerHandCard04 = new ImageView();
    @FXML    ImageView playerHandCard05 = new ImageView();
    @FXML    ImageView playerHandCard06 = new ImageView();
    @FXML    ImageView playerHandCard07 = new ImageView();
    Vector<ImageView> handCardImages = new Vector<>();

    @FXML    ImageView topOpponentCard00 = new ImageView();
    @FXML    ImageView topOpponentCard01 = new ImageView();
    @FXML    ImageView topOpponentCard02 = new ImageView();
    @FXML    ImageView topOpponentCard03 = new ImageView();
    @FXML    ImageView topOpponentCard04 = new ImageView();
    @FXML    ImageView topOpponentCard05 = new ImageView();
    @FXML    ImageView topOpponentCard06 = new ImageView();
    @FXML    ImageView topOpponentCard07 = new ImageView();
    Vector<ImageView> topOpponentCardBacks = new Vector<>();
    @FXML    ImageView topPlayerHandCard00 = new ImageView();
    @FXML    ImageView topPlayerHandCard01 = new ImageView();
    @FXML    ImageView topPlayerHandCard02 = new ImageView();
    @FXML    ImageView topPlayerHandCard03 = new ImageView();
    @FXML    ImageView topPlayerHandCard04 = new ImageView();
    @FXML    ImageView topPlayerHandCard05 = new ImageView();
    @FXML    ImageView topPlayerHandCard06 = new ImageView();
    @FXML    ImageView topPlayerHandCard07 = new ImageView();
    Vector<ImageView> topOpponentHandCardImages = new Vector<>();

    @FXML    ImageView leftOpponentCard00 = new ImageView();
    @FXML    ImageView leftOpponentCard01 = new ImageView();
    @FXML    ImageView leftOpponentCard02 = new ImageView();
    @FXML    ImageView leftOpponentCard03 = new ImageView();
    @FXML    ImageView leftOpponentCard04 = new ImageView();
    @FXML    ImageView leftOpponentCard05 = new ImageView();
    @FXML    ImageView leftOpponentCard06 = new ImageView();
    @FXML    ImageView leftOpponentCard07 = new ImageView();
    Vector<ImageView> leftOpponentCardBacks = new Vector<>();
    @FXML    ImageView leftPlayerHandCard00 = new ImageView();
    @FXML    ImageView leftPlayerHandCard01 = new ImageView();
    @FXML    ImageView leftPlayerHandCard02 = new ImageView();
    @FXML    ImageView leftPlayerHandCard03 = new ImageView();
    @FXML    ImageView leftPlayerHandCard04 = new ImageView();
    @FXML    ImageView leftPlayerHandCard05 = new ImageView();
    @FXML    ImageView leftPlayerHandCard06 = new ImageView();
    @FXML    ImageView leftPlayerHandCard07 = new ImageView();
    Vector<ImageView> leftOpponentHandCardImages = new Vector<>();
    Vector<Label> scoreLabels = new Vector<>();

    @FXML    ImageView rightOpponentCard00 = new ImageView();
    @FXML    ImageView rightOpponentCard01 = new ImageView();
    @FXML    ImageView rightOpponentCard02 = new ImageView();
    @FXML    ImageView rightOpponentCard03 = new ImageView();
    @FXML    ImageView rightOpponentCard04 = new ImageView();
    @FXML    ImageView rightOpponentCard05 = new ImageView();
    @FXML    ImageView rightOpponentCard06 = new ImageView();
    @FXML    ImageView rightOpponentCard07 = new ImageView();
    Vector<ImageView> rightOpponentCardBacks = new Vector<>();
    @FXML    ImageView rightPlayerHandCard00 = new ImageView();
    @FXML    ImageView rightPlayerHandCard01 = new ImageView();
    @FXML    ImageView rightPlayerHandCard02 = new ImageView();
    @FXML    ImageView rightPlayerHandCard03 = new ImageView();
    @FXML    ImageView rightPlayerHandCard04 = new ImageView();
    @FXML    ImageView rightPlayerHandCard05 = new ImageView();
    @FXML    ImageView rightPlayerHandCard06 = new ImageView();
    @FXML    ImageView rightPlayerHandCard07 = new ImageView();
    Vector<ImageView> rightOpponentHandCardImages = new Vector<>();
    @FXML    GridPane scoreGridPane = new GridPane();
    @FXML    Label firstPlaceText = new Label();
    @FXML    Label secondPlaceText = new Label();
    @FXML    Label thirdPlaceText = new Label();
    @FXML    Label fourthPlaceText = new Label();




    Image cardBack = new Image("/Game/CardImages/Cardback.jpg");
    Image rotatedCardBack = new Image("/Game/CardImages/Cardback_Rotated.jpg");

    Player player = new Player("Jon", "123");
    Player topOpponent = new Player("dummy", "321");
    Player leftOpponent = new Player ("lefty", "555");
    Player rightOpponent = new Player("righty", "777");
    Vector<Player> playerList = new Vector<>();
    private Vector<Vector<ImageView>> rotatingImages = new Vector<>();
    private Vector<Vector<ImageView>> handImages= new Vector<>();


    private void turn() {
        Turn turn = new Turn(player, playerCardImages, network);
        Thread turnHandler = new Thread(turn);
        turnHandler.start();
    }


    public void incrementRound(ActionEvent event) {
    }

    public void getHands(ActionEvent event) {
        Thread gameHandler = new Thread(driver);
        gameHandler.start();
//        GameDriver.calculatePoints(playerList, 0);
//        updateScores(playerList);
        System.out.println(player.getName());
//        System.out.println("\tRotating: " + player.getRotatingHand());
//        System.out.println("\tSelected: " + player.getHand());
        System.out.println(topOpponent.getName());
        System.out.println("\tRotating: " + topOpponent.getRotatingHand());
        System.out.println("\tSelected: " + topOpponent.getHand());

    }


    public void initialize() {
        scoreLabels.add(firstPlaceText);
        scoreLabels.add(secondPlaceText);
        scoreLabels.add(thirdPlaceText);
        scoreLabels.add(fourthPlaceText);


        playerList.add(player);
        playerList.add(topOpponent);
        playerList.add(leftOpponent);
        playerList.add(rightOpponent);


        playerCardImages.add(playerCard00);
        playerCardImages.add(playerCard07);
        playerCardImages.add(playerCard01);
        playerCardImages.add(playerCard06);
        playerCardImages.add(playerCard05);
        playerCardImages.add(playerCard02);
        playerCardImages.add(playerCard04);
        playerCardImages.add(playerCard03);
        topOpponentCardBacks.add(topOpponentCard00);
        topOpponentCardBacks.add(topOpponentCard07);
        topOpponentCardBacks.add(topOpponentCard01);
        topOpponentCardBacks.add(topOpponentCard06);
        topOpponentCardBacks.add(topOpponentCard05);
        topOpponentCardBacks.add(topOpponentCard02);
        topOpponentCardBacks.add(topOpponentCard04);
        topOpponentCardBacks.add(topOpponentCard03);
        leftOpponentCardBacks.add(leftOpponentCard00);
        leftOpponentCardBacks.add(leftOpponentCard07);
        leftOpponentCardBacks.add(leftOpponentCard01);
        leftOpponentCardBacks.add(leftOpponentCard06);
        leftOpponentCardBacks.add(leftOpponentCard05);
        leftOpponentCardBacks.add(leftOpponentCard02);
        leftOpponentCardBacks.add(leftOpponentCard04);
        leftOpponentCardBacks.add(leftOpponentCard03);
        rightOpponentCardBacks.add(rightOpponentCard00);
        rightOpponentCardBacks.add(rightOpponentCard07);
        rightOpponentCardBacks.add(rightOpponentCard01);
        rightOpponentCardBacks.add(rightOpponentCard06);
        rightOpponentCardBacks.add(rightOpponentCard05);
        rightOpponentCardBacks.add(rightOpponentCard02);
        rightOpponentCardBacks.add(rightOpponentCard04);
        rightOpponentCardBacks.add(rightOpponentCard03);

        handCardImages.add(playerHandCard00);
        handCardImages.add(playerHandCard01);
        handCardImages.add(playerHandCard02);
        handCardImages.add(playerHandCard03);
        handCardImages.add(playerHandCard04);
        handCardImages.add(playerHandCard05);
        handCardImages.add(playerHandCard06);
        handCardImages.add(playerHandCard07);

        topOpponentHandCardImages.add(topPlayerHandCard00);
        topOpponentHandCardImages.add(topPlayerHandCard01);
        topOpponentHandCardImages.add(topPlayerHandCard02);
        topOpponentHandCardImages.add(topPlayerHandCard03);
        topOpponentHandCardImages.add(topPlayerHandCard04);
        topOpponentHandCardImages.add(topPlayerHandCard05);
        topOpponentHandCardImages.add(topPlayerHandCard06);
        topOpponentHandCardImages.add(topPlayerHandCard07);

        leftOpponentHandCardImages.add(leftPlayerHandCard00);
        leftOpponentHandCardImages.add(leftPlayerHandCard01);
        leftOpponentHandCardImages.add(leftPlayerHandCard02);
        leftOpponentHandCardImages.add(leftPlayerHandCard03);
        leftOpponentHandCardImages.add(leftPlayerHandCard04);
        leftOpponentHandCardImages.add(leftPlayerHandCard05);
        leftOpponentHandCardImages.add(leftPlayerHandCard06);
        leftOpponentHandCardImages.add(leftPlayerHandCard07);

        rightOpponentHandCardImages.add(rightPlayerHandCard00);
        rightOpponentHandCardImages.add(rightPlayerHandCard01);
        rightOpponentHandCardImages.add(rightPlayerHandCard02);
        rightOpponentHandCardImages.add(rightPlayerHandCard03);
        rightOpponentHandCardImages.add(rightPlayerHandCard04);
        rightOpponentHandCardImages.add(rightPlayerHandCard05);
        rightOpponentHandCardImages.add(rightPlayerHandCard06);
        rightOpponentHandCardImages.add(rightPlayerHandCard07);


        rotatingImages.add(playerCardImages);
        rotatingImages.add(topOpponentCardBacks);
        rotatingImages.add(leftOpponentCardBacks);
        rotatingImages.add(rightOpponentCardBacks);
        handImages.add(handCardImages);
        handImages.add(topOpponentHandCardImages);
        handImages.add(leftOpponentHandCardImages);
        handImages.add(rightOpponentHandCardImages);

        playerCard00.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
                alert.setTitle("Confirm Card Pick");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    player.isFirstCardPicked = true;
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

                    player.isFirstCardPicked = true;
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

                    player.isFirstCardPicked = true;
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

                    player.isFirstCardPicked = true;
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

                    player.isFirstCardPicked = true;
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

                    player.isFirstCardPicked = true;
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

                    player.isFirstCardPicked = true;
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

                    player.isFirstCardPicked = true;
                    player.setSelectedCard(player.getRotatingHand().getCard(6));
                }
            }
        });


        driver = new GameDriver(playerList, rotatingImages, handImages, null, scoreLabels, player);
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
        thirdPlaceText.setText(clonePlayerList.get(2).getName() + "     " + clonePlayerList.get(2).getTotalPoints() + " Total Points");
        fourthPlaceText.setText(clonePlayerList.get(3).getName() + "     " + clonePlayerList.get(3).getTotalPoints() + " Total Points");
    }


}
