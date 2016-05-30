package it.polimi.ingsw.ps13.view.client.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.message.request.ChatRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.AcquirePermitTileRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.BuildEmporiumRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.ChangePermitTilesRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.ElectCouncillorRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.EngageAssistantRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.GainMainActionRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.OfferSelectionRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.PassTurnRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.QuickElectCouncillorRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.TradeProposalRequestMsg;
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
				
				// Chat command
				if (cmd.matches("^chat\\s(.+)$")) {
		            String param = cmd.replaceFirst("chat ", "");
		            connection.sendMessage(new ChatRequestMsg(param));
				}
				
				// Main actions
				else if (cmd.matches("^elect\\s([a-z]+)\\s([a-z]+)$")) {
		            String param = cmd.replaceFirst("elect ", "");
		            String[] params = param.split(" ");
		            
		            connection.sendMessage(new ElectCouncillorRequestMsg(params[0], params[1]));
				}
				else if (cmd.matches("^corrupt\\s([a-z]+)\\s([0-9]{1})(\\s([a-z]+))+$")) {
		            String param = cmd.replaceFirst("corrupt ", "");
		            String[] params = param.split(" ");
		            
		            String region = params[0];
		            int tile = Integer.parseInt(params[1]);
		            List<String> cards = new ArrayList<>();
		            for (int i=2; i<params.length; i++) {
		            	cards.add(params[i]);
		            }
		            
		            connection.sendMessage(new AcquirePermitTileRequestMsg(region, tile, cards));
				}
				else if (cmd.matches("^build\\s([0-9]{1})\\s([a-z]+)$")) {
		            String param = cmd.replaceFirst("build ", "");
		            String[] params = param.split(" ");
		            
		            int tile = Integer.parseInt(params[0]);
		            String city = params[1];
		            
		            connection.sendMessage(new BuildEmporiumRequestMsg(tile, city));
				}
				
				// Quick actions
				else if (cmd.matches("^changetiles\\s([a-z]+)$")) {
		            String param = cmd.replaceFirst("changetiles ", "");
		            
		            connection.sendMessage(new ChangePermitTilesRequestMsg(param));
				}
				else if (cmd.matches("^engageassistant$")) {
		            connection.sendMessage(new EngageAssistantRequestMsg());
				}
				else if (cmd.matches("^gainmainaction$")) {
		            connection.sendMessage(new GainMainActionRequestMsg());
				}
				else if (cmd.matches("^qelect\\s([a-z]+)\\s([a-z]+)$")) {
		            String param = cmd.replaceFirst("qelect ", "");
		            String[] params = param.split(" ");
		            
		            connection.sendMessage(new QuickElectCouncillorRequestMsg(params[0], params[1]));
				}
				
				// Sell action
				else if (cmd.matches("^sell$")) {
					String input = null;
					do {
						System.out.print("assistants? ");
						input = scanner.nextLine();
					} while(!input.matches("[0-9]+"));
					int assistants = Integer.parseInt(input);
					
					do {
						System.out.print("cards? (enter \"no\" for none) ");
						input = scanner.nextLine();
					} while(!input.matches("^[a-zA-Z]+(\\s{1}[a-zA-Z]+)*$") && !input.matches("no"));
					List<String> cards = new ArrayList<>();
					if (!"no".equals(input)) {
						String[] cardParams = input.split(" ");
						for (int i=0; i<cardParams.length; i++) {
							cards.add(cardParams[i]);
						}
					}
					
					do {
						System.out.print("tiles? (enter \"no\" for none) ");
						input = scanner.nextLine();
					} while(!input.matches("^[0-9]+(\\s{1}[0-9]+)*$") && !input.matches("no"));
					List<Integer> tiles = new ArrayList<>();
					if (!"no".equals(input)) {
						String[] tileParams = input.split(" ");
						for (int i=0; i<tileParams.length; i++) {
							tiles.add(Integer.parseInt(tileParams[i]));
						}
					}
					
					do {
						System.out.print("price? ");
						input = scanner.nextLine();
					} while(!input.matches("[0-9]+"));
					int price = Integer.parseInt(input);
					
					connection.sendMessage(new TradeProposalRequestMsg(assistants, tiles, cards, price));
				}
				
				// Buy action
				else if (cmd.matches("^buy\\s([0-9]{1})$")) {
		            String param = cmd.replaceFirst("buy ", "");
		            int entry = Integer.parseInt(param);
		            
		            connection.sendMessage(new OfferSelectionRequestMsg(entry));
				}
				
				// Pass action
				else if (cmd.matches("^pass$")) {
		            connection.sendMessage(new PassTurnRequestMsg());
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
