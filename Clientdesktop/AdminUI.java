package Clientdesktop;

import javax.swing.*;

import org.json.JSONObject;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdminUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public AdminUI() {
        setTitle("Admin Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    // Create the JSON object for loginadmin data
                    JSONObject loginData = new JSONObject();
                    loginData.put("username", username);
                    loginData.put("password", password);

                    // Create the URL for loginadmin endpoint
                    URL url = new URL("http://localhost:7000/admin");

                    // Create the HttpURLConnection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    // Send the loginadmin data
                    OutputStream os = connection.getOutputStream();
                    os.write(loginData.toString().getBytes());
                    os.flush();

                    // Check the response code
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        JOptionPane.showMessageDialog(null, "Login Berhasil!");

                        // Open the main menu
                        openMainMenu();
                    } else {
                        JOptionPane.showMessageDialog(null, "Login Gagal. Response code: " + responseCode);
                    }

                    connection.disconnect();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error occurred while connecting to the JSON server.");
                    ex.printStackTrace();
                }
            }
        });
    }

    private void openMainMenu() {
        dispose();
        // Create the main menu frame
        JFrame mainMenuFrame = new JFrame("Main Menu");
        mainMenuFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainMenuFrame.setSize(400, 300);
        mainMenuFrame.setLocationRelativeTo(null);
        mainMenuFrame.setLayout(new GridLayout(6, 1));

        // Create the menu buttons
        JButton ListCandidatesButton = new JButton("List Candidates");//list kendaraan
        //JButton EditCandidatesButton = new JButton("Edit Candidates");//edit kendaraan
        JButton listVotingButton = new JButton("List Voting");//list boking
        JButton VotingButton = new JButton("Voting");//Booking
        JButton cancelVotingButton = new JButton("Cancel Voting");//cancel booking
        JButton logoutButton = new JButton("Logout");

        // Add the buttons to the main menu frame
        mainMenuFrame.add(ListCandidatesButton);
        //mainMenuFrame.add(EditCandidatesButton);
        mainMenuFrame.add(listVotingButton);
        mainMenuFrame.add(VotingButton);
        mainMenuFrame.add(cancelVotingButton);
        mainMenuFrame.add(logoutButton);

        // List Kendaraan button action listener
        ListCandidatesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the action for the List Kendaraan button
                ListCandidatesUI ListCandidates = new ListCandidatesUI();
                ListCandidates.setVisible(true);
            }
        });

        // List Booking button action listener
        listVotingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the action for the List Booking button
                ListVotingUI ListVoting = new ListVotingUI();
                ListVoting.setVisible(true);
            }
        });

        // Booking button action listener
        VotingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the action for the Voting button
                // mainMenuFrame.dispose();
                VotingUI Voting = new VotingUI();
                Voting.setVisible(true);
            }
        });

        // Cancel Booking button action listener
        cancelVotingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the action for the Cancel Booking button
                CancelVotingUI cancelbook = new CancelVotingUI();
                cancelbook.setVisible(true);
            }
        });

        // Logout Button
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainMenuFrame.dispose();
                new LoginAdmin().setVisible(true);
            }
        });

        // Display the main menu frame
        mainMenuFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);
            }
        });
    }
}
