package com.AurumPro.utils;

import com.AurumPro.exceptions.utils.IdNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public class ValidadeId {

    public <T, ID> T validate(ID id, JpaRepository<T, ID> repository) {
        return repository.findById(id)
                .orElseThrow(IdNotFoundException::new);
    }
}
