package com.AurumPro.services.empresa;

import com.AurumPro.dtos.empresa.ColaboradorDTO;
import com.AurumPro.dtos.empresa.CreateColaboradorDTO;
import com.AurumPro.entities.empresa.Colaborador;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.repositories.empresa.ColaboradorRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaboradorService {

    private final ColaboradorRepository repository;
    private final EmpresaRepository empresaRepository;

    public ColaboradorService(ColaboradorRepository repository,
                              EmpresaRepository empresaRepository) {
        this.repository = repository;
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public void createColaborador(CreateColaboradorDTO dto){
        Empresa empresa = empresaRepository
                .findById(dto.empresaId())
                .orElseThrow(EmpresaNotFoundException::new);

        Colaborador colaborador = new Colaborador();
        colaborador.setNome(dto.nome());
        colaborador.setTelefone(dto.telefone());
        colaborador.setFuncao(dto.funcao());

        colaborador.setEmpresa(empresa);

        repository.save(colaborador);
    }

    public List<ColaboradorDTO> findAllColaborador(Long empresaId){
        empresaRepository
                .findById(empresaId)
                .orElseThrow(EmpresaNotFoundException::new);

        return repository
                .findAll()
                .stream()
                .map(dto -> new ColaboradorDTO(
                        dto.getId(),
                        dto.getNome(),
                        dto.getTelefone(),
                        dto.getFuncao()
                ))
                .toList();
    }

    public List<String> findAllFuncao(){
        return repository
                .findAll()
                .stream()
                .map(Colaborador::getFuncao)
                .filter(funcao -> funcao != null && !funcao.trim().isEmpty())
                .distinct()
                .sorted()
                .toList();
    }
}
