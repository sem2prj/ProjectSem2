/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import model.Employee;
import model.EmployeeDAOImplement;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class EmployeeController implements Initializable {

    private ObservableList<Employee> orderData;
    private static ObservableList<Employee> data;

    //FXML
    @FXML
    private TableView<Employee> tableView;
    @FXML
    private JFXTextField txtEplCode;
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXTextField txtPhone;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private ComboBox<String> cbPosition;
    @FXML
    private RadioButton rdMale;
    @FXML
    private RadioButton rdFemale;
    @FXML
    private ComboBox<String> cbDepartment;
    @FXML
    private ImageView imageView;
    @FXML
    private JFXTextField txtAddrees;
    @FXML
    private DatePicker txtDateBirth;
    @FXML
    private JFXTextField txtSalary;
    //combobox
    private final String position[] = {"Manager", "Employee"};
    private final String department[] = {"Store", "Sell", "Bussiness"};

    //image
    private FileChooser fileChooser;
    private File file;
    private Stage stage;
    private Image image;

    //gender
    private boolean gendercheck;

    //error
    @FXML
    private AnchorPane acPane;
    @FXML
    private Label lbCode;
    @FXML
    private Label lbUser;
    @FXML
    private Label lbPhone;
    @FXML
    private Label lbEmail;
    @FXML
    private Label lbSalary;
    @FXML
    private DatePicker txtDateWork;
    @FXML
    private Label lbAddrees;
    @FXML
    private JFXButton btnExcel;
    @FXML
    private JFXTextField fldSearch;
    @FXML
    private TableColumn<Employee, String> columeCode;
    @FXML
    private TableColumn<Employee, String> columuserName;
    @FXML
    private TableColumn<Employee, String> columPhone;
    @FXML
    private TableColumn<Employee, String> columEmail;
    @FXML
    private TableColumn<Employee, String> columnPosition;
    @FXML
    private TableColumn<Employee, String> columDPM;
    @FXML
    private Label lbImage;
    @FXML
    private Label lbDofBirth;
    @FXML
    private Label lbAWork;
    @FXML
    private ToggleGroup sex;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //load table
        loadTableForm();
        //load combobox
        initCombobox();
        //click table
        click();
        //load error
        errorValidate();
        //choose image
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Text File", "*.txt")
        );
        EmployeeDAOImplement eDAOIpl = new EmployeeDAOImplement();
        orderData = FXCollections.observableArrayList();
        //Notice Excel get all data !
        data = eDAOIpl.getAllEmployee();
        excel();
    }

    //add
    @FXML
    private void handleAdd(ActionEvent event) {
        //error
       boolean txtAddreenotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtAddrees, lbAddrees, "Addrees is requied ");
        if (!txtAddreenotEmpty) {
            txtAddrees.requestFocus();
        }

        boolean txtEmailnotSuitable = controller.ValidationController.isEmailSuitable(txtEmail, lbEmail, "Email is requied ,example :voduc204@gmail.com");
        if (!txtEmailnotSuitable) {
            txtEmail.requestFocus();
        }

        boolean txtPhonenotSuitable = controller.ValidationController.isPhoneSuitable(txtPhone, lbPhone, "Phone is requied ,example ex: +84 945 333 666, 0905123456,01233456789");
        if (!txtPhonenotSuitable) {
            txtPhone.requestFocus();
        }
        boolean txtUserNamenotEmpty = controller.ValidationController.isTextFieldHavingText(txtUsername, lbUser, "UserName is requied");
        if (!txtUserNamenotEmpty) {
            txtUsername.requestFocus();
        }

        boolean txtBarcodenotEmpty = controller.ValidationController.isTextFieldHavingBarcode(txtEplCode, lbCode, "EPLCode is requied");
        if (!txtBarcodenotEmpty) {
            txtEplCode.requestFocus();
        }

