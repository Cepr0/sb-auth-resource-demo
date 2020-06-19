package com.example.demo.common.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * An utility class intended for use with mappers, inheritors of {@link BaseMapper}, for checking the non-null value
 * of arguments of their methods, for example:
 * <pre>{@code
 *    @Mapper(config = BaseMapper.class, uses = NotNullChecker.class)
 *    public abstract class ModelMapper extends BaseMapper <Model, Integer, ModelRequest, ModelResponse> {
 *    }
 * }</pre>
 */
public class NotNullChecker {
    @BeforeMapping
    public void checkSource(Serializable source) {
        Assert.notNull(source, "Mapper source argument must not be null");
    }

    @BeforeMapping
    public void checkTarget(@MappingTarget Serializable target) {
        Assert.notNull(target, "Mapper target argument must not be null");
    }
}
