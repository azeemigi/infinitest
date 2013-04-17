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
package org.infinitest.eclipse;

import static com.google.common.collect.Lists.*;
import static java.util.Arrays.*;

import java.util.*;

import org.infinitest.*;
import org.infinitest.testrunner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class TestResultAggregator implements CoreLifecycleListener, TestResultsListener {
	private final List<TestResultsListener> listeners = newArrayList();

	@Autowired
	public void addListeners(AggregateResultsListener... resultsListeners) {
		listeners.addAll(asList(resultsListeners));
	}

	public void coreCreated(InfinitestCore core) {
		core.addTestResultsListener(this);
	}

	public void coreRemoved(InfinitestCore core) {
		core.removeTestResultsListener(this);
	}

	public void testCaseComplete(TestCaseEvent event) {
		for (TestResultsListener each : listeners) {
			each.testCaseComplete(event);
		}
	}

	public void testCaseStarting(TestEvent event) {
		for (TestResultsListener each : listeners) {
			each.testCaseStarting(event);
		}
	}
}
