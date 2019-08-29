package javaFx.styles.colors;

import javafx.scene.paint.Paint;

/**
 *
 * @author Luan Rodrigues
 */
public class Color {
    private final static String primaryColor = "1A86DB";
    private final static String grayColor = "4d4d4d";
    private final static String greenColor = "28A745";
    private final static String dangerColor = "b71c1c";
    private final static String darkColor = "000000";
    private final static String lighColor = "ffffff";
    
    public static Paint PRIMARY = Paint.valueOf(primaryColor);
    public static Paint SUCCESS = Paint.valueOf(greenColor);
    public static Paint DANGER = Paint.valueOf(dangerColor);
    public static Paint DARK = Paint.valueOf(darkColor);
    public static Paint GRAY = Paint.valueOf(grayColor);
    public static Paint LIGHT = Paint.valueOf(lighColor);
}
