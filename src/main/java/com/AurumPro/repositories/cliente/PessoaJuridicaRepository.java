package com.AurumPro.repositories.cliente;

import com.AurumPro.entities.cliente.PessoaJuridica;
import com.AurumPro.entities.empresa.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {

    @Query("""
            SELECT pj
            FROM PessoaJuridica pj
            WHERE pj.empresa = :empresa
                AND pj.estado = :estado
            """)
    List<PessoaJuridica> findByEmpresaAndEstado(@Param("empresa") Empresa empresa,
                                              @Param("estado") String estado);

}
