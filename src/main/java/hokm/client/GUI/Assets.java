package hokm.client.GUI;

import hokm.Card;

import javax.swing.*;
import java.net.URL;

public class Assets {
    public static ImageIcon getImageIcon(String filePath){
        return new ImageIcon(Assets.class.getClassLoader().getResource("pictures/"+filePath));
    }
    public static ImageIcon getCardImageIcon(Card card){
        return getImageIcon("cards/"+card.suit()+'/'+card.value()+".png");
    }
}
