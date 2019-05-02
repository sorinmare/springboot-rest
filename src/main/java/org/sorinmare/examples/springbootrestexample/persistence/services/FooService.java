/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.persistence.services;

import org.sorinmare.examples.springbootrestexample.persistence.model.Foo;
import org.sorinmare.examples.springbootrestexample.persistence.repository.IFooRepository;
import org.sorinmare.examples.springbootrestexample.services.AbstractOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FooService extends AbstractOperationsService<Foo> implements IFooService {
	@Autowired
	private IFooRepository repository;

	@Override
	protected PagingAndSortingRepository<Foo, Long> getRepository () {
		return repository;
	}

	@Override
	public Page<Foo> findPaginated (Pageable pageable) {
		return repository.findAll(pageable);
	}
}
