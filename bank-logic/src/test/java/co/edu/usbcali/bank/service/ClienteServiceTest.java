package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Cliente;
import co.edu.usbcali.bank.domain.TipoDocumento;
import co.edu.usbcali.bank.repository.TipoDocumentoRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
class ClienteServiceTest {

	@Autowired
	ClienteService clienteService;
	
	@Autowired
	TipoDocumentoRepository tipoDocumentoRepository;
	
	private final static Logger log=LoggerFactory.getLogger(ClienteServiceTest.class);
	
	final static Long ClieID=9790L;

	@Test
	@DisplayName("save")
	@Rollback(false)
	void atest() {
		Optional<Cliente> clienteOptional= clienteService.findById(ClieID);
		assertFalse(clienteOptional.isPresent());
		assertFalse(clienteService.findById(ClieID).isPresent());
		
		Cliente cliente = new Cliente();
		cliente.setActivo("S");
		cliente.setClieId(ClieID);
		cliente.setDireccion("Avenida falsa 123");
		cliente.setEmail("algo@algo.com");
		cliente.setNombre("Homero J Simpson");
		cliente.setTelefono("0000000");
		
		assertTrue(tipoDocumentoRepository.findById(1L).isPresent());
		
		TipoDocumento tipoDocumento=tipoDocumentoRepository.findById(1L).get();
		assertNotNull(tipoDocumento, "Es vacio");
		cliente.setTipoDocumento(tipoDocumento);
		
		try {
			clienteService.save(cliente);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}

	}
	
	@Test
	@DisplayName("findById")
	void btest() {
		assertTrue(clienteService.findById(ClieID).isPresent(), "El cliente no existe");

	}
	
	@Test
	@DisplayName("Update")
	@Rollback(false)
	void ctest() {
		assertTrue(clienteService.findById(ClieID).isPresent(), "El cliente no existe");
		Cliente cliente = clienteService.findById(ClieID).get();

		cliente.setActivo("N");
		try {
			clienteService.update(cliente);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}		
	}
	
	@Test
	@DisplayName("delete")
	@Rollback(false)
	void dtest() {
		assertTrue(clienteService.findById(ClieID).isPresent(), "El cliente no existe");
		Cliente cliente = clienteService.findById(ClieID).get();
		try {
			clienteService.delete(cliente);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

}
