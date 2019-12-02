package co.edu.usbcali.bank.jpa;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.bank.domain.Cliente;


class ClienteTest {

	private final static Logger log = LoggerFactory.getLogger(ClienteTest.class); 
	
	@Test
	void test() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bank-logic");		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Cliente cliente = entityManager.find(Cliente.class, 1L);		
		assertNotNull(cliente);
		log.info("Id: "+cliente.getClieId());
		log.info("Nombre: "+cliente.getNombre());
		
		entityManager.close();
		entityManagerFactory.close();
	}

}
