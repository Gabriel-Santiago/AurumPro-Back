package com.AurumPro.services.componentes;

import com.AurumPro.dtos.componentes.convenio.ConvenioDTO;
import com.AurumPro.dtos.componentes.convenio.CreateConvenioDTO;
import com.AurumPro.entities.componentes.Convenio;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.repositories.componentes.ConvenioRepository;
import com.AurumPro.utils.ValidateNomeConvenioExist;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConvenioService{

    private final ConvenioRepository repository;
    private final ValidateNomeConvenioExist validateNomeConvenioExist;

    public ConvenioService(ConvenioRepository repository,
                           ValidateNomeConvenioExist validateNomeConvenioExist) {
        this.repository = repository;
        this.validateNomeConvenioExist = validateNomeConvenioExist;
    }

    @Transactional
    public void createConvenio(CreateConvenioDTO dto, Empresa empresa){
        validateNomeConvenioExist.validate(dto.nome(), empresa.getId());

        Convenio convenio = new Convenio();
        convenio.setNome(dto.nome());

        convenio.setEmpresa(empresa);

        repository.save(convenio);
    }

    public List<ConvenioDTO> findAllConvenio(Long empresaId){
        return repository
                .findByEmpresaId(empresaId)
                .stream()
                .map(dto -> new ConvenioDTO(
                        dto.getId(),
                        dto.getNome()
                ))
                .toList();
    }
}
