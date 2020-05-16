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
        boolean result = false;
        String[] arrCardsPlayed = new String[4];
        String cardCounter = "";
        String roundCounter = "";
        ServerController serverController = new ServerController();
        if (client.getToken().equals(token)) {
            Gamelobby gamelobby = Gamelobby.exists(name);
            gamelobby.increaseCardCounter();


            if (gamelobby.getCardCounter() == 1) {
                gamelobby.setFirstCardInTurn(cardPlayed);
            }


            if (gamelobby.getCardCounter() > 1) {
                int x = 0;
                switch (gamelobby.getCardCounter()) {
                    case 2:
                        x = 9;
                        break;
                    case 3:
                        x = 18;
                        break;
                    case 4:
                        x = 27;
                        break;
                }
                if (!cardPlayed.substring(0, 4).equals(gamelobby.getFirstCardInTurn().substring(0, 4))) {
                    for (int y = x; y < x + 9; x++) {

                        if (gamelobby.getCardsDealt(y).substring(0, 4).equals(gamelobby.getFirstCardInTurn().substring(0, 4)) || gamelobby.getCardsDealt(y).substring(0, 4).equals(gamelobby.getTrumpf().substring(0, 4))) {
                            result = false;
                            break;
                        }

                    }
                } else result = true;
            }
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
            gamelobby.addCardsWithNames(cardPlayed);
            gamelobby.addCardsWithNames(username);

            if (gamelobby.getCardCounter() == 4) {
                serverController.handleStiche(gamelobby);


            }
        }


        String[] gameInfo = new String[4];
        gameInfo[1] = this.token;
        gameInfo[2] = this.name;


        gameInfo[3] = "CardsPlayed" + "|" + roundCounter + "|" + cardCounter + "|" + arrCardsPlayed[0] + "|" + arrCardsPlayed[1] + "|" + arrCardsPlayed[2] + "|" + arrCardsPlayed[3];

        if (result=true) {
            SendGameMessage msgGame = new SendGameMessage(gameInfo);

            msgGame.process(client);
        }
        else client.send(new Result(result));

    }



}
