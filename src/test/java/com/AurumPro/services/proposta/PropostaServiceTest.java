package com.AurumPro.services.proposta;

import com.AurumPro.dtos.proposta.CreatePropostaDTO;
import com.AurumPro.dtos.proposta.FindPropostaByClienteDTO;
import com.AurumPro.dtos.proposta.PropostaDTO;
import com.AurumPro.entities.base.ClienteEntity;
import com.AurumPro.entities.componentes.Convenio;
import com.AurumPro.entities.componentes.Custo;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.entities.proposta.ItemProposta;
import com.AurumPro.entities.proposta.Proposta;
import com.AurumPro.enums.TipoDesconto;
import com.AurumPro.repositories.cliente.ClienteRepository;
import com.AurumPro.repositories.componentes.ConvenioRepository;
import com.AurumPro.repositories.componentes.CustoRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import com.AurumPro.repositories.proposta.ItemPropostaRepository;
import com.AurumPro.repositories.proposta.PropostaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropostaServiceTest {

    @Mock
    private ConvenioRepository convenioRepository;

    @Mock
    private CustoRepository custoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private ItemPropostaRepository itemPropostaRepository;

    @Mock
    private PropostaRepository repository;

    @InjectMocks
    private PropostaService service;

    @Test
    void createProposta_withoutDesconto() {
        Long empresaId = 1L;
        Long clienteId = 1L;
        Long convenioId = 1L;
        List<Long> custoIds = Arrays.asList(1L, 2L);
        List<Long> itemPropostaIds = Arrays.asList(1L, 2L);

        CreatePropostaDTO dto = new CreatePropostaDTO(
                empresaId, clienteId, convenioId, custoIds, itemPropostaIds,
                TipoDesconto.NENHUM, false, BigDecimal.ZERO, BigDecimal.ZERO
        );

        Empresa empresa = mock(Empresa.class);
        ClienteEntity cliente = mock(ClienteEntity.class);
        Convenio convenio = mock(Convenio.class);

        Custo custo1 = mock(Custo.class);
        Custo custo2 = mock(Custo.class);
        List<Custo> custoList = Arrays.asList(custo1, custo2);

        ItemProposta item1 = mock(ItemProposta.class);
        ItemProposta item2 = mock(ItemProposta.class);
        List<ItemProposta> itemPropostaList = Arrays.asList(item1, item2);

        when(custo1.getValor()).thenReturn(new BigDecimal("100.00"));
        when(custo2.getValor()).thenReturn(new BigDecimal("200.00"));
        when(item1.getValorTotal()).thenReturn(new BigDecimal("150.00"));
        when(item2.getValorTotal()).thenReturn(new BigDecimal("250.00"));

        when(empresaRepository.findById(empresaId)).thenReturn(Optional.of(empresa));
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(convenioRepository.findById(convenioId)).thenReturn(Optional.of(convenio));
        when(custoRepository.findAllById(custoIds)).thenReturn(custoList);
        when(itemPropostaRepository.findAllById(itemPropostaIds)).thenReturn(itemPropostaList);

        service.createProposta(dto);

        verify(repository).save(argThat(proposta -> {
            assertEquals(empresa, proposta.getEmpresa());
            assertEquals(cliente, proposta.getCliente());
            assertEquals(convenio, proposta.getConvenio());
            assertEquals(custoList, proposta.getCustoList());
            assertEquals(itemPropostaList, proposta.getItemPropostaList());
            assertEquals(BigDecimal.ZERO, proposta.getValorDesconto());
            assertEquals(BigDecimal.ZERO, proposta.getPorcentagemDesconto());
            assertEquals(TipoDesconto.NENHUM, proposta.getTipoDesconto());
            assertEquals(new BigDecimal("700.00"), proposta.getValorTotal());
            return true;
        }));
    }

    @Test
    void createProposta_withDescontoValor() {
        Long empresaId = 1L;
        Long clienteId = 1L;
        Long convenioId = 1L;
        List<Long> custoIds = Arrays.asList(1L, 2L);
        List<Long> itemPropostaIds = Arrays.asList(1L, 2L);
        BigDecimal valorDesconto = new BigDecimal("100.00");

        CreatePropostaDTO dto = new CreatePropostaDTO(
                empresaId, clienteId, convenioId, custoIds, itemPropostaIds,
                TipoDesconto.VALOR, true, valorDesconto, BigDecimal.ZERO
        );

        Empresa empresa = mock(Empresa.class);
        ClienteEntity cliente = mock(ClienteEntity.class);
        Convenio convenio = mock(Convenio.class);

        Custo custo1 = mock(Custo.class);
        Custo custo2 = mock(Custo.class);
        List<Custo> custoList = Arrays.asList(custo1, custo2);

        ItemProposta item1 = mock(ItemProposta.class);
        ItemProposta item2 = mock(ItemProposta.class);
        List<ItemProposta> itemPropostaList = Arrays.asList(item1, item2);

        when(custo1.getValor()).thenReturn(new BigDecimal("100.00"));
        when(custo2.getValor()).thenReturn(new BigDecimal("200.00"));
        when(item1.getValorTotal()).thenReturn(new BigDecimal("150.00"));
        when(item2.getValorTotal()).thenReturn(new BigDecimal("250.00"));

        when(empresaRepository.findById(empresaId)).thenReturn(Optional.of(empresa));
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(convenioRepository.findById(convenioId)).thenReturn(Optional.of(convenio));
        when(custoRepository.findAllById(custoIds)).thenReturn(custoList);
        when(itemPropostaRepository.findAllById(itemPropostaIds)).thenReturn(itemPropostaList);

        service.createProposta(dto);

        verify(repository).save(argThat(proposta -> {
            assertEquals(empresa, proposta.getEmpresa());
            assertEquals(cliente, proposta.getCliente());
            assertEquals(convenio, proposta.getConvenio());
            assertEquals(custoList, proposta.getCustoList());
            assertEquals(itemPropostaList, proposta.getItemPropostaList());
            assertEquals(valorDesconto, proposta.getValorDesconto());
            assertEquals(BigDecimal.ZERO, proposta.getPorcentagemDesconto());
            assertEquals(TipoDesconto.VALOR, proposta.getTipoDesconto());
            assertEquals(new BigDecimal("600.00"), proposta.getValorTotal());
            return true;
        }));
    }

    @Test
    void createProposta_withDescontoPorcentagem() {
        Long empresaId = 1L;
        Long clienteId = 1L;
        Long convenioId = 1L;
        List<Long> custoIds = Arrays.asList(1L, 2L);
        List<Long> itemPropostaIds = Arrays.asList(1L, 2L);
        BigDecimal porcetagemDesconto = new BigDecimal("10");

        CreatePropostaDTO dto = new CreatePropostaDTO(
                empresaId, clienteId, convenioId, custoIds, itemPropostaIds,
                TipoDesconto.PORCENTAGEM, true, BigDecimal.ZERO, porcetagemDesconto
        );

        Empresa empresa = mock(Empresa.class);
        ClienteEntity cliente = mock(ClienteEntity.class);
        Convenio convenio = mock(Convenio.class);

        Custo custo1 = mock(Custo.class);
        Custo custo2 = mock(Custo.class);
        List<Custo> custoList = Arrays.asList(custo1, custo2);

        ItemProposta item1 = mock(ItemProposta.class);
        ItemProposta item2 = mock(ItemProposta.class);
        List<ItemProposta> itemPropostaList = Arrays.asList(item1, item2);

        when(custo1.getValor()).thenReturn(new BigDecimal("100.00"));
        when(custo2.getValor()).thenReturn(new BigDecimal("200.00"));
        when(item1.getValorTotal()).thenReturn(new BigDecimal("150.00"));
        when(item2.getValorTotal()).thenReturn(new BigDecimal("250.00"));

        when(empresaRepository.findById(empresaId)).thenReturn(Optional.of(empresa));
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(convenioRepository.findById(convenioId)).thenReturn(Optional.of(convenio));
        when(custoRepository.findAllById(custoIds)).thenReturn(custoList);
        when(itemPropostaRepository.findAllById(itemPropostaIds)).thenReturn(itemPropostaList);

        service.createProposta(dto);

        verify(repository).save(argThat(proposta -> {
            assertEquals(empresa, proposta.getEmpresa());
            assertEquals(cliente, proposta.getCliente());
            assertEquals(convenio, proposta.getConvenio());
            assertEquals(custoList, proposta.getCustoList());
            assertEquals(itemPropostaList, proposta.getItemPropostaList());
            assertEquals(BigDecimal.ZERO, proposta.getValorDesconto());
            assertEquals(porcetagemDesconto, proposta.getPorcentagemDesconto());
            assertEquals(TipoDesconto.PORCENTAGEM, proposta.getTipoDesconto());
            assertEquals(new BigDecimal("630.00"), proposta.getValorTotal());
            return true;
        }));
    }

    @Test
    void findAll() {
        Long empresaId = 1L;
        Proposta proposta1 = mock(Proposta.class);
        Proposta proposta2 = mock(Proposta.class);
        List<Proposta> propostaList = Arrays.asList(proposta1, proposta2);

        ClienteEntity cliente1 = mock(ClienteEntity.class);
        ClienteEntity cliente2 = mock(ClienteEntity.class);
        Convenio convenio1 = mock(Convenio.class);
        Convenio convenio2 = mock(Convenio.class);

        when(proposta1.getCliente()).thenReturn(cliente1);
        when(proposta1.getConvenio()).thenReturn(convenio1);
        when(proposta2.getCliente()).thenReturn(cliente2);
        when(proposta2.getConvenio()).thenReturn(convenio2);

        when(cliente1.getId()).thenReturn(1L);
        when(cliente1.getNome()).thenReturn("Cliente 1");
        when(cliente2.getId()).thenReturn(2L);
        when(cliente2.getNome()).thenReturn("Cliente 2");

        when(convenio1.getId()).thenReturn(1L);
        when(convenio1.getNome()).thenReturn("Convenio 1");
        when(convenio2.getId()).thenReturn(2L);
        when(convenio2.getNome()).thenReturn("Convenio 2");

        when(repository.findByEmpresaId(empresaId)).thenReturn(propostaList);

        List<PropostaDTO> propostaDTOList = service.findAll(empresaId);

        assertEquals(2, propostaDTOList.size());
        verify(repository, times(1)).findByEmpresaId(empresaId);
    }

    @Test
    void findPropostaByCliente() {
        Long empresaId = 1L;
        Long clienteId = 1L;
        FindPropostaByClienteDTO dto = new FindPropostaByClienteDTO(empresaId, clienteId);

        Proposta proposta = mock(Proposta.class);
        List<Proposta> propostaList = Arrays.asList(proposta);

        ClienteEntity cliente = mock(ClienteEntity.class);
        Convenio convenio = mock(Convenio.class);

        when(proposta.getCliente()).thenReturn(cliente);
        when(proposta.getConvenio()).thenReturn(convenio);
        when(cliente.getId()).thenReturn(clienteId);
        when(cliente.getNome()).thenReturn("Cliente Teste");
        when(convenio.getId()).thenReturn(1L);
        when(convenio.getNome()).thenReturn("Convenio Teste");

        when(repository.findByEmpresaIdAndClienteId(empresaId, clienteId)).thenReturn(propostaList);

        List<PropostaDTO> propostaDTOList = service.findPropostaByCliente(dto);

        assertEquals(1, propostaDTOList.size());
        verify(repository, times(1)).findByEmpresaIdAndClienteId(empresaId, clienteId);
    }
}