package com.AurumPro.repositories.componentes;

import com.AurumPro.entities.componentes.MicroServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroServicoRepository extends JpaRepository<MicroServico, Long> {

}
