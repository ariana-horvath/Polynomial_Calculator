package controller;
import exception.InputValidationFailedException;
import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.lang.Integer.parseInt;

/**The class is used to control the relationship between the model classes and the view; it validates input strings,
parse the input into Polynomial objects, calls the functions for operations and returns the result as string*/
public class MainController {

    private OperationIntegerCoefficients operationInt= new OperationIntegerCoefficients();
    private OperationRealCoefficients operationReal= new OperationRealCoefficients();

    /** method validates the input string to be in the polynomial form, without illegal characters, negative powers or
       real coefficients */
    public boolean validateInput(String polynomialString) {

        String polynomial = polynomialString.replaceAll("\\s",""); /**eliminates all spaces introduced for readability*/
        String polynomialPattern = "^([-+]?([0-9]*)?([xX](\\^[0-9]+)?)?)+"; /**pattern for a monomial, taken once or multiple times*/
        Pattern pattern = Pattern.compile(polynomialPattern);
        Matcher matcher = pattern.matcher(polynomial);

        if(!matcher.matches()) {
            throw new InputValidationFailedException("Input is not a valid polynomial!");
        }
        else {
            /**if the last character is -/+ without a number after, the polynomial is not valid*/
            if (polynomial.charAt(polynomial.length()-1) == '-' ||
                    polynomial.charAt(polynomial.length()-1) == '+')
                throw new InputValidationFailedException("Input is not a valid polynomial!");
            else
                return true;
        }
    }

    /**method transforms the input string into the corresponding PolynomialIntegerCoefficients object*/
    public PolynomialIntegerCoefficients parsePolynomial(String polynomialString) {
        /**eliminates all spaces introduced for readability*/
        polynomialString = polynomialString.replaceAll("\\s","");
        /**the parsing is done after 'x' only, not both 'x' and 'X', so make the string to lower case*/
        polynomialString = polynomialString.toLowerCase();

        /**splitting the polynomial into monomials by the sign +/-, with the sign taken into the monomial from the right*/
        ArrayList<String> monomialsString = new ArrayList<>(Arrays.asList(polynomialString.split("(?=[-+])")));
        MonomialIntegerCoefficients monomial;
        ArrayList<MonomialIntegerCoefficients> monomials = new ArrayList<>();
        /**takes all the monomial strings to convert them into MonomialIntegerCoefficients objects*/
        for (String monomialStr : monomialsString) {
            if (monomialStr.startsWith("x^")) { /**if coefficient is 1/-1, it doesn't appear in the string so the case is treated separately*/
                int power = parseInt(monomialStr.substring(2));
                monomial = new MonomialIntegerCoefficients(power, 1);
            }
            else if (monomialStr.startsWith("+x^")) {
                    int power = parseInt(monomialStr.substring(3));
                    monomial = new MonomialIntegerCoefficients(power, 1);
                }
                else if(monomialStr.startsWith("-x^")) {
                        int power = parseInt(monomialStr.substring(3));
                        monomial = new MonomialIntegerCoefficients(power, -1);
                    }
                    else if(monomialStr.contains("x^")) {
                            String [] integers = monomialStr.split("x\\^", 2); //split by "x^"
                            int coefficient = parseInt(integers[0]); /**the first is the coefficient the second is the power*/
                            int power = parseInt(integers[1]);
                            monomial = new MonomialIntegerCoefficients(power, coefficient);
                        }
                        else if(monomialStr.contains("x")) { /**if power is 1 it is an separate case, in the string it is only x/-x/+x*/
                                if (monomialStr.startsWith("+x") || monomialStr.startsWith("x")) {
                                    monomial = new MonomialIntegerCoefficients(1, 1);
                                }
                                else if (monomialStr.startsWith("-x")) {
                                    monomial = new MonomialIntegerCoefficients(1, -1);
                                }
                                else {
                                    int coefficient = parseInt(monomialStr.substring(0, monomialStr.length()-1));
                                    monomial = new MonomialIntegerCoefficients(1, coefficient);
                                }
                            }
                            else { /**if we have only a number, the power is 0*/
                                int coefficient = parseInt(monomialStr);
                                monomial= new MonomialIntegerCoefficients(0, coefficient);
                            }
            monomials.add(monomial);
        }
        /**sort the list of monomials descending by their power because this is the normal form of a polynomial*/
        Collections.sort(monomials, new Comparator<MonomialIntegerCoefficients>() {
            @Override
            public int compare(MonomialIntegerCoefficients o1, MonomialIntegerCoefficients o2) {
                if (o1.getPower() < o2.getPower()) return 1;
                else if (o1.getPower() == o2.getPower()) return 0;
                else return -1;
            }
        });

        return new PolynomialIntegerCoefficients(monomials);
    }

