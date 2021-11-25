package model;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

/**class with operations on polynomial with integer coefficients*/
public class OperationIntegerCoefficients {

    /**derives a polynomial and returns the polynomial result*/
    public PolynomialIntegerCoefficients derivative(PolynomialIntegerCoefficients polynomial) {
        int index = -1, power0Existent = 0;
        /**takes all the monomials from polynomial and multiply the coefficient with the power*/
        for (MonomialIntegerCoefficients monomial : polynomial.getPolynomialIntCoeff()) {
            int newCoefficient = monomial.getCoefficient()*monomial.getPower();

            monomial.setCoefficient(newCoefficient);

            if(monomial.getPower() == 0){ /**if power is 0, the monomial needs to be removed*/
                power0Existent = 1;
                index = polynomial.getPolynomialIntCoeff().indexOf(monomial);
            }
            else { /**decrease all the powers by 1*/
                int newPower = monomial.getPower() - 1;
                monomial.setPower(newPower);
            }
        }

        if (power0Existent == 1){
            polynomial.getPolynomialIntCoeff().remove(index);
            /**if the result is empty, create a monomial with 0 coefficient and power, for display purposes*/
            if(polynomial.getPolynomialIntCoeff().isEmpty()) {
                ArrayList<MonomialIntegerCoefficients> monomials = new ArrayList<>();
                monomials.add(new MonomialIntegerCoefficients(0, 0));
                polynomial.setPolynomialIntCoeff(monomials);
            }
        }

        return polynomial;
    }

    /**adds 2 polynomials and returns a polynomial as result*/
    public PolynomialIntegerCoefficients addition(PolynomialIntegerCoefficients polynomial1, PolynomialIntegerCoefficients polynomial2) {
        ArrayList<MonomialIntegerCoefficients> polynomialSum = new ArrayList<>();

        /**takes the first polynomial and checks each monomial with the monomials a of second polynomials; if they have the same
           power the coefficients are added (and the result is added in the polynomialSum) and the monomial from second polynomial
           is removed;
         *if we don't find in the second polynomial a monomial with the same power, the monomial is added in the
           sum as it is*/
        for (MonomialIntegerCoefficients monomial1 : polynomial1.getPolynomialIntCoeff()) {
            Iterator<MonomialIntegerCoefficients> iterator = polynomial2.getPolynomialIntCoeff().iterator();
            boolean powerFound = false;
            while (iterator.hasNext()) {
                MonomialIntegerCoefficients monomial2 = iterator.next();
                if (monomial2.getPower() == monomial1.getPower()) {
                    powerFound = true;
                    polynomialSum.add(new MonomialIntegerCoefficients(monomial1.getPower(),
                                 monomial1.getCoefficient() + monomial2.getCoefficient()));
                    iterator.remove();
                }
            }
            if (!powerFound) {
                polynomialSum.add(monomial1);
            }
        }
        /**if we still have monomials in the second polynomials it means that there are terms not added in the sum
           because they don't have the same power as a monomial from first polynomial, so they are added as they are*/
        for(MonomialIntegerCoefficients monomial2 : polynomial2.getPolynomialIntCoeff()) {
            polynomialSum.add(monomial2);
        }
        /**if there are monomials in the sum with coefficient 0, they are removed*/
        polynomialSum.removeIf(monomialIntegerCoefficients -> monomialIntegerCoefficients.getCoefficient() == 0);
        /**if the result is empty add a 0 monomial for printing purposes*/
        if(polynomialSum.isEmpty()) {
            polynomialSum.add(new MonomialIntegerCoefficients(0, 0));
        }
        else { /**sort the sum by decreasing powers of the monomials*/
            Collections.sort(polynomialSum, new Comparator<MonomialIntegerCoefficients>() {
                @Override
                public int compare(MonomialIntegerCoefficients o1, MonomialIntegerCoefficients o2) {
                    if (o1.getPower() < o2.getPower()) return 1;
                    else if (o1.getPower() == o2.getPower()) return 0;
                    else return -1;
                }
            });
        }
        return new PolynomialIntegerCoefficients(polynomialSum);
    }

