package zacarias.desktopSchedule.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import zacarias.desktopSchedule.DAO.*;
import zacarias.desktopSchedule.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The type Form controller.
 */
public class FormController implements Initializable {
    @FXML
    private TableView customerTableView;
    @FXML
    private TableColumn customerId;
    @FXML
    private TableColumn customerName;
    @FXML
    private TableColumn customerAddress;
    @FXML
    private TableColumn customerZip;
    @FXML
    private TableColumn customerPhone;
    @FXML
    private TableColumn customerDivisionId;
    @FXML
    private TableColumn customerCountry;
    @FXML
    private Button customerAddButton;
    @FXML
    private Button customerModifyButton;
    @FXML
    private Button customerDeleteButton;
    @FXML
    private TableView appointmentTableView;
    @FXML
    private TableColumn appointmentId;
    @FXML
    private TableColumn appointmentTitle;
    @FXML
    private TableColumn appointmentDescription;
    @FXML
    private TableColumn appointmentLocation;
    @FXML
    private TableColumn appointmentType;
    @FXML
    private TableColumn appointmentContactId;
    @FXML
    private TableColumn appointmentUserId;
    @FXML
    private TableColumn appointmentStart;
    @FXML
    private TableColumn appointmentEnd;
    @FXML
    private TableColumn appointmentCustomerId;
    @FXML
    private RadioButton radioAllButton;
    @FXML
    private ToggleGroup appointmentFilter;
    @FXML
    private RadioButton radioMonthButton;
    @FXML
    private RadioButton radioWeekButton;
    @FXML
    private Button appointmentAddButton;
    @FXML
    private Button appointmentModifyButton;
    @FXML
    private Button appointmentDeleteButton;
    @FXML
    private TableView reportAppointmentTableView;
    @FXML
    private TableColumn<Total, String> reportAppointmentMonth;
    @FXML
    private TableColumn<Total, String> reportAppointmentType;
    @FXML
    private TableColumn<Total, Integer> reportAppointmentCount;
    @FXML
    private TableView reportContactTableView;
    @FXML
    private TableColumn reportContactAppointmentId;
    @FXML
    private TableColumn reportContactTitle;
    @FXML
    private TableColumn reportContactType;
    @FXML
    private TableColumn reportContactDescription;
    @FXML
    private TableColumn reportContactStart;
    @FXML
    private TableColumn reportContactEnd;
    @FXML
    private TableColumn reportContactCustomerId;
    @FXML
    private ComboBox<Contacts> reportContactSelect;
    @FXML
    private TableView<Total> reportCustomerTableView;
    @FXML
    private TableColumn<Total, String> reportCustomerCountry;
    @FXML
    private TableColumn<Total, String> reportCustomerDivision;
    @FXML
    private TableColumn<Total, Integer> reportCustomerCount;
    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    private static Customers selectedCustomer;
    private static Appointments selectedAppointment;

    /**
     * Gets selected customer.
     *
     * @return selected customer
     */
    public static Customers getSelectedCustomer() {
        return selectedCustomer;
    }

    /**
     * Gets selected appointment.
     *
     * @return selected appointment
     */
    public static Appointments getSelectedAppointment() {
        return selectedAppointment;
    }

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Customers> allCustomerRecords = CustomerDao.getAllCustomers();
            customerTableView.setItems(allCustomerRecords);

            // Set cell value factories for customer table columns
            customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            customerZip.setCellValueFactory(new PropertyValueFactory<>("zip"));
            customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            customerDivisionId.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
            customerCountry.setCellValueFactory(new PropertyValueFactory<>("country"));

            customerTableView.setPlaceholder(new Label("There are no customers in the database."));

            ObservableList<Appointments> allAppointmentRecords = AppointmentDao.getAllAppointments();
            appointmentTableView.setItems(allAppointmentRecords);

            // Set cell value factories for appointment table columns
            appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            appointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
            appointmentContactId.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            appointmentUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
            appointmentStart.setCellValueFactory(new PropertyValueFactory<>("start"));
            appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
            appointmentCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

