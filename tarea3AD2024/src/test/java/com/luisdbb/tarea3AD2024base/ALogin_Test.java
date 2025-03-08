package com.luisdbb.tarea3AD2024base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luisdbb.tarea3AD2024base.controller.LoginController;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.Perfil;
import com.luisdbb.tarea3AD2024base.services.Sesion;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.view.FxmlView;
import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class ALogin_Test {

	@InjectMocks
	private LoginController controller;

	@Mock
	private UserService userService;
	@Mock
	private PeregrinoService peregrinoService;
	@Mock
	private ParadaService paradaService;
	@Mock
	private StageManager stageManager;
	@Mock
	private TextField username;
	@Mock
	private PasswordField password;
	@Mock
	private TextField passwordvisible;
	@Mock
	private ToggleButton viewtoggle;
	@Mock
	private ImageView imgpass;
	@Mock
	private Label lblLogin;

	@BeforeAll
	static void setUpAll() {

		Platform.startup(() -> {
		});
	}

	@BeforeEach
	public void setUp() {
		Tarea3Ad2024baseApplication.useractivo = new Sesion();
		Tarea3Ad2024baseApplication.useractivo.setId(0);
		Tarea3Ad2024baseApplication.useractivo.setNombre("Invitado");
		Tarea3Ad2024baseApplication.useractivo.setPerfil(Perfil.INVITADO);
		MockitoAnnotations.openMocks(this);
		controller = spy(controller);

		controller.username = username;
		controller.password = password;
		controller.passwordvisible = passwordvisible;
		controller.viewtoggle = viewtoggle;
		controller.imgpass = imgpass;
		controller.lblLogin = lblLogin;

	}

	@Test
	void testLoginSuccessAdmin() throws IOException {

		when(username.getText()).thenReturn("admin");
		when(password.getText()).thenReturn("admin");

		when(userService.authenticate("admin", "admin")).thenReturn(1);

		controller.login();
		assertEquals(Perfil.ADMIN, Tarea3Ad2024baseApplication.useractivo.getPerfil());

	}

	@Test
	void testLoginSuccessParada() throws IOException {

		when(username.getText()).thenReturn("ferrol");
		when(password.getText()).thenReturn("ferrol");

		when(userService.authenticate("ferrol", "ferrol")).thenReturn(2);

		Usuario mockUser = mock(Usuario.class);
		when(mockUser.getId()).thenReturn(2);
		when(userService.findByName("ferrol")).thenReturn(mockUser);

		Parada parada = new Parada();
		parada.setResponsable("ferrol");
		when(paradaService.findbyrespons("ferrol")).thenReturn(parada);

		controller.login();

		assertEquals(Perfil.PARADA, Tarea3Ad2024baseApplication.useractivo.getPerfil());
	}

	@Test
	void testLoginSuccessPeregrino() throws IOException {

		when(username.getText()).thenReturn("luis");
		when(password.getText()).thenReturn("luis");

		when(userService.authenticate("luis", "luis")).thenReturn(3);

		Usuario mockUser = mock(Usuario.class);
		when(mockUser.getId()).thenReturn(3);
		when(userService.findByName("luis")).thenReturn(mockUser);

		Peregrino mockPeregrino = mock(Peregrino.class);
		when(peregrinoService.findbyiduser(3)).thenReturn(mockPeregrino);

		controller.login();

		assertEquals(Perfil.PEREGRINO, Tarea3Ad2024baseApplication.useractivo.getPerfil());
	}

	@Test
	void testLoginFailure() {

		when(username.getText()).thenReturn("mal");
		when(password.getText()).thenReturn("mal");

		when(userService.authenticate("muymal", "megamal")).thenReturn(0);

		Platform.runLater(() -> {
			controller.login();

			verify(controller, times(1)).updateAlert();
		});

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
