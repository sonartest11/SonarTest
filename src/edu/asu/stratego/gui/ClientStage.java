package edu.asu.stratego.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import edu.asu.stratego.media.ImageConstants;
import javafx.stage.Stage;

/**
 * The ConnectionStage class, which inherits from the JavaFX Stage class, is a 
 * preset Stage for facilitating easy navigation between scenes in the Client 
 * application.
 */
public class ClientStage extends Stage {
    
    private ConnectionScene connection;
    // Calculate the BoardScene dimensions from screen resolution.
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int SIDE = (int) (0.85 * screenSize.getHeight()) / 12 * 12;
    private static final double UNIT =(double) SIDE / 12;

    /**
     * Creates a new instance of ClientStage.
     */
    public ClientStage() {
        setConnectionScene();
        this.setTitle("ASU Stratego");
        this.setResizable(false);
        this.getIcons().add(ImageConstants.stratego_logo);
        this.show();
    }
    
    /**
     * Switch to the Connection Scene.
     * @see edu.asu.stratego.gui.ConnectionScene
     */
    private void setConnectionScene() {
        connection = new ConnectionScene();
        this.setScene(getConnection().scene);
    }
    
    /**
     * Switch to the Waiting Scene.
     * @see edu.asu.stratego.gui.WaitingScene
     */
    public void setWaitingScene() {
        WaitingScene waiting = new WaitingScene();
        this.setScene(waiting.scene);
    }
    
    /**
     * Switch to the Board Scene.
     * @see BoardScene
     */
    public void setBoardScene() {
        BoardScene board = new BoardScene();
        this.setScene(board.scene);
    }

    /**
     * Returns the ConnectionScene created in the ClientStage instance.
     * @return ConnectionScene object
     */
    private ConnectionScene getConnection() {
        return connection;
    }

    /**
     * @return the board scene SIDE length (in pixels) divided by 12.
     */
    public static double getUnit() {
        return UNIT;
    }
    
    /**
     * @return the SIDE length of the board scene (in pixels)
     */
    public static int getSide() {
        return SIDE;
    }
}