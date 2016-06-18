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
 * This object is created when the RMI connection type is chosen.
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

	/**
	 * Creates a new RMI connection.
	 * 
	 * The constructor of this object tries to connect to the RMI registry and get the server stub via lookup.
	 * Then a call to the server stub's only method, connect(), returns the handler stub used to send request
	 * messages to the server.
	 * 
	 * @throws RemoteException
	 * @throws NotBoundException
	 * @throws AlreadyBoundException
	 */
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
	
	/**
	 * This method is called by a thread which reads response messages from the server.
	 * If the inbox is empty, this method is blocking.
	 * 
	 * The notify is called by updateClient, which is part of the remote interface ClientRMIRemote,
	 * as soon as a new message has been sent from the server and added to the inbox.
	 * 
	 */
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

	/**
	 * Calls the remote processRequest method on the handler stub.
	 * If the server has disconnected since the last time this method has been called, shuts
	 * this connection down by setting the active flag to false.
	 * 
	 */
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

	/**
	 * This method is called by the server on the RMI client stub. Adds the response message to the inbox
	 * and notifies the consumer thread, so the message is correctly handled by the client.
	 * 
	 */
	@Override
	public synchronized void updateClient(ResponseMsg msg) throws RemoteException {
		
		inbox.add(msg);
		notifyAll();
		
	}
	
	/**
	 * Returns true if the connection is active.
	 * 
	 * @return true if the connection is active
	 */
	@Override
	public boolean isActive() {
		
		return active;
		
	}
	
}
