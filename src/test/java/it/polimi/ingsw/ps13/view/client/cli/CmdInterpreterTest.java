package it.polimi.ingsw.ps13.view.client.cli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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
 * Test cases for the CLI command interpreter.
 *
 */
public class CmdInterpreterTest {
	
	RequestMsg msg;
	
	@Test
	public void chatAndRenameCommands() {
		
		// String sent to CmdInterpreter: "chat hello guys!"
		String chatMessage = "hello guys!";
		String chatCommand = "chat " + chatMessage;
		msg = CmdInterpreter.parseCommand(chatCommand);
		assertTrue(msg instanceof ChatRequestMsg);
		ChatRequestMsg chatMsg = (ChatRequestMsg) msg;
		assertEquals(chatMsg.getMessage(), chatMessage);
		
		// Let's test getter and setter of RequestMsg abstract class also
		String playerName = "random player name";
		chatMsg.setPlayerName(playerName);
		assertEquals(chatMsg.getPlayerName(), playerName);
		
		// String sent to CmdInterpreter: "chat hello guys!"
		String newName = "new_name";
		String renameCommand = "rename " + newName;
		msg = CmdInterpreter.parseCommand(renameCommand);
		assertTrue(msg instanceof RenameRequestMsg);
		RenameRequestMsg renameMsg = (RenameRequestMsg) msg;
		assertEquals(renameMsg.getNewName(), newName);
		
		
		
	}
	
	@Test
	public void electCommand() {
		
		// String sent to CmdInterpreter: "elect coast white"
		String region = "coast";
		String color = "white";
		String electCommand = "elect " + region + " " + color;
		msg = CmdInterpreter.parseCommand(electCommand);
		assertTrue(msg instanceof ElectCouncillorRequestMsg);
		ElectCouncillorRequestMsg electMsg = (ElectCouncillorRequestMsg) msg;
		assertEquals(electMsg.getRegion(), region);
		assertEquals(electMsg.getCouncillor(), color);
		
	}
	
	@Test
	public void corruptCommand() {
		
		// String sent to CmdInterpreter: "corrupt coast 1 blue white jolly pink"
		String region = "coast";
		int tile = 1;
		List<String> cards = new ArrayList<>();
		cards.add("blue");
		cards.add("white");
		cards.add("jolly");
		cards.add("pink");
		String corruptCommand = "corrupt " + region + " " + tile;
		StringBuilder sb = new StringBuilder();
		sb.append(corruptCommand);
		for (String card : cards)
			sb.append(" ").append(card);
		msg = CmdInterpreter.parseCommand(sb.toString());
		assertTrue(msg instanceof AcquirePermitTileRequestMsg);
		AcquirePermitTileRequestMsg electMsg = (AcquirePermitTileRequestMsg) msg;
		assertEquals(electMsg.getRegion(), region);
		assertEquals(electMsg.getTile(), tile);
		assertTrue(electMsg.getCards().equals(cards));
		
	}
	
	@Test
	public void buildCommand() {
		
		// String sent to CmdInterpreter: "build 4 graden"
		int tile = 4;
		String city = "graden";
		String buildCommand = "build " + tile + " " + city;
		msg = CmdInterpreter.parseCommand(buildCommand);
		assertTrue(msg instanceof BuildEmporiumRequestMsg);
		BuildEmporiumRequestMsg buildMsg = (BuildEmporiumRequestMsg) msg;
		assertEquals(buildMsg.getTile(), tile);
		assertEquals(buildMsg.getCity(), city);
		
	}
	
	@Test
	public void kingCommand() {
		
		// String sent to CmdInterpreter: "king arkon black magenta white orange"
		String city = "arkon";
		List<String> cards = new ArrayList<>();
		cards.add("black");
		cards.add("magenta");
		cards.add("white");
		cards.add("orange");
		String kingCommand = "king " + city;
		StringBuilder sb = new StringBuilder();
		sb.append(kingCommand);
		for (String card : cards)
			sb.append(" ").append(card);
		msg = CmdInterpreter.parseCommand(sb.toString());
		assertTrue(msg instanceof KingActionRequestMsg);
		KingActionRequestMsg kingMsg = (KingActionRequestMsg) msg;
		assertEquals(kingMsg.getCity(), city);
		assertTrue(kingMsg.getCards().equals(cards));
		
	}
	
	@Test
	public void changeTilesCommand() {
		
		// String sent to CmdInterpreter: "changetiles mountain"
		String region = "mountain";
		String changeTilesCommand = "changetiles " + region;
		msg = CmdInterpreter.parseCommand(changeTilesCommand);
		assertTrue(msg instanceof ChangePermitTilesRequestMsg);
		ChangePermitTilesRequestMsg changeTilesMsg = (ChangePermitTilesRequestMsg) msg;
		assertEquals(changeTilesMsg.getRegion(), region);
		
	}
	
	@Test
	public void engageAssistantCommand() {
		
		// String sent to CmdInterpreter: "engageassistant"
		String engageAssistantCommand = "engageassistant";
		msg = CmdInterpreter.parseCommand(engageAssistantCommand);
		assertTrue(msg instanceof EngageAssistantRequestMsg);
		
	}
	
	@Test
	public void gainMainActionCommand() {
		
		// String sent to CmdInterpreter: "gainmainaction"
		String gainMainActionCommand = "gainmainaction";
		msg = CmdInterpreter.parseCommand(gainMainActionCommand);
		assertTrue(msg instanceof GainMainActionRequestMsg);
		
	}
	
