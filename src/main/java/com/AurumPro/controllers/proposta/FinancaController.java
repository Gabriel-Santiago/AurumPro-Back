package com.AurumPro.controllers.proposta;

import com.AurumPro.dtos.proposta.FinancasDashboardDTO;
import com.AurumPro.dtos.proposta.PropostaDTO;
import com.AurumPro.dtos.proposta.UpdateStatusPropostaDTO;
import com.AurumPro.services.proposta.FinancaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/financas")
public class FinancaController {

    private final FinancaService financaService;

    public FinancaController(FinancaService financaService) {
        this.financaService = financaService;
    }

    @GetMapping("/dashboard/{empresaId}")
    public ResponseEntity<FinancasDashboardDTO> getDashboard(@PathVariable("empresaId") Long empresaId){
        return new ResponseEntity<>(financaService.getDashboard(empresaId), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<PropostaDTO> updateStatusProposta(@RequestBody UpdateStatusPropostaDTO dto){
        return new ResponseEntity<>(financaService.updateStatusProposta(dto), HttpStatus.OK);
    }
}
