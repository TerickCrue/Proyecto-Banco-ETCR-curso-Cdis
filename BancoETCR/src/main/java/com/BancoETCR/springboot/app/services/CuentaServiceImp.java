package com.BancoETCR.springboot.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BancoETCR.springboot.app.models.entity.Cuenta;
import com.BancoETCR.springboot.app.models.entity.Tarjeta;

@Service
public class CuentaServiceImp implements ICuentaService {

	private List<Cuenta> lista;
	
	private List<Tarjeta> listaT;
	
	public CuentaServiceImp() {
		
	}
	
	@Override
	public Cuenta getById(Long id, List<Cuenta> lista) {
		this.lista = lista;
		Cuenta cuentaResult = null;
		
		for(Cuenta cuenta: this.lista){
			if(id == cuenta.getIdCuenta()){
				cuentaResult = cuenta;
				break;
			}
		}
		return cuentaResult;
	}

	@Override
	public Boolean tipoDisponible(Tarjeta tarjeta, List<Tarjeta> listaTarjetas) {
		Boolean result = true;
		this.listaT = listaTarjetas;
		for(Tarjeta tarjetaAux: this.listaT){
			if(tarjeta.getTipoTarjeta().equals(tarjetaAux.getTipoTarjeta())) {
				result = false;
				break;
			}
		}
		return result;
	}
	
	

}
