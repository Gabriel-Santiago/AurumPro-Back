package com.AurumPro.controllers.empresa;

import com.AurumPro.dtos.empresa.CreateEmpresaDTO;
import com.AurumPro.dtos.empresa.DeleteEmpresaDTO;
import com.AurumPro.dtos.empresa.EmpresaDTO;
import com.AurumPro.dtos.empresa.LoginEmpresaDTO;
import com.AurumPro.dtos.empresa.UpdateCepEmpresaDTO;
import com.AurumPro.dtos.empresa.UpdateEmailEmpresaDTO;
import com.AurumPro.dtos.empresa.UpdateTelefoneEmpresaDTO;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.security.RefreshToken;
import com.AurumPro.security.RefreshTokenService;
import com.AurumPro.security.TokenService;
import com.AurumPro.services.empresa.EmpresaService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/empresas")
@RestController
public class EmpresaController {

    private final EmpresaService service;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    public EmpresaController(EmpresaService service,
                             AuthenticationManager authenticationManager,
                             TokenService tokenService,
                             RefreshTokenService refreshTokenService) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginEmpresaDTO dto,
                                      HttpServletResponse response){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.senha())
        );

        Empresa empresa = (Empresa) authentication.getPrincipal();
        String accessToken = tokenService.generateToken(empresa);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(empresa);

        ResponseCookie accessCookie = ResponseCookie.from("access_token", accessToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(Duration.ofMinutes(10))
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", refreshToken.getToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/empresas/refresh")
                .maxAge(Duration.ofDays(3))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.accepted().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {

        Empresa empresa = (Empresa)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        refreshTokenService.deleteByEmpresa(empresa.getId());

        ResponseCookie deleteAccess = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();

        ResponseCookie deleteRefresh = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/empresas/refresh")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, deleteAccess.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, deleteRefresh.toString());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(HttpServletRequest request,
                                        HttpServletResponse response) {

        String refreshTokenValue = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("refresh_token"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new RuntimeException("Refresh token ausente"));

        RefreshToken oldToken =
                refreshTokenService.validateRefreshToken(refreshTokenValue);

        RefreshToken newRefreshToken =
                refreshTokenService.rotateRefreshToken(oldToken);

        String newAccessToken =
                tokenService.generateToken(newRefreshToken.getEmpresa());

        ResponseCookie accessCookie = ResponseCookie.from("access_token", newAccessToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", newRefreshToken.getToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/empresas/refresh")
                .maxAge(Duration.ofDays(3))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/auth/check")
    public ResponseEntity<Void> check(Authentication auth) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Void> createEmpresa(@RequestBody CreateEmpresaDTO dto) throws Exception {
        service.createEmpresa(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> findAllEmpresas() {
        return new ResponseEntity<>(service.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<EmpresaDTO> findById(@AuthenticationPrincipal Empresa empresa) {
        return new ResponseEntity<>(service.findById(empresa.getId()),
                HttpStatus.OK);
    }

    @GetMapping("/nome")
    public ResponseEntity<String> getNome(@AuthenticationPrincipal Empresa empresa) {
        return new ResponseEntity<>(empresa.getNome(), HttpStatus.OK);
    }

    @PatchMapping("/cep")
    public ResponseEntity<Void> updateCepEmpresa(@RequestBody UpdateCepEmpresaDTO dto) throws Exception {
        service.updateCepEmpresa(dto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/telefone")
    public ResponseEntity<Void> updateTelefoneEmpresa(@RequestBody UpdateTelefoneEmpresaDTO dto) {
        service.updateTelefoneEmpresa(dto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/email")
    public ResponseEntity<Void> updateEmailEmpresa(@RequestBody UpdateEmailEmpresaDTO dto) {
        service.updateEmailEmpresa(dto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteEmpresa(@RequestBody DeleteEmpresaDTO dto) {
        service.deleteEmpresa(dto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
