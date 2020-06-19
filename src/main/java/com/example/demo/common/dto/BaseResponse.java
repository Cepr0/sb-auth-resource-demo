package com.example.demo.common.dto;

import com.example.demo.common.model.Identifiable;
import org.springframework.lang.NonNull;

import java.io.Serializable;

/**
 * Interface for all output (response) DTOs. Such DTOs are supposed to have an identifier.
 *
 * @param <ID> identifier type of the related entity.
 */
public interface BaseResponse<ID extends Serializable> extends Identifiable<ID>, Serializable {
    @NonNull
    @Override
    ID getId();
}
