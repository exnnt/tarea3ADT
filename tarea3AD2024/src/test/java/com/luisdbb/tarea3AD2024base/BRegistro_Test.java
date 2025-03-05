package com.luisdbb.tarea3AD2024base;

import static org.mockito.Mockito.*;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.quality.Strictness;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.controller.InvitadoController;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.Perfil;
import com.luisdbb.tarea3AD2024base.services.RutaService;
import com.luisdbb.tarea3AD2024base.services.Sesion;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BRegistro_Test {

	@Mock
	private StageManager stageManager;
	@Mock
	private UserService userService;
	@Mock
	private ParadaService paradaService;
	@Mock
	private PeregrinoService peregrinoService;
	@Mock
	private CarnetService carnetService;
	@Mock
	private RutaService rutaService;

	@InjectMocks
	private InvitadoController controller;

	@Mock
	private TextField userId;
	@Mock
	private ComboBox<String> cbNa;
	@Mock
	private ComboBox<String> cbParada;
	@Mock
	private PasswordField pass;

	@BeforeAll
	static void setUpAll() {

		Platform.startup(() -> {
		});
	}

	@BeforeEach
	public void setup() {
		Tarea3Ad2024baseApplication.useractivo = new Sesion();
		Tarea3Ad2024baseApplication.useractivo.setId(0);
		Tarea3Ad2024baseApplication.useractivo.setNombre("Invitado");
		Tarea3Ad2024baseApplication.useractivo.setPerfil(Perfil.INVITADO);
		MockitoAnnotations.openMocks(this);
		controller = spy(controller);
		controller.userId = userId;
		controller.cbNa = cbNa;
		controller.cbParada = cbParada;
		controller.pass = pass;
	}

	@Test
	public void testRegistraUserSuccess() {
		// sin el lenient me daba error y no se muy bien que mas hacer
		lenient().when(userId.getText()).thenReturn("teamporar");
		lenient().when(cbNa.getValue()).thenReturn("Austria");
		lenient().when(cbParada.getValue()).thenReturn("Gijon");
		lenient().when(pass.getText()).thenReturn("idk test");
		Usuario mockUser = mock(Usuario.class);
		lenient().when(mockUser.getId()).thenReturn(1);
		lenient().when(mockUser.getName()).thenReturn("teamporar");
		lenient().when(userService.crearUsuario(any(Usuario.class))).thenReturn(mockUser);
		Peregrino mockPeregrino = mock(Peregrino.class);
		lenient().when(mockPeregrino.getId_user()).thenReturn(1);
		lenient().when(peregrinoService.creaPeregrino(any(Peregrino.class))).thenReturn(mockPeregrino);

		Parada mockParada = mock(Parada.class);
		lenient().when(mockParada.getId()).thenReturn(1L);
		lenient().when(paradaService.findbyName("Gijon")).thenReturn(mockParada);

		Platform.runLater(() -> {
			ActionEvent event = mock(ActionEvent.class);
			controller.registraUser(event);
			assertEquals(Perfil.PEREGRINO, Tarea3Ad2024baseApplication.useractivo.getPerfil());
		});

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
	}

	@Test
	public void testRegistraUserFailureEmptyUsername() {

		when(userId.getText()).thenReturn("");
		when(cbNa.getValue()).thenReturn("Austria");
		when(cbParada.getValue()).thenReturn("Gijon");
		when(pass.getText()).thenReturn("password");

		Platform.runLater(() -> {
			ActionEvent event = mock(ActionEvent.class);
			controller.registraUser(event);
			verify(controller, times(1)).updateAlert();
		});

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRegistraUserFailureInvalidUsername() {
		when(userId.getText()).thenReturn("user@123");
		when(cbNa.getValue()).thenReturn("Austria");
		when(cbParada.getValue()).thenReturn("Gijon");
		when(pass.getText()).thenReturn("password");

		Platform.runLater(() -> {
			ActionEvent event = mock(ActionEvent.class);
			controller.registraUser(event);
			verify(controller, times(1)).updateAlert();
		});

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
	}

	@Test
	public void testRegistraUserFailureEmptyPassword() {

		when(userId.getText()).thenReturn("luis");
		when(cbNa.getValue()).thenReturn("Austria");
		when(cbParada.getValue()).thenReturn("Gijon");
		when(pass.getText()).thenReturn("");

		Platform.runLater(() -> {
			ActionEvent event = mock(ActionEvent.class);
			controller.registraUser(event);
			verify(controller, times(1)).updateAlert();
		});

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
	}

	@Test
	public void testRegistraUserFailureEmptyPais() {

		when(userId.getText()).thenReturn("luis");

		when(cbParada.getValue()).thenReturn("Gijon");
		when(pass.getText()).thenReturn("testerinos");

		Platform.runLater(() -> {
			ActionEvent event = mock(ActionEvent.class);
			controller.registraUser(event);
			verify(controller, times(1)).updateAlert();
		});

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
	}

	@Test
	public void testRegistraUserFailureEmptyParada() {

		when(userId.getText()).thenReturn("luis");

		when(cbNa.getValue()).thenReturn("Austria");
		when(pass.getText()).thenReturn("testerinos");

		Platform.runLater(() -> {
			ActionEvent event = mock(ActionEvent.class);
			controller.registraUser(event);
			verify(controller, times(1)).updateAlert();
		});

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
	}
}