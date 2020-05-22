package Server.message;

import Server.Client;
import Server.Gamelobby;
import Server.ServerController;
import jassmendModel.Card;

/**
 * Add a user as a member of a gamelobby.
 */
public class CardPlayed extends Message {
    private String token;
    private String name;
    private String username;
    private String cardPlayed;
    private String firstPlayer;


    public CardPlayed(String[] data) {
        super(data);
        this.token = data[1];
        this.name = data[2];
        this.username = data[3];
        this.cardPlayed = data[4];
        this.firstPlayer = data [5];
    }


    /**
     * The owner of a gamelobby can add anyone. A user can add themselves to any
     * public gamelobby. Otherwise, no one can add anyone.
     */
    @Override
    public void process(Client client) {
        Gamelobby gamelobby = Gamelobby.exists(name);

        boolean result = false;
        String[] arrCardsPlayed = new String[4];
        String cardCounter = "";
        String roundCounter = "";
        int firstPlayerID=Integer.parseInt(firstPlayer);

        ServerController serverController = new ServerController();
        if (client.getToken().equals(token)&&cardPlayed!="null") {
            gamelobby.increaseCardCounter();
            if (gamelobby.getCardCounter() > 4) {
                gamelobby.resetCardCounter();
                gamelobby.increaseCardCounter();
            }

            if (gamelobby.getCardCounter() == 1) {
                gamelobby.setFirstCardInTurn(cardPlayed);
                result = true;
            }


            if (gamelobby.getCardCounter() > 1) {
                int x = 0;
                switch(firstPlayerID){
                    case 0:

                    case 1:
                        if(gamelobby.getCardCounter()==2){
                        x=9;}
                    else if(gamelobby.getCardCounter()==3){
                        x=18;}
                    else if(gamelobby.getCardCounter()==4){
                        x=27;}
                        break;

                    case 2:if(gamelobby.getCardCounter()==2){
                                 x=18;}
                            else if(gamelobby.getCardCounter()==3){
                                 x=27;}
                            else if(gamelobby.getCardCounter()==4){
                                 x=0;}
                            break;


                    case 3:if(gamelobby.getCardCounter()==2){
                                x=27;}
                            else if(gamelobby.getCardCounter()==3){
                                x=0;}
                            else if(gamelobby.getCardCounter()==4){
                                x=9;}
                            break;


                    case 4:if(gamelobby.getCardCounter()==2){
                             x=0;}
                            else if(gamelobby.getCardCounter()==3){
                             x=9;}
                            else if(gamelobby.getCardCounter()==4){
                             x=18;}
                            break;
                }




                if (!cardPlayed.substring(0, 4).equals(gamelobby.getFirstCardInTurn().substring(0, 4)) && !cardPlayed.substring(0, 4).equals(gamelobby.getTrumpf().substring(0, 4))) {
                    for (int y = x; y < x + 9; y++) {
                        if (gamelobby.getCardsDealt(y) != null) {
                            if (gamelobby.getCardsDealt(y).substring(0, 4).equals(gamelobby.getFirstCardInTurn().substring(0, 4))) {
                                if (!gamelobby.getCardsDealt(y).equals(gamelobby.getTrumpf() + "Bube")) {
                                    result = false;
                                    break;
                                }
                            } else result = true;
                        }
                    }
                }else result = true;

                if (cardPlayed.substring(0, 4).equals(gamelobby.getTrumpf().substring(0, 4))&&!gamelobby.getFirstCardInTurn().substring(0,4).equals(gamelobby.getTrumpf().substring(0,4))) {
                    for (int z = 0; z < gamelobby.getCardCounter() - 1; z++) {
                        if (gamelobby.getCardsInRound(z).substring(0, 4).equals(gamelobby.getTrumpf().substring(0, 4))) {

                            Card card1=serverController.stringToCard(gamelobby.getCardsInRound(z));
                            Card card2=serverController.stringToCard(cardPlayed);

                            card1.completeSuitValue();
                            card2.completeSuitValue();

                            if (card1.compareTo(card2) == 1) {
                                result = false;
                                break;
                            } else result = true;
                        } else result = true;
                    }


                }
                else if(result ==false){
                    result=false;
                }
                else result = true;
            }


            if (result == true) {

                for(int x = 0;x<36;x++){
                    if(cardPlayed.equals(gamelobby.getCardsDealt(x))){
                        gamelobby.setCardsDealtNull(x);
                    }
                }


                roundCounter = Integer.toString(gamelobby.getRoundCounter());


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
        }

        String[] gameInfo = new String[4];
        gameInfo[1] = this.token;
        gameInfo[2] = this.name;


        gameInfo[3] = "CardsPlayed" + "|" + roundCounter + "|" + cardCounter + "|" + arrCardsPlayed[0] + "|" + arrCardsPlayed[1] + "|" + arrCardsPlayed[2] + "|" + arrCardsPlayed[3]+"|"+firstPlayerID;

        if (result==true) {
            SendGameMessage msgGame = new SendGameMessage(gameInfo);
            msgGame.process(client);
            System.out.println("Server: Seen the card, checked and sent back to client!");
        }
        else {
            gamelobby.decreaseCardCounter();
            client.send(new Result(result));
            System.out.println("Illegal Move");
        }
    }



}
