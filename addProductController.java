package zacarias.c482project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import zacarias.c482project.model.Inventory;
import zacarias.c482project.model.Part;

import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {
    public TextField addProductIDTextF;
    public TextField addProductNameTextF;
    public TextField addProductInvTextF;
    public TextField addProductPriceTextF;
    public TextField addProductMaxTextF;
    public TextField addProductMinTextF;
    public TextField addProductSearchField;
    public TableView allPartsTableView;
    public TableColumn allPartID;
    public TableColumn allPartName;
    public TableColumn allPartInv;
    public TableColumn allPartCost;
    public Button addProductAssociateButton;
    public TableView associatedPartsTableView;
    public TableColumn associatedPartID;
    public TableColumn associatedPartName;
    public TableColumn associatedPartInv;
    public TableColumn associatedPartCost;
    public Button addProductRemoveButton;
    public Button addProductSaveButton;
    public Button addProductCancelButton;
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allPartsTableView.setItems(Inventory.getAllParts());
        associatedPartsTableView.setItems(associatedParts);

        allPartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        allPartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        allPartInv.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        allPartCost.setCellValueFactory(new PropertyValueFactory<>("Price"));
        allPartsTableView.setPlaceholder(new Label("There are no parts in the system."));

        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        associatedPartInv.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        associatedPartCost.setCellValueFactory(new PropertyValueFactory<>("Price"));
        associatedPartsTableView.setPlaceholder(new Label("There are no parts associated with the product."));
    }


    @FXML
    void partSearchTextF(KeyEvent event) {
    }

    @FXML
    void clearPartSearch(MouseEvent event) {
    }

    @FXML
    void addProductAssociateButton(MouseEvent event) {
    }

    @FXML
    void addProductDisassociateButton(MouseEvent event) {
    }

    @FXML
    void addProductSaveButton(MouseEvent event) {
    }

    @FXML
    void addProductCloseButton(MouseEvent event) {
    }
}
