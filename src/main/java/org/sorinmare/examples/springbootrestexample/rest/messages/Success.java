/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.rest.messages;

import io.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.XmlRootElement;

@ApiModel (description = "Standard success element of a \\<response\\>")
@XmlRootElement
public class Success {
}
