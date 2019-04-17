/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ReportDemo_LiabilitiesController implements Initializable {

    @FXML
    private AnchorPane anchorpane;
    @FXML
    private JFXTextField tf_search;
    @FXML
    private JFXComboBox<?> combobox;
    @FXML
    private TableView<?> table_view;
    @FXML
    private TableColumn<?, ?> column_id;
    @FXML
    private TableColumn<?, ?> column_name;
    @FXML
    private TableColumn<?, ?> column_totalpaying;
    @FXML
    private TableColumn<?, ?> column_alreadypaying;
    @FXML
    private TableColumn<?, ?> column_remain;
    @FXML
    private TableColumn<?, ?> column_exdate;
    @FXML
    private TableColumn<?, ?> column_confirm;
    @FXML
    private Button button;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void OkAction(ActionEvent event) {
    }
    
}
