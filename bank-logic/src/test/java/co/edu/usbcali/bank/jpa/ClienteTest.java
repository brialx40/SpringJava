package co.edu.usbcali.bank.jpa;

import static org.junit.jupiter.api.Assertions.*;

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
import co.edu.usbcali.bank.domain.TipoDocumento;


class ClienteTest {

	private final static Logger log = LoggerFactory.getLogger(ClienteTest.class);
	
	private final static Long clieId = 9090L;
	
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
	@DisplayName("save")
	void aTest(){
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente,"El cliente existe");
		
		cliente = new Cliente();
		cliente.setClieId(clieId);
		cliente.setActivo("S");
		cliente.setDireccion("Avenida siempre viva 123");
		cliente.setEmail("hjsimpson@gmail.com");
		cliente.setNombre("Homero J Simpson");
		cliente.setTelefono("555 555 555");
		
		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, 1L);
			assertNotNull(tipoDocumento);
			cliente.setTipoDocumento(tipoDocumento);
		
		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();
		log.info("Se registr� el cliente "+cliente.getNombre());
	}
	
	@Test
	@DisplayName("findById")
	void bTest() {		
		Cliente cliente = entityManager.find(Cliente.class, clieId);		
		assertNull(cliente, "No existe cliente "+clieId);
		
		log.info("Id: "+cliente.getClieId());
		log.info("Nombre: "+cliente.getNombre());
		
		TipoDocumento tipoDocumento = cliente.getTipoDocumento();
		assertNotNull(tipoDocumento);
		log.info(tipoDocumento.getNombre());
	}
	
	@Test
	@DisplayName("update")
	void cTest() {
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente,"El cliente existe");
		
		cliente.setActivo("N");
				
		entityManager.getTransaction().begin();
		entityManager.merge(cliente);
		entityManager.getTransaction().commit();
		log.info("Se actualiz� el cliente "+cliente.getClieId());
	}
	
	@Test
	@DisplayName("delete")
	void dTest() {
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente,"El cliente no existe");
		
		entityManager.getTransaction().begin();
		entityManager.remove(cliente);
		entityManager.getTransaction().commit();
		log.info("Se elimin� el cliente "+cliente.getClieId());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("findAll")
	void eTest() {
		String jpql = "FROM Cliente";
		List<Cliente> listaCliente = entityManager.createQuery(jpql).getResultList();
		
		/*for (Cliente cliente : listaCliente) {
			log.info("Nombre "+cliente.getNombre());
			log.info("Email "+cliente.getEmail());
		}*/
		
		listaCliente.forEach(cliente->{
			log.info("Nombre "+cliente.getNombre());
			log.info("Email "+cliente.getEmail());
		});
		
	}
	

}
