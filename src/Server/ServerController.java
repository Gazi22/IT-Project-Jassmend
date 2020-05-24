package Server;

import jassmendModelClasses.Card;

import java.util.ArrayList;
import java.util.Collections;

//Author Florian Jäger

public class ServerController {
    Card card;
    Gamelobby gamelobby;

    ArrayList<Card> cardsTotalCard = new ArrayList<>();

//Change numbers to words of card strings
    public String switchNumbersToWords(String number) {
        switch (number) {
            case "6":
                return "Sechs";
            case "7":
                return "Sieben";
            case "8":
                return "Acht";
            case "9":
                return "Neun";
            case "10":
                return "Zehn";
            default:
                return number;

        }
    }


    //https://stackoverflow.com/questions/1080904/how-can-i-lookup-a-java-enum-from-its-string-value/1080914
//https://stackoverflow.com/questions/9276639/java-how-to-split-a-string-by-a-number-of-characters
    public Card stringToCard(String stringOfCard) {
        if (stringOfCard.startsWith("Kreuz")) {
            String suitString;
            String rankString;

            suitString = (stringOfCard.substring(0, 5));
            rankString = switchNumbersToWords(stringOfCard.substring(5));

            Card card = new Card(Card.Suit.valueOf(suitString), Card.Rank.valueOf(rankString));
            return card;

        } else if (stringOfCard.startsWith("Herz")) {
            String suitString;
            String rankString;

            suitString = (stringOfCard.substring(0, 4));
            rankString = switchNumbersToWords(stringOfCard.substring(4));

            Card card = new Card(Card.Suit.valueOf(suitString), Card.Rank.valueOf(rankString));
            return card;

        } else if (stringOfCard.startsWith("Ecke")) {
            String suitString;
            String rankString;

            suitString = (stringOfCard.substring(0, 4));
            rankString = switchNumbersToWords(stringOfCard.substring(4));

            Card card = new Card(Card.Suit.valueOf(suitString), Card.Rank.valueOf(rankString));
            return card;

        } else if (stringOfCard.startsWith("Schaufel")) {
            String suitString;
            String rankString;

            suitString = (stringOfCard.substring(0, 8));
            rankString = switchNumbersToWords(stringOfCard.substring(8));

            Card card = new Card(Card.Suit.valueOf(suitString), Card.Rank.valueOf(rankString));
            return card;

        } else return null;
    }



//Depending on Trumpf change values of suits
    public void trumpfComparison(String trumpf) {
        switch (trumpf) {
            case "Herz":
                for(Card card:cardsTotalCard) {
                    card.trumpfHerzSuitValue();
                }
                break;
            case "Schaufel":
                for(Card card:cardsTotalCard) {
                    card.trumpfSchaufelSuitValue();

                }
                break;
            case "Ecke":
                for(Card card:cardsTotalCard) {
                    card.trumpfEckeSuitValue();

                }
                break;
            case "Kreuz":
                for(Card card:cardsTotalCard) {
                    card.trumpfKreuzSuitValue();

                }
                break;
        }

    }

    //Depending on Trumpf change values of suits without trumpf
    public void firstCardComparison(String trumpf) {
        switch (trumpf) {
            case "Herz":
                for(Card card:cardsTotalCard) {
                    card.firstCardHerzSuitValue();
                }
                break;
            case "Schaufel":
                for(Card card:cardsTotalCard) {
                    card.firstCardSchaufelSuitValue();

                }
                break;
            case "Ecke":
                for(Card card:cardsTotalCard) {
                    card.firstCardEckeSuitValue();

                }
                break;
            case "Kreuz":
                for(Card card:cardsTotalCard) {
                    card.firstCardKreuzSuitValue();

                }
                break;
        }

    }

    public Card getCardsTotalCard(int i) {
        return cardsTotalCard.get(i);
    }

    public void setCardsTotalCard(Card card) {
        cardsTotalCard.add(card);
    }

//Sort Card Arraylist
    public void sortTotalCards() {
        Collections.sort(cardsTotalCard);
        Collections.reverse(cardsTotalCard);
    }


    public void clearTotalCards() {
        cardsTotalCard.clear();
    }


