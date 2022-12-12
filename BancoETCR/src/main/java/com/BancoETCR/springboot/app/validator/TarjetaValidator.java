package com.BancoETCR.springboot.app.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.BancoETCR.springboot.app.models.entity.Tarjeta;

@Component
public class TarjetaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Tarjeta.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Tarjeta tarjeta = (Tarjeta)target;
		
		
		if (!tarjeta.getNumeroTarjeta().matches("[0-9]{18}")) {
			errors.rejectValue("numeroTarjeta", "Format.tarjeta.numerotarjeta");
		}
		
		if(!tarjeta.getIcv().matches("[0-9]{3}")) {
			errors.rejectValue("icv", "Format.tarjeta.icv");
		}
	}

}
