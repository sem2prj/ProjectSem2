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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
public class ReportDemo_EmployeeController implements Initializable {

    @FXML
    private TableView<EmployeeReport> table_view;
    @FXML
    private TableColumn<EmployeeReport, String> column_manv;
    @FXML
    private TableColumn<EmployeeReport, String> column_tennv;
    @FXML
    private TableColumn<EmployeeReport, String> column_sdt;
    @FXML
    private TableColumn<EmployeeReport, String> column_ngayvaolam;
    @FXML
    private TableColumn<EmployeeReport, String> column_bophan;
    @FXML
    private TableColumn<EmployeeReport, String> column_chucvu;
    @FXML
    private TableColumn<EmployeeReport, String> column_tiendaban;
    @FXML
    private JFXComboBox<String> combobox;
    @FXML
    private Label lb_tu;
    @FXML
    private Button button;
    @FXML
    private JFXDatePicker date_1;
    @FXML
    private Label lb_den;
    @FXML
    private JFXDatePicker date_2;

    private ObservableList<EmployeeReport> data;

    private Connection con;
    private PreparedStatement pst;
    private PreparedStatement pst2;
    private ResultSet rs;
    
    @FXML
    private AnchorPane anchorpane;

// AlertDialog.display("Info", "Ngay");
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = controller.ConnectDB.connectSQLServer();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportDemo_EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportDemo_EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        data = FXCollections.observableArrayList();

        column_manv.setCellValueFactory(new PropertyValueFactory<EmployeeReport, String>("manv"));
        column_tennv.setCellValueFactory(new PropertyValueFactory<EmployeeReport, String>("tennv"));
        column_sdt.setCellValueFactory(new PropertyValueFactory<EmployeeReport, String>("sdt"));
        column_ngayvaolam.setCellValueFactory(new PropertyValueFactory<EmployeeReport, String>("ngayvaolam"));
        column_bophan.setCellValueFactory(new PropertyValueFactory<EmployeeReport, String>("bophan"));
        column_chucvu.setCellValueFactory(new PropertyValueFactory<EmployeeReport, String>("chucvu"));
        column_tiendaban.setCellValueFactory(new PropertyValueFactory<EmployeeReport, String>("tiendaban"));

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
            Logger.getLogger(ReportDemo_EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        table_view.setItems(data);

    }

    private void comboboxDefault() {
        combobox.getItems().addAll("Day", "Week", "Month", "Year", "All Time", "Custom");
        combobox.getSelectionModel().selectFirst();
        combobox.getValue();
    }

