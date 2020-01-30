package edu.asu.stratego.game;

import java.io.*;


import edu.asu.stratego.Client;
import edu.asu.stratego.Server;
import edu.asu.stratego.media.PlaySound;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import edu.asu.stratego.game.board.ClientSquare;
import edu.asu.stratego.gui.BoardScene;
import edu.asu.stratego.gui.ClientStage;
import edu.asu.stratego.gui.ConnectionScene;
import edu.asu.stratego.gui.board.BoardTurnIndicator;
import edu.asu.stratego.media.ImageConstants;
import edu.asu.stratego.util.HashTables;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Task to handle the Stratego game on the client-side.
 */
public class ClientGameManager implements Runnable {

    private static final Object setupPieces = new Object();
    private static final Object sendMove = new Object();
    private static final Object waitFade = new Object();

    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;
    private PieceColor winnerColor;

    private ClientStage stage;

    private int shiftX = 0;
    private int shiftY = 0;

    private static final Logger LOGGER =Logger.getLogger(Server.class.getName());
    private static final String MESSAGE="Mesagge: ";
    private static final String CAUSSE="\n Cause: ";

    /**
     * Creates a new instance of ClientGameManager.
     *
     * @param stage the stage that the client is set in
     */
    public ClientGameManager(ClientStage stage) {
        this.stage = stage;
    }

    /**
     * See ServerGameManager's run() method to understand how the client
     * interacts with the server.
     *
     //* @see edu.asu.stratego.Game.ServerGameManager
     */
    @Override
    public void run() {
        connectToServer();
        waitForOpponent();

        setupBoard();
        playGame();
    }

    /**
     * @return Object used for communication between the Setup Board GUI and
     * the ClientGameManager to indicate when the player has finished setting
     * up their pieces.
     */
    public static Object getSetupPieces() {
        return setupPieces;
    }

