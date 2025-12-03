package com.AurumPro.repositories.empresa;

import com.AurumPro.entities.empresa.Consultor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultorRepository extends JpaRepository<Consultor, Long> {

}
