package Server.message;

import Server.Client;
import Server.Gamelobby;
import Server.ServerController;
import jassmendModel.Card;

import java.util.Collections;

/**
 * Add a user as a member of a gamelobby.
 */
public class CardPlayed extends Message {
    private String token;
    private String name;
    private String username;
    private String cardPlayed;
    ServerController serverController;


    public CardPlayed(String[] data) {
        super(data);
        this.token = data[1];
        this.name = data[2];
        this.username = data[3];
        this.cardPlayed = data[4];
    }


    /**
     * The owner of a gamelobby can add anyone. A user can add themselves to any
     * public gamelobby. Otherwise, no one can add anyone.
     */
    @Override
    public void process(Client client) {
        String[] arrCardsPlayed = new String[4];
        String cardCounter = "";
        String roundCounter = "";
        if (client.getToken().equals(token)) {
            Gamelobby gamelobby = Gamelobby.exists(name);
            gamelobby.increaseCardCounter();
            roundCounter = Integer.toString(gamelobby.getRoundCounter());
            if (gamelobby.getCardCounter() > 4) {
                gamelobby.resetCardCounter();
                gamelobby.increaseCardCounter();
            }

            for (int x = gamelobby.getCardCounter(); x < gamelobby.getCardCounter() + 1; x++) {

                gamelobby.setCardsInRound(x, cardPlayed);
                arrCardsPlayed[x - 1] = gamelobby.getCardsInRound(x - 1);
                cardCounter = Integer.toString(gamelobby.getCardCounter());
            }
            gamelobby.addToCardsTotal(cardPlayed);
              if (gamelobby.getCardCounter() == 4){
                  int trumpfYN=0;
                for(int x =1;x<4;x++){
                    serverController.setCardsTotalCard(serverController.stringToCard(gamelobby.getCardsTotal(x)));
                }
                for(int y =1;y<4;y++){
                    if(!gamelobby.getCardsTotal(y).startsWith(gamelobby.getTrumpf())){
                        trumpfYN=0;
                    }

                    else trumpfYN=1;
                }
                if (trumpfYN==0){
                    if(gamelobby.getCardsTotal(0).startsWith("Kreuz")){
                        serverController.trumpfComparison("Kreuz");}

                    else if(gamelobby.getCardsTotal(0).startsWith("Herz")){
                        serverController.trumpfComparison("Herz");}

                    else if(gamelobby.getCardsTotal(0).startsWith("Schaufel")){
                        serverController.trumpfComparison("Schaufel");}

                    else if(gamelobby.getCardsTotal(0).startsWith("Ecke")){
                        serverController.trumpfComparison("Ecke");}

                    else serverController.trumpfComparison(gamelobby.getTrumpf());

                }



                serverController.sortTotalCards();
                //prüfen wenn keine Trumpf - welche Karte zuerst gespielt wurde
                //Abgleich in welche Teamarray das geschrieben werden muss...


                }
        }


        String[] gameInfo = new String[4];
        gameInfo[1] = this.token;
        gameInfo[2] = this.name;


        gameInfo[3] = "CardsPlayed" + "|" + roundCounter + "|" + cardCounter + "|" + arrCardsPlayed[0] + "|" + arrCardsPlayed[1] + "|" + arrCardsPlayed[2] + "|" + arrCardsPlayed[3];

        SendGameMessage msgGame = new SendGameMessage(gameInfo);

        msgGame.process(client);


    }
}
