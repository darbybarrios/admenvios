package controlador;

import general.Credenciales;
import general.EncriptarClave;


import java.util.List;

import modelo.Destino;
import modelo.Pais;
import modelo.TipoUsuario;
import modelo.Usuarios;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;

public class RegistrarEmpleado extends GenericForwardComposer{
	private Combobox cmbTipo;
	private Textbox txtNombre;
	private Textbox txtApellido;
	private Textbox txtCorreo;
	private Textbox txtUsuario;
	private Textbox txtClave;
	private Textbox txtId;
	private Listbox listaEmpleados;
	private Button btnModificar;
	private Button btnRegistrar;
	private String auxClave;
	private Credenciales Credenciales;
	private Radio ra1;
	private Radio ra2;
	
	public RegistrarEmpleado() {
		
	}
	public void onCreate$grContenedor(){
		Credenciales =(Credenciales) session.getAttribute("Credenciales");
		LlenarComboTipo();
		CargarEmpleados();
	}
	
	public void onClick$btnRegistrar()throws Exception{
		Guardar();
		onClick$btnLimpiar();
		LimpiarList(listaEmpleados);
		CargarEmpleados();
		
	}
	
	public void onClick$btnModificar()throws Exception{
		Modificar();
		onClick$btnLimpiar();
		LimpiarList(listaEmpleados);
		CargarEmpleados();
		
	}
	public void onClick$btnLimpiar()throws Exception{
		txtNombre.setConstraint("");
		txtApellido.setConstraint("");
		txtUsuario.setConstraint("");
		txtCorreo.setConstraint("");
		txtClave.setConstraint("");
		
		txtUsuario.setReadonly(false);
		txtNombre.setText("");
		txtApellido.setText("");
		txtUsuario.setText("");
		txtCorreo.setText("");
		txtClave.setText("");
		cmbTipo.setText("");
		btnModificar.setVisible(false);
		btnRegistrar.setVisible(true);
		ra1.setChecked(false);
		ra2.setChecked(false);
	}
	
	
	
	
	public void LlenarComboTipo(){
		try {			
			TipoUsuario objTipo = new TipoUsuario();
			List<Object[]> lista = null;
			lista = objTipo.ConsultarTipoUsuarioActivo();
			if (lista.size() > 0) {
				int i = 0;
				while (i < lista.size()) {
					Comboitem combo = new Comboitem();
					combo.setValue((Integer)lista.get(i)[0]);
					combo.setLabel((String)lista.get(i)[1]);
					cmbTipo.appendChild(combo);
					++i;
				}
				
				
			} else {
				Comboitem combo = new Comboitem();
				combo.setValue(0);
				combo.setLabel("- No existen estados registradas -");
				cmbTipo.appendChild(combo);
			}
		} catch (Exception ex) {
			System.out.println("Excepcion - LlenarComboPais - " + ex.toString());
		}
		
	}
	
	public void Guardar()throws Exception{
		try{
			txtNombre.setConstraint("no empty");
			txtApellido.setConstraint("no empty");
			txtUsuario.setConstraint("no empty");
			txtCorreo.setConstraint("/.+@.+\\.[a-z]+/: Por favor sumistre una cuenta de correo válida.");
			txtClave.setConstraint("no empty");
			
			Usuarios objUsuario = new Usuarios();
			Pais objPais = new Pais();
			Destino objDestino = new Destino();
			TipoUsuario objTipo = new TipoUsuario();
			
			if(cmbTipo.getSelectedItem() != null){				
			
				objPais.setIdPais(1);
				
				objUsuario.setPais(objPais);
				objUsuario.setUsuario(txtUsuario.getText().trim());
				objUsuario.setNombre(txtNombre.getText().trim());
				objUsuario.setApellido(txtApellido.getText().trim());
				objUsuario.setCorreo(txtCorreo.getText().trim());
				objUsuario.setTelefono("");
				objUsuario.setDireccion("");
				objUsuario.setClave(EncriptarClave.Encriptar(txtClave.getText()));
				objUsuario.setEstatus(true);
				objUsuario.setActivo(true);
				objTipo.setIdTipoUsuario(new Integer (cmbTipo.getSelectedItem().getValue().toString()));
				objUsuario.setTipoUsuario(objTipo);
				if(Validar(txtUsuario.getText().trim())){
					if (ra1.isChecked()){
						objDestino.setIdDestino(new Integer (ra1.getValue().toString()));
						objUsuario.setDestino(objDestino);				
						objUsuario.incluir(objUsuario);				
						Messagebox.show("Empleado registrado con exito. ", "Mensaje",Messagebox.OK, Messagebox.INFORMATION);				
						
					}else if(ra2.isChecked()){
						objDestino.setIdDestino(new Integer (ra2.getValue().toString()));
						objUsuario.setDestino(objDestino);				
						objUsuario.incluir(objUsuario);				
						Messagebox.show("Empleado registrado con exito. ", "Mensaje",Messagebox.OK, Messagebox.INFORMATION);				
						
					}else{
						Messagebox.show("Debe seleccionar un destino", "Mensaje",Messagebox.OK, Messagebox.INFORMATION);
					}
						
					
				}else{
					Messagebox.show("Ya el usuario se encuentra registrado", "Mensaje",Messagebox.OK, Messagebox.INFORMATION);
					txtUsuario.setFocus(true);
				}
			}else{
				Messagebox.show("Debe seleccionar un Tipo", "Mensaje",Messagebox.OK, Messagebox.INFORMATION);
			}
		
			
		}catch (Exception ex) {
			throw ex;
		}
		
	}
	
