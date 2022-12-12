package com.BancoETCR.springboot.app.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.BancoETCR.springboot.app.models.entity.Cliente;

@Component
public class ClienteValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Cliente.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Cliente cliente = (Cliente)target;
		
		//
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "NotEmpty.cliente.nombre");
		
		//
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apellido", "NotEmpty.cliente.nombre");
		
		//
		if (!cliente.getNumeroTelefonico().matches("[0-9]{10}")) {
			errors.rejectValue("numeroTelefonico", "Format.cliente.numerotelefono");
		}
		
		//
		if (!cliente.getNombre().matches("[a-z,A-Z]{1,17}?[ ]?[a-z,A-Z]{1,17}")) {
			errors.rejectValue("nombre", "Format.cliente.nombre");
		}
		
		//
		if (!cliente.getApellido().matches("[a-z,A-Z]{1,17}?[ ]?[a-z,A-Z]{1,17}")) {
			errors.rejectValue("apellido", "Format.cliente.nombre");
		}
		
		//
		if(!cliente.getEmail().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
			errors.rejectValue("email", "Format.cliente.email");
		}
			
	}

}
