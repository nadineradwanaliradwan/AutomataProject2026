import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.Stack;

public class PDAPanel extends JPanel {
    private JTextField inputField;
    private JLabel resultLabel;
    private JTextArea explanationArea;

    public PDAPanel() {
        setLayout(new BorderLayout(0, 16));
        setBackground(Main.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JPanel descCard = Main.card();
        descCard.setLayout(new BorderLayout(0, 4));
        JLabel title = new JLabel("PDA Simulator — aⁿbⁿ");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Main.ACCENT);
        JLabel desc = new JLabel("Accepts strings of the form aⁿbⁿ where n ≥ 0 (equal number of a's followed by b's).");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        desc.setForeground(Main.SUBTEXT);
        descCard.add(title, BorderLayout.NORTH);
        descCard.add(desc, BorderLayout.CENTER);

        JPanel inputCard = Main.card();
        inputCard.setLayout(new BorderLayout(12, 8));
        JLabel inputLbl = Main.sectionLabel("INPUT STRING");
        inputField = Main.styledField();
        inputField.setPreferredSize(new Dimension(420, 40));
        JButton checkBtn = Main.styledButton("Check");
        JPanel inputRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        inputRow.setBackground(Main.WHITE);
        inputRow.add(inputField);
        inputRow.add(checkBtn);
        inputCard.add(inputLbl, BorderLayout.NORTH);
        inputCard.add(inputRow, BorderLayout.CENTER);

        JPanel resultCard = Main.card();
        resultCard.setLayout(new BorderLayout(16, 0));
        resultCard.setPreferredSize(new Dimension(0, 80));
        resultLabel = new JLabel("Enter a string and click Check");
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        resultLabel.setForeground(Main.SUBTEXT);
        resultCard.add(resultLabel, BorderLayout.CENTER);

        JPanel traceCard = Main.card();
        traceCard.setLayout(new BorderLayout(0, 8));
        traceCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Main.BORDER, 1, true),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        JLabel traceLbl = Main.sectionLabel("EXECUTION TRACE");
        explanationArea = Main.styledTextArea();
        explanationArea.setEditable(false);
        explanationArea.setRows(10);
        explanationArea.setBackground(new Color(248, 249, 252));
        traceCard.add(traceLbl, BorderLayout.NORTH);
        traceCard.add(Main.styledScroll(explanationArea), BorderLayout.CENTER);

        JPanel top = new JPanel(new GridLayout(1, 2, 12, 0));
        top.setBackground(Main.BG);
        top.add(inputCard);
        top.add(resultCard);

        add(descCard, BorderLayout.NORTH);
        add(top, BorderLayout.CENTER);
        add(traceCard, BorderLayout.SOUTH);

        checkBtn.addActionListener(e -> checkPDA());
        inputField.addActionListener(e -> checkPDA());
    }

    private void checkPDA() {
        String input = inputField.getText().trim();
        if (!input.matches("[ab]*")) {
           
            resultLabel.setText("Invalid — only a's and b's allowed");
            resultLabel.setForeground(new Color(220, 150, 0));
            return;
        }
        if (input.isEmpty()) {
            
            resultLabel.setText("ACCEPTED  (n = 0)");
            resultLabel.setForeground(Main.SUCCESS);
            explanationArea.setText("  Empty string accepted — n = 0 satisfies aⁿbⁿ.");
            return;
        }

        Stack<Character> stack = new Stack<>();
        stack.push('Z');
        StringBuilder trace = new StringBuilder();
        trace.append(String.format("  %-8s %-10s %-20s%n", "Char", "State", "Stack"));
        trace.append("  " + "─".repeat(40) + "\n");

        String state = "q0";
        boolean rejected = false;
        String reason = "";

        for (char c : input.toCharArray()) {
            // Apply transition first, then record post-transition state in trace
            if (state.equals("q0")) {
                if (c == 'a') {
                    stack.push('a');
                } else {
                    state = "q1";
                    if (stack.peek() == 'a') stack.pop();
                    else { rejected = true; reason = "More b's than a's"; }
                }
            } else {
                if (c == 'a') { rejected = true; reason = "'a' found after 'b'"; }
                else if (stack.peek() == 'a') stack.pop();
                else { rejected = true; reason = "More b's than a's"; }
            }
            trace.append(String.format("  %-8s %-10s %-20s%n", c, state, stack.toString()));
            if (rejected) break;
        }

        trace.append(String.format("  %-8s %-10s %-20s%n", "END", state, stack.toString()));
        boolean accepted = !rejected && stack.size() == 1 && stack.peek() == 'Z';

        trace.append("\n  " + "\u2500".repeat(40) + "\n");
        if (rejected) trace.append("  Reason: " + reason + "\n");
        else if (!accepted) trace.append("  Reason: " + (stack.size() - 1) + " unmatched a(s) remain\n");
        trace.append("\n  Verdict: " + (accepted ? "ACCEPTED [OK]" : "REJECTED [X]"));

        if (accepted) {
            
            resultLabel.setText("ACCEPTED");
            resultLabel.setForeground(Main.SUCCESS);
        } else {
           
            resultLabel.setText("REJECTED");
            resultLabel.setForeground(Main.ERROR);
        }
        explanationArea.setText(trace.toString());
    }
}