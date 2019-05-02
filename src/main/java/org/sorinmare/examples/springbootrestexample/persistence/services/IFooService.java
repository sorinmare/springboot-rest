/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.persistence.services;

import org.sorinmare.examples.springbootrestexample.persistence.model.Foo;
import org.sorinmare.examples.springbootrestexample.services.IOperations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFooService extends IOperations<Foo> {
	Page<Foo> findPaginated (Pageable pageable);
}
