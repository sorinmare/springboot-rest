/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.persistence.repository;

import org.sorinmare.examples.springbootrestexample.persistence.model.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFooRepository extends JpaRepository<Foo, Long> {

}
