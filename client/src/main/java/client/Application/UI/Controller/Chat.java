package client.Application.UI.Controller;

import client.Application.Validation;
import commons.Exception.GuiElementException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.awt.*;

public class Chat {

    @FXML private Button bttnAddContact;

    @FXML private AnchorPane panelContacts;

    @FXML private Pane menuAddContact;

    @FXML private TextField inputContactName;
    @FXML private TextField inputContactAddress;

    @FXML private ListView<Pane> listContacts;

    @Autowired
    private Validation validation;

    @FXML public void addContact() {
        inputContactAddress.setText("");
        inputContactName.setText("");
        menuAddContact.setVisible(true);
        validation.validateAddContactDetails();
    }

    @FXML public void createContact() {
        createContactPane(inputContactName.getText(), inputContactAddress.getText());
    }

    private Pane createContactPane(String contactName, String contactAddress) {
        Pane pane = new Pane();
        Text contactNameText = new Text(contactName);

        contactNameText.setStyle("" +
                "-fx-font-size: 10; " +
                "-fx-background-color: black");
        pane.setPrefHeight(50);
        pane.setPrefWidth(100);
        pane.setStyle("-fx-background-color: rgb(100, 100, 100)");
        pane.getChildren().add(contactNameText);
        listContacts.getItems().add(pane);
        return pane;
    }
}
