package it.polimi.ingsw.ps13.controller.actions;

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
 * This class implements the visitor of a visitor pattern
 * The visitable objects are all the action request messages.
 *
 */
public interface ActionVisitor {

	/**
	 * Creates a new PassTurnAction from the corresponding request message.
	 */
	public Action visit(PassTurnRequestMsg pass);
	
	/**
	 * Creates a new AcquirePermitTileAction from the corresponding request message.
	 */
	public Action visit(AcquirePermitTileRequestMsg acquireTile);
	
	/**
	 * Creates a new BuildEmporiumAction from the corresponding request message.
	 */
	public Action visit(BuildEmporiumRequestMsg build);
	
	/**
	 * Creates a new ElectCouncillorAction from the corresponding request message.
	 */
	public Action visit(ElectCouncillorRequestMsg elect);
	
	/**
	 * Creates a new KingAction from the corresponding request message.
	 */
	public Action visit(KingActionRequestMsg king);
	
	/**
	 * Creates a new OfferSelectionAction from the corresponding request message.
	 */
	public Action visit(OfferSelectionRequestMsg buy);
	
	/**
	 * Creates a new TradeProposalAction from the corresponding request message.
	 */
	public Action visit(TradeProposalRequestMsg sell);
	
	/**
	 * Creates a new ChangePermitTilesAction from the corresponding request message.
	 */
	public Action visit(ChangePermitTilesRequestMsg changeTiles);
	
	/**
	 * Creates a new EngageAssistantAction from the corresponding request message.
	 */
	public Action visit(EngageAssistantRequestMsg engageAssistant);
	
	/**
	 * Creates a new GainMainActionAction from the corresponding request message.
	 */
	public Action visit(GainMainActionRequestMsg gainMain);
	
	/**
	 * Creates a new QuickElectCouncillorAction from the corresponding request message.
	 */
	public Action visit(QuickElectCouncillorRequestMsg quickElect);
	
	/**
	 * Creates a new RegainPermitTileBonusAction from the corresponding request message.
	 */
	public Action visit(RegainPermitTileBonusRequestMsg tileBonus);
	
	/**
	 * Creates a new RegainRewardTokenAction from the corresponding request message.
	 */
	public Action visit(RegainRewardTokenRequestMsg rewardToken);
	
	/**
	 * Creates a new GainVisiblePermitTileAction from the corresponding request message.
	 */
	public Action visit(VisiblePermitTileRequestMsg takeTile);
	
}
