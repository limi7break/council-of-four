package it.polimi.ingsw.ps13.view.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.controller.GamesController;
import it.polimi.ingsw.ps13.view.client.rmi.ClientRMIRemote;
import it.polimi.ingsw.ps13.view.server.rmi.RMIHandler;
import it.polimi.ingsw.ps13.view.server.rmi.RMIHandlerRemote;
import it.polimi.ingsw.ps13.view.server.rmi.RMIServerRemote;
import it.polimi.ingsw.ps13.view.server.socket.SocketHandler;

/**
 * Entry point for a game server.
 *
 */
public class GameServer implements RMIServerRemote {

	private static final Logger LOG = Logger.getLogger(GameServer.class.getSimpleName());
	
	private static final int PORT = 1337;
	private static final int RMI_PORT = 7777;
	private static final String NAME = "curvanerd";
	
	private final GamesController mainController;
	
	private boolean running;
	
	// player number should reset when current game starts and a new game is created
	private int playerNumber = 0;
	
	public GameServer() {
		
		running = true;
		mainController = new GamesController();
		
	}
	
	/**
	 * 
	 */
	public void startRMI() {
		
		try {
			Registry registry = LocateRegistry.createRegistry(RMI_PORT);
			
			@SuppressWarnings("unused")
			RMIServerRemote serverRemote = (RMIServerRemote) UnicastRemoteObject.exportObject(this, 0);
			
			registry.bind(NAME, this);
		} catch(RemoteException | AlreadyBoundException e) {
			LOG.log(Level.SEVERE, "A problem was encountered while initializing RMI registry.", e);
		}
		
	}
	
	/**
	 * 
	 */
	public void startSocket() {
		
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
	
			LOG.log(Level.INFO, "SERVER SOCKET READY ON PORT " + PORT);
			
			while (running) {
				Socket socket = serverSocket.accept();
	
				playerNumber++;
				String playerName = "Giocatore " + playerNumber;
				
				SocketHandler socketHandler = new SocketHandler(socket, playerName);
				
				mainController.getWaitingGame().registerObserver(socketHandler);
				socketHandler.registerObserver(mainController.getWaitingGame());
				
				mainController.addPlayer(playerName);
				new Thread(socketHandler).start();
			}
			
			serverSocket.close();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "A problem was encountered while creating socket handler.", e);
		}
		
	}

	/**
	 * Used for RMI connection. Returns a new RMI handler every time the
	 * method is called.
	 */
	@Override
	public RMIHandlerRemote connect(ClientRMIRemote clientStub) throws RemoteException {
		
		playerNumber++;
		String playerName = "Giocatore " + playerNumber;
		
		RMIHandler rmiHandler = new RMIHandler(clientStub, playerName);
		
		mainController.getWaitingGame().registerObserver(rmiHandler);
		rmiHandler.registerObserver(mainController.getWaitingGame());
		
		mainController.addPlayer(playerName);
		
		RMIHandlerRemote handlerStub = (RMIHandlerRemote) UnicastRemoteObject.exportObject(rmiHandler, 0);
		
		return handlerStub;
		
	}
	
	/**
	 * 
	 */
	public static void main(String[] args) {

		GameServer server = new GameServer();
		server.startRMI();
		server.startSocket();
		
	}

}
