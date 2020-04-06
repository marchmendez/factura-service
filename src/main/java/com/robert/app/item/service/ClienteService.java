package com.robert.app.item.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robert.app.item.entity.Cliente;
import com.robert.app.item.repository.ClienteRepository;

@Service
public class ClienteService {

	private ClienteRepository clienteRepository;
	
	@Autowired
	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public Cliente crearCliente(Cliente cliente) {
		Cliente response = new Cliente();
		response = clienteRepository.save(cliente);
		return response;
	} 
	
	public Cliente modificarCliente(Cliente cliente) {
		Cliente response = new Cliente();
		response = clienteRepository.save(cliente);
		return response;
	} 
	
	public List<Cliente> buscarClientes() {
		List<Cliente> response = new ArrayList<>();
		response = clienteRepository.findAll();
		return response;
	}
	
	public Cliente buscarCliente(String nroDocumento) {
		Cliente response = new Cliente();
		response = clienteRepository.findById(nroDocumento).orElse(null);
		return response;
	}
	
	public void borrarCliente(String nroDocumento) {
		clienteRepository.deleteById(nroDocumento);
	}
	
}
