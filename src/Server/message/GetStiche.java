package Server.message;

import Server.Client;
import Server.Gamelobby;

/**
 * Add a user as a member of a gamelobby.
 */
public class GetStiche extends Message {
	private String token;
	private String name;
	private String username;
	//provisorisch 100
	String stichWinner="";
	String[] sticheTeam1 = new String[4];
	String[] sticheTeam2 = new String[4];
	int stichCounter;

	public GetStiche(String[] data) {
		super(data);
		this.token = data[1];
		this.name = data[2];
		this.username = data[3];
	}



	/**
	 * The owner of a gamelobby can add anyone. A user can add themselves to any
	 * public gamelobby. Otherwise, no one can add anyone.
	 */
	@Override
	public void process(Client client) {
		if (client.getToken().equals(token)) {
			Gamelobby gamelobby = Gamelobby.exists(name);
			stichWinner=gamelobby.getStichWinner();
			int y = 0;
			int z = 0;
			int roundCount = (gamelobby.getRoundCounter());


			for (int x = gamelobby.getTurnCounter()-4; x < gamelobby.getTurnCounter(); x++) {

					if (gamelobby.getSticheTeam1(x) != null && gamelobby.getSizeSticheTeam1() > x) {
						sticheTeam1[y] =gamelobby.getSticheTeam1(x).toString();
						y++;
					}

					//Stiche Team 2
					if (gamelobby.getSticheTeam2(x) != null && gamelobby.getSizeSticheTeam2() > x) {
						sticheTeam2[z] = gamelobby.getSticheTeam2(x).toString();
						z++;
					}
				}




			gamelobby.increaseStichCounter();
			stichCounter = gamelobby.getStichCounter();

			if (gamelobby.getTurnCounter()%36==0){
				gamelobby.clearCardsDealt();
				gamelobby.increaseRoundCounter();
				gamelobby.getDeck().shuffle();

				gamelobby.resetStichCounter();
			}

		}





		String[] gameInfo = new String[4];
		gameInfo[1] = this.token;
		gameInfo[2] = this.name;


		gameInfo[3] = "Stich" + "|"+stichCounter+"|"+stichWinner +"|"+"SticheTeam1"+"|"+sticheTeam1[0]+"|"+sticheTeam1[1]+"|"+sticheTeam1[2]+"|"+sticheTeam1[3]+"|"+"SticheTeam2"+"|"+sticheTeam2[0]+"|"+sticheTeam2[1]+"|"+sticheTeam2[2]+"|"+sticheTeam2[3];

		SendGameMessage msgGame = new SendGameMessage(gameInfo);

		msgGame.process(client);
	}
}
