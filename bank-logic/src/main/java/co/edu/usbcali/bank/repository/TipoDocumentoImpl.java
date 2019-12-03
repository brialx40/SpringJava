package co.edu.usbcali.bank.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import co.edu.usbcali.bank.domain.TipoDocumento;

@Repository
public class TipoDocumentoImpl implements TipoDocumentoRepository{
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public TipoDocumento save(TipoDocumento entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<TipoDocumento> findById(Long id) {
		TipoDocumento tipoDocumento=entityManager.find(TipoDocumento.class, id);
				Optional<TipoDocumento> tipoDocumentoOptional=
						Optional.ofNullable(tipoDocumento);
				return tipoDocumentoOptional;
	}

	@Override
	public void delete(TipoDocumento entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TipoDocumento> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
