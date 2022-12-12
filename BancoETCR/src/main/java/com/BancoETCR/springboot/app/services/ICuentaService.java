package com.BancoETCR.springboot.app.services;

import java.util.List;

import com.BancoETCR.springboot.app.models.entity.Cuenta;
import com.BancoETCR.springboot.app.models.entity.Tarjeta;

public interface ICuentaService {
	
	public Cuenta getById(Long id, List<Cuenta> lista);
	
	public Boolean tipoDisponible(Tarjeta tarjeta, List<Tarjeta> listaTarjetas);
}
