package Game;

import Cards.Card;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.Optional;
import java.util.Vector;


public class FourPlayerController /*implements Runnable*/ {

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
    Pane playerPane1 = new Pane();
    @FXML
    Button paneButton00;
    @FXML
    Button paneButton01;
    @FXML
    Button paneButton02;
    @FXML
    Button paneButton03;
    @FXML
    Button paneButton04;
    @FXML
    Button paneButton05;
    @FXML
    Button paneButton06;
    @FXML
    Button paneButton07;

    Vector<Button> buttons = new Vector<>();
    Vector<ImageView> imageViews = new Vector<>();

    Player player = new Player("Jon", "123");
    Deck deck = new Deck();
    int roundCount = 0;
    Turn turn = new Turn(player);

    private Card selectedCard = null;
    private boolean cardConfirmed = false;
    double turnTimeLimit = 4e9;

    private void turn() {
        Thread turnHandler = new Thread(turn, player.getName());
        turnHandler.start();
        }

//    public void run() {
//
//    }

    public void startTurn(ActionEvent event) {
//        Thread primaryPlayerTurnThread = new Thread(player, player.getName());
//        primaryPlayerTurnThread.start();
        turn();
    }


    public void initialize() {
        imageViews.add(playerCard03);
        imageViews.add(playerCard04);
        imageViews.add(playerCard02);
        imageViews.add(playerCard05);
        imageViews.add(playerCard06);
        imageViews.add(playerCard01);
        imageViews.add(playerCard07);
        imageViews.add(playerCard00);
        player.drawHand(deck, 4);
        buttons.add(paneButton00);
        buttons.add(paneButton07);
        buttons.add(paneButton01);
        buttons.add(paneButton06);
        buttons.add(paneButton05);
        buttons.add(paneButton02);
        buttons.add(paneButton04);
        buttons.add(paneButton03);

    }


    public void incrementRound(ActionEvent event) {
        player.getRotatingHand().setCards(deck.drawCards(7 - roundCount));


        // re-enable all buttons to that will be populated by card images this round
        enableAppropiateButtons();
        System.out.println(player.getHand());
        player.firstCardPicked = true;
        roundCount++;
    }

    public void getRotatingHand(ActionEvent event) {

        for (ImageView iv : imageViews) {
            iv.setImage(null);
        }
        for (int i = 0; i < player.getRotatingHand().getCards().size(); i++) {
            Card tmp = player.getRotatingHand().getCard(i);
            Image image = new Image(tmp.getImagePath());
            imageViews.get(i).setImage(image);
        }


    }


    public void button00Clicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
        alert.setTitle("Confirm Card Pick");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            disableButtons();
            System.out.println("0");
            player.getHand().addCard(player.getRotatingHand().selectAndRemoveCard(player.getRotatingHand().getCard(7)));
        } else {
            System.out.println("cancelled");
        }
    }

    public void button01Clicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
        alert.setTitle("Confirm Card Pick");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            disableButtons();
            System.out.println("1");
            player.getHand().addCard(player.getRotatingHand().selectAndRemoveCard(player.getRotatingHand().getCard(5)));
        } else {
            System.out.println("cancelled");
        }
    }

    public void button02Clicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
        alert.setTitle("Confirm Card Pick");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            disableButtons();
            System.out.println("2");
            player.getHand().addCard(player.getRotatingHand().selectAndRemoveCard(player.getRotatingHand().getCard(2)));
        } else {
            System.out.println("cancelled");
        }
    }

    public void button03Clicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
        alert.setTitle("Confirm Card Pick");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            disableButtons();
            System.out.println("3");
            cardConfirmed = true;
            player.getHand().addCard(player.getRotatingHand().selectAndRemoveCard(player.getRotatingHand().getCard(0)));
        } else {
            System.out.println("cancelled");
        }
    }

    public void button04Clicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
        alert.setTitle("Confirm Card Pick");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            disableButtons();
            System.out.println("4");
            player.getHand().addCard(player.getRotatingHand().selectAndRemoveCard(player.getRotatingHand().getCard(1)));
        } else {
            System.out.println("cancelled");
        }
    }

    public void button05Clicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
        alert.setTitle("Confirm Card Pick");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            disableButtons();
            System.out.println("5");
            player.getHand().addCard(player.getRotatingHand().selectAndRemoveCard(player.getRotatingHand().getCard(3)));
        } else {
            System.out.println("cancelled");
        }
    }

    public void button06Clicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
        alert.setTitle("Confirm Card Pick");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            disableButtons();
            System.out.println("6");
            player.getHand().addCard(player.getRotatingHand().selectAndRemoveCard(player.getRotatingHand().getCard(4)));
        } else {
            System.out.println("cancelled");
        }
    }

    public void button07Clicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Final Choice?");
        alert.setTitle("Confirm Card Pick");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            disableButtons();
            System.out.println("7");
            player.getHand().addCard(player.getRotatingHand().selectAndRemoveCard(player.getRotatingHand().getCard(6)));
        } else {
            System.out.println("cancelled");
        }
    }

    private void disableButtons() {
        for (Button button : buttons) {
            button.setDisable(true);
        }
    }

    private void enableAppropiateButtons() {
        for (int i = 7; i > roundCount; i--) {
            buttons.get(i).setDisable(false);
        }
    }

}