    @FXML
    private void action_combobox(ActionEvent event) throws SQLException {
        comboboxDo();
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
                    column_tiendaban.setSortType(TableColumn.SortType.ASCENDING);
                    table_view.getSortOrder().add(column_tiendaban);
                    column_tiendaban.setSortable(true);
                    break;
                case "week":
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
                    comboboxDo_tuan();
                    column_tiendaban.setSortType(TableColumn.SortType.ASCENDING);
                    table_view.getSortOrder().add(column_tiendaban);
                    column_tiendaban.setSortable(true);
                    break;
                case "month":
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
                    comboboxDo_thang();
                    column_tiendaban.setSortType(TableColumn.SortType.ASCENDING);
                    table_view.getSortOrder().add(column_tiendaban);
                    column_tiendaban.setSortable(true);
                    break;
                case "year":
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
                    comboboxDo_nam();
                    column_tiendaban.setSortType(TableColumn.SortType.ASCENDING);
                    table_view.getSortOrder().add(column_tiendaban);
                    column_tiendaban.setSortable(true);
                    break;
                case "all time":
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
                    comboboxDo_alltime();
                    column_tiendaban.setSortType(TableColumn.SortType.ASCENDING);
                    table_view.getSortOrder().add(column_tiendaban);
                    column_tiendaban.setSortable(true);
                    break;
                case "custom":
                    lb_tu.setVisible(true);
                    lb_den.setVisible(true);
                    date_1.setVisible(true);
                    date_2.setVisible(true);
                    comboboxDo_custom();
                    column_tiendaban.setSortType(TableColumn.SortType.ASCENDING);
                    table_view.getSortOrder().add(column_tiendaban);
                    column_tiendaban.setSortable(true);
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
        String sql_custom = "select DetailUser.code,Users.UsersName ,DetailUser.phone,DetailUser.workday,DetailUser.department,DetailUser.mission,max(AmountTotal)\n"
                + " from DetailUser   \n"
                + " INNER JOIN  Orders   \n"
                + " on DetailUser.UsersID = Orders.UsersID   \n"
                + "  INNER JOIN Users\n"
                + "  on DetailUser.UsersID = Users.UsersID\n"
                + " where OrderDate between ? and ?\n"
                + " group by DetailUser.Code,Users.UsersName,DetailUser.Phone,DetailUser.WorkDay,DetailUser.Department,DetailUser.Mission";
        pst = con.prepareStatement(sql_custom);
        pst.setDate(1, java.sql.Date.valueOf(date_1.getValue()));
        pst.setDate(2, java.sql.Date.valueOf(date_2.getValue()));
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new EmployeeReport(rs.getString("code"), rs.getString("usersname"), rs.getString("phone"), rs.getString("workday"), rs.getString("department"), rs.getString("mission"), rs.getString(7)));

        }
        table_view.setItems(data);

    }

    private void comboboxDo_alltime() throws SQLException {
        String sql_alltime = "select DetailUser.code,Users.UsersName ,DetailUser.phone,DetailUser.workday,DetailUser.department,DetailUser.mission,max(AmountTotal)\n"
                + " from DetailUser   \n"
                + " INNER JOIN  Orders   \n"
                + " on DetailUser.UsersID = Orders.UsersID   \n"
                + "  INNER JOIN Users\n"
                + "  on DetailUser.UsersID = Users.UsersID\n"
                + " group by DetailUser.Code,Users.UsersName,DetailUser.Phone,DetailUser.WorkDay,DetailUser.Department,DetailUser.Mission";
        pst = con.prepareStatement(sql_alltime);
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new EmployeeReport(rs.getString("code"), rs.getString("usersname"), rs.getString("phone"), rs.getString("workday"), rs.getString("department"), rs.getString("mission"), rs.getString(7)));

        }
        table_view.setItems(data);

    }

    private void comboboxDo_nam() throws SQLException {
        String sql_custom = "select DetailUser.code,Users.UsersName ,DetailUser.phone,DetailUser.workday,DetailUser.department,DetailUser.mission,max(AmountTotal)\n"
                + " from DetailUser   \n"
                + " INNER JOIN  Orders   \n"
                + " on DetailUser.UsersID = Orders.UsersID   \n"
                + "  INNER JOIN Users\n"
                + "  on DetailUser.UsersID = Users.UsersID\n"
                + " where OrderDate between DATEADD(year, -1,  ? ) and ?\n"
                + " group by DetailUser.Code,Users.UsersName,DetailUser.Phone,DetailUser.WorkDay,DetailUser.Department,DetailUser.Mission";
        pst = con.prepareStatement(sql_custom);
        pst.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
        pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new EmployeeReport(rs.getString("code"), rs.getString("usersname"), rs.getString("phone"), rs.getString("workday"), rs.getString("department"), rs.getString("mission"), rs.getString(7)));

        }
        table_view.setItems(data);

    }

    private void comboboxDo_thang() throws SQLException {
        String sql_custom = "select DetailUser.code,Users.UsersName ,DetailUser.phone,DetailUser.workday,DetailUser.department,DetailUser.mission,max(AmountTotal)\n"
                + " from DetailUser   \n"
                + " INNER JOIN  Orders   \n"
                + " on DetailUser.UsersID = Orders.UsersID   \n"
                + "  INNER JOIN Users\n"
                + "  on DetailUser.UsersID = Users.UsersID\n"
                + " where OrderDate between DATEADD(month, -1,  ? ) and ?\n"
                + " group by DetailUser.Code,Users.UsersName,DetailUser.Phone,DetailUser.WorkDay,DetailUser.Department,DetailUser.Mission";
        pst = con.prepareStatement(sql_custom);
        pst.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
        pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new EmployeeReport(rs.getString("code"), rs.getString("usersname"), rs.getString("phone"), rs.getString("workday"), rs.getString("department"), rs.getString("mission"), rs.getString(7)));

        }
        table_view.setItems(data);

    }

    private void comboboxDo_tuan() throws SQLException {
        String sql_custom = "select DetailUser.code,Users.UsersName ,DetailUser.phone,DetailUser.workday,DetailUser.department,DetailUser.mission,max(AmountTotal)\n"
                + " from DetailUser   \n"
                + " INNER JOIN  Orders   \n"
                + " on DetailUser.UsersID = Orders.UsersID   \n"
                + "  INNER JOIN Users\n"
                + "  on DetailUser.UsersID = Users.UsersID\n"
                + " where OrderDate between DATEADD(day, -7,  ?) and ?\n"
                + " group by DetailUser.Code,Users.UsersName,DetailUser.Phone,DetailUser.WorkDay,DetailUser.Department,DetailUser.Mission";
        pst = con.prepareStatement(sql_custom);
        pst.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
        pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new EmployeeReport(rs.getString("code"), rs.getString("usersname"), rs.getString("phone"), rs.getString("workday"), rs.getString("department"), rs.getString("mission"), rs.getString(7)));

        }
        table_view.setItems(data);

    }

    private void comboboxDo_ngay() throws SQLException {
        String sql_custom = "select DetailUser.code,Users.UsersName ,DetailUser.phone,DetailUser.workday,DetailUser.department,DetailUser.mission,max(AmountTotal)\n"
                + " from DetailUser   \n"
                + " INNER JOIN  Orders   \n"
                + " on DetailUser.UsersID = Orders.UsersID   \n"
                + "  INNER JOIN Users\n"
                + "  on DetailUser.UsersID = Users.UsersID\n"
                + " where OrderDate = ?\n"
                + " group by DetailUser.Code,Users.UsersName,DetailUser.Phone,DetailUser.WorkDay,DetailUser.Department,DetailUser.Mission";
        pst = con.prepareStatement(sql_custom);
        pst.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {

            data.add(new EmployeeReport(rs.getString("code"), rs.getString("usersname"), rs.getString("phone"), rs.getString("workday"), rs.getString("department"), rs.getString("mission"), rs.getString(7)));

        }
        table_view.setItems(data);

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
    private void deleteAction(ActionEvent event) {
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

    public static class EmployeeReport {

        private final SimpleStringProperty manv;
        private final SimpleStringProperty tennv;
        private final SimpleStringProperty sdt;
        private final SimpleStringProperty ngayvaolam;
        private final SimpleStringProperty bophan;
        private final SimpleStringProperty chucvu;
        private final SimpleStringProperty tiendaban;

        EmployeeReport(String fmanv, String ftennv, String fsdt, String fngayvaolam, String fbophan, String fchucvu, String ftiendaban) {
            this.manv = new SimpleStringProperty(fmanv);
            this.tennv = new SimpleStringProperty(ftennv);
            this.sdt = new SimpleStringProperty(fsdt);
            this.ngayvaolam = new SimpleStringProperty(fngayvaolam);
            this.bophan = new SimpleStringProperty(fbophan);
            this.chucvu = new SimpleStringProperty(fchucvu);
            this.tiendaban = new SimpleStringProperty(ftiendaban);

        }

        public String getManv() {
            return manv.get();
        }

        public void setManv(String fmanv) {
            manv.set(fmanv);
        }

        public String getTennv() {
            return tennv.get();
        }

        public void setTennv(String ftennv) {
            tennv.set(ftennv);
        }

        public String getSdt() {
            return sdt.get();
        }

        public void setSdt(String fsdt) {
            sdt.set(fsdt);
        }

        public String getNgayvaolam() {
            return ngayvaolam.get();
        }

        public void setNgayvaolam(String fngayvaolam) {
            ngayvaolam.set(fngayvaolam);
        }

        public String getBophan() {
            return bophan.get();
        }

        public void setBophan(String fbophan) {
            bophan.set(fbophan);
        }

        public String getChucvu() {
            return chucvu.get();
        }

        public void setChucvu(String fchucvu) {
            chucvu.set(fchucvu);
        }

        public String getTiendaban() {
            return tiendaban.get();
        }

        public void setTiendaban(String ftiendaban) {
            tiendaban.set(ftiendaban);
        }

    }

}
