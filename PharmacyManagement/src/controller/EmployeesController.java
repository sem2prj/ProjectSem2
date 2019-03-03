/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.DAOEmployees;
import model.Employees;
import model.EmployeesDAOImplement;
//import model.ListEmployees;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
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

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    private JFXComboBox<String> comboRole;

    private final ObservableList<String> data = FXCollections.observableArrayList("admin", "staff", "customers");

    public static ObservableList<Employees> ListEmployees = FXCollections.observableArrayList();
    @FXML
    private JFXTextField txtCode;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtPhone;
    @FXML
    private DatePicker txtDate;
    @FXML
    private JFXTextField txtJob;
    @FXML
    private JFXTextField txtSalary;
    @FXML
    private JFXTextField txtCommis;
    @FXML
    private JFXTextField txtManager;
    @FXML
    private JFXTextField txtDeparment;
    @FXML
    private TableView<Employees> tbView;
    @FXML
    private JFXButton btnAddNew;
//        comboRole.setItems(data);
    @FXML
    private JFXTextField search;

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
        //call loadCLFront();
        loadCLFront();
        setCellValueFromTableToTextField();

    }

// method add new memember
    @FXML
    private void handleAddMew(ActionEvent event) {
        EmployeesDAOImplement employ = new EmployeesDAOImplement();
        employ.insertEmployees(Integer.parseInt(txtCode.getText()), txtName.getText(), txtPhone.getText(), txtEmail.getText(),
                java.sql.Date.valueOf(txtDate.getValue()), txtJob.getText(), Integer.parseInt(txtSalary.getText()),
                Double.parseDouble(txtCommis.getText()), Integer.parseInt(txtManager.getText()), Integer.parseInt(txtDeparment.getText()));
    }
// method update memember

    @FXML
    private void handleUpdate(ActionEvent event) {
        EmployeesDAOImplement employ = new EmployeesDAOImplement();
        employ.updateEmployees(Integer.parseInt(txtCode.getText()), txtName.getText(), txtPhone.getText(), txtEmail.getText(),
                java.sql.Date.valueOf(txtDate.getValue()), txtJob.getText(), Integer.parseInt(txtSalary.getText()),
                Double.parseDouble(txtCommis.getText()), Integer.parseInt(txtManager.getText()), Integer.parseInt(txtDeparment.getText()));
    }
//method delete

    @FXML
    private void handleDelete(ActionEvent event) {
        EmployeesDAOImplement employ = new EmployeesDAOImplement();
        employ.deleteEmployees(Integer.parseInt(txtCode.getText()));
    }

