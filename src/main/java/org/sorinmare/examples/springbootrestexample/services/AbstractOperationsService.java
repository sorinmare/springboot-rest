/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
public abstract class AbstractOperationsService<T extends Serializable> implements IOperations<T> {

	@Override
	@Transactional (readOnly = true)
	public T findById (final long id) {
		return getRepository().findById(id).orElse(null);
	}

	@Override
	@Transactional (readOnly = true)
	public List<T> findAll () {
		return StreamSupport.stream(getRepository().findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public long count () {
		return getRepository().count();
	}

	@Override
	public Page<T> findPaginated (final int page, final int size) {
		return getRepository().findAll(PageRequest.of(page, size));
	}

	@Override
	public T save (final T entity) {
		return getRepository().save(entity);
	}

	@Override
	public void delete (final T entity) {
		getRepository().delete(entity);
	}

	@Override
	public void deleteById (final long entityId) {
		getRepository().deleteById(entityId);
	}

	protected abstract PagingAndSortingRepository<T, Long> getRepository ();

}
