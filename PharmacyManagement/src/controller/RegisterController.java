/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
//import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import model.Employee;
import model.LOCAL_DATE;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class RegisterController implements Initializable {

    //Anchor
    @FXML
    private AnchorPane anchorPane;

    //Connection
    private Connection con;
    private PreparedStatement pst = null;
    private ResultSet rs;

    //JFXTextField
    @FXML
    private JFXTextField tf_email;
    @FXML
    private JFXTextField tf_name;
    @FXML
    private JFXTextField tf_address;
    @FXML
    private JFXTextField tf_phone;
    @FXML
    private JFXDatePicker date_work;
    @FXML
    private JFXDatePicker date_birth;
    @FXML
    private JFXTextField tf_username;
    @FXML
    private PasswordField pf_password;
    @FXML
    private PasswordField pf_repassword;

    //combobox
    private JFXComboBox<String> combobox_position;
    @FXML
    private JFXComboBox<String> combobox_department;
    @FXML
    private JFXComboBox<String> combobox_mission;

    //image
    private FileChooser fileChooser;
    private File file;
    private Stage stage;
    private Image image;

    //Toggle Gender not using
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton rdFemale;
    @FXML
    private RadioButton rdMale;
    //Label
    @FXML
    private Label error_address1;
    @FXML
    private Label error_address11;
    @FXML
    private Label error_password1;
    @FXML
    private Label error_email;
    @FXML
    private Label error_name;
    @FXML
    private Label error_address;
    @FXML
    private Label error_phone;
    @FXML
    private Label error_username;
    @FXML
    private Label error_password;
    @FXML
    private Label error_repassword;

    //check gender
    private boolean gendercheck;
    @FXML
    private ImageView imageView;
    @FXML
    private Label lbImage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Text File", "*.txt")
        );

        combobox_department.setDisable(true);
        combobox_mission.setDisable(true);
        comboboxInit();
        cssError();
//        imageClose();
    }

