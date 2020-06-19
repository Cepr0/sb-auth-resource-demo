package com.example.demo.common.mapper;

import com.example.demo.common.dto.BaseRequest;
import com.example.demo.common.dto.BaseResponse;
import com.example.demo.common.model.BaseEntity;
import lombok.NonNull;
import org.mapstruct.*;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Mapper interface that used to map entities and their input (request) and output (response) DTOs.
 * <p/>
 * This interface relays on <a href = "http://mapstruct.org/">MapStruct</a> framework.
 * The inheritors of this interface must relay on that framework as well and
 * use the mapper configuration defined on this interface (i.e. {@code @Mapper(config = CrudMapper.class)}).
 * This configuration defines the following:
 * <ul>
 * 	<li>the target bean type always instantiated and returned regardless of whether the source is NULL or not</li>
 *    <li>the source property values are always checked for null</li>
 *    <li>if a source bean property equals null the target bean property will be ignored and retain its existing value</li>
 * </ul>
 *
 * @param <T> type of the entity which extends {@link BaseEntity}
 * @param <Q> type of the input (request) DTO
 * @param <S> type of the output (response) DTO
 */
@MapperConfig(
        nullValueMappingStrategy = RETURN_DEFAULT,
        nullValueCheckStrategy = ALWAYS,
        nullValuePropertyMappingStrategy = IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public abstract class BaseMapper<T extends BaseEntity<ID>, ID extends Serializable, Q extends BaseRequest, S extends BaseResponse<ID>> {

    private final NotNullChecker notNullChecker = new NotNullChecker();

    /**
     * Maps a given input (request) DTO to the entity.
     *
     * @param request input (request) DTO, must not be {@code null}
     * @return the entity, never {@code null}
     */
    public abstract T toEntity(Q request);

    /**
     * Maps a given entity to the output (response) DTO.
     *
     * @param entity to be mapped to the output DTO, must not be {@code null}
     * @return output (response) DTO, never {@code null}
     */
    public abstract S toResponse(T entity);

    /**
     * Maps an input (request) DTO to the entity to be updated.
     *
     * @param request input (request) DTO, must not be {@code null}
     * @param target updated entity, must not be {@code null}
     * @return updated entity, never {@code null}
     */
    public abstract T update(Q request, @MappingTarget T target);

    /**
     * Maps one entity to another to be updated.
     *
     * @param source updating entity, must not be {@code null}
     * @param target updated entity, must not be {@code null}
     * @return updated entity, never {@code null}
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract T update(T source, @MappingTarget T target);

    /**
     * Maps an entity to its id.
     *
     * @param entity mapping entity, must not be {@code null}
     * @return id of the given entity
     */
    @Nullable
    protected ID toId(@NonNull BaseEntity<ID> entity) {
        return entity.getId();
    }

    /**
     * Convert a collection of entities to {@link List} of response DTOs.
     *
     * @param entities must not be {@code null}
     * @return {@link List} of response DTOs
     */
    public List<S> toResponseList(@NonNull Collection<T> entities) {
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    /**
     * Convert a collection of entities to {@link Set} of response DTOs.
     *
     * @param entities must not be {@code null}
     * @return {@link Set} of response DTOs
     */
    public Set<S> toResponseSet(@NonNull Collection<T> entities) {
        return entities.stream().map(this::toResponse).collect(Collectors.toSet());
    }

    /**
     * Convert a collection of request DTOs to {@link List} of entities.
     * @param requests must not be {@code null}
     * @return {@link List} of entities
     */
    public List<T> toEntityList(@NonNull Collection<Q> requests) {
        return requests.stream().map(this::toEntity).collect(Collectors.toList());
    }

    /**
     * Convert a collection of request DTOs to {@link Set} of entities.
     * @param requests must not be {@code null}
     * @return {@link Set} of entities
     */
    public Set<T> toEntitySet(@NonNull Collection<Q> requests) {
        return requests.stream().map(this::toEntity).collect(Collectors.toSet());
    }
}
