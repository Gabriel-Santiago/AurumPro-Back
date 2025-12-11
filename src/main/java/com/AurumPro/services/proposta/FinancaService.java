package com.AurumPro.services.proposta;

import com.AurumPro.dtos.componentes.custo.CustoDTO;
import com.AurumPro.dtos.proposta.FinancasDashboardDTO;
import com.AurumPro.dtos.proposta.ItemPropostaDTO;
import com.AurumPro.dtos.proposta.PropostaDTO;
import com.AurumPro.dtos.proposta.UpdateStatusPropostaDTO;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.entities.proposta.Proposta;
import com.AurumPro.enums.StatusProposta;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.exceptions.proposta.PropostaNotFoundException;
import com.AurumPro.exceptions.proposta.StatusInvalidoException;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import com.AurumPro.repositories.proposta.PropostaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class FinancaService {

    private final PropostaRepository propostaRepository;
    private final EmpresaRepository empresaRepository;
    private final PropostaService propostaService;

    public FinancaService(PropostaRepository propostaRepository,
                          EmpresaRepository empresaRepository,
                          PropostaService propostaService) {
        this.propostaRepository = propostaRepository;
        this.empresaRepository = empresaRepository;
        this.propostaService = propostaService;
    }

    @Transactional(readOnly = true)
    public FinancasDashboardDTO getDashboard(Long empresaId){
        List<Proposta> propostaList = propostaRepository
                .findByEmpresaId(empresaId);

        List<Proposta> propostaAtualizada = propostaList
                .stream()
                .map(propostaService::updateStatus)
                .toList();

        List<PropostaDTO> emAvaliacao = propostaAtualizada
                .stream()
                .filter(p -> p.getStatusProposta() == StatusProposta.EM_AVALIACAO)
                .map(this::toDTO)
                .toList();

        List<PropostaDTO> aceita = propostaAtualizada
                .stream()
                .filter(p -> p.getStatusProposta() == StatusProposta.ACEITA)
                .map(this::toDTO)
                .toList();

        List<PropostaDTO> recusada = propostaAtualizada
                .stream()
                .filter(p -> p.getStatusProposta() == StatusProposta.RECUSADA)
                .map(this::toDTO)
                .toList();

        List<PropostaDTO> expirada = propostaAtualizada
                .stream()
                .filter(p -> p.getStatusProposta() == StatusProposta.EXPIRADA)
                .map(this::toDTO)
                .toList();

        BigDecimal valorAReceber = emAvaliacao
                .stream()
                .map(PropostaDTO::valorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorRecebido = aceita
                .stream()
                .map(PropostaDTO::valorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalProposta = propostaAtualizada.size();
        long propostasEmAvaliacao = emAvaliacao.size();
        long propostasAceitas = aceita.size();
        long propostasRecusadas = recusada.size();
        long propostasExpiradas = expirada.size();

        BigDecimal taxaAceitacao = BigDecimal.ZERO;

        if (totalProposta > 0) {
            taxaAceitacao = BigDecimal
                    .valueOf(propostasAceitas)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(totalProposta), 2, RoundingMode.HALF_UP);
        }

        return new FinancasDashboardDTO(
                emAvaliacao,
                aceita,
                recusada,
                expirada,
                valorAReceber,
                valorRecebido,
                totalProposta,
                propostasEmAvaliacao,
                propostasAceitas,
                propostasRecusadas,
                propostasExpiradas,
                taxaAceitacao
        );
    }

    public PropostaDTO updateStatusProposta(UpdateStatusPropostaDTO dto){
        Empresa empresa = empresaRepository
                .findById(dto.empresaId())
                .orElseThrow(EmpresaNotFoundException::new);

        Proposta proposta = propostaRepository
                .findByPropostaId(dto.propostaId())
                .orElseThrow(PropostaNotFoundException::new);

        if (!proposta.getEmpresa().getId().equals(empresa.getId())) {
            throw new RuntimeException("Esta proposta não pertence à sua empresa");
        }

        validarTransicaoStatus(proposta.getStatusProposta(), dto.statusProposta());

        proposta.setStatusProposta(dto.statusProposta());
        proposta.setDataMudancaStatus(LocalDateTime.now());

        propostaRepository.save(proposta);

        return toDTO(proposta);
    }

    private PropostaDTO toDTO(Proposta proposta) {
        return new PropostaDTO(
                proposta.getPropostaId(),
                proposta.getCliente().getId(),
                proposta.getCliente().getNome(),
                proposta.getConvenio() != null ? proposta.getConvenio().getId() : null,
                proposta.getColaborador() != null ? proposta.getColaborador().getId() : null,
                proposta.getEmpresa().getId(),
                proposta.getConvenio() != null ? proposta.getConvenio().getNome() : null,
                proposta.getCustoList().stream()
                        .map(c -> new CustoDTO(c.getId(), c.getNome(), c.getValor()))
                        .toList(),
                proposta.getItemPropostaList().stream()
                        .map(i -> new ItemPropostaDTO(
                                i.getItemPropostaId(),
                                i.getServico().getId(),
                                i.getMicroServico().getId(),
                                i.getValorHora(),
                                i.getQtdHora(),
                                i.getValorTotal()
                        ))
                        .toList(),
                proposta.getTipoDesconto(),
                proposta.isDesconto(),
                proposta.getValorDesconto(),
                proposta.getPorcentagemDesconto(),
                proposta.getValorTotal(),
                proposta.getStatusProposta(),
                proposta.getDataCriacao(),
                proposta.getDataValidade(),
                proposta.getDataMudancaStatus()
        );
    }

    private void validarTransicaoStatus(StatusProposta statusAtual, StatusProposta novoStatus) {
        Map<StatusProposta, List<StatusProposta>> transicoesPermitidas = Map.of(
                StatusProposta.EM_AVALIACAO, List.of(StatusProposta.ACEITA, StatusProposta.RECUSADA),
                StatusProposta.RECUSADA, List.of(StatusProposta.ACEITA)
        );

        List<StatusProposta> permitidos = transicoesPermitidas.get(statusAtual);

        if (permitidos == null || !permitidos.contains(novoStatus)) {
            throw new StatusInvalidoException(statusAtual.getDescricao(), novoStatus.getDescricao());
        }
    }
}