    /**subtracts 2 polynomials and returns a polynomial as result
    *the behaviour is exactly the same as the addition function, only that the coefficients are subtracted */
    public PolynomialIntegerCoefficients subtraction(PolynomialIntegerCoefficients polynomial1, PolynomialIntegerCoefficients polynomial2) {
        ArrayList<MonomialIntegerCoefficients> polynomialDiff = new ArrayList<>();

        for (MonomialIntegerCoefficients monomial1 : polynomial1.getPolynomialIntCoeff()) {
            Iterator<MonomialIntegerCoefficients> iterator = polynomial2.getPolynomialIntCoeff().iterator();
            boolean powerFound = false;
            while (iterator.hasNext()) {
                MonomialIntegerCoefficients monomial2 = iterator.next();
                if (monomial2.getPower() == monomial1.getPower()) {
                    powerFound = true;
                    int newCoefficient = monomial1.getCoefficient() - monomial2.getCoefficient();
                    polynomialDiff.add(new MonomialIntegerCoefficients(monomial1.getPower(), newCoefficient));
                    iterator.remove();
                }
            }
            if (!powerFound) {
                polynomialDiff.add(monomial1);
            }
        }

        for (MonomialIntegerCoefficients monomial2 : polynomial2.getPolynomialIntCoeff()) {
            monomial2.setCoefficient(-monomial2.getCoefficient());
            polynomialDiff.add(monomial2);
        }

        polynomialDiff.removeIf(monomialIntegerCoefficients -> monomialIntegerCoefficients.getCoefficient() == 0);

        if(polynomialDiff.isEmpty()) {
            polynomialDiff.add(new MonomialIntegerCoefficients(0,0));
        }
        else {
            Collections.sort(polynomialDiff, new Comparator<MonomialIntegerCoefficients>() {
                @Override
                public int compare(MonomialIntegerCoefficients o1, MonomialIntegerCoefficients o2) {
                    if (o1.getPower() < o2.getPower()) return 1;
                    else if (o1.getPower() == o2.getPower()) return 0;
                    else return -1;
                }
            });
        }
        return new PolynomialIntegerCoefficients(polynomialDiff);
    }

    /**multiplies 2 polynomials and returns a polynomial as result*/
    public PolynomialIntegerCoefficients multiplication(PolynomialIntegerCoefficients polynomial1, PolynomialIntegerCoefficients polynomial2) {
        ArrayList<MonomialIntegerCoefficients> polynomialProduct = new ArrayList<>();

        /**each monomial of first polynomials is multiplied with each monomial of second polynomial,
           i.e. coefficients are multiplied and powers are added */
        for(MonomialIntegerCoefficients monomial1 : polynomial1.getPolynomialIntCoeff()) {
            for(MonomialIntegerCoefficients monomial2 : polynomial2.getPolynomialIntCoeff()) {
                polynomialProduct.add(new MonomialIntegerCoefficients(monomial1.getPower()+monomial2.getPower(),
                                 monomial1.getCoefficient()*monomial2.getCoefficient()));
            }
        }
        /**after all were multiplied, there are monomials with the same power, so their coefficients are added together and
         only one monomial will remain;
         *this is done with a map having the key the power of the monomials and the values are the sum of coefficients*/
        Map<Integer, Integer> map = polynomialProduct.stream().collect(
                groupingBy(MonomialIntegerCoefficients::getPower, summingInt(MonomialIntegerCoefficients::getCoefficient))
        );
        ArrayList<MonomialIntegerCoefficients> polynomialProductReduced = new ArrayList<>();
        /**the terms from the map are added in a list of monomials*/
        for(Map.Entry<Integer, Integer> monomial : map.entrySet()) {
            polynomialProductReduced.add(new MonomialIntegerCoefficients(monomial.getKey(), monomial.getValue()));
        }
        /**the list is reversed because the map has ordered the powers ascending*/
        Collections.reverse(polynomialProductReduced);

        return new PolynomialIntegerCoefficients(polynomialProductReduced);
    }
}
