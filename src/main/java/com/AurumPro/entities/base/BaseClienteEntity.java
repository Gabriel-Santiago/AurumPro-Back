package com.AurumPro.entities.base;

import com.AurumPro.entities.empresa.Empresa;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public abstract class BaseClienteEntity extends BaseEntity{

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String telefone;

    @Getter
    @Setter
    private String cep;

    @Getter
    @Setter
    private String rua;

    @Getter
    @Setter
    private String bairro;

    @Getter
    @Setter
    private String cidade;

    @Getter
    @Setter
    private String estado;

    @Getter
    @Setter
    private Integer numero;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empresaId")
    private Empresa empresa;
}
