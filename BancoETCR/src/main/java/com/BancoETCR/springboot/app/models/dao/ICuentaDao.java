package com.BancoETCR.springboot.app.models.dao;

import java.util.List;
import com.BancoETCR.springboot.app.models.entity.Cuenta;

public interface ICuentaDao {
	
	public List<Cuenta> findAll();
	
	public void save(Cuenta cuenta);
	
	public Cuenta findOne(Long id);
	
	public List<Cuenta> findByClienteId(String term);
	
	public void delete(Long id);

}
