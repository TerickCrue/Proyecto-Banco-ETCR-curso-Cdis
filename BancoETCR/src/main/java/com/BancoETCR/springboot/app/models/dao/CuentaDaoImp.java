package com.BancoETCR.springboot.app.models.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.exception.DataException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.BancoETCR.springboot.app.errors.DBBancoException;
import com.BancoETCR.springboot.app.models.entity.Cuenta;

@Repository
public class CuentaDaoImp implements ICuentaDao {
	
	@PersistenceContext
	private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Cuenta> findAll() {
		// .createQuery hace y debe hacer referencia a la entidad misma (Cuenta), no a la BD
		return em.createQuery("from Cuenta").getResultList();
	}


	@Override
	@Transactional
	public void save(Cuenta cuenta) {
		if(cuenta.getIdCuenta() != null && cuenta.getIdCuenta() > 0) {
			try {
				em.merge(cuenta);
			}
			catch(DataException e) {
				throw new DBBancoException();
			}
		} 
		else {
			try {
				em.persist(cuenta);
			}
			catch(DataException e) {
				throw new DBBancoException();
			}
		}
	}


	@Override
	@Transactional(readOnly = true)
	public Cuenta findOne(Long id) {
		return em.find(Cuenta.class, id);
	}


	@Override
	@Transactional
	public void delete(Long id) {
		em.remove(findOne(id));
	}


	@Override
	@Transactional(readOnly = true)
	public List<Cuenta> findByClienteId(String term) {
		List<Cuenta> cuentas = new ArrayList<Cuenta>();
		for(Cuenta cuenta : this.findAll()) {
			if(cuenta.getCliente().getIdCliente().toString().equals(term)) {
				cuentas.add(cuenta);
			}
		}
		return cuentas;
	}
	

	

}
