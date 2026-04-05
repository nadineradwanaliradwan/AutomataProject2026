import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;

public class CFGtoPDAPanel extends JPanel {
    private JTextArea grammarInput;
    private JTextArea pdaOutput;

    public CFGtoPDAPanel() {
        setLayout(new BorderLayout(0, 16));
        setBackground(Main.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JPanel descCard = Main.card();
        descCard.setLayout(new BorderLayout(0, 4));
        JLabel title = new JLabel("CFG → PDA Converter");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Main.ACCENT);
        JLabel desc = new JLabel("Enter a Context-Free Grammar and convert it to an equivalent Pushdown Automaton.");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        desc.setForeground(Main.SUBTEXT);
        descCard.add(title, BorderLayout.NORTH);
        descCard.add(desc, BorderLayout.CENTER);

        JPanel inputCard = Main.card();
        inputCard.setLayout(new BorderLayout(0, 10));
        JLabel inputLbl = Main.sectionLabel("GRAMMAR RULES  (one per line, e.g.  S -> a S b | ε )");
        grammarInput = Main.styledTextArea();
        grammarInput.setRows(7);
        grammarInput.setText("S -> a S b | ε");
        JButton convertBtn = Main.styledButton("Convert to PDA  →");
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnRow.setBackground(Main.WHITE);
        btnRow.add(convertBtn);
        inputCard.add(inputLbl, BorderLayout.NORTH);
        inputCard.add(Main.styledScroll(grammarInput), BorderLayout.CENTER);
        inputCard.add(btnRow, BorderLayout.SOUTH);

        JPanel outputCard = Main.card();
        outputCard.setLayout(new BorderLayout(0, 10));
        JLabel outputLbl = Main.sectionLabel("PDA TRANSITIONS");
        pdaOutput = Main.styledTextArea();
        pdaOutput.setEditable(false);
        pdaOutput.setRows(10);
        pdaOutput.setBackground(new Color(248, 249, 252));
        outputCard.add(outputLbl, BorderLayout.NORTH);
        outputCard.add(Main.styledScroll(pdaOutput), BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, inputCard, outputCard);
        split.setDividerLocation(240);
        split.setBorder(null);
        split.setBackground(Main.BG);
        split.setDividerSize(8);

        add(descCard, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);

        convertBtn.addActionListener(e -> convert());
    }

    private void convert() {
        String[] lines = grammarInput.getText().trim().split("\n");
        StringBuilder out = new StringBuilder();
        out.append("  PDA = (Q, Σ, Γ, δ, q0, Z, F)\n");
        out.append("  States  : Q = { q0, qloop, qaccept }\n");
        out.append("  Start   : q0       Accept: qaccept\n");
        out.append("  Stack ⊥ : Z\n\n");
        out.append("  ── Transitions ─────────────────────────────────\n");
        out.append("  δ(q0,    ε, Z) → (qloop,   SZ)   [initialize]\n");
        out.append("  δ(qloop, ε, Z) → (qaccept, Z )   [accept]\n\n");

        Set<String> terminals = new LinkedHashSet<>();
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || !line.contains("->")) continue;
            String[] parts = line.split("->");
            if (parts.length < 2) continue;
            String lhs = parts[0].trim();
            for (String prod : parts[1].split("\\|")) {
                prod = prod.trim();
                out.append("  δ(qloop, ε, " + lhs + ") → (qloop, " + prod + ")\n");
                for (char c : prod.toCharArray())
                    if (Character.isLowerCase(c) && c != 'ε')
                        terminals.add(String.valueOf(c));
            }
        }
        out.append("\n  ── Terminal Matching ────────────────────────────\n");
        for (String t : terminals)
            out.append("  δ(qloop, " + t + ", " + t + ") → (qloop, ε)\n");

        pdaOutput.setText(out.toString());
    }
}