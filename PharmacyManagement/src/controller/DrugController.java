/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import static controller.MainController.infoUser;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import model.Drug;
import model.DrugDAOImplement;
import model.User;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class DrugController implements Initializable {

    @FXML
    private TableView<Drug> tableView;
    @FXML
    private JFXTextField txtSearch;

    private ObservableList<Drug> data;
    ;
    //Image
    private FileChooser fileChooser;
    private File file;
    private Stage stage;
    private Image image;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXTextField txtCode;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtCategories;
    @FXML
    private ChoiceBox<String> cUnit;
    @FXML
    private JFXTextField txtBuy;
    @FXML
    private JFXTextField txtSell;
    @FXML
    private JFXTextField txtSup;
    @FXML
    private ChoiceBox<String> cbStatus;
    @FXML
    private JFXTextArea txtAreaDes;
    @FXML
    private TableColumn<Drug, String> columnCode;
    @FXML
    private TableColumn<Drug, String> columnName;
    @FXML
    private TableColumn<Drug, String> columnSup;
    @FXML
    private TableColumn<Drug, String> columnStatus;
    @FXML
    private TableColumn<Drug, String> columnDes;

    private final String unit[] = {"    Wrap    ", "    Blister    "};
    private final String status[] = {"    Active    ", "    Inactive    "};
    @FXML
    private ImageView imageView;

    private int id1, id2;
    public static ObservableList<Drug> ListDrug = FXCollections.observableArrayList();
    @FXML
    private Label lbCode;
    @FXML
    private Label lbName;
    @FXML
    private Label lbCategories;
    @FXML
    private Label lbBuy;
    @FXML
    private Label lbSell;
    @FXML
    private Label lbSupplier;
    @FXML
    private Label lbImage;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnDelete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        DrugDAOImplement dDI = new DrugDAOImplement();

        data = dDI.getAllDrug();
        ListDrug = dDI.getAllDrug();
        choiceBox();

        //choose image
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Text File", "*.txt")
        );

        loadTable();
        click();
        css();

        FilteredList<Drug> filteredData = new FilteredList<>(data, e -> true);
        txtSearch.setOnKeyReleased(e -> {
            txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Drug>) drug -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (drug.getDCode().contains(newValue)) {
                        return true;
                    }
                    return false;
                });
            });
        });
        SortedList<Drug> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);

        mission();
    }

    @FXML
    private void handleAdd(ActionEvent event) {

        boolean textCodeNotEmpty = controller.ValidationController.isTextFieldHavingBarcode(txtCode, lbCode, "Code must be filled out");
        if (!textCodeNotEmpty) {
            txtCode.requestFocus();
        }
        boolean txtNameNotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtName, lbName, "Name must be filled out");
        if (!txtNameNotEmpty) {
            txtName.requestFocus();
        }
        boolean txtCategoriesnotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtCategories, lbCategories, "Categories must be filled out");
        if (!txtCategoriesnotEmpty) {
            txtCategories.requestFocus();
        }
        boolean txtBuyNotEmpty = controller.ValidationController.isTextFieldTypeNumber(txtBuy, lbBuy, "BuyPrice must be filled out");
        if (!txtBuyNotEmpty) {
            txtBuy.requestFocus();
        }
        boolean txtSellNotEmpty = controller.ValidationController.isTextFieldTypeNumber(txtSell, lbSell, "SellPrice must be filled out");
        if (!txtSellNotEmpty) {
            txtSell.requestFocus();
        }
        boolean txtSupNotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtSup, lbSupplier, "Supplier must be filled out");
        if (!txtSupNotEmpty) {
            txtSup.requestFocus();
        }
        if (imageView.getImage() == null) {
            lbImage.setText("Image is required");
        } else if (imageView.getImage() != null) {
            lbImage.setText("");
        }

        String name = txtName.getText().trim().replaceAll("\\s+", " ");
        
        if (imageView.getImage() != null && textCodeNotEmpty && txtNameNotEmpty && txtCategoriesnotEmpty && txtBuyNotEmpty && txtSellNotEmpty && txtSupNotEmpty) {
            
            
                BufferedImage bImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
                byte[] res;
                try (ByteArrayOutputStream s = new ByteArrayOutputStream()) {
                    ImageIO.write(bImage, "png", s);
                    res = s.toByteArray();
                    Blob blob = new SerialBlob(res);
                    Drug drug = new Drug();
                    drug.setImage(blob);
                    DrugDAOImplement dDI = new DrugDAOImplement();
                    dDI.insertDrug(txtCode.getText(),name,txtSup.getText(), txtCode.getText(), name, txtCategories.getText(), cUnit.getSelectionModel().getSelectedItem() + "", blob,
                            cbStatus.getSelectionModel().getSelectedItem() + "", Double.parseDouble(txtBuy.getText()), Double.parseDouble(txtSell.getText()),
                            txtSup.getText(), txtAreaDes.getText());

                } catch (IOException | SQLException ex) {
                    Logger.getLogger(DrugController.class.getName()).log(Level.SEVERE, null, ex);
                }
                clear();
                clearLabel();
            

        }
        loadTable();

    }

    @FXML
    private void handleUpdate(ActionEvent event) {

        boolean textCodeNotEmpty = controller.ValidationController.isTextFieldHavingBarcode(txtCode, lbCode, "Code must be filled out");
        if (!textCodeNotEmpty) {
            txtCode.requestFocus();
        }
        boolean txtNameNotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtName, lbName, "Name must be filled out");
        if (!txtNameNotEmpty) {
            txtName.requestFocus();
        }
        boolean txtCategoriesnotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtCategories, lbCategories, "Categories must be filled out");
        if (!txtCategoriesnotEmpty) {
            txtCategories.requestFocus();
        }
        boolean txtBuyNotEmpty = controller.ValidationController.isTextFieldTypeNumber(txtBuy, lbBuy, "BuyPrice must be filled out");
        if (!txtBuyNotEmpty) {
            txtBuy.requestFocus();
        }
        boolean txtSellNotEmpty = controller.ValidationController.isTextFieldTypeNumber(txtSell, lbSell, "SellPrice must be filled out");
        if (!txtSellNotEmpty) {
            txtSell.requestFocus();
        }

        boolean txtSupNotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtSup, lbBuy, "Supplier must be filled out");
        if (!txtSupNotEmpty) {
            txtSup.requestFocus();
        }

        if (imageView.getImage() == null) {
            lbImage.setText("Image is required");
        } else if (imageView.getImage() != null) {
            lbImage.setText("");
        }
        String name = txtName.getText().trim().replaceAll("\\s+", " ");
        if (imageView.getImage() != null && textCodeNotEmpty && txtNameNotEmpty && txtCategoriesnotEmpty && txtBuyNotEmpty && txtSellNotEmpty && txtSupNotEmpty) {
            
            
                Drug drug = new Drug();
                BufferedImage bImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
                byte[] res;
                try (ByteArrayOutputStream s = new ByteArrayOutputStream()) {
                    ImageIO.write(bImage, "png", s);
                    res = s.toByteArray();
                    Blob blob = new SerialBlob(res);
                    drug.setImage(blob);
                    DrugDAOImplement dDI = new DrugDAOImplement();
                    dDI.updateDrug(txtCode.getText(), name, txtCategories.getText(), cUnit.getSelectionModel().getSelectedItem() + "", blob, cbStatus.getSelectionModel().getSelectedItem() + "",
                            Double.parseDouble(txtBuy.getText()), Double.parseDouble(txtSell.getText()),
                            txtSup.getText(), txtAreaDes.getText(), id1);

                } catch (IOException | SQLException ex) {
                    Logger.getLogger(DrugController.class.getName()).log(Level.SEVERE, null, ex);
                }
                clear();
                clearLabel();
            
        }
        loadTable();
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        boolean textCodeNotEmpty = controller.ValidationController.isTextFieldHavingBarcode(txtCode, lbCode, "Code must be filled out");
        if (!textCodeNotEmpty) {
            txtCode.requestFocus();
        }
        if (textCodeNotEmpty) {
            DrugDAOImplement dDI = new DrugDAOImplement();
            dDI.deleteDrug(txtCode.getText());
            clear();
            clearLabel();
        }
        loadTable();

    }

    @FXML
    private void handleClear(ActionEvent event) {
        clear();
    }

    private void choiceBox() {
        List<String> listUnit = new ArrayList<>();
        for (String listSPS : unit) {
            listUnit.add(listSPS);
        }
        ObservableList obListPS = FXCollections.observableArrayList(listUnit);
        cUnit.setItems(obListPS);
        List<String> listStatus = new ArrayList<>();
        for (String listst : status) {
            listStatus.add(listst);
        }
        ObservableList obListST = FXCollections.observableArrayList(listStatus);
        cbStatus.setItems(obListST);

    }

    private void loadTable() {
        DrugDAOImplement dDI = new DrugDAOImplement();
        ObservableList<Drug> listDrug = dDI.getAllDrug();
        tableView.setItems(listDrug);

        columnCode.setCellValueFactory(new PropertyValueFactory<>("DCode"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        columnSup.setCellValueFactory(new PropertyValueFactory<>("Supplier"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        columnDes.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void clear() {
        txtCode.clear();
        txtName.clear();
        txtCategories.clear();
        cUnit.getSelectionModel().clearSelection();
        txtBuy.clear();
        txtSell.clear();
        txtSup.clear();
        cbStatus.getSelectionModel().clearSelection();
        txtAreaDes.clear();
        imageView.setImage(null);
    }

    private void clearLabel() {
        lbCode.setText("");
        lbName.setText("");
        lbSupplier.setText("");
        lbCategories.setText("");
        lbImage.setText("");
        lbSell.setText("");
        lbBuy.setText("");
    }

    private void css() {
        lbCode.setStyle("-fx-text-fill:#FF0000");
        lbName.setStyle("-fx-text-fill:#FF0000");
        lbSupplier.setStyle("-fx-text-fill:#FF0000");
        lbCategories.setStyle("-fx-text-fill:#FF0000");
        lbImage.setStyle("-fx-text-fill:#FF0000");
        lbSell.setStyle("-fx-text-fill:#FF0000");
        lbBuy.setStyle("-fx-text-fill:#FF0000");

    }

    private void click() {
        tableView.setOnMouseClicked(e -> {
            Drug drug = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());
            txtCode.setText(drug.getDCode());
            txtName.setText(drug.getName());
            txtCategories.setText(drug.getCategories());
            cUnit.setValue(drug.getUnit());
            txtBuy.setText(String.format("%.1f", drug.getBuyPrice()));
            txtSell.setText(String.format("%.1f", drug.getSellPrice()));
            txtSup.setText(drug.getSupplier());
            cbStatus.setValue(drug.getStatus());
            txtAreaDes.setText(drug.getDescription());
            //Show image 
            DrugDAOImplement DrugIm = new DrugDAOImplement();
            imageView.setImage(DrugIm.getImage(drug.getDCode()));
            id1 = drug.getId1();
        });
    }

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

    private void mission() {
        infoUser = LoginController.ListUserLogin;
        for (User user : infoUser) {
            if (user.getMission().equals("") && user.getDeparment().equals("") || user.getDeparment().equals("Sell")
                    ||user.getDeparment().equals("Business")&&user.getMission().equals("User")) {
                btnAdd.setDisable(true);
                btnAdd.setStyle("-fx-background-color: #393535; -fx-text-fill: white;");
                btnEdit.setDisable(true);
                btnEdit.setStyle("-fx-background-color: #393535; -fx-text-fill: white;");
                btnDelete.setDisable(true);
                btnDelete.setStyle("-fx-background-color: #393535; -fx-text-fill: white;");
            }
        }
    }

}
