/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.services;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public interface IOperations<T extends Serializable> {
	T findById (final long id);

	List<T> findAll ();

	long count ();

	Page<T> findPaginated (int page, int size);

	T save (final T entity);

	void delete (final T entity);

	void deleteById (final long entityId);
}
