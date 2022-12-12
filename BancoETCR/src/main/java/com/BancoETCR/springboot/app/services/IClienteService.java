package com.BancoETCR.springboot.app.services;

import java.util.List;

import com.BancoETCR.springboot.app.models.entity.Cliente;

public interface IClienteService {
	
	public Cliente getById(Long id, List<Cliente> lista);
	
	
}
