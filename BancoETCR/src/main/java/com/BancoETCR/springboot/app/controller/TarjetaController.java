package com.BancoETCR.springboot.app.controller;

import java.util.List;
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

import com.BancoETCR.springboot.app.editors.CuentaPropertyEditor;
import com.BancoETCR.springboot.app.errors.DBBancoException;
import com.BancoETCR.springboot.app.models.dao.ICuentaDao;
import com.BancoETCR.springboot.app.models.dao.ITarjetaDao;
import com.BancoETCR.springboot.app.models.entity.Cuenta;
import com.BancoETCR.springboot.app.models.entity.Tarjeta;
import com.BancoETCR.springboot.app.services.ICuentaService;
import com.BancoETCR.springboot.app.services.ITarjetaService;
import com.BancoETCR.springboot.app.validator.TarjetaValidator;

@Controller
@SessionAttributes("tarjeta")
public class TarjetaController {
	
	@Autowired
	private ITarjetaDao tarjetaDao;
	
	@Autowired
	private ICuentaDao cuentaDao;
	
	@Autowired
	private ICuentaService cuentaService;
	
	@Autowired
	private ITarjetaService tarjetaService;
	
	@Autowired
	private TarjetaValidator tarjetaValidator;
	
	@Autowired
	private CuentaPropertyEditor cuentaEditor;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Cuenta.class, "cuenta", cuentaEditor);
		binder.addValidators(tarjetaValidator);
	}
	
	
	@RequestMapping(value = "/tarjetas-lista", method = RequestMethod.GET)
	public String tarjetaLista(Model model) {
		model.addAttribute("titulo", "lista de tarjetas");
		model.addAttribute("tarjetas", tarjetaDao.findAll());

		return "tarjetas-lista";
	}
	
	@RequestMapping(value = "/form-tarjeta")
	public String crear(Map<String, Object> model, Model modelList) {
		Tarjeta tarjeta = new Tarjeta();
		List<Cuenta> listaCuentas = cuentaDao.findAll();
		model.put("tarjeta", tarjeta);
		modelList.addAttribute("listaCuentas", listaCuentas);
		model.put("titulo", "Llenar los datos de una tarjeta");
		return "form-tarjeta";
	}
	
	@RequestMapping(value = "/form-tarjeta/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {

		Tarjeta tarjeta = null;

		if (id != null && id > 0) {
			tarjeta = tarjetaDao.findOne(id);
		} 
		else {
			return "index";
		}
		model.put("tarjeta", tarjeta);
		model.put("titulo", "Editar tarjeta");

		return "form-tarjeta";
	}
	
	@RequestMapping(value = "/eliminar-tarjeta/{id}")
	public String eliminar(@PathVariable(value = "id") Long id) {

		if (id != null && id > 0) {
			tarjetaDao.delete(id);
		}
		return "redirect:/tarjetas-lista";
	}
	
	@RequestMapping(value = "/form-tarjeta", method = RequestMethod.POST)
	public String guardar(@Valid Tarjeta tarjeta, BindingResult result, Model model, SessionStatus status, RedirectAttributes flash) {
		model.addAttribute("titulo", "Formulario de tarjeta");
		List<Tarjeta> listaTarjetasCuenta = tarjetaDao.findByCuentaId(tarjeta.getCuenta().getIdCuenta().toString());
		List<Tarjeta> listaTarjetas = tarjetaDao.findAll();
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Corregir la informacion");
			model.addAttribute("result", result.hasErrors());
			model.addAttribute("mensajeErr", "Error al enviar los datos, porfavor escriba correctamente los campos");
			return "form-tarjeta";
		} 
		else {
			model.addAttribute("result", false);
		}
		
		if(cuentaService.tipoDisponible(tarjeta, listaTarjetasCuenta) && tarjetaService.tarjetaDisponible(tarjeta, listaTarjetas)) {
			try {
				tarjetaDao.save(tarjeta);
				model.addAttribute("mensajeSuccess", "Se envio la informacion correctamente");

			}
			catch(DBBancoException e){
				e.printStackTrace();
				flash.addFlashAttribute("mensaje", e.getMessage());
			}
		}
		else {
			if(!cuentaService.tipoDisponible(tarjeta, listaTarjetasCuenta)) {
				flash.addFlashAttribute("mensajeErr", "La cuenta ya tiene una tarjeta de este tipo");

			}
			if(!tarjetaService.tarjetaDisponible(tarjeta, listaTarjetas)) {
				flash.addFlashAttribute("mensajeErr", "El numero de tarjeta ya existe");
			}

		}
		
		status.setComplete();

		return "redirect:form-tarjeta";
	}
	
	@RequestMapping(value = "/buscar-cuenta-id", method = RequestMethod.POST)
	public String cargarTarjetasCuentaId(Tarjeta tarjeta, RedirectAttributes flash) {

		if (!tarjetaDao.findByCuentaId(tarjeta.getCuenta().getIdCuenta().toString()).isEmpty()) {
			flash.addFlashAttribute("listTarjetasCuentaId", tarjetaDao.findByCuentaId(tarjeta.getCuenta().getIdCuenta().toString()));
			flash.addFlashAttribute("mensajeSucces", "Se encontraron tarjetas");
		} 
		else {
			flash.addFlashAttribute("mensaje", "No se encontraron tarjetas");
		}
		return "redirect:/tarjetas-lista";
	}
	
	@RequestMapping(value = "/buscar-numero-tarjeta", method = RequestMethod.POST)
	public String cargarTarjetasNumTarjeta(Tarjeta tarjeta, RedirectAttributes flash) {

		if (!tarjetaDao.findByNumeroTarjeta(tarjeta.getNumeroTarjeta()).isEmpty()) {
			flash.addFlashAttribute("listNumTarjeta", tarjetaDao.findByNumeroTarjeta(tarjeta.getNumeroTarjeta()));
			flash.addFlashAttribute("mensajeSucces", "Se encontraron tarjetas");
		}
		else {
			flash.addFlashAttribute("mensaje", "No se encontraron tarjetas");
		}
		return "redirect:/tarjetas-lista";
	}
	
}
