package it.polimi.ingsw.ps13.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * Very basic idea of GameClient.
 * Communicates with the server via strings and only gives the possibility to see the model.
 *
 * Warnings are suppressed for now as this is only a prototype made for testing.
 */
@SuppressWarnings("all")
public class GameClient {
	
	private static final Logger LOG = Logger.getLogger(GameClient.class.getName());

	public static final int PORT = 1337;
	private Game game;
	private Socket sock;
	private String username;
	private Scanner scanner;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public static void main(String[] args) throws Exception {
		
		GameClient client = new GameClient();
		client.start();
		
	}
	
	public void start() throws Exception {
		
		scanner = new Scanner(System.in);
		
		System.out.println("Council of Four Client.");
		System.out.print("Server IP address: ");
		String address = scanner.nextLine();
		
		String result = null;
		do {
			System.out.print("username: ");
			username = scanner.nextLine();
			
			connect(address, username);
			
			try {
				result = (String) ois.readObject();
			} catch(IOException e) {
				LOG.log(Level.SEVERE, "I/O exception while getting response from server.", e);
			}
			
			System.out.println(result);
		} while(!result.equals("Connection accepted. :)"));
		
		try {
			// Receive welcome message
			System.out.println((String)ois.readObject());

			// Listen for game broadcast from the server
			listen();
		} catch(IOException e) {
			LOG.log(Level.SEVERE, "I/O exception while doing stuff.", e);
		}
		
		scanner.close();
		
	}
	
	public void connect(String address, String username) {
		
		try {
			sock = new Socket(address, PORT);
			oos = new ObjectOutputStream(sock.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(sock.getInputStream());
			
			oos.writeObject(username);
		} catch(IOException e) {
			LOG.log(Level.SEVERE, "There was a problem connecting to the server.", e);
		}
		
	}
	
	public void listen() throws Exception {
		
		System.out.print("Listening for game broadcast from server...");
		game = (Game) ois.readObject();
		System.out.println(" OK.");
		
		// Only here for testing purposes
		System.out.println("Initializing testing phase.");
		test();
		
	}
	
	/**
	 * Only here for testing purposes
	 * 
	 */
	public void test() {
		
		String command = null;
		while(command != "quit") {
			System.out.println("Whatcha wanna see? <regions, citycolors, cities, king, politicsdeck, balcony, councillors, nobility, players, quit>");
			command = scanner.nextLine();
			
			switch(command.toLowerCase()) {
				case "regions":
					System.out.println("Which region?");
					for (String region : game.getBoard().getRegions().keySet()) {
						System.out.print(region + " ");
					}
					String inputRegion = scanner.nextLine();
					System.out.println(game.getBoard().getRegion(inputRegion.toLowerCase()).toString());
					break;
				case "citycolors":
					System.out.println("Which citycolor?");
					for (String cityColor : game.getBoard().getCityColors().keySet()) {
						System.out.print(cityColor + " ");
					}
					String inputColor = scanner.nextLine();
					System.out.println(game.getBoard().getCityColor(inputColor.toLowerCase()).toString());
					break;
				case "cities":
					System.out.println("quale city?");
					for (String city : game.getBoard().getCities().keySet()) {
						System.out.print(city + " ");
					}
					String inputCity = scanner.nextLine();
					System.out.println(game.getBoard().getCity(inputCity.toLowerCase()).toString());
					break;
				case "king":
					System.out.println(game.getBoard().getKing().toString());
					break;
				case "politicsdeck":
					System.out.println(game.getBoard().getPoliticsCardDeck().toString());
					break;
				case "balcony":
					System.out.println(game.getBoard().getKingBalcony().toString());
					break;
				case "councillors":
					for (Councillor c : game.getBoard().getCouncillors()) {
						System.out.println(c.toString() + " ");
					}
					break;
				case "nobility":
					System.out.println(game.getBoard().getNobilityTrack().toString());
					break;
				case "players":
					System.out.println("Which player? (number)");
					for (Map.Entry<Integer, Player> entry : game.getPlayers().entrySet()) {
						System.out.println(entry.getKey() + " " + entry.getValue().getName());
					}
					Integer choice = scanner.nextInt();
					System.out.println(game.getPlayers().get(choice).toString());
					break;
				default:
					break;
			}
		}
		
		System.out.println("Bye!");
		scanner.close();
		
	}

}