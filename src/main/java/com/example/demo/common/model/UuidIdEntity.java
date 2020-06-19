package com.example.demo.common.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.UUID;

/**
 * Base class for entities whose identifiers are of UUID type.<br/>
 * Has entity version, which allows optimistic locks to be used.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class UuidIdEntity extends BaseEntity<UUID> {
	/**
	 * Entity Identifier
	 */
	@Id
	@GeneratedValue(generator = "uuid2")
	protected UUID id;

	/**
	 * Entity version
	 */
	@Version
	protected Short version;

	public UuidIdEntity(UUID id) {
		this.id = id;
	}

	public UuidIdEntity() {
	}

	@Nullable
	public Short getVersion() {
		return this.version;
	}
}
