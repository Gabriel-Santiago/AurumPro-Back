package com.AurumPro.entities.empresa;

import com.AurumPro.entities.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "colaborador")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Colaborador extends BaseEntity {

    private String telefone;

    private String funcao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empresaId")
    private Empresa empresa;

}
