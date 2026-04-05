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
        explanationArea.setText("Empty string rejected: doesn't end with 0.");
        return;
    }

    int onesMod3 = 0;
    char lastChar = ' ';
    StringBuilder trace = new StringBuilder();
    trace.append(String.format("  %-8s %-18s %-10s%n", "Char", "State (1s mod 3)", "Last"));
    trace.append("  " + "─".repeat(38) + "\n");

    for (char c : input.toCharArray()) {
        if (c == '1') onesMod3 = (onesMod3 + 1) % 3;
        lastChar = c;
        trace.append(String.format("  %-8s %-18s %-10s%n", c, "q" + onesMod3, lastChar));
    }

    boolean accepted = (onesMod3 == 0) && (lastChar == '0');
    trace.append("\n  " + "─".repeat(38) + "\n");
    trace.append("  Final state  : q" + onesMod3 + "  " + (onesMod3 == 0 ? "[OK]" : "[X]") + "\n");
    trace.append("  Last char    : " + lastChar + "  " + (lastChar == '0' ? "[OK]" : "[X]") + "\n");
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