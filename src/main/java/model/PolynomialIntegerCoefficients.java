package model;

import java.util.ArrayList;
import java.util.Iterator;

/**the class is a model of a polynomial with integer coefficients, which is an array of monomials*/
public class PolynomialIntegerCoefficients {

    private ArrayList<MonomialIntegerCoefficients> polynomialIntCoeff;

    public PolynomialIntegerCoefficients(ArrayList<MonomialIntegerCoefficients> polynomialIntCoeff) {
        this.polynomialIntCoeff = polynomialIntCoeff;
    }

    public ArrayList<MonomialIntegerCoefficients> getPolynomialIntCoeff() {
        return polynomialIntCoeff;
    }

    public void setPolynomialIntCoeff(ArrayList<MonomialIntegerCoefficients> polynomialIntCoeff) {
        this.polynomialIntCoeff = polynomialIntCoeff;
    }

    /**the method is used to print/display a polynomial as a string*/
    @Override
    public String toString() {
        String polynomialString = "";
        /**the first monomial is different, because if it has positive coefficient, it should not be displayed with a '+' in front*/
        if (polynomialIntCoeff.get(0).getCoefficient() != 1 && polynomialIntCoeff.get(0).getCoefficient() != -1) {
            polynomialString = polynomialString + Integer.toString(polynomialIntCoeff.get(0).getCoefficient());
        }
        else { /**if coefficient is 1 is is not displayed*/
            if (polynomialIntCoeff.get(0).getCoefficient() < 0) {
                polynomialString = polynomialString + "-";
            }
            if(polynomialIntCoeff.get(0).getPower() == 0) {
                polynomialString = polynomialString + "1";
            }
        }
        /**power 1 and power 0 are different because they do not appear with "X^"*/
        if(polynomialIntCoeff.get(0).getPower() == 1) {
            polynomialString = polynomialString + "X ";
        }
        else if(polynomialIntCoeff.get(0).getPower() != 0)
            polynomialString = polynomialString + "X^" + Integer.toString(polynomialIntCoeff.get(0).getPower()) + " ";

        Iterator<MonomialIntegerCoefficients> iterator = polynomialIntCoeff.listIterator(1);
        while (iterator.hasNext()) {
            MonomialIntegerCoefficients monomial = iterator.next();
            if (monomial.getCoefficient() >= 0) {
                polynomialString = polynomialString + "+";
            }

            if (monomial.getCoefficient() != 1 && monomial.getCoefficient() != -1) {
                polynomialString = polynomialString + Integer.toString(monomial.getCoefficient());
            }
            else {
                if (monomial.getCoefficient() < 0 && monomial.getPower() != 0) {
                    polynomialString = polynomialString + "-";
                }

                if (monomial.getPower() == 0) { /**if power is 0 we only print the coefficient*/
                    polynomialString = polynomialString + Integer.toString(monomial.getCoefficient());
                }
            }
            if (monomial.getPower() != 0){
                if(monomial.getPower() == 1) {
                    polynomialString = polynomialString + "X ";
                }
                else
                    polynomialString = polynomialString + "X^" +Integer.toString(monomial.getPower()) + " ";
            }
        }
        return polynomialString;
    }
}
