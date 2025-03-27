package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.Perfil;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

@Controller
public class EditController implements Initializable {
	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private PeregrinoService peregrinoService;
	@Autowired
	private UserService userService;

	public Peregrino p = null;
	public Usuario u = null;
	@FXML
	public TextField userId;
	@FXML
	private Button btnConfirm;
	@FXML
	private Button btnVolver;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		p = peregrinoService.findbyiduser(Tarea3Ad2024baseApplication.useractivo.getId());
		u = userService.find(Tarea3Ad2024baseApplication.useractivo.getId());
		userId.setText(p.getNombre());

	}

	@FXML
	private void volver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.PEREGRINO);
	}

	@FXML
	public void editaUser() {
		String e = userId.getText();
		System.out.println(e);
		if (!checkname()) {
			userId.setText(p.getNombre());
			errorAlert();
		} else {
			peregrinoService.actualizarPeregrino(p.getId(), e);
			oleAlert();
			stageManager.switchScene(FxmlView.PEREGRINO);
		}
	}

	private void errorAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Input invalid");
		alert.setHeaderText(null);
		alert.setContentText("El nombre no puede estar vacio ni contener caracteres especiales");
		alert.showAndWait();

	}
	
	private void oleAlert() {
		String e = userId.getText();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Operacion Completada");
		alert.setHeaderText(null);
		alert.setContentText(
				"Cambio de nombre efectuado, Buen camino " + e + "\n Tu nombre de usuario sigue siendo " + u.getName());
		alert.showAndWait();

	}

	public boolean checkname() {
		String e = userId.getText();
		if (e == null || e.trim().isEmpty()) {
			return false;
		}

		String regex = "^[a-zA-Z0-9 ]+$";

		return e.matches(regex);
	}
}
