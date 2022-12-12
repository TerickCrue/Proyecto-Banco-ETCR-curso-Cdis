package com.BancoETCR.springboot.app.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import com.BancoETCR.springboot.app.models.entity.Cuenta;

@Component
public class CuentaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		//Aseguramos que la clase es asignable
		return Cuenta.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Cuenta cuenta = (Cuenta)target;
		
		//
		if (cuenta.getSaldoActual() <= 99.0) {
			errors.rejectValue("saldoActual", "MinRequerido.cuenta.saldo");
		}
		
		//
		if (cuenta.getCreacion() == null) {
			errors.rejectValue("creacion", "typeMismatch.cuenta.creacion");
		}
		
		
	}

}