//    private void imageClose() {
//        Image imageMiniClose = new Image(getClass().getResourceAsStream("/image/close.png"));
//        ImageView imageView = new ImageView(imageMiniClose);
//        imageView.setFitWidth(20);
//        imageView.setFitHeight(20);
//        btnClose.setGraphic(imageView);
//    }
    @FXML
    private void loginFirst_action(ActionEvent event) throws IOException {
        boolean isUserNameNotEmpty = controller.ValidationController.isTextFieldHavingText(tf_username, error_username, "Username is requied");
        boolean isPasswordNotEmpty = controller.ValidationController.isPasswordFieldHavingText(pf_password, error_password, "Password is requied");
        boolean isREPasswordNotEmpty = controller.ValidationController.isPasswordFieldHavingText(pf_repassword, error_repassword, "Repassword is requied");
        boolean arePasswordsametoREPassword = controller.ValidationController.arePasswordAndREPasswordSame(pf_password, pf_repassword, error_repassword, "Password and Re-password not match");
        boolean isEmailNotEmpty = controller.ValidationController.isTextFieldHavingText(tf_email, error_email, "Email is requied");
        boolean isNameNotEmpty = controller.ValidationController.isTextFieldHavingText(tf_name, error_name, "Name is requied");
        boolean isAddressNotEmpty = controller.ValidationController.isTextFieldHavingText(tf_address, error_address, "Address is requied");
        boolean isPhoneNotEmpty = controller.ValidationController.isTextFieldHavingText(tf_phone, error_phone, "Phone is requied");

        

        if (imageView.getImage() == null) {
            lbImage.setText("Image is required");
        } else if (imageView.getImage() != null) {
            lbImage.setText("");
        }
        if (imageView.getImage() != null && isUserNameNotEmpty && isPasswordNotEmpty && isREPasswordNotEmpty && arePasswordsametoREPassword
                && isEmailNotEmpty && isNameNotEmpty && isAddressNotEmpty && isPhoneNotEmpty) {
            boolean isEmailTrue = controller.ValidationController.isEmailSuitable(tf_email, error_email, "Ex: xxx@yyy.vn");
        boolean isPhoneTrue = controller.ValidationController.isPhoneSuitable(tf_phone, error_phone, "Ex: +84 925 111 4456, 0905999999,...");
        boolean isUsernameTrue = controller.ValidationController.isUsernameTrueType(tf_username, error_username, "Username is not suitable");
        boolean isPasswordTrue = controller.ValidationController.isPasswordTrueType(pf_password, error_password, "Password is not suitable");
            if (isUsernameTrue && isPasswordTrue && isEmailTrue && isPhoneTrue) {
                try {
                    String username = tf_username.getText();
                    String password = PasswordHash.encryptPass(pf_password.getText());
                    String addrees = tf_address.getText().trim().replaceAll("\\s+", " ");
                    String fullname = tf_name.getText().trim().replaceAll("\\s+", " ");
                    String phone = tf_phone.getText();
                    Date datebirth = java.sql.Date.valueOf(date_birth.getValue());

//                    String position = combobox_position.getValue();
                    String department = combobox_department.getValue();
                    String mission = combobox_mission.getValue();
                    Date workday = java.sql.Date.valueOf(date_work.getValue());
                    String email = tf_email.getText();

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
                        con = controller.ConnectDB.connectSQLServer();

                        pst = con.prepareStatement("insert into Users(UsersName,UsersPass,UsersFullName)values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
                        pst.setString(1, username);
                        pst.setString(2, password);
                        pst.setString(3, fullname);
                        pst.executeUpdate();

                        rs = pst.getGeneratedKeys();
                        rs.next();
                        Object key = rs.getObject(1);

                        pst = con.prepareStatement("INSERT INTO DetailUser(Code,Phone,Email,Addrees,Sex,BirthDay,Department,ImageBlob,Mission,WorkDay,UsersID) VALUES(?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                        pst.setString(1, "ENUM001");
                        pst.setString(2, phone);
                        pst.setString(3, email);
                        pst.setString(4, addrees);
                        pst.setBoolean(5, gendercheck);
                        pst.setDate(6, datebirth);
                        pst.setString(7, department);
                        pst.setBlob(8, blob);
                        pst.setString(9, mission);
                        pst.setDate(10, workday);
                        pst.setInt(11, Integer.parseInt(String.valueOf(key)));
                        
                        int i = pst.executeUpdate();
                        if (i==1) {
                            System.out.println("Success");
                        }
                    }
                    pst.close();
                    con.close();
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }

//                    Employee employee = new Employee();
//                    BufferedImage bImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
//                    byte[] res;
//                    try (ByteArrayOutputStream s = new ByteArrayOutputStream()) {
//                        ImageIO.write(bImage, "png", s);
//                        res = s.toByteArray();
//                        Blob blob = new SerialBlob(res);
//                        employee.setImageBlob(blob);
//                        con = controller.ConnectDB.connectSQLServer();
//                        pst = con.prepareStatement("INSERT INTO DetailUser(Code,Phone,Email,Addrees,Sex,BirthDay,Department,ImageBlob,Mission,WorkDay) VALUES(?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
//                        pst.setString(1, "ENUM001");
//                        pst.setString(2, phone);
//                        pst.setString(3, email);
//                        pst.setString(4, addrees);
//                        pst.setBoolean(5, gendercheck);
//                        pst.setDate(6, datebirth);
//                        pst.setString(7, department);
//                        pst.setBlob(8, blob);
//                        pst.setString(9, mission);
//                        pst.setDate(10, workday);
//
//                        pst.executeUpdate();
//                        rs = pst.getGeneratedKeys();
//                        rs.next();
//                        Object key = rs.getObject(1);
//                        String sql = "insert into Users(DetailID,UsersName,UsersPass,UsersFullName)values (?,?,?,?)";
//                        pst = con.prepareStatement(sql);
//
//                        pst.setInt(1, Integer.parseInt(String.valueOf(key)));
//
//                        pst.setString(2, username);
//                        pst.setString(3, password);
//                        pst.setString(4, tennhanvien);
//                        int a = pst.executeUpdate();
//
//                        if (a == 1) {
//                            System.out.println("Add success !");
//                        }
//                    }
//
//                    pst.close();
//                    con.close();
//
//                } catch (ClassNotFoundException | SQLException ex) {
//                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
//                }
                Stage stage = (Stage) anchorPane.getScene().getWindow();
                stage.close();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
                stage.getIcons().add(new Image("/image/hyhy.png"));
//                stage.initStyle(StageStyle.DECORATED);
//                Image applicationIcon = new Image(getClass().getResourceAsStream("/image/Login-icon.png"));
//                stage.getIcons().add(applicationIcon);
//        stage.setResizable(false);
                Scene scene = new Scene(root);
                stage.setTitle("Login");
                scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
                stage.setScene(scene);
                stage.show();

            }
        }
    }

    private void comboboxInit() {
        //Combobox_position
//        combobox_position.getItems().addAll("Manager");
//        combobox_position.getSelectionModel().selectFirst();
//        combobox_position.getValue();

        //Combobox_department
        combobox_department.getItems().addAll("Business");
        combobox_department.getSelectionModel().selectFirst();
        combobox_department.getValue();

        //Combobox_mission
        combobox_mission.getItems().addAll("Admin");
        combobox_mission.getSelectionModel().selectFirst();
        combobox_mission.getValue();

        // Set default for date_birth
        date_birth.setValue(LOCAL_DATE.localDate("01-01-1991"));

        // Set default for date_work
        date_work.setValue(LocalDate.now());
    }

    private void cssError() {
        error_username.setStyle("-fx-text-fill: #daa520");
        error_password.setStyle("-fx-text-fill: #daa520");
        error_email.setStyle("-fx-text-fill: #daa520");
        error_name.setStyle("-fx-text-fill: #daa520");
        error_address.setStyle("-fx-text-fill: #daa520");
        error_phone.setStyle("-fx-text-fill: #daa520");
        lbImage.setStyle("-fx-text-fill: #daa520");
    }

//    private void handleClose(ActionEvent event) {
//        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
//        stage.close();
//    }
    @FXML
    private void handleChooseImage(ActionEvent event) {
        stage = (Stage) anchorPane.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            System.out.println("" + file.getAbsolutePath());
            image = new Image(file.getAbsoluteFile().toURI().toString(), imageView.getFitWidth(), imageView.getFitHeight(), true, true);
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
        }
    }
}
