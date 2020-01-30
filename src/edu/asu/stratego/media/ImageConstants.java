package edu.asu.stratego.media;

import javafx.scene.image.Image;

public class ImageConstants {
    private ImageConstants() {
    }

    public static final Image stratego_logo = new Image(ImageConstants.class.getResource("images/board/stratego_logo.png").toString());
    // Board Images.
    public static final Image READY_HOVER = new Image(ImageConstants.class.getResource("images/board/ready_hover.png").toString());
    public static final Image READY_IDLE = new Image(ImageConstants.class.getResource("images/board/ready_idle.png").toString());
    public static final Image BORDER = new Image(ImageConstants.class.getResource("images/board/border.png").toString());
    public static final Image BUTTON_NO = new Image(ImageConstants.class.getResource("images/board/buttonNo.png").toString());
    public static final Image BUTTON_YES = new Image(ImageConstants.class.getResource("images/board/buttonYes.png").toString());
    public static final Image WINNER_IMAGE_BLUE = new Image(ImageConstants.class.getResource("images/board/winnerBlue.png").toString());
    public static final Image WINNER_IMAGE_RED = new Image(ImageConstants.class.getResource("images/board/winnerRed.png").toString());
    public static final Image RANDOM_BUTTON = new Image(ImageConstants.class.getResource("images/board/random.png").toString());
    public static final Image HIGHLIGHT_NONE = new Image(ImageConstants.class.getResource("images/board/highlight_none.png").toString());
    public static final Image HIGHLIGHT_VALID = new Image(ImageConstants.class.getResource("images/board/highlight_valid.png").toString());
    public static final Image HIGHLIGHT_INVALID = new Image(ImageConstants.class.getResource("images/board/highlight_invalid.png").toString());
    public static final Image HIGHLIGHT_WHITE = new Image(ImageConstants.class.getResource("images/board/highlight_white.png").toString());
    public static final Image HELP_BUTTON = new Image(ImageConstants.class.getResource("images/help/info.png").toString());
    public static final Image GRASS =new Image(ImageConstants.class.getResource("images/board/grass1.png").toString());
    public static final Image FIGHTING =new Image(ImageConstants.class.getResource("images/board/Fighting.gif").toString());


    public static final Image MOVEARROW_RED = new Image(ImageConstants.class.getResource("images/board/movearrow_red.png").toString());
    public static final Image MOVEARROW_BLUE = new Image(ImageConstants.class.getResource("images/board/movearrow_blue.png").toString());

    public static final Image LAKE_1_1 = new Image(ImageConstants.class.getResource("images/board/lake1_1.png").toString());
    public static final Image LAKE_1_2 = new Image(ImageConstants.class.getResource("images/board/lake1_2.png").toString());
    public static final Image LAKE_1_3 = new Image(ImageConstants.class.getResource("images/board/lake1_3.png").toString());
    public static final Image LAKE_1_4 = new Image(ImageConstants.class.getResource("images/board/lake1_4.png").toString());

    public static final Image LAKE_2_1 = new Image(ImageConstants.class.getResource("images/board/lake2_1.png").toString());
    public static final Image LAKE_2_2 = new Image(ImageConstants.class.getResource("images/board/lake2_2.png").toString());
    public static final Image LAKE_2_3 = new Image(ImageConstants.class.getResource("images/board/lake2_3.png").toString());
    public static final Image LAKE_2_4 = new Image(ImageConstants.class.getResource("images/board/lake2_4.png").toString());

