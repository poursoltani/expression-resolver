package com.github.ayaanqui.ExpressionResolver.util;

import java.util.LinkedList;

import com.github.ayaanqui.ExpressionResolver.ExpressionResolver;
import com.github.ayaanqui.ExpressionResolver.algorithms.RelatedParentheses;
import com.github.ayaanqui.ExpressionResolver.objects.Response;

import java.util.HashMap;

public class EvaluateParentheses {
    public static Response condense(LinkedList<String> formattedList, int start) {
        HashMap<Integer, Integer> relatedParentheses = RelatedParentheses.evaluateRelations(formattedList);

        if (relatedParentheses.isEmpty())
            return Response.getError(new String[] { "Parentheses mismatch" });

        int end = relatedParentheses.get(start);

        ExpressionResolver newExpression = new ExpressionResolver();
        newExpression.expressionList(new LinkedList<String>(formattedList.subList(start + 1, end)));
        Response res = newExpression.solveExpression();

        // Removing elements from the back, to avoid IndexOutOfBounds Errors
        for (int t = end; t > start; t--)
            formattedList.remove(t);

        if (res.success)
            formattedList.set(start, Double.toString(res.result));
        return res;
    }
}