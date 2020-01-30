package edu.asu.stratego;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import edu.asu.stratego.game.ServerGameManager;

/**
 * The Stratego Server creates a socket and listens for connections from every
 * two players to form a game session. Each session is handled by a thread,
 * ServerGameManager, that communicates with the two players and determines the
 * status of the game.
 */
public class Server {
    private static final Logger LOGGER =Logger.getLogger(Server.class.getName());

    public static void main(String[] args) throws IOException {

        String hostAddress    = InetAddress.getLocalHost().getHostAddress();

        int sessionNumber     = 1;
        try (ServerSocket listener = new ServerSocket(4212)) {
            String s = "Server started @ " + hostAddress;
            LOGGER.info(s);
            LOGGER.info("Waiting for incoming connections...\n");

            while (true) {
                Socket playerOne = listener.accept();
                String sesionP1 = "Session " + sessionNumber +
                        ": Player 1 has joined the session";
                LOGGER.info(sesionP1);

                Socket playerTwo = listener.accept();
                String sesionP2 = "Session " + sessionNumber +
                        ": Player 2 has joined the session";
                LOGGER.info(sesionP2);

                Thread session = new Thread(new ServerGameManager(
                        playerOne, playerTwo, sessionNumber++));
                session.setDaemon(true);
                session.start();
                if (!session.isAlive()){
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }
}
