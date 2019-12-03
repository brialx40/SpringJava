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

import co.edu.usbcali.bank.domain.Cliente;
import co.edu.usbcali.bank.domain.TipoUsuario;
import co.edu.usbcali.bank.domain.Usuario;

class UsuarioTest {
	
	EntityManagerFactory entityManagerFactory = null ;
	EntityManager entityManager = null;
	
	public final static Logger log = LoggerFactory.getLogger(UsuarioTest.class);
	
	@BeforeEach
	void beforeEach() {
		log.info("Before");
		entityManagerFactory = Persistence.createEntityManagerFactory("bank-logic");
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	@AfterEach
	void afterEach() {
		log.info("after");
		entityManager.close();
		entityManagerFactory.close();
	}

	@Test
	@DisplayName("insertUser")
	void atest() {
		Usuario usuario = entityManager.find(Usuario.class, "wcarrascal");
		assertNull(usuario, "Existe el tipo de usuario");
		
		usuario= new Usuario();
		
		usuario.setUsuUsuario("wcarrascal");
		usuario.setActivo("Y");
		usuario.setClave("pass123");
		usuario.setIdentificacion(new BigDecimal(1090));
		usuario.setNombre("Pepito");
		
		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, 1L);
		assertNotNull(tipoUsuario, "No existe el tipo de usuario");
		
		usuario.setTipoUsuario(tipoUsuario);
		
		entityManager.getTransaction().begin();
		entityManager.persist(usuario);
		entityManager.getTransaction().commit();
		
	}
	
	@Test
	@DisplayName("FindId")
	void bTest() {
		Usuario usuario = entityManager.find(Usuario.class, "zwhiteson6y");
		assertNotNull(usuario, "No existe el tipo de usuario");
		
	}
	
	@Test
	@DisplayName("updateUser")
	void ctest() {
		Usuario usuario = entityManager.find(Usuario.class, "wcarrascal");
		assertNotNull(usuario, "No existe el tipo de usuario");
		
		usuario.setActivo("N");
		
		entityManager.getTransaction().begin();
		entityManager.merge(usuario);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("deleteUser")
	void dtest() {
		Usuario usuario = entityManager.find(Usuario.class, "wcarrascal");
		assertNotNull(usuario, "No existe el tipo de usuario");
		
		entityManager.getTransaction().begin();
		entityManager.remove(usuario);
		entityManager.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("FindAll")
	void eTest() {
		String jpql = "FROM Usuario";
		List<Usuario> listaUsuario = entityManager.createQuery(jpql).getResultList();
		
		listaUsuario.forEach(usuario -> log.info("Nombre:"+usuario.getNombre()));
		
	}

}
