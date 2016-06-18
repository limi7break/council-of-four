package it.polimi.ingsw.ps13.view.client.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.polimi.ingsw.ps13.message.request.ChatRequestMsg;
import it.polimi.ingsw.ps13.message.request.RenameRequestMsg;
import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.request.action.AcquirePermitTileRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.BuildEmporiumRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.ChangePermitTilesRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.ElectCouncillorRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.EngageAssistantRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.GainMainActionRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.KingActionRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.OfferSelectionRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.PassTurnRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.QuickElectCouncillorRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.RegainPermitTileBonusRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.RegainRewardTokenRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.TradeProposalRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.VisiblePermitTileRequestMsg;

/**
 * This class converts command line input into request messages.
 *
 */
public class CmdInterpreter {

	private CmdInterpreter() { 
		
		// Private constructor for non-instantiability
		
	}
	
	/**
	 * Parses the command entered by the user trying to match it with regular expressions
	 * Returns a request message which corresponds to the entered command, ready to be sent to the server.
	 * 
	 * @param cmd the input command entered by the user from the command line interface
	 * @return the created request msg ready to be sent, or null if the command does not match any of the regular expressions
	 */
	public static RequestMsg parseCommand(String cmd) {
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		RequestMsg msg = null;
		
		// Chat command
		if (cmd.matches("^chat\\s(.+)$")) {
            String param = cmd.replaceFirst("chat ", "");
            
            msg = new ChatRequestMsg(param);
		}
		
		// Rename command
		else if (cmd.matches("^rename\\s.*$")) {
			String param = cmd.replaceFirst("rename ", "");
			
			msg = new RenameRequestMsg(param);
		}
		
		// Main actions
		else if (cmd.matches("^elect\\s([A-Za-z]+)\\s([A-Za-z]+)$")) {
            String param = cmd.replaceFirst("elect ", "");
            String[] params = param.split(" ");
            
            msg = new ElectCouncillorRequestMsg(params[0], params[1]);
		}
		else if (cmd.matches("^corrupt\\s([A-Za-z]+)\\s([0-9]+)(\\s([A-Za-z]+))+$")) {
            String param = cmd.replaceFirst("corrupt ", "");
            String[] params = param.split(" ");
            
            String region = params[0];
            int tile = Integer.parseInt(params[1]);
            List<String> cards = new ArrayList<>();
            for (int i=2; i<params.length; i++) {
            	cards.add(params[i]);
            }
            
            msg = new AcquirePermitTileRequestMsg(region, tile, cards);
		}
		else if (cmd.matches("^build\\s([0-9]+)\\s([A-Za-z]+)$")) {
            String param = cmd.replaceFirst("build ", "");
            String[] params = param.split(" ");
            
            int tile = Integer.parseInt(params[0]);
            String city = params[1];
            
            msg = new BuildEmporiumRequestMsg(tile, city);
		}
		else if (cmd.matches("^king\\s([A-Za-z]+)(\\s([A-Za-z]+))+$")) {
            String param = cmd.replaceFirst("king ", "");
            String[] params = param.split(" ");
            
            String city = params[0];
            List<String> cards = new ArrayList<>();
            for (int i=1; i<params.length; i++) {
            	cards.add(params[i]);
            }
            
            msg = new KingActionRequestMsg(city, cards);
		}
		
		// Quick actions
		else if (cmd.matches("^changetiles\\s([A-Za-z]+)$")) {
            String param = cmd.replaceFirst("changetiles ", "");
            
            msg = new ChangePermitTilesRequestMsg(param);
		}
		else if (cmd.matches("^engageassistant$")) {
            msg = new EngageAssistantRequestMsg();
		}
		else if (cmd.matches("^gainmainaction$")) {
            msg = new GainMainActionRequestMsg();
		}
		else if (cmd.matches("^qelect\\s([A-Za-z]+)\\s([A-Za-z]+)$")) {
            String param = cmd.replaceFirst("qelect ", "");
            String[] params = param.split(" ");
            
            msg = new QuickElectCouncillorRequestMsg(params[0], params[1]);
		}
		
		// Sell action
		else if (cmd.matches("^sell$")) {
			String input;
			do {
				System.out.print("assistants? ");
				input = scanner.nextLine();
			} while(!input.matches("[0-9]+"));
			int assistants = Integer.parseInt(input);
			
			do {
				System.out.print("cards? (enter \"no\" for none) ");
				input = scanner.nextLine();
			} while(!input.matches("^[A-Za-z]+(\\s{1}[A-Za-z]+)*$") && !input.matches("no"));
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
			
			msg = new TradeProposalRequestMsg(assistants, tiles, cards, price);
		}
		
		// Buy action
		else if (cmd.matches("^buy\\s([0-9]+(\\s{1}[0-9]+)*)$")) {
            String param = cmd.replaceFirst("buy ", "");
            List<Integer> entries = new ArrayList<>();
            String[] entryParams = param.split(" ");
            for (int i=0; i<entryParams.length; i++) {
            	entries.add(Integer.parseInt(entryParams[i]));
            }
            
            msg = new OfferSelectionRequestMsg(entries);
		}
		
		// Get visible tile bonus action
		else if (cmd.matches("^get\\stile\\s([A-Za-z]+)\\s([0-9]+)$")) {
			String param = cmd.replaceFirst("get tile ", "");
			String[] params = param.split(" ");
            
            String region = params[0];
            int tile = Integer.parseInt(params[1]);
			
			msg = new VisiblePermitTileRequestMsg(region, tile);
		}
		
		// Regain reward token action
		else if (cmd.matches("^get\\srt\\s([A-Za-z]+)$")) {
			String param = cmd.replaceFirst("get rt ", "");
			
			msg = new RegainRewardTokenRequestMsg(param);
		}
		
		// Regain tile bonus action
		else if (cmd.matches("^get\\stb\\s([0-9]+)$")) {
			String param = cmd.replaceFirst("get tb ", "");
			int tile = Integer.parseInt(param);
			
			msg = new RegainPermitTileBonusRequestMsg(tile);
		}
		
		// Pass action
		else if (cmd.matches("^pass$")) {
            msg = new PassTurnRequestMsg();
		}
		
		return msg;
		
	}
	
}
