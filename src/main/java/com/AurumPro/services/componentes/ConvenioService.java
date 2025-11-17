package com.AurumPro.services.componentes;

import com.AurumPro.dtos.componentes.convenio.ConvenioDTO;
import com.AurumPro.dtos.componentes.convenio.CreateConvenioDTO;
import com.AurumPro.entities.componentes.Convenio;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.repositories.componentes.ConvenioRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConvenioService{

    private final ConvenioRepository repository;
    private final EmpresaRepository empresaRepository;

    public ConvenioService(ConvenioRepository repository, EmpresaRepository empresaRepository) {
        this.repository = repository;
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public void createConvenio(CreateConvenioDTO dto){
        Empresa empresa = empresaRepository
                .findById(dto.id())
                .orElseThrow(EmpresaNotFoundException::new);

        Convenio convenio = new Convenio();
        convenio.setNome(dto.nome());

        convenio.setEmpresa(empresa);

        repository.save(convenio);
    }

    public List<ConvenioDTO> findAll(Long empresaId){
        List<Convenio> convenioList = repository.findByEmpresaId(empresaId);

        return convenioList
                .stream()
                .map(dto -> new ConvenioDTO(
                        dto.getNome()
                ))
                .toList();
    }
}
