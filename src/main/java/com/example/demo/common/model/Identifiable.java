package com.example.demo.common.model;

import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * Used to mark entities or DTOs that are identifiable by an ID of any type.
 *
 * @param <ID> type of the identifier
 */
public interface Identifiable<ID extends Serializable> extends Serializable {
	/**
	 * @return the identifier of the entity/DTOs or {@code null} if not available.
	 */
	@Nullable
    ID getId();
}