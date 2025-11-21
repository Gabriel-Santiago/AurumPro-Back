package com.AurumPro.repositories.cliente;

import com.AurumPro.entities.base.ClienteEntity;
import com.AurumPro.entities.cliente.PessoaFisica;
import com.AurumPro.entities.cliente.PessoaJuridica;
import com.AurumPro.entities.empresa.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long>{

    @Query("""
            SELECT pf
            FROM PessoaFisica pf
            WHERE pf.empresa = :empresa
                AND pf.estado = :estado
            """)
    List<PessoaFisica> findPessoFisicaByEmpresaAndEstado(@Param("empresa") Empresa empresa,
                                              @Param("estado") String estado);

    @Query("""
            SELECT pj
            FROM PessoaJuridica pj
            WHERE pj.empresa = :empresa
                AND pj.estado = :estado
            """)
    List<PessoaJuridica> findPessoaJuridicaByEmpresaAndEstado(@Param("empresa") Empresa empresa,
                                                @Param("estado") String estado);

    @Query("""
            SELECT pf
            FROM PessoaFisica pf
            WHERE pf.empresa.id = :empresaId
            """)
    List<PessoaFisica> findAllPessoaFisica(@Param("empresaId") Long empresaId);

    @Query("""
            SELECT pj
            FROM PessoaJuridica pj
            WHERE pj.empresa.id = :empresaId
            """)
    List<PessoaJuridica> findAllPessoaJuridica(@Param("empresaId") Long empresaId);
}
