package Server;

import jassmendModel.Card;

import java.util.ArrayList;
import java.util.Collections;

public class ServerController {
Card card;

    ArrayList<Card> cardsTotalCard = new ArrayList<>();





    public String switchNumbersToWords(String number){
        switch(number){
            case "6": return "Sechs";
            case "7": return "Sieben";
            case "8": return "Acht";
            case "9": return "Neun";
            case "10": return "Zehn";
            default:
                return number;

        }
    }


    //https://stackoverflow.com/questions/1080904/how-can-i-lookup-a-java-enum-from-its-string-value/1080914
//https://stackoverflow.com/questions/9276639/java-how-to-split-a-string-by-a-number-of-characters
    public Card stringToCard(String stringOfCard){
        if(stringOfCard.startsWith("Kreuz")){
            String suitString;
            String rankString;

            suitString=(stringOfCard.substring(0,5));
            rankString=switchNumbersToWords(stringOfCard.substring(5));

            Card card=new Card(Card.Suit.valueOf(suitString),Card.Rank.valueOf(rankString));
            return card;

        }
        else if(stringOfCard.startsWith("Herz")){
            String suitString;
            String rankString;

            suitString=(stringOfCard.substring(0,4));
            rankString=switchNumbersToWords(stringOfCard.substring(4));

            Card card=new Card(Card.Suit.valueOf(suitString),Card.Rank.valueOf(rankString));
            return card;

        }
        else if(stringOfCard.startsWith("Ecke")){
            String suitString;
            String rankString;

            suitString=(stringOfCard.substring(0,4));
            rankString=switchNumbersToWords(stringOfCard.substring(4));

            Card card=new Card(Card.Suit.valueOf(suitString),Card.Rank.valueOf(rankString));
            return card;

        }
        else if(stringOfCard.startsWith("Schaufel")){
            String suitString;
            String rankString;

            suitString=(stringOfCard.substring(0,8));
            rankString=switchNumbersToWords(stringOfCard.substring(8));

            Card card=new Card(Card.Suit.valueOf(suitString),Card.Rank.valueOf(rankString));
            return card;

        }

        else return null;
    }


    public void trumpfComparison(String trumpf){
        switch(trumpf){
            case "Herz":
                card.trumpfHerzSuitValue();
                break;

            case "Schaufel":
                card.trumpfSchaufelSuitValue();
                break;

            case "Ecke":
                card.trumpfEckeSuitValue();
                break;

            case "Kreuz":
                card.trumpfKreuzSuitValue();
                break;
        }

    }

    public Card getCardsTotalCard(int i){
        return cardsTotalCard.get(i);
    }

    public void setCardsTotalCard(Card card) {
        cardsTotalCard.add(card);
    }


    public void sortTotalCards() {
        Collections.sort(cardsTotalCard);
    }
}
