package com.ayaanqui.calculator.util;

import java.util.ArrayList;
import java.lang.Math;

import com.ayaanqui.calculator.objects.Response;

public class MathFunctions {
    private static final String[] functionsList = { "sqrt", "sin", "cos", "tan", "ln", "abs", "exp", "fact", "arcsin",
            "arccos", "arctan" };
    private static final String[] negFunctionsList = { "-sqrt", "-sin", "-cos", "-tan", "-ln", "-abs", "-exp", "-fact",
            "-arcsin", "-arccos", "-arctan" };
    private ArrayList<String> formattedUserInput;

    public MathFunctions(ArrayList<String> formattedUserInput) {
        this.formattedUserInput = formattedUserInput;
    }

    public double factorialOf(double x) {
        double factorial = 1;

        for (int i = (int) x; i > 1; i--) {
            factorial *= i;
        }
        return factorial;
    }

    /**
     * Goes through the all of formattedUserInput to check if a negative functions
     * is found (using negFunctionsList), add a "-1" and "*" and replaces the
     * negative function with the equivalent element from functionsList.
     */
    public void formatNegativeFunctions() {
        for (int i = 0; i < formattedUserInput.size(); i++) {
            for (int j = 0; j < negFunctionsList.length; j++) {
                if (formattedUserInput.get(i).equals(negFunctionsList[j])) {
                    formattedUserInput.set(i, functionsList[j]);
                    formattedUserInput.add(i, "-1");
                    formattedUserInput.add(i + 1, "*");
                }
            }
        }
    }

    public Response evaluateFunctions() {
        formatNegativeFunctions();

        for (int i = 0; i < formattedUserInput.size(); i++) {
            for (String operator : functionsList) {
                if (formattedUserInput.get(i).equals(operator)) {
                    // Evaluates [(, x, )] from [sin, (, x, )], leaving us with [sin, x]
                    Response evalaluateParenthesesResponse = EvaluateParentheses.condense(formattedUserInput, i + 1);

                    if (!evalaluateParenthesesResponse.success)
                        return evalaluateParenthesesResponse;
                    double x = evalaluateParenthesesResponse.result;

                    switch (operator) {
                        case "sqrt": // Square Root
                            formattedUserInput.set(i, Math.sqrt(x) + "");
                            break;
                        case "sin": // Sine function
                            formattedUserInput.set(i, Math.sin(x) + "");
                            break;
                        case "cos": // Cosine function
                            formattedUserInput.set(i, Math.cos(x) + "");
                            break;
                        case "tan": // Tangent function
                            formattedUserInput.set(i, Math.tan(x) + "");
                            break;
                        case "ln": // Natural Log (base e) function
                            formattedUserInput.set(i, Math.log(x) + "");
                            break;
                        case "abs": // Absolute
                            formattedUserInput.set(i, Math.abs(x) + "");
                            break;
                        case "exp": // Exponential e
                            formattedUserInput.set(i, Math.exp(x) + "");
                            break;
                        case "fact": // Factorial
                            formattedUserInput.set(i, factorialOf(x) + "");
                            break;
                        case "arcsin": // Inverse Sine
                            formattedUserInput.set(i, Math.asin(x) + "");
                            break;
                        case "arccos": // Inverse Cosine
                            formattedUserInput.set(i, Math.acos(x) + "");
                            break;
                        case "arctan": // Inverse Tangent value
                            formattedUserInput.set(i, Math.atan(x) + "");
                            break;
                    }
                    formattedUserInput.remove(i + 1); // Remove x from formattedUserInput
                }
            }
        }
        return null;
    }
}