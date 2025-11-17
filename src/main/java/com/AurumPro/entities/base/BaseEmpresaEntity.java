package com.AurumPro.entities.base;

import com.AurumPro.entities.empresa.Empresa;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.ManyToOne;
import lombok.Setter;

@MappedSuperclass
@Setter
public abstract class BaseEmpresaEntity extends BaseEntity{

    @ManyToOne(optional = false)
    @JoinColumn(name = "empresaId")
    private Empresa empresa;
}
