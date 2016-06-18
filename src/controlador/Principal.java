package controlador;

import general.Credenciales;

import org.hibernate.transform.ToListResultTransformer;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Toolbarbutton;

public class Principal extends GenericForwardComposer {
	
	private Include pagina;
	private Credenciales Credenciales;
	private Toolbarbutton menuInformacion;
	private Toolbarbutton menuRegistrar;
	private Toolbarbutton menuLista;
	private Toolbarbutton menuEnvios;
	private Toolbarbutton menuUsuarios;
	private Toolbarbutton menuEmpleados;
	private Tab tabEnvios;
	private Tab tabAdministracion;
	private Tabpanel tabpEnvios;
	private Tabpanel tabpAdministracion;
	public void onCreate$windPrincipal(){
		Credenciales =(Credenciales) session.getAttribute("Credenciales");
		
		if(Credenciales.getTipo().equals("ROOT")){
			menuInformacion.setVisible(true);
			menuRegistrar.setVisible(true);
			menuLista.setVisible(true);
			menuEnvios.setVisible(true);
			menuUsuarios.setVisible(true);
			menuEmpleados.setVisible(true);
			tabEnvios.setVisible(true);
			tabAdministracion.setVisible(true);
		}
		if(Credenciales.getTipo().equals("EM")){
			menuEnvios.setVisible(true);
			tabAdministracion.setVisible(true);
			
		}
		if(Credenciales.getTipo().equals("US")){
			menuInformacion.setVisible(true);
			menuRegistrar.setVisible(true);
			menuLista.setVisible(true);
			tabEnvios.setVisible(true);
			pagina.setSrc("Informacion.zul");
		}
		if(Credenciales.getTipo().equals("AD") || Credenciales.getTipo().equals("ADP") || Credenciales.getTipo().equals("ADV")){			
			menuEnvios.setVisible(true);
			menuUsuarios.setVisible(true);
			tabAdministracion.setVisible(true);
		}
		
		
		
		
		
	}
	
	public Principal() {
		
	}
	
	public void onClick$menuInformacion(){
	
		pagina.setSrc("Informacion.zul");  
	}
	public void onClick$menuRegistrar(){
		pagina.setSrc("RegistrarEnvios.zul");  
	}
	public void onClick$menuLista(){
		pagina.setSrc("ListaEnvios.zul");  
	}
	public void onClick$menuEnvios(){
		pagina.setSrc("ListaEnviosGeneral.zul");  
	}
	public void onClick$menuUsuarios(){
		pagina.setSrc("ListaUsuarios.zul");  
	}
	public void onClick$menuEmpleados(){
		pagina.setSrc("RegistrarEmpleado.zul");  
	}
	public void onClick$btnSalir(){
		Cerrar();
	}

	void Cerrar() {
		try {
			int opcion = Messagebox.show("¿Desea salir del Sistema?","Confirmación", Messagebox.OK | Messagebox.CANCEL,
					Messagebox.QUESTION);
			if (opcion == 1) {	    	   
				session.removeAttribute("Credenciales");
				Executions.getCurrent().sendRedirect("index.zul");
			}
		} catch (Exception ex) {
			
		}
	}
}
