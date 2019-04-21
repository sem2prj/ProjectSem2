/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import static controller.MainController.infoUser;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import model.Employee;
import model.EmployeeDAOImplement;
import model.User;
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
    private ObservableList<Employee> dataEmployee;

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
    private final String department[] = {"Warehouse", "Sell", "Business"};
    private final String roles[] = {"User", "Admin",};

    //image
    private FileChooser fileChooser;
    private File file;
    private Stage stage;
    private Image image;

    //excel 
    private Stage stageExcel;
//    private Stage stageImportExcel;
//    private File fileImport;

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
    private TableColumn<Employee, String> columDPM;
    @FXML
    private Label lbImage;
    @FXML
    private Label lbDofBirth;
    @FXML
    private Label lbAWork;
    @FXML
    private ToggleGroup sex;
    @FXML
    private JFXPasswordField txtPass;
    @FXML
    private Label labelPass;
    @FXML
    private JFXPasswordField txtConfirmPass;
    @FXML
    private Label lbConfirm;

    private int id;
    @FXML
    private Label labelRoles;
    @FXML
    private JFXComboBox<String> cbRoles;
    @FXML
    private JFXButton buttonAdd;
    @FXML
    private JFXButton buttonEdit;
    @FXML
    private JFXButton buttonDelete;
    @FXML
    private JFXButton buttonClear;
    @FXML
    private JFXButton buttonExcel;
    @FXML
    private JFXButton buttonChangePass;
    @FXML
    private JFXTextField txtFullName;
    @FXML
    private Label lbFullName;

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
        dataEmployee = eDAOIpl.getAllEmployee();
        excel();
        mission();
        //search
        FilteredList<Employee> filteredData = new FilteredList<>(dataEmployee, e -> true);
        fldSearch.setOnKeyReleased(e -> {
            fldSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Employee>) employee -> {
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    if (employee.getEplCode().contains(newValue)) {
                        return true;
                    }
                    if (employee.getUserName().contains(newValue)) {
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

    //add
    @FXML
    private void handleAdd(ActionEvent event) {

        boolean isPasswordNotEmpty = controller.ValidationController.isPasswordFieldHavingText(txtPass, labelPass, "Password must be filled out");
        if (!isPasswordNotEmpty) {
            txtPass.requestFocus();
        }

        boolean isConfirmNotEmpty = controller.ValidationController.isPasswordFieldHavingText(txtConfirmPass, lbConfirm, "Confirm password must be filled out");
        if (!isConfirmNotEmpty) {
            txtConfirmPass.requestFocus();
        }

        boolean arePasswordsametoREPassword = controller.ValidationController.arePasswordAndREPasswordSame(txtPass, txtConfirmPass, lbConfirm, "Your password and confirmation password do not match");
        if (!arePasswordsametoREPassword) {
            txtPass.requestFocus();
        }
        boolean txtAddreenotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtAddrees, lbAddrees, "Addrees must be filled out ");
        if (!txtAddreenotEmpty) {
            txtAddrees.requestFocus();
        }

        boolean txtEmailnotSuitable = controller.ValidationController.isTextFieldHavingText(txtEmail, lbEmail, "Email must be filled out");
        if (!txtEmailnotSuitable) {
            txtEmail.requestFocus();
        }

        boolean txtPhonenotSuitable = controller.ValidationController.isTextFieldHavingText(txtPhone, lbPhone, "Phone must be filled out");
        if (!txtPhonenotSuitable) {
            txtPhone.requestFocus();
        }
        boolean txtUserNamenotEmpty = controller.ValidationController.isTextFieldHavingText(txtUsername, lbUser, "Name must be filled out");
        if (!txtUserNamenotEmpty) {
            txtUsername.requestFocus();
        }

        boolean txtBarcodenotEmpty = controller.ValidationController.isTextFieldHavingBarcode(txtEplCode, lbCode, "Code must be filled out");
        if (!txtBarcodenotEmpty) {
            txtEplCode.requestFocus();
        }

        boolean txtSalarynotNumber = controller.ValidationController.isTextFieldTypeNumber(txtSalary, lbSalary, "Salary must be filled out");
        if (!txtSalarynotNumber) {
            txtSalary.requestFocus();
        }
        if (txtDateBirth.getValue() == null) {
            lbDofBirth.setText("Date must be filled out");
        } else if (txtDateBirth.getValue() != null) {
            lbDofBirth.setText("");
        }
        if (txtDateWork.getValue() == null) {
            lbAWork.setText("Date must be filled out");
        } else if (txtDateWork.getValue() != null) {
            lbAWork.setText("");
        }

        if (imageView.getImage() == null) {
            lbImage.setText("Image is required");
        } else if (imageView.getImage() != null) {
            lbImage.setText("");
        }

        if (ValidationController.checkPolymerCode(txtEplCode.getText())) {
            if (imageView.getImage() != null && txtBarcodenotEmpty && txtUserNamenotEmpty && txtPhonenotSuitable && txtEmailnotSuitable && txtAddreenotEmpty && isPasswordNotEmpty && isConfirmNotEmpty && arePasswordsametoREPassword && txtSalarynotNumber) {
                boolean isEmailTrue = controller.ValidationController.isEmailSuitable(txtEmail, lbEmail, "Example: xxx@yyy.com");
                boolean isPhoneTrue = controller.ValidationController.isPhoneSuitable(txtPhone, lbPhone, "Example: +84 925 111 4456, 0905999999,...");
                boolean isUsernameTrue = controller.ValidationController.isUsernameTrueType(txtUsername, lbUser, "Username is not suitable");
                boolean isPasswordTrue = controller.ValidationController.isPasswordTrueType(txtPass, labelPass, "Password is not suitable");
                if (isPhoneTrue && isEmailTrue && isUsernameTrue && isPasswordTrue) {
                    if (rdMale.isSelected()) {
                        gendercheck = true;
                    } else if (rdFemale.isSelected()) {
                        gendercheck = false;
                    }

                    BufferedImage bImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
                    String password = PasswordHash.encryptPass(txtConfirmPass.getText());
                    System.out.println(password);
                    byte[] res;
                    try (ByteArrayOutputStream s = new ByteArrayOutputStream()) {
                        ImageIO.write(bImage, "png", s);
                        res = s.toByteArray();
                        Blob blob = new SerialBlob(res);
                        Employee employee = new Employee();
                        employee.setImageBlob(blob);
                        EmployeeDAOImplement eDAOIpl = new EmployeeDAOImplement();
                        String username = txtUsername.getText().trim().replaceAll("\\s+", "");
                        String addrees = txtAddrees.getText().trim().replaceAll("\\s+", " ");
                        eDAOIpl.insertEmployee(username, username, password, txtFullName.getText(), txtEplCode.getText(), txtPhone.getText(), txtEmail.getText(), addrees, gendercheck, java.sql.Date.valueOf(txtDateBirth.getValue()),
                                Double.parseDouble(txtSalary.getText()), cbDepartment.getSelectionModel().getSelectedItem() + "", blob, java.sql.Date.valueOf(txtDateWork.getValue()), cbRoles.getSelectionModel().getSelectedItem() + "");

                    } catch (IOException | SQLException ex) {
                        Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    emptyLabel();
                    clear();
                }
            } else {
//               AlertDialog.display("Info", "Not found);
            }

        }
        loadTableForm();
    }

    //update
    @FXML
    private void handleUpdate(ActionEvent event) {

        boolean txtAddreenotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtAddrees, lbAddrees, "Addrees must be filled out");
        if (!txtAddreenotEmpty) {
            txtAddrees.requestFocus();
        }

        boolean txtEmailnotSuitable = controller.ValidationController.isTextFieldHavingText(txtEmail, lbEmail, "Email must be filled out");
        if (!txtEmailnotSuitable) {
            txtEmail.requestFocus();
        }

        boolean txtPhonenotSuitable = controller.ValidationController.isTextFieldHavingText(txtPhone, lbPhone, "Phone must be filled out");
        if (!txtPhonenotSuitable) {
            txtPhone.requestFocus();
        }
        boolean txtUserNamenotEmpty = controller.ValidationController.isTextFieldHavingText(txtUsername, lbUser, "Name must be filled out");
        if (!txtUserNamenotEmpty) {
            txtUsername.requestFocus();
        }

        boolean txtBarcodenotEmpty = controller.ValidationController.isTextFieldHavingBarcode(txtEplCode, lbCode, "Code must be filled out");
        if (!txtBarcodenotEmpty) {
            txtEplCode.requestFocus();
        }

        boolean txtSalarynotNumber = controller.ValidationController.isTextFieldTypeNumber(txtSalary, lbSalary, "Salary must be filled out");
        if (!txtSalarynotNumber) {
            txtSalary.requestFocus();
        }
        if (txtDateBirth.getValue() == null) {
            lbDofBirth.setText("Date must be filled out");
        } else if (txtDateBirth.getValue() != null) {
            lbDofBirth.setText("");
        }
        if (txtDateWork.getValue() == null) {
            lbAWork.setText("Date must be filled out");
        } else if (txtDateWork.getValue() != null) {
            lbAWork.setText("");
        }
        if (imageView.getImage() == null) {
            lbImage.setText("Image is required");
        } else if (imageView.getImage() != null) {
            lbImage.setText("");
        }

        if (imageView.getImage() != null && txtDateWork.getValue() != null && txtDateBirth.getValue() != null && txtBarcodenotEmpty && txtUserNamenotEmpty && txtPhonenotSuitable && txtEmailnotSuitable && txtAddreenotEmpty && txtSalarynotNumber) {
            boolean isEmailTrue = controller.ValidationController.isEmailSuitable(txtEmail, lbEmail, "Example: xxx@yyy.com");
            boolean isPhoneTrue = controller.ValidationController.isPhoneSuitable(txtPhone, lbPhone, "Example: +84 925 111 4456, 0905999999,...");
            boolean isUsernameTrue = controller.ValidationController.isUsernameTrueType(txtUsername, lbUser, "Username is not suitable");

            if (isEmailTrue && isPhoneTrue && isUsernameTrue) {
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
                    String addrees = txtAddrees.getText().trim().replaceAll("\\s+", " ");
                    eDAOIpl.updateEmployee(txtUsername.getText(), txtFullName.getText(), id, txtEplCode.getText(), txtPhone.getText(), txtEmail.getText(), addrees, gendercheck, java.sql.Date.valueOf(txtDateBirth.getValue()),
                            Double.parseDouble(txtSalary.getText()), cbDepartment.getSelectionModel().getSelectedItem() + "", blob, java.sql.Date.valueOf(txtDateWork.getValue()), cbRoles.getSelectionModel().getSelectedItem() + "");

                } catch (IOException | SQLException ex) {
                    Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                }
                emptyLabel();
                clear();
            }

        }
        loadTableForm();

    }

    //delete
    @FXML
    private void HandleDelete(ActionEvent event) {
        boolean txtUsernamenotEmpty = controller.ValidationController.isTextFieldHavingBarcode(txtUsername, lbUser, "Code must be filled out");
        if (!txtUsernamenotEmpty) {
            txtUsername.requestFocus();
        }
        if (txtUsernamenotEmpty) {
            EmployeeDAOImplement eDAOIpl = new EmployeeDAOImplement();
            eDAOIpl.deleteEmployee(txtUsername.getText());
            emptyLabel();
            clear();
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
            cbDepartment.setValue(employee.getDepartment());
            cbRoles.setValue(employee.getRole());
            //Transaction id employee to update
            id = employee.getId();
        });
    }

    private void initCombobox() {

        List<String> listDP = new ArrayList<>();
        for (String listSDP : department) {
            listDP.add(listSDP);
        }
        ObservableList obListDP = FXCollections.observableArrayList(listDP);
        cbDepartment.setItems(obListDP);

        List<String> listRL = new ArrayList<>();
        for (String listSRL : roles) {
            listRL.add(listSRL);
        }
        ObservableList obListRL = FXCollections.observableArrayList(listRL);
        cbRoles.setItems(obListRL);
    }

    //Css error
    private void errorValidate() {
        lbCode.setStyle("-fx-text-fill:#FF0000");
        lbUser.setStyle("-fx-text-fill:#FF0000");
        lbPhone.setStyle("-fx-text-fill:#FF0000");
        lbEmail.setStyle("-fx-text-fill:#FF0000");
        lbAddrees.setStyle("-fx-text-fill:#FF0000");
        lbSalary.setStyle("-fx-text-fill:#FF0000");
        lbImage.setStyle("-fx-text-fill:#FF0000");
        lbDofBirth.setStyle("-fx-text-fill:#FF0000");
        lbAWork.setStyle("-fx-text-fill:#FF0000");
        labelPass.setStyle("-fx-text-fill:#FF0000");
        lbConfirm.setStyle("-fx-text-fill:#FF0000");
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
        txtFullName.clear();
        txtEplCode.clear();
        txtUsername.clear();
        rdMale.setSelected(false);
        rdFemale.setSelected(false);
        txtAddrees.clear();
        txtPhone.clear();
        txtEmail.clear();
        txtDateBirth.getEditor().clear();
//        txtDateBirth.setValue(LocalDate.now());
        txtSalary.clear();
        imageView.setImage(null);
        txtDateWork.getEditor().clear();
        txtPass.clear();
        txtConfirmPass.clear();
        cbDepartment.getSelectionModel().clearSelection();
        cbRoles.getSelectionModel().clearSelection();
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
        columDPM.setCellValueFactory(new PropertyValueFactory<>("department"));
    }

    public void excel() {
        buttonExcel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                FileChooser fileChooserExCel = new FileChooser();
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
                HSSFCell cellG1 = row1.createCell((short) 5);
                cellG1.setCellValue("Department");
                cellStyle = workbook.createCellStyle();
                cellG1.setCellStyle(cellStyle);
                //add cell 7
                HSSFCell cellH1 = row1.createCell((short) 6);
                cellH1.setCellValue("Salary");
                cellStyle = workbook.createCellStyle();
                cellH1.setCellStyle(cellStyle);
                //add cell 8
                HSSFCell cellK1 = row1.createCell((short) 7);
                cellK1.setCellValue("Date At Work");
                cellStyle = workbook.createCellStyle();
                cellK1.setCellStyle(cellStyle);
                for (int i = 0; i < dataEmployee.size(); i++) {
                    HSSFRow row2 = worksheet.createRow((short) i + 1);
                    row2.createCell(0).setCellValue(((Employee) dataEmployee.get(i)).getEplCode());
                    row2.createCell(1).setCellValue(((Employee) dataEmployee.get(i)).getUserName());
                    row2.createCell(2).setCellValue(((Employee) dataEmployee.get(i)).getPhone());
                    row2.createCell(3).setCellValue(((Employee) dataEmployee.get(i)).getEmail());
                    row2.createCell(4).setCellValue(((Employee) dataEmployee.get(i)).getAddrees());
                    row2.createCell(5).setCellValue(((Employee) dataEmployee.get(i)).getDepartment());
                    row2.createCell(6).setCellValue(((Employee) dataEmployee.get(i)).getSalary());
                    row2.createCell(7).setCellValue(((Employee) dataEmployee.get(i)).getDateWork().toString());
                }

                //Set extension filter to .xlsx files
//                FileChooser.ExtensionFilter extFilterXLSX = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
                fileChooserExCel.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx"),
                        new FileChooser.ExtensionFilter("Excel files (*.xlsm)", "*.xlsm"),
                        new FileChooser.ExtensionFilter("Excel files (*.xls)", "*.xls")
                );
                //Show save file dialog
                stageExcel = (Stage) acPane.getScene().getWindow();
                File fileExcel = fileChooserExCel.showSaveDialog(stageExcel);

                //If file is not null, write to file using output stream.
                if (fileExcel != null) {
                    try (FileOutputStream outputStream = new FileOutputStream(fileExcel.getAbsolutePath())) {
                        workbook.write(outputStream);

                        outputStream.flush();
                        outputStream.close();

                    } catch (IOException ex) {
                        Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

//    private void importExcelData() {
//        btnImport.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                data.clear();
//                FileChooser fileChooserImport = new FileChooser();
//                fileChooserImport.getExtensionFilters().addAll(
//                        new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx"),
//                        new FileChooser.ExtensionFilter("Excel files (*.xlsm)", "*.xlsm"),
//                        new FileChooser.ExtensionFilter("Excel files (*.xls)", "*.xls")
//                );
//
//                stageImportExcel = (Stage) acPane.getScene().getWindow();
//                fileImport = fileChooser.showOpenDialog(stage);
//
//                HSSFWorkbook workbook;
//                try {
//                    workbook = new HSSFWorkbook(new FileInputStream(fileImport.getAbsolutePath()));
//                    HSSFSheet sheet = workbook.getSheetAt(0);
//                    HSSFRow row;
//                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//                        row = sheet.getRow(i);
//                        //add constructor 
//                        data.add(new Employee(
//                                row.getCell(0).getStringCellValue(),
//                                row.getCell(1).getStringCellValue(),
//                                row.getCell(2).getStringCellValue(),
//                                row.getCell(3).getStringCellValue(),
//                                row.getCell(4).getStringCellValue(),
//                                row.getCell(5).getStringCellValue(),
//                                row.getCell(6).getStringCellValue(),
//                                row.getCell(7).getStringCellValue()
//                                )
//                        );
//                        
//                    }
//                } catch (IOException ex) {
//                    Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            }
//
//        });
//    }
    @FXML
    private void handleChangePass(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ChangePass.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/changepass.css").toExternalForm());
        stage.getIcons().add(new Image("/image/hyhy.png"));
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Change Password");
        stage.setScene(scene);
        stage.show();
    }

    private void mission() {
        infoUser = LoginController.ListUserLogin;
        for (User user : infoUser) {
            if (user.getMission().equals("Admin") && user.getDeparment().equals("Business")) {
                buttonAdd.setDisable(false);
                buttonDelete.setDisable(false);
                buttonEdit.setDisable(false);
                buttonExcel.setDisable(false);
                buttonChangePass.setDisable(false);
            }else if (user.getMission().equals("User") && user.getDeparment().equals("Warehouse") 
                    || user.getMission().equals("User") && user.getDeparment().equals("Sell")||user.getMission().equals("") && user.getDeparment().equals("")
                    ||user.getMission().equals("Admin") && user.getDeparment().equals("Sell") || user.getMission().equals("Admin") && user.getDeparment().equals("Warehouse")) {
                buttonAdd.setDisable(true);
                buttonAdd.setStyle("-fx-background-color: #393535; -fx-text-fill: white;");
                buttonDelete.setDisable(true);
                buttonDelete.setStyle("-fx-background-color: #393535; -fx-text-fill: white;");
                buttonEdit.setDisable(true);
                buttonEdit.setStyle("-fx-background-color: #393535; -fx-text-fill: white;");
                buttonExcel.setDisable(true);
                buttonExcel.setStyle("-fx-background-color: #393535; -fx-text-fill: white;");
                buttonChangePass.setDisable(true);
                buttonChangePass.setStyle("-fx-background-color: #393535; -fx-text-fill: white;");
                dataEmployee.clear();
            } else if (user.getMission().equals("User") && user.getDeparment().equals("Business")) {
                buttonAdd.setDisable(true);
                buttonAdd.setStyle("-fx-background-color: #393535; -fx-text-fill: white;");
                buttonDelete.setDisable(true);
                buttonDelete.setStyle("-fx-background-color: #393535; -fx-text-fill: white;");
                buttonEdit.setDisable(true);
                buttonEdit.setStyle("-fx-background-color: #393535; -fx-text-fill: white;");
                buttonExcel.setDisable(false);
                buttonChangePass.setDisable(true);
                buttonChangePass.setStyle("-fx-background-color: #393535; -fx-text-fill: white;");
                dataEmployee.clear();
            }
        }
    }

    /*
    private void SaveFile(String content, File file) {
        try {
            FileWriter fileWriter = null;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    try {
                    FileOutputStream fileOut = new FileOutputStream("E:\\ExcelEmployee.xls");
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
                    HSSFCell cellK1 = row1.createCell((short) 8);
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
                        row2.createCell(8).setCellValue(((Employee) data.get(i)).getDateWork().toString());
                    }
                    workbook.write(fileOut);
                    if (!data.isEmpty()) {
                        AlertDialog.display("Info", "Excel Insert E:\\ExcelEmployee.xls Successfully");
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
     */
}
