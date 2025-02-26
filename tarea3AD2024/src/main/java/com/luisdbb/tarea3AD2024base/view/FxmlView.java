package com.luisdbb.tarea3AD2024base.view;

import java.util.ResourceBundle;

public enum FxmlView {
	INVITADO {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("user.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/MenuInvitado.fxml";
		}
	},
	ADMIN1 {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("admin.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/MenuParada2.fxml";
		}
	},
	ADMIN2 {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("admin.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ServiciosSeVen.fxml";
		}
	},
	ENVIAR{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("paradas.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/EnvioCasa.fxml";
		}
	},
	LOGIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("login.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Login.fxml";
		}
	},
	CONJUNTO {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("login.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ConjuntoServicios.fxml";
		}
	},
	
	
	ADMIN{
		@Override
		public String getTitle() {
		return getStringFromResourceBundle("admin.title");
	}

	@Override
	public String getFxmlFile() {
		return "/fxml/MenuAdmin.fxml";
	}
},
	EXPORT{
	@Override
	public String getTitle() {
	return getStringFromResourceBundle("exportar.title");
}

@Override
public String getFxmlFile() {
	return "/fxml/CU3.fxml";
}
},
	PEREGRINO{
	@Override
	public String getTitle() {
	return getStringFromResourceBundle("peregrino.title");
}

@Override
public String getFxmlFile() {
	return "/fxml/ExportPeregrino.fxml";
}
},
	PARADA{
	@Override
	public String getTitle() {
	return getStringFromResourceBundle("paradas.title");
	//change esto pa pulir
}
	

@Override
public String getFxmlFile() {
	return "/fxml/MenuParada.fxml";
}
},
	SELLAR{
		@Override
		public String getTitle() {
		return getStringFromResourceBundle("sellar.title");
		//change esto pa pulir
	}
		

	@Override
	public String getFxmlFile() {
		return "/fxml/CU5.fxml";
	}
	};
	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
