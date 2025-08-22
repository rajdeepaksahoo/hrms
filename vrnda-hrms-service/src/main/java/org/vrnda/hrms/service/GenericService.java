package org.vrnda.hrms.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

public interface GenericService<T,Id extends Serializable> {

	T find(Id key);
	
	List<T> findAll();

	void save(T bean);

	void delete(T bean);

	void update(T bean);
	
	public <E> E convertJsonToValueObject(String jsonString, Class<E> clazz);
	
	public <E> List<E> convertJsonToValueObjectList(String jsonString, Class<E> clazz);
	
	public <S,D> D getValueMapper(Class<S> source,Class<D> Destination, S object);
	
	public <GEntity> Specification<GEntity> findAllWithOrgIdSpec();
	
	public <GEntity> Specification<GEntity> findByIdWithOrgIdSpec(String key,String value );
	
}
