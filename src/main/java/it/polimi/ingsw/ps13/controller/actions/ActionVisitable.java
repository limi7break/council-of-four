package it.polimi.ingsw.ps13.controller.actions;

@FunctionalInterface
public interface ActionVisitable {

	public void accept(ActionVisitor av);
	
}
