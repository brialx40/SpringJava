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
	
	private final static Logger log=LoggerFactory.getLogger(ClienteTest.class);
	
	EntityManagerFactory entityManagerFactory= null;
	EntityManager entityManager=null;
	
	private final static Long clieId=9090L;

	@BeforeEach
	void beforeEach() {
		log.info("beforeEach");
		entityManagerFactory=Persistence.createEntityManagerFactory("bank-logic");
		entityManager=entityManagerFactory.createEntityManager();
	}
	
	@AfterEach
	void afterEach() {
		log.info("afterEach");
		entityManager.close();
		entityManagerFactory.close();
	}
	
	@Test
	@DisplayName("Save")
	void aTest() {
		Cliente cliente= entityManager.find(Cliente.class, clieId);
		assertNull(cliente, "Ya existe un cliente con id:"+clieId);
		
		cliente = new Cliente();
		cliente.setActivo("S");
		cliente.setClieId(clieId);
		cliente.setDireccion("Avenida falsa 123");
		cliente.setEmail("algo@algo.com");
		cliente.setNombre("Homero J Simpson");
		cliente.setTelefono("000");
		
		TipoDocumento tipoDocumento=entityManager.find(TipoDocumento.class, 1L);
		assertNotNull(tipoDocumento, "El tipo de documento es nulo.");
		cliente.setTipoDocumento(tipoDocumento);
		
		
		entityManager.getTransaction().begin();
		
		entityManager.persist(cliente);
		
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("update")
	void cTest() {
		Cliente cliente= entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "No existe un cliente con id:"+clieId);
		
		cliente.setActivo("N");		
		
		entityManager.getTransaction().begin();
		
		entityManager.merge(cliente);
		
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("delete")
	void dTest() {
		Cliente cliente= entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "No existe un cliente con id:"+clieId);	
		
		entityManager.getTransaction().begin();
		
		entityManager.remove(cliente);
		
		entityManager.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("findAll")
	void eTest() {
		String jpql="FROM Cliente";
		List<Cliente> listaCliente= entityManager.createQuery(jpql).getResultList();
		
		//tradicional
		for (Cliente cliente : listaCliente) {
			log.info("Nombre: "+cliente.getNombre());
		}
		
		//funcional
		listaCliente.forEach(cliente->{
			
				log.info("Nombre: "+cliente.getNombre());
				log.info("Email: "+cliente.getEmail());
			});
	}
	
	
	@Test
	@DisplayName("findById")
	void btest() {		
		Cliente cliente=entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente);
		log.info("id="+cliente.getClieId());
		log.info("Nombre="+cliente.getNombre());
		
		TipoDocumento tipoDocumento =cliente.getTipoDocumento();
		assertNotNull(tipoDocumento);
		log.info(tipoDocumento.getNombre());		
	}

}
