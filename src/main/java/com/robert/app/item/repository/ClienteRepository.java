package com.robert.app.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robert.app.item.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String>{

	public Cliente findByNroDocumento(String nroDocumento);
}
