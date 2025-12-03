package com.AurumPro.services.empresa;

import com.AurumPro.dtos.empresa.ConsultorDTO;
import com.AurumPro.dtos.empresa.CreateConsultorDTO;
import com.AurumPro.entities.empresa.Consultor;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.enums.TipoConsultor;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.repositories.empresa.ConsultorRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ConsultorService {

    private final ConsultorRepository repository;
    private final EmpresaRepository empresaRepository;

    public ConsultorService(ConsultorRepository repository,
                            EmpresaRepository empresaRepository) {
        this.repository = repository;
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public void createConsultor(CreateConsultorDTO dto){
        Empresa empresa = empresaRepository
                .findById(dto.empresaId())
                .orElseThrow(EmpresaNotFoundException::new);

        Consultor consultor = new Consultor();
        consultor.setNome(dto.nome());
        consultor.setTelefone(dto.telefone());
        consultor.setTipoConsultor(dto.tipoConsultor());

        consultor.setEmpresa(empresa);

        repository.save(consultor);
    }

    public List<ConsultorDTO> findAllConsultor(Long empresaId){
        empresaRepository
                .findById(empresaId)
                .orElseThrow(EmpresaNotFoundException::new);

        return repository
                .findAll()
                .stream()
                .map(dto -> new ConsultorDTO(
                        dto.getId(),
                        dto.getNome(),
                        dto.getTelefone(),
                        dto.getTipoConsultor()
                ))
                .toList();
    }

    public List<Map<String, String>> findAllTipoConsultor(){
        return Arrays
                .stream(TipoConsultor.values())
                .map(tipoConsultor -> Map.of(
                        tipoConsultor.name(),
                        tipoConsultor.getDescricao()
                ))
                .toList();
    }
}