            appointmentTableView.setPlaceholder(new Label("There are no appointments in the database."));

            reportAppointmentTableView.setPlaceholder(new Label("There are no appointments in the database."));
            reportContactTableView.setPlaceholder(new Label("Select a contact to view their appointments."));

            ObservableList<Total> appointmentsByMountainRecords = TotalDao.getTotalAppointmentReports();
            reportAppointmentTableView.setItems(appointmentsByMountainRecords);

            reportCustomerTableView.setItems(TotalDao.getTotalCustomerReports());
            reportAppointmentMonth.setCellValueFactory(new PropertyValueFactory<>("keyOne"));
            reportAppointmentType.setCellValueFactory(new PropertyValueFactory<>("keyTwo"));
            reportAppointmentCount.setCellValueFactory(new PropertyValueFactory<>("count"));

            reportAppointmentTableView.setItems(appointmentsByMountainRecords); //Use a for loop to check for select filtering
            // so contact ID = to selected
            //Set to filtered observable list, contacts works because of getAllContacts()

            ObservableList<Contacts> allContacts = ContactsDao.getAllContacts();
            reportContactSelect.setItems(allContacts);
            reportContactSelect.setVisibleRowCount(5);
            reportContactSelect.setPromptText("Select contact...");
            reportAppointmentTableView.setItems(TotalDao.getTotalAppointmentReports());
            reportContactSelect.setItems(ContactsDao.getAllContacts());

