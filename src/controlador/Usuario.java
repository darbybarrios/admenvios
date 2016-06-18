package controlador;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import general.Credenciales;
import general.EncriptarClave;
import modelo.Usuarios;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class Usuario extends GenericForwardComposer{
	private Window windUsuario;
	private Usuarios usuario;
	private Textbox txtCi;
	private Textbox txtNombre;
	private Textbox txtApellido;
	private Textbox txtCorreo;
	private Textbox txtTelefono;
	private Textbox txtDireccion;
	private Textbox txtIdentificador;
	private Radio ra1;
	private Radio ra2;
	private Credenciales Credenciales;
	
	public void onCreate$windUsuario(){
		Credenciales =(Credenciales) session.getAttribute("Credenciales");
		usuario = (Usuarios) windUsuario.getAttribute("Usuario", true);
		CargarUsuario();
	}
	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	
	public void CargarUsuario(){
		txtCi.setText(usuario.getUsuario());
		txtNombre.setText(usuario.getNombre());
		txtApellido.setText(usuario.getApellido());
		txtCorreo.setText(usuario.getCorreo());
		txtTelefono.setText(usuario.getTelefono());
		txtDireccion.setText(usuario.getDireccion());
		if(usuario.getDestino().getIdDestino() == 1){
			ra2.setChecked(true);
		}else{
			ra1.setChecked(true);
		}
		txtIdentificador.setText(usuario.getIdentificador());
		
		
		
	}
	
	public void onClick$btnModificar() throws Exception{
		
		if(!usuario.isActivo()){
			usuario.setActivo(true);
			EnviarCorreo();
		}
		usuario.setIdentificador(txtIdentificador.getText());
		usuario.modificar(usuario);
		Messagebox.show("Usuario modificado con exito.", "Mensaje",Messagebox.OK, Messagebox.INFORMATION);
		windUsuario.detach();
		
		
	}
	
	public void EnviarCorreo() {
		  
		// Recipient's email ID needs to be mentioned.
	      String to = txtCorreo.getText().trim();
	      String mensaje ="";
		
		  //String to = "darbybarrios@gmail.com";

	      // Sender's email ID needs to be mentioned
	      String from = "contacto@cargoboxpanama.com";

	      // Assuming you are sending email from localhost
	      String host = "mail.cargoboxpanama.com";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	     //properties.setProperty("mail.smtp.user", "contacto@cargoboxpanama.com"); 
	    // properties.setProperty("mail.smtp.password", "18736830");
	     properties.setProperty("mail.smtp.host", host);
	     properties.setProperty("mail.smtp.port", "25");
	     properties.setProperty("mail.smtp.auth", "true");
	     properties.setProperty("mail.smtp.starttls.enable", "true");

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("Detalles de la cuenta de "+txtNombre.getText()+" "+txtApellido.getText()+" en CargoBox Panama (aprobada)");

	         // Now set the actual message
	         
	         
	         
	         
	         mensaje = "Hola "+txtNombre.getText()+" "+txtApellido.getText()+" su registro se ha completado con exito!!! \n";
	         mensaje += " \n";
	         mensaje += "Usuario: "+usuario.getUsuario()+" \n";
	         mensaje += "Contraseña: "+EncriptarClave.Desencriptar(usuario.getClave())+" \n";
	         mensaje += " \n";
	         mensaje += "Nuestro sistema le ha asignado el numero de identificación ("+txtIdentificador.getText()+") el \n";
	         mensaje += "cual debe colocar junto con nuestra dirección en Miami como se detalla a \n";
	         mensaje += "continuación: \n";
	         
	         if(usuario.getDestino().getIdDestino() == 1){
	        	 mensaje += " \n";
	        	 mensaje += "DIRECCIÓN PARA ENVIOS AEREOS \n";
		         mensaje += ""+txtNombre.getText()+" "+txtApellido.getText()+" \n";
		         mensaje += "7230 NW 56th Street /"+txtIdentificador.getText()+" \n";
		         mensaje += "Miami, Florida 33166 \n";
		         mensaje += "Tel:  (305) 677.0991 \n";
		         mensaje += " \n";
		         
		         mensaje += " \n";
		         mensaje += "DIRECCIÓN PARA ENVIOS MARITIMOS \n";
		         mensaje += ""+txtNombre.getText()+" "+txtApellido.getText()+" \n";
		         mensaje += "8020 NW 60th St / CargoBox \n";
		         mensaje += "Miami, Florida 33166 \n";
		         mensaje += "Tel:  (786) 315.4600 \n";
		         mensaje += " \n";
		         
	         }else{
	        	 mensaje += " \n";
		         mensaje += ""+txtNombre.getText()+" "+txtApellido.getText()+" \n";
		         mensaje += "7230 NW 56th street/"+txtIdentificador.getText()+" \n";
		         mensaje += "Miami, Florida 33166 \n";
		         mensaje += "Tel:  (305) 677.0991 \n";
		         mensaje += " \n";
	         }
	         
	         
	         
	         
	         
	         mensaje += "Ingresar al sistema con el usuario dado y notificar los envios realizados a nuestro almacenes \n";
	         mensaje += " \n";
	         mensaje += "Att, \n";
	         mensaje += "-- El equipo de CARGO BOX \n";
	         
	         
	         message.setText(mensaje);

	         // Send message
	         Transport t = session.getTransport("smtp");
	         t.connect("contacto@cargoboxpanama.com","18736830");
	         t.sendMessage(message,message.getAllRecipients());
	         t.close();
	         //Transport.send(message);
	         System.out.println("Sent message successfully....");



        } catch (Exception e) {
    	    e.printStackTrace();
    	}
    }

}
