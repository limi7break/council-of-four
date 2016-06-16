package it.polimi.ingsw.ps13.view.client.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.server.rmi.RMIHandlerRemote;
import it.polimi.ingsw.ps13.view.server.rmi.RMIServerRemote;

/**
 * This client is started when the RMI connection type is chosen.
 *
 */
public class ClientRMI extends UnicastRemoteObject implements ClientRMIRemote, ClientConnection {
	
	private static final Logger LOG = Logger.getLogger(ClientRMI.class.getSimpleName());
	
	private static final long serialVersionUID = 0L;
	public static final String HOST = "localhost";
	public static final int PORT = 7777;
	public static final String NAME = "curvanerd";
	
	private final transient RMIHandlerRemote handlerStub;
	
	private List<ResponseMsg> inbox;
	
	private boolean active;

	public ClientRMI() throws RemoteException, NotBoundException, AlreadyBoundException {
		
		inbox = new ArrayList<>();
		active = true;
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.print("IP Address: ");
		String host = scanner.nextLine();
		
		Registry registry = LocateRegistry.getRegistry(host, PORT);
		RMIServerRemote serverStub = (RMIServerRemote) registry.lookup(NAME);
		
		handlerStub = serverStub.connect(this);
		
		LOG.log(Level.INFO, "RMI Connection established @ " + host + ":" + PORT + ". (" + NAME + ")");
		
	}
	
	@Override
	public synchronized ResponseMsg receiveMessage() {
		
		while (inbox.isEmpty()) {
			try {
				wait();
			} catch(InterruptedException e) {
				LOG.log(Level.WARNING, "A problem was encountered while receiving data from the server.", e);
				Thread.currentThread().interrupt();
			}
		}
		
		return inbox.remove(0);
		
	}

	@Override
	public void sendMessage(RequestMsg msg) {
		
		if (!active) {
			throw new IllegalStateException("Connection is closed!");
		}
		
		try {
			
			handlerStub.processRequest(msg);
			
		} catch(RemoteException e) {
			active = false;
			try {
				updateClient(new ResponseMsg("You have lost connection with the server!"));
			} catch (RemoteException e1) {
				// method called locally, no risk of RemoteException
			}
		}
		
	}

	@Override
	public synchronized void updateClient(ResponseMsg msg) throws RemoteException {
		
		inbox.add(msg);
		notifyAll();
		
	}
	
	/**
	 * 
	 * @return
	 */
	@Override
	public boolean isActive() {
		
		return active;
		
	}
	
}
