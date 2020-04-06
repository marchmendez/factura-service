package com.robert.app.item.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robert.app.item.entity.Cliente;
import com.robert.app.item.service.ClienteService;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

	private ClienteService clienteService;

	@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	@PostMapping("/crear-cliente")
	public ResponseEntity<?> crearCliente(@RequestBody Cliente cliente) {
		Cliente clienteNew = new Cliente();
		Map<String, Object> response = new HashMap<>();
				
		try {
			clienteNew = clienteService.crearCliente(cliente);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido creado con éxito!");
		response.put("cliente", clienteNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);		
	}
	
	@GetMapping("/buscar-clientes")
	public ResponseEntity<?> buscarClientes() {
		List<Cliente> clientes = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();
				
		try {
			clientes = clienteService.buscarClientes();
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar al buscar los clientes en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("clientes", clientes);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
		
	@GetMapping("/buscar-cliente/{nroDocumento}")
	public ResponseEntity<?> buscarCliente(@PathVariable String nroDocumento) {
		Cliente cliente = new Cliente();
		Map<String, Object> response = new HashMap<>();
		
		try {
			cliente = clienteService.buscarCliente(nroDocumento);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(cliente == null) {
			response.put("mensaje", "El cliente ID: ".concat(nroDocumento.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/borrar-cliente/{nroDocumento}")
	public ResponseEntity<?> borrarCliente(@PathVariable String nroDocumento) {
		Map<String, Object> response = new HashMap<>();
		try {
			Cliente cliente = clienteService.buscarCliente(nroDocumento);
			if (cliente != null) {
				clienteService.borrarCliente(nroDocumento);	
			} else {
				response.put("mensaje", "El cliente ID: ".concat(nroDocumento.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cliente de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);	}
	
	@PutMapping("/modificar-cliente/{nroDocumento}")
	public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable String nroDocumento) {
		Map<String, Object> response = new HashMap<>();
		Cliente clienteActual = clienteService.buscarCliente(nroDocumento);
		Cliente clienteUpdated = new Cliente();
		if (clienteActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el cliente ID: "
					.concat(nroDocumento.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setDireccion(cliente.getDireccion());
			clienteUpdated = clienteService.crearCliente(clienteActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente ha sido actualizado con éxito!");
		response.put("cliente", clienteUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}
