package Server.message;

import Server.Client;
import Server.Gamelobby;
import Server.ServerController;

/**
 * Add a user as a member of a gamelobby.
 */
public class Evaluation extends Message {
	private String token;
	private String name;
	private String username;
	private int pointsTeam1;
	private int points;
	private int pointsTeam2;



	public Evaluation(String[] data) {
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
			ServerController serverController = new ServerController();

			for (int x = 0; x < gamelobby.getSizeSticheTeam1(); x++){
				points = serverController.getPoints(gamelobby, gamelobby.getSticheTeam1(x));
				pointsTeam1=pointsTeam1+points;
		}


			for (int x = 0; x < gamelobby.getSizeSticheTeam2(); x++) {
				points = serverController.getPoints(gamelobby, gamelobby.getSticheTeam2(x));
				pointsTeam2=pointsTeam2+points;
		}
	}



		String[] gameInfo = new String[4];
		gameInfo[1] = this.token;
		gameInfo[2] = this.name;


		gameInfo[3] = "EvaluateRound" + "|"+"PointsTeam1"+"|"+pointsTeam1+"|"+"PointsTeam2"+"|"+pointsTeam2;

		SendGameMessage msgGame = new SendGameMessage(gameInfo);

		msgGame.process(client);
	}
}
