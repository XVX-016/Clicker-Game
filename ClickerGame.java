import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class ClickerGame extends JFrame {
    private int clickCount = 0;
    private JLabel clickLabel;
    private static JLabel imageLabel;
    private static ImageIcon imageIcon;
    private BufferedImage originalImage;

    public ClickerGame() {
        setTitle("Clicking Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load the image from a file or resource
        try {
            // Load the original image
            originalImage = ImageIO.read(new File("C:/Users/tanmm/OneDrive/Pictures/Camera Roll/1306304.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Image not found!");
        }

        // Create an ImageIcon from the resized image
        imageIcon = new ImageIcon(getScaledImage(originalImage, 800, 600)); // Example resize to 400x300
        imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        // Create a label to display the click count
        clickLabel = new JLabel("Score: 0");
        clickLabel.setHorizontalAlignment(JLabel.RIGHT);
        clickLabel.setVerticalAlignment(JLabel.BOTTOM);
        clickLabel.setFont(new Font("Arial", Font.BOLD, 20));
        clickLabel.setForeground(Color.BLACK);

        // Add mouse listener to the image label to increment clicks
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickCount++;
                clickLabel.setText("Score: " + clickCount);
                playSound("AUDIO_PATH"); // Play sound on click
                // Optionally change the image after clicking
                if (clickCount % 5 == 0) {
                    changeImage();  // Update the image every 5 clicks, for example
                }
            }
        });

        // Set layout and add components
        setLayout(new BorderLayout());
        add(imageLabel, BorderLayout.CENTER);
        add(clickLabel, BorderLayout.SOUTH);
    }

    // Method to resize the image while maintaining aspect ratio
    private Image getScaledImage(BufferedImage srcImg, int width, int height) {
        Image scaledImage = srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return scaledImage;
    }

    private void changeImage() {
        // Optionally load a new image after every 5 clicks (or any condition you like)
        try {
            // Load a new image if required
            BufferedImage newImage = ImageIO.read(new File( IMAGE_PATH));
            imageIcon.setImage(getScaledImage(newImage, 800, 600)); // Resize new image
            imageLabel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to play sound
    private void playSound(String soundFile) {
        System.out.println("Attempting to play sound from: " + soundFile);  // Debug line
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFile).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error playing sound!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClickerGame game = new ClickerGame();
            game.setVisible(true);
        });
    }
}