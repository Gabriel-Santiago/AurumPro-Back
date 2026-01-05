package com.AurumPro.services.componentes;

import com.AurumPro.dtos.componentes.convenio.ConvenioDTO;
import com.AurumPro.dtos.componentes.convenio.CreateConvenioDTO;
import com.AurumPro.entities.componentes.Convenio;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.repositories.componentes.ConvenioRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import com.AurumPro.utils.ValidateNomeConvenioExist;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConvenioService{

    private final ConvenioRepository repository;
    private final EmpresaRepository empresaRepository;
    private final ValidateNomeConvenioExist validateNomeConvenioExist;

    public ConvenioService(ConvenioRepository repository,
                           EmpresaRepository empresaRepository,
                           ValidateNomeConvenioExist validateNomeConvenioExist) {
        this.repository = repository;
        this.empresaRepository = empresaRepository;
        this.validateNomeConvenioExist = validateNomeConvenioExist;
    }

    @Transactional
    public void createConvenio(CreateConvenioDTO dto){
        Empresa empresa = empresaRepository
                .findById(dto.id())
                .orElseThrow(EmpresaNotFoundException::new);

        validateNomeConvenioExist.validate(dto.nome(), dto.id());

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
                        dto.getId(),
                        dto.getNome()
                ))
                .toList();
    }
}
