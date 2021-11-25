package view;

import controller.MainController;
import exception.InputValidationFailedException;

import javax.swing.*;
import java.awt.*;

/**the view/GUI class of the Polynomial Calculator having a single frame
*the input is introduced as string in the text fields, one button for operation is
 pressed and the result is displayed in the result text field, or error messages are displayed*/
public class MainScreen extends JFrame{

    private JTextField polynomial1TextField;
    private JTextField polynomial2TextField;
    private JTextField resultTextField;

    private JButton addButton;
    private JButton subtractButton;
    private JButton multiplyButton;
    private JButton divideButton;
    private JButton deriveButton;
    private JButton integrateButton;
    private JButton exitButton;

    private MainController controller;

    public MainScreen() throws HeadlessException {
        initialize();
    }

    /**the method initializes the aspect of the frame: title, dimension, color*/
    public void initialize() {
        this.setTitle("Polynomial Calculator");
        this.setSize(500, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.setLocationRelativeTo(null);

        controller = new MainController();

        initializeForm(panel);
        initializeListeners();

        this.setContentPane(panel);
        this.setVisible(true);
        this.getContentPane().setBackground(new Color(194,220,253));
    }

    /**the method adds the elements to the frame - buttons, text fields and labels*/
    private void initializeForm(JPanel panel) {

        JLabel polynomial1Label = new JLabel("First polynomial: ");
        polynomial1Label.setBounds(30, 40, 300, 30);
        panel.add(polynomial1Label);

        JLabel polynomial2Label = new JLabel("Second polynomial: ");
        polynomial2Label.setBounds(30, 90, 300, 30);
        panel.add(polynomial2Label);

        JLabel resultLabel = new JLabel("Result: ");
        resultLabel.setBounds(30, 400, 300, 30);
        panel.add(resultLabel);

        JLabel noteLabel = new JLabel("*Introduce the polynomials in the form: an x^n + an-1 x^n-1 + ... + a1 x + a0");
        noteLabel.setBounds(30, 150, 500, 30);
        panel.add(noteLabel);

        JLabel noteLabel2 = new JLabel("or an X^n + an-1 X^n-1 + ... + a1 X + a0");
        noteLabel2.setBounds(30, 170, 500, 30);
        panel.add(noteLabel2);

        polynomial1TextField = new JTextField();
        polynomial1TextField.setBounds(150, 40, 300, 30);
        polynomial1TextField.setBackground(new Color(225, 237, 246));
        panel.add(polynomial1TextField);

        polynomial2TextField = new JTextField();
        polynomial2TextField.setBounds(150, 90, 300, 30);
        polynomial2TextField.setBackground(new Color(225, 237, 246));
        panel.add(polynomial2TextField);

        resultTextField = new JTextField();
        resultTextField.setBounds(150, 400, 300, 30);
        resultTextField.setBackground(new Color(225, 237, 246));
        panel.add(resultTextField);

        addButton = new JButton("Add");
        addButton.setBounds(30, 230, 110, 40);
        panel.add(addButton);

        subtractButton = new JButton("Subtract");
        subtractButton.setBounds(30, 300, 110, 40);
        panel.add(subtractButton);

        multiplyButton = new JButton("Multiply");
        multiplyButton.setBounds(190, 230, 110, 40);
        panel.add(multiplyButton);

        divideButton = new JButton("Divide");
        divideButton.setBounds(190, 300, 110, 40);
        panel.add(divideButton);

        deriveButton = new JButton("Derive");
        deriveButton.setBounds(340, 230, 110, 40);
        panel.add(deriveButton);

        integrateButton = new JButton("Integrate");
        integrateButton.setBounds(340, 300, 110, 40);
        panel.add(integrateButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(190, 500, 110, 40);
        panel.add(exitButton);
    }

    /**the method initializes listeners for the buttons*/
    private void initializeListeners() {
        exitButton.addActionListener(e-> {
            System.exit(0);
        });

        /**all the buttons for operations call the validateTextField method from controller, so that if the text field is empty
        an error message pop-up appears; after that it is called the method corresponding to the operation from controller;
        if everything is ok the result is added to the resultTextField
        also here are caught the exceptions threw by methods validateTextField and operation controllers*/
        deriveButton.addActionListener(e-> {
            try {
                controller.validateTextField(polynomial1TextField.getText());
                String result = controller.derivativeController(polynomial1TextField.getText());
                resultTextField.setText(result);
            } catch (InputValidationFailedException exception) {
                displayErrorMessage(exception);
            }
        });

        integrateButton.addActionListener(e-> {
            try {
                controller.validateTextField(polynomial1TextField.getText());
                String result = controller.integrationController(polynomial1TextField.getText());
                resultTextField.setText(result);
            } catch (InputValidationFailedException exception) {
                displayErrorMessage(exception);
            }
        });

        addButton.addActionListener(e-> {
            try {
                controller.validateTextField(polynomial1TextField.getText());
                controller.validateTextField(polynomial2TextField.getText());
                String result = controller.additionController(polynomial1TextField.getText(), polynomial2TextField.getText());
                resultTextField.setText(result);
            } catch (InputValidationFailedException exception) {
                displayErrorMessage(exception);
            }
        });

        subtractButton.addActionListener(e-> {
            try {
                controller.validateTextField(polynomial1TextField.getText());
                controller.validateTextField(polynomial2TextField.getText());
                String result = controller.subtractionController(polynomial1TextField.getText(), polynomial2TextField.getText());
                resultTextField.setText(result);
            } catch (InputValidationFailedException exception) {
                displayErrorMessage(exception);
            }
        });

        multiplyButton.addActionListener(e-> {
            try {
                controller.validateTextField(polynomial1TextField.getText());
                controller.validateTextField(polynomial2TextField.getText());
                String result = controller.multiplicationController(polynomial1TextField.getText(), polynomial2TextField.getText());
                resultTextField.setText(result);
            } catch (InputValidationFailedException exception) {
                displayErrorMessage(exception);
            }
        });

        divideButton.addActionListener(e-> {
            try {
                controller.validateTextField(polynomial1TextField.getText());
                controller.validateTextField(polynomial2TextField.getText());
                controller.validateDivision(polynomial2TextField.getText());
                String result = controller.divisionController(polynomial1TextField.getText(), polynomial2TextField.getText());
                resultTextField.setText(result);
            } catch (InputValidationFailedException exception) {
                displayErrorMessage(exception);
            }
        });
    }

    public void displayErrorMessage(Exception exception) {
        if (exception != null) {
            String message = exception.getMessage();
            UIManager UI = new UIManager();
            UI.put("OptionPane.background", new Color(194,220,253));
            UI.put("Panel.background", new Color(194,220,253));
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JTextField getPolynomial1TextField() {
        return polynomial1TextField;
    }

    public JTextField getPolynomial2TextField() {
        return polynomial2TextField;
    }

    public JTextField getResultTextField() {
        return resultTextField;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getSubtractButton() {
        return subtractButton;
    }

    public JButton getMultiplyButton() {
        return multiplyButton;
    }

    public JButton getDivideButton() {
        return divideButton;
    }

    public JButton getDeriveButton() {
        return deriveButton;
    }

    public JButton getIntegrateButton() {
        return integrateButton;
    }

}
