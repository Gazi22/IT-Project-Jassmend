package Server;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

//Chat Server from Bradley Richards


public class Server {
	private static final Logger logger = Logger.getLogger("");
	private static int port = -1;
	private static String homeDirectory = "";
	;
	
	public static void main(String[] args) {

		// Setup logging, including a file handler
		setupLogging();
		
		// Reload any existing gamelobbys and accounts
		logger.info("Read any existing data");
		Gamelobby.readgamelobbys();
		Account.readAccounts();
		
		
		
		try {
			// Read command-line parameters, if they exist - otherwise read from the console
			if (args.length > 0) {
				logger.info("Process command-line parameters");
				port = Integer.parseInt(args[0]);
				if (args.length > 1) homeDirectory = args[1];
			} else {
				readOptions();
			}
			
			// Start the listener
			ListenerThread lt = new ListenerThread(port);
			lt.start();
			
			// Start the clean-up thread
			CleanupThread ct = new CleanupThread();
			ct.start();
		} catch (IOException e) {
			
			}
					}
	
	
	private static void setupLogging() {
		logger.setLevel(Level.FINE);
		logger.getHandlers()[0].setLevel(Level.WARNING); // Standard (console) handler
		try {
			FileHandler fh = new FileHandler("%h/gamelobbyServer_%u_%g.log", 10000000, 2);
			fh.setFormatter(new SimpleFormatter());
			fh.setLevel(Level.FINE);
			logger.addHandler(fh);
		} catch (Exception e) {
			logger.severe("Unable to create file handler for logging: " + e.toString());
			throw new RuntimeException("Unable to initialize log files: " + e.toString());
		}
	}

	private static void readOptions() {
		logger.info("Read start-up info from the console");
		port = 8080;
	}
			
			
			
		
	
	
	public static String getHome() { return homeDirectory; }
}