    /**
     * Executes the ConnectToServer thread. Blocks the current thread until
     * the ConnectToServer thread terminates.
     *
     * @see ConnectionScene.ConnectToServer
     */
    private void connectToServer() {
        try {
            ConnectionScene.ConnectToServer connectToServer =
                    new ConnectionScene.ConnectToServer();
            Thread serverConnect = new Thread(connectToServer);
            serverConnect.setDaemon(true);
            serverConnect.start();
            serverConnect.join();
        } catch (InterruptedException e) {
            LOGGER.severe(MESSAGE+e.getMessage()+CAUSSE+ e.getCause());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Establish I/O streams between the client and the server. Send player
     * information to the server. Then, wait until an object containing player
     * information about the opponent is received from the server.
     *
     * <p>
     * After the player information has been sent and opponent information has
     * been received, the method terminates indicating that it is time to set up
     * the game.
     * </p>
     */
    private void waitForOpponent() {
        Platform.runLater(() ->
            stage.setWaitingScene()
        );
        try {
            // I/O Streams.
            toServer = new ObjectOutputStream(ClientSocket.getInstance().getOutputStream());
            fromServer = new ObjectInputStream(ClientSocket.getInstance().getInputStream());

            // Exchange player information.
            toServer.writeObject(Game.getPlayer());
            Game.setOpponent((Player) fromServer.readObject());

            // Infer player color from opponent color.
            if (Game.getOpponent().getColor() == PieceColor.RED)
                Game.getPlayer().setColor(PieceColor.BLUE);
            else
                Game.getPlayer().setColor(PieceColor.RED);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.severe(MESSAGE+e.getMessage()+CAUSSE+ e.getCause());
        }
    }

    /**
     * Switches to the game setup scene. Players will place their pieces to
     * their initial starting positions. Once the pieces are placed, their
     * positions are sent to the server.
     */
    private void setupBoard() {
        Platform.runLater(() ->
            stage.setBoardScene()
        );

        synchronized (setupPieces) {
            try {
                // Wait for the player to set up their pieces.
                boolean waiting = true;
                while (waiting) {
                    setupPieces.wait();
                    waiting = false;
                }
                Game.setStatus(GameStatus.WAITING_OPP);

                // Send initial piece positions to server.
                SetupBoard initial = new SetupBoard();
                initial.getPiecePositions();
                toServer.writeObject(initial);

                // Receive opponent's initial piece positions from server.
                final SetupBoard opponentInitial = (SetupBoard) fromServer.readObject();

                // Place the opponent's pieces on the board.
                Platform.runLater(() -> {
                    for (int row = 0; row < 4; ++row) {
                        for (int col = 0; col < 10; ++col) {
                            ClientSquare square = Game.getBoard().getSquare(row, col);
                            square.setPiece(opponentInitial.getPiece(row, col));

                            if (Game.getPlayer().getColor() == PieceColor.RED)
                                square.getPiecePane().setPiece(ImageConstants.getBlueBack());
                            else
                                square.getPiecePane().setPiece(ImageConstants.getRedBack());
                        }
                    }
                });
            } catch (InterruptedException | IOException | ClassNotFoundException e) {
                LOGGER.severe(MESSAGE+e.getMessage()+CAUSSE+ e.getCause());
                Thread.currentThread().interrupt();
            }
        }
    }

    private void playGame() {
        // Remove setup panel
        Platform.runLater(() ->
            BoardScene.getRootPane().getChildren().remove(BoardScene.getSetupPanel())
        );
        PlaySound.playMusic();
        // Get game status from the serverRun
        try {
            Game.setStatus((GameStatus) fromServer.readObject());
        } catch (ClassNotFoundException | IOException e) {
            LOGGER.severe(MESSAGE+e.getMessage()+CAUSSE+ e.getCause());
        }


        // Main loop (when playing)
        while (Game.getStatus() == GameStatus.IN_PROGRESS) {
            try {
                // Get turn color from server.
                Game.setTurn((PieceColor) fromServer.readObject());

                // If the turn is the client's, set move status to none selected
                if (Game.getPlayer().getColor() == Game.getTurn())
                    Game.setMoveStatus(MoveStatus.NONE_SELECTED);
                else
                    Game.setMoveStatus(MoveStatus.OPP_TURN);

                // Notify turn indicator.
                synchronized (BoardTurnIndicator.getTurnIndicatorTrigger()) {
                    BoardTurnIndicator.getTurnIndicatorTrigger().notifyAll();
                }

                // Send move to the server.
                if (Game.getPlayer().getColor() == Game.getTurn() && Game.getMoveStatus() != MoveStatus.SERVER_VALIDATION) {
                    synchronized (sendMove) {
                        sendMove.wait();
                        toServer.writeObject(Game.getMove());
                        Game.setMoveStatus(MoveStatus.SERVER_VALIDATION);
                    }
                }

                // Receive move from the server.
                Game.setMove((Move) fromServer.readObject());
                Piece startPiece = Game.getMove().getStartPiece();
                Piece endPiece = Game.getMove().getEndPiece();

                // If the move is an attack, not just a move to an unoccupied square
                if (Game.getMove().isAttackMove()) {
                    attackMove();
                }

               playMove(startPiece,endPiece);

                // If it is an attack, wait 0.05 seconds to allow the arrow to be visible
                if (Game.getMove().isAttackMove()) {
                    Thread.sleep(50);
                }
                runLater();

                // Wait for fade animation to complete before continuing.
                synchronized (waitFade) {
                    waitFade.wait();
                }

                // Get game status from server.
                Game.setStatus((GameStatus) fromServer.readObject());
            } catch (ClassNotFoundException | IOException | InterruptedException e) {
                LOGGER.info(MESSAGE+e.getMessage()+CAUSSE+ e.getCause());
                Thread.currentThread().interrupt();
            }
             winnerColor = Game.getTurn();
        }
        revealAll();

    }

    private void playMove (Piece startPiece, Piece endPiece){
        // Set the piece on the software (non-GUI) board to the updated pieces (either null or the winning piece)
        Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y).setPiece(startPiece);
        Game.getBoard().getSquare(Game.getMove().getEnd().x, Game.getMove().getEnd().y).setPiece(endPiece);

        // Update GUI.
        Platform.runLater(() -> {

            ClientSquare endSquare = Game.getBoard().getSquare(Game.getMove().getEnd().x, Game.getMove().getEnd().y);
            // Draw
            if (endPiece == null)
                endSquare.getPiecePane().setPiece(null);
            else {
                // If not a draw, set the end piece to the PieceType face
                if (endPiece.getPieceColor() == Game.getPlayer().getColor()) {
                    endSquare.getPiecePane().setPiece(HashTables.getPieceMap().get(endPiece.getPieceSpriteKey()));
                }
                // ...unless it is the opponent's piece which it will display the back instead
                else {
                    if (endPiece.getPieceColor() == PieceColor.BLUE)
                        endSquare.getPiecePane().setPiece(ImageConstants.getBlueBack());
                    else
                        endSquare.getPiecePane().setPiece(ImageConstants.getRedBack());
                }
            }
        });
    }
    private void runLater(){
        Platform.runLater(() -> {
            // Arrow

            ClientSquare arrowSquare = Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y);

            // Change the arrow to an image (and depending on what color the arrow should be)
            if (Game.getMove().getMoveColor() == PieceColor.RED)
                arrowSquare.getPiecePane().setPiece(ImageConstants.MOVEARROW_RED);
            else
                arrowSquare.getPiecePane().setPiece(ImageConstants.MOVEARROW_BLUE);

            // Rotate the arrow to show the direction of the move
            if (Game.getMove().getStart().x > Game.getMove().getEnd().x)
                arrowSquare.getPiecePane().getPiece().setRotate(0);
            else if (Game.getMove().getStart().y < Game.getMove().getEnd().y)
                arrowSquare.getPiecePane().getPiece().setRotate(90);
            else if (Game.getMove().getStart().x < Game.getMove().getEnd().x)
                arrowSquare.getPiecePane().getPiece().setRotate(180);
            else
                arrowSquare.getPiecePane().getPiece().setRotate(270);

            winnerMove(arrowSquare, null);
        });
    }
    private void attackMove() throws InterruptedException {
        Piece attackingPiece = Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y).getPiece();

