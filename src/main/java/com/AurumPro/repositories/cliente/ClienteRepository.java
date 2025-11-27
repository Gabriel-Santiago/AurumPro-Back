package com.AurumPro.repositories.cliente;

import com.AurumPro.entities.cliente.Cliente;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.enums.TipoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    @Query("""
            SELECT c
            FROM Cliente c
            WHERE c.empresa = :empresa
                AND c.estado = :estado
                AND c.tipoPessoa = :tipoPessoa
            """)
    List<Cliente> findTipoPessoaByEstado(@Param("empresa") Empresa empresa,
                                         @Param("estado") String estado,
                                         @Param("tipoPessoa")TipoPessoa tipoPessoa);

}
