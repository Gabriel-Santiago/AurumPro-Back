package com.AurumPro.entities.componentes;

import com.AurumPro.entities.base.BaseComponenteEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "convenio")
@AllArgsConstructor
@Getter
@Setter
public class Convenio extends BaseComponenteEntity {

}
