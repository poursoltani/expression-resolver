package com.github.ayaanqui.ExpressionResolver.util;

import java.util.LinkedList;
import java.util.Set;

import com.github.ayaanqui.ExpressionResolver.objects.Response;

import java.lang.Math;

public class MathFunctions {
    private LinkedList<String> formattedUserInput;
    private Set<String> functionList;

    public MathFunctions(LinkedList<String> formattedUserInput, Set<String> functionList) {
        this.formattedUserInput = formattedUserInput;
        this.functionList = functionList;
    }

    public double factorialOf(double x) {
        double factorial = 1;

        for (int i = (int) x; i > 1; i--) {
            factorial *= i;
        }
        return factorial;
    }

    public Response evaluateFunctions() {
        for (int i = 0; i < formattedUserInput.size(); i++) {
            for (String function : functionList) {
                if (formattedUserInput.get(i).equals(function)) {
                    // Evaluates [(, x, )] from [sin, (, x, )], leaving us with [sin, x]
                    Response evalaluateParenthesesResponse = EvaluateParentheses.condense(formattedUserInput, i + 1);

                    if (!evalaluateParenthesesResponse.success)
                        return evalaluateParenthesesResponse;
                    double x = evalaluateParenthesesResponse.result;

                    switch (function) {
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
                        case "deg": // Natural Log (base e) function
                            formattedUserInput.set(i, Double.toString(Math.toDegrees(x)));
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
        return Response.getSuccess(0);
    }
}