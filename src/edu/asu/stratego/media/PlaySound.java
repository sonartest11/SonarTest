package edu.asu.stratego.media;

import javafx.application.Platform;

import edu.asu.stratego.util.HashTables;
import javafx.scene.media.AudioClip;


import static javafx.scene.media.AudioClip.INDEFINITE;


public class PlaySound {

    private static final AudioClip MUSIC = HashTables.getSoundMap().get("music");

    private PlaySound(){}

    public static void playMusic() {
        Platform.runLater(() -> {
            MUSIC.setCycleCount(INDEFINITE);
            MUSIC.play();
        });
    }
    public static void stopMusic() {
        MUSIC.stop();
    }

}
