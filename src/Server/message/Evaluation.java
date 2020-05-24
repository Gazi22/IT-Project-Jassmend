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
	private int pointsTeam2;
	private int points1;
	private int points2;
	private int count1;
	private int count2;



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
				if(gamelobby.getSticheTeam1(x)!=null) {
					points1 = serverController.getPoints(gamelobby, gamelobby.getSticheTeam1(x));
					gamelobby.setPointsTeam1(gamelobby.getPointsTeam1()+points1);

					count1++;

				}
		}

			for (int y = 0; y < gamelobby.getSizeSticheTeam2(); y++) {
				if (gamelobby.getSticheTeam2(y) != null) {
					points2 = serverController.getPoints(gamelobby, gamelobby.getSticheTeam2(y));
					gamelobby.setPointsTeam2(gamelobby.getPointsTeam2()+points2);

					count2++;
				}
			}



			if(count1==36){
				gamelobby.setPointsTeam1(gamelobby.getPointsTeam1()+100);
			}

			if(count2==36){
				gamelobby.setPointsTeam2(gamelobby.getPointsTeam2()+100);
			}


			//5 points for last Stich
			if(gamelobby.getSticheTeam1(35)!= null){
				gamelobby.setPointsTeam1(gamelobby.getPointsTeam1()+5);
			}
			else gamelobby.setPointsTeam1(gamelobby.getPointsTeam2()+5);



			pointsTeam1=gamelobby.getPointsTeam1();
			pointsTeam2=gamelobby.getPointsTeam2();
			gamelobby.clearSticheTeams();
	}



		String[] gameInfo = new String[4];
		gameInfo[1] = this.token;
		gameInfo[2] = this.name;


		gameInfo[3] = "EvaluateRound" + "|"+"PointsTeam1"+"|"+pointsTeam1+"|"+"PointsTeam2"+"|"+pointsTeam2;

		SendGameMessage msgGame = new SendGameMessage(gameInfo);

		msgGame.process(client);
	}
}