	public void Modificar()throws Exception{
		try{
			txtNombre.setConstraint("no empty");
			txtApellido.setConstraint("no empty");
			txtUsuario.setConstraint("no empty");
			txtCorreo.setConstraint("/.+@.+\\.[a-z]+/: Por favor sumistre una cuenta de correo válida.");
			txtClave.setConstraint("no empty");
			
			Usuarios objUsuario = new Usuarios();
			Pais objPais = new Pais();
			Destino objDestino = new Destino();
			TipoUsuario objTipo = new TipoUsuario();
			
			if(cmbTipo.getSelectedItem() != null){				
			
				objPais.setIdPais(1);
				objUsuario.setIdUsuario(Integer.valueOf(txtId.getValue()));
				objUsuario.setPais(objPais);
				objUsuario.setUsuario(txtUsuario.getText().trim());
				objUsuario.setNombre(txtNombre.getText().trim());
				objUsuario.setApellido(txtApellido.getText().trim());
				objUsuario.setCorreo(txtCorreo.getText().trim());
				objUsuario.setTelefono("");
				objUsuario.setDireccion("");
				if(auxClave.equals(txtClave.getValue())){
					objUsuario.setClave(auxClave);
				}else{
					objUsuario.setClave(EncriptarClave.Encriptar(txtClave.getText()));
				}
				
				
				objUsuario.setEstatus(true);
				objUsuario.setActivo(true);
				objTipo.setIdTipoUsuario(new Integer (cmbTipo.getSelectedItem().getValue().toString()));
				objUsuario.setTipoUsuario(objTipo);
				if (ra1.isChecked()){
					objDestino.setIdDestino(new Integer (ra1.getValue().toString()));
					objUsuario.setDestino(objDestino);
				}else if(ra2.isChecked()){
					objDestino.setIdDestino(new Integer (ra2.getValue().toString()));
					objUsuario.setDestino(objDestino);	
				}				
				objUsuario.modificar(objUsuario);				
				Messagebox.show("Empleado modificado con exito.", "Mensaje",Messagebox.OK, Messagebox.INFORMATION);				
				txtUsuario.setFocus(true);
				
			}else{
				Messagebox.show("Debe seleccionar un Tipo", "Mensaje",Messagebox.OK, Messagebox.INFORMATION);
			}
		
			
		}catch (Exception ex) {
			throw ex;
		}
		
	}
	
	public void CargarEmpleados(){
		List<Object[]> listaUsuario = null;
		Usuarios objUsuario = new Usuarios();
		listaUsuario = objUsuario.ConsultarEmpleadosActivo();
		if(listaUsuario.size()>0){			
				for (int i = 0; i < listaUsuario.size(); i++) {
					final Listitem item = new Listitem();					
					item.appendChild(new Listcell(String.valueOf((Integer) listaUsuario.get(i)[0])));
					item.appendChild(new Listcell((String) listaUsuario.get(i)[1]));					
					item.appendChild(new Listcell((String) listaUsuario.get(i)[2]));
					item.appendChild(new Listcell((String) listaUsuario.get(i)[3]));
					item.appendChild(new Listcell((String) listaUsuario.get(i)[9]));
					item.appendChild(new Listcell((String) listaUsuario.get(i)[5]));
					item.appendChild(new Listcell(String.valueOf((Integer) listaUsuario.get(i)[10])));
					item.appendChild(new Listcell((String) listaUsuario.get(i)[6]));
					
					listaEmpleados.appendChild(item);
				}	
				
			
			
		}
	}
	
	private Boolean Validar(String cedula){
		Boolean existe = true;
		List<Object[]> listaUsuario = null;
		Usuarios objUsuario = new Usuarios();
		listaUsuario = objUsuario.ConsultarUsuarioXLoginActivo(cedula);
		if(listaUsuario.size()>0){			
			existe = false;			
		}
		return existe;
	}
	
	public void onSelect$listaEmpleados()throws InterruptedException{
		
		if (listaEmpleados.getSelectedItem() != null) {
			txtUsuario.setReadonly(true);
			btnModificar.setVisible(true);
			btnRegistrar.setVisible(false);
			List hijo = listaEmpleados.getSelectedItem().getChildren();
			
			
			txtId.setText(((Listcell) hijo.get(0)).getLabel());
			txtUsuario.setText(((Listcell) hijo.get(1)).getLabel());
			txtNombre.setText(((Listcell) hijo.get(2)).getLabel());
			txtApellido.setText(((Listcell) hijo.get(3)).getLabel());
			txtCorreo.setText(((Listcell) hijo.get(4)).getLabel());
			posicionarCombo(cmbTipo,((Listcell) hijo.get(6)).getLabel());
			txtClave.setText(((Listcell) hijo.get(7)).getLabel());
			auxClave = ((Listcell) hijo.get(7)).getLabel();
			
		}
		
	}
	
	public int SeleccionItem(Combobox combo,String valor) {
		int indice=0;
        if (valor == null) {
  			combo.setSelectedIndex(-1);
  		} else {
  			for (int i = 0; combo.getItemCount()>i; ++i) {
  				org.zkoss.zul.Comboitem item = combo.getItemAtIndex(i);
				if (valor.equals(item.getValue().toString())) {  					
					indice=i;
  					break;
  				}
  			}
  		}
        return indice;
  	}
	
	void posicionarCombo(Combobox combo, String cadena){
		 int registro=0;			 
		 registro = SeleccionItem(combo,cadena);			 
		 combo.setSelectedIndex(registro);
	}

	void LimpiarList(Listbox lista) {
		int n = 0;
		
		while (lista.getItems().size() > n) {
			lista.removeItemAt(n);
		}
		lista.setRows(0);
	}
}
