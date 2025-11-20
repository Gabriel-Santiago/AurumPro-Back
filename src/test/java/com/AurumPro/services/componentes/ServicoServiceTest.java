package com.AurumPro.services.componentes;

import com.AurumPro.dtos.componentes.servico.CreateServicoDTO;
import com.AurumPro.dtos.componentes.servico.ServicoDTO;
import com.AurumPro.entities.componentes.Servico;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.repositories.componentes.ServicoRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicoServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private ServicoRepository repository;

    @InjectMocks
    private ServicoService service;

    @Test
    void createServico() {
        Long empresaId = 1L;
        CreateServicoDTO dto = new CreateServicoDTO(empresaId, "Serviço de Consultoria");

        Empresa empresa = new Empresa();
        empresa.setId(empresaId);
        empresa.setNome("Empresa Teste");

        when(empresaRepository.findById(empresaId)).thenReturn(Optional.of(empresa));
        when(repository.save(any(Servico.class))).thenAnswer(invocation -> {
            Servico servico = invocation.getArgument(0);
            servico.setId(1L);
            return servico;
        });

        service.createServico(dto);

        verify(empresaRepository).findById(empresaId);

        ArgumentCaptor<Servico> servicoCaptor = ArgumentCaptor.forClass(Servico.class);
        verify(repository).save(servicoCaptor.capture());

        Servico savedServico = servicoCaptor.getValue();
        assertEquals(dto.nome(), savedServico.getNome());
        assertEquals(empresa, savedServico.getEmpresa());
    }

    @Test
    void findAll() {
        Servico servico1 = new Servico();
        servico1.setId(1L);
        servico1.setNome("Consultoria Estratégica");

        Servico servico2 = new Servico();
        servico2.setId(2L);
        servico2.setNome("Desenvolvimento Web");

        Servico servico3 = new Servico();
        servico3.setId(3L);
        servico3.setNome("Design UX/UI");

        List<Servico> servicos = List.of(servico1, servico2, servico3);

        when(repository.findAll()).thenReturn(servicos);

        List<ServicoDTO> result = service.findAll();

        assertNotNull(result);
        assertEquals(3, result.size());

        ServicoDTO dto1 = result.get(0);
        assertEquals("Consultoria Estratégica", dto1.nome());

        ServicoDTO dto2 = result.get(1);
        assertEquals("Desenvolvimento Web", dto2.nome());

        ServicoDTO dto3 = result.get(2);
        assertEquals("Design UX/UI", dto3.nome());

        verify(repository).findAll();
    }
}