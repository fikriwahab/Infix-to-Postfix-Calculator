import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class PostfixCalculatorPanel extends JPanel {

    private JTextField infixField;
    private JLabel postfixField;
    private JLabel resultField;
    private JLabel errorField;

    public PostfixCalculatorPanel() {
        setLayout(new GridLayout(4, 2, 0, 0));
        this.setPreferredSize(new Dimension(400, 120));
        this.setBackground(Color.LIGHT_GRAY);
        JLabel infixLabel = new JLabel("Enter infix expression:");
        infixField = new JTextField();
        infixField.addActionListener(action);
        infixField.setBackground(Color.YELLOW);

        JLabel postfixLabel = new JLabel("Postfix expression:");
        postfixField = new JLabel();

        JLabel resultLabel = new JLabel("Result:");
        resultField = new JLabel();

        JLabel errorLabel = new JLabel("Error Messages:");
        errorField = new JLabel();

        add(infixLabel);
        add(infixField);
        add(postfixLabel);
        add(postfixField);
        add(resultLabel);
        add(resultField);
        add(errorLabel);
        add(errorField);
    }

    Action action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            calculate();
        }
    };

    private void calculate() {
        String infixExpression = infixField.getText().trim();
        try {
            if (infixExpression.isEmpty()) {
                throw new IllegalArgumentException("Empty infix");
            }
            if (infixExpression.matches(".*[a-zA-Z].*")) {
                throw new IllegalArgumentException("Contain Character");
            }
            if (infixExpression.contains("(") && !infixExpression.contains(")")) {
                throw new IllegalArgumentException("Missing close parentheses");
            }
            if (!infixExpression.contains("(") && infixExpression.contains(")")) {
                throw new IllegalArgumentException("Missing open parentheses");
            }

            String postfixExpression = InfixToPostfixConverter.infixToPostfix(infixExpression);
            postfixField.setText(postfixExpression);
            long result = PostfixEvaluator.evaluatePostfix(postfixExpression);
            resultField.setText(String.valueOf(result));
            errorField.setText("No error.");
        } catch (IllegalArgumentException ex) {
            postfixField.setText("");
            resultField.setText("");
            errorField.setText("[" + ex.getMessage() + "]");
        }
    }
}
