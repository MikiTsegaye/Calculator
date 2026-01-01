package com.example.calc;

import java.util.*;

public class Calculator {

    public static double evaluate(String expr) {
        List<String> postfix = infixToPostfix(expr);
        return evaluatePostfix(postfix);
    }

    private static List<String> infixToPostfix(String expr) {
        List<String> output = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                number.append(c);
            } else {
                if(c == '-' && (i==0 || expr.charAt(i-1)== '(' || isOperator(expr.charAt(i-1))))
                {
                    number.append(c);
                    continue;
                }
                if (number.length() > 0) {
                    output.add(number.toString());
                    number.setLength(0);
                }

                if (c == '(') {
                    stack.push(c);
                } else if (c == ')') {
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        output.add(String.valueOf(stack.pop()));
                    }
                    stack.pop();
                } else if (isOperator(c)) {
                    while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                        output.add(String.valueOf(stack.pop()));
                    }
                    stack.push(c);
                }
            }
        }

        if (number.length() > 0)
            output.add(number.toString());

        while (!stack.isEmpty())
            output.add(String.valueOf(stack.pop()));

        return output;
    }

    private static double evaluatePostfix(List<String> postfix) {
        Stack<Double> stack = new Stack<>();

        for (String token : postfix) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "*": stack.push(a * b); break;
                    case "/": stack.push(a / b); break;
                }
            }
        }

        return stack.pop();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }

    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}