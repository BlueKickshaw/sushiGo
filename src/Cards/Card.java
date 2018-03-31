package Cards;

import java.io.Serializable;

public abstract class Card implements Serializable{

    private String cardBackImagePath = "/Game/CardImages/Cardback.jpg";
    private String name;

    public Card(String name) {
        this.name = name;
    }

    public abstract String getImagePath();

    public String getName() {
        return this.name;
    }

    public String getCardBackImagePath() {
        return cardBackImagePath;
    }


}


