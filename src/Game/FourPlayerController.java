package Game;

import Cards.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.EventListener;
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
    Pane pane = new Pane();


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

    public void testButton(ActionEvent event) {
        player.getRotatingHand().selectAndRemoveCard(player.getRotatingHand().getCard(0));
        buttons.get(roundCount).setDisable(true);
        roundCount++;
    }

    public void testButton2(ActionEvent event) {

        for (ImageView iv : imageViews) {
            iv.setImage(null);
        }
        for (int i = 0; i < player.getRotatingHand().getCards().size(); i++) {
            Card tmp = player.getRotatingHand().getCard(i);
            Image image = new Image(tmp.getImagePath());
            imageViews.get(i).setImage(image);
            primaryPlayerGrid.getChildren().get(i).setUserData(player.getRotatingHand().getCard(0));
        }


    }

    public void button00Clicked(ActionEvent event) {
        System.out.println("0");
    }

    public void button01Clicked(ActionEvent event) {
        System.out.println("1");
    }

    public void button02Clicked(ActionEvent event) {
        System.out.println("2");
    }

    public void button03Clicked(ActionEvent event) {
        System.out.println("3");
    }

    public void button04Clicked(ActionEvent event) {
        System.out.println("4");
    }

    public void button05Clicked(ActionEvent event) {
        System.out.println("5");
    }

    public void button06Clicked(ActionEvent event) {
        System.out.println("6");
    }

    public void button07Clicked(ActionEvent event) {
        System.out.println("7");
    }

}
