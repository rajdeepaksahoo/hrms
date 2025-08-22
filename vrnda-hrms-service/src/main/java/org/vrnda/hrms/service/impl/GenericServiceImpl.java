package org.vrnda.hrms.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.vrnda.hrms.service.GenericService;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.googlecode.jmapper.JMapper;

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class GenericServiceImpl<T, Id extends Serializable> implements GenericService<T, Id> {

	private PagingAndSortingRepository<T, Id> genericRepository;

	public GenericServiceImpl(PagingAndSortingRepository<T, Id> typeRepository) {

		this.genericRepository = typeRepository;
	}

	public T find(Id key) {
		return null;
	}

	public void save(T bean) {
		genericRepository.save(bean);
	}
	public void saveAll(List<T> bean) {

		genericRepository.saveAll(bean);

	}


	public void delete(T bean) {

		genericRepository.delete(bean);

	}
	public void deleteAll(List<T> bean ) {
		genericRepository.deleteAll(bean);
	}
	
	public void deleteById(Id Id) {
		
		genericRepository.deleteById(Id);
		
	}

	public void update(T bean) {

		genericRepository.save(bean);

	}

	public List<T> findAll() {

		return (List<T>) genericRepository.findAll();
	}

	@Override
	public <E> E convertJsonToValueObject(String jsonString, Class<E> clazz) {

		Gson gson = new Gson();
		E parsedDto = gson.fromJson(jsonString, clazz);

		return parsedDto;

	}

	@Override
	public <E> List<E> convertJsonToValueObjectList(String jsonString, Class<E> clazz) {

		List<E> list = new ArrayList<E>();
		try {
			Gson gson = new Gson();
			JsonArray jsonArray = new JsonParser().parse(jsonString).getAsJsonArray();
			for (JsonElement jsonElement : jsonArray) {
				list.add(gson.fromJson(jsonElement, clazz));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public <S, D> D getValueMapper(Class<S> source, Class<D> destination, S object) {

		JMapper<D, S> mapper = new JMapper<>(destination, source);
		D result = mapper.getDestination(object);

		return result;
	}

	@Override
	public <GEntity> Specification<GEntity> findAllWithOrgIdSpec() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <GEntity> Specification<GEntity> findByIdWithOrgIdSpec(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

}