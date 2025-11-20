package com.AurumPro.services.proposta;

import com.AurumPro.dtos.proposta.CreateItemPropostaDTO;
import com.AurumPro.entities.componentes.MicroServico;
import com.AurumPro.entities.componentes.Servico;
import com.AurumPro.entities.proposta.ItemProposta;
import com.AurumPro.repositories.componentes.MicroServicoRepository;
import com.AurumPro.repositories.componentes.ServicoRepository;
import com.AurumPro.repositories.proposta.ItemPropostaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void createItemProposta(CreateItemPropostaDTO dto) {
        Servico servico = servicoRepository.findById(dto.servicoId())
                .orElseThrow();

        List<MicroServico> microServicoList = microServicoRepository.findAllById(dto.microServicoId());

        for (MicroServico microServico : microServicoList){
            ItemProposta itemProposta = new ItemProposta();
            itemProposta.setServico(servico);
            itemProposta.setMicroServico(microServico);
            itemProposta.setValorTotal(microServico.getValorTotal());

            repository.save(itemProposta);
        }
    }
}
