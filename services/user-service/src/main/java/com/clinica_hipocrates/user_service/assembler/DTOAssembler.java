package com.clinica_hipocrates.user_service.assembler;

import java.util.List;

public interface DTOAssembler<E, D> {
    D toModel(E entity);
    E toEntity(D dto);

    default List<D> toModelList(List<E> entities) {
        return entities.stream()
                .map(this::toModel)
                .toList();
    }

    default List<E> toEntityList(List<D> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .toList();
    }
}
