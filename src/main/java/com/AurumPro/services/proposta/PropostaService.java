package com.AurumPro.services.proposta;

import com.AurumPro.dtos.componentes.custo.CustoDTO;
import com.AurumPro.dtos.proposta.AtividadeDTO;
import com.AurumPro.dtos.proposta.CreatePropostaDTO;
import com.AurumPro.dtos.proposta.ItemPropostaDTO;
import com.AurumPro.dtos.proposta.PropostaDTO;
import com.AurumPro.entities.cliente.Cliente;
import com.AurumPro.entities.componentes.Convenio;
import com.AurumPro.entities.componentes.Custo;
import com.AurumPro.entities.empresa.Colaborador;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.entities.proposta.Atividade;
import com.AurumPro.entities.proposta.ItemProposta;
import com.AurumPro.entities.proposta.Proposta;
import com.AurumPro.enums.StatusProposta;
import com.AurumPro.enums.TipoDesconto;
import com.AurumPro.exceptions.cliente.ClienteNotFoundException;
import com.AurumPro.exceptions.componentes.ConvenioNotAssociatedToEmpresaException;
import com.AurumPro.exceptions.componentes.ConvenioNotFoundException;
import com.AurumPro.exceptions.empresa.ColaboradorNotAssociatedToEmpresaException;
import com.AurumPro.exceptions.empresa.ColaboradorNotFoundException;
import com.AurumPro.exceptions.proposta.PropostaNotFoundException;
import com.AurumPro.repositories.cliente.ClienteRepository;
import com.AurumPro.repositories.componentes.ConvenioRepository;
import com.AurumPro.repositories.componentes.CustoRepository;
import com.AurumPro.repositories.empresa.ColaboradorRepository;
import com.AurumPro.repositories.proposta.AtividadeRepository;
import com.AurumPro.repositories.proposta.ItemPropostaRepository;
import com.AurumPro.repositories.proposta.PropostaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class PropostaService {

    private final ColaboradorRepository colaboradorRepository;
    private final ConvenioRepository convenioRepository;
    private final CustoRepository custoRepository;
    private final ClienteRepository clienteRepository;
    private final ItemPropostaRepository itemPropostaRepository;
    private final PropostaRepository repository;
    private  final AtividadeRepository atividadeRepository;

    public PropostaService(ColaboradorRepository colaboradorRepository,
                           ConvenioRepository convenioRepository,
                           CustoRepository custoRepository,
                           ClienteRepository clienteRepository,
                           ItemPropostaRepository itemPropostaRepository,
                           PropostaRepository repository,
                           AtividadeRepository atividadeRepository) {
        this.colaboradorRepository = colaboradorRepository;
        this.convenioRepository = convenioRepository;
        this.custoRepository = custoRepository;
        this.clienteRepository = clienteRepository;
        this.itemPropostaRepository = itemPropostaRepository;
        this.repository = repository;
        this.atividadeRepository = atividadeRepository;
    }

    @Transactional
    public void createProposta(CreatePropostaDTO dto, Empresa empresa) {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(ClienteNotFoundException::new);

        Convenio convenio = null;
        if (dto.convenioId() != null) {
            convenio = convenioRepository.findById(dto.convenioId())
                    .orElseThrow(ConvenioNotFoundException::new);

            if (!convenio.getEmpresa().getId().equals(empresa.getId())) {
                throw new ConvenioNotAssociatedToEmpresaException();
            }
        }

        Colaborador colaborador = null;
        if (dto.colaboradorId() != null) {
            colaborador = colaboradorRepository.findById(dto.colaboradorId())
                    .orElseThrow(ColaboradorNotFoundException::new);

            if (!colaborador.getEmpresa().getId().equals(empresa.getId())) {
                throw new ColaboradorNotAssociatedToEmpresaException();
            }
        }

        List<Long> custoIds = dto.custoList() == null ? Collections.emptyList() : dto.custoList();
        List<Long> itemIds = dto.itemPropostaList() == null ? Collections.emptyList() : dto.itemPropostaList();
        List<Long> atividadeIds = dto.atividadeList() == null ? Collections.emptyList() : dto.atividadeList();

        List<Custo> custoList = custoRepository.findAllById(custoIds);
        List<ItemProposta> itemPropostaList = itemPropostaRepository.findAllById(itemIds);
        List<Atividade> atividadeList = atividadeRepository.findAllById(atividadeIds);

        if (custoList.size() != custoIds.size()) {
            throw new RuntimeException("Um ou mais custos não foram encontrados");
        }

        if (itemPropostaList.size() != itemIds.size()) {
            throw new RuntimeException("Um ou mais itens da proposta não foram encontrados");
        }

        if (atividadeList.size() != atividadeIds.size()) {
            throw new RuntimeException("Uma ou mais atividades não foram encontradas");
        }

        Proposta proposta = new Proposta();
        proposta.setDataCriacao(LocalDateTime.now());
        proposta.setDataValidade(LocalDateTime.now().plusDays(8));
        proposta.setEmpresa(empresa);
        proposta.setCliente(cliente);
        proposta.setConvenio(convenio);
        proposta.setColaborador(colaborador);
        proposta.setStatusProposta(StatusProposta.EM_AVALIACAO);
        proposta.setValorDesconto(BigDecimal.ZERO);
        proposta.setPorcentagemDesconto(BigDecimal.ZERO);
        proposta.setTipoDesconto(dto.tipoDesconto());

        proposta = repository.save(proposta);

        Proposta finalProposta = proposta;

        custoList.forEach(c -> c.setProposta(finalProposta));
        itemPropostaList.forEach(i -> i.setProposta(finalProposta));
        atividadeList.forEach(a -> {
            a.setProposta(finalProposta);
            a.setConcluida(false);
        });

        BigDecimal sumCusto = custoList.stream()
                .map(Custo::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal sumItemProposta = itemPropostaList.stream()
                .map(ItemProposta::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalBruto = sumCusto.add(sumItemProposta);
        BigDecimal valorTotalFinal = totalBruto;

        if (dto.desconto() && dto.tipoDesconto() != TipoDesconto.NENHUM) {
            switch (dto.tipoDesconto()) {
                case VALOR -> {
                    proposta.setValorDesconto(dto.valorDesconto());
                    valorTotalFinal = totalBruto.subtract(dto.valorDesconto());
                }
                case PORCENTAGEM -> {
                    proposta.setPorcentagemDesconto(dto.porcentagemDesconto());

                    BigDecimal desconto = totalBruto
                            .multiply(dto.porcentagemDesconto())
                            .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

                    valorTotalFinal = totalBruto.subtract(desconto);
                }
            }
        }

        proposta.setValorTotal(valorTotalFinal.max(BigDecimal.ZERO));
        proposta.setCustoList(custoList);
        proposta.setItemPropostaList(itemPropostaList);
        proposta.setAtividadeList(atividadeList);
    }

    public List<PropostaDTO> findAll(Long empresaId){
        List<Proposta> propostaList = repository.findByEmpresaId(empresaId);

        return propostaList
                .stream()
                .map(this::updateStatus)
                .map(this::toDTO)
                .toList();
    }

    public List<PropostaDTO> findPropostaByCliente(Long empresaId, Long clienteId){
        List<Proposta> propostaList = repository.findByEmpresaIdAndClienteId(empresaId, clienteId);

        return propostaList
                .stream()
                .map(this::updateStatus)
                .map(this::toDTO)
                .toList();
    }

    public List<PropostaDTO> findPropostaByStatus(Long empresaId) {
        return repository
                .findByStatusPropostaAndEmpresaId(StatusProposta.ACEITA, empresaId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Proposta updateStatus(Proposta proposta){
        boolean expirou = LocalDateTime.now().isAfter(proposta.getDataValidade());

        if (expirou
                && proposta.getStatusProposta() != StatusProposta.ACEITA
                && proposta.getStatusProposta() != StatusProposta.RECUSADA){
            proposta.setStatusProposta(StatusProposta.EXPIRADA);

            return repository.save(proposta);
        }

        return proposta;
    }

    @Transactional
    public void delete(Long propostaId){
        Proposta proposta = repository
                .findByPropostaId(propostaId)
                .orElseThrow(PropostaNotFoundException::new);

        repository.delete(proposta);
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
                proposta.getAtividadeList().stream()
                        .map(a -> new AtividadeDTO(a.getId(), a.getNome(), a.isConcluida()))
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
}
