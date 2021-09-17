package Engine.Cards;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public enum CardType {

    HA(Suite.HEARTS, 13, "AH"),
    H2(Suite.HEARTS, 1, "2H"),
    H3(Suite.HEARTS, 2, "3H"),
    H4(Suite.HEARTS, 3, "4H"),
    H5(Suite.HEARTS, 4, "5H"),
    H6(Suite.HEARTS, 5, "6H"),
    H7(Suite.HEARTS, 6, "7H"),
    H8(Suite.HEARTS, 7, "8H"),
    H9(Suite.HEARTS, 8, "9H"),
    H10(Suite.HEARTS,9, "10H"),
    HJ(Suite.HEARTS, 10, "JH"),
    HQ(Suite.HEARTS, 11, "QH"),
    HK(Suite.HEARTS, 12, "KH"),

    SA(Suite.SPADES, 13, "AS"),
    S2(Suite.SPADES, 1, "2S"),
    S3(Suite.SPADES, 2, "3S"),
    S4(Suite.SPADES, 3, "4S"),
    S5(Suite.SPADES, 4, "5S"),
    S6(Suite.SPADES, 5, "6S"),
    S7(Suite.SPADES, 6, "7S"),
    S8(Suite.SPADES, 7, "8S"),
    S9(Suite.SPADES, 8, "9S"),
    S10(Suite.SPADES,9, "10S"),
    SJ(Suite.SPADES, 10, "JS"),
    SQ(Suite.SPADES, 11, "QS"),
    SK(Suite.SPADES, 12, "KS"),

    DA(Suite.DIAMONDS, 13, "AD"),
    D2(Suite.DIAMONDS, 1, "2D"),
    D3(Suite.DIAMONDS, 2, "3D"),
    D4(Suite.DIAMONDS, 3, "4D"),
    D5(Suite.DIAMONDS, 4, "5D"),
    D6(Suite.DIAMONDS, 5, "6D"),
    D7(Suite.DIAMONDS, 6, "7D"),
    D8(Suite.DIAMONDS, 7, "8D"),
    D9(Suite.DIAMONDS, 8, "9D"),
    D10(Suite.DIAMONDS,9, "10D"),
    DJ(Suite.DIAMONDS, 10, "JD"),
    DQ(Suite.DIAMONDS, 11, "QD"),
    DK(Suite.DIAMONDS, 12, "KD"),

    CA(Suite.CLUBS, 13, "AC"),
    C2(Suite.CLUBS, 1, "2C"),
    C3(Suite.CLUBS, 2, "3C"),
    C4(Suite.CLUBS, 3, "4C"),
    C5(Suite.CLUBS, 4, "5C"),
    C6(Suite.CLUBS, 5, "6C"),
    C7(Suite.CLUBS, 6, "7C"),
    C8(Suite.CLUBS, 7, "8C"),
    C9(Suite.CLUBS, 8, "9C"),
    C10(Suite.CLUBS,9, "10C"),
    CJ(Suite.CLUBS, 10, "JC"),
    CQ(Suite.CLUBS, 11, "QC"),
    CK(Suite.CLUBS, 12, "KC");

    public static final int IMAGE_WIDTH = 691;
    public static final int IMAGE_HEIGHT = 1056;

    public static final int CARD_WIDTH = 103;

    public static final float IMAGE_SCALE = (float)CARD_WIDTH / (float)IMAGE_WIDTH;
    public static final float CARD_ASPECT = (float)IMAGE_WIDTH / (float)IMAGE_HEIGHT;

    public static final int CARD_HEIGHT = (int)(CARD_WIDTH / CARD_ASPECT);

    private Suite suite;
    private int number;
    private BufferedImage texture;
    private String texName;

    CardType(Suite suite, int number, String texName)
    {
        this.suite = suite;
        this.number = number;
        this.texName = texName;

        try {
            texture = ImageIO.read(new File("./res/" + texName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Suite getSuite() {
        return suite;
    }

    public int getNumber() {
        return number;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public String getTexName() {
        return texName;
    }
}

