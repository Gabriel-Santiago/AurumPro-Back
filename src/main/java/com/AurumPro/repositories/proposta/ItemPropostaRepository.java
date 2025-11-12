package com.AurumPro.repositories.proposta;

import com.AurumPro.entities.proposta.ItemProposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPropostaRepository extends JpaRepository<ItemProposta, Long> {

}
