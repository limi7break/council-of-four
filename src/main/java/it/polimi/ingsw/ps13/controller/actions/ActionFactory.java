package it.polimi.ingsw.ps13.controller.actions;

import java.util.Collection;

import it.polimi.ingsw.ps13.controller.actions.bonus.GainVisiblePermitTileAction;
import it.polimi.ingsw.ps13.controller.actions.bonus.RegainPermitTileBonusAction;
import it.polimi.ingsw.ps13.controller.actions.bonus.RegainRewardTokenAction;
import it.polimi.ingsw.ps13.controller.actions.main.AcquirePermitTileAction;
import it.polimi.ingsw.ps13.controller.actions.main.BuildEmporiumAction;
import it.polimi.ingsw.ps13.controller.actions.main.ElectCouncillorAction;
import it.polimi.ingsw.ps13.controller.actions.main.KingAction;
import it.polimi.ingsw.ps13.controller.actions.market.OfferSelectionAction;
import it.polimi.ingsw.ps13.controller.actions.market.TradeProposalAction;
import it.polimi.ingsw.ps13.controller.actions.quick.ChangePermitTilesAction;
import it.polimi.ingsw.ps13.controller.actions.quick.EngageAssistantAction;
import it.polimi.ingsw.ps13.controller.actions.quick.GainMainActionAction;
import it.polimi.ingsw.ps13.controller.actions.quick.QuickElectCouncillorAction;
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
 * This factory class creates all the actions once action request messages are received
 * and preliminary validity checks are performed (e.g. it's the player's turn) 
 *
 */
public class ActionFactory implements ActionVisitor {

	/**
	 * Creates a new PassTurnAction from the corresponding request message.
	 */
	@Override
	public Action visit(PassTurnRequestMsg pass) {
		
		return new PassTurnAction(pass.getPlayerName());
		
	}

	/**
	 * Creates a new AcquirePermitTileAction from the corresponding request message.
	 */
	@Override
	public Action visit(AcquirePermitTileRequestMsg acquireTile) {
		
		String playerName = acquireTile.getPlayerName();
		String region = acquireTile.getRegion();
		int tile = acquireTile.getTile();
		Collection<String> cards = acquireTile.getCards();
		
		return new AcquirePermitTileAction(playerName, region, tile, cards);
		
	}

	/**
	 * Creates a new BuildEmporiumAction from the corresponding request message.
	 */
	@Override
	public Action visit(BuildEmporiumRequestMsg build) {
		
		String playerName = build.getPlayerName();
		int tile = build.getTile();
		String city = build.getCity();
		
		return new BuildEmporiumAction(playerName, tile, city);
		
	}

	/**
	 * Creates a new ElectCouncillorAction from the corresponding request message.
	 */
	@Override
	public Action visit(ElectCouncillorRequestMsg elect) {
		
		String playerName = elect.getPlayerName();
		String region = elect.getRegion();
		String councillor = elect.getCouncillor();
		
		return new ElectCouncillorAction(playerName, region, councillor);
		
	}

	/**
	 * Creates a new KingAction from the corresponding request message.
	 */
	@Override
	public Action visit(KingActionRequestMsg king) {
		
		String playerName = king.getPlayerName();
		String city = king.getCity();
		Collection<String> cards = king.getCards();
		
		return new KingAction(playerName, city, cards);
		
	}

	/**
	 * Creates a new OfferSelectionAction from the corresponding request message.
	 */
	@Override
	public Action visit(OfferSelectionRequestMsg buy) {
		
		String playerName = buy.getPlayerName();
		int entry = buy.getEntry();
		
		return new OfferSelectionAction(playerName, entry);
		
	}

	/**
	 * Creates a new TradeProposalAction from the corresponding request message.
	 */
	@Override
	public Action visit(TradeProposalRequestMsg sell) {
		
		String playerName = sell.getPlayerName();
		int assistants = sell.getAssistants();
		Collection<Integer> tiles = sell.getTiles();
		Collection<String> cards = sell.getCards();
		int price = sell.getPrice();
		
		return new TradeProposalAction(playerName, assistants, tiles, cards, price);
		
	}

	/**
	 * Creates a new ChangePermitTilesAction from the corresponding request message.
	 */
	@Override
	public Action visit(ChangePermitTilesRequestMsg changeTiles) {
		
		String playerName = changeTiles.getPlayerName();
		String region = changeTiles.getRegion();
		
		return new ChangePermitTilesAction(playerName, region);
		
	}

	/**
	 * Creates a new EngageAssistantAction from the corresponding request message.
	 */
	@Override
	public Action visit(EngageAssistantRequestMsg engageAssistant) {
		
		return new EngageAssistantAction(engageAssistant.getPlayerName());
		
	}

	/**
	 * Creates a new GainMainActionAction from the corresponding request message.
	 */
	@Override
	public Action visit(GainMainActionRequestMsg gainMain) {
		
		return new GainMainActionAction(gainMain.getPlayerName());
		
	}

	/**
	 * Creates a new QuickElectCouncillorAction from the corresponding request message.
	 */
	@Override
	public Action visit(QuickElectCouncillorRequestMsg quickElect) {
		
		String playerName = quickElect.getPlayerName();
		String region = quickElect.getRegion();
		String councillor = quickElect.getCouncillor();
		
		return new QuickElectCouncillorAction(playerName, region, councillor);
		
	}

	/**
	 * Creates a new RegainPermitTileBonusAction from the corresponding request message.
	 */
	@Override
	public Action visit(RegainPermitTileBonusRequestMsg tileBonus) {
		
		String playerName = tileBonus.getPlayerName();
		int tile = tileBonus.getTile();
		
		return new RegainPermitTileBonusAction(playerName, tile);
		
	}

	/**
	 * Creates a new RegainRewardTokenAction from the corresponding request message.
	 */
	@Override
	public Action visit(RegainRewardTokenRequestMsg rewardToken) {
		
		String playerName = rewardToken.getPlayerName();
		String city = rewardToken.getCity();
		
		return new RegainRewardTokenAction(playerName, city);		
	}

	/**
	 * Creates a new GainVisiblePermitTileAction from the corresponding request message.
	 */
	@Override
	public Action visit(VisiblePermitTileRequestMsg takeTile) {
		
		String playerName = takeTile.getPlayerName();
		String region = takeTile.getRegion();
		int tile = takeTile.getTile();
		
		return new GainVisiblePermitTileAction(playerName, region, tile);
		
	}
	
}