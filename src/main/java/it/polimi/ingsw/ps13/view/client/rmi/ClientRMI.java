package it.polimi.ingsw.ps13.view.client.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
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
	
	private static final long serialVersionUID = 0L;
	public static final String HOST = "localhost";
	public static final int PORT = 7777;
	public static final String NAME = "curvanerd";
	
	private final transient RMIHandlerRemote handlerStub;
	
	private transient CountDownLatch cdl;
	private ResponseMsg lastReadObject;
	
	private static final Logger LOG = Logger.getLogger(ClientRMI.class.getSimpleName());

	public ClientRMI() throws RemoteException, NotBoundException, AlreadyBoundException {
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.print("IP Address: ");
		String host = scanner.nextLine();
		
		Registry registry = LocateRegistry.getRegistry(host, PORT);
		RMIServerRemote serverStub = (RMIServerRemote) registry.lookup(NAME);
		
		cdl = new CountDownLatch(1);
		
		handlerStub = serverStub.connect(this);
		
		LOG.log(Level.INFO, "RMI Connection established @ " + host + ":" + PORT + ". (" + NAME + ")");
		
	}
	
	@Override
	public ResponseMsg receiveMessage() {
		
		try {
			cdl.await();
		} catch (InterruptedException e) {
			LOG.log(Level.WARNING, "Thread interrupted", e);
			Thread.currentThread().interrupt();
		}
		
		cdl = new CountDownLatch(1);
		
		return lastReadObject;
		
	}

	@Override
	public void sendMessage(RequestMsg msg) {
		
		try {
			
			handlerStub.processRequest(msg);
			
		} catch(RemoteException e) {
			LOG.log(Level.WARNING, "A problem was encountered while sending data to the server.", e);
		}
		
	}

	@Override
	public void updateClient(ResponseMsg msg) throws RemoteException {
		
		this.lastReadObject = msg;
		cdl.countDown();
		
	}
	
}
