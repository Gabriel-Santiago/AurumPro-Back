package com.AurumPro.services.proposta;

import com.AurumPro.dtos.proposta.AtividadeDTO;
import com.AurumPro.dtos.proposta.CreateAtividadeDTO;
import com.AurumPro.dtos.proposta.UpdateAtividadeDTO;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.entities.proposta.Atividade;
import com.AurumPro.entities.proposta.Proposta;
import com.AurumPro.exceptions.proposta.AtividadeNotFoundException;
import com.AurumPro.exceptions.proposta.PropostaNotFoundException;
import com.AurumPro.repositories.proposta.AtividadeRepository;
import com.AurumPro.repositories.proposta.PropostaRepository;
import com.AurumPro.utils.ValidateNomeAtividadeExist;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class AtividadeService {

    private final AtividadeRepository repository;
    private final PropostaRepository propostaRepository;
    private final ValidateNomeAtividadeExist validateNomeAtividadeExist;

    public AtividadeService(AtividadeRepository repository,
                            PropostaRepository propostaRepository,
                            ValidateNomeAtividadeExist validateNomeAtividadeExist) {
        this.repository = repository;
        this.propostaRepository = propostaRepository;
        this.validateNomeAtividadeExist = validateNomeAtividadeExist;
    }

    @Transactional
    public void createAtividade(CreateAtividadeDTO dto, Empresa empresa) {
        Proposta proposta = propostaRepository
                .findByPropostaId(dto.propostaId())
                .orElseThrow(PropostaNotFoundException::new);

        validateNomeAtividadeExist.validate(dto.nome(), dto.propostaId());

        Atividade atividade = new Atividade();
        atividade.setNome(dto.nome());
        atividade.setConcluida(false);
        atividade.setProposta(proposta);
        atividade.setEmpresa(empresa);

        repository.save(atividade);
    }

    public List<AtividadeDTO> findAllAtividade(Long propostaId, Long empresaId) {
        Optional<Proposta> proposta = propostaRepository
                .findByPropostaIdAndEmpresaId(propostaId, empresaId);

        if (proposta.isEmpty()) {
            throw new PropostaNotFoundException();
        }

        return repository
                .findByPropostaPropostaId(propostaId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public void updateConcluidaAtividade(UpdateAtividadeDTO dto, Empresa empresa) throws AccessDeniedException {
        Atividade atividade = getAtividadeAutorizada(dto.atividadeId(), empresa);

        atividade.setConcluida(dto.concluida());
    }

    @Transactional
    public void deleteAtividade(Long atividadeId, Empresa empresa) throws AccessDeniedException {
        Atividade atividade = getAtividadeAutorizada(atividadeId, empresa);

        repository.delete(atividade);
    }

    private AtividadeDTO toDTO(Atividade atividade) {
        return new AtividadeDTO(
                atividade.getAtividadeId(),
                atividade.getNome(),
                atividade.isConcluida()
        );
    }

    private Atividade getAtividadeAutorizada(Long atividadeId, Empresa empresa) throws AccessDeniedException {
        Atividade atividade = repository
                .findAutorizada(atividadeId)
                .orElseThrow(AtividadeNotFoundException::new);

        Proposta proposta = atividade.getProposta();

        if (!proposta.getEmpresa().getId().equals(empresa.getId())) {
            throw new AccessDeniedException("Não autorizado");
        }

        return atividade;
    }
}
