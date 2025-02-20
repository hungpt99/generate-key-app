package org.example;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGeneratorApp {
    private static JTextField keyField;
    private static JComboBox<String> keySizeSelector;
    private static JComboBox<String> algorithmSelector;
    private static boolean darkMode = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SecretKeyGeneratorApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Secret Key Generator");
        frame.setSize(550, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("🔑 Secret Key Generator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        keyField = new JTextField(40);
        keyField.setEditable(false);
        keyField.setHorizontalAlignment(JTextField.CENTER);
        keyField.setFont(new Font("Monospaced", Font.BOLD, 14));

        keySizeSelector = new JComboBox<>(new String[]{"128-bit", "192-bit", "256-bit"});
        keySizeSelector.setFont(new Font("Arial", Font.PLAIN, 14));

        algorithmSelector = new JComboBox<>(new String[]{"HmacSHA256", "HmacSHA512", "AES"});
        algorithmSelector.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton generateButton = new JButton("🎲 Generate Key");
        JButton copyButton = new JButton("📋 Copy to Clipboard");
        JButton saveButton = new JButton("💾 Save to File");
        JButton themeButton = new JButton("🌙 Toggle Dark Mode");

        // Event Listeners
        generateButton.addActionListener(e -> keyField.setText(generateSecretKey()));
        copyButton.addActionListener(e -> copyToClipboard(keyField.getText()));
        saveButton.addActionListener(e -> saveToFile(keyField.getText()));
        themeButton.addActionListener(e -> toggleTheme(frame, panel));

        panel.add(titleLabel);
        panel.add(algorithmSelector);
        panel.add(keySizeSelector);
        panel.add(keyField);
        panel.add(generateButton);
        panel.add(copyButton);
        panel.add(saveButton);
        panel.add(themeButton);

        keyField.setText(generateSecretKey());

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static String generateSecretKey() {
        String algorithm = (String) algorithmSelector.getSelectedItem();
        int keySize = switch (keySizeSelector.getSelectedIndex()) {
            case 0 -> 128;
            case 1 -> 192;
            default -> 256;
        };

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
            keyGen.init(keySize, new SecureRandom());
            SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            return "❌ Error: Algorithm not supported!";
        }
    }

    private static void copyToClipboard(String text) {
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No key generated!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
        JOptionPane.showMessageDialog(null, "✅ Key copied to clipboard!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void saveToFile(String text) {
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No key generated!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fileName = JOptionPane.showInputDialog(null, "Enter file name:", "Save Secret Key", JOptionPane.PLAIN_MESSAGE);
        if (fileName == null || fileName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "File name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (FileWriter writer = new FileWriter(fileName + ".txt")) {
            writer.write(text);
            JOptionPane.showMessageDialog(null, "✅ Key saved to " + fileName + ".txt!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void toggleTheme(JFrame frame, JPanel panel) {
        darkMode = !darkMode;
        Color bgColor = darkMode ? Color.DARK_GRAY : Color.WHITE;
        Color textColor = darkMode ? Color.WHITE : Color.BLACK;

        frame.getContentPane().setBackground(bgColor);
        panel.setBackground(bgColor);
        for (Component comp : panel.getComponents()) {
            comp.setBackground(bgColor);
            comp.setForeground(textColor);
        }
        frame.repaint();
    }
}