    // Piece Images.
    private static Image red02 = new Image(ImageConstants.class.getResource("images/pieces/red/ExplorerRedRetro.png").toString());
    private static Image red03 = new Image(ImageConstants.class.getResource("images/pieces/red/MinerRedRetro.png").toString());
    private static Image red04 = new Image(ImageConstants.class.getResource("images/pieces/red/SargentRedRetro.png").toString());
    private static Image red05 = new Image(ImageConstants.class.getResource("images/pieces/red/LieutenauntRedRetro.png").toString());
    private static Image red06 = new Image(ImageConstants.class.getResource("images/pieces/red/CaptainRedRetro.png").toString());
    private static Image red07 = new Image(ImageConstants.class.getResource("images/pieces/red/CommanderRedRetro.png").toString());
    private static Image red08 = new Image(ImageConstants.class.getResource("images/pieces/red/ColonelRedRetro.png").toString());
    private static Image red09 = new Image(ImageConstants.class.getResource("images/pieces/red/GeneralRedRetro.png").toString());
    private static Image red10 = new Image(ImageConstants.class.getResource("images/pieces/red/MarshallRedRetro.png").toString());
    private static Image redSpy = new Image(ImageConstants.class.getResource("images/pieces/red/SpyRedRetro.png").toString());
    private static Image redBack = new Image(ImageConstants.class.getResource("images/pieces/red/cardRedRetro.png").toString());
    private static Image redBomb = new Image(ImageConstants.class.getResource("images/pieces/red/BombRedRetro.png").toString());
    private static Image redFlag = new Image(ImageConstants.class.getResource("images/pieces/red/FlagRedRetro.png").toString());

    private static Image blue02 = new Image(ImageConstants.class.getResource("images/pieces/blue/ExplorerBlueRetro.png").toString());
    private static Image blue03 = new Image(ImageConstants.class.getResource("images/pieces/blue/MinerBlueRetro.png").toString());
    private static Image blue04 = new Image(ImageConstants.class.getResource("images/pieces/blue/SargentBlueRetro.png").toString());
    private static Image blue05 = new Image(ImageConstants.class.getResource("images/pieces/blue/LieutenauntBlueRetro.png").toString());
    private static Image blue06 = new Image(ImageConstants.class.getResource("images/pieces/blue/CaptainBlueRetro.png").toString());
    private static Image blue07 = new Image(ImageConstants.class.getResource("images/pieces/blue/CommanderBlueRetro.png").toString());
    private static Image blue08 = new Image(ImageConstants.class.getResource("images/pieces/blue/ColonelBlueRetro.png").toString());
    private static Image blue09 = new Image(ImageConstants.class.getResource("images/pieces/blue/GeneralBlueRetro.png").toString());
    private static Image blue10 = new Image(ImageConstants.class.getResource("images/pieces/blue/MarshallBlueRetro.png").toString());
    private static Image blueSpy = new Image(ImageConstants.class.getResource("images/pieces/blue/SpyBlueRetro.png").toString());
    private static Image blueBack = new Image(ImageConstants.class.getResource("images/pieces/blue/cardBlueRetro.png").toString());
    private static Image blueBomb = new Image(ImageConstants.class.getResource("images/pieces/blue/BombBlueRetro.png").toString());
    private static Image blueFlag = new Image(ImageConstants.class.getResource("images/pieces/blue/FlagBlueRetro.png").toString());

