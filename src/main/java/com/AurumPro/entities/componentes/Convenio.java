package com.AurumPro.entities.componentes;

import com.AurumPro.entities.base.BaseEmpresaEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "convenio")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Convenio extends BaseEmpresaEntity {

}
