import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.Map;

public class CardRenderer {

    private final EnvLoader env;

    public CardRenderer(EnvLoader env) {
        this.env = env;
    }

    public BufferedImage createFront(Map<String, String> studentData, BufferedImage photo) {
        BufferedImage image = new BufferedImage(Theme.CARD_WIDTH, Theme.CARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = setupGraphics(image);

        // 1. Header Background
        g2d.setColor(Theme.PRIMARY);
        g2d.fillRect(0, 0, Theme.CARD_WIDTH, 110);

        // 2. College Name
        g2d.setColor(Color.WHITE);
        drawCenteredText(g2d, env.get("COLLEGE_NAME"), Theme.FONT_HEADER, 45);
        
        g2d.setFont(Theme.FONT_SMALL);
        drawCenteredText(g2d, "IDENTITY CARD", Theme.FONT_SMALL, 70);

        // 3. Photo Area
        int photoSize = 120;
        int photoX = (Theme.CARD_WIDTH - photoSize) / 2;
        int photoY = 85;
        
        // Draw Border
        g2d.setColor(Color.WHITE);
        g2d.fillOval(photoX - 4, photoY - 4, photoSize + 8, photoSize + 8); 

        // Draw Photo (or Placeholder)
        if (photo != null) {
            // Clip to circle and draw uploaded image
            Shape originalClip = g2d.getClip();
            g2d.setClip(new Ellipse2D.Float(photoX, photoY, photoSize, photoSize));
            g2d.drawImage(photo, photoX, photoY, photoSize, photoSize, null);
            g2d.setClip(originalClip);
        } else {
            // Default Placeholder
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillOval(photoX, photoY, photoSize, photoSize);
            g2d.setColor(Color.GRAY);
            g2d.fillOval(photoX + 30, photoY + 20, 60, 60); 
            g2d.fillArc(photoX + 10, photoY + 70, 100, 80, 0, 180); 
        }

        // 4. Student Details
        int startY = 230;
        int gap = 32;
        
        // Name (Large)
        g2d.setColor(Theme.PRIMARY);
        drawCenteredText(g2d, studentData.get("Name").toUpperCase(), Theme.FONT_TITLE, startY);

        // Divider
        g2d.setColor(Theme.ACCENT);
        g2d.fillRect(100, startY + 10, 200, 3);
        
        // Info Fields
        int currentY = startY + 45;
        drawField(g2d, "ID NO", studentData.get("ID"), currentY);
        currentY += gap;
        
        drawField(g2d, "COURSE", studentData.get("Course"), currentY);
        currentY += gap;
        
        drawField(g2d, "DOB", studentData.get("DOB"), currentY);
        currentY += gap;
        
        drawField(g2d, "BLOOD GRP", studentData.get("BloodGroup"), currentY);
        currentY += gap;

        drawField(g2d, "ADDRESS", studentData.get("Address"), currentY);
        currentY += gap;

        // Valid Date
        g2d.setColor(Theme.PRIMARY);
        g2d.setFont(Theme.FONT_SMALL);
        drawCenteredText(g2d, "VALID UP TO: " + LocalDate.now().plusYears(3).toString(), Theme.FONT_SMALL, currentY + 15);

        // 5. Footer (Principal Signature)
        g2d.setColor(Theme.PRIMARY);
        g2d.fillRect(0, Theme.CARD_HEIGHT - 50, Theme.CARD_WIDTH, 50);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Segoe Script", Font.ITALIC, 14));
        g2d.drawString(env.get("PRINCIPAL_NAME"), Theme.CARD_WIDTH - 150, Theme.CARD_HEIGHT - 20);
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 9));
        g2d.drawString("Principal Signature", Theme.CARD_WIDTH - 140, Theme.CARD_HEIGHT - 8);

        g2d.dispose();
        return image;
    }

    public BufferedImage createBack() {
        BufferedImage image = new BufferedImage(Theme.CARD_WIDTH, Theme.CARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = setupGraphics(image);

        // Header
        g2d.setColor(Theme.PRIMARY);
        g2d.fillRect(0, 0, Theme.CARD_WIDTH, 60);
        g2d.setColor(Color.WHITE);
        drawCenteredText(g2d, "TERMS & CONDITIONS", Theme.FONT_TITLE, 40);

        // Return Info
        g2d.setColor(Theme.TEXT);
        int y = 100;
        
        g2d.setFont(Theme.FONT_LABEL);
        g2d.drawString("IF FOUND, PLEASE RETURN TO:", 30, y);
        y += 25;
        
        g2d.setFont(Theme.FONT_VALUE);
        g2d.drawString(env.get("COLLEGE_NAME"), 30, y);
        y += 25;
        g2d.drawString(env.get("COLLEGE_ADDRESS"), 30, y);
        y += 25;
        g2d.drawString("Ph: " + env.get("CONTACT_PHONE"), 30, y);

        // Instructions
        y += 60;
        g2d.setColor(Theme.PRIMARY);
        g2d.fillRect(30, y, Theme.CARD_WIDTH - 60, 2);
        y += 30;
        
        g2d.setColor(Theme.TEXT);
        g2d.setFont(Theme.FONT_VALUE);
        String[] rules = {
            "1. This card is the property of the institution.",
            "2. Transfer of this card is strictly prohibited.",
            "3. Report loss of card immediately.",
            "4. Must be worn at all times on campus."
        };

        for (String rule : rules) {
            g2d.drawString(rule, 30, y);
            y += 30;
        }

        // Mock Barcode
        y = Theme.CARD_HEIGHT - 80;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(50, y, 300, 40);
        g2d.setColor(Color.WHITE);
        drawCenteredText(g2d, env.get("WEBSITE"), Theme.FONT_SMALL, y + 55);

        g2d.dispose();
        return image;
    }

    // --- Helpers ---

    private Graphics2D setupGraphics(BufferedImage image) {
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(Theme.BACKGROUND);
        g2d.fillRect(0, 0, Theme.CARD_WIDTH, Theme.CARD_HEIGHT);
        return g2d;
    }

    private void drawField(Graphics2D g2d, String label, String value, int y) {
        g2d.setColor(Theme.TEXT);
        g2d.setFont(Theme.FONT_LABEL);
        g2d.drawString(label, 40, y);
        
        g2d.setColor(Theme.PRIMARY);
        g2d.setFont(Theme.FONT_VALUE);
        
        // Simple truncation if value is too long
        if (value.length() > 25) value = value.substring(0, 22) + "...";
        g2d.drawString(value, 140, y);
    }

    private void drawCenteredText(Graphics2D g2d, String text, Font font, int y) {
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics(font);
        int x = (Theme.CARD_WIDTH - metrics.stringWidth(text)) / 2;
        g2d.drawString(text, x, y);
    }

}
