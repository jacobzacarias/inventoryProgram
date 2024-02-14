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




