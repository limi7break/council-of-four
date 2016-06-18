package it.polimi.ingsw.ps13.view.server;

import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.util.observer.Observable;
import it.polimi.ingsw.ps13.util.observer.Observer;

/**
 * This class represents a single client connection on the server.
 * 
 * When a request msg is received, it's passed to the game controller via notify.
 * The controller then notifies the handler with a response msg, which is sent to the client.
 *
 */
public abstract class Handler extends Observable<RequestMsg> implements Observer<ResponseMsg> { }