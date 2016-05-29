package it.polimi.ingsw.ps13.message.request.action;

import java.util.Collection;

import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.Region;

public class AcquirePermitTileRequestMsg extends ActionRequestMsg{

	private static final long serialVersionUID = 0L;

	private final Player player;
	private final Collection<PoliticsCard> cards;
	private final Region region;
	private final PermitTile tile;
	
	public AcquirePermitTileRequestMsg(Player player, Collection<PoliticsCard> cards, Region region, PermitTile tile) {
		
		this.player = player;
		this.cards = cards;
		this.region = region;
		this.tile = tile;
		
		setPlayerName(player.getName());
	}
	
	@Override
	public void accept(ActionVisitor av) {
		
		av.visit(this);
		
	}

	public Player getPlayer() {
		return player;
	}
	
	public Collection<PoliticsCard> getCards() {
		return cards;
	}
	
	public Region getRegion() {
		return region;
	}
	
	public PermitTile getTile() {
		return tile;
	}
	
}