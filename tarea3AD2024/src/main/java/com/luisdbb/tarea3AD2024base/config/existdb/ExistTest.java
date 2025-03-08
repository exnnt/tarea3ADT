package com.luisdbb.tarea3AD2024base.config.existdb;

import java.io.File;

public class ExistTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("prueba conexion");

		File ccarnet = new File(
				"C:/Users/anton/git/tarea3ADT2/tarea3AD2024/src/main/resources/escritura/testfinal_peregrino.xml");
		ExistDBManageante.storeCarnet("PARADA1", ccarnet);
		System.out.println("test");
	}

}
