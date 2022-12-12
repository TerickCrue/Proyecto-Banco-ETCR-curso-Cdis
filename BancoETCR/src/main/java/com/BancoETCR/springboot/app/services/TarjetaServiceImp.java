package com.BancoETCR.springboot.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BancoETCR.springboot.app.models.entity.Tarjeta;

@Service
public class TarjetaServiceImp implements ITarjetaService {

	private List<Tarjeta> listaT;
	
	@Override
	public Boolean tarjetaDisponible(Tarjeta tarjeta, List<Tarjeta> listaTarjetas) {
		this.listaT = listaTarjetas;
		Boolean result = true;
		
		for(Tarjeta tarjetaAux: this.listaT) {
			if(tarjeta.getNumeroTarjeta().equals(tarjetaAux.getNumeroTarjeta())) {
				result = false;
				break;
			}
		}
		return result;
	}

}
