package com.AurumPro.entities.base;

import com.AurumPro.entities.empresa.Empresa;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.ManyToOne;

@MappedSuperclass
public abstract class BaseComponenteEntity extends BaseEntity{

    @ManyToOne(optional = false)
    @JoinColumn(name = "empresaId")
    private Empresa empresa;

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
