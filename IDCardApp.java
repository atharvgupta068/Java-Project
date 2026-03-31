import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IDCardApp extends JFrame {

    private JTextField nameField, idField, courseField, dobField, bloodField, addressField;
    private JLabel imagePreviewLabel;
    private JLabel photoStatusLabel;
    
    private BufferedImage currentFront, currentBack;
    private BufferedImage uploadedPhoto = null;
    private CardRenderer renderer;
    private boolean showingFront = true;

    public IDCardApp() {
        EnvLoader env = new EnvLoader(".env");
        Theme.loadColors(env);
        renderer = new CardRenderer(env);

        setTitle("ID Generator - " + env.get("COLLEGE_NAME"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 750);
        setLayout(new BorderLayout(10, 10));

        // Panels
        add(createInputPanel(), BorderLayout.WEST);
        add(createPreviewPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private JPanel createInputPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setPreferredSize(new Dimension(320, 0));
        wrapper.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridLayout(0, 1, 5, 5));

        // Photo Upload Section
        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton uploadBtn = new JButton("Browse Photo...");
        photoStatusLabel = new JLabel("No image selected");
        photoStatusLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        
        uploadBtn.addActionListener(e -> selectPhoto());
        photoPanel.add(uploadBtn);
        photoPanel.add(photoStatusLabel);
        
        form.add(new JLabel("Student Photo:"));
        form.add(photoPanel);

        // Text Fields
        form.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        form.add(nameField);

        form.add(new JLabel("Student ID:"));
        idField = new JTextField();
        form.add(idField);

        form.add(new JLabel("Course:"));
        courseField = new JTextField();
        form.add(courseField);

        form.add(new JLabel("Date of Birth:"));
        dobField = new JTextField();
        form.add(dobField);

        form.add(new JLabel("Blood Group:"));
        bloodField = new JTextField();
        form.add(bloodField);

        form.add(new JLabel("City/Address (Short):"));
        addressField = new JTextField();
        form.add(addressField);

        wrapper.add(form, BorderLayout.NORTH);
        return wrapper;
    }

    private JPanel createPreviewPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.DARK_GRAY);
        
        imagePreviewLabel = new JLabel("Generata Card to Preview");
        imagePreviewLabel.setForeground(Color.WHITE);
        imagePreviewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(imagePreviewLabel);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(new EmptyBorder(10, 0, 10, 0));

        JButton generateBtn = new JButton("Generate Card");
        generateBtn.setBackground(Theme.PRIMARY);
        generateBtn.setForeground(Color.WHITE);
        generateBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        JButton toggleBtn = new JButton("Flip Front/Back");
        JButton saveBtn = new JButton("Save to PNG");

        generateBtn.addActionListener(e -> generateCard());
        toggleBtn.addActionListener(e -> toggleView());
        saveBtn.addActionListener(e -> saveImages());

        panel.add(generateBtn);
        panel.add(toggleBtn);
        panel.add(saveBtn);

        return panel;
    }

    private void selectPhoto() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "jpeg"));
        
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                uploadedPhoto = ImageIO.read(chooser.getSelectedFile());
                photoStatusLabel.setText("Image Loaded");
                photoStatusLabel.setForeground(new Color(0, 100, 0));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to load image");
            }
        }
    }

    private void generateCard() {
        Map<String, String> data = new HashMap<>();
        data.put("Name", getText(nameField, "Jane Doe"));
        data.put("ID", getText(idField, "ST-2024-001"));
        data.put("Course", getText(courseField, "Computer Science"));
        data.put("DOB", getText(dobField, "2002-05-15"));
        data.put("BloodGroup", getText(bloodField, "B+"));
        data.put("Address", getText(addressField, "New York, USA"));

        currentFront = renderer.createFront(data, uploadedPhoto);
        currentBack = renderer.createBack();
        
        updatePreview();
    }

    private String getText(JTextField field, String defaultVal) {
        return field.getText().trim().isEmpty() ? defaultVal : field.getText().trim();
    }

    private void toggleView() {
        showingFront = !showingFront;
        updatePreview();
    }

    private void updatePreview() {
        if (currentFront == null) return;
        
        BufferedImage toShow = showingFront ? currentFront : currentBack;
        Image scaled = toShow.getScaledInstance(400, 600, Image.SCALE_SMOOTH);
        imagePreviewLabel.setIcon(new ImageIcon(scaled));
        imagePreviewLabel.setText("");
    }

    private void saveImages() {
        if (currentFront == null) {
            JOptionPane.showMessageDialog(this, "Generate card first!");
            return;
        }

        try {
            String id = idField.getText().trim();
            if(id.isEmpty()) id = "student";
            
            ImageIO.write(currentFront, "png", new File("ID_" + id + "_Front.png"));
            ImageIO.write(currentBack, "png", new File("ID_" + id + "_Back.png"));

            JOptionPane.showMessageDialog(this, "Files saved as ID_" + id + "_Front.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new IDCardApp().setVisible(true));
    }

}
