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
package org.infinitest.intellij.idea.facet;

import org.infinitest.intellij.idea.*;
import org.infinitest.intellij.idea.greenhook.*;
import org.infinitest.intellij.idea.window.*;
import org.infinitest.intellij.plugin.*;
import org.infinitest.intellij.plugin.launcher.*;
import org.jdom.*;

import com.intellij.facet.*;
import com.intellij.facet.ui.*;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.module.*;
import com.intellij.openapi.util.*;
import com.intellij.openapi.wm.*;

public class InfinitestFacetConfiguration implements FacetConfiguration, InfinitestConfiguration {
	private static final String SCM_UPDATE_GREEN_HOOK = "scmUpdateGreenHook";

	private boolean scmUpdateGreenHook;
	private String licenseKey;

	private Module module;
	private InfinitestConfigurationListener listener;

	public InfinitestFacetConfiguration() {
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public FacetEditorTab[] createEditorTabs(FacetEditorContext editorContext, FacetValidatorsManager validatorsManager) {
		return new FacetEditorTab[] { new InfinitestFacetEditorTab(this) };
	}

	public void readExternal(Element element) throws InvalidDataException {
		try {
			Attribute scmUpdateAttribute = element.getAttribute(SCM_UPDATE_GREEN_HOOK);
			if (scmUpdateAttribute != null) {
				scmUpdateGreenHook = scmUpdateAttribute.getBooleanValue();
			}

			Element licenseElement = element.getChild("license");
			if (licenseElement != null) {
				licenseKey = licenseElement.getValue();
			}
		} catch (DataConversionException e) {
			throw new InvalidDataException(e);
		}
	}

	public void writeExternal(Element configElement) throws WriteExternalException {
		configElement.setAttribute(SCM_UPDATE_GREEN_HOOK, Boolean.toString(scmUpdateGreenHook));

		Element licenseElement = configElement.getChild("license");
		if (licenseElement == null) {
			licenseElement = new Element("license");
			configElement.addContent(licenseElement);
		}
		licenseElement.setText(licenseKey);
	}

	public boolean isScmUpdateEnabled() {
		return scmUpdateGreenHook;
	}

	public void setScmUpdateEnabled(boolean scmUpdateGreenHook) {
		this.scmUpdateGreenHook = scmUpdateGreenHook;
	}

	public InfinitestLauncher createLauncher() {
		InfinitestLauncherImpl launcher = new InfinitestLauncherImpl(new IdeaModuleSettings(module), new IdeaToolWindowRegistry(module.getProject()), new IdeaCompilationNotifier(module.getProject()), new IdeaSourceNavigator(module.getProject()), FileEditorManager.getInstance(module.getProject()), ToolWindowManager.getInstance(module.getProject()));

		if (isScmUpdateEnabled()) {
			launcher.addGreenHook(new ScmUpdater(module.getProject()));
		}

		return launcher;
	}

	public void registerListener(InfinitestConfigurationListener listener) {
		this.listener = listener;
	}

	public void updated() {
		if (listener != null) {
			listener.configurationUpdated(this);
		}
	}
}
