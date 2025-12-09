package com.AurumPro.controllers.proposta;

import com.AurumPro.dtos.proposta.PropostaDocumentoDTO;
import com.AurumPro.services.proposta.DocumentoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/documento")
public class DocumentoController {

    private final DocumentoService documentoService;

    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropostaDocumentoDTO> getDadosDocumento(@PathVariable Long id) {
        return new ResponseEntity<>(documentoService.getDadosParaDocumento(id), HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocumentoDoc(@PathVariable Long id) {
        PropostaDocumentoDTO propostaDocumento = documentoService.getDadosParaDocumento(id);
        byte[] documento = documentoService.gerarDocumentoWord(propostaDocumento);

        String nomeCliente = propostaDocumento.cliente().nome().replaceAll("\\s+", "_");
        String dataAtual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmm"));
        String nomeArquivo = String.format("Proposta_%s_%s.docx", nomeCliente, dataAtual);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + nomeArquivo + "\"")
                .body(documento);
    }

    @GetMapping("/visual/{id}")
    public ResponseEntity<Map<String, String>> getConteudoDocumentoVisual(@PathVariable Long id) {
        PropostaDocumentoDTO propostaDocumento = documentoService.getDadosParaDocumento(id);

        String conteudo = documentoService.gerarConteudoVisual(propostaDocumento);

        return ResponseEntity.ok(Map.of("conteudo", conteudo));
    }
}
