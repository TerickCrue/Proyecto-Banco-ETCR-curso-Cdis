package com.BancoETCR.springboot.app.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.BancoETCR.springboot.app.errors.DBBancoException;
import com.BancoETCR.springboot.app.models.dao.IClienteDao;
import com.BancoETCR.springboot.app.models.dao.ICuentaDao;
import com.BancoETCR.springboot.app.models.entity.Cliente;
import com.BancoETCR.springboot.app.validator.ClienteValidator;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteDao clienteDao;
	
	@Autowired
	private ICuentaDao cuentaDao;
	
	@Autowired
	private ClienteValidator clienteValidator;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		
		binder.addValidators(clienteValidator);
	}
	
	
	@RequestMapping(value = "/clientes-lista", method = RequestMethod.GET)
	public String clienteLista(Model model) {
		model.addAttribute("titulo", "lista de clientes");
		model.addAttribute("clientes", clienteDao.findAll());

		return "clientes-lista";
	}
	
	@RequestMapping(value = "/form-cliente")
	public String crear(Map<String, Object> model){
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Nuevo cliente, llene los datos ");
		return "form-cliente";
	}
	
	@RequestMapping(value = "/form-cliente/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteDao.findOne(id);
		} else {
			return "redirect:/clientes-lista";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Edite el cliente");
		return "form-cliente";

	}
	
	@RequestMapping(value = "/form-cliente", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status, RedirectAttributes flash){
		model.addAttribute("titulo", "Formulario de cliente");

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Corregir la informacion");
			model.addAttribute("result", result.hasErrors());
			model.addAttribute("mensajeErr", "Error al enviar los datos, porfavor escriba correctamente los campos");
			return "form-cliente";
		} 
		else {
			model.addAttribute("result", false);
		}
				
		try {
			clienteDao.save(cliente);
			model.addAttribute("mensajeSuccess", "Se envio la informacion correctamente");
		}
		catch(DBBancoException e){
			e.printStackTrace();
			flash.addFlashAttribute("mensaje", e.getMessage());
		}
	
		status.setComplete(); 					

		return "redirect:form-cliente";
	}
	
	@RequestMapping(value = "/eliminar-cliente/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id != null && id > 0) {

			if (cuentaDao.findByClienteId(id.toString()).isEmpty()) {
				System.out.println("no hay cuenta asociada"); 
				clienteDao.delete(id);
			} 
			else {
				flash.addFlashAttribute("mensajeErr", "El cliente aun tiene cuenta, debe eliminarla primero ");
			}
		}
		return "redirect:/clientes-lista";
	}
	
	@RequestMapping(value = "/buscar-Id", method = RequestMethod.POST)
	public String cargarClientesId(Cliente cliente, RedirectAttributes flash) {
		
		if(clienteDao.findOne(cliente.getIdCliente()) != null) {
			flash.addFlashAttribute("clientePorId", clienteDao.findOne(cliente.getIdCliente()));
			flash.addFlashAttribute("mensajeSuccess", "Se encontró un cliente");
		}
		else {
			flash.addFlashAttribute("mensaje", "No se encontró ningún cliente");
		}
		return "redirect:/clientes-lista";
	}
	
	
	
	@RequestMapping(value = "/buscar-numero-tel", method = RequestMethod.POST )
	public String cargarClientesNumeroTelefono(Cliente cliente, RedirectAttributes flash) {
		if (!clienteDao.findByNumeroTelefono(cliente.getNumeroTelefonico()).isEmpty()) {
			flash.addFlashAttribute("listClientesNumeroT", clienteDao.findByNumeroTelefono(cliente.getNumeroTelefonico()));
			flash.addFlashAttribute("mensajeSuccess", "Se encontraron clientes");
		} 
		else {
			flash.addFlashAttribute("mensaje", "No se encontraron clientes");
		}
	
		return "redirect:/clientes-lista";
	}
	
	
}