//method load form and read database
    public void loadCLFront() {
        // cái này dễ :))
        TableColumn<Employees, Integer> code = new TableColumn<>("Code");
        code.setPrefWidth(70);

        TableColumn<Employees, String> name = new TableColumn("Name");
        name.setPrefWidth(70);

        TableColumn<Employees, String> phone = new TableColumn("Phone");
        phone.setPrefWidth(70);

        TableColumn<Employees, String> email = new TableColumn("Email");
        email.setPrefWidth(70);

        TableColumn<Employees, DatePicker> date = new TableColumn("Date");
        date.setPrefWidth(70);

        TableColumn<Employees, String> jobId = new TableColumn("JobID");
        jobId.setPrefWidth(70);

        TableColumn<Employees, Integer> salary = new TableColumn("Salary");
        salary.setPrefWidth(70);

        TableColumn<Employees, Double> commis = new TableColumn("CMSS");
        commis.setPrefWidth(70);

        TableColumn<Employees, Integer> manage = new TableColumn("Manage");
        manage.setPrefWidth(70);

        TableColumn<Employees, Integer> department = new TableColumn("DPM");
        department.setPrefWidth(70);

        tbView.getColumns().addAll(code, name, phone, email, date, jobId, salary, commis, manage, department);

        //lấy employee_id theo model Employees 
        code.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        date.setCellValueFactory(new PropertyValueFactory<>("hire_date"));
        jobId.setCellValueFactory(new PropertyValueFactory<>("job_id"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        commis.setCellValueFactory(new PropertyValueFactory<>("commission_pct"));
        manage.setCellValueFactory(new PropertyValueFactory<>("manager_id"));
        department.setCellValueFactory(new PropertyValueFactory<>("department_id"));


        EmployeesDAOImplement employ = new EmployeesDAOImplement();
        ObservableList<Employees> ListEP = employ.getAllEmployee();

        tbView.setItems(ListEP);
    }

    @FXML
    private void handleSearchId(ActionEvent event) {
        EmployeesDAOImplement employ = new EmployeesDAOImplement();
        ObservableList<Employees> ListEP=employ.searchIdEmployee(Integer.parseInt(txtCode.getText()));
        if (ListEP.size()>0) {
            tbView.setItems(ListEP);
        }else{
            System.out.println("Not found");
        }      
    }


    private void setCellValueFromTableToTextField() {
        tbView.setOnMouseClicked(e->{
            Employees employee =tbView.getItems().get(tbView.getSelectionModel().getSelectedIndex());
            txtCode.setText( Integer.toString(employee.getEmployee_id()));
        });
        
        
        
        
//        table_employees.setOnMouseClicked(e -> {
//            ListEmployees le = table_employees.getItems().get(table_employees.getSelectionModel().getSelectedIndex());
//            txt_codeemoloyees.setText(le.getCode());
//            txt_name.setText(le.getName());
//            txt_address.setText(le.getAddrees());
//            txt_phone.setText(le.getPhone());
//            txt_role.setText(le.getRoles());
//            txt_date.setValue(LocalDate.parse(le.getDate_created()));
//        });
    }

    public void export() {
//        btnExport.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                try {
//                    FileOutputStream fileOut = new FileOutputStream("poi-test.xls");
//                    HSSFWorkbook workbook = new HSSFWorkbook();
//                    HSSFSheet worksheet = workbook.createSheet("POI Worksheet");
//
//                    //row 0
//                    HSSFRow row1 = worksheet.createRow((short) 0);
//
//                    //add cell 1
//                    HSSFCell cellA1 = row1.createCell((short) 0);
//                    cellA1.setCellValue("Code");
//                    HSSFCellStyle cellStyle = workbook.createCellStyle();
////                     cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
////                     cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//                    cellA1.setCellStyle(cellStyle);
//
//                    //add cell 2
//                    HSSFCell cellB1 = row1.createCell((short) 1);
//                    cellB1.setCellValue("Name");
//                    cellStyle = workbook.createCellStyle();
////                     cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
////                     cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//                    cellB1.setCellStyle(cellStyle);
//
//                    //add cell 3
//                    HSSFCell cellC1 = row1.createCell((short) 2);
//                    cellC1.setCellValue("Address");
//                    cellStyle = workbook.createCellStyle();
////                     cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
////                     cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//                    cellC1.setCellStyle(cellStyle);
//
//                    //add cell 4
//                    HSSFCell cellD1 = row1.createCell((short) 3);
//                    cellD1.setCellValue("Phone");
//                    cellStyle = workbook.createCellStyle();
////                     cellStyle.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
////                     cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//                    cellD1.setCellStyle(cellStyle);
//
//                    //add cell 5
//                    HSSFCell cellE1 = row1.createCell((short) 4);
//                    cellE1.setCellValue("Role");
//                    cellStyle = workbook.createCellStyle();
////                     cellStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
////                     cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//                    cellE1.setCellStyle(cellStyle);
//
//                    //add cell 6
//                    HSSFCell cellF1 = row1.createCell((short) 5);
//                    cellF1.setCellValue("Role");
//                    cellStyle = workbook.createCellStyle();
////                     cellStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
////                     cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//                    cellF1.setCellStyle(cellStyle);
//
////                    for (int i = 0; i < data.size(); i++) {
////                        HSSFRow row2 = worksheet.createRow((short) i + 1);
////                        row2.createCell(0).setCellValue(((ListEmployees) data.get(i)).getCode());
////                        row2.createCell(1).setCellValue(((ListEmployees) data.get(i)).getName());
////                        row2.createCell(2).setCellValue(((ListEmployees) data.get(i)).getAddrees());
////                        row2.createCell(3).setCellValue(((ListEmployees) data.get(i)).getPhone());
////                        row2.createCell(4).setCellValue(((ListEmployees) data.get(i)).getRoles());
////                        row2.createCell(5).setCellValue(((ListEmployees) data.get(i)).getDate_created());
//
//                    }
////                    workbook.write(fileOut);
////                    fileOut.flush();
////                    fileOut.close();
////
////                } catch (FileNotFoundException ex) {
////                    Logger.getLogger(EmployeesController.class.getName()).log(Level.SEVERE, null, ex);
////                } catch (IOException ex) {
////                    Logger.getLogger(EmployeesController.class.getName()).log(Level.SEVERE, null, ex);
////                }
////            }
//
//        });
//    }

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
    }

    private void handle_print(ActionEvent event) throws JRException {

//        JasperReport jp = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/teacherPay.jrxml"));
        JasperDesign jasperDesign = JRXmlLoader.load("model/MyReport.jrxml");
        String query = "select * from employee";
        JRDesignQuery jrquery = new JRDesignQuery();
        jrquery.setText(query);
        jasperDesign.setQuery(jrquery);

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint JasperPrint = JasperFillManager.fillReport(jasperReport, null, con);
        JRViewer viewer = new JRViewer(JasperPrint);

        /*JasperReport jasperReport = JasperCompileManager.compileReport("E:\CabinetJava\PharmacyManagement\src\model\MyReport.jrxml");
             JasperPrint JasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
             JRViewer viewer = new JRViewer(JasperPrint);*/
        viewer.setOpaque(true);
        viewer.setVisible(true);

    }

}
