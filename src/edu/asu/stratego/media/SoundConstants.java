package edu.asu.stratego.media;

import javafx.scene.media.AudioClip;

public class SoundConstants {

    private SoundConstants(){}

    public static final AudioClip MUSIC = new AudioClip(SoundConstants.class.getResource("sound/music.wav").toString());

}
