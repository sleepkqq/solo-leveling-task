package com.sleepkqq.sololeveling.task.mapper;

import java.util.Collection;

public interface BaseMapper<E, D> {

  D toDto(E entity);

  E toEntity(D dto);

  Collection<D> toDtoList(Collection<E> entityList);

  Collection<E> toEntityList(Collection<D> dtoList);
}
