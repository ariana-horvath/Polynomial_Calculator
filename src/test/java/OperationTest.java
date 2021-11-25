import org.junit.Test;
import view.MainScreen;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class OperationTest {

    @Test
    public void derivativeTest() {
        MainScreen GUI = new MainScreen();
        GUI.getPolynomial1TextField().setText("3x^4+x^3-2x^2+9");
        GUI.getDeriveButton().doClick();
        assertEquals("12X^3 +3X^2 -4X ", GUI.getResultTextField().getText());
    }

    @Test
    public void integrationTest() {
        MainScreen GUI = new MainScreen();
        GUI.getPolynomial1TextField().setText("3x^4+x^3-2x^2+9");
        GUI.getIntegrateButton().doClick();
        assertEquals("0.60X^5 +0.25X^4 -0.67X^3 +9X ", GUI.getResultTextField().getText());
    }

    @Test
    public void additionTest() {
        MainScreen GUI = new MainScreen();
        GUI.getPolynomial1TextField().setText("3x^4+x^3-2x^2+9");
        GUI.getPolynomial2TextField().setText("-x^3+7x^2+4x-1");
        GUI.getAddButton().doClick();
        assertEquals("3X^4 +5X^2 +4X +8", GUI.getResultTextField().getText());
    }

    @Test
    public void additionTest2() {
        MainScreen GUI = new MainScreen();
        GUI.getPolynomial1TextField().setText("x^3-7x^2-4x+1");
        GUI.getPolynomial2TextField().setText("-x^3+7x^2+4x-1");
        GUI.getAddButton().doClick();
        assertEquals("0", GUI.getResultTextField().getText());
    }

    @Test
    public void subtractionTest() {
        MainScreen GUI = new MainScreen();
        GUI.getPolynomial1TextField().setText("3x^4+x^3-2x^2+9");
        GUI.getPolynomial2TextField().setText("x^3+7x^2+4x-1");
        GUI.getSubtractButton().doClick();
        assertEquals("3X^4 -9X^2 -4X +10", GUI.getResultTextField().getText());
    }

    @Test
    public void multiplicationTest() {
        MainScreen GUI = new MainScreen();
        GUI.getPolynomial1TextField().setText("x^3-2x^2+1");
        GUI.getPolynomial2TextField().setText("-x^2+2");
        GUI.getMultiplyButton().doClick();
        assertEquals("-X^5 +2X^4 +2X^3 -5X^2 +2", GUI.getResultTextField().getText());
    }

    @Test
    public void divisionTest() {
        MainScreen GUI = new MainScreen();
        GUI.getPolynomial1TextField().setText("x^3-2x^2+6x-5");
        GUI.getPolynomial2TextField().setText("x^2-1");
        GUI.getDivideButton().doClick();
        assertEquals("Quotient = X -2  Remainder = 7X -7", GUI.getResultTextField().getText());
    }

    @Test
    public void divisionTest2() {
        MainScreen GUI = new MainScreen();
        GUI.getPolynomial1TextField().setText("x^2-1");
        GUI.getPolynomial2TextField().setText("x^3-2x^2+6x-5");
        GUI.getDivideButton().doClick();
        assertEquals("Quotient = 0  Remainder = X^2 -1", GUI.getResultTextField().getText());
    }

    @Test
    public void divisionTest3() {
        MainScreen GUI = new MainScreen();
        GUI.getPolynomial1TextField().setText("x^2-4x+4");
        GUI.getPolynomial2TextField().setText("x-2");
        GUI.getDivideButton().doClick();
        assertEquals("Quotient = X -2  Remainder = 0", GUI.getResultTextField().getText());
    }
}
