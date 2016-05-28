package it.polimi.ingsw.ps13.view.client.cli;

import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.message.request.ChatRequestMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.message.response.broadcast.ChatBroadcastMsg;
import it.polimi.ingsw.ps13.message.response.broadcast.UpdateBroadcastMsg;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.ClientView;

public class ClientCLI implements ClientView {
	
	private static final Logger LOG = Logger.getLogger(ClientCLI.class.getSimpleName());
	private static final Scanner scanner = new Scanner(System.in);
	
	private ClientConnection connection;
	
	private Game game;
	
	@Override
	public void run() {
		
		// implement CLI
		LOG.log(Level.WARNING, "CLI has not been implemented yet.");
		
		startHandlers();
		
	}
	
	/**
	 * Only here for testing purposes, must be changed!.
	 * 
	 */
	@Override
	public void showModel() {
			
		System.out.println("Whatcha wanna see? <regions, citycolors, cities, king, politicsdeck, balcony, councillors, nobility, players, quit>");
		
	}
	
	/**
	 * Only here for testing purposes, must be removed!
	 * 
	 * @param command
	 */
	// Warning suppressed, test method
	@SuppressWarnings("all")
	public void test(String command) {
		
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
	
	/**
	 * @TODO: create and implement CLI message visitor
	 */
	public void handleMessage(ResponseMsg msg) {
		
		if (msg instanceof UpdateBroadcastMsg) {
			UpdateBroadcastMsg updateMsg = (UpdateBroadcastMsg)msg;
			this.game = updateMsg.getGame();
			showModel();
			System.out.println(updateMsg.getMessage());
		}
		else if (msg instanceof ChatBroadcastMsg) {
			ChatBroadcastMsg chatMsg = (ChatBroadcastMsg) msg;
			System.out.println("[" + chatMsg.getPlayerName() + "] " + chatMsg.getMessage());
		}
		else {
			System.out.println(msg.getMessage());
		}
		
	}
	
	public void startHandlers() {
		
		// This is the output handler
		new Thread(() -> {
			while(true) {
				
				String cmd = scanner.nextLine();
				
				if (cmd.matches("^chat\\s(.+)$")) {
		            String param = cmd.replaceFirst("chat ", "");
		            connection.sendMessage(new ChatRequestMsg(param));
				} 
				
				// Some commands are elaborated locally
				else {
					test(cmd);
				}
				
			}
		}).start();
				
		// This is the input handler
		new Thread(() -> {
			while(true) {
				
				ResponseMsg msg = connection.receiveMessage();
				handleMessage(msg);
				
			}
		}).start();
		
	}

	@Override
	public void setConnection(ClientConnection connection) {
		 
		this.connection = connection;
		
	}
	
}
