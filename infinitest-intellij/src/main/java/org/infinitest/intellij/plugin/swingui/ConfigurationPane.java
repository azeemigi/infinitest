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
package org.infinitest.intellij.plugin.swingui;

import static java.awt.FlowLayout.*;
import static javax.swing.Box.*;

import java.awt.*;

import javax.swing.*;

public class ConfigurationPane extends JPanel {
	private static final long serialVersionUID = -1L;
	private JRadioButton enableScmButton;

	public ConfigurationPane() {
		setLayout(new BorderLayout());

		JTabbedPane pane = new JTabbedPane();
		pane.addTab("General", createGeneralTab());
		add(pane, BorderLayout.CENTER);
	}

	private Component createGeneralTab() {
		JPanel panel = new JPanel(new FlowLayout(LEFT));

		Box box = createHorizontalBox();

		box.add(logo());
		box.add(createHorizontalStrut(20));
		box.add(scmUpdatePane());

		panel.add(box);

		return panel;
	}

	private JComponent logo() {
		ImageIcon logo = new ImageIcon(getClass().getResource("/infinitestMed.png"));
		return new JLabel(logo);
	}

	private JComponent scmUpdatePane() {
		Box box = createVerticalBox();

		JLabel heading = new JLabel("Green Hooks");
		heading.setFont(new Font(null, Font.BOLD, 14));

		box.add(heading);
		box.add(createVerticalStrut(10));
		box.add(new JLabel("Automatically perform SCM update when tests pass?"));
		box.add(optionsBox());

		return box;
	}

	private Box optionsBox() {
		ButtonGroup options = new ButtonGroup();

		enableScmButton = new JRadioButton("Yes, please");
		options.add(enableScmButton);

		JRadioButton noButton = new JRadioButton("No, thanks");
		noButton.setSelected(true);
		options.add(noButton);

		Box optionsBox = createVerticalBox();
		optionsBox.add(enableScmButton);
		optionsBox.add(noButton);

		return optionsBox;
	}

	public boolean isScmUpdateEnabled() {
		return enableScmButton.isSelected();
	}

	public void setScmUpdateEnabled(boolean scmUpdateEnabled) {
		enableScmButton.setSelected(scmUpdateEnabled);
	}
}
