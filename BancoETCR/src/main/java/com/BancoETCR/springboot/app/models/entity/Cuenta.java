package com.BancoETCR.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

//Entidad Cuenta para la tabla cuentas en la DB
@Entity
@Table(name = "cuentas")
public class Cuenta implements Serializable {

	private static final long serialVersionUID = 5332835253799210253L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCuenta;
	
	@Column(name = "saldo_actual")
	@NotNull
	private double saldoActual;
	
	@Column(name = "dia_creacion")
	private Date creacion;

	
	@JoinColumn(name = "cliente", referencedColumnName = "idCliente", nullable = false)
	@OneToOne(optional = false, fetch = FetchType.EAGER)
	private Cliente cliente; 
	
	
	public Cliente getCliente() {
		return cliente;
	}
	

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public Long getIdCuenta() {
		return idCuenta;
	}


	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}


	public double getSaldoActual() {
		return saldoActual;
	}


	public void setSaldoActual(double saldoActual) {
		this.saldoActual = saldoActual;
	}


	public Date getCreacion() {
		return creacion;
	}


	public void setCreacion(Date creacion) {
		this.creacion = creacion;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}

