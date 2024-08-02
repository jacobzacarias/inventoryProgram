package zacarias.desktopSchedule.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import zacarias.desktopSchedule.DAO.CountriesDao;
import zacarias.desktopSchedule.DAO.CustomerDao;
import zacarias.desktopSchedule.DAO.FirstLevelDivisionsDao;
import zacarias.desktopSchedule.model.Countries;
import zacarias.desktopSchedule.model.Customers;
import zacarias.desktopSchedule.model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Holds the methods for adding new customers.
 */
public class AddCustomerController implements Initializable {
    @FXML private TextField addCustomerIdField;
    @FXML private TextField addCustomerNameField;
    @FXML private TextField addCustomerAddressField;
    @FXML private TextField addCustomerPhoneField;
    @FXML private TextField addCustomerZipField;
    @FXML private ComboBox<Countries> addCustomerCountryCombo;
    @FXML private ComboBox<FirstLevelDivisions> addCustomerStateCombo;
    @FXML private Button addCustomerSaveButton;
    @FXML private Button addCustomerCancelButton;

    /**
     * Initializes the controller. The lambda expression filters divisions based on the specific country that it goes to.
     *
     * @param url The location used to find the relative paths for the root object.
     * @param resourceBundle The resources that are being used to localize the root object.
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Countries> countries = CountriesDao.getAllCountries();
            addCustomerCountryCombo.setItems(countries);
            addCustomerCountryCombo.setPromptText("Country...");
            addCustomerStateCombo.setPromptText("State/Division...");

            // Lambda expression for event handling with selecting a country
            addCustomerCountryCombo.setOnAction(event -> {
                Countries selectedCountry = addCustomerCountryCombo.getValue();
                ObservableList<FirstLevelDivisions> divisions = FirstLevelDivisionsDao.getDivisionsByCountryId(selectedCountry.getCountryId());
                addCustomerStateCombo.setItems(divisions);
                }
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add customer save.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML void addCustomerSave(ActionEvent event) throws IOException {
        String name = addCustomerNameField.getText().trim();
        String address = addCustomerAddressField.getText().trim();
        String zip = addCustomerZipField.getText().trim();
        String phone = addCustomerPhoneField.getText().trim();
        String country = addCustomerCountryCombo.getSelectionModel().toString();
        FirstLevelDivisions division = addCustomerStateCombo.getValue();

        if (name.isEmpty() || address.isEmpty() || zip.isEmpty() || phone.isEmpty() || country.isBlank()) {
            showAlert("Data must be input for all fields.");
            return;
        }
        if (name.length() > 50 || address.length() > 100 || zip.length() > 50 || phone.length() > 50) {
            showAlert("Input length exceeds maximum allowed characters.");
            return;
        }
        Customers createCustomer = new Customers(-1, name, address, zip, phone, division.getDivisionId(), country);
        try {
            CustomerDao.insertCustomer(createCustomer);
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
    @FXML private void addCustomerCancel(ActionEvent event) throws IOException {
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