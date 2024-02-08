package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The mainFormController class is used as the Controller for mainFormScreen.fxml.
 * <p>
 * <b>Compatible feature suitable to the application that would extend functionality to the next version:</b>
 * I think a compatible feature that could be added in the next version of the program would be to allow for Orders to be created.
 * Similar to how Products can have associated Parts, when an Order is created it could have associated Parts and Products.
 * It makes sense to me that a system that is used for Inventory management could extend into an Orders system.
 * <p>
 * The functionality to extend into an Orders feature would include the ability to associate Parts and Products, with a quantity for each in the Order.
 * The user could associate a business or buyer with the order.
 * It could calculate a total cost for the order by taking the Price for each Part and multiplying it by the set quantity, the same would apply to Products in the order.
 * This Orders feature would also not allow the quantity to exceed the current Inventory Level for a selected Part or Product.
 * With this, it could remove that quantity from the Inventory Level of the Part or Product that has been added to the Order once it is confirmed.
 * </p>
 * @author Jacob Zacarias
 */
public class formController implements Initializable {

    @FXML
    private AnchorPane mainForm;
    @FXML
    private TextField partSearchField;
    @FXML
    private Button partAddButton;
    @FXML
    private Button partModifyButton;
    @FXML
    private Button partDeleteButton;
    @FXML
    private TextField productSearchField;
    @FXML
    private Button productAddButton;
    @FXML
    private Button productModifyButton;
    @FXML
    private Button productDeleteButton;
    @FXML
    private Button exitButton;

    //TableView/TableColumn declarations
    @FXML
    private TableView partTableView;
    @FXML
    private TableColumn partID;
    @FXML
    private TableColumn partName;
    @FXML
    private TableColumn partInvLevel;
    @FXML
    private TableColumn partCost;
    @FXML
    private TableView productTableView;
    @FXML
    private TableColumn productID;
    @FXML
    private TableColumn productName;
    @FXML
    private TableColumn productInvLevel;
    @FXML
    private TableColumn productCost;

    public formController(Part modifySelectedProduct) {
    }

    public void modifyProductFormButton(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void partSearchTextF(KeyEvent keyEvent) {
    }

    public void clearPartSearch(MouseEvent mouseEvent) {
    }

    public void addPartFormButton(MouseEvent mouseEvent) {
    }
}

