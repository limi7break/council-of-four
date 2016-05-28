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

	public void visit(PassTurnRequestMsg pass);
	
	public void visit(AcquirePermitTileRequestMsg acquireTile);
	
	public void visit(BuildEmporiumRequestMsg build);
	
	public void visit(ElectCouncillorRequestMsg elect);
	
	public void visit(KingActionRequestMsg king);
	
	public void visit(OfferSelectionRequestMsg buy);
	
	public void visit(TradeProposalRequestMsg sell);
	
	public void visit(ChangePermitTilesRequestMsg changeTiles);
	
	public void visit(EngageAssistantRequestMsg engageAssistant);
	
	public void visit(GainMainActionRequestMsg gainMain);
	
	public void visit(QuickElectCouncillorRequestMsg quickElect);
	
	public void visit(RegainPermitTileBonusRequestMsg tileBonus);
	
	public void visit(RegainRewardTokenRequestMsg rewardToken);
	
	public void visit(VisiblePermitTileRequestMsg takeTile);
	
}
