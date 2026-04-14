import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class DFAPanel extends JPanel {
    private JTextField inputField;
    private JLabel resultLabel;
    private JTextArea explanationArea;

    public DFAPanel() {
        setLayout(new BorderLayout(0, 16));
        setBackground(Main.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        // Top description card
        JPanel descCard = Main.card();
        descCard.setLayout(new BorderLayout(0, 4));
        JLabel title = new JLabel("DFA Simulator");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Main.ACCENT);
        JLabel desc = new JLabel("Accepts strings over {0,1} where the number of 1s is divisible by 3 AND the string ends with 0.");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        desc.setForeground(Main.SUBTEXT);
        descCard.add(title, BorderLayout.NORTH);
        descCard.add(desc, BorderLayout.CENTER);

        // Input card
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

        // Result card
        JPanel resultCard = Main.card();
        resultCard.setLayout(new BorderLayout(16, 0));
        resultCard.setPreferredSize(new Dimension(0, 80));

        
        resultLabel = new JLabel("Enter a string and click Check");
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        resultLabel.setForeground(Main.SUBTEXT);

        
        resultCard.add(resultLabel, BorderLayout.CENTER);

        // Trace card
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

        // Layout
        JPanel top = new JPanel(new GridLayout(1, 2, 12, 0));
        top.setBackground(Main.BG);
        top.add(inputCard);
        top.add(resultCard);

        add(descCard, BorderLayout.NORTH);
        add(top, BorderLayout.CENTER);
        add(traceCard, BorderLayout.SOUTH);

        checkBtn.addActionListener(e -> checkDFA());
        inputField.addActionListener(e -> checkDFA());
    }

    private void checkDFA() {
        String input = inputField.getText().trim();
        if (!input.matches("[01]*")) {
            resultLabel.setText("Invalid — only 0s and 1s allowed");
            resultLabel.setForeground(new Color(220, 150, 0));
            return;
        }
        if (input.isEmpty()) {
            resultLabel.setText("REJECTED — empty string");
            resultLabel.setForeground(Main.ERROR);
            explanationArea.setText(
                "  Empty string: 0 ones (divisible by 3 \u2713) but no last character (must end with 0 \u2717).\n  Rejected.");
            return;
        }

        int onesMod3 = 0;
        char lastChar = ' ';
        StringBuilder trace = new StringBuilder();
        // Full DFA state encodes both (1s mod 3) and (last char), e.g. q0_0 is the sole accept state
        trace.append(String.format("  %-6s  %-24s%n", "Char", "DFA State (mod3_last)"));
        trace.append("  " + "\u2500".repeat(34) + "\n");

        for (char c : input.toCharArray()) {
            if (c == '1') onesMod3 = (onesMod3 + 1) % 3;
            lastChar = c;
            trace.append(String.format("  %-6s  %-24s%n", c, "q" + onesMod3 + "_" + c));
        }

        boolean accepted = (onesMod3 == 0) && (lastChar == '0');
        trace.append("\n  " + "\u2500".repeat(34) + "\n");
        trace.append("  Final state  : q" + onesMod3 + "_" + lastChar + "\n");
        trace.append("  Accept state : q0_0\n");
        trace.append("  1s mod 3 = 0 : " + (onesMod3 == 0 ? "[\u2713]" : "[\u2717]") + "\n");
        trace.append("  Ends with 0  : " + (lastChar == '0' ? "[\u2713]" : "[\u2717]") + "\n");
        trace.append("\n  Verdict: " + (accepted ? "ACCEPTED [\u2713]" : "REJECTED [\u2717]"));

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