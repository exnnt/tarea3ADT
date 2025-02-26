package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.services.ConjuntoService;
import com.luisdbb.tarea3AD2024base.services.ServicioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class ConjuntoController implements Initializable {

	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private ServicioService servicioService;
	@Autowired
	private ConjuntoService conjuntoService;
	@Autowired
	private CU3and5Controller cu35;
	@Autowired
	private Tarea3Ad2024baseApplication main;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnConfirm;

	@FXML
	private Button btnParadas;

	@FXML
	private Button btnServicios;

	@FXML
	private Button btnAyuda;
	@FXML
	private Button edit1;
	@FXML
	private Button edit11;

	@FXML
	private AnchorPane root;
	@FXML
	private TableView<Servicio> tableServicios;
	@FXML
	private TableColumn<Servicio, Long> idservicio;
	@FXML
	private TableColumn<Servicio, String> nameservicio;
	@FXML
	private TableColumn<Servicio, Double> precioservicio;
	@FXML
	private TableView<Servicio> tableServicios1;
	@FXML
	private TableColumn<Servicio, String> nameservicio1;
	@FXML
	private TableColumn<Servicio, Double> precioservicio1;
	private Servicio serviciopasiempre;
	private Servicio serviciopasiempre2;
	private ObservableList<Servicio> servicios = FXCollections.observableArrayList();
	private ObservableList<Servicio> servicios2 = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Servicio, String> paradasservicio;
	private Long idEstancia;
	private ConjuntoContratado temp;
	@FXML
	private RadioButton Tarjeta;
	@FXML
	private RadioButton Efectivo;
	@FXML
	private RadioButton Bizum;
	@FXML
	private TextField estra;
	ToggleGroup grupoo = new ToggleGroup();
	private boolean Envio = false;

	@FXML
	private void back(ActionEvent event) throws IOException {
		System.out.println("test");
		stageManager.switchScene(FxmlView.ADMIN1);
	}

	public void mostrarAyuda() {
		try {
			WebView webView = new WebView();

			String url = getClass().getResource("/help/html/admin.html").toExternalForm();
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
		idEstancia = cu35.getidEstancia();
		idservicio.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameservicio.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		precioservicio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		getservicios();

		Tarjeta.setToggleGroup(grupoo);
		Efectivo.setToggleGroup(grupoo);
		Bizum.setToggleGroup(grupoo);
		nameservicio1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		precioservicio1.setCellValueFactory(new PropertyValueFactory<>("precio"));
		temp = new ConjuntoContratado(idEstancia);
		servicios2.clear();
	}

	public void getservicios() {
		// xddd System.out.println(idEstancia);

		List<Servicio> serviciosdb = servicioService.listarServicios();
		List<Servicio> disponible = new ArrayList<>();
		for (Servicio s : serviciosdb) {
			if (s.getparadasString().contains(String.valueOf(main.inicial.getId()))) {
				System.out.println(s.getNombre());
				disponible.add(s);
				

			}
			System.out.println("next");
		}
		servicios.setAll(disponible);
		tableServicios.setItems(servicios);
	}

	@FXML
	public void lastSelected() {
		Servicio sel = tableServicios.getSelectionModel().getSelectedItem();
		System.out.println(sel.getId());
		System.out.println(sel.getparadasString());
		serviciopasiempre = sel;
	}

	@FXML
	public void lastSelected2() {
		Servicio sel = tableServicios1.getSelectionModel().getSelectedItem();
		System.out.println(sel.getId());
		System.out.println(sel.getparadasString());
		serviciopasiempre2 = sel;
	}

	@FXML
	public void addServicios() {
		if (temp.addServicios(serviciopasiempre)) {
			System.out.println("Conjnto temp tiene nosecuanto");
			for (Servicio s : temp.getServiciosContratados()) {
				System.out.println(s.getNombre() + " id: " + s.getId());
			}
			servicios2.add(serviciopasiempre);
			tableServicios1.setItems(servicios2);

		} else {
			// cambiar con aletra al pulir
			System.out.println("Conjunto ya añadido");
		}

	}

	@FXML
	public void deleteServicio() {

		if (serviciopasiempre2 != null) {

			if (temp.deleteServicio(serviciopasiempre2)) {
				System.out.println("Service removed from temp ConjuntoContratado");

				for (Servicio s : temp.getServiciosContratados()) {
					System.out.println(s.getNombre() + " id: " + s.getId());
				}

				servicios2.remove(serviciopasiempre2);

				tableServicios1.setItems(servicios2);

			}
		}
		// cambiar con aletra al pulir
		System.out.println("happens ns");
	}

	@FXML
	public void confirm() {
		// calcula precio
		// confirma radiobutton selected if not empty list2

		if (!servicios2.isEmpty()) {
			// esto a method y call envio if true
			double preciofinal = 0.0;
			for (Servicio s : servicios2) {
				preciofinal += s.getPrecio();
				if (s.getNombre().equals("Envio a Casa")) {
					Envio = true;
					System.out.println("asies");
				}
			}
			temp.setPrecioTotal(preciofinal);
			Toggle radio = grupoo.getSelectedToggle();
			char pago;
			if (radio != null) {
				if (radio == Tarjeta) {
					pago = 'T';
				} else if (radio == Efectivo) {
					pago = 'E';
				} else {
					pago = 'B';
				}
				temp.setModoPago(pago);
				if (Envio) {
					stageManager.switchScene(FxmlView.ENVIAR);
					//llamar a pago desde envio por si cancela el envio q pague menos
					// ye mucha movida rn 
				} else {
					fuera();
					stageManager.switchScene(FxmlView.PARADA);
				}
			} else {
				System.out.println("pago cant be null try again");
			}
			

			// ahora syso pa debug luego alerta pa qeste guapo
		} else {
			stageManager.switchScene(FxmlView.PARADA);
		}

	}

	public void fuera() {
		String extra = estra.getText();
		temp.setExtras(extra);
		
		conjuntoService.guardarconjunto(temp);

		System.out.println(temp.toString());
		List<ConjuntoContratado> s = conjuntoService.listarConjuntosContratados();
		for (ConjuntoContratado t : s) {
			System.out.println(t.toString2());
		}
	}
		
	}
	

