package com.AurumPro.repositories.componentes;

import com.AurumPro.entities.componentes.Custo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustoRepository extends JpaRepository<Custo, Long> {

}
