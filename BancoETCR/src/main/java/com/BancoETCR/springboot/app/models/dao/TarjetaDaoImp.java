package com.BancoETCR.springboot.app.models.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.exception.DataException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.BancoETCR.springboot.app.errors.DBBancoException;
import com.BancoETCR.springboot.app.models.entity.Tarjeta;

@Repository
public class TarjetaDaoImp implements ITarjetaDao {
	
	@PersistenceContext
	private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Tarjeta> findAll() {
		return em.createQuery("from Tarjeta").getResultList();
	}


	@Override
	@Transactional
	public void save(Tarjeta tarjeta) {
		if(tarjeta.getIdTarjeta() != null && tarjeta.getIdTarjeta() > 0) {
			try {
				em.merge(tarjeta);
			}
			catch(DataException e) {
				throw new DBBancoException();
			}
		} 
		else {
			try {
				em.persist(tarjeta);
			}
			catch(DataException e) {
				throw new DBBancoException();	
			}
		}
	}


	@Override
	@Transactional(readOnly = true)
	public Tarjeta findOne(Long id) {
		return em.find(Tarjeta.class, id);	
	}


	@Override
	@Transactional
	public void delete(Long id) {
		em.remove(findOne(id));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Tarjeta> findByCuentaId(String term) {
		List<Tarjeta> tarjetas = new ArrayList<Tarjeta>();
		for(Tarjeta tarjeta : this.findAll()) {
			if(tarjeta.getCuenta().getIdCuenta().toString().equals(term)) {
				tarjetas.add(tarjeta);
			}
		}
		return tarjetas;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tarjeta> findByNumeroTarjeta(String term) {
		List<Tarjeta> tarjetas = new ArrayList<Tarjeta>();
		for(Tarjeta tarjeta: this.findAll()) {
			if(tarjeta.getNumeroTarjeta().equals(term)) {
				tarjetas.add(tarjeta);
			}
		}
		
		return tarjetas;
	}

}
