package com.AurumPro.services.proposta;

import com.AurumPro.dtos.proposta.AtividadeDTO;
import com.AurumPro.dtos.proposta.CreateAtividadeDTO;
import com.AurumPro.dtos.proposta.UpdateAtividadeDTO;
import com.AurumPro.entities.proposta.Atividade;
import com.AurumPro.entities.proposta.Proposta;
import com.AurumPro.exceptions.proposta.AtividadeNotFoundException;
import com.AurumPro.exceptions.proposta.PropostaNotFoundException;
import com.AurumPro.repositories.proposta.AtividadeRepository;
import com.AurumPro.repositories.proposta.PropostaRepository;
import com.AurumPro.utils.ValidateNomeAtividadeExist;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void createAtividade(CreateAtividadeDTO dto) {
        Proposta proposta = propostaRepository
                .findByPropostaId(dto.propostaId())
                .orElseThrow(PropostaNotFoundException::new);

        validateNomeAtividadeExist.validate(dto.nome(), dto.propostaId());

        Atividade atividade = new Atividade();
        atividade.setNome(dto.nome());
        atividade.setConcluida(false);
        atividade.setProposta(proposta);

        repository.save(atividade);
    }

    public List<AtividadeDTO> findAllAtividade(Long propostaId) {
        return repository
                .findByPropostaPropostaId(propostaId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public void updateConcluidaAtividade(UpdateAtividadeDTO dto) {
        Atividade atividade = repository
                .findById(dto.atividadeId())
                .orElseThrow(AtividadeNotFoundException::new);

        atividade.setConcluida(dto.concluida());
    }

    @Transactional
    public void deleteAtividade(Long atividadeId) {
        Atividade atividade = repository
                .findById(atividadeId)
                .orElseThrow(AtividadeNotFoundException::new);

        repository.delete(atividade);
    }

    private AtividadeDTO toDTO(Atividade atividade) {
        return new AtividadeDTO(
                atividade.getAtividadeId(),
                atividade.getNome(),
                atividade.isConcluida()
        );
    }
}
