import java.awt.Color;
import java.awt.Font;

public class Theme {
    public static final int CARD_WIDTH = 400;
    public static final int CARD_HEIGHT = 600; // Vertical ID Card
    
    // Fonts
    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_LABEL = new Font("SansSerif", Font.BOLD, 12);
    public static final Font FONT_VALUE = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FONT_SMALL = new Font("SansSerif", Font.PLAIN, 10);
    
    // Colors
    public static Color PRIMARY;
    public static Color ACCENT;
    public static Color TEXT;
    public static final Color BACKGROUND = Color.WHITE;

    public static void loadColors(EnvLoader env) {
        PRIMARY =  Color.decode(env.get("PRIMARY_COLOR", "#1a237e"));
        ACCENT = Color.decode(env.get("ACCENT_COLOR", "#ffd700"));
        TEXT = Color.decode(env.get("TEXT_COLOR", "#333333"));
    }
}
