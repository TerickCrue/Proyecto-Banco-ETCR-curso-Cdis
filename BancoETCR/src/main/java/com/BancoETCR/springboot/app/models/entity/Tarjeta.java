package com.BancoETCR.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

//Entidad Tarjeta para la tabla tarjetas en la DB
@Entity
@Table(name = "tarjetas")
public class Tarjeta implements Serializable {

	private static final long serialVersionUID = 2285032466702744310L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTarjeta;
	
	@Column(name = "numero_tarjeta", nullable = false, length = 18)
	@NotEmpty
	private String numeroTarjeta;
	
	
	@Column(name = "icv", nullable = false, length = 3)
	@NotEmpty
	private String icv;
	
	
	@Column
	@NotEmpty
	private String tipoTarjeta;
	
	@JoinColumn(name = "cuenta", referencedColumnName = "idCuenta", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY )
	private Cuenta cuenta;

	
	
	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public String getTipoTarjeta() {
		return tipoTarjeta;
	}

	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public Long getIdTarjeta() {
		return idTarjeta;
	}

	public void setIdTarjeta(Long idTarjeta) {
		this.idTarjeta = idTarjeta;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}
	
	public String getIcv() {
		return icv;
	}

	public void setIcv(String icv) {
		this.icv = icv;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