    //Compare cards depending on trumpf or not
    public void handleStiche(Gamelobby gamelobby) {

        int trumpfYN = 0;
        for (int x = 0; x < 4; x++) {
            setCardsTotalCard(stringToCard(gamelobby.getCardsTotal(x)));
        }
        for (int y = 0; y < 4; y++) {
            if (gamelobby.getCardsTotal(y).startsWith(gamelobby.getTrumpf())) {
                trumpfYN = 1;
                break;
            }
            }
            if (trumpfYN == 0) {
                if (gamelobby.getCardsTotal(0).startsWith("Kreuz")) {
                    firstCardComparison("Kreuz");
                } else if (gamelobby.getCardsTotal(0).startsWith("Herz")) {
                    firstCardComparison("Herz");
                } else if (gamelobby.getCardsTotal(0).startsWith("Schaufel")) {
                    firstCardComparison("Schaufel");
                } else if (gamelobby.getCardsTotal(0).startsWith("Ecke")) {
                    firstCardComparison("Ecke");
                }

            } else{
                trumpfComparison(gamelobby.getTrumpf());
            }


            Collections.sort(cardsTotalCard);
            Collections.reverse(cardsTotalCard);
            String username2 = "";
            for (int x = 0; x < 4; x++) {
                if (getCardsTotalCard(0).toString().equals(gamelobby.getCardsWithNames(x))) {
                    username2 = gamelobby.getCardsWithNames(x + 1);
                }
                else if (getCardsTotalCard(0).toString().equals(gamelobby.getCardsWithNames(x+4))) {
                    username2 = gamelobby.getCardsWithNames(x + 5);
                }
            }
            for (int y = 0; y < 2; y++) {
                if (username2.equals(gamelobby.getTeam1Members(y))) {
                    gamelobby.setSticheTeam1(getCardsTotalCard(0));
                    gamelobby.setSticheTeam1(getCardsTotalCard(1));
                    gamelobby.setSticheTeam1(getCardsTotalCard(2));
                    gamelobby.setSticheTeam1(getCardsTotalCard(3));
                    gamelobby.setSticheTeam2(null);
                    gamelobby.setSticheTeam2(null);
                    gamelobby.setSticheTeam2(null);
                    gamelobby.setSticheTeam2(null);


                } else if (username2.equals(gamelobby.getTeam2Members(y))) {
                    gamelobby.setSticheTeam2(getCardsTotalCard(0));
                    gamelobby.setSticheTeam2(getCardsTotalCard(1));
                    gamelobby.setSticheTeam2(getCardsTotalCard(2));
                    gamelobby.setSticheTeam2(getCardsTotalCard(3));
                    gamelobby.setSticheTeam1(null);
                    gamelobby.setSticheTeam1(null);
                    gamelobby.setSticheTeam1(null);
                    gamelobby.setSticheTeam1(null);
                }
            }

            gamelobby.clearTotalCards(); //clear string cards arraylist
            clearTotalCards(); //clear card cards arraylist
            gamelobby.clearCardsWithNames();//Clear cards with names arraylist string
            gamelobby.setStichWinner(username2);

        }



//Evaluate points of Stiche

        public int getPoints(Gamelobby gamelobby, Card card){

            if (card.getSuit().toString().equals( gamelobby.getTrumpf())) {
                if (card.getRank().toString() .equals ("Bube")) {
                    return 20;
                } else if (card.getRank().toString() .equals ("9")) {
                    return 14;
                }
               else if (card.getRank().toString() .equals ("Koenig")) {
                    return 4;
                }
               else if (card.getRank().toString() .equals ("Dame")) {
                    return 3;
                }
               else if (card.getRank().toString() .equals ("10")) {
                    return 10;
                }
               else if (card.getRank().toString() .equals ("Ass")) {
                    return 11;
                }
            }

           else if(card.getSuit().toString()!=gamelobby.getTrumpf()){
                if (card.getRank().toString() .equals ("Bube")) {
                    return 2;
                }
                if (card.getRank().toString() .equals ("Koenig")) {
                    return 4;
                }
                if (card.getRank().toString() .equals ("Dame")) {
                    return 3;
                }
                if (card.getRank().toString() .equals ("10")){
                    return 10;
                }
                if (card.getRank().toString() .equals ("Ass")) {
                    return 11;
                }
            }



            return 0;
        }




}

