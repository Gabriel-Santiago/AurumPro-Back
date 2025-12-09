package com.AurumPro.services.componentes;

import com.AurumPro.dtos.componentes.servico.CreateServicoDTO;
import com.AurumPro.dtos.componentes.servico.ServicoDTO;
import com.AurumPro.entities.componentes.Servico;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.repositories.componentes.ServicoRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    private final EmpresaRepository empresaRepository;
    private final ServicoRepository repository;

    public ServicoService(EmpresaRepository empresaRepository,
                          ServicoRepository repository) {
        this.empresaRepository = empresaRepository;
        this.repository = repository;
    }

    @Transactional
    public void createServico(CreateServicoDTO dto){
        Empresa empresa = empresaRepository
                .findById(dto.id())
                .orElseThrow(EmpresaNotFoundException::new);

        Servico servico = new Servico();
        servico.setNome(dto.nome());
        servico.setEmpresa(empresa);

        repository.save(servico);
    }

    public List<ServicoDTO> findAll(Long empresaId){
        empresaRepository
                .findById(empresaId)
                .orElseThrow(EmpresaNotFoundException::new);

        return repository
                .findAll()
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
                .orElseThrow();
    }
}
