package com.AurumPro.entities.base;

import com.AurumPro.entities.empresa.Empresa;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Setter;

@MappedSuperclass
public abstract class BaseClienteEntity extends BaseEntity{

    @Setter
    private String email;
    @Setter
    private String telefone;

    @Setter
    private String cep;
    @Setter
    private String rua;
    @Setter
    private String bairro;
    @Setter
    private String cidade;
    @Setter
    private String estado;
    @Setter
    private Integer numero;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empresaId")
    private Empresa empresa;
}