    public static void updateImages(String style) {
        red02 = new Image(ImageConstants.class.getResource("images/pieces/red/ExplorerRed" + style + ".png").toString());
        red03 = new Image(ImageConstants.class.getResource("images/pieces/red/MinerRed" + style + ".png").toString());
        red04 = new Image(ImageConstants.class.getResource("images/pieces/red/SargentRed" + style + ".png").toString());
        red05 = new Image(ImageConstants.class.getResource("images/pieces/red/LieutenauntRed" + style + ".png").toString());
        red06 = new Image(ImageConstants.class.getResource("images/pieces/red/CaptainRed" + style + ".png").toString());
        red07 = new Image(ImageConstants.class.getResource("images/pieces/red/CommanderRed" + style + ".png").toString());
        red08 = new Image(ImageConstants.class.getResource("images/pieces/red/ColonelRed" + style + ".png").toString());
        red09 = new Image(ImageConstants.class.getResource("images/pieces/red/GeneralRed" + style + ".png").toString());
        red10 = new Image(ImageConstants.class.getResource("images/pieces/red/MarshallRed" + style + ".png").toString());
        redSpy = new Image(ImageConstants.class.getResource("images/pieces/red/SpyRed" + style + ".png").toString());
        redBack = new Image(ImageConstants.class.getResource("images/pieces/red/cardRed" + style + ".png").toString());
        redBomb = new Image(ImageConstants.class.getResource("images/pieces/red/BombRed" + style + ".png").toString());
        redFlag = new Image(ImageConstants.class.getResource("images/pieces/red/FlagRed" + style + ".png").toString());

        blue02 = new Image(ImageConstants.class.getResource("images/pieces/blue/ExplorerBlue" + style + ".png").toString());
        blue03 = new Image(ImageConstants.class.getResource("images/pieces/blue/MinerBlue" + style + ".png").toString());
        blue04 = new Image(ImageConstants.class.getResource("images/pieces/blue/SargentBlue" + style + ".png").toString());
        blue05 = new Image(ImageConstants.class.getResource("images/pieces/blue/LieutenauntBlue" + style + ".png").toString());
        blue06 = new Image(ImageConstants.class.getResource("images/pieces/blue/CaptainBlue" + style + ".png").toString());
        blue07 = new Image(ImageConstants.class.getResource("images/pieces/blue/CommanderBlue" + style + ".png").toString());
        blue08 = new Image(ImageConstants.class.getResource("images/pieces/blue/ColonelBlue" + style + ".png").toString());
        blue09 = new Image(ImageConstants.class.getResource("images/pieces/blue/GeneralBlue" + style + ".png").toString());
        blue10 = new Image(ImageConstants.class.getResource("images/pieces/blue/MarshallBlue" + style + ".png").toString());
        blueSpy = new Image(ImageConstants.class.getResource("images/pieces/blue/SpyBlue" + style + ".png").toString());
        blueBack = new Image(ImageConstants.class.getResource("images/pieces/blue/cardBlue" + style + ".png").toString());
        blueBomb = new Image(ImageConstants.class.getResource("images/pieces/blue/BombBlue" + style + ".png").toString());
        blueFlag = new Image(ImageConstants.class.getResource("images/pieces/blue/FlagBlue" + style + ".png").toString());
    }

    public static Image getRed02() {
        return red02;
    }

    public static Image getRed03() {
        return red03;
    }

    public static Image getRed04() {
        return red04;
    }

    public static Image getRed05() {
        return red05;
    }

    public static Image getRed06() {
        return red06;
    }

    public static Image getRed07() {
        return red07;
    }

    public static Image getRed08() {
        return red08;
    }

    public static Image getRed09() {
        return red09;
    }

    public static Image getRed10() {
        return red10;
    }

    public static Image getRedSpy() {
        return redSpy;
    }

    public static Image getRedBack() {
        return redBack;
    }

    public static Image getRedBomb() {
        return redBomb;
    }

    public static Image getRedFlag() {
        return redFlag;
    }

    public static Image getBlue02() {
        return blue02;
    }

    public static Image getBlue03() {
        return blue03;
    }

    public static Image getBlue04() {
        return blue04;
    }

    public static Image getBlue05() {
        return blue05;
    }

    public static Image getBlue06() {
        return blue06;
    }

    public static Image getBlue07() {
        return blue07;
    }

    public static Image getBlue08() {
        return blue08;
    }

    public static Image getBlue09() {
        return blue09;
    }

    public static Image getBlue10() {
        return blue10;
    }

    public static Image getBlueSpy() {
        return blueSpy;
    }

    public static Image getBlueBack() {
        return blueBack;
    }

    public static Image getBlueBomb() {
        return blueBomb;
    }

    public static Image getBlueFlag() {
        return blueFlag;
    }
}
