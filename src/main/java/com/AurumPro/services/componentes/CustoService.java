package com.AurumPro.services.componentes;

import com.AurumPro.dtos.componentes.custo.CreateCustoDTO;
import com.AurumPro.entities.componentes.Custo;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.entities.proposta.Proposta;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.exceptions.proposta.PropostaNotFoundException;
import com.AurumPro.repositories.componentes.CustoRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import com.AurumPro.repositories.proposta.PropostaRepository;
import org.springframework.stereotype.Service;

@Service
public class CustoService{

    private final CustoRepository repository;
    private final EmpresaRepository empresaRepository;
    private final PropostaRepository propostaRepository;

    public CustoService(CustoRepository repository,
                        EmpresaRepository empresaRepository,
                        PropostaRepository propostaRepository) {
        this.repository = repository;
        this.empresaRepository = empresaRepository;
        this.propostaRepository = propostaRepository;
    }

    public void createCusto(CreateCustoDTO dto){
        Empresa empresa = empresaRepository
                .findById(dto.empresaId())
                .orElseThrow(EmpresaNotFoundException::new);

        Proposta proposta = propostaRepository
                .findById(dto.propostaId())
                .orElseThrow(PropostaNotFoundException::new);

        Custo custo = new Custo();
        custo.setNome(dto.nome());
        custo.setValor(dto.valor());

        custo.setEmpresa(empresa);
        custo.setProposta(proposta);

        repository.save(custo);
    }
}
