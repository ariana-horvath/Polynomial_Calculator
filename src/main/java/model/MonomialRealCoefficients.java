package model;

/**the class is the model of a monomial with real coefficient, subclass of Monomial*/
public class MonomialRealCoefficients extends Monomial {

    private double coefficient;

    public MonomialRealCoefficients(int power, double coefficient) {
        super(power);
        this.coefficient = coefficient;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
}
