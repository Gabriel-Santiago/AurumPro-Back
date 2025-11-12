package com.AurumPro.repositories.cliente;

import com.AurumPro.entities.cliente.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long>{

}
