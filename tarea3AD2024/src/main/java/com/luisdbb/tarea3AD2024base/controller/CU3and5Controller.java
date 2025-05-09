package com.luisdbb.tarea3AD2024base.controller;

import java.awt.Label;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.RutaService;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;

@Controller
public class CU3and5Controller implements Initializable {
	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private ParadasController pcontrol;
	@Autowired
	private UserService userService;
	@Autowired
	private RutaService rutaService;
	@Autowired
	private PeregrinoService peregrinoService;
	@Autowired
	private EstanciaService estanciaService;
	@FXML
	public TextField userId;
	@FXML
	private CheckBox estanciar;
	@FXML
	private CheckBox vip;
	@FXML
	private javafx.scene.control.Label lblLogin1;
	@FXML
	private Button btnConfirm;
	@FXML
	private Button btnVolver;
	@FXML
	public PasswordField pass;
	@FXML
	private Button btnBusc;
	@FXML
	private DatePicker fInicio;
	@FXML
	private DatePicker fFin;
	@FXML
	private TableView<Estancia> tableEstancia;

	@FXML
	private TableColumn<Estancia, Long> eId;

	@FXML
	private TableColumn<Estancia, String> eFecha;

	@FXML
	private TableColumn<Estancia, Long> peid;

	@FXML
	private TableColumn<Estancia, Long> pid;

	@FXML
	private TableColumn<Estancia, String> vips;
	private Long idEstancia;

	@FXML
	public void Sellar(ActionEvent event) throws IOException {
		try {
			int usr = userService.authenticate(getUsername(), getPassword());
			if (usr == 3) {
				Peregrino p = peregrinoService.findbyiduser(userService.findByName(getUsername()).getId());

				rutaService.crearRuta(p.getId(), Tarea3Ad2024baseApplication.inicial.getId(), (float) 5.5);
				if (estanciar.isSelected()) {
					Estanciar(p);
				} else {
					sellaAlert(p);
					pcontrol.cu3 = false;
					stageManager.switchScene(FxmlView.PARADA);
				}

			} else {
				saveAlert();
				clearFields();
			}

		} catch (Exception e) {
			credencialesAlert();
		}
	}

	@FXML
	private LocalDate gIni() {
		return fInicio.getValue();
	}

	@FXML
	private LocalDate gFin() {
		return fFin.getValue();
	}

	public void ayudaF1(KeyEvent event) {
		if (event.getCode().toString().equals("F1")) {
			mostrarAyuda();
		}
	}

	@FXML
	private void volver(ActionEvent event) throws IOException {
		pcontrol.cu3 = false;
		stageManager.switchScene(FxmlView.PARADA);
	}

	@FXML
	private void export(ActionEvent event) throws IOException {
		// ççç
		tableEstancia.getItems().clear();

		System.out.println(Tarea3Ad2024baseApplication.inicial.getId());
		LocalDate min = gIni();
		LocalDate max = gFin();
		if (min == null || max == null) {
			fechaAlerta("Error", "Fechas Invalidas", Alert.AlertType.ERROR);
			return;
		}
		if (min.isAfter(max)) {
			fechaAlerta("Error", "Fecha inicio cant be after Fecha fin", Alert.AlertType.ERROR);
			return;
		}
		List<Estancia> a = estanciaService.findbyFecha(min, max);

		for (Estancia e : a) {

			String vipStatus = "Normal";
			if (e.isVip())
				vipStatus = "VIP";
			Estancia row = new Estancia(e.getId(), e.getFecha().toString(), e.getPeregrinoId(), e.getParadaId(),
					vipStatus);

			tableEstancia.getItems().add(row);

		}
		tableEstancia.refresh();

	}

	private void Estanciar(Peregrino p) {
		boolean viper = vip.isSelected();

		idEstancia = estanciaService.creaEstancia(p, Tarea3Ad2024baseApplication.inicial, viper).getId();

		stageManager.switchScene(FxmlView.CONJUNTO);

	};

	public void mostrarAyuda() {
		try {

			WebView webView = new WebView();

			String url = getClass().getResource("/help/html/parada.html").toExternalForm();
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		if (pcontrol.cu3) {
			lblLogin1.setText("Ver estancias en : " + Tarea3Ad2024baseApplication.inicial.getNombre());
			eId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
			eFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFestring()));
			peid.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPeregrinoId()));
			pid.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getParadaId()));
			vips.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getvipstring()));
		} else {
			lblLogin1.setText("Sellar/Estanciar: " + Tarea3Ad2024baseApplication.inicial.getNombre());
		}
	}

	public String getPassword() {
		return pass.getText();
	}

	public String getUsername() {
		return userId.getText();
	}

	public void saveAlert() {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Wrong Credentials");
		alert.setHeaderText(null);
		alert.setContentText("Tipo de usuario o credenciales incorrectas");
		alert.showAndWait();
	}

	private void clearFields() {
		userId.setText(null);

		pass.clear();
	}

	private void fechaAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

	private void credencialesAlert() {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid input");
		alert.setHeaderText(null);
		alert.setContentText("Alguno de los campos esta vacio ");
		alert.showAndWait();

	}

	private void sellaAlert(Peregrino p) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Sellado confirmado");
		alert.setHeaderText(null);
		alert.setContentText("Peregrino " + p.getNombre() + " sellado. Buen camino!");
		alert.showAndWait();

	}

	public Long getidEstancia() {
		return idEstancia;
	}
}
