/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ReportDetailWarehouseController implements Initializable {

    @FXML
    private JFXComboBox<String> combobox;
    @FXML
    private TableView<LiabilitiesReport> table_view;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_id;

    @FXML
    private Button button;

    private ObservableList<LiabilitiesReport> data;

    private Connection con;
    private PreparedStatement pst;
    private PreparedStatement pst2;
    private ResultSet rs;
    @FXML
    private Label lb_tu;
    @FXML
    private JFXDatePicker date_1;
    @FXML
    private Label lb_den;
    @FXML
    private JFXDatePicker date_2;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_drugname;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_supplier;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_qty;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_drugexpdate;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_stockdatein;
    @FXML
    private AnchorPane anchorpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            con = controller.ConnectDB.connectSQLServer();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportDetailWarehouseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportDetailWarehouseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        comboboxDefault();
        data = FXCollections.observableArrayList();

        try {
            setDate1InCombobox();
        } catch (SQLException ex) {
            Logger.getLogger(ReportDetailWarehouseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        date_2.setValue(LocalDate.now());

        column_id.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("id"));
        column_drugname.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("suppliername"));
        column_supplier.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("total"));
        column_qty.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("already"));
        column_drugexpdate.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("remain"));
        column_stockdatein.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("expdate"));

        try {
            comboboxDo();
        } catch (SQLException ex) {
            Logger.getLogger(ReportDetailWarehouseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table_view.setItems(data);

    }

    private void comboboxDefault() {
        combobox.getItems().addAll("Day", "Week", "Month", "Year", "All Time", "Custom");
        combobox.getSelectionModel().selectFirst();
        combobox.getValue();
    }

    private void setDate1InCombobox() throws SQLException {
        LocalDate date = LocalDate.now();

        pst = con.prepareStatement(" select MIN(stockdetaildate) from stockdetail");
        rs = pst.executeQuery();
        if (rs.next()) {
            date = rs.getDate(1).toLocalDate();
        }
        date_1.setValue(date);

    }

    private void comboboxDo() throws SQLException {
        if (combobox.getValue() != null) {
            switch (combobox.getValue().toLowerCase()) {
                case "day":
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
                    comboboxDo_ngay();

                    break;
                case "week":
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
                    comboboxDo_tuan();

                    break;
                case "month":
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
                    comboboxDo_thang();

                    break;
                case "year":
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
                    comboboxDo_nam();

                    break;
                case "all time":
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
                    comboboxDo_alltime();

                    break;
                case "custom":
                    lb_tu.setVisible(true);
                    lb_den.setVisible(true);
                    date_1.setVisible(true);
                    date_2.setVisible(true);
                    comboboxDo_custom();

                    break;
                default:
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);

                    break;
            }
        }
    }

    private void comboboxDo_custom() throws SQLException {
        String sql_custom = "select stockdetailID,PCode,Supplier,qty,drugexdate,stockdetaildate\n"
                + "from stock\n"
                + " INNER JOIN stockdetail\n"
                + " on stock.stockID = stockdetail.stockID\n"
                + " where stockdetaildate between ? and ?";
        pst = con.prepareStatement(sql_custom);
        pst.setDate(1, java.sql.Date.valueOf(date_1.getValue()));
        pst.setDate(2, java.sql.Date.valueOf(date_2.getValue()));
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new LiabilitiesReport(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));

        }
        table_view.setItems(data);

    }

    private void comboboxDo_ngay() throws SQLException {
        String sql_custom = "select stockdetailID,PCode,Supplier,qty,drugexdate,stockdetaildate\n"
                + "from stock\n"
                + " INNER JOIN stockdetail\n"
                + " on stock.stockID = stockdetail.stockID\n"
                + " where stockdetaildate = ?";
        pst = con.prepareStatement(sql_custom);
        pst.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new LiabilitiesReport(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));

        }
        table_view.setItems(data);

    }

    private void comboboxDo_tuan() throws SQLException {
        String sql_custom = "select stockdetailID,PCode,Supplier,qty,drugexdate,stockdetaildate\n"
                + "from stock\n"
                + " INNER JOIN stockdetail\n"
                + " on stock.stockID = stockdetail.stockID\n"
                + " where stockdetaildate between DATEADD(day, -7,  ?) and ?";
        pst = con.prepareStatement(sql_custom);
        pst.setDate(1, java.sql.Date.valueOf(date_1.getValue()));
        pst.setDate(2, java.sql.Date.valueOf(date_2.getValue()));
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new LiabilitiesReport(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));

        }
        table_view.setItems(data);

    }

    private void comboboxDo_thang() throws SQLException {
        String sql_custom = "select stockdetailID,PCode,Supplier,qty,drugexdate,stockdetaildate\n"
                + "from stock\n"
                + " INNER JOIN stockdetail\n"
                + " on stock.stockID = stockdetail.stockID\n"
                + " where stockdetaildate between DATEADD(month, -1,  ? ) and ?";
        pst = con.prepareStatement(sql_custom);
        pst.setDate(1, java.sql.Date.valueOf(date_1.getValue()));
        pst.setDate(2, java.sql.Date.valueOf(date_2.getValue()));
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new LiabilitiesReport(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));

        }
        table_view.setItems(data);

    }

    private void comboboxDo_nam() throws SQLException {
        String sql_custom = "select stockdetailID,PCode,Supplier,qty,drugexdate,stockdetaildate\n"
                + "from stock\n"
                + " INNER JOIN stockdetail\n"
                + " on stock.stockID = stockdetail.stockID\n"
                + " where stockdetaildate between DATEADD(year, -1,  ? ) and ?";
        pst = con.prepareStatement(sql_custom);
        pst.setDate(1, java.sql.Date.valueOf(date_1.getValue()));
        pst.setDate(2, java.sql.Date.valueOf(date_2.getValue()));
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new LiabilitiesReport(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));

        }
        table_view.setItems(data);

    }

    private void comboboxDo_alltime() throws SQLException {
        String sql_custom = "select stockdetailID,PCode,Supplier,qty,drugexdate,stockdetaildate\n"
                + "from stock\n"
                + " INNER JOIN stockdetail\n"
                + " on stock.stockID = stockdetail.stockID\n";

        pst = con.prepareStatement(sql_custom);

        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new LiabilitiesReport(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));

        }
        table_view.setItems(data);

    }

    @FXML
    private void OkAction(ActionEvent event) {
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void action_date1(ActionEvent event) throws SQLException {
        comboboxDo_custom();
    }

    @FXML
    private void action_date2(ActionEvent event) throws SQLException {
        comboboxDo_custom();
    }

    @FXML
    private void action_combobox(ActionEvent event) throws SQLException {
        comboboxDo();
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
