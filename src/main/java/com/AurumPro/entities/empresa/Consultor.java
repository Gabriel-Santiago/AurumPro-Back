package com.AurumPro.entities.empresa;

import com.AurumPro.entities.base.BaseEntity;
import com.AurumPro.enums.TipoConsultor;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "consultor")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Consultor extends BaseEntity {

    private String telefone;

    @Enumerated(EnumType.STRING)
    private TipoConsultor tipoConsultor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empresaId")
    private Empresa empresa;

}
