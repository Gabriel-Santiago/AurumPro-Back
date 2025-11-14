package com.AurumPro.repositories.cliente;

import com.AurumPro.entities.cliente.PessoaFisica;
import com.AurumPro.entities.empresa.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long>{

    @Query("""
            SELECT pf
            FROM PessoaFisica pf
            WHERE pf.empresa = :empresa
                AND pf.estado = :estado
            """)
    List<PessoaFisica> findByEmpresaAndEstado(@Param("empresa") Empresa empresa,
                                              @Param("estado") String estado);

}
