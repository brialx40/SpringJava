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
@ContextConfiguration("/applicationContext.xml")
class ClienteSpringTest {
	
	@PersistenceContext
	EntityManager entityManager;
	
	private final static Logger log=LoggerFactory.getLogger(ClienteSpringTest.class);
	
	final static Long ClieID=9790L;

	@Test
	@DisplayName("save")
	@Transactional(readOnly= false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void atest() {
		Cliente cliente= entityManager.find(Cliente.class, ClieID);
		assertNull(cliente, "Ya existe un cliente con id:"+ClieID);
		
		cliente = new Cliente();
		cliente.setActivo("S");
		cliente.setClieId(ClieID);
		cliente.setDireccion("Avenida falsa 123");
		cliente.setEmail("algo@algo.com");
		cliente.setNombre("Homero J Simpson");
		cliente.setTelefono("000");
		
		TipoDocumento tipoDocumento=entityManager.find(TipoDocumento.class, 1L);
		assertNotNull(tipoDocumento, "El tipo de documento es nulo.");
		cliente.setTipoDocumento(tipoDocumento);
		
		entityManager.persist(cliente);

	}
	
	@Test
	@DisplayName("findById")
	@Transactional(readOnly = true)
	void btest() {		
		Cliente cliente=entityManager.find(Cliente.class, ClieID);
		assertNotNull(cliente);
		log.info("id="+cliente.getClieId());
		log.info("Nombre="+cliente.getNombre());
		
		TipoDocumento tipoDocumento =cliente.getTipoDocumento();
		assertNotNull(tipoDocumento);
		log.info(tipoDocumento.getNombre());		
	}
	
	@Test
	@DisplayName("update")
	@Transactional(readOnly= false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void cTest() {
		Cliente cliente= entityManager.find(Cliente.class, ClieID);
		assertNotNull(cliente, "No existe un cliente con id:"+ClieID);
		
		cliente.setActivo("N");		
		
		entityManager.merge(cliente);

	}
	
	@Test
	@DisplayName("delete")
	@Transactional(readOnly= false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dTest() {
		Cliente cliente= entityManager.find(Cliente.class, ClieID);
		assertNotNull(cliente, "No existe un cliente con id:"+ClieID);
		
		entityManager.remove(cliente);

	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = true)
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

}
