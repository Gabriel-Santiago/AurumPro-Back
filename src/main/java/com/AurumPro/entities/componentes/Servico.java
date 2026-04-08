package com.AurumPro.entities.componentes;

import com.AurumPro.entities.base.BaseComponenteEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "servico")
public class Servico extends BaseComponenteEntity {

    @OneToMany(mappedBy = "servico")
    private List<MicroServico> microServicoList;

    public Servico() {
    }

    public Servico(List<MicroServico> microServicoList) {
        this.microServicoList = microServicoList;
    }

    public List<MicroServico> getMicroServicoList() {
        return microServicoList;
    }

    public void setMicroServicoList(List<MicroServico> microServicoList) {
        this.microServicoList = microServicoList;
    }
}
