package co.edu.usbcali.bank.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import co.edu.usbcali.bank.domain.Cliente;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository{
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Cliente save(Cliente entity) {
		if(!entityManager.contains(entity) 
				&& findById(entity.getClieId()).isPresent()==false) {
			entityManager.persist(entity);
		}else {
			entityManager.merge(entity);
		}
		return entity;
	}

	@Override
	public Optional<Cliente> findById(Long id) {
		Cliente cliente=entityManager.find(Cliente.class, id);
		Optional<Cliente> clienteOptional= Optional.ofNullable(cliente);
		return clienteOptional;
	}

	@Override
	public void delete(Cliente entity) {
		entityManager.remove(entity);
		
	}

	@Override
	public void deleteById(Long id) {
		findById(id).ifPresent(entity -> delete(entity));
		
	}

	@Override
	public List<Cliente> findAll() {
		return entityManager.createQuery("FROM Cliente", Cliente.class).getResultList();		 
	}

}
