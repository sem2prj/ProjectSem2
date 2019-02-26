/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ListEmployees;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class EmployeesController implements Initializable {

    @FXML
    private JFXTextField txt_codeemoloyees;
    @FXML
    private JFXTextField txt_name;
    @FXML
    private JFXTextField txt_address;
    @FXML
    private JFXTextField txt_phone;
    @FXML
    private JFXTextField txt_role;
    @FXML
    private DatePicker txt_date;
    @FXML
    private TableView<ListEmployees> table_employees;
    @FXML
    private TableColumn<ListEmployees, String> column_code;
    @FXML
    private TableColumn<ListEmployees, String> column_name;
    @FXML
    private TableColumn<ListEmployees, String> column_address;
    @FXML
    private TableColumn<ListEmployees, String> column_phone;
    @FXML
    private TableColumn<ListEmployees, String> column_role;
    @FXML
    private TableColumn<ListEmployees, String> column_date;
    @FXML
    private JFXButton btn_add;
    @FXML
    private JFXButton btn_update;
    @FXML
    private JFXButton btn_delete;
    @FXML
    private JFXButton btn_clear;

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    private ObservableList<ListEmployees> data;
    @FXML
    private JFXButton btnExport;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = controller.ConnectDB.connectSQLServer();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmployeesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        data = FXCollections.observableArrayList();
        load();
        setCellValueFromTableToTextField();
        export();

    }

    @FXML
    private void handle_add(ActionEvent event) {
        String sql = "insert into products(code,productname,priceInt, priceOut,pimage) Values(?,?,?,?,?)";

    }

    @FXML
    private void handle_update(ActionEvent event) {
    }

    @FXML
    private void handle_delete(ActionEvent event) {
    }

    @FXML
    private void handle_clear(ActionEvent event) {

        txt_codeemoloyees.clear();
        txt_name.clear();
        txt_address.clear();
        txt_phone.clear();
        txt_role.clear();
        txt_date.setValue(LocalDate.now());
    }

    private void load() {
//        data.clear();
        try {
            pst = con.prepareStatement("select employee.code,employee.name,employee.addrees,employee.phone,employee.roles,employee.date_created from employee");
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new ListEmployees(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), "" + rs.getDate(6)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        table_employees.setItems(data);
        //quy chuẩn đặt tên property phải đúng theo thứ tự theo kiểu java bean

        column_code.setCellValueFactory(new PropertyValueFactory<>("Code"));
        column_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        column_address.setCellValueFactory(new PropertyValueFactory<>("Addrees"));
        column_phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        column_role.setCellValueFactory(new PropertyValueFactory<>("Roles"));
        column_date.setCellValueFactory(new PropertyValueFactory<>("Date_created"));

    }

    private void setCellValueFromTableToTextField() {
        table_employees.setOnMouseClicked(e -> {
            ListEmployees le = table_employees.getItems().get(table_employees.getSelectionModel().getSelectedIndex());
            txt_codeemoloyees.setText(le.getCode());
            txt_name.setText(le.getName());
            txt_address.setText(le.getAddrees());
            txt_phone.setText(le.getPhone());
            txt_role.setText(le.getRoles());
            txt_date.setValue(LocalDate.parse(le.getDate_created()));
        });
    }

    public void export() {
        btnExport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FileOutputStream fileOut = new FileOutputStream("poi-test.xls");
                    HSSFWorkbook workbook = new HSSFWorkbook();
                    HSSFSheet worksheet = workbook.createSheet("POI Worksheet");

                    //row 0
                    HSSFRow row1 = worksheet.createRow((short) 0);

                    //add cell 1
                    HSSFCell cellA1 = row1.createCell((short) 0);
                    cellA1.setCellValue("Code");
                    HSSFCellStyle cellStyle = workbook.createCellStyle();
//                     cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
//                     cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    cellA1.setCellStyle(cellStyle);

                    //add cell 2
                    HSSFCell cellB1 = row1.createCell((short) 1);
                    cellB1.setCellValue("Name");
                    cellStyle = workbook.createCellStyle();
//                     cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
//                     cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    cellB1.setCellStyle(cellStyle);

                    //add cell 3
                    HSSFCell cellC1 = row1.createCell((short) 2);
                    cellC1.setCellValue("Address");
                    cellStyle = workbook.createCellStyle();
//                     cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
//                     cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    cellC1.setCellStyle(cellStyle);

                    //add cell 4
                    HSSFCell cellD1 = row1.createCell((short) 3);
                    cellD1.setCellValue("Phone");
                    cellStyle = workbook.createCellStyle();
//                     cellStyle.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
//                     cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    cellD1.setCellStyle(cellStyle);

                    //add cell 5
                    HSSFCell cellE1 = row1.createCell((short) 4);
                    cellE1.setCellValue("Role");
                    cellStyle = workbook.createCellStyle();
//                     cellStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
//                     cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    cellE1.setCellStyle(cellStyle);

                    //add cell 6
                    HSSFCell cellF1 = row1.createCell((short) 5);
                    cellF1.setCellValue("Role");
                    cellStyle = workbook.createCellStyle();
//                     cellStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
//                     cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    cellF1.setCellStyle(cellStyle);

                    for (int i = 0; i < data.size(); i++) {
                        HSSFRow row2 = worksheet.createRow((short) i + 1);
                        row2.createCell(0).setCellValue(((ListEmployees) data.get(i)).getCode());
                        row2.createCell(1).setCellValue(((ListEmployees) data.get(i)).getName());
                        row2.createCell(2).setCellValue(((ListEmployees) data.get(i)).getAddrees());
                        row2.createCell(3).setCellValue(((ListEmployees) data.get(i)).getPhone());
                        row2.createCell(4).setCellValue(((ListEmployees) data.get(i)).getRoles());
                        row2.createCell(5).setCellValue(((ListEmployees) data.get(i)).getDate_created());

                    }
                    workbook.write(fileOut);
                    fileOut.flush();
                    fileOut.close();

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EmployeesController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(EmployeesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            

        });
    }

//    public void imxport() {
//        btnimport.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                try {
//                    data.clear();
//                    HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("poi-test.xls"));
//                    HSSFSheet sheet = workbook.getSheetAt(0);
//                    HSSFRow row;
//                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//                        row = sheet.getRow(i);
//                        data.add(new Property(
//                                row.getCell(0).getStringCellValue(),
//                                row.getCell(1).getStringCellValue(),
//                                row.getCell(2).getStringCellValue(),
//                                row.getCell(3).getStringCellValue(),
//                                row.getCell(4).getStringCellValue()));
//
//                    }
//                    count.setText(String.valueOf(data.size()));
//
//                } catch (FileNotFoundException e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                } catch (IOException e2) {
//                    e2.printStackTrace();
//                }
//            }
//        });
//
//    }
}
