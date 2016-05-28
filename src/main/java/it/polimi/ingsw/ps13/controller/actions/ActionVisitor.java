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

	public Action visit(PassTurnRequestMsg pass);
	
	public Action visit(AcquirePermitTileRequestMsg acquireTile);
	
	public Action visit(BuildEmporiumRequestMsg build);
	
	public Action visit(ElectCouncillorRequestMsg elect);
	
	public Action visit(KingActionRequestMsg king);
	
	public Action visit(OfferSelectionRequestMsg buy);
	
	public Action visit(TradeProposalRequestMsg sell);
	
	public Action visit(ChangePermitTilesRequestMsg changeTiles);
	
	public Action visit(EngageAssistantRequestMsg engageAssistant);
	
	public Action visit(GainMainActionRequestMsg gainMain);
	
	public Action visit(QuickElectCouncillorRequestMsg quickElect);
	
	public Action visit(RegainPermitTileBonusRequestMsg tileBonus);
	
	public Action visit(RegainRewardTokenRequestMsg rewardToken);
	
	public Action visit(VisiblePermitTileRequestMsg takeTile);
	
}