	@Test
	public void quickElectCommand() {
		
		// String sent to CmdInterpreter: "qelect hill black"
		String region = "hill";
		String color = "black";
		String quickElectCommand = "qelect " + region + " " + color;
		msg = CmdInterpreter.parseCommand(quickElectCommand);
		assertTrue(msg instanceof QuickElectCouncillorRequestMsg);
		QuickElectCouncillorRequestMsg quickElectMsg = (QuickElectCouncillorRequestMsg) msg;
		assertEquals(quickElectMsg.getRegion(), region);
		assertEquals(quickElectMsg.getCouncillor(), color);
		
	}
	
	@Test
	public void sellCommand() {
		
		// User input simulation
		ByteArrayInputStream in = new ByteArrayInputStream("7\nblack blue jolly\n1 2 3\n10\n".getBytes());
		System.setIn(in);
		int assistants = 7;
		List<String> cards = new ArrayList<>();
		cards.add("black");
		cards.add("blue");
		cards.add("jolly");
		List<Integer> tiles = new ArrayList<>();
		tiles.add(1);
		tiles.add(2);
		tiles.add(3);
		int price = 10;
		msg = CmdInterpreter.parseCommand("sell");
		assertTrue(msg instanceof TradeProposalRequestMsg);
		TradeProposalRequestMsg sellMsg = (TradeProposalRequestMsg) msg;
		assertEquals(sellMsg.getAssistants(), assistants);
		assertEquals(sellMsg.getCards(), cards);
		assertEquals(sellMsg.getTiles(), tiles);
		assertEquals(sellMsg.getPrice(), price);
		System.setIn(System.in);
		
	}
	
	@Test
	public void buyCommand() {
		
		// String sent to CmdInterpreter: "buy 4 7 10"
		List<Integer> entries = new ArrayList<>();
		entries.add(4);
		entries.add(7);
		entries.add(10);
		String buyCommand = "buy";
		StringBuilder sb = new StringBuilder();
		sb.append(buyCommand);
		for (Integer entry : entries)
			sb.append(" ").append(entry.intValue());
		msg = CmdInterpreter.parseCommand(sb.toString());
		assertTrue(msg instanceof OfferSelectionRequestMsg);
		OfferSelectionRequestMsg buyMsg = (OfferSelectionRequestMsg) msg;
		assertEquals(buyMsg.getEntries(), entries);
		
	}
	
	@Test
	public void getTileCommand() {
		
		// String sent to CmdInterpreter: "get tile coast 1"
		String region = "coast";
		int tile = 1;
		String getTileCommand = "get tile " + region + " " + tile;
		msg = CmdInterpreter.parseCommand(getTileCommand);
		assertTrue(msg instanceof VisiblePermitTileRequestMsg);
		VisiblePermitTileRequestMsg getTileMsg = (VisiblePermitTileRequestMsg) msg;
		assertEquals(getTileMsg.getRegion(), region);
		assertEquals(getTileMsg.getTile(), tile);
		
	}
	
	@Test
	public void getRTCommand() {
		
		// String sent to CmdInterpreter: "get rt merkatim"
		String city = "merkatim";
		String getRTCommand = "get rt " + city;
		msg = CmdInterpreter.parseCommand(getRTCommand);
		assertTrue(msg instanceof RegainRewardTokenRequestMsg);
		RegainRewardTokenRequestMsg getRTMsg = (RegainRewardTokenRequestMsg) msg;
		assertEquals(getRTMsg.getCity(), city);
		
	}
	
	@Test
	public void getTBCommand() {
		
		// String sent to CmdInterpreter: "get tb 7"
		int tile = 7;
		String getTBCommand = "get tb " + tile;
		msg = CmdInterpreter.parseCommand(getTBCommand);
		assertTrue(msg instanceof RegainPermitTileBonusRequestMsg);
		RegainPermitTileBonusRequestMsg getTBMsg = (RegainPermitTileBonusRequestMsg) msg;
		assertEquals(getTBMsg.getTile(), tile);
		
	}
	
	@Test
	public void passCommand() {
		
		// String sent to CmdInterpreter: "pass"
		String passCommand = "pass";
		msg = CmdInterpreter.parseCommand(passCommand);
		assertTrue(msg instanceof PassTurnRequestMsg);
		
	}
	
	@Test
	public void commandNotRecognized() {
		
		assertNull(CmdInterpreter.parseCommand("chathello guys!"));
		assertNull(CmdInterpreter.parseCommand("elect coast 7"));
		
		assertNull(CmdInterpreter.parseCommand("corrupt coast x black blue white magenta"));
		assertNull(CmdInterpreter.parseCommand("build abc arkon"));
		assertNull(CmdInterpreter.parseCommand("elect 94 councillors"));
		assertNull(CmdInterpreter.parseCommand("king 9000 jolly blue random cards"));
		
		assertNull(CmdInterpreter.parseCommand("changetiles .mountain"));
		assertNull(CmdInterpreter.parseCommand("qelect coast ____"));
		
		assertNull(CmdInterpreter.parseCommand("buy market entry"));
		
		assertNull(CmdInterpreter.parseCommand("gettile mountain 4"));
		assertNull(CmdInterpreter.parseCommand("get rt 4"));
		assertNull(CmdInterpreter.parseCommand("get tb lulz"));
		
		assertNull(CmdInterpreter.parseCommand("pas"));
		
	}

}
