/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    private JFXComboBox<String> combobox;
    @FXML
    private TableView<LiabilitiesReport> table_view;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_id;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_name;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_totalpaying;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_alreadypaying;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_remain;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_exdate;
    @FXML
    private Button button;

    private ObservableList<LiabilitiesReport> data;

    private Connection con;
    private PreparedStatement pst;
    private PreparedStatement pst2;
    private ResultSet rs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            con = controller.ConnectDB.connectSQLServer();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportDemo_LiabilitiesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportDemo_LiabilitiesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        data = FXCollections.observableArrayList();

        column_id.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("id"));
        column_name.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("suppliername"));
        column_totalpaying.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("total"));
        column_alreadypaying.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("already"));
        column_remain.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("remain"));
        column_exdate.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("expdate"));
        try {
            liabilitiesDo();
        } catch (SQLException ex) {
            Logger.getLogger(ReportDemo_LiabilitiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tf_search.setOnKeyReleased((KeyEvent ke) -> {

            data.clear();
            try {
                doSearchAction();
            } catch (SQLException ex) {
                Logger.getLogger(ReportDemo_LiabilitiesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            table_view.setItems(data);
           

        });
        table_view.setItems(data);

        
        
    }
    
    private void doSearchAction() throws SQLException{
        String sql = "select stockdetailID,Supplier,amount,already,remain,liabilitiesexdate\n"
                + "from stock\n"
                + " INNER JOIN stockdetail\n"
                + " on stock.stockID = stockdetail.stockID\n"
                + " where liabilitiesexdate is not null and Supplier like ?";
        
        pst = con.prepareStatement(sql);
        pst.setString(1, "%"+tf_search.getText()+"%");
        rs = pst.executeQuery();
                while (rs.next()) {
            data.add(new LiabilitiesReport(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));

        }
    
    }

    private void liabilitiesDo() throws SQLException {
        pst = con.prepareStatement("select stockdetailID,Supplier,amount,already,remain,liabilitiesexdate\n"
                + "from stock\n"
                + " INNER JOIN stockdetail\n"
                + " on stock.stockID = stockdetail.stockID\n"
                + " where liabilitiesexdate is not null");
        rs = pst.executeQuery();
        while (rs.next()) {
            data.add(new LiabilitiesReport(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));

        }

    }

    @FXML
    private void OkAction(ActionEvent event) {
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        stage.close();

    }

    public static class LiabilitiesReport {

        private final SimpleStringProperty id;
        private final SimpleStringProperty suppliername;
        private final SimpleStringProperty total;
        private final SimpleStringProperty already;
        private final SimpleStringProperty remain;
        private final SimpleStringProperty expdate;

        LiabilitiesReport(String fid, String fsuppliername, String ftotal, String falready, String fremain, String fexpdate) {
            this.id = new SimpleStringProperty(fid);
            this.suppliername = new SimpleStringProperty(fsuppliername);
            this.total = new SimpleStringProperty(ftotal);
            this.already = new SimpleStringProperty(falready);
            this.remain = new SimpleStringProperty(fremain);
            this.expdate = new SimpleStringProperty(fexpdate);

        }

        public String getId() {
            return id.get();
        }

        public void setId(String fid) {
            id.set(fid);
        }

        public String getSuppliername() {
            return suppliername.get();
        }

        public void setSuppliername(String fsuppliername) {
            suppliername.set(fsuppliername);
        }

        public String getTotal() {
            return total.get();
        }

        public void setTotal(String ftotal) {
            total.set(ftotal);
        }

        public String getAlready() {
            return already.get();
        }

        public void setAlready(String falready) {
            already.set(falready);
        }

        public String getRemain() {
            return remain.get();
        }

        public void setRemain(String fremain) {
            remain.set(fremain);
        }

        public String getExpdate() {
            return expdate.get();
        }

        public void setExpdate(String fexpdate) {
            expdate.set(fexpdate);
        }
    }
}
