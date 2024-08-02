package zacarias.desktopSchedule.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import zacarias.desktopSchedule.DAO.AppointmentDao;
import zacarias.desktopSchedule.DAO.UsersDao;
import zacarias.desktopSchedule.model.Appointments;
import zacarias.desktopSchedule.model.Users;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.SQLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The type Login controller.
 */
public class LoginController implements Initializable {
    /**
     * The Username field.
     */
    public TextField usernameField;
    /**
     * The Password field.
     */
    public PasswordField passwordField;
    /**
     * The Location text field.
     */
    public TextField locationTextField;
    /**
     * The Login button.
     */
    public Button loginButton;
    /**
     * The Cancel button.
     */
    public Button cancelButton;
    /**
     * The Location label.
     */
    public Label locationLabel;
    /**
     * The Username label.
     */
    public Label usernameLabel;
    /**
     * The Password label.
     */
    public Label passwordLabel;
    /**
     * The Timezone text.
     */
    public Label timezoneText;
    /**
     * The Login field.
     */
    public Label loginField;

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ZoneId zoneId = ZoneId.systemDefault();
            locationTextField.setText(zoneId.getId());
            ResourceBundle bundle = ResourceBundle.getBundle("zacarias.desktopSchedule/login", Locale.getDefault());
            usernameLabel.setText(bundle.getString("username"));
            passwordLabel.setText(bundle.getString("password"));
            loginButton.setText(bundle.getString("loginButton"));
            loginField.setText(bundle.getString("login.title"));
            cancelButton.setText(bundle.getString("cancelButton"));
            timezoneText.setText(bundle.getString("timezone"));
        } catch (MissingResourceException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param actionEvent
     */
    @FXML
    private void loginButton(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        try {
            Users validUser = UsersDao.validateCredentials(username, password);
            if (validUser != null) {
                try {
                    Appointments upcomingAppointment = AppointmentDao.getUpcomingAppointments(validUser);
                    if (upcomingAppointment != null) {
                        ResourceBundle bundle = ResourceBundle.getBundle("zacarias.desktopSchedule/login", Locale.getDefault());
                        LocalDateTime appointmentDateTime = upcomingAppointment.getStart();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        String formattedDateTime = appointmentDateTime.format(formatter);
                        showAlert (bundle.getString("upcomingAppointment.valid") + " - " +
                                (bundle.getString("appointment")) + upcomingAppointment.getAppointmentId() + " " +
                                (bundle.getString("dateTime")) + formattedDateTime);
                    } else {
                        ResourceBundle bundle = ResourceBundle.getBundle("zacarias.desktopSchedule/login", Locale.getDefault());
                        showAlert (bundle.getString("upcomingAppointment.invalid"));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                loginAttempt(username, true);
                Locale.setDefault(Locale.ENGLISH);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/zacarias/desktopSchedule/formScreen.fxml"));
                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setTitle("Form Screen");
                stage.setScene(scene);
                stage.show();
            } else {
                ResourceBundle bundle = ResourceBundle.getBundle("zacarias.desktopSchedule/login", Locale.getDefault());
                loginAttempt(username, false);
                showAlert (bundle.getString("login.error.invalid.credentials"));
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param username
     * @param success
     *
     * Tracks user activity by recording login attempts in a file named login_activity.txt.
     * Code logs each login attempt along with the date, time, and if the login was successful.
     */
    private void loginAttempt(String username, boolean success) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/login_activity.txt", true))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
            String formattedTimestamp = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).format(formatter);
            String status = success ? "Successful" : "Failed";
            String logMessage = String.format("[%s] User: %s, Status: %s%n", formattedTimestamp, username, status);
            writer.write(logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cancel button.
     *
     * @param event the event
     */
    @FXML
    public void cancelButton(ActionEvent event) {
        ResourceBundle bundle = ResourceBundle.getBundle("zacarias.desktopSchedule/login", Locale.getDefault());
        showAlert(bundle.getString("exit"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     *
     * @param message
     */
    private void showAlert(String message) {
        ResourceBundle bundle = ResourceBundle.getBundle("zacarias.desktopSchedule/login", Locale.getDefault());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("login.error.title"));
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText(bundle.getString("accept"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}