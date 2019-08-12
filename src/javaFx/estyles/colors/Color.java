package javaFx.estyles.colors;

import javafx.scene.paint.Paint;

/**
 *
 * @author Luan Rodrigues
 */
public class Color {
    private final static String primaryColor = "1A86DB";
    private final static String grayColor = "4d4d4d";
    private final static String greenColor = "28A745";
    private final static String dangerColor = "DC3545";
    private final static String darkColor = "4f5153";
    
    public static Paint primary = Paint.valueOf(primaryColor);
    public static Paint success = Paint.valueOf(greenColor);
    public static Paint danger = Paint.valueOf(dangerColor);
    public static Paint dark = Paint.valueOf(darkColor);
    public static Paint gray = Paint.valueOf(grayColor);
}