            reportCustomerTableView.setItems(TotalDao.getTotalCustomerReports());
            reportCustomerCount.setCellValueFactory(new PropertyValueFactory<>("count"));
            reportCustomerCountry.setCellValueFactory(new PropertyValueFactory<>("keyOne"));
            reportCustomerDivision.setCellValueFactory(new PropertyValueFactory<>("keyTwo"));
        } catch (SQLException e) {
            e.printStackTrace(); //SQLException is caught and handled in the try block by printing the stack trace,
            // showing an error message, and continuing to run
            showAlert("An error occurred while initializing the form.");
        } //Handles exceptions locally without propagating them up the call stack, allows for better control over
        // error handling, and stops the application from crashing suddenly
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void customerAdd(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/zacarias/desktopSchedule/addCustomerScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void customerModify(ActionEvent event) throws IOException {
        //Gets the selected customer
        selectedCustomer = (Customers) customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert("Select a customer in order to modify it.");
        } else {
            //Switches scenes when button is clicked
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/zacarias/desktopSchedule/modifyCustomerScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Modify Product");
            stage.show();
        }
    }

    /**
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    private void customerDelete(ActionEvent event) throws SQLException {
        Customers selectedCustomer = (Customers) customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert("Select a customer in order to delete it.");
        }
        if (selectedCustomer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setContentText("Deleting this customer will delete all associated appointments. Are you sure?");
            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.isPresent() && answer.get() == ButtonType.OK) {
                AppointmentDao.deleteAppointmentsByCustomer(selectedCustomer.getCustomerId());
                CustomerDao.deleteCustomer(selectedCustomer);
                ObservableList<Customers> customer = CustomerDao.getAllCustomers();
                customerTableView.setItems(customer);
            } else {
                int customerId = selectedCustomer.getCustomerId();
                String customerName = selectedCustomer.getCustomerName();

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm");
                alert.setContentText("Are you sure you want to delete customer ID: " + customerId + "; " + customerName + "?");
                Optional<ButtonType> confirmation = alert.showAndWait();
                if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                    AppointmentDao appointmentToDelete = new AppointmentDao();
                    if (!appointmentToDelete.appointmentsByCustomer(customerId).isEmpty()) {
                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirm");
                        alert.setContentText("Customer ID " + customerId + "; " + customerName + " has associated appointments, are you still sure you want to delete the customer?");
                        Optional<ButtonType> finalConfirmation = alert.showAndWait();
                        if (finalConfirmation.isPresent() && finalConfirmation.get() == ButtonType.OK) {
                            appointmentToDelete.deleteAppointmentsByCustomer(selectedCustomer.getCustomerId());
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    private void appointmentAllFilter(ActionEvent event) throws SQLException {
        ObservableList<Appointments> allAppointmentRecords = AppointmentDao.getAllAppointments();

        appointmentTableView.setItems(allAppointmentRecords);
        appointmentTableView.setPlaceholder(new Label("There are no appointments."));
    }

    /**
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    private void appointmentMonthFilter(ActionEvent event) throws SQLException {
        ObservableList<Appointments> allAppointmentRecords = AppointmentDao.getMonthAppointmentTable();

        appointmentTableView.setItems(allAppointmentRecords);
        appointmentTableView.setPlaceholder(new Label("There are no appointments in the upcoming month."));
    }

    /**
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    private void appointmentWeekFilter(ActionEvent event) throws SQLException {
        ObservableList<Appointments> allAppointmentRecords = AppointmentDao.getWeekAppointmentTable();

        appointmentTableView.setItems(allAppointmentRecords);
        appointmentTableView.setPlaceholder(new Label("There are no appointments in the upcoming week."));
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void appointmentAdd(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/zacarias/desktopSchedule/addAppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void appointmentModify(ActionEvent event) throws IOException {
        //Gets the selected appointment
        selectedAppointment = (Appointments) appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            showAlert("Select an appointment in order to modify it.");
        } else {
            //Switches scenes when button is clicked
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/zacarias/desktopSchedule/modifyAppointmentScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Modify Appointment");
            stage.show();
        }
    }

    /**
     *
     * @param event
     * @throws SQLException
     */
    @FXML private void appointmentDelete(ActionEvent event) throws SQLException {
        Appointments selectedAppointment = (Appointments) appointmentTableView.getSelectionModel().getSelectedItem();
        if (AppointmentDao.getAllAppointments().isEmpty()) {
            showAlert("There are no appointments to be deleted.");
        }
        if (selectedAppointment == null) {
            showAlert("Select an appointment in order to delete it.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setContentText("Delete this appointment?");
            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.isPresent() && answer.get() == ButtonType.OK) {
                int appointmentId = selectedAppointment.getAppointmentId();
                String appointmentType = selectedAppointment.getType();
                String appointmentTitle = selectedAppointment.getTitle();

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm");
                alert.setContentText("Are you sure you want to delete the appointment ID:" + appointmentId + "; " + appointmentType + ", " + appointmentTitle + "?");
                Optional<ButtonType> confirmation = alert.showAndWait();
                if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                    AppointmentDao.deleteAppointment(selectedAppointment);
                    ObservableList<Appointments> allAppointmentRecords = AppointmentDao.getAllAppointments();
                    appointmentTableView.setItems(allAppointmentRecords);
                }
            }
        }
    }

    /**
     *
     * @param event
     * @throws SQLException
     */
    @FXML private void reportContactPicked(ActionEvent event) throws SQLException {
        Contacts selectedContact = reportContactSelect.getValue();
        int contactId = selectedContact.getContactId();

        ObservableList<Appointments> allAppointments = AppointmentDao.getAllAppointments();
        ObservableList<Appointments> appointmentsByContact = FXCollections.observableArrayList();
        for (Appointments appointment: allAppointments) {
                    System.out.println("Contact IDs"+appointment.getContactId()+" selected contact"+contactId);
            if (appointment.getContactId()==contactId) {
                appointmentsByContact.add(appointment); //Gets appointments by matching the contact ID
            }
        }
        reportContactTableView.setPlaceholder(new Label("Select a contact to view their appointments."));
        reportContactTableView.setItems(appointmentsByContact);

        //Set table view to each of the columns
        reportContactAppointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        reportContactTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        reportContactType.setCellValueFactory(new PropertyValueFactory<>("type"));
        reportContactDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        reportContactStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        reportContactEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        reportContactCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
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