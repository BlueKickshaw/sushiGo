package Cards;

import java.util.Objects;

public abstract class Card {

    private String cardBackImagePath = "/CardImages/Cardback.jpg";
    private String name;

    public Card(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getCardBackImagePath() {
        return cardBackImagePath;
    }


}


