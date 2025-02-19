package com.luisdbb.tarea3AD2024base.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import javafx.scene.web.WebView;
import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.RutaService;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;


/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Controller
public class InvitadoController implements Initializable {

	@FXML
	private Button btnLogIn;

	@FXML
	private TextField userId;

	@FXML
	private ComboBox<String> cbNa;
	@FXML
	private ComboBox<String> cbParada;

	@FXML
	private PasswordField pass;
	@FXML
	private Button btnAyuda;
	@FXML
	private Button reset;

	@FXML
	private Button saveUser;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private UserService userService;
	@Autowired
	private ParadaService paradaService;
	@Autowired
	private PeregrinoService peregrinoService;
	@Autowired
	private CarnetService carnetService;
	@Autowired
	private RutaService rutaService;
	@FXML
	private void exit(ActionEvent event) {
		Platform.exit();
	}

	/**
	 * Logout and go to the login page
	 */
	@FXML
	private void login(ActionEvent event) throws IOException {
		// no le cambio el nombre pero es el login
		stageManager.switchScene(FxmlView.LOGIN);
	}

	@FXML
	void reset(ActionEvent event) {
		clearFields();
	}

	@FXML
	private void registraUser(ActionEvent event) {
		// esto lo tengo q cambiar
		String pername = userId.getText();
		String username = userId.getText().replace(" ", "");
		String nacion = cbNa.getValue();
		String parada = cbParada.getValue();
		System.out.println(parada);
		String password = getPassword();
		// luego hago el validAlert
		boolean valid = true;
		if (!validate(pername)) {
			userId.setStyle("-fx-border-color: red;");
			valid = false;
		}
		if (pername.isEmpty()) {
			userId.setStyle("-fx-border-color: red;");
			valid = false;
		}
		if (password.isEmpty()) {
			pass.setStyle("-fx-border-color: red;");
			valid = false;
		}
		if (nacion == null) {
			cbNa.setStyle("-fx-border-color: red;");
			valid = false;
		}
		if (parada == null) {
			cbParada.setStyle("-fx-border-color: red;");
			valid = false;
		}
		if (!valid) {
			updateAlert();
			return;
		}

		Usuario user = new Usuario();
		user.setName(username);
		user.setType("peregrino");
		user.setPass(password);
		Peregrino per = new Peregrino();
		per.setNombre(pername);
		per.setNacionalidad(nacion);

		Usuario newUser = userService.crearUsuario(user);
		int id_user = newUser.getId();
		per.setId_user(id_user);
		peregrinoService.creaPeregrino(per);
		Parada p = paradaService.findbyName(parada);

		Tarea3Ad2024baseApplication.useractivo.setId(id_user);

		Tarea3Ad2024baseApplication.useractivo.setNombre(user.getName());

		Tarea3Ad2024baseApplication.useractivo.setPerfil(com.luisdbb.tarea3AD2024base.services.Perfil.PEREGRINO);

		carnetService.creaCarnet(per, p);
		
		// crea ruta
		rutaService.crearRuta(per.getId(),p.getId(),0);
		saveAlert(newUser);
		System.out.println("User: " + user.getName());
		 stageManager.switchScene(FxmlView.PEREGRINO);
		System.out.println("done working");
		clearFields();

	}

	private void loadCountries() {
		ObservableList<String> countries = FXCollections.observableArrayList();

		try {

			File xmlFile = new File(getClass().getResource("/paises.xml").toURI());
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);

			NodeList paisList = document.getElementsByTagName("pais");

			for (int i = 0; i < paisList.getLength(); i++) {
				Node paisNode = paisList.item(i);
				if (paisNode.getNodeType() == Node.ELEMENT_NODE) {
					Element paisElement = (Element) paisNode;
					NodeList nombreList = paisElement.getElementsByTagName("nombre");
					Element nombreElement = (Element) nombreList.item(0);
					String countryName = nombreElement.getTextContent();
					countries.add(countryName);
				}

				cbNa.setItems(countries);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadPinicial() {
		// lo mismo pero cuado tenga alguna parada
		ObservableList<String> paradas = FXCollections.observableArrayList();
		List<Parada> allParadas = paradaService.findAll();
		for (Parada parada : allParadas) {
			paradas.add(parada.getNombre());
		}
		cbParada.setItems(paradas);
	}

	// lo puedo usar para crear mas rapido ig luego lo borro

	private void clearFields() {
		userId.setText(null);

		cbNa.getSelectionModel().clearSelection();

		pass.clear();
	}

	private void saveAlert(Usuario user) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText(
				"The user " + user.getName() + " has been created and \n" + " pasword is " + user.getPass() + ".");
		alert.showAndWait();
	}
	private void updateAlert() {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid input");
		alert.setHeaderText(null);
		alert.setContentText("Alguno de los campos esta vacio o invalido");
		alert.showAndWait();
	}
	
	public void mostrarAyuda() {
        try {
            
            WebView webView = new WebView();

            URL test = getClass().getResource("/help/html/invitado.html");
            System.out.println(test);
            String url = getClass().getResource("/help/html/invitado.html").toExternalForm();
            System.out.println(url);
            webView.getEngine().load(url);

           
            Stage helpStage = new Stage();
            helpStage.setTitle("Ayuda");

          
            StackPane root = new StackPane(webView);
            Scene helpScene = new Scene(root, 600, 400);
            helpStage.setScene(helpScene);

          
            helpStage.initModality(Modality.APPLICATION_MODAL);
            helpStage.setResizable(true);
            helpStage.show();
            
        } catch (NullPointerException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Archivo de Ayuda no encontrado");
        
            alert.showAndWait();
        }
    }

	

	public String getPassword() {
		return pass.getText();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		loadCountries();
		loadPinicial();
		// useless no? setColumnProperties();

	}

	/*
	 * Set All userTable column properties
	 */
	private void setColumnProperties() {
		/*
		 * Override date format in table
		 * colDOB.setCellFactory(TextFieldTableCell.forTableColumn(new
		 * StringConverter<LocalDate>() { String pattern = "dd/MM/yyyy";
		 * DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		 * 
		 * @Override public String toString(LocalDate date) { if (date != null) { return
		 * dateFormatter.format(date); } else { return ""; } }
		 * 
		 * @Override public LocalDate fromString(String string) { if (string != null &&
		 * !string.isEmpty()) { return LocalDate.parse(string, dateFormatter); } else {
		 * return null; } } }));
		 */

	}

	/*
	 * Add All users to observable list and update table
	 */
	private void loadUserDetails() {

	}

	/*
	 * Validations
	 */

	private boolean validate(String input) {
		return input.matches("[a-zA-Z0-9 ]+");
	}

	private boolean emptyValidation(String field, boolean empty) {
		if (!empty) {
			return true;
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	private void validationAlert(String field, boolean empty) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Validation Error");
		alert.setHeaderText(null);
		if (field.equals("Role"))
			alert.setContentText("Please Select " + field);
		else {
			if (empty)
				alert.setContentText("Please Enter " + field);
			else
				alert.setContentText("Please Enter Valid " + field);
		}
		alert.showAndWait();
	}
}
