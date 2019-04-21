/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
public class ReportDemo_GrossRevenueController implements Initializable {

    @FXML
    private JFXComboBox<String> combobox;
    @FXML
    private Label lb_tu;
    @FXML
    private JFXDatePicker date_1;
    @FXML
    private Label lb_den;
    @FXML
    private JFXDatePicker date_2;
    @FXML
    private TableView<RevenueReport> table_view;
    @FXML
    private TableColumn<RevenueReport, String> column_cuahang;
    @FXML
    private TableColumn<RevenueReport, String> column_doanhthu;
    @FXML
    private TableColumn<RevenueReport, String> column_ngay;
    @FXML
    private Button button;

    private Connection con;
    private PreparedStatement pst;
    private PreparedStatement pst2;
    private ResultSet rs;

    private ObservableList<RevenueReport> data;
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
            Logger.getLogger(ReportDemo_GrossRevenueController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportDemo_GrossRevenueController.class.getName()).log(Level.SEVERE, null, ex);
        }
       

        data = FXCollections.observableArrayList();

        column_cuahang.setCellValueFactory(new PropertyValueFactory<RevenueReport, String>("cuahang"));
        column_ngay.setCellValueFactory(new PropertyValueFactory<RevenueReport, String>("ngay"));
        column_doanhthu.setCellValueFactory(new PropertyValueFactory<RevenueReport, String>("doanhthu"));

        column_cuahang.setStyle("-fx-alignment: CENTER;");
        column_ngay.setStyle("-fx-alignment: CENTER;");
        column_doanhthu.setStyle("-fx-alignment: CENTER-RIGHT;");

        try {
            setDate1InCombobox();
        } catch (SQLException ex) {
            Logger.getLogger(ReportDemo_EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        date_2.setValue(LocalDate.now());

        comboboxDefault();
        try {
            comboboxDo();
        } catch (SQLException ex) {
            Logger.getLogger(ReportDemo_GrossRevenueController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table_view.setItems(data);
    }

    private void comboboxDefault() {
        combobox.getItems().addAll("Day", "Week", "Month", "Year", "All time", "Custom");
        combobox.getSelectionModel().selectFirst();
        combobox.getValue();
    }

    private void comboboxDo() throws SQLException {
        if (combobox.getValue() != null) {
            switch (combobox.getValue().toLowerCase()) {
                case "day":
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
                    comboboxDo_day();
                    column_doanhthu.setSortType(TableColumn.SortType.ASCENDING);
                    table_view.getSortOrder().add(column_doanhthu);
                    column_doanhthu.setSortable(true);
                    break;
                case "week":
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
                    comboboxDo_week();
                    column_doanhthu.setSortType(TableColumn.SortType.ASCENDING);
                    table_view.getSortOrder().add(column_doanhthu);
                    column_doanhthu.setSortable(true);
                    break;
                case "month":
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
                    comboboxDo_thang();
                    column_doanhthu.setSortType(TableColumn.SortType.ASCENDING);
                    table_view.getSortOrder().add(column_doanhthu);
                    column_doanhthu.setSortable(true);

                    break;
                case "year":
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
                    comboboxDo_nam();
                    column_doanhthu.setSortType(TableColumn.SortType.ASCENDING);
                    table_view.getSortOrder().add(column_doanhthu);
                    column_doanhthu.setSortable(true);

                    break;

                case "all time":

                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
                    comboboxDo_alltime();
                    column_doanhthu.setSortType(TableColumn.SortType.ASCENDING);
                    table_view.getSortOrder().add(column_doanhthu);
                    column_doanhthu.setSortable(true);
                    break;

                case "custom":


                    lb_tu.setVisible(true);
                    lb_den.setVisible(true);
                    date_1.setVisible(true);
                    date_2.setVisible(true);
                    comboboxDo_custom();
                    column_doanhthu.setSortType(TableColumn.SortType.ASCENDING);
                    table_view.getSortOrder().add(column_doanhthu);
                    column_doanhthu.setSortable(true);
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

    private void comboboxDo_day() throws SQLException {
        String sql_custom = " select OrderDate, max(AmountTotal)\n"
                + " from Orders\n"
                + " where OrderDate = ?\n"
                + " group by OrderDate\n"
                + "	";
        pst = con.prepareStatement(sql_custom);
        pst.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new RevenueReport(rs.getString(1), rs.getString(2)));

        }
        table_view.setItems(data);

    }

    private void comboboxDo_week() throws SQLException {
        String sql_custom = " select OrderDate, max(AmountTotal)\n"
                + " from Orders\n"
                + " where OrderDate between DATEADD(day, -7,  ?) and ?\n"
                + " group by OrderDate\n"
                + "	";
        pst = con.prepareStatement(sql_custom);
        pst.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
        pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new RevenueReport(rs.getString(1), rs.getString(2)));

        }
        table_view.setItems(data);

    }

    private void comboboxDo_thang() throws SQLException {
        String sql_custom = " select OrderDate, max(AmountTotal)\n"
                + " from Orders\n"
                + " where OrderDate between DATEADD(month, -1,  ?) and ?\n"
                + " group by OrderDate\n"
                + "	";
        pst = con.prepareStatement(sql_custom);
        pst.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
        pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new RevenueReport(rs.getString(1), rs.getString(2)));

        }
        table_view.setItems(data);

    }

    private void comboboxDo_nam() throws SQLException {
        String sql_custom = " select OrderDate, max(AmountTotal)\n"
                + " from Orders\n"
                + " where OrderDate between DATEADD(year, -1,  ?) and ?\n"
                + " group by OrderDate\n"
                + "	";
        pst = con.prepareStatement(sql_custom);
        pst.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
        pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new RevenueReport(rs.getString(1), rs.getString(2)));

        }
        table_view.setItems(data);

    }

    private void comboboxDo_alltime() throws SQLException {
        String sql_custom = " select OrderDate, max(AmountTotal)\n"
                + " from Orders\n"
                + " group by OrderDate";
        pst = con.prepareStatement(sql_custom);
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new RevenueReport(rs.getString(1), rs.getString(2)));

        }
        table_view.setItems(data);

    }

    private void comboboxDo_custom() throws SQLException {
        String sql_custom = " select OrderDate, max(AmountTotal)\n"
                + " from Orders\n"
                + " where OrderDate between ? and ?\n"
                + " group by OrderDate\n"
                + "	";
        pst = con.prepareStatement(sql_custom);
        pst.setDate(1, java.sql.Date.valueOf(date_1.getValue()));
        pst.setDate(2, java.sql.Date.valueOf(date_2.getValue()));
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new RevenueReport(rs.getString(1), rs.getString(2)));

        }
        table_view.setItems(data);

    }

    @FXML
    private void action_combobox(ActionEvent event) throws SQLException {
        comboboxDo();
    }

    private void setDate1InCombobox() throws SQLException {
        LocalDate date = LocalDate.now();

        pst = con.prepareStatement("select MIN(OrderDate) from Orders");
        rs = pst.executeQuery();
        if (rs.next()) {
            date = rs.getDate(1).toLocalDate();
        }
        date_1.setValue(date);

    }

    @FXML
    private void OutAction(ActionEvent event) {
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void action_date1(ActionEvent event) throws SQLException {
            comboboxDo();
    }

    @FXML
    private void action_date2(ActionEvent event) throws SQLException {
            comboboxDo();
    }

    public static class RevenueReport {

        private final SimpleStringProperty cuahang;
        private final SimpleStringProperty ngay;
        private final SimpleStringProperty doanhthu;

        RevenueReport(String fngay, String fdoanhthu) {
            this.cuahang = new SimpleStringProperty("3D Pharmacy");
            this.ngay = new SimpleStringProperty(fngay);
            this.doanhthu = new SimpleStringProperty(fdoanhthu);

        }

        public String getCuahang() {
            return cuahang.get();
        }

        public void setCuahang(String fcuahang) {
            cuahang.set(fcuahang);
        }

        public String getNgay() {
            return ngay.get();
        }

        public void setNgay(String fngay) {
            ngay.set(fngay);
        }

        public String getDoanhthu() {
            return doanhthu.get();
        }

        public void setDoanhthu(String fdoanhthu) {
            doanhthu.set(fdoanhthu);
        }

    }
}
