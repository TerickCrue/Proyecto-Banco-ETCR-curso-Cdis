package com.BancoETCR.springboot.app.models.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.exception.DataException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.BancoETCR.springboot.app.errors.DBBancoException;
import com.BancoETCR.springboot.app.models.entity.Cliente;

@Repository
public class ClienteDaoImp implements IClienteDao {

	@PersistenceContext
	private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Cliente").getResultList();
	}


	@Override
	@Transactional
	public void save(Cliente cliente) {
		if(cliente.getIdCliente() != null && cliente.getIdCliente() > 0) {
			try {
				em.merge(cliente);
			}
			catch(DataException e) {
				throw new DBBancoException();
			}
		} 
		else {
			try {
				em.persist(cliente);
			}
			catch(DataException e) {
				throw new DBBancoException();
			}
		}
	}


	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return em.find(Cliente.class, id);

	}


	@Override
	@Transactional
	public void delete(Long id) {
		em.remove(findOne(id));
		
	}


	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findByNumeroTelefono(String term) {
		List<Cliente> clientes = new ArrayList<Cliente>();
		for(Cliente cliente : this.findAll()) {
			if(cliente.getNumeroTelefonico().equals(term)) {
				clientes.add(cliente);
			}
		}
		return clientes;
	}

}
