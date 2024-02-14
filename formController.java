package zacarias.c482project.controller;

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
import zacarias.c482project.model.Inventory;
import zacarias.c482project.model.Product;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FormScreenController implements Initializable {
    public AnchorPane mainForm;
    public TextField partSearchField;
    public TableView partTableView;
    public TableColumn partID;
    public TableColumn partName;
    public TableColumn partInvLevel;
    public TableColumn partCost;
    public Button partAddButton;
    public Button partModifyButton;
    public Button partDeleteButton;
    public TextField productSearchField;
    public TableView productTableView;
    public TableColumn productID;
    public TableColumn productName;
    public TableColumn productInvLevel;
    public TableColumn productCost;
    public Button productAddButton;
    public Button productModifyButton;
    public Button productDeleteButton;
    public Button exitButton;

    public void partSearchTextF(KeyEvent keyEvent) {
    }

    public void clearPartSearch(MouseEvent mouseEvent) {
    }

    public void addPartButton(MouseEvent mouseEvent) {
    }

    public void modifyPartButton(MouseEvent mouseEvent) {
    }

    public void deletePartButton(MouseEvent mouseEvent) {
    }

    public void productSearchTextF(KeyEvent keyEvent) {
    }

    public void clearProductSearch(MouseEvent mouseEvent) {
    }

    public void addProductButton(MouseEvent mouseEvent) {
    }

    public void deleteProductButton(MouseEvent mouseEvent) {
    }

    public void exitProgramButton(MouseEvent mouseEvent) {
        System.out.println("Exit button has been clicked.");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        partTableView.setItems(Inventory.getAllParts());
        productTableView.setItems(Inventory.getAllProducts());

        partID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partInvLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("Price"));

        partTableView.setPlaceholder(new Label("No parts in the system."));

        productID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productInvLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productCost.setCellValueFactory(new PropertyValueFactory<>("Price"));

        productTableView.setPlaceholder(new Label("No products in the system."));
    }

    @FXML
    void modifyProductButton(MouseEvent event) throws IOException {
                Product modifySelectedProduct = (Product) productTableView.getSelectionModel().getSelectedItem();
                if (Inventory.getAllProducts().isEmpty()) {
                    showAlert("There are no products in the system to modify.");
                    return;
                } else if (modifySelectedProduct == null) {
                    showAlert("Please select a product to modify.");
                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/modifyProductScreen.fxml"));
                    ModifyProductController controller = new ModifyProductController(modifySelectedProduct);
                    loader.setController(controller);
                    Parent parent = loader.load();

                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    primaryStage.hide();
                    primaryStage.setScene(new Scene(parent));
                    primaryStage.setTitle("Modify Product Form");
                    primaryStage.setResizable(false);
                    primaryStage.show();
                }
    }
            private void showAlert(String message) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
        }
    }