//        boolean txtSalarynotNumber = controller.ValidationController.isTextFieldTypeNumber(txtSalary, lbSalary, "Salary is number");
//        if (!txtSalarynotNumber) {
//            txtSalary.requestFocus();
//        }
        if (txtDateBirth.getValue() == null) {
            lbDofBirth.setText("Date is required");
        } else if (txtDateBirth.getValue() != null) {
            lbDofBirth.setText("");
        }
        if (txtDateWork.getValue() == null) {
            lbAWork.setText("Date is required");
        } else if (txtDateWork.getValue() != null) {
            lbAWork.setText("");
        }

        if (imageView.getImage() == null) {
            lbImage.setText("Image is required");
        } else if (imageView.getImage() != null) {
            lbImage.setText("");
        }

        if (txtBarcodenotEmpty && txtUserNamenotEmpty && txtPhonenotSuitable && txtEmailnotSuitable && txtAddreenotEmpty) {
            if (rdMale.isSelected()) {
                gendercheck = true;
            } else if (rdFemale.isSelected()) {
                gendercheck = false;
            }
            Employee employee = new Employee();
            BufferedImage bImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
            byte[] res;
            try (ByteArrayOutputStream s = new ByteArrayOutputStream()) {
                ImageIO.write(bImage, "png", s);
                res = s.toByteArray();
                Blob blob = new SerialBlob(res);
                employee.setImageBlob(blob);
                EmployeeDAOImplement eDAOIpl = new EmployeeDAOImplement();
                eDAOIpl.insertEmployee(txtEplCode.getText(), txtUsername.getText(), txtPhone.getText(), txtEmail.getText(), txtAddrees.getText(), gendercheck, java.sql.Date.valueOf(txtDateBirth.getValue()),
                        Double.parseDouble(txtSalary.getText()), cbPosition.getSelectionModel().getSelectedItem() + "", cbDepartment.getSelectionModel().getSelectedItem() + "", blob, java.sql.Date.valueOf(txtDateWork.getValue()));

            } catch (IOException | SQLException ex) {
                Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            emptyLabel();
        }
        loadTableForm();
    }

    //update
    @FXML
    private void handleUpdate(ActionEvent event) {

        boolean txtAddreenotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtAddrees, lbAddrees, "Addrees is requied ");
        if (!txtAddreenotEmpty) {
            txtAddrees.requestFocus();
        }

        boolean txtEmailnotSuitable = controller.ValidationController.isEmailSuitable(txtEmail, lbEmail, "Email is requied ,example :voduc204@gmail.com");
        if (!txtEmailnotSuitable) {
            txtEmail.requestFocus();
        }

        boolean txtPhonenotSuitable = controller.ValidationController.isPhoneSuitable(txtPhone, lbPhone, "Phone is requied ,example ex: +84 945 333 666, 0905123456,01233456789");
        if (!txtPhonenotSuitable) {
            txtPhone.requestFocus();
        }
        boolean txtUserNamenotEmpty = controller.ValidationController.isTextFieldHavingText(txtUsername, lbUser, "UserName is requied");
        if (!txtUserNamenotEmpty) {
            txtUsername.requestFocus();
        }

        boolean txtBarcodenotEmpty = controller.ValidationController.isTextFieldHavingBarcode(txtEplCode, lbCode, "EPLCode is requied");
        if (!txtBarcodenotEmpty) {
            txtEplCode.requestFocus();
        }

