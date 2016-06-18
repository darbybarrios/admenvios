package controlador;

import general.Credenciales;
import general.EncriptarClave;

import java.util.List;

import modelo.Destino;
import modelo.Pais;
import modelo.TipoUsuario;
import modelo.Usuarios;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ListaUsuarios extends GenericForwardComposer{
	private Listbox listaUsuarios;
	private Listbox listaUsuariosActivos;
	private Textbox txtNombre;
	private Textbox txtApellido;
	private Textbox txtCorreo;
	private Textbox txtUsuario;
	private Textbox txtClave;
	private Textbox txtId;
	private Credenciales Credenciales;
	
	public void onCreate$grContenedor(){
		Credenciales =(Credenciales) session.getAttribute("Credenciales");
		CargarUsuarios();
	}
	
	public ListaUsuarios() {
		// TODO Auto-generated constructor stub
	}
	
	public void onSelect$tab1(){
		LimpiarList(listaUsuarios);
		CargarUsuarios();
	}
	
	public void onSelect$tab2(){
		LimpiarList(listaUsuariosActivos);
		CargarUsuariosActivos();
	}
	
	public void onSelect$listaUsuarios(){
		try {
			List<Object[]> Usuario = null;
			Usuarios objUsuario = new Usuarios();
			Pais objPais = new Pais();
			Destino objDestino = new Destino();
			TipoUsuario objTipo = new TipoUsuario();
			
			if (listaUsuarios.getSelectedItem() != null) {
				
				List hijo = listaUsuarios.getSelectedItem().getChildren();
				
				Usuario = objUsuario.ConsultarUsuariosxId(((Listcell) hijo.get(0)).getLabel());
				if(Usuario.size()>0){			
					for (int i = 0; i < Usuario.size(); i++) {
						
						objUsuario.setIdUsuario((Integer) Usuario.get(i)[0]);
						objUsuario.setUsuario((String) Usuario.get(i)[1]);
						objUsuario.setNombre((String) Usuario.get(i)[2]);
						objUsuario.setApellido((String) Usuario.get(i)[3]);
						objUsuario.setClave((String) Usuario.get(i)[4]);
						objUsuario.setIdentificador((String) Usuario.get(i)[5]);
						objTipo.setIdTipoUsuario((Integer) Usuario.get(i)[6]);
						objUsuario.setTipoUsuario(objTipo);
						objUsuario.setEstatus((Boolean) Usuario.get(i)[7]);
						objUsuario.setActivo((Boolean) Usuario.get(i)[8]);
						objPais.setIdPais((Integer) Usuario.get(i)[9]);
						objUsuario.setPais(objPais);
						objUsuario.setDireccion((String) Usuario.get(i)[10]);
						objUsuario.setTelefono((String) Usuario.get(i)[11]);
						objDestino.setIdDestino((Integer) Usuario.get(i)[12]);
						objUsuario.setDestino(objDestino);
						objUsuario.setCorreo((String) Usuario.get(i)[13]);
						objUsuario.setFechaRegistro((String) Usuario.get(i)[14]);
					
					
					}
				}					
				
			
			}
			final Window ventana = (Window) Executions.createComponents("Usuario.zul", null, null);
			ventana.setMaximizable(false);
			ventana.setClosable(true);
			ventana.setAttribute("Usuario",objUsuario);
			ventana.doModal();
			
			LimpiarList(listaUsuarios);
			CargarUsuarios();
			
			
		} catch (Exception ex) {
			Messagebox.show(ex.getMessage(), "Error", Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	public void onSelect$listaUsuariosActivos(){
		try {
			List<Object[]> Usuario = null;
			Usuarios objUsuario = new Usuarios();
			Pais objPais = new Pais();
			Destino objDestino = new Destino();
			TipoUsuario objTipo = new TipoUsuario();
			
			if (listaUsuariosActivos.getSelectedItem() != null) {
				
				List hijo = listaUsuariosActivos.getSelectedItem().getChildren();
				
				Usuario = objUsuario.ConsultarUsuariosxId(((Listcell) hijo.get(0)).getLabel());
				if(Usuario.size()>0){			
					for (int i = 0; i < Usuario.size(); i++) {
						
						objUsuario.setIdUsuario((Integer) Usuario.get(i)[0]);
						objUsuario.setUsuario((String) Usuario.get(i)[1]);
						objUsuario.setNombre((String) Usuario.get(i)[2]);
						objUsuario.setApellido((String) Usuario.get(i)[3]);
						objUsuario.setClave((String) Usuario.get(i)[4]);
						objUsuario.setIdentificador((String) Usuario.get(i)[5]);
						objTipo.setIdTipoUsuario((Integer) Usuario.get(i)[6]);
						objUsuario.setTipoUsuario(objTipo);
						objUsuario.setEstatus((Boolean) Usuario.get(i)[7]);
						objUsuario.setActivo((Boolean) Usuario.get(i)[8]);
						objPais.setIdPais((Integer) Usuario.get(i)[9]);
						objUsuario.setPais(objPais);
						objUsuario.setDireccion((String) Usuario.get(i)[10]);
						objUsuario.setTelefono((String) Usuario.get(i)[11]);
						objDestino.setIdDestino((Integer) Usuario.get(i)[12]);
						objUsuario.setDestino(objDestino);
						objUsuario.setCorreo((String) Usuario.get(i)[13]);
						objUsuario.setFechaRegistro((String) Usuario.get(i)[14]);
					
					
					}
				}					
				
			
			}
			final Window ventana = (Window) Executions.createComponents("Usuario.zul", null, null);
			ventana.setMaximizable(false);
			ventana.setClosable(true);
			ventana.setAttribute("Usuario",objUsuario);
			ventana.doModal();
			
			LimpiarList(listaUsuariosActivos);
			CargarUsuariosActivos();
			
			
		} catch (Exception ex) {
			Messagebox.show(ex.getMessage(), "Error", Messagebox.OK,Messagebox.ERROR);
		}
	}

	public void CargarUsuarios(){
		List<Object[]> listaUsuario = null;
		Usuarios objUsuario = new Usuarios();
		if (Credenciales.getTipo().equals("AD")||Credenciales.getTipo().equals("ROOT")){
			listaUsuario = objUsuario.ConsultarUsuarios(false);
		}else if(Credenciales.getTipo().equals("EM")||Credenciales.getTipo().equals("ADP")||Credenciales.getTipo().equals("ADV")){
			listaUsuario = objUsuario.ConsultarUsuariosxDestino(false, Credenciales.getId_destino());
		}
		
		
		
		if(listaUsuario.size()>0){			
				for (int i = 0; i < listaUsuario.size(); i++) {
					final Listitem item = new Listitem();					
					item.appendChild(new Listcell(String.valueOf((Integer) listaUsuario.get(i)[0])));
					item.appendChild(new Listcell((String) listaUsuario.get(i)[1]));					
					item.appendChild(new Listcell((String) listaUsuario.get(i)[2]));
					item.appendChild(new Listcell((String) listaUsuario.get(i)[3]));
					item.appendChild(new Listcell((String) listaUsuario.get(i)[8]));
					item.appendChild(new Listcell((String) listaUsuario.get(i)[4]));
					item.appendChild(new Listcell(String.valueOf((Integer) listaUsuario.get(i)[7])));
					item.appendChild(new Listcell((String) listaUsuario.get(i)[5]));
					item.appendChild(new Listcell(String.valueOf((Integer) listaUsuario.get(i)[10])));
					listaUsuarios.appendChild(item);
				}
		}
	}
	public void CargarUsuariosActivos(){
		List<Object[]> listaUsuario = null;
		Usuarios objUsuario = new Usuarios();
		if (Credenciales.getTipo().equals("AD")||Credenciales.getTipo().equals("ROOT")){
			listaUsuario = objUsuario.ConsultarUsuarios(true);
		}else if(Credenciales.getTipo().equals("EM")||Credenciales.getTipo().equals("ADP")||Credenciales.getTipo().equals("ADV")){
			listaUsuario = objUsuario.ConsultarUsuariosxDestino(true, Credenciales.getId_destino());
		}
		if(listaUsuario.size()>0){			
				for (int i = 0; i < listaUsuario.size(); i++) {										
					final Listitem item = new Listitem();					
					item.appendChild(new Listcell(String.valueOf((Integer) listaUsuario.get(i)[0])));
					item.appendChild(new Listcell((String) listaUsuario.get(i)[1]));					
					item.appendChild(new Listcell((String) listaUsuario.get(i)[2]));
					item.appendChild(new Listcell((String) listaUsuario.get(i)[3]));
					item.appendChild(new Listcell((String) listaUsuario.get(i)[8]));
					item.appendChild(new Listcell((String) listaUsuario.get(i)[4]));
					item.appendChild(new Listcell(String.valueOf((Integer) listaUsuario.get(i)[7])));
					item.appendChild(new Listcell((String) listaUsuario.get(i)[5]));					
					listaUsuariosActivos.appendChild(item);
				}
		}
	}
	
	void LimpiarList(Listbox lista) {
		int n = 0;
		
		while (lista.getItems().size() > n) {
			lista.removeItemAt(n);
		}
		lista.setRows(0);
	}

}
