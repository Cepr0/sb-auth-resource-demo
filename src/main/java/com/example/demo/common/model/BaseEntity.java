package com.example.demo.common.model;

import java.io.Serializable;

/**
 * Abstract base class for the entities which should work in JPA environment and support optimistic locking.<br/>
 *
 * It's based only on identifier of the entity - its {@code equals} and {@code hashCode} methods behave consistently
 * across all entity state transitions (according to this
 * <a href="https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/">
 *    Vlad Mihalcea's post</a>).<br/>
 *
 * This class inherit {@link Identifiable} so that its inheritors must contain an '{@code ID id}' property as well as its getter;
 * and this class contains an abstract method '{@code Short getVersion()}' so that its inheritors must contain
 * an '{@code short version}' property and its getter.
 *
 * @param <ID> type of the entity identifier
 */
public abstract class BaseEntity<ID extends Serializable> implements Serializable, Identifiable<ID> {

	public abstract Short getVersion();

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{id=" + getId() + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!getClass().isInstance(o)) return false;
		return getId() != null && getId().equals(((BaseEntity<?>) o).getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}
}
