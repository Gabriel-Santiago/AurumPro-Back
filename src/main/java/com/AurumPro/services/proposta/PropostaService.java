package com.AurumPro.services.proposta;

import com.AurumPro.dtos.componentes.custo.CustoDTO;
import com.AurumPro.dtos.proposta.CreatePropostaDTO;
import com.AurumPro.dtos.proposta.ItemPropostaDTO;
import com.AurumPro.dtos.proposta.PropostaDTO;
import com.AurumPro.entities.cliente.Cliente;
import com.AurumPro.entities.componentes.Convenio;
import com.AurumPro.entities.componentes.Custo;
import com.AurumPro.entities.empresa.Colaborador;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.entities.proposta.ItemProposta;
import com.AurumPro.entities.proposta.Proposta;
import com.AurumPro.enums.TipoDesconto;
import com.AurumPro.exceptions.cliente.ClienteNotFoundException;
import com.AurumPro.exceptions.componentes.ConvenioNotFoundException;
import com.AurumPro.exceptions.empresa.ColaboradorNotFoundEmpresaException;
import com.AurumPro.exceptions.empresa.ColaboradorNotFoundException;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.exceptions.proposta.PropostaNotFoundException;
import com.AurumPro.repositories.cliente.ClienteRepository;
import com.AurumPro.repositories.componentes.ConvenioRepository;
import com.AurumPro.repositories.componentes.CustoRepository;
import com.AurumPro.repositories.empresa.ColaboradorRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import com.AurumPro.repositories.proposta.ItemPropostaRepository;
import com.AurumPro.repositories.proposta.PropostaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PropostaService {

    private final ColaboradorRepository colaboradorRepository;
    private final ConvenioRepository convenioRepository;
    private final CustoRepository custoRepository;
    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;
    private final ItemPropostaRepository itemPropostaRepository;
    private final PropostaRepository repository;

    public PropostaService(ColaboradorRepository colaboradorRepository,
                           ConvenioRepository convenioRepository,
                           CustoRepository custoRepository,
                           ClienteRepository clienteRepository,
                           EmpresaRepository empresaRepository,
                           ItemPropostaRepository itemPropostaRepository,
                           PropostaRepository repository) {
        this.colaboradorRepository = colaboradorRepository;
        this.convenioRepository = convenioRepository;
        this.custoRepository = custoRepository;
        this.clienteRepository = clienteRepository;
        this.empresaRepository = empresaRepository;
        this.itemPropostaRepository = itemPropostaRepository;
        this.repository = repository;
    }

    @Transactional
    public void createProposta(CreatePropostaDTO dto) {

        Empresa empresa = empresaRepository.findById(dto.empresaId())
                .orElseThrow(EmpresaNotFoundException::new);

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(ClienteNotFoundException::new);

        Convenio convenio = convenioRepository.findById(dto.convenioId())
                .orElseThrow(ConvenioNotFoundException::new);

        Colaborador colaborador = null;
        if (dto.colaboradorId() != null) {
            colaborador = colaboradorRepository.findById(dto.colaboradorId())
                    .orElseThrow(ColaboradorNotFoundException::new);

            if (!colaborador.getEmpresa().getId().equals(empresa.getId())){
                throw new ColaboradorNotFoundEmpresaException();
            }
        }

        Proposta proposta = new Proposta();
        proposta.setDataCriacao(LocalDateTime.now());
        proposta.setEmpresa(empresa);
        proposta.setCliente(cliente);
        proposta.setConvenio(convenio);
        proposta.setColaborador(colaborador);

        proposta.setValorDesconto(BigDecimal.ZERO);
        proposta.setPorcentagemDesconto(BigDecimal.ZERO);
        proposta.setTipoDesconto(dto.tipoDesconto());

        Proposta propostaSalva = repository.save(proposta);

        List<Custo> custoList = custoRepository.findAllById(dto.custoList());
        custoList.forEach(c -> c.setProposta(propostaSalva));

        List<ItemProposta> itemPropostaList = itemPropostaRepository.findAllById(dto.itemPropostaList());
        itemPropostaList.forEach(i -> i.setProposta(propostaSalva));

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

                case VALOR:
                    proposta.setValorDesconto(dto.valorDesconto());
                    valorTotalFinal = totalBruto.subtract(dto.valorDesconto());
                    break;

                case PORCENTAGEM:
                    proposta.setPorcentagemDesconto(dto.porcentagemDesconto());

                    BigDecimal desconto = totalBruto
                            .multiply(dto.porcentagemDesconto())
                            .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

                    valorTotalFinal = totalBruto.subtract(desconto);
                    break;
            }
        }

        proposta.setValorTotal(valorTotalFinal.max(BigDecimal.ZERO));

        proposta.setCustoList(custoList);
        proposta.setItemPropostaList(itemPropostaList);

        repository.save(proposta);
    }


    public List<PropostaDTO> findAll(Long empresaId){
        List<Proposta> propostaList = repository.findByEmpresaId(empresaId);

        return propostaList
                .stream()
                .map(dto -> new PropostaDTO(
                        dto.getPropostaId(),
                        dto.getCliente().getId(),
                        dto.getCliente().getNome(),
                        dto.getConvenio().getId(),
                        dto.getColaborador() != null ? dto.getColaborador().getId() : null,
                        dto.getEmpresa().getId(),
                        dto.getConvenio().getNome(),
                        dto.getCustoList().stream()
                                .map(c -> new CustoDTO(c.getId(), c.getNome(), c.getValor()))
                                .toList(),
                        dto.getItemPropostaList().stream()
                                .map(i -> new ItemPropostaDTO(
                                        i.getItemPropostaId(),
                                        i.getServico().getId(),
                                        i.getMicroServico().getId(),
                                        i.getMicroServico().getValorHora(),
                                        i.getMicroServico().getQtdHora(),
                                        i.getValorTotal()
                                ))
                                .toList(),
                        dto.getTipoDesconto(),
                        dto.isDesconto(),
                        dto.getValorDesconto(),
                        dto.getPorcentagemDesconto(),
                        dto.getValorTotal()
                ))
                .toList();
    }

    public List<PropostaDTO> findPropostaByCliente(Long empresaId, Long clienteId){
        List<Proposta> propostaList = repository.findByEmpresaIdAndClienteId(empresaId, clienteId);

        return propostaList
                .stream()
                .map(prop -> new PropostaDTO(
                        prop.getPropostaId(),
                        prop.getCliente().getId(),
                        prop.getCliente().getNome(),
                        prop.getConvenio().getId(),
                        prop.getColaborador() != null ? prop.getColaborador().getId() : null,
                        prop.getEmpresa().getId(),
                        prop.getConvenio().getNome(),
                        prop.getCustoList().stream()
                                .map(c -> new CustoDTO(c.getId(), c.getNome(), c.getValor()))
                                .toList(),
                        prop.getItemPropostaList().stream()
                                .map(i -> new ItemPropostaDTO(
                                        i.getItemPropostaId(),
                                        i.getServico().getId(),
                                        i.getMicroServico().getId(),
                                        i.getMicroServico().getValorHora(),
                                        i.getMicroServico().getQtdHora(),
                                        i.getValorTotal()
                                ))
                                .toList(),
                        prop.getTipoDesconto(),
                        prop.isDesconto(),
                        prop.getValorDesconto(),
                        prop.getPorcentagemDesconto(),
                        prop.getValorTotal()
                ))
                .toList();
    }

    public Proposta findById(Long id){
        return repository
                .findById(id)
                .orElseThrow(PropostaNotFoundException::new);
    }
}
