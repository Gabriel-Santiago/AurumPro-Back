package com.AurumPro.services.componentes;

import com.AurumPro.dtos.componentes.custo.CreateCustoDTO;
import com.AurumPro.dtos.componentes.custo.CustoDTO;
import com.AurumPro.entities.componentes.Custo;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.repositories.componentes.CustoRepository;
import org.springframework.stereotype.Service;

@Service
public class CustoService{

    private final CustoRepository repository;

    public CustoService(CustoRepository repository) {
        this.repository = repository;
    }

    public CustoDTO createCusto(CreateCustoDTO dto, Empresa empresa){
        Custo custo = new Custo();
        custo.setNome(dto.nome());
        custo.setValor(dto.valor());

        custo.setEmpresa(empresa);

        Custo custoSalvo = repository.save(custo);

        return new CustoDTO(
                custoSalvo.getId(),
                custo.getNome(),
                custoSalvo.getValor()
        );
    }
}
