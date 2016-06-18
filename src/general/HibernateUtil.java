package general;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.zkoss.zul.Messagebox;

import javax.servlet.http.HttpSession;
/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author insicontratado
 */
@SuppressWarnings("unused")
public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		Configuration configuracion = new Configuration().configure("hibernate.cfg.xml");
		try {
			File archivo = null;
			/*
			System.out.println("Ruta raíz sistema: " + MetodosGenericos.getPathSistema());
			System.out.println("Ruta completa: " + MetodosGenericos.getPathSistema() + "/webapps/siges/WEB-INF/configuracion/");
			*/
			archivo = new File(System.getProperty("user.dir") + "/webapps/cargobox/WEB-INF/configuracion/hibernate.web.cfg.xml");
			
			if (archivo.exists()) {
				configuracion.configure(archivo);
			} else {
				Logger.getLogger(HibernateUtil.class.getName()).log(Level.WARNING,"No se encontro el archivo de configuracion extra para hibernate");
			}
			sessionFactory = configuracion.buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed. " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

  public static SessionFactory getSessionFactory(){
      if(sessionFactory == null){

      }
		return sessionFactory;
  } 
}