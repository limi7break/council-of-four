package it.polimi.ingsw.ps13.view.client.cli;

import java.util.Scanner;

import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.response.ChatResponseMsg;
import it.polimi.ingsw.ps13.message.response.PingResponseMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.message.response.UpdateResponseMsg;
import it.polimi.ingsw.ps13.message.response.unicast.ConnectionUnicastMsg;
import it.polimi.ingsw.ps13.message.response.unicast.RenameUnicastMsg;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.board.KingRewardTile;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.ClientView;

public class ClientCLI implements ClientView {
	
	private static final Scanner scanner = new Scanner(System.in);
	
	private ClientConnection connection;
	
	private Game game;
	private String playerName;
	
	@Override
	public void run() {
		
		startHandlers();
		
	}
	
	/**
	 *
	 * 
	 */
	@Override
	public void showModel() {
			
		System.out.println("Whatcha wanna see? <region, color, city, king, kingbalcony, kingrt, councillors, nobility, market, me, others, menu, actions, quit>");
		
	}
	
	/**
	 *
	 * 
	 * @param command
	 */
	@SuppressWarnings("all")
	public void localCommand(String command) {
		
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
			case "kingrt":
				int i=0;
				for (KingRewardTile krt : game.getBoard().getKingRewardTiles()) {
					i++;
					System.out.println(i + ". " + krt.getBonus().toString() + ", available = " + krt.isAvailable());
				}
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
			case "others":
				for (Player player : game.getPlayers().values()) {
					if (!player.getName().equals(playerName)) {
						StringBuilder sb = new StringBuilder();
						
						sb.append("\n[").append(player.getName()).append("]\n")
						  .append("VICTORY POINTS: ").append(player.getVictoryPoints()).append(" ")
						  .append("COINS: ").append(player.getCoins()).append(" ")
						  .append("ASSISTANTS: ").append(player.getAssistants()).append(" ")
						  .append("EMPORIUMS: ").append(player.getNumberOfEmporiums()).append(" ")
						  .append("NOBILITY POSITION: ").append(player.getNobilityPosition()).append(" ")
						  .append("POLITICS CARDS: ").append(player.getPoliticsCards().size()).append(" ")
						  .append("PERMIT TILES: ").append(player.getPermitTiles().size()).append("\n\n")
						  .append("CITIES: ").append(player.getCityNames().toString()).append("\n");
						
						System.out.println(sb.toString());
					}
				}
				break;
			case "menu":
				showModel();
				break;
			case "actions":
				System.out.println("MAIN ACTIONS");
				System.out.println("elect <region> <color>");
				System.out.println("corrupt <region> <visible tile number> <card color> <card color> ...");
				System.out.println("build <tile number> <city>");
				System.out.println("king <city> <card color> <card color> ...");
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
				
				System.out.println("BONUS ACTIONS");
				System.out.println("get tile <region> <visible tile number>");
				System.out.println("get rt <city>");
				System.out.println("get tb <tile number>");
				System.out.println();
				break;
			default:
				System.out.println("Command not recognized.");
				break;
		}
		
	}
	
	/**
	 *
	 */
	public void handleMessage(ResponseMsg msg) {
		
		if (msg instanceof UpdateResponseMsg) {
			UpdateResponseMsg updateMsg = (UpdateResponseMsg)msg;
			this.game = updateMsg.getGame();
			System.out.println(updateMsg.getMessage());
		}
		else if (msg instanceof ChatResponseMsg) {
			ChatResponseMsg chatMsg = (ChatResponseMsg) msg;
			System.out.println("[" + chatMsg.getPlayerName() + "] " + chatMsg.getMessage());
		}
		else if (msg instanceof ConnectionUnicastMsg) {
			ConnectionUnicastMsg connMsg = (ConnectionUnicastMsg) msg;
			this.playerName = connMsg.getPlayerName();
			System.out.println(connMsg.getMessage());
		}
		else if (msg instanceof RenameUnicastMsg) {
			RenameUnicastMsg renameMsg = (RenameUnicastMsg) msg;
			this.playerName = renameMsg.getNewName();
			System.out.println(renameMsg.getMessage());
		}
		else if (msg instanceof PingResponseMsg) {
			// Ping from the server is ignored
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
				else if (game != null) {
					localCommand(cmd);
				}
				else {
					System.out.println("Command not available until the game is started!");
				}
				
			}
		}).start();
				
		// This is the input handler
		new Thread(() -> {
			while(connection.isActive()) {
				
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
