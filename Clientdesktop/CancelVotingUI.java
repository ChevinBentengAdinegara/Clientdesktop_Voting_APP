package Clientdesktop;

import javax.swing.*;

import org.json.JSONObject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CancelVotingUI extends JFrame {
    private JTextField usernameField;
    private JTextField votingIdField;

    public CancelVotingUI() {
        setTitle("Cancel voting");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        JPanel buttonPanel = new JPanel();

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel votingIdLabel = new JLabel("voting ID:");
        votingIdField = new JTextField();
        JButton cancelButton = new JButton("Cancel voting");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(votingIdLabel);
        panel.add(votingIdField);
        panel.add(new JLabel());
        panel.add(cancelButton);

        add(panel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            dispose(); // Close the CancelVotingUI window
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String votingId = votingIdField.getText();

                try {
                    // Create the JSON object for cancelvoting data
                    JSONObject cancelData = new JSONObject();
                    cancelData.put("username", username);
                    cancelData.put("votingid", votingId);

                    // Create the URL for cancelvoting endpoint
                    URL url = new URL("http://localhost:7000/cancelvoting");

                    // Create the HttpURLConnection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    // Send the cancelvoting data
                    OutputStream os = connection.getOutputStream();
                    os.write(cancelData.toString().getBytes());
                    os.flush();

                    // Check the response code
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        JOptionPane.showMessageDialog(null, "Berhasil menghapus voting!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Gagal menghapus voting. Response code: " + responseCode);
                    }

                    connection.disconnect();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error occurred while connecting to the JSON server.");
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Clientdesktop.CancelVotingUI().setVisible(true);
            }
        });
    }
}
