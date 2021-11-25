package model;

import java.util.*;

/**class with operations on polynomial with real coefficients, used only for integration and division*/
public class OperationRealCoefficients {

    /**integrates a polynomial and returns the polynomial result with real coefficients*/
    public PolynomialRealCoefficients integration(PolynomialIntegerCoefficients polynomial) {
        ArrayList<MonomialRealCoefficients> polynomialReal = new ArrayList<>();

        /**make a new polynomial with real coefficients from the input with integer coefficients,
         so that the coefficients can be divided directly*/
        for (MonomialIntegerCoefficients monomial : polynomial.getPolynomialIntCoeff()) {
            polynomialReal.add(new MonomialRealCoefficients(monomial.getPower(), monomial.getCoefficient()));
        }

        /**the coefficients of all monomials are divided by the (power + 1) and the powers are increased by 1*/
        for (MonomialRealCoefficients monomial : polynomialReal) {
            int newPower = monomial.getPower() + 1;
            monomial.setPower(newPower);

            double newCoefficient = monomial.getCoefficient() / monomial.getPower();
            monomial.setCoefficient(newCoefficient);
        }
        return new PolynomialRealCoefficients(polynomialReal);
    }

    /**divides polynomial1 by polynomial2 and returns a list of 2 polynomials, the quotient and the remainder*/
    public ArrayList<PolynomialRealCoefficients> division(PolynomialIntegerCoefficients polynomial1,
                                                          PolynomialIntegerCoefficients polynomial2){
        /**make new polynomials with real coefficients from the input with integer coefficients,
         so that the coefficients can be divided directly*/
        ArrayList<MonomialRealCoefficients> monomialsReal1 = new ArrayList<>();
        for (MonomialIntegerCoefficients monomial : polynomial1.getPolynomialIntCoeff()) {
            monomialsReal1.add(new MonomialRealCoefficients(monomial.getPower(), monomial.getCoefficient()));
        }
        /**the remainder will be at each step the new dividend*/
        PolynomialRealCoefficients remainder = new PolynomialRealCoefficients(monomialsReal1);

        ArrayList<MonomialRealCoefficients> monomialsReal2 = new ArrayList<>();
        for (MonomialIntegerCoefficients monomial : polynomial2.getPolynomialIntCoeff()) {
            monomialsReal2.add(new MonomialRealCoefficients(monomial.getPower(), monomial.getCoefficient()));
        }
        PolynomialRealCoefficients quotient = new PolynomialRealCoefficients(monomialsReal2);

        ArrayList<MonomialRealCoefficients> monomialsQuotient = new ArrayList<>();
        ArrayList<PolynomialRealCoefficients> result = new ArrayList<>();

        /**if the degree of the first polynomial is smaller than the degree of the second polynomial,
         * the quotient is 0 and the remainder is the first polynomial, like the division of a number by a greater number*/
        if(remainder.getPolynomialRealCoeff().get(0).getPower() < quotient.getPolynomialRealCoeff().get(0).getPower()) {
            monomialsQuotient.add(new MonomialRealCoefficients(0,0));
            result.add(new PolynomialRealCoefficients(monomialsQuotient));
            result.add(remainder);
            return result;
        }
        else {
            int degreeQ = quotient.getPolynomialRealCoeff().get(0).getPower();
            PolynomialRealCoefficients intermediate;
            /**when the remainder(dividend at each step) has smaller degree than quotient, it cannot be divided anymore*/
            while (remainder.getPolynomialRealCoeff().get(0).getPower() >= degreeQ && remainder.getPolynomialRealCoeff().get(0).getCoefficient() != 0) {
                /**divide the first monomial from remainder with the first monomial of divisor (polynomial2)*/
                int pow = remainder.getPolynomialRealCoeff().get(0).getPower() - degreeQ;
                double coefficient = remainder.getPolynomialRealCoeff().get(0).getCoefficient() /
                                     quotient.getPolynomialRealCoeff().get(0).getCoefficient();
                monomialsQuotient.add(new MonomialRealCoefficients(pow, coefficient));
                /**multiply the new obtained monomial of quotient with the divisor*/
                intermediate = multiplication(quotient, new MonomialRealCoefficients(pow, coefficient));
                /**subtract the result of multiplication from the dividend*/
                remainder = subtraction(remainder, intermediate);
            }
            result.add(new PolynomialRealCoefficients(monomialsQuotient));
            result.add(remainder);
            return result;
        }
    }

    /**the method is like the subtraction on integer coefficients,
       but uses real coefficient polynomials and it is used only for the division method*/
    public PolynomialRealCoefficients subtraction(PolynomialRealCoefficients polynomial1, PolynomialRealCoefficients polynomial2) {
        ArrayList<MonomialRealCoefficients> polynomialDiff = new ArrayList<>();

        for (MonomialRealCoefficients monomial1 : polynomial1.getPolynomialRealCoeff()) {
            Iterator<MonomialRealCoefficients> iterator = polynomial2.getPolynomialRealCoeff().iterator();
            boolean powerFound = false;
            while (iterator.hasNext()) {
                MonomialRealCoefficients monomial2 = iterator.next();
                if (monomial2.getPower() == monomial1.getPower()) {
                    powerFound = true;
                    double newCoefficient = monomial1.getCoefficient() - monomial2.getCoefficient();
                    polynomialDiff.add(new MonomialRealCoefficients(monomial1.getPower(), newCoefficient));
                    iterator.remove();
                }
            }
            if (!powerFound) {
                polynomialDiff.add(monomial1);
            }
        }

        for (MonomialRealCoefficients monomial2 : polynomial2.getPolynomialRealCoeff()) {
            monomial2.setCoefficient(-monomial2.getCoefficient());
            polynomialDiff.add(monomial2);
        }

        polynomialDiff.removeIf(monomialRealCoefficients -> monomialRealCoefficients.getCoefficient() == 0);

        if(polynomialDiff.isEmpty()) {
            polynomialDiff.add(new MonomialRealCoefficients(0,0));
        }
        else {
            Collections.sort(polynomialDiff, new Comparator<MonomialRealCoefficients>() {
                @Override
                public int compare(MonomialRealCoefficients o1, MonomialRealCoefficients o2) {
                    if (o1.getPower() < o2.getPower()) return 1;
                    else if (o1.getPower() == o2.getPower()) return 0;
                    else return -1;
                }
            });
        }
        return new PolynomialRealCoefficients(polynomialDiff);
    }

    /**the method is used only for the division method, and it multiplies a polynomial with a monomial, not 2 polynomials*/
    public PolynomialRealCoefficients multiplication(PolynomialRealCoefficients polynomial1, MonomialRealCoefficients monomial) {

        ArrayList<MonomialRealCoefficients> polynomialProduct = new ArrayList<>();

        for(MonomialRealCoefficients monomial1 : polynomial1.getPolynomialRealCoeff()) {
                polynomialProduct.add(new MonomialRealCoefficients(monomial1.getPower()+monomial.getPower(),
                                                          monomial1.getCoefficient()*monomial.getCoefficient()));
            }

       return new PolynomialRealCoefficients(polynomialProduct);
    }
}
