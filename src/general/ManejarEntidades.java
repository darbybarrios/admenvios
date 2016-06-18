package general;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author insicontratado
 */
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.*;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

//import general.MetodosGenericos;


/**
 * 
 * @author Insicontratado
 */
@SuppressWarnings("serial")
public class ManejarEntidades implements java.io.Serializable {
	//MetodosGenericos m = new MetodosGenericos();
	//private String path = m.getPathSistema();

	public Session currentSession() {
		/*
		System.out.println("Ruta raíz sistema: " + MetodosGenericos.getPathSistema());
		System.out.println("Ruta completa: " + MetodosGenericos.getPathSistema() + "/webapps/siges/WEB-INF/configuracion/");
		*/
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session;
	}	
	public void incluir(Object objeto)throws Exception {
		Transaction tx=null;
		Session sess =null;		
		String entidad = objeto.getClass().toString();
		String resultado = "OK";
		try {
			sess = currentSession();
			tx=sess.beginTransaction();
			sess.persist(objeto);
			tx.commit();
		} catch (Exception e) {
			throw new Exception("Error al incluir el registro");
		} finally {
			if (sess != null) {
				try {
					//sess.disconnect();
					sess.close();
			    }
				catch (HibernateException e)  {
				
				}
			}
						
		}
	}

	@SuppressWarnings("unchecked")
	public void modificar(Object objeto)throws Exception {
		Transaction tx=null;
		Session sess =null;		
		String entidad = objeto.getClass().toString();
		String resultado = "OK";
		try {
			sess = currentSession();
			tx = sess.beginTransaction();
			sess.merge(objeto);
			tx.commit();
		} catch (Exception e) {
			throw new Exception("Error al modificar el registro");
		} finally {
			if (sess != null) {
				try {
					//sess.disconnect();
					sess.close();
			    }
				catch (HibernateException e)  {
				
				}
			}
			
		}
	}

	@SuppressWarnings("unchecked")
	public void eliminar(Object objeto, ArrayList DatosAuditoria)
			throws Exception {
		Transaction tx=null;
		Session sess = null;
		String entidad = objeto.getClass().toString();
		String resultado = "OK";
		try {
			sess = currentSession();
			tx = sess.beginTransaction();
			sess.delete(objeto);
			tx.commit();
		} catch (Exception e) {
			
			throw new Exception("Error al eliminar el registro");
		} finally {
			if (sess != null) {
				try {
					//sess.disconnect();
					sess.close();
			    }
				catch (HibernateException e)  {
				
				}
			}
			if (resultado.equals("OK") && DatosAuditoria != null) {
				//Auditoria
			}			
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> ConsultarLista(String Sql, ArrayList DatosAuditoria)
			throws Exception {
		Transaction tx=null;
		Session sess = null;
		List<Object> ObjetosLista = null;
		String entidad = "";
		String resultado = "OK";
		try {
			sess = currentSession();
			tx=sess.beginTransaction();
			Query q = sess.createQuery(Sql);
			Type[] tipo = q.getReturnTypes();
			entidad = tipo[0].getReturnedClass().toString();
			ObjetosLista = (List<Object>) q.list();
			tx.commit();
			return ObjetosLista;
		} catch (Exception e) {
			
			throw new Exception("Error al consultar los registros");
		} finally {
			if (sess != null) {
				try {
					//sess.disconnect();
					sess.close();
			    }
				catch (HibernateException e)  {
				
				}
			}
			if (resultado.equals("OK") && DatosAuditoria != null) {
				//Auditoria
			}			
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> ConsultarListaValores(String Sql)
			throws Exception {
		Transaction tx=null;
		Session sess = null;
		List<Object[]> ObjetosLista = null;
		String entidad = "";
		String resultado = "OK";
		try {
			sess = currentSession();
			tx=sess.beginTransaction();
			Query q = sess.createQuery(Sql);
			Type[] tipo = q.getReturnTypes();
			entidad = tipo[0].getReturnedClass().toString();
			ObjetosLista = (List<Object[]>) q.list();
			tx.commit();
			return ObjetosLista;
		} catch (Exception e) {
			throw new Exception("Error al consultar los registros");
		} finally {
			if (sess != null) {
				try {
					//sess.disconnect();
					sess.close();
			    }
				catch (HibernateException e)  {
				
				}
			}			
		}
	}

	public Object ConsultarObjeto(String Sql, ArrayList DatosAuditoria)
			throws Exception {
		Session sess = null;
		Transaction tx=null;
		Object objeto = null;
		String entidad = "";
		String resultado = "OK";
		try {sess = currentSession();
			tx=sess.beginTransaction();
			Query q = sess.createQuery(Sql);
			Type[] tipo = q.getReturnTypes();
			entidad = tipo[0].getReturnedClass().toString();
			objeto = (Object) q.uniqueResult();
			tx.commit();
			return objeto;
		} catch (Exception e) {
			throw new Exception("Error al consultar el registro");
		} finally {
			if (sess != null) {
				try {
					//sess.disconnect();
					sess.close();
			    }
				catch (HibernateException e)  {
				
				}
			}
			if (resultado.equals("OK") && DatosAuditoria != null) {
				//Auditoria
			}			
		}
	}
}