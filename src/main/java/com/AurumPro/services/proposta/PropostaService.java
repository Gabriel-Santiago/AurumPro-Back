package com.AurumPro.services.proposta;

import com.AurumPro.dtos.proposta.CreatePropostaDTO;
import com.AurumPro.entities.base.ClienteEntity;
import com.AurumPro.entities.componentes.Convenio;
import com.AurumPro.entities.componentes.Custo;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.entities.proposta.ItemProposta;
import com.AurumPro.entities.proposta.Proposta;
import com.AurumPro.enums.TipoDesconto;
import com.AurumPro.exceptions.cliente.ClienteNotFoundException;
import com.AurumPro.exceptions.componentes.ConvenioNotFoundException;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.repositories.cliente.ClienteRepository;
import com.AurumPro.repositories.componentes.ConvenioRepository;
import com.AurumPro.repositories.componentes.CustoRepository;
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

    private final ConvenioRepository convenioRepository;
    private final CustoRepository custoRepository;
    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;
    private final ItemPropostaRepository itemPropostaRepository;
    private final PropostaRepository repository;

    public PropostaService(ConvenioRepository convenioRepository,
                           CustoRepository custoRepository,
                           ClienteRepository clienteRepository,
                           EmpresaRepository empresaRepository,
                           ItemPropostaRepository itemPropostaRepository,
                           PropostaRepository repository) {
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

        ClienteEntity cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(ClienteNotFoundException::new);

        Convenio convenio = convenioRepository.findById(dto.convenioId())
                .orElseThrow(ConvenioNotFoundException::new);

        List<Custo> custoList = custoRepository.findAllById(dto.custoList());
        List<ItemProposta> itemPropostaList = itemPropostaRepository.findAllById(dto.itemPropostaList());

        BigDecimal sumCusto = custoList.stream()
                .map(Custo::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal sumItemProposta = itemPropostaList.stream()
                .map(ItemProposta::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalBruto = sumCusto.add(sumItemProposta);

        Proposta proposta = new Proposta();
        proposta.setDataCriacao(LocalDateTime.now());
        proposta.setEmpresa(empresa);
        proposta.setCliente(cliente);
        proposta.setConvenio(convenio);
        proposta.setCustoList(custoList);
        proposta.setItemPropostaList(itemPropostaList);
        
        proposta.setValorDesconto(BigDecimal.ZERO);
        proposta.setPorcentagemDesconto(BigDecimal.ZERO);
        proposta.setTipoDesconto(dto.tipoDesconto());

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

        repository.save(proposta);
    }

}
