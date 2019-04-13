/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package controller;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ReportDemo_CustomerController implements Initializable {

    @FXML
    private TableView<CustomerReport> table_view;
    @FXML
    private TableColumn<CustomerReport, String> column_tenkh;
    @FXML
    private TableColumn<CustomerReport, String> column_makh;
    @FXML
    private TableColumn<CustomerReport, String> column_diachi;
    @FXML
    private TableColumn<CustomerReport, String> column_sdt;
    @FXML
    private TableColumn<CustomerReport, String> column_ngaymua;
    @FXML
    private TableColumn<CustomerReport, String> column_sohoadon;
    @FXML
    private TableColumn<CustomerReport, String> column_sotien;
    @FXML
    private Button button;
    @FXML
    private Label lb_tongtien;
    @FXML
    private Label lb_solan;
    @FXML
    private Label lb_tien;
    @FXML
    private Label lb_lan;
    @FXML
    private JFXTextField tf_search;

    private Connection con;
    private PreparedStatement pst;
    private PreparedStatement pst2;
    private PreparedStatement pst3;
    private ResultSet rs;

    private ObservableList<CustomerReport> data;
    @FXML
    private ImageView image_view;
    @FXML
    private AnchorPane anchorpane;

    double grandTotal = 0;
    int count=0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = controller.ConnectDB.connectSQLServer();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        data = FXCollections.observableArrayList();

        column_tenkh.setCellValueFactory(new PropertyValueFactory<>("tenkh"));
        column_makh.setCellValueFactory(new PropertyValueFactory<>("makh"));
        column_diachi.setCellValueFactory(new PropertyValueFactory<>("diachi"));
        column_sdt.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        column_ngaymua.setCellValueFactory(new PropertyValueFactory<>("ngaymua"));
        column_sohoadon.setCellValueFactory(new PropertyValueFactory<>("sohoadon"));
        column_sotien.setCellValueFactory(new PropertyValueFactory<>("sotien"));

        lb_tien.setText("");
        lb_lan.setText("");
        tf_search.setOnKeyReleased((KeyEvent ke) -> {

            data.clear();
            table_view.setItems(data);
            doSearchAction();

        });
        table_view.setItems(data);

        Image image = new Image("/image/jimmy.jpg");
        image_view.setImage(image);
    }

    private void doSearchAction() {
        System.out.println(tf_search.getText());
        data.clear();
        table_view.setItems(data);

        try {
            pst = con.prepareStatement(""
                    + " select Customer.CuCode,Customer.CuName,Customer.CuAddrees,Customer.CuPhone,Orders.OrderDate,Orders.OrderID,Orders.AmountTotal\n"
                    + " from Customer\n"
                    + " INNER JOIN  Orders  \n"
                    + " on  Customer.CuId = Orders.CuId \n"
                    + " where CuName like ?");
            pst.setString(1, "%" + tf_search.getText() + "%");
            rs = pst.executeQuery();

            while (rs.next()) {
                data.add(new CustomerReport(rs.getString("CuCode"),
                        rs.getString("CuName"),
                        rs.getString("CuAddrees"),
                        rs.getString("CuPhone"),
                        rs.getString("OrderDate"),
                        rs.getString("OrderID"),
                        rs.getString("AmountTotal")));
                table_view.setItems(data);

            }

            rs.close();
            pst.close();

        } catch (SQLException ex) {
            Logger.getLogger(ReportDemo_CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    


    @FXML
    private void deleteAction(ActionEvent event) {
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void action_clicked(MouseEvent event) {
        grandTotal = 0;
        count = 0;
        CustomerReport him = table_view.getItems().get(table_view.getSelectionModel().getSelectedIndex());
        for(CustomerReport her : data){
            if(her.getTenkh().equalsIgnoreCase(him.getTenkh())){
                grandTotal += Double.parseDouble(String.valueOf(her.getSotien()));
                count ++;
            }
        }
        
        lb_tien.setText(String.valueOf(grandTotal));
        lb_lan.setText(String.valueOf(count));
    }

    public static class CustomerReport {

//        private final SimpleStringProperty makh;
//        private final SimpleStringProperty tenkh;
//        private final SimpleStringProperty diachi;
//        private final SimpleStringProperty sdt;
//        private final SimpleStringProperty ngaymua;
//        private final SimpleStringProperty sohoadon;
//        private final SimpleStringProperty sotien;
//
//        CustomerReport(String ftenkh, String fmakh, String fdiachi, String fsdt, String fngaymua, String fsohoadon, String fsotien) {
//            this.tenkh = new SimpleStringProperty(ftenkh);
//            this.makh = new SimpleStringProperty(fmakh);
//            this.diachi = new SimpleStringProperty(fdiachi);
//            this.sdt = new SimpleStringProperty(fsdt);
//            this.ngaymua = new SimpleStringProperty(fngaymua);
//            this.sohoadon = new SimpleStringProperty(fsohoadon);
//            this.sotien = new SimpleStringProperty(fsotien);
//        }
//
//        public String getMakh() {
//            return makh.get();
//        }
//
//        public void setMakh(String fmakh) {
//            makh.set(fmakh);
//        }
//
//        public String getTenkh() {
//            return tenkh.get();
//        }
//
//        public void setTenkh(String ftenkh) {
//            tenkh.set(ftenkh);
//        }
//
//
//
//        public String getDiachi() {
//            return diachi.get();
//        }
//
//        public void setDiachi(String fdiachi) {
//            diachi.set(fdiachi);
//        }
//
//        public String getSdt() {
//            return sdt.get();
//        }
//
//        public void setSdt(String fsdt) {
//            sdt.set(fsdt);
//        }
//
//        public String getNgaymua() {
//            return ngaymua.get();
//        }
//
//        public void setNgaymua(String fngaymua) {
//            ngaymua.set(fngaymua);
//        }
//
//        public String getSohoadon() {
//            return sohoadon.get();
//        }
//
//        public void setSohoadon(String fsohoadon) {
//            sohoadon.set(fsohoadon);
//        }
//
//        public String getSotien() {
//            return sotien.get();
//        }
//
//        public void setSotien(String fsotien) {
//            sotien.set(fsotien);
//        }
        private String makh;
        private String tenkh;
        private String diachi;
        private String sdt;
        private String ngaymua;
        private String sohoadon;
        private String sotien;

        public CustomerReport(String makh, String tenkh, String diachi, String sdt, String ngaymua, String sohoadon, String sotien) {
            this.makh = makh;
            this.tenkh = tenkh;
            this.diachi = diachi;
            this.sdt = sdt;
            this.ngaymua = ngaymua;
            this.sohoadon = sohoadon;
            this.sotien = sotien;
        }

        public String getMakh() {
            return makh;
        }

        public void setMakh(String makh) {
            this.makh = makh;
        }

        public String getTenkh() {
            return tenkh;
        }

        public void setTenkh(String tenkh) {
            this.tenkh = tenkh;
        }

        public String getDiachi() {
            return diachi;
        }

        public void setDiachi(String diachi) {
            this.diachi = diachi;
        }

        public String getSdt() {
            return sdt;
        }

        public void setSdt(String sdt) {
            this.sdt = sdt;
        }

        public String getNgaymua() {
            return ngaymua;
        }

        public void setNgaymua(String ngaymua) {
            this.ngaymua = ngaymua;
        }

        public String getSohoadon() {
            return sohoadon;
        }

        public void setSohoadon(String sohoadon) {
            this.sohoadon = sohoadon;
        }

        public String getSotien() {
            return sotien;
        }

        public void setSotien(String sotien) {
            this.sotien = sotien;
        }

    }
}
