package com.AurumPro.services.componentes;

import com.AurumPro.dtos.componentes.custo.CreateCustoDTO;
import com.AurumPro.entities.componentes.Custo;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.repositories.componentes.CustoRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import org.springframework.stereotype.Service;

@Service
public class CustoService{

    private final CustoRepository repository;
    private final EmpresaRepository empresaRepository;

    public CustoService(CustoRepository repository,
                        EmpresaRepository empresaRepository) {
        this.repository = repository;
        this.empresaRepository = empresaRepository;
    }

    public void createCusto(CreateCustoDTO dto){
        Empresa empresa = empresaRepository
                .findById(dto.empresaId())
                .orElseThrow(EmpresaNotFoundException::new);

        Custo custo = new Custo();
        custo.setNome(dto.nome());
        custo.setValor(dto.valor());

        custo.setEmpresa(empresa);

        repository.save(custo);
    }
}
