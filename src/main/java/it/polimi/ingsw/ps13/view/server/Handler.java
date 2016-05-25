package it.polimi.ingsw.ps13.view.server;

import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.util.observer.Observable;
import it.polimi.ingsw.ps13.util.observer.Observer;

public abstract class Handler extends Observable<RequestMsg> implements Observer<ResponseMsg> { }