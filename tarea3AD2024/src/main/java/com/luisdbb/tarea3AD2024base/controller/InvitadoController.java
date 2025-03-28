package com.luisdbb.tarea3AD2024base.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.config.existdb.ExistDBManageante;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class InvitadoController implements Initializable {

	@FXML
	private Button btnLogIn;

	@FXML
	public TextField userId;

	@FXML
	public ComboBox<String> cbNa;
	@FXML
	public ComboBox<String> cbParada;

	@FXML
	public PasswordField pass;
	@FXML
	private Button btnAyuda;
	@FXML
	private Button reset;

	@FXML
	private Button saveUser;
	@FXML
	private TextField nombre;
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

	public void ayudaF1(KeyEvent event) {
		if (event.getCode().toString().equals("F1")) {
			mostrarAyuda();
		}
	}

	@FXML
	public void registraUser(ActionEvent event) {

		String pername = nombre.getText();
		String username = userId.getText();
		String nacion = cbNa.getValue();
		String parada = cbParada.getValue();
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

		if (!username.equals(username.replaceAll(" ", "")) || !password.equals(password.replaceAll(" ", ""))) {
			passAlert();
			return;
		}

		Usuario user = new Usuario();
		user.setName(username);
		user.setType("peregrino");
		user.setPass(password);
		Peregrino per = new Peregrino();
		per.setNombre(pername);
		per.setNacionalidad(nacion);

		try {
			Usuario newUser = userService.crearUsuario(user);
			int id_user = newUser.getId();
			per.setId_user(id_user);
			peregrinoService.creaPeregrino(per);
			Parada p = paradaService.findbyName(parada);

			Tarea3Ad2024baseApplication.useractivo.setId(id_user);

			Tarea3Ad2024baseApplication.useractivo.setNombre(user.getName());

			Tarea3Ad2024baseApplication.useractivo.setPerfil(com.luisdbb.tarea3AD2024base.services.Perfil.PEREGRINO);
			rutaService.crearRuta(per.getId(), p.getId(), 0);
			carnetService.creaCarnet(per, p);
			String carnet = carnetService.exportCarnet(per);
			File ccarnet = new File(carnet);
			ExistDBManageante.storeCarnet(p.getNombre(), ccarnet);
			// crea ruta
	
			saveAlert(newUser);
			stageManager.switchScene(FxmlView.PEREGRINO);
			clearFields();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			userAlert(user);
		}

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

	private void userAlert(Usuario user) {

		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("No se pudo crear el usuario");
		alert.setHeaderText(null);
		alert.setContentText("El nombre de usuario " + user.getName() + " no esta disponible, pruebe con otro.");
		alert.showAndWait();
	}

	public void updateAlert() {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid input");
		alert.setHeaderText(null);
		alert.setContentText("Alguno de los campos esta vacio o invalido");
		alert.showAndWait();
	}

	public void passAlert() {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid input");
		alert.setHeaderText(null);
		alert.setContentText("Ni el usuario ni la contraseña pueden tener espacios");
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

	}

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
		if (field.equals(null))
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
