package com.AurumPro.repositories.proposta;

import com.AurumPro.entities.proposta.ItemProposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPropostaRepository extends JpaRepository<ItemProposta, Long> {

    @Query("select ip from ItemProposta ip where ip.proposta.propostaId = :propostaId")
    List<ItemProposta> findByPropostaId(Long propostaId);
}
