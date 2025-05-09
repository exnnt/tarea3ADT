package com.luisdbb.tarea3AD2024base.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.Perfil;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
public class ExportController implements Initializable {
	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private CarnetService carnetService;
	@Autowired
	private PeregrinoService peregrinoService;
	@FXML
	private Label lblMecachis;
	@FXML
	private Button btnExportar;
	@FXML
	private Button btnVolver;

	@FXML
	private ImageView image;
	@FXML
	private Button btnEdit;
	private Peregrino p = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		p = peregrinoService.findbyiduser(Tarea3Ad2024baseApplication.useractivo.getId());

		lblMecachis.setText("Bienvenido " + p.getNombre());

	}

	@FXML
	private void volver(ActionEvent event) throws IOException {

		Tarea3Ad2024baseApplication.useractivo.setId(0);
		Tarea3Ad2024baseApplication.useractivo.setNombre("Invitado");
		Tarea3Ad2024baseApplication.useractivo.setPerfil(Perfil.INVITADO);
		stageManager.switchScene(FxmlView.LOGIN);
	}

	public void mostrarAyuda() {
		try {

			WebView webView = new WebView();

			String url = getClass().getResource("/help/html/peregrino.html").toExternalForm();
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

	public void ayudaF1(KeyEvent event) {
		if (event.getCode().toString().equals("F1")) {
			mostrarAyuda();
		}
	}

	@FXML
	private void exportCarnet(ActionEvent event) throws IOException {
		try {
			carnetService.exportCarnet(p);
			exportaAlert(p);
			//informe(p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void eidt(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.EDIT);
	}

	private void exportaAlert(Peregrino p) {
		String name = p.getNombre().replaceAll(" ", "");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Carnet exportado");
		alert.setHeaderText(null);
		alert.setContentText("Carnet exportado, esta en /src/main/resources/escritura/" + name + "_peregrino.xml");
		alert.showAndWait();

	}

	public void informe(Peregrino peregrino) {
		Connection conexion = null;
		String name = p.getNombre().replaceAll(" ", "");
		try {

			System.out.println("url");
			URL url2 = getClass().getResource("/template/CarnetPregrino.jasper");
			System.out.println(url2);

			if (url2 == null) {

				return;
			}

			JasperReport reporte = (JasperReport) JRLoader.loadObject(url2);

			Long idPeregrino = peregrino.getId();

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("id", idPeregrino);

			DataSource ds = getDataSource();
			conexion = ds.getConnection();

			JasperPrint print = JasperFillManager.fillReport(reporte, parametros, conexion);

			String rutaSalida = "src/main/resources/escritura/" + name + "_peregrino.pdf";

			JasperExportManager.exportReportToPdfFile(print, rutaSalida);
// no hace flata pa adat
			// abrirPDF(rutaSalida);

		} catch (JRException | SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			System.out.println(e.getStackTrace());
		} finally {
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	public void abrirPDF(String rutaSalida) {
		File archivoPDF = new File(rutaSalida);
		if (!archivoPDF.exists()) {
			System.err.println("El archivo no existe: " + rutaSalida);
			return;
		}
		try {
			Runtime.getRuntime().exec(new String[] { "cmd", "/c", "start", "", archivoPDF.getAbsolutePath() });

		} catch (IOException e) {

		}
	}

	private DataSource getDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl("jdbc:mysql://localhost:3306/bdtarea3ad?useSSL=false");
		ds.setUsername("root");
		ds.setPassword("");
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		return ds;
	}
}
