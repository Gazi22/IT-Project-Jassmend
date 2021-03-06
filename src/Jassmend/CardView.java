package Jassmend;

import jassmendModelClasses.Card;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Code from Pokerproject || with adjustments from Davide Seabra

    public class CardView extends Button {

    public CardView() {
        super();
       this.getStyleClass().add("card");

    }
   public void setCard(Card card) {


        if (card != null) {
            String fileName = cardToFileName(card);
            Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("cards/images/" + fileName));
            ImageView imv = new ImageView(image);
        	imv.setFitWidth(100);
        	imv.setFitHeight(100);
            imv.setPreserveRatio(true);
            this.setGraphic(imv);

        }

        else {


            this.setGraphic(null);

            System.out.println("Could not set graphics for cards");
            
            
        }

    }


     private String cardToFileName(Card card) {
        String rank = card.getRank().toString();
        String suit = card.getSuit().toString();
        return suit + "_" + rank + ".jpg";
    }
} 
