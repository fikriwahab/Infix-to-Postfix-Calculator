import java.util.Stack;
import java.util.StringTokenizer;

class InfixToPostfixConverter {
    public static String infixToPostfix(String infixExpression) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> operators = new Stack<>();

        StringTokenizer tokenizer = new StringTokenizer(infixExpression, "+-*/^()", true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.isEmpty()) {
                continue;
            }

            char ch = token.charAt(0);
            if (Character.isDigit(ch)) {
                postfix.append(token).append(" ");
            } else if (isOperator(ch)) {
                while (!operators.isEmpty() && operators.peek() != '(' && hasHigherPrecedence(operators.peek(), ch)) {
                    postfix.append(operators.pop()).append(" ");
                }
                operators.push(ch);
            } else if (ch == '(') {
                operators.push(ch);
            } else if (ch == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    postfix.append(operators.pop()).append(" ");
                }
                if (!operators.isEmpty() && operators.peek() == '(') {
                    operators.pop();
                }
            }
        }

        while (!operators.isEmpty()) {
            postfix.append(operators.pop()).append(" ");
        }

        return postfix.toString().trim();
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^';
    }

    private static int getPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return 0;
        }
    }

    private static boolean hasHigherPrecedence(char operator1, char operator2) {
        if (operator2 == '^') {
            return getPrecedence(operator1) > getPrecedence(operator2);
        } else {
            return getPrecedence(operator1) >= getPrecedence(operator2);
        }
    }
}

class PostfixEvaluator { //checking wether a char is an operator for 2 operands
    public static long evaluatePostfix(String postfixExpression) {
        Stack<Long> operands = new Stack<>();

        StringTokenizer tokenizer = new StringTokenizer(postfixExpression, " ", false);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.isEmpty()) {
                continue;
            }

            if (Character.isDigit(token.charAt(0))) {
                operands.push(Long.parseLong(token));
            } else if (isOperator(token.charAt(0))) {
                if (operands.size() < 2) {
                    throw new IllegalArgumentException("Missing Operand");
                }
                long operand2 = operands.pop();
                long operand1 = operands.pop();
                try {
                    long result = performOperation(token.charAt(0), operand1, operand2);
                    operands.push(result);
                } catch (ArithmeticException e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            }
        }

        if (operands.size() != 1) {
            throw new IllegalArgumentException("Invalid postfix expression");
        }

        return operands.pop();
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^';
    }

    private static long performOperation(char operator, long operand1, long operand2) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                return operand1 / operand2;
            case '^':
                return (long) Math.pow(operand1, operand2);
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
