package com.meuupa.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.meuupa.app.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{

 @Query("SELECT p FROM Paciente p WHERE p.status = 'AGUARDANDO' ORDER BY p.data ASC")
 List<Paciente> findFilaOrdenada();
	
}
