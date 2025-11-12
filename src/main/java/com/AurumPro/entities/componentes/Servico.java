package com.AurumPro.entities.componentes;

import com.AurumPro.entities.base.BaseEmpresaEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "servico")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Servico extends BaseEmpresaEntity {

    @OneToMany(mappedBy = "servico")
    private List<MicroServico> microServicoList;
}
