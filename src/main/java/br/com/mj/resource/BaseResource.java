package br.com.mj.resource;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseResource<E, D> {

	@Autowired
	private ModelMapper modelMapper;

	private Class<E> entityClass;
	private Class<D> dtoClass;

	@SuppressWarnings("unchecked")
	public BaseResource() {
		entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		dtoClass = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	public D toDto(E entity) {
		return modelMapper.map(entity, dtoClass);
	}

	public Collection<D> toDto(Collection<E> entities) {
		return entities.stream().map((entity) -> modelMapper.map(entity, dtoClass)).collect(Collectors.toList());
	}

	public E toEntity(D dto) {
		return modelMapper.map(dto, entityClass);
	}
	
	public Collection<E> toEntity(Collection<D> dtos) {
		return dtos.stream().map((dto) -> modelMapper.map(dto, entityClass)).collect(Collectors.toList());
	}
}
