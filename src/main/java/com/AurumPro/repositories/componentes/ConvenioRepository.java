package com.AurumPro.repositories.componentes;

import com.AurumPro.entities.componentes.Convenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvenioRepository extends JpaRepository<Convenio, Long> {

}
