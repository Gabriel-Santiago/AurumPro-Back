package com.AurumPro.services.proposta;

import com.AurumPro.dtos.proposta.CreateItemPropostaDTO;
import com.AurumPro.dtos.proposta.ItemPropostaDTO;
import com.AurumPro.entities.componentes.MicroServico;
import com.AurumPro.entities.componentes.Servico;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.entities.proposta.ItemProposta;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.repositories.componentes.MicroServicoRepository;
import com.AurumPro.repositories.componentes.ServicoRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import com.AurumPro.repositories.proposta.ItemPropostaRepository;
import com.AurumPro.repositories.proposta.PropostaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ItemPropostaService {

    private final EmpresaRepository empresaRepository;
    private final ItemPropostaRepository repository;
    private final MicroServicoRepository microServicoRepository;
    private final ServicoRepository servicoRepository;

    public ItemPropostaService(ItemPropostaRepository repository,
                               MicroServicoRepository microServicoRepository,
                               PropostaRepository propostaRepository, EmpresaRepository empresaRepository,
                               ServicoRepository servicoRepository) {
        this.empresaRepository = empresaRepository;
        this.repository = repository;
        this.microServicoRepository = microServicoRepository;
        this.servicoRepository = servicoRepository;
    }

    public void createItemProposta(CreateItemPropostaDTO dto){
        Empresa empresa = empresaRepository
                .findById(dto.empresaId())
                .orElseThrow(EmpresaNotFoundException::new);

        List<Servico> servicoList = servicoRepository.findAllById(dto.servicoId());
        List<MicroServico> microServicoList = microServicoRepository.findAllById(dto.microServicoId());

        ItemProposta itemProposta = new ItemProposta();
        itemProposta.setEmpresa(empresa);
        itemProposta.setServico(servicoList);
        itemProposta.setMicroServico(microServicoList);

        BigDecimal total = servicoList.stream()
                .flatMap(s -> s.getMicroServicoList().stream())
                .map(MicroServico::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        itemProposta.setValorTotal(total);

        repository.save(itemProposta);
    }

    public List<ItemPropostaDTO> findAll(Long empresaId){
        List<ItemProposta> itemPropostaList = repository.findByEmpresaId(empresaId);

        return itemPropostaList
                .stream()
                .map(dto -> new ItemPropostaDTO(
                        dto.getServico(),
                        dto.getMicroServico(),
                        dto.getValorTotal()
                ))
                .toList();
    }



}
