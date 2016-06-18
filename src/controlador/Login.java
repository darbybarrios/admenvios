package controlador;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import general.Credenciales;

public class Login extends GenericForwardComposer {
	private Textbox txtUsuario;
	private Textbox txtContrasena;
	Credenciales Credenciales = new Credenciales();
	public Login() {
		// TODO Auto-generated constructor stub
	}
	public void onClick$btnEntrar(){
		validarLogin();
	}
	public void onOK$txtContrasena() throws Exception {
		validarLogin();
	}
	public void onClick$btnRegistrarse(){
		try {
			final Window ventana = (Window) Executions.createComponents("RegistrarUsuario.zul", null, null);
			ventana.setMaximizable(false);
			ventana.setClosable(true);
			ventana.doModal();
		} catch (Exception ex) {
			Messagebox.show(ex.getMessage(), "Error", Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	public void validarLogin(){   	
		        txtUsuario.setConstraint("no empty");
		        txtContrasena.setConstraint("no empty");
		        txtUsuario.getValue();
		        txtContrasena.getValue();
		        try{
		    		HttpServletRequest servletRequest= (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		    	    Credenciales = Credenciales.ValidarLogin(txtUsuario.getText(),txtContrasena.getText(),servletRequest);                     
		    	    session.setAttribute("Credenciales",Credenciales);
		    	    
		    	    Executions.getCurrent().sendRedirect("Principal.zul");        	
		        }
		        catch (Exception ex){
		              if (ex.getMessage()==null){
		            	  Messagebox.show("El usuario"+" " + "''"+txtUsuario.getText()+"''"+" "+"no tiene permisos para ingresar al sistema", "Error", Messagebox.OK,Messagebox.ERROR);
		                  
		              }else{
		            	  Messagebox.show(ex.getMessage(), "Error", Messagebox.OK,Messagebox.ERROR);
		            	  
		              }
		        } 
		        
		        
		     
	}
	
	public void onClick$btnLimpiar(){
		txtContrasena.setText("");
		txtUsuario.setText("");
	}
}
