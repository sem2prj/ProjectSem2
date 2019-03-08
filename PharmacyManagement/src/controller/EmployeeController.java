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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventType;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import model.Employee;
import model.EmployeeDAOImplement;


/**
 * FXML Controller class
 *
 * @author PC
 */
public class EmployeeController implements Initializable {

    private ObservableList<Employee> orderData;

    //FXML
    @FXML
    private TableView<Employee> tableView;
    @FXML
    private JFXButton btnChoose;
    @FXML
    private JFXTextField txtEplCode;
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXTextField txtPhone;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtConfirmPW;
    @FXML
    private JFXTextField txtPassword;
    @FXML
    private ComboBox<String> cbPosition;
    @FXML
    private HBox hboxGender;
    @FXML
    private RadioButton rdMale;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton rdFemale;
    @FXML
    private ComboBox<String> cbDepartment;
    @FXML
    private ImageView imageView;
    @FXML
    private JFXTextField txtSearch;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //load table
        loadTable();
        //load combobox
        initCombobox();
        //load error
        errorValidate();
        //choose image
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Text File", "*.txt")
        );
        
       orderData= FXCollections.observableArrayList();
    }

    //add
    @FXML
    private void handleAdd(ActionEvent event) {
        //error
        boolean txtBarcodenotEmpty = controller.ValidationController.isTextFieldHavingBarcode(txtEplCode, lbCode, "EPLCode is requied");
        if (!txtBarcodenotEmpty) {
            txtEplCode.requestFocus();
        }
        boolean txtUserNamenotEmpty = controller.ValidationController.isTextFieldHavingText(txtUsername, lbUser, "UserName is requied");
        if (!txtUserNamenotEmpty) {
            txtUsername.requestFocus();
        }
        boolean txtPhonenotSuitable = controller.ValidationController.isPhoneSuitable(txtPhone, lbPhone, "Phone is requied");
        if (!txtPhonenotSuitable) {
            txtPhone.requestFocus();
        }
        boolean txtEmailnotSuitable = controller.ValidationController.isEmailSuitable(txtEmail, lbEmail, "Email is requied");
        if (!txtEmailnotSuitable) {
            txtEmail.requestFocus();
        }

        if (txtBarcodenotEmpty&&txtUserNamenotEmpty&&txtPhonenotSuitable&&txtEmailnotSuitable) {
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
                System.out.println(employee.getImageBlob());
                eDAOIpl.insertEmployee(txtEplCode.getText(), txtUsername.getText(), txtPhone.getText(), txtEmail.getText(), txtAddrees.getText(), gendercheck, java.sql.Date.valueOf(txtDateBirth.getValue()),
                        Double.parseDouble(txtSalary.getText()), cbPosition.getSelectionModel().getSelectedItem() + "", cbDepartment.getSelectionModel().getSelectedItem() + "", blob, java.sql.Date.valueOf(txtDateWork.getValue()));

            } catch (IOException | SQLException ex) {
                Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    //update
    @FXML
    private void handleUpdate(ActionEvent event) {
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
    }

    //delete
    @FXML
    private void HandleDelete(ActionEvent event) {
        EmployeeDAOImplement eDAOIpl = new EmployeeDAOImplement();
        eDAOIpl.deleteEmployee(txtEplCode.getText());
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

    private void loadTable() {
        TableColumn<Employee, String> eCode = new TableColumn<>("ECode");
        eCode.setPrefWidth(50);

        TableColumn<Employee, String> userName = new TableColumn("UserName");
        userName.setPrefWidth(100);

        TableColumn<Employee, String> phone = new TableColumn("Phone");
        phone.setPrefWidth(100);

        TableColumn<Employee, String> email = new TableColumn("Email");
        email.setPrefWidth(130);

        TableColumn<Employee, String> position = new TableColumn("Position");
        position.setPrefWidth(70);

        TableColumn<Employee, String> department = new TableColumn("DPM");
        department.setPrefWidth(70);

        tableView.getColumns().addAll(eCode, userName, phone, email, position, department);

        EmployeeDAOImplement eDAOIpl = new EmployeeDAOImplement();
        ObservableList<Employee> listEmployee = eDAOIpl.getAllEmployee();
        tableView.setItems(listEmployee);

        //cell of tableView
        eCode.setCellValueFactory(new PropertyValueFactory<>("EplCode"));
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));

        //click on tableView
        tableView.setOnMouseClicked(e -> {
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
            //converted Object to Date 
            Date dt = (Date) employee.getDateBirth();
            txtDateBirth.setValue(LocalDate.parse(dt.toString()));
            txtSalary.setText(String.valueOf(employee.getSalary()));
            txtAddrees.setText(employee.getAddrees());
            
            //Show image with eCode
            imageView.setImage(getImage(employee.getEplCode()));
            
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
    
    //converted image on Blob SQL
    public Image getImage(String eplCode) {
        String sql = "select blogImage from employee where eplCode=?";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement prepareStatement = connection.prepareStatement(sql);) {
            prepareStatement.setString(1, eplCode);
            ResultSet rs = prepareStatement.executeQuery();
            Image image = null;
            if (rs.next()) {
                Blob botto = rs.getBlob("blogImage");
                InputStream is = botto.getBinaryStream();
                image = new Image(is);
                is.close();
            }
            rs.close();
            return image;

        } catch (ClassNotFoundException | SQLException | IOException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //css error
    private void errorValidate() {
        lbCode.setStyle("-fx-text-fill:orange");
        lbUser.setStyle("-fx-text-fill:orange");
        lbPhone.setStyle("-fx-text-fill:orange");
        lbEmail.setStyle("-fx-text-fill:orange");
    }
/*
    @FXML
    private void handlePrint(ActionEvent event) {
        String souceFile = "E:\\CabinetJava\\PharmacyManagement\\src\\model\\eReport.jrxml";
        try {
            JasperReport jr = JasperCompileManager.compileReport(souceFile);
            HashMap<String, Object> para = new HashMap<>();
            para.put("cashier", "duc");
            ObservableList<ePrint> plist = FXCollections.observableArrayList();
            for (Employee employee : orderData) {
                    plist.add(new ePrint(employee.getEplCode(),""+ employee.getUserName(),""+ employee.getEmail(),""+ employee.getPhone())); 
            }
            JRBeanCollectionDataSource jcs = new JRBeanCollectionDataSource(plist);
            JasperPrint jp = JasperFillManager.fillReport(jr, para, jcs);
            JasperViewer.viewReport(jp);

        } catch (JRException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
*/
    @FXML
    private void handleClear(ActionEvent event) {
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

    @FXML
    private void handleLoadTable(ActionEvent event) {
        TableColumn<Employee, String> eCode = new TableColumn<>("ECode");
        eCode.setPrefWidth(50);

        TableColumn<Employee, String> userName = new TableColumn("UserName");
        userName.setPrefWidth(100);

        TableColumn<Employee, String> phone = new TableColumn("Phone");
        phone.setPrefWidth(100);

        TableColumn<Employee, String> email = new TableColumn("Email");
        email.setPrefWidth(130);

        TableColumn<Employee, String> position = new TableColumn("Position");
        position.setPrefWidth(70);

        TableColumn<Employee, String> department = new TableColumn("DPM");
        department.setPrefWidth(70);

        tableView.getColumns().addAll(eCode, userName, phone, email, position, department);

        EmployeeDAOImplement eDAOIpl = new EmployeeDAOImplement();
        ObservableList<Employee> listEmployee = eDAOIpl.getAllEmployee();
        tableView.setItems(listEmployee);
        eCode.setCellValueFactory(new PropertyValueFactory<>("EplCode"));
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
    }

}
