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
import zacarias.desktopSchedule.model.Countries;
import zacarias.desktopSchedule.model.Customers;
import zacarias.desktopSchedule.model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The type Modify customer controller.
 */
public class ModifyCustomerController implements Initializable {
    @FXML private TextField modifyCustomerId;
    @FXML private TextField modifyCustomerNameField;
    @FXML private TextField modifyCustomerAddressField;
    @FXML private TextField modifyCustomerPhoneField;
    @FXML private TextField modifyCustomerZipField;
    @FXML private ComboBox<Countries> modifyCustomerCountryCombo;
    @FXML private ComboBox<FirstLevelDivisions> modifyCustomerStateCombo;
    @FXML private Button modifyCustomerSaveButton;
    @FXML private Button modifyCustomerCancelButton;
    @FXML private Customers modifyCustomer;
    /**
     * The Customer id.
     */
    int customerId;

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            modifyCustomer = FormController.getSelectedCustomer();
            customerId = modifyCustomer.getCustomerId();
            String name = modifyCustomer.getCustomerName();
            String address = modifyCustomer.getAddress();
            String zip = modifyCustomer.getZip();
            String phone = modifyCustomer.getPhone();

            modifyCustomerId.setText(String.valueOf(customerId));
            modifyCustomerNameField.setText(name);
            modifyCustomerAddressField.setText(address);
            modifyCustomerZipField.setText(zip);
            modifyCustomerPhoneField.setText(phone);

            ObservableList<Countries> countries = CountriesDao.getAllCountries();
            modifyCustomerCountryCombo.setItems(countries);
            modifyCustomerCountryCombo.setVisibleRowCount(3);
            modifyCustomerCountryCombo.setPromptText("Country");
            modifyCustomerCountryCombo.setItems(CountriesDao.getAllCountries());
            Countries country = CountriesDao.getCountryByDivisionId(modifyCustomer.getDivisionId()); //Gets the country
            modifyCustomerCountryCombo.setValue(country);

            ObservableList<FirstLevelDivisions> divisions = FirstLevelDivisionsDao.getDivisionsByCountryId(country.getCountryId());
            modifyCustomerStateCombo.setItems(divisions);
            modifyCustomerStateCombo.setVisibleRowCount(10);
            modifyCustomerStateCombo.setPromptText("State/Division");
            modifyCustomerStateCombo.setItems(FirstLevelDivisionsDao.loadFirstLevelDivisions());
            FirstLevelDivisions division = FirstLevelDivisionsDao.getDivisionById(modifyCustomer.getDivisionId());
            modifyCustomerStateCombo.setValue(division);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Modify customer save.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML void modifyCustomerSave(ActionEvent event) throws IOException {
        int customerId = modifyCustomer.getCustomerId();
        String name = modifyCustomerNameField.getText().trim();
        String address = modifyCustomerAddressField.getText().trim();
        String zip = modifyCustomerZipField.getText().trim();
        String phone = modifyCustomerPhoneField.getText().trim();
        String country = modifyCustomerCountryCombo.getSelectionModel().toString();
        FirstLevelDivisions division = modifyCustomerStateCombo.getValue();

        if(name.isEmpty() || address.isEmpty() || zip.isEmpty() || phone.isEmpty() || country.isBlank() || modifyCustomerStateCombo.getValue() == null){
            showAlert("Data must be entered for all fields.");
            return;
        }
        if(modifyCustomerNameField.getText().trim().length() > 50){
            showAlert("Names must be less than 50 characters long.");
            return;
        }
        else{
            name = modifyCustomerNameField.getText().trim();
        }
        if(modifyCustomerAddressField.getText().trim().length() > 100){
            showAlert("Addresses must be less than 100 characters long.");
            return;
        }
        else{
            address = modifyCustomerAddressField.getText().trim();
        }
        if(modifyCustomerZipField.getText().trim().length() > 50){
            showAlert("Zip Codes must be less than 50 characters long.");
            return;
        }
        else{
            zip = modifyCustomerZipField.getText().trim();
        }
        if(modifyCustomerPhoneField.getText().trim().length() > 50){
            showAlert("Phone Numbers must be less than 50 characters long.");
            return;
        }
        else{
            phone = modifyCustomerPhoneField.getText().trim();
        }
        FirstLevelDivisions selectedDivision = modifyCustomerStateCombo.getSelectionModel().getSelectedItem();

        int divisionId = selectedDivision.getDivisionId();
        CustomerDao customer = new CustomerDao();
        try {
            CustomerDao.updateCustomer(new Customers(customerId, name, address, zip, phone, division.getDivisionId(), country));
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
    @FXML private void modifyCustomerCancel(ActionEvent event) throws IOException {
        if (!(modifyCustomerNameField.getText().isEmpty() || modifyCustomerAddressField.getText().isEmpty() ||
                modifyCustomerZipField.getText().isEmpty() || modifyCustomerPhoneField.getText().isEmpty() ||
                modifyCustomerCountryCombo.getValue() == null || modifyCustomerStateCombo.getValue() == null)) {
            showConfirmDialog("Data must be entered for all fields.", "Confirm Cancel?");
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
     */
    @FXML private void handleCountrySelection(ActionEvent event) {
        // Get the selected country
        Countries selectedCountry = modifyCustomerCountryCombo.getSelectionModel().getSelectedItem();
        // Check if a country is selected
        if (selectedCountry != null) {
            // Get the divisions associated with the selected country
            ObservableList<FirstLevelDivisions> divisions = FirstLevelDivisionsDao.getDivisionsByCountryId(selectedCountry.getCountryId());
            // Set the divisions in the state combo box
            modifyCustomerStateCombo.setItems(divisions);
            // Select the first division in the list
            if (!divisions.isEmpty()) {
                modifyCustomerStateCombo.getSelectionModel().selectFirst();
            }
        } else {
            // Clear the state combo box if no country is selected
            modifyCustomerStateCombo.getItems().clear();
        }
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
