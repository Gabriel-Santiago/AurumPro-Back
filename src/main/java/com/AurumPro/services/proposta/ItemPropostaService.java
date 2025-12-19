package com.AurumPro.services.proposta;

import com.AurumPro.dtos.proposta.CreateItemPropostaDTO;
import com.AurumPro.dtos.proposta.ItemPropostaDTO;
import com.AurumPro.entities.componentes.MicroServico;
import com.AurumPro.entities.componentes.Servico;
import com.AurumPro.entities.proposta.ItemProposta;
import com.AurumPro.exceptions.componentes.MicroServicoNotFoundException;
import com.AurumPro.exceptions.componentes.ServicoNotFoundException;
import com.AurumPro.repositories.componentes.MicroServicoRepository;
import com.AurumPro.repositories.componentes.ServicoRepository;
import com.AurumPro.repositories.proposta.ItemPropostaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ItemPropostaService {

    private final ItemPropostaRepository repository;
    private final MicroServicoRepository microServicoRepository;
    private final ServicoRepository servicoRepository;

    public ItemPropostaService(ItemPropostaRepository repository,
                               MicroServicoRepository microServicoRepository,
                               ServicoRepository servicoRepository) {
        this.repository = repository;
        this.microServicoRepository = microServicoRepository;
        this.servicoRepository = servicoRepository;
    }

    @Transactional
    public ItemPropostaDTO createItemProposta(CreateItemPropostaDTO dto) {
        Servico servico = servicoRepository.findById(dto.servicoId())
                .orElseThrow(ServicoNotFoundException::new);

        MicroServico microServico = microServicoRepository.findById(dto.microServicoId())
                .orElseThrow(MicroServicoNotFoundException::new);

        ItemProposta itemProposta = getItemProposta(dto, microServico, servico);

        ItemProposta itemPropostaSalva = repository.save(itemProposta);

        return new ItemPropostaDTO(
                itemPropostaSalva.getItemPropostaId(),
                itemPropostaSalva.getServico().getId(),
                itemPropostaSalva.getMicroServico().getId(),
                itemPropostaSalva.getValorHora(),
                itemPropostaSalva.getQtdHora(),
                itemPropostaSalva.getValorTotal()
        );
    }

    private static ItemProposta getItemProposta(CreateItemPropostaDTO dto, MicroServico microServico, Servico servico) {
        BigDecimal valorHora = dto.valorHora() != null ? dto.valorHora() : null;
        BigDecimal quantidadeHoras = dto.qtdHora() != null ? dto.qtdHora() : null;
        BigDecimal valorTotal = dto.valorTotal() != null ? dto.valorTotal() : null;

        if (valorTotal == null && valorHora != null && quantidadeHoras != null) {
            valorTotal = valorHora.multiply(quantidadeHoras);
        }

        ItemProposta itemProposta = new ItemProposta();
        itemProposta.setServico(servico);
        itemProposta.setMicroServico(microServico);
        itemProposta.setQtdHora(quantidadeHoras);
        itemProposta.setValorHora(valorHora);
        itemProposta.setValorTotal(valorTotal != null ? valorTotal : BigDecimal.ZERO);
        return itemProposta;
    }
}
