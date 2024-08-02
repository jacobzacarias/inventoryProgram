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
import zacarias.desktopSchedule.DAO.AppointmentDao;
import zacarias.desktopSchedule.DAO.ContactsDao;
import zacarias.desktopSchedule.DAO.CustomerDao;
import zacarias.desktopSchedule.DAO.UsersDao;
import zacarias.desktopSchedule.helper.TimeUtility;
import zacarias.desktopSchedule.model.Appointments;
import zacarias.desktopSchedule.model.Contacts;
import zacarias.desktopSchedule.model.Customers;
import zacarias.desktopSchedule.model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * The type Add appointment controller.
 */
public class AddAppointmentController implements Initializable {
    @FXML private Button addAppointmentSaveButton;
    @FXML private Button addAppointmentCancelButton;
    @FXML private TextField addAppointmentTitleField;
    @FXML private TextField addAppointmentTypeField;
    @FXML private TextField addAppointmentDescriptionField;
    @FXML private TextField addAppointmentLocationField;
    @FXML private DatePicker addAppointmentStartDate;
    @FXML private ComboBox<LocalTime> addAppointmentStartCombo;
    @FXML private DatePicker addAppointmentEndDate;
    @FXML private ComboBox<LocalTime> addAppointmentEndCombo;
    @FXML private ComboBox<Customers> addAppointmentCustomerCombo;
    @FXML private ComboBox<Contacts> addAppointmentContactCombo;
    @FXML private ComboBox<Users> addAppointmentUserIdCombo;

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAppointmentStartCombo.setItems(TimeUtility.getStartTimes());
        addAppointmentEndCombo.setItems(TimeUtility.getEndTimes());
        try {
            addAppointmentContactCombo.setItems(ContactsDao.getAllContacts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            addAppointmentCustomerCombo.setItems(CustomerDao.getAllCustomers()); //Make call directly to DAO same for users and contacts
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            addAppointmentUserIdCombo.setItems(UsersDao.getUsers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add appointment save.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML void addAppointmentSave(ActionEvent event) throws IOException {
        String title = addAppointmentTitleField.getText();
        String description = addAppointmentDescriptionField.getText();
        String location = addAppointmentLocationField.getText();
        String type = addAppointmentTypeField.getText();
        LocalDate startDate = addAppointmentStartDate.getValue();
        LocalTime startTime = addAppointmentStartCombo.getValue();
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDate endDate = addAppointmentEndDate.getValue();
        LocalTime endTime = addAppointmentEndCombo.getValue();
        LocalDateTime end = LocalDateTime.of(endDate, endTime);
        int customerId = addAppointmentCustomerCombo.getValue().getCustomerId();
        int userId = addAppointmentUserIdCombo.getValue().getUserId();
        int contactId = addAppointmentContactCombo.getValue().getContactId();

        //Get all values, then VALIDATE statements, after passing the validation IF (empty) ELSE
        if (title.isBlank() || description.isBlank() ||
                location.isBlank() || type.isBlank() ||
                addAppointmentStartDate.getValue() == null ||
                addAppointmentEndDate.getValue() == null ||
                addAppointmentContactCombo.getSelectionModel().isEmpty() ||
                addAppointmentCustomerCombo.getSelectionModel().isEmpty() ||
                addAppointmentUserIdCombo.getSelectionModel().isEmpty()) {
            showAlert("Data must be entered for all fields.");
        }
        if (title.length() > 50 || description.length() > 50 ||
                location.length() > 50 || type.length() > 50) {
            showAlert("Input length exceeds maximum allowed characters.");
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

        AppointmentDao sameDay = new AppointmentDao();
        ObservableList<Appointments> conflictingAppointments = AppointmentDao.getConflictingAppointments(customerId, -1, start, end);
        if (!conflictingAppointments.isEmpty()) {
            showAlert("Selected appointment times conflict with already existing appointments for the selected customer.");
            return;
        }
        //ELSE
        //Create appointment object
        //call INSERT method from DAO then pass the object into it, the insert method saves the appointment
        Appointments createAppointment = new Appointments(-1, title, description, location, type, start, end, customerId, userId, contactId);
        try {
            AppointmentDao.insertAppointment(createAppointment);
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
     * Add appointment cancel.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void addAppointmentCancel(ActionEvent event) throws IOException {
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
}