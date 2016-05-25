package it.polimi.ingsw.ps13.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This controller handles multiple games.
 * When two players join the waiting game, a countdown is started to start the game.
 * If in the meantime someone else tries to connect the timer resets and starts over.
 * If the number of waiting players reaches 8 the game starts instantly.
 * 
 */
public class GamesController {
	
	private static final Logger LOG = Logger.getLogger(GamesController.class.getSimpleName());
	private static final int COUNTDOWN = 5;
	
	private final Set<GameController> games;
	
	private Timer timer;
	private TimerTask timerTask;
	
	private final List<String> waitingPlayers;
	private GameController waitingGame;
	
	/**
	 * Initializes the timer, the set 
	 * 
	 */
	public GamesController() { 
	
		timer = new Timer();
		games = new HashSet<>();
		waitingPlayers = new ArrayList<>();
		waitingGame = new GameController();
		games.add(waitingGame);
	
	}
	
	/**
	 * 
	 */
	public void addPlayer(String name) {
		
		waitingPlayers.add(name);
		waitingGame.addPlayer(name);
        LOG.log(Level.INFO, "New player added to waiting game.");

        if ((waitingPlayers.size() >= 2) && (waitingPlayers.size() <= 7)) {

            timer.cancel();

            timerTask = new TimerTask() {
                @Override
                public void run() {
                    try {

                        new Thread(waitingGame).start();
                        waitingGame = new GameController();
                        games.add(waitingGame);

                    } catch (Exception e) {
                        LOG.log(Level.SEVERE, "Unexpected exception while creating a new game.", e);
                    }
                }
            };

            timer = new Timer();

            timer.schedule(timerTask, (long) COUNTDOWN * 1000);

        } else if (waitingPlayers.size() == 8) {

            timerTask.cancel();
            timer.cancel();
            // Start game instantly
            timer.schedule(timerTask, 0);

        }
		
	}
	
	/**
	 * 
	 * @return
	 */
	public GameController getWaitingGame() {
		
		return waitingGame;
		
	}
	
}
