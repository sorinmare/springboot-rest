/*
 * Copyright (c) 2019.
 * @ Sorin Alex Mare
 * Email: sorin.mare@gmail.com
 */

package org.sorinmare.examples.springbootrestexample.rest.adapters;

import org.sorinmare.examples.springbootrestexample.constants.FormatConstants;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {
	@Override
	public ZonedDateTime unmarshal (final String strLocalDateTime) throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FormatConstants.DATE_TIME_ZONE_FMT);
		return ZonedDateTime.parse(strLocalDateTime, formatter);
	}

	@Override
	public String marshal (final ZonedDateTime localDateTime) throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FormatConstants.DATE_TIME_ZONE_FMT);
		return formatter.format(localDateTime);
	}
}
