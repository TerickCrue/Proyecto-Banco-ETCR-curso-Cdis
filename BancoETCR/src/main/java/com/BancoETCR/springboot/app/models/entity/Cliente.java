package com.BancoETCR.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//Entidad Cliente para la tabla de cuentas de la DB
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {
	
	private static final long serialVersionUID = -173876462500615873L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCliente;
	
	
	@Column(name = "nombre")
	@NotEmpty
	@NotNull
	private String nombre;
	
	
	@Column(name = "apellido")
	@NotEmpty
	@NotNull
	private String apellido;
	
	
	@Column(name = "numero_telefono")
	@NotNull
	@NotEmpty
	private String numeroTelefonico;
	
	
	@Column(name = "email")
	@NotEmpty
	@Email
	private String email;
	
	@OneToOne(mappedBy = "cliente")
	private Cuenta cuenta;
	
	public Long getIdCliente() {
		return idCliente;
	}


	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public String getNumeroTelefonico() {
		return numeroTelefonico;
	}


	public void setNumeroTelefonico(String numeroTelefonico) {
		this.numeroTelefonico = numeroTelefonico;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Cuenta getCuenta() {
		return cuenta;
	}


	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
