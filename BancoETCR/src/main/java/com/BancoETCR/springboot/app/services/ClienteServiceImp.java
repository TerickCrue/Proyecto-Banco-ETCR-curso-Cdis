package com.BancoETCR.springboot.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BancoETCR.springboot.app.models.entity.Cliente;

@Service
public class ClienteServiceImp implements IClienteService {
	
	private List<Cliente> lista;
	
	public ClienteServiceImp() {
		
	}
	
	@Override
	public Cliente getById(Long id, List<Cliente> lista) {
		this.lista = lista;
		Cliente clienteResult = null;
		
		for(Cliente cliente: this.lista){
			if(id == cliente.getIdCliente()){
				clienteResult = cliente;
				break;
			}
		}
		return clienteResult;
	}

}