        if (attackingPiece.getPieceType() == PieceType.SCOUT) {
           attackScout();
        }
        Platform.runLater(() -> {
            try {
                // Set the face images visible to both players (from the back that doesn't show piecetype)

                Piece animStartPiece = this.variableStartSquare().getPiece();
                Piece animEndPiece = this.variableEndSquare().getPiece();

                this.variableStartSquare().getPiecePane().setPiece(HashTables.getPieceMap().get(animStartPiece.getPieceSpriteKey()));
                this.variableEndSquare().getPiecePane().setPiece(HashTables.getPieceMap().get(animEndPiece.getPieceSpriteKey()));

            } catch (Exception e) {
                LOGGER.severe(MESSAGE+e.getMessage()+CAUSSE+ e.getCause());
            }
        });

        // Wait three seconds (the image is shown to client, then waits 2 seconds)
        Thread.sleep(2000);

        // Fade out pieces that lose (or draw)
        Platform.runLater(() -> {
            try {
                
                // If the piece dies, fade it out (also considers a draw, where both "win" are set to false)
                if (!Game.getMove().isAttackWin()) {
                    winnerMove(this.variableStartSquare(), this.variableEndSquare());
                }
                if (!Game.getMove().isDefendWin()) {
                    winnerMove(this.variableEndSquare(),this.variableStartSquare());
                }
            } catch (Exception e) {
                LOGGER.severe(MESSAGE+e.getMessage()+CAUSSE+ e.getCause());
            }
        });

