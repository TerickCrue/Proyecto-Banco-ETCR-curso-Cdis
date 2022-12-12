package com.BancoETCR.springboot.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.BancoETCR.springboot.app.models.dao.ICuentaDao;
import com.BancoETCR.springboot.app.services.ICuentaService;

@Component
public class CuentaPropertyEditor extends PropertyEditorSupport {

	@Autowired
	private ICuentaService cuentaService;
	
	@Autowired
	private ICuentaDao cuentaDao;
	
	@Override
	public void setAsText(String idString) throws IllegalArgumentException {
		
		try {
			Long id = Long.parseLong(idString);
			this.setValue(cuentaService.getById(id, cuentaDao.findAll()));
		}
		catch(Exception e) {
			System.out.println("Hubo un error al asignar el objeto cuenta a la tarjeta");
		}
	}
	
}