//        boolean txtSalarynotNumber = controller.ValidationController.isTextFieldTypeNumber(txtSalary, lbSalary, "Salary is number");
//        if (!txtSalarynotNumber) {
//            txtSalary.requestFocus();
//        }
        if (txtDateBirth.getValue() == null) {
            lbDofBirth.setText("Date is required");
        } else if (txtDateBirth.getValue() != null) {
            lbDofBirth.setText("");
        }
        if (txtDateWork.getValue() == null) {
            lbAWork.setText("Date is required");
        } else if (txtDateWork.getValue() != null) {
            lbAWork.setText("");
        }
        if (imageView.getImage() == null) {
            lbImage.setText("Image is required");
        } else if (imageView.getImage() != null) {
            lbImage.setText("");
        }
        if (txtBarcodenotEmpty && txtUserNamenotEmpty && txtPhonenotSuitable && txtEmailnotSuitable && txtAddreenotEmpty) {
            if (rdMale.isSelected()) {
                gendercheck = true;
            } else if (rdFemale.isSelected()) {
                gendercheck = false;
            }
            Employee employee = new Employee();
            BufferedImage bImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
            byte[] res;
            try (ByteArrayOutputStream s = new ByteArrayOutputStream()) {
                ImageIO.write(bImage, "png", s);
                res = s.toByteArray();
                Blob blob = new SerialBlob(res);
                employee.setImageBlob(blob);
                EmployeeDAOImplement eDAOIpl = new EmployeeDAOImplement();
                eDAOIpl.updateEmployee(txtEplCode.getText(), txtUsername.getText(), txtPhone.getText(), txtEmail.getText(), txtAddrees.getText(), gendercheck, java.sql.Date.valueOf(txtDateBirth.getValue()),
                        Double.parseDouble(txtSalary.getText()), cbPosition.getSelectionModel().getSelectedItem() + "", cbDepartment.getSelectionModel().getSelectedItem() + "", blob, java.sql.Date.valueOf(txtDateWork.getValue()));

            } catch (IOException | SQLException ex) {
                Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            emptyLabel();
        }
        loadTableForm();
    }

    //delete
    @FXML
    private void HandleDelete(ActionEvent event) {
        boolean txtBarcodenotEmpty = controller.ValidationController.isTextFieldHavingBarcode(txtEplCode, lbCode, "EPLCode is requied");
        if (!txtBarcodenotEmpty) {
            txtEplCode.requestFocus();
        }
        if (txtBarcodenotEmpty) {
            EmployeeDAOImplement eDAOIpl = new EmployeeDAOImplement();
            eDAOIpl.deleteEmployee(txtEplCode.getText());
        }
        loadTableForm();
    }

    @FXML
    private void handleChoose(ActionEvent event) {
//        Link file window image with imageview
//        Notice : add fileChooser on the initialize();
        stage = (Stage) acPane.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            System.out.println("" + file.getAbsolutePath());
            image = new Image(file.getAbsoluteFile().toURI().toString(), imageView.getFitWidth(), imageView.getFitHeight(), true, true);
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
        }
    }

    //Click on tableView
    private void click() {
        tableView.setOnMouseClicked((MouseEvent e) -> {
            Employee employee = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());
            txtEplCode.setText(employee.getEplCode());
            txtUsername.setText(employee.getUserName());
            txtPhone.setText(employee.getPhone());
            txtEmail.setText(employee.getEmail());
            if (employee.getGender() == true) {
                rdMale.setSelected(true);
                rdFemale.setSelected(false);
            } else if (employee.getGender() == false) {
                rdMale.setSelected(false);
                rdFemale.setSelected(true);
            }
            //Converted Object to Date
            Date dt = (Date) employee.getDateBirth();
            txtDateBirth.setValue(LocalDate.parse(dt.toString()));
            txtSalary.setText(String.format("%.2f", employee.getSalary()));
            txtAddrees.setText(employee.getAddrees());

            //Show image with eCode
            EmployeeDAOImplement eDI = new EmployeeDAOImplement();
            imageView.setImage(eDI.getImage(employee.getEplCode()));
            //Show Datepicker
            Date datetW = (Date) employee.getDateWork();
            txtDateWork.setValue(LocalDate.parse(datetW.toString()));
        });
    }

    private void initCombobox() {
        List<String> listPS = new ArrayList<>();
        for (String listSPS : position) {
            listPS.add(listSPS);
        }
        ObservableList obListPS = FXCollections.observableArrayList(listPS);
        cbPosition.setItems(obListPS);

        List<String> listDP = new ArrayList<>();
        for (String listSDP : department) {
            listDP.add(listSDP);
        }
        ObservableList obListDP = FXCollections.observableArrayList(listDP);
        cbDepartment.setItems(obListDP);
    }

    //Css error
    private void errorValidate() {
        lbCode.setStyle("-fx-text-fill:orange");
        lbUser.setStyle("-fx-text-fill:orange");
        lbPhone.setStyle("-fx-text-fill:orange");
        lbEmail.setStyle("-fx-text-fill:orange");
        lbAddrees.setStyle("-fx-text-fill:orange");
        lbSalary.setStyle("-fx-text-fill:orange");
        lbImage.setStyle("-fx-text-fill:orange");
        lbDofBirth.setStyle("-fx-text-fill:orange");
        lbAWork.setStyle("-fx-text-fill:orange");
    }

    @FXML
    private void handleClear(ActionEvent event) {
        clear();
    }

    private void emptyLabel() {
        lbCode.setText("");
        lbUser.setText("");
        lbPhone.setText("");
        lbEmail.setText("");
        lbAddrees.setText("");
        lbSalary.setText("");
        lbImage.setText("");
        lbDofBirth.setText("");
        lbAWork.setText("");
    }

    private void clear() {
        txtEplCode.clear();
        txtUsername.clear();
        rdMale.setSelected(false);
        rdFemale.setSelected(false);
        txtAddrees.clear();
        txtPhone.clear();
        txtEmail.clear();
        txtDateBirth.setValue(LocalDate.now());
        txtSalary.clear();
        imageView.setImage(null);
        txtDateWork.setValue(LocalDate.now());
    }

    private void loadTableForm() {
        EmployeeDAOImplement eDAOIpl = new EmployeeDAOImplement();
        ObservableList<Employee> listEmployee = eDAOIpl.getAllEmployee();
        tableView.setItems(listEmployee);
        //cell of tableView
        columeCode.setCellValueFactory(new PropertyValueFactory<>("EplCode"));
        columuserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        columPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        columDPM.setCellValueFactory(new PropertyValueFactory<>("department"));
    }

    public void excel() {
        btnExcel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FileOutputStream fileOut = new FileOutputStream("E:\\Excel\\ExcelEmployee.xls");
                    HSSFWorkbook workbook = new HSSFWorkbook();
                    HSSFSheet worksheet = workbook.createSheet("Employee");
                    //row 0
                    HSSFRow row1 = worksheet.createRow((short) 0);
                    //add cell 1
                    HSSFCell cellA1 = row1.createCell((short) 0);
                    cellA1.setCellValue("EmployeeCode");
                    HSSFCellStyle cellStyle = workbook.createCellStyle();
                    cellA1.setCellStyle(cellStyle);
                    //add cell 2
                    HSSFCell cellB1 = row1.createCell((short) 1);
                    cellB1.setCellValue("Name");
                    cellStyle = workbook.createCellStyle();
                    cellB1.setCellStyle(cellStyle);
                    //add cell 3
                    HSSFCell cellC1 = row1.createCell((short) 2);
                    cellC1.setCellValue("Phone");
                    cellStyle = workbook.createCellStyle();
                    cellC1.setCellStyle(cellStyle);
                    //add cell 4
                    HSSFCell cellD1 = row1.createCell((short) 3);
                    cellD1.setCellValue("Email");
                    cellStyle = workbook.createCellStyle();
                    cellD1.setCellStyle(cellStyle);
                    //add cell 5
                    HSSFCell cellE1 = row1.createCell((short) 4);
                    cellE1.setCellValue("Addrees");
                    cellStyle = workbook.createCellStyle();
                    cellE1.setCellStyle(cellStyle);
                    //add cell 6
                    HSSFCell cellF1 = row1.createCell((short) 5);
                    cellF1.setCellValue("Date Of Birth");
                    cellStyle = workbook.createCellStyle();
                    cellF1.setCellStyle(cellStyle);
                    //add cell 7
                    HSSFCell cellG1 = row1.createCell((short) 6);
                    cellG1.setCellValue("Department");
                    cellStyle = workbook.createCellStyle();
                    cellG1.setCellStyle(cellStyle);
                    //add cell 8
                    HSSFCell cellH1 = row1.createCell((short) 7);
                    cellH1.setCellValue("Salary");
                    cellStyle = workbook.createCellStyle();
                    cellH1.setCellStyle(cellStyle);
                    //add cell 9
                    HSSFCell cellI1 = row1.createCell((short) 8);
                    cellI1.setCellValue("Position");
                    cellStyle = workbook.createCellStyle();
                    cellI1.setCellStyle(cellStyle);
                    //add cell 10
                    HSSFCell cellK1 = row1.createCell((short) 9);
                    cellK1.setCellValue("Date At Work");
                    cellStyle = workbook.createCellStyle();
                    cellK1.setCellStyle(cellStyle);
                    for (int i = 0; i < data.size(); i++) {
                        HSSFRow row2 = worksheet.createRow((short) i + 1);
                        row2.createCell(0).setCellValue(((Employee) data.get(i)).getEplCode());
                        row2.createCell(1).setCellValue(((Employee) data.get(i)).getUserName());
                        row2.createCell(2).setCellValue(((Employee) data.get(i)).getPhone());
                        row2.createCell(3).setCellValue(((Employee) data.get(i)).getEmail());
                        row2.createCell(4).setCellValue(((Employee) data.get(i)).getAddrees());
                        row2.createCell(5).setCellValue(((Employee) data.get(i)).getDateBirth().toString());
                        row2.createCell(6).setCellValue(((Employee) data.get(i)).getDepartment());
                        row2.createCell(7).setCellValue(((Employee) data.get(i)).getSalary());
                        row2.createCell(8).setCellValue(((Employee) data.get(i)).getPosition());
                        row2.createCell(9).setCellValue(((Employee) data.get(i)).getDateWork().toString());
                    }
                    workbook.write(fileOut);
                    if (!data.isEmpty()) {
                        AlertDialog.display("Info", "Excel Insert E:\\Excel\\ExcelEmployee Successfully");
                    } else {
                        AlertDialog.display("Info", "Excel Insert Failure");
                    }
                    fileOut.flush();
                    fileOut.close();

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    private void handleKeySearch(KeyEvent event) {
        EmployeeDAOImplement eDAOIpl = new EmployeeDAOImplement();
        data = eDAOIpl.getAllEmployee();
        FilteredList<Employee> filteredData = new FilteredList<>(data, e -> true);
        fldSearch.setOnKeyReleased(e -> {
            fldSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Employee>) employee -> {
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (employee.getEplCode().contains(newValue)) {
                        return true;
                    }
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Employee> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);
        });
    }
}
