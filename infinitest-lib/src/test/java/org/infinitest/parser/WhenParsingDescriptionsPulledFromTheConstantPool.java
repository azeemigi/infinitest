/*
 * Infinitest, a Continuous Test Runner.
 *
 * Copyright (C) 2010-2013
 * "Ben Rady" <benrady@gmail.com>,
 * "Rod Coffin" <rfciii@gmail.com>,
 * "Ryan Breidenbach" <ryan.breidenbach@gmail.com>
 * "David Gageot" <david@gageot.net>, et al.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.infinitest.parser;

import static org.infinitest.parser.DescriptorParser.*;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

/**
 * http://www.murrayc.com/learning/java/java_classfileformat.shtml#
 * TypeDescriptors <br>
 * RISK Do we care about MethodDescriptors?
 */
public class WhenParsingDescriptionsPulledFromTheConstantPool {
	@Test
	public void shouldConvertFieldDescriptorPrimitiveTypesToObject() {
		assertEquals(Object.class.getName(), parse("B"));
		assertEquals(Object.class.getName(), parse("C"));
		assertEquals(Object.class.getName(), parse("D"));
		assertEquals(Object.class.getName(), parse("F"));
		assertEquals(Object.class.getName(), parse("I"));
		assertEquals(Object.class.getName(), parse("J"));
		assertEquals(Object.class.getName(), parse("S"));
		assertEquals(Object.class.getName(), parse("Z"));
	}

	@Test
	public void shouldConvertArraysToSimpleClasses() {
		assertEquals("com.fake.Product", parse("[[Lcom/fake/Product"));
	}

	@Test
	public void shouldConvertClasses() {
		assertEquals(List.class.getName(), parse("Ljava/util/List"));
	}

	private String parse(String descriptor) {
		return parseClassNameFromConstantPoolDescriptor(descriptor);
	}
}
