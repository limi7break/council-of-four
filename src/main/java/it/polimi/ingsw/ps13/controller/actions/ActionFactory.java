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

public class ActionFactory implements ActionVisitor {
	
	@Override
	public void visit(PassTurnRequestMsg pass) {
		
		
		
	}

	@Override
	public void visit(AcquirePermitTileRequestMsg acquireTile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BuildEmporiumRequestMsg build) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ElectCouncillorRequestMsg elect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(KingActionRequestMsg king) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OfferSelectionRequestMsg buy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TradeProposalRequestMsg sell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ChangePermitTilesRequestMsg changeTiles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EngageAssistantRequestMsg engageAssistant) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(GainMainActionRequestMsg gainMain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(QuickElectCouncillorRequestMsg quickElect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(RegainPermitTileBonusRequestMsg tileBonus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(RegainRewardTokenRequestMsg rewardToken) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VisiblePermitTileRequestMsg takeTile) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
