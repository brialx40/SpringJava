package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
class ClienteRespositoryTest {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	TipoDocumentoRepository tipoDocumentoRepository;

	private final static Logger log = LoggerFactory.getLogger(ClienteRespositoryTest.class);

	final static Long ClieID = 9790L;

	@Test
	@DisplayName("save")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void atest() {
		Optional<Cliente> clienteOptional = clienteRepository.findById(ClieID);
		assertFalse(clienteOptional.isPresent(), "primero");
		assertFalse(clienteRepository.findById(ClieID).isPresent(), "segundo");

		Cliente cliente = new Cliente();
		cliente.setActivo("S");
		cliente.setClieId(ClieID);
		cliente.setDireccion("Avenida falsa 123");
		cliente.setEmail("algo@algo.com");
		cliente.setNombre("Homero J Simpson");
		cliente.setTelefono("0000000");

		assertTrue(tipoDocumentoRepository.findById(1L).isPresent());

		TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(1L).get();
		assertNotNull(tipoDocumento, "Es vacio");
		cliente.setTipoDocumento(tipoDocumento);

		clienteRepository.save(cliente);

	}

	@Test
	@DisplayName("findById")
	@Transactional(readOnly = true)
	void btest() {
		assertTrue(clienteRepository.findById(ClieID).isPresent(), "El cliente no existe");

	}

	@Test
	@DisplayName("Update")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void ctest() {
		assertTrue(clienteRepository.findById(ClieID).isPresent(), "El cliente no existe");
		Cliente cliente = clienteRepository.findById(ClieID).get();

		cliente.setActivo("N");
		clienteRepository.save(cliente);
	}

	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dtest() {
		assertTrue(clienteRepository.findById(ClieID).isPresent(), "El cliente no existe");
		Cliente cliente = clienteRepository.findById(ClieID).get();
		clienteRepository.delete(cliente);
	}

}
