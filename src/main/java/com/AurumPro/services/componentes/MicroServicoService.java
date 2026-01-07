package com.AurumPro.services.componentes;

import com.AurumPro.dtos.componentes.microServico.CreateMicroServicoDTO;
import com.AurumPro.dtos.componentes.microServico.MicroServicoDTO;
import com.AurumPro.entities.componentes.MicroServico;
import com.AurumPro.entities.componentes.Servico;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.exceptions.componentes.MicroServicoNotAssociatedToEmpresaException;
import com.AurumPro.exceptions.componentes.MicroServicoNotFoundException;
import com.AurumPro.repositories.componentes.MicroServicoRepository;
import com.AurumPro.repositories.componentes.ServicoRepository;
import com.AurumPro.utils.ValidateNomeMicroServicoExist;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MicroServicoService {

    private final MicroServicoRepository repository;
    private final ServicoRepository servicoRepository;
    private final ValidateNomeMicroServicoExist  validateNomeMicroServicoExist;

    public MicroServicoService(MicroServicoRepository repository,
                               ServicoRepository servicoRepository,
                               ValidateNomeMicroServicoExist validateNomeMicroServicoExist) {
        this.repository = repository;
        this.servicoRepository = servicoRepository;
        this.validateNomeMicroServicoExist = validateNomeMicroServicoExist;
    }

    @Transactional
    public void createMicroServico(CreateMicroServicoDTO dto, Empresa empresa){
        Servico servico = servicoRepository
                .findById(dto.servicoId())
                .orElseThrow(MicroServicoNotAssociatedToEmpresaException::new);

        validateNomeMicroServicoExist.validate(dto.nome(), empresa.getId(), dto.servicoId());

        MicroServico microServico = new MicroServico();
        microServico.setNome(dto.nome());
        microServico.setDescricao(dto.descricao());

        microServico.setEmpresa(empresa);
        microServico.setServico(servico);

        repository.save(microServico);
    }

    public List<MicroServicoDTO> findAll(Long servicoId){
        List<MicroServico> microServicoList = repository.findByServicoId(servicoId);

        return microServicoList
                .stream()
                .map(dto -> new MicroServicoDTO(
                        dto.getId(),
                        dto.getNome()
                ))
                .toList();
    }

    public MicroServicoDTO findMicroServico(Long id){
        return repository
                .findById(id)
                .map(dto -> new MicroServicoDTO(
                        dto.getId(),
                        dto.getNome()
                ))
                .orElseThrow(MicroServicoNotFoundException::new);
    }
}
