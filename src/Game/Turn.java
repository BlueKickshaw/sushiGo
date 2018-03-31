package Game;

import Cards.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import server.Client;
import server.Network;

import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;


public class Turn implements Runnable {


    private Player player;
    private long timeLimit = (long) 5e10;
    private Vector<ImageView> playerCardImages;
    private Network network;
    private Socket socket;

    Turn(Player player,Vector<ImageView> playerCardImages, Network network) {
        this.network = network;
        this.player = player;
        this.playerCardImages = playerCardImages;
    }


    public void run() {
        socket = network.socket;
        long startTime = System.nanoTime();
        while (!player.isFirstCardPicked && (System.nanoTime() - startTime < timeLimit)) {

        }
        if (player.isFirstCardPicked) {
            disableButtons();
            System.out.println(player.getSelectedCard());
            player.getHand().addCard(player.getRotatingHand().selectAndRemoveCard(player.getSelectedCard()));
        } else if (!player.isFirstCardPicked) {
            disableButtons();
            player.setSelectedCard(player.getRotatingHand().getCard(0));
            player.getHand().addCard(player.getRotatingHand().selectAndRemoveCard(player.getRotatingHand().getCard(0)));
            System.out.println("Automatically selected card: " + player.getHand().getCard(player.getHand().getCards().size() - 1));
        }
        player.isFirstCardPicked = false;

        // First things first, the players will need to send the cards they've chosen. We want to ignore the
        // host though
        // this might nullPE -> so check for null server instead or null CCM?
        if (network.server == null) {
            // Modify the hand to pass
            Hand handToPass = player.getHand();
            handToPass.selectAndRemoveCard(player.getSelectedCard());

            // Send: ID, picked card, passed hand
            network.sendRequest(socket,"endTurn".getBytes());
            network.sendRequest(socket,network.username);
            network.sendRequest(socket,player.getSelectedCard());
            network.sendRequest(socket, handToPass);
        }
        player.setSelectedCard(null);
    }

    private void disableButtons() {
        for (ImageView buttonClick : playerCardImages) {
            buttonClick.setDisable(true);
        }
    }

}

