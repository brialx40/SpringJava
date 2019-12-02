package co.edu.usbcali.bank.jpa;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Cliente;
import co.edu.usbcali.bank.domain.TipoDocumento;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationcontext.xml")
class ClienteSpringTest {
	
	@PersistenceContext
	EntityManager entityManager;
	
	final static Logger log = LoggerFactory.getLogger(ClienteSpringTest.class);
	
	final static Long clieId = 9790L;
	
	@Test
	@DisplayName("save")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void aTest() {
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNull(cliente,"El cliente no existe");
		
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
		
		entityManager.persist(cliente);
		
		log.info("Se registró el cliente "+cliente.getNombre());
	}
	
	@Test
	@DisplayName("findById")
	@Transactional(readOnly = true)
	void bTest() {		
		Cliente cliente = entityManager.find(Cliente.class, clieId);		
		assertNotNull(cliente, "No existe cliente "+clieId);
		
		log.info("Id: "+cliente.getClieId());
		log.info("Nombre: "+cliente.getNombre());
		
		TipoDocumento tipoDocumento = cliente.getTipoDocumento();
		assertNotNull(tipoDocumento);
		log.info(tipoDocumento.getNombre());
	}
	
	@Test
	@DisplayName("update")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void cTest() {
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente,"El cliente existe");
		
		cliente.setActivo("N");
				
		entityManager.merge(cliente);
		log.info("Se actualizó el cliente "+cliente.getClieId());
	}
	
	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dTest() {
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente,"El cliente no existe");
		
		entityManager.remove(cliente);
		log.info("Se eliminó el cliente "+cliente.getClieId());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = true)
	void eTest() {
		String jpql = "FROM Cliente";
		List<Cliente> listaCliente = entityManager.createQuery(jpql).getResultList();
		
		listaCliente.forEach(cliente->{
			log.info("Nombre "+cliente.getNombre());
			log.info("Email "+cliente.getEmail());
		});
		
	}

}
