package com.AurumPro.entities.base;

import com.AurumPro.entities.empresa.Empresa;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseComponenteEntity extends BaseEntity{

    @ManyToOne(optional = false)
    @JoinColumn(name = "empresaId")
    private Empresa empresa;
}
