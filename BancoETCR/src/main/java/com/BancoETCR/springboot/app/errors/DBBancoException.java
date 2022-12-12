package com.BancoETCR.springboot.app.errors;

public class DBBancoException extends RuntimeException {

	
	private static final long serialVersionUID = -1167787496416124490L;
	
	public DBBancoException(){
		super("Contacte con soporte, hubo un error en la base de datos");
	}

}
