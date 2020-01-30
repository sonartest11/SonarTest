package edu.asu.stratego.util;

import java.util.HashMap;
import java.util.Map;

import edu.asu.stratego.media.SoundConstants;
import javafx.scene.image.Image;
import edu.asu.stratego.media.ImageConstants;
import javafx.scene.media.AudioClip;

public class HashTables{
    private HashTables(){}
    // Piece Image Map (String -> Image).
    protected static final Map<String, Image> PIECE_MAP = new HashMap<>(24);
    static {
        // RED Pieces.
        PIECE_MAP.put("RED_02", ImageConstants.getRed02());
        PIECE_MAP.put("RED_03", ImageConstants.getRed03());
        PIECE_MAP.put("RED_04", ImageConstants.getRed04());
        PIECE_MAP.put("RED_05", ImageConstants.getRed05());
        PIECE_MAP.put("RED_06", ImageConstants.getRed06());
        PIECE_MAP.put("RED_07", ImageConstants.getRed07());
        PIECE_MAP.put("RED_08", ImageConstants.getRed08());
        PIECE_MAP.put("RED_09", ImageConstants.getRed09());
        PIECE_MAP.put("RED_10", ImageConstants.getRed10());
        PIECE_MAP.put("RED_SPY", ImageConstants.getRedSpy());
        PIECE_MAP.put("RED_BOMB", ImageConstants.getRedBomb());
        PIECE_MAP.put("RED_FLAG", ImageConstants.getRedFlag());
        PIECE_MAP.put("RED_BACK", ImageConstants.getRedBack());

        // Blue Pieces.
        PIECE_MAP.put("BLUE_02", ImageConstants.getBlue02());
        PIECE_MAP.put("BLUE_03", ImageConstants.getBlue03());
        PIECE_MAP.put("BLUE_04", ImageConstants.getBlue04());
        PIECE_MAP.put("BLUE_05", ImageConstants.getBlue05());
        PIECE_MAP.put("BLUE_06", ImageConstants.getBlue06());
        PIECE_MAP.put("BLUE_07", ImageConstants.getBlue07());
        PIECE_MAP.put("BLUE_08", ImageConstants.getBlue08());
        PIECE_MAP.put("BLUE_09", ImageConstants.getBlue09());
        PIECE_MAP.put("BLUE_10", ImageConstants.getBlue10());
        PIECE_MAP.put("BLUE_SPY", ImageConstants.getBlueSpy());
        PIECE_MAP.put("BLUE_BOMB", ImageConstants.getBlueBomb());
        PIECE_MAP.put("BLUE_FLAG", ImageConstants.getBlueFlag());
        PIECE_MAP.put("BLUE_BACK", ImageConstants.getBlueBack());


    }
    protected static final Map<String, AudioClip> SOUND_MAP = new HashMap<>(1);
    static {
        SOUND_MAP.put("music", SoundConstants.MUSIC);
    }

    public static Map<String, Image> getPieceMap() {
        return PIECE_MAP;
    }

    public static Map<String, AudioClip> getSoundMap() {
        return SOUND_MAP;
    }
}
