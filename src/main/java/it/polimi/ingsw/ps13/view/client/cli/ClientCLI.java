package it.polimi.ingsw.ps13.view.client.cli;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.message.response.UpdateResponseMsg;
import it.polimi.ingsw.ps13.message.response.multicast.ChatMulticastMsg;
import it.polimi.ingsw.ps13.message.response.unicast.ConnectionUnicastMsg;
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
	private String playerName;
	
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
			
		System.out.println("Whatcha wanna see? <region, color, city, king, kingbalcony, councillors, nobility, market, me, menu, actions, quit>");
		
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
			case "region":
				System.out.print("Which region? <");
				for (String region : game.getBoard().getRegions().keySet()) {
					System.out.print(region + " ");
				}
				System.out.println(">");
				
				String inputRegion;
				do {
					inputRegion = scanner.nextLine();
				} while(!game.getBoard().getRegions().containsKey(inputRegion));
				
				System.out.println(game.getBoard().getRegion(inputRegion).toString());
				break;
			case "color":
				System.out.print("Which citycolor? <");
				for (String cityColor : game.getBoard().getCityColors().keySet()) {
					System.out.print(cityColor + " ");
				}
				System.out.println(">");
				
				String inputColor;
				do {
					inputColor = scanner.nextLine();
				} while(!game.getBoard().getCityColors().containsKey(inputColor));
				
				System.out.println(game.getBoard().getCityColor(inputColor).toString());
				break;
			case "city":
				System.out.print("Which city? <");
				for (String city : game.getBoard().getCities().keySet()) {
					System.out.print(city + " ");
				}
				System.out.println(">");
				
				String inputCity;
				do {
					inputCity = scanner.nextLine();
				} while(!game.getBoard().getCities().containsKey(inputCity));
				
				System.out.println(game.getBoard().getCity(inputCity).toString());
				break;
			case "king":
				System.out.println(game.getBoard().getKing().toString());
				break;
			case "kingbalcony":
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
			case "market":
				System.out.println(game.getMarket().toString());
				break;
			case "me":
				Player p = game.getPlayer(playerName);
				System.out.println(p.toString());
				break;
			case "menu":
				showModel();
				break;
			case "actions":
				System.out.println("MAIN ACTIONS");
				System.out.println("elect <region> <color>");
				System.out.println("corrupt <region> <visible tile number> <card color> <card color> ...");
				System.out.println("build <tile number> <city>");
				System.out.println("king COMING SOON!!");
				System.out.println();
				
				System.out.println("QUICK ACTIONS");
				System.out.println("changetiles <region>");
				System.out.println("engageassistant");
				System.out.println("gainmainaction");
				System.out.println("qelect <region> <color>");
				System.out.println();
				
				System.out.println("SELL ACTION");
				System.out.println("sell");
				System.out.println();
				
				System.out.println("BUY ACTION");
				System.out.println("buy <entry number>");
				System.out.println();
				break;
			default:
				System.out.println("Command not recognized.");
				break;
		}
		
	}
	
	/**
	 * @TODO: create and implement CLI message visitor
	 */
	public void handleMessage(ResponseMsg msg) {
		
		if (msg instanceof UpdateResponseMsg) {
			UpdateResponseMsg updateMsg = (UpdateResponseMsg)msg;
			this.game = updateMsg.getGame();
			System.out.println(updateMsg.getMessage());
		}
		else if (msg instanceof ChatMulticastMsg) {
			ChatMulticastMsg chatMsg = (ChatMulticastMsg) msg;
			System.out.println("[" + chatMsg.getPlayerName() + "] " + chatMsg.getMessage());
		}
		else if (msg instanceof ConnectionUnicastMsg) {
			ConnectionUnicastMsg connMsg = (ConnectionUnicastMsg) msg;
			this.playerName = connMsg.getPlayerName();
			System.out.println(connMsg.getMessage());
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
				
				RequestMsg msg = CmdInterpreter.parseCommand(cmd);
				if (msg != null) {
					connection.sendMessage(msg);
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
