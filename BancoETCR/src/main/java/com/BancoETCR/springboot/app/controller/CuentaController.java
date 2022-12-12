package com.BancoETCR.springboot.app.controller;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

import com.BancoETCR.springboot.app.editors.ClientePropertyEditor;
import com.BancoETCR.springboot.app.errors.DBBancoException;
import com.BancoETCR.springboot.app.models.dao.IClienteDao;
import com.BancoETCR.springboot.app.models.dao.ICuentaDao;
import com.BancoETCR.springboot.app.models.dao.ITarjetaDao;
import com.BancoETCR.springboot.app.models.entity.Cliente;
import com.BancoETCR.springboot.app.models.entity.Cuenta;
import com.BancoETCR.springboot.app.validator.CuentaValidator;

@Controller
@SessionAttributes("cuenta")
public class CuentaController {
	
	@Autowired
	private ICuentaDao cuentaDao;
	
	@Autowired
	private ITarjetaDao tarjetaDao;
	
	@Autowired
	private IClienteDao clienteDao;
	
	@Autowired
	private ClientePropertyEditor clienteEditor;
	
	@Autowired
	private CuentaValidator cuentaValidator;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Cliente.class, "cliente", clienteEditor);
		binder.addValidators(cuentaValidator);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "creacion", new CustomDateEditor(dateFormat, false));
	}
	
	
	@RequestMapping(value = "/cuentas-lista", method = RequestMethod.GET)
	public String cuentaLista(Model model) {
		model.addAttribute("titulo", "lista de cuentas");
		model.addAttribute("cuentas", cuentaDao.findAll());

		return "cuentas-lista";
	}
	
	@RequestMapping(value = "/form-cuenta")
	public String crear(Map<String, Object> model, Model modelList) {
		Cuenta cuenta = new Cuenta();
		List<Cliente> listaClientes = clienteDao.findAll();
		model.put("cuenta", cuenta);
		modelList.addAttribute("listaClientes", listaClientes);
		model.put("titulo", "Nueva cuenta, llene los datos");
		return "form-cuenta"; 
	}
	
	@RequestMapping(value = "/form-cuenta/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		Cuenta cuenta = null;
		if (id != null && id > 0) {
			cuenta = cuentaDao.findOne(id);
		} else {
			return "redirect:/cuentas-lista";
		}
		model.put("cuenta", cuenta);
		model.put("titulo", "Edite la cuenta");
		
		return "form-cuenta";

	}
	
	@RequestMapping(value = "/form-cuenta", method = RequestMethod.POST)
	public String guardar(@Valid Cuenta cuenta, BindingResult result, Model model, SessionStatus status, RedirectAttributes flash){
		model.addAttribute("titulo", "Formulario de cuenta");
	
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Corregir la informacion");
			model.addAttribute("result", result.hasErrors());
			model.addAttribute("mensajeErr", "Error al enviar los datos, porfavor escriba correctamente los campos");
			return "form-cuenta";
		} 
		else {
			model.addAttribute("result", false);
		}
		
		if (cuentaDao.findByClienteId(cuenta.getCliente().getIdCliente().toString()).isEmpty()) {
			System.out.println("no hay cuenta asociada"); 
			try {
				cuentaDao.save(cuenta);
				model.addAttribute("mensajeSuccess", "Se envio la informacion correctamente");
			}
			catch(DBBancoException e) {
				e.printStackTrace();
				flash.addFlashAttribute("mensajeErr", e.getMessage());
			}
		} 
		else {
			flash.addFlashAttribute("mensajeErr", "El cliente ya tiene una cuenta, no puede crear otra ");
		}
		
		status.setComplete(); 					

		return "redirect:form-cuenta";
	}
	
	@RequestMapping(value = "/eliminar-cuenta/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id != null && id > 0) {
			if (tarjetaDao.findByCuentaId(id.toString()).isEmpty()) {
				System.out.println("La lista esta vacia");
				cuentaDao.delete(id);
			} 
			else {
				flash.addFlashAttribute("mensajeErr", "La cuenta tiene tarjetas pendientes por eliminar");
			}
		}
		return "redirect:/cuentas-lista";
	}
	
	
	@RequestMapping(value = "/buscar-cuenta-Id", method = RequestMethod.POST)
	public String cargarCuentaId(Cuenta cuenta, RedirectAttributes flash) {
		
		if(cuentaDao.findOne(cuenta.getIdCuenta()) != null) {
			flash.addFlashAttribute("cuentaPorId", cuentaDao.findOne(cuenta.getIdCuenta()));
			flash.addFlashAttribute("mensajeSuccess", "Se encontró una cuenta");
		}
		else {
			flash.addFlashAttribute("mensaje", "No se encontró ninguna cuenta");
		}
		return "redirect:/cuentas-lista";
	}
	
}
	