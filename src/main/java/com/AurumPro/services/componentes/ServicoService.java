package com.AurumPro.services.componentes;

import com.AurumPro.dtos.componentes.servico.CreateServicoDTO;
import com.AurumPro.dtos.componentes.servico.ServicoDTO;
import com.AurumPro.entities.componentes.Servico;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.exceptions.componentes.ServicoNotFoundException;
import com.AurumPro.repositories.componentes.ServicoRepository;
import com.AurumPro.utils.ValidateNomeServicoExist;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository repository;
    private final ValidateNomeServicoExist validateNomeServicoExist;

    public ServicoService(ServicoRepository repository,
                          ValidateNomeServicoExist validateNomeServicoExist) {
        this.repository = repository;
        this.validateNomeServicoExist = validateNomeServicoExist;
    }

    @Transactional
    public void createServico(CreateServicoDTO dto, Empresa empresa){
        validateNomeServicoExist.validate(dto.nome(), empresa.getId());

        Servico servico = new Servico();
        servico.setNome(dto.nome());
        servico.setEmpresa(empresa);

        repository.save(servico);
    }

    public List<ServicoDTO> findAll(Long empresaId){
        return repository
                .findByEmpresaId(empresaId)
                .stream()
                .map(dto -> new ServicoDTO(
                        dto.getId(),
                        dto.getNome()
                ))
                .toList();
    }

    public ServicoDTO findServico(Long id){
        return repository
                .findById(id)
                .map(dto -> new ServicoDTO(
                        dto.getId(),
                        dto.getNome()
                ))
                .orElseThrow(ServicoNotFoundException::new);
    }
}
