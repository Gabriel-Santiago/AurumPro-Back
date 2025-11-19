package com.AurumPro.entities.componentes;

import com.AurumPro.entities.base.BaseComponenteEntity;
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
public class Servico extends BaseComponenteEntity {

    @OneToMany(mappedBy = "servico")
    private List<MicroServico> microServicoList;
}
