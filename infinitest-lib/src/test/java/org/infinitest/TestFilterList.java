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
package org.infinitest;

import static org.junit.Assert.*;

import java.io.*;

import org.infinitest.filter.*;
import org.junit.*;

public class TestFilterList {
	private final static String TEST_FILTER_LIST_REGEX = "org\\.infinitest\\.TestFilterList";

	@Test
	public void testFilterList() throws IOException {
		File file = File.createTempFile("filter", "conf");
		file.deleteOnExit();
		PrintWriter writer = new PrintWriter(file);
		try {
			writer.println(TEST_FILTER_LIST_REGEX);
			writer.print("!foo.bar");
			writer.print("#foo.bar");
		} finally {
			writer.close();
		}
		TestFilter filters = new RegexFileFilter(file);
		assertTrue(filters.match(TestFilterList.class.getName()));
	}

	@Test
	public void shouldIgnoreAllBlankLines() {
		RegexFileFilter filter = new RegexFileFilter();
		filter.addFilter("");
		filter.addFilter("");
		assertFalse(filter.match("MyClassName"));
	}

	@Test
	public void testJMockClasses() {
		RegexFileFilter filterList = new RegexFileFilter();
		filterList.addFilter("org.jmock.test.acceptance.junit4.testdata.JUnit4TestWithNonPublicBeforeMethod");
		assertTrue(filterList.match("org.jmock.test.acceptance.junit4.testdata.JUnit4TestWithNonPublicBeforeMethod"));
	}

	@Test
	public void testFiltering() {
		RegexFileFilter filters = new RegexFileFilter();
		filters.addFilter("org\\.infinitest\\..*");
		assertFalse(filters.match(com.fakeco.fakeproduct.TestFakeProduct.class.getName()));
		assertFalse(filters.match(com.fakeco.fakeproduct.FakeProduct.class.getName()));
		assertTrue(filters.match(org.infinitest.changedetect.WhenLookingForClassFiles.class.getName()));
	}

	@Test
	public void testSingleTest() {
		RegexFileFilter filters = new RegexFileFilter();
		filters.addFilter(TEST_FILTER_LIST_REGEX);
		assertFalse(filters.match(RegexFileFilter.class.getName()));
		assertTrue("Single test should match regex", filters.match(TestFilterList.class.getName()));
	}

	@Test
	public void testLeadingWildcard() {
		RegexFileFilter filters = new RegexFileFilter();
		filters.addFilter(".*TestFilterList");
		assertTrue(filters.match(TestFilterList.class.getName()));
	}

	@Test
	public void testTrailingWildcard() {
		RegexFileFilter filters = new RegexFileFilter();
		filters.addFilter("org\\..*");
		assertTrue(filters.match(TestFilterList.class.getName()));
	}
}
