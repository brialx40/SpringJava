package co.edu.usbcali.bank.jpa;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.bank.domain.TipoUsuario;
import co.edu.usbcali.bank.domain.Usuario;

class UsurioTest {

private final static Logger log = LoggerFactory.getLogger(ClienteTest.class);
	
	private final static String usuUsuario = "elbartosimpson";
	
	EntityManagerFactory entityManagerFactory;
	EntityManager entityManager;
	
	@BeforeEach
	void beforeEach() {
		entityManagerFactory = Persistence.createEntityManagerFactory("bank-logic");		
		entityManager = entityManagerFactory.createEntityManager();
		log.info("@BeforeEach");
	}
	
	@AfterEach
	void afterEach() {
		entityManager.close();
		entityManagerFactory.close();
		log.info("@AfterEach");
	}
	
	@Test
	@DisplayName("saveUser")
	void aTest() {
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNull(usuario,"No Existe usuario "+usuUsuario);
		
		usuario = new Usuario();
		usuario.setUsuUsuario(usuUsuario);
		usuario.setClave("Open2017*");
		usuario.setIdentificacion(new BigDecimal(9204));
		usuario.setNombre("Bartolomeo Simpson");
		usuario.setActivo("Y");
		
		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, 1L);
		assertNotNull(tipoUsuario);
		
		usuario.setTipoUsuario(tipoUsuario);
		
		entityManager.getTransaction().begin();
		entityManager.persist(usuario);
		entityManager.getTransaction().commit();
		log.info("Se creó usuario "+usuUsuario);
	}
	
	@Test
	@DisplayName("findUserById")
	void bTest() {
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(usuario,"No Existe usuario "+usuUsuario);
		
		log.info("user: "+usuario.getUsuUsuario());
		log.info("Name: "+usuario.getNombre());
		
		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, usuario.getTipoUsuario().getTiusId());
		assertNotNull(tipoUsuario);
		
		log.info(tipoUsuario.getNombre());
	}
	
	@Test
	@DisplayName("updateUser")
	void cTest() {
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(usuario,"No Existe usuario "+usuUsuario);
		
		usuario.setActivo("N");
		
		entityManager.getTransaction().begin();
		entityManager.merge(usuario);
		entityManager.getTransaction().commit();
		
		log.info("Se actualizó usuario "+usuUsuario);
		log.info("Activo "+usuario.getActivo());
	}
	
	@Test
	@DisplayName("deleteUser")
	void dTest() {
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(usuario,"No Existe usuario "+usuUsuario);
		
		entityManager.getTransaction().begin();
		entityManager.remove(usuario);
		entityManager.getTransaction().commit();
		
		log.info("Se eliminó usuario correctamente");
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("findUsersByUserType")
	void eTest() {
		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, 2L);
		assertNotNull(tipoUsuario);
		
		String jpql = "FROM Usuario WHERE tius_id = "+tipoUsuario.getTiusId();
		List<Usuario> listaUsuarios = entityManager.createQuery(jpql).getResultList();
		assertNotNull(listaUsuarios);
		
		listaUsuarios.forEach(usuario->log.info("user "+usuario.getUsuUsuario()));
	}
}
