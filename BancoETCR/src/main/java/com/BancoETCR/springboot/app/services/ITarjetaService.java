package com.BancoETCR.springboot.app.services;

import java.util.List;

import com.BancoETCR.springboot.app.models.entity.Tarjeta;

public interface ITarjetaService {
	
	public Boolean tarjetaDisponible(Tarjeta tarjeta, List<Tarjeta> listaTarjetas);
	
}
