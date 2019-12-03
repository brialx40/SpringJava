package co.edu.usbcali.bank.jpa;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Cliente;
import co.edu.usbcali.bank.dto.ResultadoAritmeticaDTO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
class JpqlTest {
	
	@PersistenceContext
	EntityManager entityManager;
	
	private final static Logger log=LoggerFactory.getLogger(JpqlTest.class);

	@Test
	@Transactional(readOnly = true)
	void selectWhere() {
		String jpql = "SELECT cli FROM Cliente cli WHERE cli.tipoDocumento.tdocId=2";
		List<Cliente> listaCliente =entityManager.createQuery(jpql).getResultList();
		assertFalse(listaCliente.isEmpty());
		
		listaCliente.forEach(cliente -> {
			log.info("Nombre: "+cliente.getNombre());
			log.info("Tipo de documento: "+cliente.getTipoDocumento().getNombre());
		});
		
		//listaCliente.stream().limit(10);
		//listaCliente.stream().map(cliente->cliente.getNombre()).forEach(System.out::println);
		//List<String> listaNombre=listaCliente.stream().map(cliente->cliente.getNombre()).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional(readOnly = true)
	void selectWhereParameter() {
		String jpql = "SELECT cli FROM Cliente cli WHERE "
				+ "cli.tipoDocumento.tdocId=:idTipoDoc";
		List<Cliente> listaCliente =entityManager.createQuery(jpql).
				setParameter("idTipoDoc", 2L).getResultList();
		assertFalse(listaCliente.isEmpty());
		
		listaCliente.forEach(cliente -> {
			log.info("Nombre: "+cliente.getNombre());
			log.info("Tipo de documento: "+cliente.getTipoDocumento().getNombre());
		});
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional(readOnly = true)
	void selectWhereParameterAnd() {
		String jpql = "SELECT cli FROM Cliente cli WHERE "
				+ "cli.tipoDocumento.tdocId=:idTipoDoc AND "
				+ "cli.nombre lIKE :nombre";
		List<Cliente> listaCliente =entityManager.createQuery(jpql).
				setParameter("nombre", "%J%").
				setParameter("idTipoDoc", 2L).getResultList();
		assertFalse(listaCliente.isEmpty());
		
		listaCliente.forEach(cliente -> {
			log.info("Nombre: "+cliente.getNombre());
			log.info("Tipo de documento: "+cliente.getTipoDocumento().getNombre());
		});
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional(readOnly = true)
	void selectClienteConMax4Cuenta() {
		String jpql = "SELECT cli FROM Cliente cli WHERE size(cli.cuentas)>4";
		List<Cliente> listaCliente =entityManager.createQuery(jpql).getResultList();
		assertFalse(listaCliente.isEmpty());
		
		listaCliente.forEach(cliente -> {
			log.info("4Nombre: "+cliente.getNombre());
			log.info("Tipo de documento: "+cliente.getTipoDocumento().getNombre());
		});
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional(readOnly = true)
	void selectAritmeticas() {
		String jpql = "SELECT "
				+ "MAX(cue.saldo), "
				+ "MIN(cue.saldo), "
				+ "AVG(cue.saldo), "
				+ "COUNT(cue.saldo)"
				+ "FROM Cuenta cue";
		
		Object[] object=(Object[])entityManager.createQuery(jpql).getSingleResult();
		log.info("MAX: "+object[0]);
		log.info("MIN: "+object[1]);
		log.info("AVG: "+object[2]);
		log.info("COUNT: "+object[3]);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional(readOnly = true)
	void selectAritmeticasDTO() {
		String jpql = "SELECT "
				+ "new co.edu.usbcali.bank.dto.ResultadoAritmeticaDTO("
				+ "MAX(cue.saldo), "
				+ "MIN(cue.saldo), "
				+ "AVG(cue.saldo), "
				+ "COUNT(cue.saldo) "
				+ ")"
				+ "FROM Cuenta cue";
		
		ResultadoAritmeticaDTO resultadoAritmeticaDTO=
				(ResultadoAritmeticaDTO)entityManager.createQuery(jpql).
				getSingleResult();
		log.info("MAX: "+resultadoAritmeticaDTO.getMax());
		log.info("MIN: "+resultadoAritmeticaDTO.getMin());
		log.info("AVG: "+resultadoAritmeticaDTO.getAvg());
		log.info("COUNT: "+resultadoAritmeticaDTO.getCount());
		
	}
}
