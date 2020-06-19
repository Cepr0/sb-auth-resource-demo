package com.example.demo.common.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;

/**
 * Base class for entities whose identifiers are of Long type.<br/>
 * Uses sequence "long_id_sequence" to generate identifiers (initialValue = 1, allocationSize = 50).<br/>
 * Has entity version, which allows optimistic locks to be used.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class LongIdEntity extends BaseEntity<Long> {
	/**
	 * Entity Identifier
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "long_id_sequence")
	@SequenceGenerator(name = "long_id_sequence")
	protected Long id;

	/**
	 * Entity version
	 */
	@Version
	protected Short version;

	public LongIdEntity(long id) {
		this.id = id;
	}

	public LongIdEntity() {
	}

	@Nullable
	public Short getVersion() {
		return this.version;
	}
}
