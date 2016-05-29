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
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.market.MarketEntry;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.City;
import it.polimi.ingsw.ps13.model.region.Region;


/**
 * This factory class creates all the actions once action request messages are received and validity is checked 
 * (GameController's task)
 *
 */
public class ActionFactory implements ActionVisitor {

	//PassTurnAction
	@Override
	public Action visit(PassTurnRequestMsg pass) {
		
		return new PassTurnAction(pass.getPlayer());
		
	}

	//Acquire Permit tile main action
	@Override
	public Action visit(AcquirePermitTileRequestMsg acquireTile) {
		
		Player player = acquireTile.getPlayer();
		Collection<PoliticsCard> cards = acquireTile.getCards();
		Region region = acquireTile.getRegion();
		PermitTile tile = acquireTile.getTile();
		
		return new AcquirePermitTileAction(player, cards, region, tile);
		
	}

	//Build emporium main action
	@Override
	public Action visit(BuildEmporiumRequestMsg build) {
		
		Player player = build.getPlayer();
		PermitTile tile = build.getTile();
		City city = build.getCity();
		
		return new BuildEmporiumAction(player, tile, city);
		
	}

	//Elect councillor main action
	@Override
	public Action visit(ElectCouncillorRequestMsg elect) {
		
		Player player = elect.getPlayer();
		Councillor councillor = elect.getCouncillor();
		Region region = elect.getRegion();
		
		return new ElectCouncillorAction(player, councillor, region);
		
	}

	//King main action
	@Override
	public Action visit(KingActionRequestMsg king) {
		
		Player player = king.getPlayer();
		Collection<PoliticsCard> cards = king.getCards();
		City city = king.getCity();
		
		return new KingAction(player, cards, city);
		
	}

	//Market buy action
	@Override
	public Action visit(OfferSelectionRequestMsg buy) {
		
		Player player = buy.getPlayer();
		int entry = buy.getEntry();
		
		return new OfferSelectionAction(player, entry);
		
	}

	//Market sell action
	@Override
	public Action visit(TradeProposalRequestMsg sell) {
		
		Player player = sell.getPlayer();
		MarketEntry entry = sell.getEntry();
		
		return new TradeProposalAction(player, entry);
		
	}

	//Change permit tiles quick action
	@Override
	public Action visit(ChangePermitTilesRequestMsg changeTiles) {
		
		Player player = changeTiles.getPlayer();
		Region region = changeTiles.getRegion();
		
		return new ChangePermitTilesAction(player, region);
		
	}

	//Engage assistant quick action
	@Override
	public Action visit(EngageAssistantRequestMsg engageAssistant) {
		
		return new EngageAssistantAction(engageAssistant.getPlayer());
		
	}

	// ------Gain another main action------ quick action
	@Override
	public Action visit(GainMainActionRequestMsg gainMain) {
		
		return new GainMainActionAction(gainMain.getPlayer());
		
	}

	//Elect councillor quick action
	@Override
	public Action visit(QuickElectCouncillorRequestMsg quickElect) {
		
		Player player = quickElect.getPlayer();
		Councillor councillor = quickElect.getCouncillor();
		Region region = quickElect.getRegion();
		
		return new QuickElectCouncillorAction(player, councillor, region);
		
	}

	//Gain permit tile bonus again bonus action
	@Override
	public Action visit(RegainPermitTileBonusRequestMsg tileBonus) {
		
		Player player = tileBonus.getPlayer();
		PermitTile tile = tileBonus.getTile();
		
		return new RegainPermitTileBonusAction(player, tile);
		
	}

	//Reward token bonus again bonus action
	@Override
	public Action visit(RegainRewardTokenRequestMsg rewardToken) {
		
		Player player = rewardToken.getPlayer();
		City city = rewardToken.getCity();
		
		return new RegainRewardTokenAction(player, city);		
	}

	//Take a visible permit tile bonus action
	@Override
	public Action visit(VisiblePermitTileRequestMsg takeTile) {
		
		Player player = takeTile.getPlayer();
		PermitTile tile = takeTile.getTile();
		
		return new GainVisiblePermitTileAction(player, tile);
		
	}
	
}