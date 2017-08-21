import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class App {
	
	public static final String APP_NAME = "SI Analysis Test App";
	JFrame frame;
	
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static double screenWidth = screenSize.getWidth();
	static double screenHeight = screenSize.getHeight();
	
	public static void main(String[] args) {
	    DataLoader.initializeUnits();
	    DataLoader.initializePrefixes();
		new App();
	}

	public App() {
		frame = new JFrame();

		// Makes sure that the program exits when the frame closes
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set size and title
		frame.setTitle(APP_NAME);
		frame.setPreferredSize(new Dimension(500, 500));

        // Set up the content pane.
        addComponentsToPane(frame.getContentPane());
 
        // Resize, center, and display
        frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;

	public static void addComponentsToPane(Container pane) {
		
		JPanel fieldPanel = new JPanel();
		
		JLabel numScalar = new JLabel("num scalar");
		JLabel demScalar = new JLabel("dem scalar");
		JLabel numLabel = new JLabel("Numerator");
		JLabel demLabel = new JLabel("Denominator");
		
		JLabel results = new JLabel("results");
		JButton button = new JButton("Start");
		JButton clear = new JButton("Clear");

		JTextField numScalarField = new JTextField();
		JTextField demScalarField = new JTextField();
		JTextField numeratorField = new JTextField();
		JTextField denominatorField = new JTextField();
		
		GridLayout gridLayout = new GridLayout(0, 2);
		fieldPanel.setLayout(gridLayout);
		
		customAdd(fieldPanel, numScalar);
		customAdd(fieldPanel, numScalarField);
		customAdd(fieldPanel, demScalar);
		customAdd(fieldPanel, demScalarField);
		
		customAdd(fieldPanel, numLabel);
		customAdd(fieldPanel, numeratorField);
		customAdd(fieldPanel, demLabel);
		customAdd(fieldPanel, denominatorField);
		
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		pane.add(fieldPanel);
		
		customAdd(pane, button);
		customAdd(pane, clear);
		customAdd(pane, results);
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DimensionObject obj = new UnitParser().parse(numeratorField.getText());
		        String text = obj.toExtendedString() + " = " + obj.getMagnitude() + " " + obj.output();
		        
				results.setText(text);
			}
		
		});
		
	}

	public static void customAdd(Container pane, JComponent component) {
		component.setFont(new Font("Arial", Font.PLAIN, (int) screenWidth / 85));
		pane.add(component);
	}
	
}
