import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ExampleApp {

	public static final String APP_NAME = "SI Analysis Test App";
	JFrame frame;

	public static void main(String[] args) {
		new ExampleApp();
	}

	public ExampleApp() {
		frame = new JFrame();

		// Makes sure that the program exits when the frame closes
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set size, title, and center frame
		frame.setTitle(APP_NAME);
		frame.setSize(300, 250);
		frame.setLocationRelativeTo(null);

        // Set up the content pane.
        addComponentsToPane(frame.getContentPane());
 
        // Resize and display
        frame.pack();
		frame.setVisible(true);
	}

	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;

	public static void addComponentsToPane(Container pane) {
		JLabel numScalar = new JLabel("num scalar");
		JLabel demScalar = new JLabel("dem scalar");
		JLabel numLabel = new JLabel("Numerator");
		JLabel demLabel = new JLabel("Denominator");
		
		JLabel results = new JLabel();
		
		JTextField numScalarField = new JTextField();
		JTextField demScalarField = new JTextField();
		JTextField numeratorField = new JTextField();
		JTextField denominatorField = new JTextField();
		
		GridLayout gridLayout = new GridLayout(0, 2);
		pane.setLayout(gridLayout);
		
		pane.add(numScalar);
		pane.add(numScalarField);
		pane.add(demScalar);
		pane.add(demScalarField);
		
		pane.add(numLabel);
		pane.add(numeratorField);
		pane.add(demLabel);
		pane.add(denominatorField);
		
		pane.add(results);
	}

}
