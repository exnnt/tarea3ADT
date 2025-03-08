package com.luisdbb.tarea3AD2024base;

import static org.mockito.Mockito.*;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.controller.EditController;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.Perfil;
import com.luisdbb.tarea3AD2024base.services.Sesion;
import com.luisdbb.tarea3AD2024base.services.UserService;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

@ExtendWith(MockitoExtension.class)
public class CModificar_Test {

	@Mock
	private StageManager stageManager;

	@Mock
	private PeregrinoService peregrinoService;

	@Mock
	private UserService userService;

	@InjectMocks
	private EditController controller;
	@Mock
	private Peregrino p;
	@Mock
	private Usuario u;

	@BeforeAll
	static void setUpAll() {

		Platform.startup(() -> {
		});
	}

	@BeforeEach
	void setUp() {
		u = new Usuario();
		u.setId(13);
		u.setName("testfinal");

		p = new Peregrino();
		p.setId_user(13);
		p.setNombre("testfinal");
		Tarea3Ad2024baseApplication.useractivo = new Sesion();
		Tarea3Ad2024baseApplication.useractivo.setId(13);

		Tarea3Ad2024baseApplication.useractivo.setNombre("testfinal");

		Tarea3Ad2024baseApplication.useractivo.setPerfil(com.luisdbb.tarea3AD2024base.services.Perfil.PEREGRINO);

		controller.userId = new TextField();
		controller.u = u;
		controller.p = p;

	}

	@Test
	void testEditaUserSuccess() {

		controller.userId.setText("cambiado");
		Platform.runLater(() -> {

			controller.editaUser();

		});

		try {
			Thread.sleep(500);
			//comprueba en "base de datos"
			verify(peregrinoService).actualizarPeregrino(p.getId(), "cambiado");
		} catch (InterruptedException e) {
			e.printStackTrace();

		}

	}

	@Test
	void testEditaUserFailure() {
	   
	    controller.userId.setText(""); 

	    
	    System.out.println("Before editaUser: " + controller.userId.getText());

	
	    Platform.runLater(() -> {
	 
	        assertEquals(controller.checkname(), false); 
	        controller.editaUser();  
	      
	    });

	   
	    try {
	    	//si ye menor q 1 sec de espera falla idk why
	        Thread.sleep(1000);  
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }

	  
	    System.out.println("After editaUser: " + controller.userId.getText());

	  
	    assertEquals("testfinal", controller.userId.getText());
	}
	
}

