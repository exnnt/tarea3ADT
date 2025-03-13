package com.luisdbb.tarea3AD2024base.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.config.existdb.ExistDBManageante;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

@Controller
public class CU11Controller implements Initializable {
	@Lazy
	@Autowired
	private StageManager stageManager;
	@FXML
	private javafx.scene.control.Label lblLogin1;
	@FXML
	private TextArea tesarea;
	@FXML
	private ComboBox comboCarnet;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		lblLogin1.setText("Ver carnets creados en : " + Tarea3Ad2024baseApplication.inicial.getNombre());
		List<String> carnets = ExistDBManageante.getCarnetsParada(Tarea3Ad2024baseApplication.inicial.getNombre());
		if (carnets.isEmpty()) {
			comboCarnet.setPromptText("No hay Carnets");
			comboCarnet.setDisable(true);
		} else {
			for (String carnet : carnets) {
				String carnetName = extractCarnetName(carnet);
				comboCarnet.getItems().add(carnetName);
			}

			comboCarnet.setDisable(false);
		}

	}

	@FXML
	private void volver(ActionEvent event) throws IOException {

		stageManager.switchScene(FxmlView.PARADA);

	}

	private String extractCarnetName(String xml) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new ByteArrayInputStream(xml.getBytes()));
			NodeList nodeList = doc.getElementsByTagName("nombre");
			return nodeList.item(0).getTextContent();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "a";

	}
	@FXML
	public void setxml() {
		List<String> carnets = ExistDBManageante.getCarnetsParada(Tarea3Ad2024baseApplication.inicial.getNombre());
		  int index = comboCarnet.getSelectionModel().getSelectedIndex();
          tesarea.setText(carnets.get(index));
	}
}