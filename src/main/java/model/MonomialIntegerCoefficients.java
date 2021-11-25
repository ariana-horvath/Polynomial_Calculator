package model;

/**the class is the model of a monomial with integer coefficient, subclass of Monomial*/
public class MonomialIntegerCoefficients extends Monomial{

    private int coefficient;

    public MonomialIntegerCoefficients(int power, int coefficient) {
        super(power);
        this.coefficient = coefficient;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }
}
