package zacarias.desktopSchedule.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import zacarias.desktopSchedule.DAO.*;
import zacarias.desktopSchedule.helper.TimeUtility;
import zacarias.desktopSchedule.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The type Modify appointment controller.
 */
public class ModifyAppointmentController implements Initializable {
    @FXML private TextField modifyAppointmentId;
    @FXML private Button modifyAppointmentSave;
    @FXML private Button modifyAppointmentCancel;
    @FXML private TextField modifyAppointmentTitleField;
    @FXML private TextField modifyAppointmentTypeField;
    @FXML private TextField modifyAppointmentDescriptionField;
    @FXML private TextField modifyAppointmentLocationField;
    @FXML private DatePicker modifyAppointmentStartDate;
    @FXML private ComboBox<LocalTime> modifyAppointmentStartCombo;
    @FXML private DatePicker modifyAppointmentEndDate;
    @FXML private ComboBox<LocalTime> modifyAppointmentEndCombo;
    @FXML private ComboBox<Customers> modifyAppointmentCustomerCombo;
    @FXML private ComboBox<Users> modifyAppointmentUserIdCombo;
    @FXML private ComboBox<Contacts> modifyAppointmentContactCombo;
    @FXML private Appointments modifyAppointment;
    /**
     * The Appointment id.
     */
    int appointmentId;

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            modifyAppointment = FormController.getSelectedAppointment();
            appointmentId = modifyAppointment.getAppointmentId();
            String title = modifyAppointment.getTitle();
            String type = modifyAppointment.getType();
            String description = modifyAppointment.getDescription();
            String location = modifyAppointment.getLocation();
            // Set start and end date/time
            LocalDateTime start = modifyAppointment.getStart();
            LocalDateTime end = modifyAppointment.getEnd();
            // Set start and end time combo boxes
            modifyAppointmentStartCombo.setItems(TimeUtility.getStartTimes());
            modifyAppointmentEndCombo.setItems(TimeUtility.getEndTimes());
            modifyAppointmentId.setText(String.valueOf(appointmentId));
            modifyAppointmentTitleField.setText(title);
            modifyAppointmentTypeField.setText(type);
            modifyAppointmentDescriptionField.setText(description);
            modifyAppointmentLocationField.setText(location);
            modifyAppointmentStartDate.setValue(start.toLocalDate());
            modifyAppointmentEndDate.setValue(end.toLocalDate());
            // Set selected start and end times
            modifyAppointmentStartCombo.setValue(start.toLocalTime());
            modifyAppointmentEndCombo.setValue(end.toLocalTime());
            // Set customer, user, and contact combo boxes
            ObservableList<Customers> customers = CustomerDao.getAllCustomers();
            modifyAppointmentCustomerCombo.setItems(customers);
            modifyAppointmentCustomerCombo.setPromptText("Customer");
            Customers customer = CustomerDao.getCustomerById(modifyAppointment.getCustomerId());
            modifyAppointmentCustomerCombo.setValue(customer);
            ObservableList<Users> users = UsersDao.getUsers();
            modifyAppointmentUserIdCombo.setItems(users);
            modifyAppointmentUserIdCombo.setPromptText("User ID");
            Users user = UsersDao.getUserById(modifyAppointment.getUserId());
            modifyAppointmentUserIdCombo.setValue(user);
            ObservableList<Contacts> contacts = ContactsDao.getAllContacts();
            modifyAppointmentContactCombo.setItems(contacts);
            modifyAppointmentContactCombo.setPromptText("Contact");
            Contacts contact = ContactsDao.getContactById(modifyAppointment.getContactId());
            modifyAppointmentContactCombo.setValue(contact);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML private void modifyAppointmentSave(ActionEvent event) throws IOException {
        String title = modifyAppointmentTypeField.getText();
        String description = modifyAppointmentDescriptionField.getText();
        String location = modifyAppointmentLocationField.getText();
        String type = modifyAppointmentTypeField.getText();
        LocalDate startDate = modifyAppointmentStartDate.getValue();
        LocalTime startTime = modifyAppointmentStartCombo.getValue();
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDate endDate = modifyAppointmentEndDate.getValue();
        LocalTime endTime = modifyAppointmentEndCombo.getValue();
        LocalDateTime end = LocalDateTime.of(endDate, endTime);
        int customerId = modifyAppointmentCustomerCombo.getValue().getCustomerId();
        int userId = modifyAppointmentUserIdCombo.getValue().getUserId();
        int contactId = modifyAppointmentContactCombo.getValue().getContactId();
        if(modifyAppointmentId.getText().isEmpty() ||
                modifyAppointmentTitleField.getText().isEmpty() ||
                modifyAppointmentDescriptionField.getText().isEmpty() ||
                modifyAppointmentLocationField.getText().isEmpty() ||
                modifyAppointmentTypeField.getText().isEmpty() ||
                modifyAppointmentStartDate.getValue() == null ||
                modifyAppointmentStartCombo.getSelectionModel().isEmpty() ||
                modifyAppointmentEndDate.getValue() == null ||
                modifyAppointmentEndCombo.getSelectionModel().isEmpty() ||
                modifyAppointmentContactCombo.getValue() == null ||
                modifyAppointmentCustomerCombo.getValue() == null ||
                modifyAppointmentUserIdCombo.getValue() == null){
            showAlert("Data must be entered for all fields.");
            return;
        }

        if(modifyAppointmentTitleField.getText().trim().length() > 50){
            showAlert("Enter a title that is less than 50 characters long.");
            return;
        }
        else{
            title = modifyAppointmentTitleField.getText().trim();
        }

        if(modifyAppointmentDescriptionField.getText().trim().length() > 50){
            showAlert("Enter a description that is less than 50 characters long.");
            return;
        }
        else{
            description = modifyAppointmentDescriptionField.getText().trim();
        }

        if(modifyAppointmentLocationField.getText().trim().length() > 50){
            showAlert("Enter a location that is less than 50 characters long.");
            return;
        }
        else{
            location = modifyAppointmentLocationField.getText().trim();
        }

        if(modifyAppointmentTypeField.getText().trim().length() > 50){
            showAlert("Enter an appointment type that is less than 50 characters long.");
            return;
        }
        else{
            type = modifyAppointmentTypeField.getText().trim();
        }

        if(endDate.isBefore(startDate)||startDate.isAfter(endDate)){
            showAlert("Appointment start must be before the appointment end.");
            return;
        }

        //Sees if start and end date time for EST is on the same day
        if (TimeUtility.checkSameDay(start, end)) {
            LocalTime localStart = TimeUtility.getLocalStart();
            LocalTime localEnd = TimeUtility.getLocalEnd();
            showAlert("The appointment has to be scheduled within business hours: 08:00-22:00 EST; " +
                    localStart.format(DateTimeFormatter.ofPattern("HH:mm")) + "-" + localEnd.format(DateTimeFormatter.ofPattern("HH:mm")) + " Local.");
            return;
        }

        //Check that start time is before the end time
        if(startDate.isEqual(endDate)){
            if(startTime.isAfter(endTime)||endTime.isBefore(startTime)){
                showAlert("Appointment start must come before the appointment end.");
                return;
            }
        }

        //If time is outside of business hours then notify the user of the hours that appointments need to be in
        if(TimeUtility.outsideLocalHours(start,end)){
            LocalTime localStart = TimeUtility.getLocalStart();
            LocalTime localEnd = TimeUtility.getLocalEnd();
            showAlert("Appointment times have to be within business hours: 08:00-22:00 EST; "+
                    localStart.format(DateTimeFormatter.ofPattern("HH:mm"))+"-"+localEnd.format(DateTimeFormatter.ofPattern("HH:mm"))+" Local.");
            return;
        }

        //If there are conflicting appointments for customers, notify the user
        AppointmentDao sameDay = new AppointmentDao();
        ObservableList<Appointments> conflictingAppointments = AppointmentDao.getConflictingAppointments(customerId, appointmentId, start, end);
        if (!conflictingAppointments.isEmpty()) {
            showAlert("Selected appointment times conflict with already existing appointments for the selected customer.");
            return;
        }

        AppointmentDao createAppointment = new AppointmentDao();
        try {
            createAppointment.updateAppointment(new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/zacarias/desktopSchedule/formScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Form Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML private void modifyAppointmentCancel(ActionEvent event) throws IOException {
        if (!(modifyAppointmentTitleField.getText().isEmpty() ||
                modifyAppointmentDescriptionField.getText().isEmpty() ||
                modifyAppointmentLocationField.getText().isEmpty() ||
                modifyAppointmentTypeField.getText().isEmpty() ||
                modifyAppointmentStartDate.getValue() == null ||
                modifyAppointmentStartCombo.getSelectionModel().isEmpty() ||
                modifyAppointmentEndDate.getValue() == null ||
                modifyAppointmentEndCombo.getSelectionModel().isEmpty() ||
                modifyAppointmentContactCombo.getValue() == null ||
                modifyAppointmentCustomerCombo.getValue() == null ||
                modifyAppointmentUserIdCombo.getValue() == null)){
            showConfirmDialog("Cancel without modifying this appointment?", "Confirm Cancel?");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/zacarias/desktopSchedule/formScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Form Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param message
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Show confirm dialog boolean.
     *
     * @param message the message
     * @param title   the title
     * @return boolean
     */
    @FXML
    public boolean showConfirmDialog(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
