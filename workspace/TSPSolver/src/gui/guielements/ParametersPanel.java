package gui.guielements;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.text.NumberFormatter;

import tspsolver.algorithms.Algorithm;
import tspsolver.model.AlgorithmParameters;
import tspsolver.model.CockroachParameters;
import tspsolver.model.GeneticParameters;

class ParametersPanel extends JPanel implements ActionListener, PropertyChangeListener{
	private List<String> labels;
	private List<JFormattedTextField> values;
	private JProgressBar progressBar;
	private int parametersNumber;
	private JButton startStopButton;
	private Algorithm algorithm;

	public ParametersPanel(Algorithm algorithm, Icon icon) {
		super();
		this.algorithm = algorithm;
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		int i = 0;
		c.gridx = 0;
		c.gridy = i++;
		c.gridwidth = 2;
		c.insets = new Insets(10, 10, 40, 10);
		JLabel iconLabel;
		if (icon != null)
			iconLabel = new JLabel(algorithm.name(), icon, JLabel.CENTER);
		else
			iconLabel = new JLabel(algorithm.name(), JLabel.CENTER);
		this.add(iconLabel, c);
		c.gridwidth = 1;
		if (algorithm == Algorithm.Cockroach) {
			parametersNumber = 3;
			labels = new ArrayList<String>(parametersNumber);
			labels.add("iterations: ");
			labels.add("population: ");
			labels.add("steps: ");
		} else {
			parametersNumber = 5;
			labels = new ArrayList<String>(parametersNumber);
			labels.add("iteracje:");
			labels.add("liczba Osobnikow:");
			labels.add("licza krzy�owa�:");
			labels.add("liczba mutacji:");
			labels.add("liczba podmian: ");
		}

		values = new ArrayList<JFormattedTextField>(parametersNumber);

		c = new GridBagConstraints();
		int n = 0;
		for (String s : labels) {
			c.gridx = 0;
			c.gridy = i;
			this.add(new JLabel(s), c);
			c.gridx = 1;
			c.gridy = i++;
			NumberFormatter formatter = new NumberFormatter(NumberFormat.getIntegerInstance());
			formatter.setValueClass(Integer.class);
			JFormattedTextField tf = new JFormattedTextField(formatter);
			values.add(tf);
			tf.setValue(new Integer(0));
			
			tf.setColumns(7);
			this.add(tf, c);
		}

		startStopButton = new JButton("Start");
		startStopButton.addActionListener(this);
		c.gridx = 0;
		c.gridy = i++;
		c.gridwidth = 2;
		c.insets = new Insets(20, 10, 10, 10);
		this.add(startStopButton, c);

		progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		c.gridx = 0;
		c.gridy = i++;
		c.gridwidth = 2;
		c.insets = new Insets(10, 10, 10, 10); // top padding
		c.anchor = GridBagConstraints.PAGE_END;
		this.add(progressBar, c);
//		c.gridx=0;
//		c.gridy = i++;
//		c.gridwidth=2;
//		JLabel label = new JLabel("a                                                           a");
//		label.setMinimumSize(new Dimension(3000,10));
//		this.add(label, c);
	}

	public void addActionListener(ActionListener ac) {
		startStopButton.addActionListener(ac);
	}

	public AlgorithmParameters getParameters() {
		if (algorithm == Algorithm.Cockroach)
			return new CockroachParameters(
					((int)values.get(0).getValue()),
					((int)values.get(1).getValue()),
					((int)values.get(2).getValue()));
		else if (algorithm == Algorithm.Genetic)
			return new GeneticParameters(
					((int)values.get(0).getValue()),
					((int)values.get(1).getValue()),
					((int)values.get(2).getValue()),
					((int)values.get(3).getValue()),
					((int)values.get(4).getValue()));
		else return null;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand() == "Start"){
			startStopButton.setText("Stop");
			for (JFormattedTextField jftf : values){
				jftf.setEnabled(false);
			}
			progressBar.setMaximum((int)values.get(0).getValue());
			progressBar.setValue(0);
		}
		if (arg0.getActionCommand() == "Stop"){
			startStopButton.setText("Start");			
			for (JFormattedTextField jftf : values){
				jftf.setEnabled(true);
			}
//			progressBar.setValue(0);
		}
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "progress"){
			progressBar.setValue((int)evt.getNewValue());
		}
		if(evt.getPropertyName() == "state"){
			if (evt.getNewValue().toString() == "DONE"){
				startStopButton.setText("Start");
				progressBar.setValue(progressBar.getMaximum());
			}
			for (JFormattedTextField jftf : values){
				jftf.setEnabled(true);
			}
		}
	}
	
}