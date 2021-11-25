package model;

import java.util.ArrayList;
import java.util.Iterator;

/**the class is a model of a polynomial with real coefficients, which is an array of monomials*/
public class PolynomialRealCoefficients {

    private ArrayList<MonomialRealCoefficients> polynomialRealCoeff;

    public PolynomialRealCoefficients(ArrayList<MonomialRealCoefficients> polynomialRealCoeff) {
        this.polynomialRealCoeff = polynomialRealCoeff;
    }

    public ArrayList<MonomialRealCoefficients> getPolynomialRealCoeff() {
        return polynomialRealCoeff;
    }

    public void setPolynomialRealCoeff(ArrayList<MonomialRealCoefficients> polynomialRealCoeff) {
        this.polynomialRealCoeff = polynomialRealCoeff;
    }

    /**the method is used to print/display a polynomial as a string*/
    public String toString() {
        String polynomialString = "";
        /**the first monomial is different, because if it has positive coefficient, it should not be displayed with a '+' in front*/
        if(polynomialRealCoeff.get(0).getCoefficient() != 1 && polynomialRealCoeff.get(0).getCoefficient() != -1) {
            /**if coefficient is integer it is displayed as so, without .00*/
            if (polynomialRealCoeff.get(0).getCoefficient() == (int)polynomialRealCoeff.get(0).getCoefficient()) {
                polynomialString = polynomialString + Integer.toString(((int) polynomialRealCoeff.get(0).getCoefficient()));
            } /**if it is real it is displayed with 2 decimals*/
            else
                polynomialString = polynomialString + String.format("%.2f", polynomialRealCoeff.get(0).getCoefficient());
        }
        else { /**if coefficient is 1 is is not displayed*/
            if (polynomialRealCoeff.get(0).getCoefficient() < 0) {
                polynomialString = polynomialString + "-";
            }
            if (polynomialRealCoeff.get(0).getPower() == 0) {
                polynomialString = polynomialString + "1";
            }
        }
        /**power 1 and power 0 are different because they do not appear with "X^"*/
        if(polynomialRealCoeff.get(0).getPower() == 1) {
            polynomialString = polynomialString + "X ";
        }
        else if(polynomialRealCoeff.get(0).getPower() != 0)
                 polynomialString = polynomialString + "X^" + Integer.toString(polynomialRealCoeff.get(0).getPower()) + " ";

        Iterator<MonomialRealCoefficients> iterator = polynomialRealCoeff.listIterator(1);
        while(iterator.hasNext()) {
            MonomialRealCoefficients monomial = iterator.next();
            if(monomial.getCoefficient() >= 0) {
                polynomialString = polynomialString + "+";
            }

            if(monomial.getCoefficient() != 1 && monomial.getCoefficient() != -1) {
                /**if coefficient is integer it is displayed as so, without .00*/
                if (monomial.getCoefficient() == (int)monomial.getCoefficient()) {
                    polynomialString = polynomialString + Integer.toString(((int) monomial.getCoefficient()));
                }
                else /**if it is real it is displayed with 2 decimals*/
                    polynomialString = polynomialString + String.format("%.2f", monomial.getCoefficient());
            }
            else {
                if (monomial.getCoefficient() < 0 && monomial.getPower() != 0) {
                    polynomialString = polynomialString + "-";
                }

                if (monomial.getPower() == 0) { /**if power is 0 we only print the coefficient*/
                    /**if coefficient is integer it is displayed as so, without .00*/
                    if (monomial.getCoefficient() == (int)monomial.getCoefficient()) {
                        polynomialString = polynomialString + Integer.toString(((int) monomial.getCoefficient()));
                    }
                    else /**if it is real it is displayed with 2 decimals*/
                        polynomialString = polynomialString + String.format("%.2f", monomial.getCoefficient());
                }
            }
            if(monomial.getPower() != 0){
                if(monomial.getPower() == 1) {
                    polynomialString = polynomialString + "X ";
                }
                else
                polynomialString = polynomialString + "X^" + Integer.toString(monomial.getPower()) + " ";
            }
        }
        return polynomialString;
    }
}
