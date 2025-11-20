package com.AurumPro.services.componentes;

import com.AurumPro.dtos.componentes.convenio.ConvenioDTO;
import com.AurumPro.dtos.componentes.convenio.CreateConvenioDTO;
import com.AurumPro.entities.componentes.Convenio;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.exceptions.empresa.EmpresaNotFoundException;
import com.AurumPro.repositories.componentes.ConvenioRepository;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConvenioServiceTest {

    @Mock
    private ConvenioRepository repository;

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private ConvenioService service;

    @Test
    void createConvenio_whenEmpresaExists() {
        Long empresaId = 1L;
        CreateConvenioDTO dto = new CreateConvenioDTO(
                empresaId,
                "Convenio Teste"
        );

        Empresa empresa = new Empresa();
        empresa.setId(empresaId);
        empresa.setNome("Empresa Teste");

        Convenio convenio = new Convenio();
        convenio.setId(1L);
        convenio.setNome(dto.nome());
        convenio.setEmpresa(empresa);

        when(empresaRepository.findById(empresaId)).thenReturn(Optional.of(empresa));
        when(repository.save(any(Convenio.class))).thenReturn(convenio);

        service.createConvenio(dto);

        verify(empresaRepository).findById(empresaId);
        verify(repository).save(any(Convenio.class));

        verify(repository).save(argThat(convenio1 ->
                convenio.getNome().equals(dto.nome()) &&
                convenio.getEmpresa().equals(empresa)
        ));
    }

    @Test
    void createConvenio_whenEmpresaNotExists() {
        Long empresaId = 1L;
        CreateConvenioDTO dto = new CreateConvenioDTO(
                empresaId,
                "Convenio Teste"
        );

        when(empresaRepository.findById(empresaId)).thenReturn(Optional.empty());

        assertThrows(EmpresaNotFoundException.class, () -> service.createConvenio(dto));

        verify(empresaRepository).findById(empresaId);
        verify(repository, never()).save(any(Convenio.class));

    }

    @Test
    void findAll_whenConvenioExists() {
        Long empresaId = 1L;

        Empresa empresa = new Empresa();
        empresa.setId(empresaId);

        Convenio convenio1 = new Convenio();
        convenio1.setId(1L);
        convenio1.setNome("Convenio Saúde");
        convenio1.setEmpresa(empresa);

        Convenio convenio2 = new Convenio();
        convenio2.setId(2L);
        convenio2.setNome("Convenio Odonto");
        convenio2.setEmpresa(empresa);

        List<Convenio> convenioList = List.of(convenio1, convenio2);

        when(repository.findByEmpresaId(empresaId)).thenReturn(convenioList);

        List<ConvenioDTO> convenioDTOList = service.findAll(empresaId);

        assertNotNull(convenioDTOList);
        assertEquals(2, convenioDTOList.size());

        ConvenioDTO dto1 = convenioDTOList.get(0);
        assertEquals("Convenio Saúde", dto1.nome());

        ConvenioDTO dto2 = convenioDTOList.get(1);
        assertEquals("Convenio Odonto", dto2.nome());

        verify(repository).findByEmpresaId(empresaId);
    }
}