        // Wait 1.5 seconds while the image fades out
        Thread.sleep(1500);
    }

    private void attackScout() throws InterruptedException {
        // Check if the scout is attacking over more than one square
        int moveX = Game.getMove().getStart().x - Game.getMove().getEnd().x;
        int moveY = Game.getMove().getStart().y - Game.getMove().getEnd().y;

        if (Math.abs(moveX) > 1 || Math.abs(moveY) > 1) {
            Platform.runLater(() -> {
                try {

                    move(moveX, moveY);

                    // Move the sDoublecout in front of the piece it's attacking before actually fading out
                    ClientSquare scoutSquare = Game.getBoard().getSquare(Game.getMove().getEnd().x + shiftX, Game.getMove().getEnd().y + shiftY);
                    ClientSquare startSquare = Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y);
                    scoutSquare.getPiecePane().setPiece(HashTables.getPieceMap().get(startSquare.getPiece().getPieceSpriteKey()));
                    startSquare.getPiecePane().setPiece(null);
                } catch (Exception e) {
                    LOGGER.severe(MESSAGE+e.getMessage()+CAUSSE+ e.getCause());
                }
            });

            // Wait 1 second after moving the scout in front of the piece it's going to attack
            Thread.sleep(1000);

            move(moveX, moveY);


            // Fix the clientside software boards (and move) to reflect new scout location, now attacks like a normal piece
            Game.getBoard().getSquare(Game.getMove().getEnd().x + shiftX, Game.getMove().getEnd().y + shiftY).setPiece(this.variableStartSquare().getPiece());
            Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y).setPiece(null);

            Game.getMove().setStart(Game.getMove().getEnd().x + shiftX, Game.getMove().getEnd().y + shiftY);
        }
    }
    private void winnerMove(ClientSquare startSquare, ClientSquare actualwinner) {
        if (actualwinner != null) {
            fight(startSquare, actualwinner);
        }
        FadeTransition fade = new FadeTransition(Duration.millis(1500), startSquare.getPiecePane().getPiece());
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.play();
        fade.setOnFinished(new ResetSquareImage());
    }

    public static Object getSendMove() {
        return sendMove;
    }


    public void resolveFight(Image actualwinner){
        Label winner = new Label();
        GridPane winnerpane = new GridPane();
        winnerpane.setBackground(new Background(new BackgroundImage(ImageConstants.GRASS, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        Scene winnerScene = new Scene(winnerpane);
        winnerScene.setFill(Color.TRANSPARENT);
        Stage winnerfinalStage = new Stage();
        winnerfinalStage.initStyle(StageStyle.TRANSPARENT);
        winner.setText("WINNER");
        winner.setFont(new Font("Arial",25));
        winner.setTextFill(Color.WHITE);
        ImageView winnerSoldier = new ImageView();
        winnerSoldier.setImage(actualwinner);
        winnerpane.add(winner,2,1);
        winnerpane.add(winnerSoldier,2,2);
        winnerfinalStage.setScene(winnerScene);
        winnerfinalStage.show();
        Timeline timeline1 = new Timeline();
        KeyFrame key1 = new KeyFrame(Duration.millis(3000),
                new KeyValue(winnerfinalStage.getScene().getRoot().opacityProperty(), 0));
        timeline1.getKeyFrames().add(key1);
        timeline1.setOnFinished(ae -> winnerfinalStage.close());
        timeline1.play();
    }
    public void fight(ClientSquare startSquare, ClientSquare actualwinner){
        GridPane pane = new GridPane();
        pane.setBackground(new Background(new BackgroundImage(ImageConstants.GRASS, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        Scene scene = new Scene(pane);
        scene.setFill(Color.TRANSPARENT);
        Stage finalStage = new Stage();
        finalStage.initStyle(StageStyle.TRANSPARENT);
        Label winnner = new Label();
        ImageView view = new ImageView();
        ImageView view2 = new ImageView();
        view.setImage(HashTables.getPieceMap().get(actualwinner.getPiece().getPieceSpriteKey()));
        view2.setImage(HashTables.getPieceMap().get(startSquare.getPiece().getPieceSpriteKey()));

        ImageView imageView1 = new ImageView(ImageConstants.FIGHTING);
        imageView1.setFitHeight(80);
        imageView1.setFitWidth(140);
        pane.add(winnner, 2, 1);
        pane.add(view, 1, 2);
        pane.add(view2,3,2);
        pane.add(imageView1,2,2);
        finalStage.setScene(scene);
        finalStage.show();
        Timeline timeline = new Timeline();
        KeyFrame key = new KeyFrame(Duration.millis(3001),
                new KeyValue(finalStage.getScene().getRoot().opacityProperty(), 0));
        timeline.getKeyFrames().add(key);
        Image image = (HashTables.getPieceMap().get(actualwinner.getPiece().getPieceSpriteKey()));
        timeline.setOnFinished(ae -> {
            finalStage.close();
            resolveFight(image);
        });
        timeline.play();
    }

    private void revealAll() {
        // End game, reveal all pieces
        Platform.runLater(() -> {
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    if (Game.getBoard().getSquare(row, col).getPiece() != null && Game.getBoard().getSquare(row, col).getPiece().getPieceColor() != Game.getPlayer().getColor()) {
                        Game.getBoard().getSquare(row, col).getPiecePane().setPiece(HashTables.getPieceMap().get(Game.getBoard().getSquare(row, col).getPiece().getPieceSpriteKey()));
                    }
                }
            }
            PlaySound.stopMusic();
            restartGame();
        });
    }

    private void restartGame(){
        GridPane pane = new GridPane();
        Scene scene = new Scene(pane);
        Stage finalStage = new Stage();

        Label question = new Label();
        question.setText("Do you wish to play another game?");
        question.setMinWidth(75);
        question.setMinHeight(75);

        ImageView buttonYes = new ImageView();
        ImageView buttonNo = new ImageView();
        ImageView winnerImage = new ImageView();
        if(winnerColor.equals(PieceColor.BLUE)){
            winnerImage.setImage(ImageConstants.WINNER_IMAGE_BLUE);
            buttonNo.setFitWidth(100);
            buttonNo.setFitHeight(100);
        }else if (winnerColor.equals(PieceColor.RED)){
            winnerImage.setImage(ImageConstants.WINNER_IMAGE_RED);
            buttonNo.setFitWidth(100);
            buttonNo.setFitHeight(100);
        }
        buttonNo.setImage(ImageConstants.BUTTON_NO);
        buttonNo.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> System.exit(0) );
        buttonNo.setFitWidth(100);
        buttonNo.setFitHeight(100);
        buttonYes.setImage(ImageConstants.BUTTON_YES);
        buttonYes.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                restartApplication();
            } catch (URISyntaxException | IOException ex) {
                LOGGER.severe(MESSAGE+ex.getMessage()+CAUSSE+ ex.getCause());
            }
        });
        buttonYes.setFitWidth(100);
        buttonYes.setFitHeight(100);

        pane.add(question,1,1);
        pane.add(buttonYes,0,2);
        pane.add(buttonNo,2,2);
        pane.add(winnerImage,1,2);

        finalStage.setScene(scene);
        finalStage.show();
    }
    public void restartApplication() throws URISyntaxException, IOException {
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        final File currentJar = new File(Client.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        /* is it a jar file? */
        if(!currentJar.getName().endsWith(".jar"))
            return;

        /* Build command: java -jar application.jar */
        final ArrayList<String> command = new ArrayList<>();
        command.add(javaBin);
        command.add("-jar");
        command.add(currentJar.getPath());

        final ProcessBuilder builder = new ProcessBuilder(command);
        builder.start();
        System.exit(0);
    }
    // Finicky, ill-advised to edit. Resets the opacity, rotation, and piece to null
    // Duplicate "ResetImageVisibility" class was intended to not set piece to null, untested though.

    private class ResetSquareImage implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            synchronized (waitFade) {
                waitFade.notifyAll();
                Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y).getPiecePane().getPiece().setOpacity(1.0);
                Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y).getPiecePane().getPiece().setRotate(0.0);
                Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y).getPiecePane().setPiece(null);

                Game.getBoard().getSquare(Game.getMove().getEnd().x, Game.getMove().getEnd().y).getPiecePane().getPiece().setOpacity(1.0);
                Game.getBoard().getSquare(Game.getMove().getEnd().x, Game.getMove().getEnd().y).getPiecePane().getPiece().setRotate(0.0);
            }
        }
    }

    private void move (int moveX,int moveY){
        shiftX=0;
        shiftY=0;

        if (moveX > 0){
            shiftX = 1;
        } else if (moveX < 0){
            shiftX = -1;
        } else if (moveY > 0){
            shiftY = 1;
        } else if (moveY < 0){
            shiftY = -1;
        }
    }

    private ClientSquare variableStartSquare (){
        return Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y);
    }

    private ClientSquare variableEndSquare (){
        return Game.getBoard().getSquare(Game.getMove().getEnd().x, Game.getMove().getEnd().y);
    }
}