    /**method that controls the derivative operation*/
    public String derivativeController(String polynomialString) {

        if (validateInput(polynomialString)) {  /**validating input*/
            PolynomialIntegerCoefficients polynomial = parsePolynomial(polynomialString); /**parsing input*/
            PolynomialIntegerCoefficients resultPolynomial = operationInt.derivative(polynomial);  /**calling the operation from model*/
            return resultPolynomial.toString(); /**returning the result as a string*/
        }
        else return null;
    }

    /**method that controls the integration operation*/
    public String integrationController(String polynomialString) {
        if (validateInput(polynomialString)) {
            PolynomialIntegerCoefficients polynomial = parsePolynomial(polynomialString);
            PolynomialRealCoefficients resultPolynomial = operationReal.integration(polynomial);
            return resultPolynomial.toString();
        }
        else return null;
    }

    /**method that controls the addition operation*/
    public String additionController(String polynomialString1, String polynomialString2) {
        if (validateInput(polynomialString1) && validateInput(polynomialString2)) {
            PolynomialIntegerCoefficients polynomial1 = parsePolynomial(polynomialString1);
            PolynomialIntegerCoefficients polynomial2 = parsePolynomial(polynomialString2);
            PolynomialIntegerCoefficients resultPolynomial = operationInt.addition(polynomial1, polynomial2);
            return resultPolynomial.toString();
        }
        else return null;
    }

    /**method that controls the subtraction operation*/
    public String subtractionController(String polynomialString1, String polynomialString2) {
        if (validateInput(polynomialString1) && validateInput(polynomialString2)) {
            PolynomialIntegerCoefficients polynomial1 = parsePolynomial(polynomialString1);
            PolynomialIntegerCoefficients polynomial2 = parsePolynomial(polynomialString2);
            PolynomialIntegerCoefficients resultPolynomial = operationInt.subtraction(polynomial1, polynomial2);
            return resultPolynomial.toString();
        }
        else return null;
    }

    /**method that controls the multiplication operation*/
    public String multiplicationController(String polynomialString1, String polynomialString2) {
        if (validateInput(polynomialString1) && validateInput(polynomialString2)) {
            PolynomialIntegerCoefficients polynomial1 = parsePolynomial(polynomialString1);
            PolynomialIntegerCoefficients polynomial2 = parsePolynomial(polynomialString2);
            PolynomialIntegerCoefficients resultPolynomial = operationInt.multiplication(polynomial1, polynomial2);
            return resultPolynomial.toString();
        }
        else return null;
    }

    /**method that controls the division operation*/
    public String divisionController(String polynomialString1, String polynomialString2) {
        if (validateInput(polynomialString1) && validateInput(polynomialString2)) {
            PolynomialIntegerCoefficients polynomial1 = parsePolynomial(polynomialString1);
            PolynomialIntegerCoefficients polynomial2 = parsePolynomial(polynomialString2);
            ArrayList<PolynomialRealCoefficients> result = operationReal.division(polynomial1, polynomial2);
            return "Quotient = "+result.get(0).toString() + "  Remainder = "+result.get(1).toString();
        }
        else return null;
    }

    /**will display a message if the user tries to divide by 0*/
    public void validateDivision(String string) {
        if(string.compareTo("0") == 0)
            throw new InputValidationFailedException("Division by zero not possible.");
    }

    /**will display a message if the user tries to leave input text field empty*/
    public void validateTextField(String string) {
        if(string.compareTo("") == 0)
            throw new InputValidationFailedException("Input cannot be empty.");
    }
}
