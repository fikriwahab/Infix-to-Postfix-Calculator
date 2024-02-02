import javax.swing.JFrame;

public class PostfixCalculatorApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Infix -> Postfix Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PostfixCalculatorPanel panel = new PostfixCalculatorPanel();
        frame.getContentPane().add(panel);

        frame.pack();
        frame.setFocusable(false);
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(frame.getSize(null));
        frame.setVisible(true);
    }
}