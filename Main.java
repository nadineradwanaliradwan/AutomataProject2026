import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class Main {
    public static final Color BG = new Color(245, 247, 250);
    public static final Color WHITE = Color.WHITE;
    public static final Color ACCENT = new Color(67, 97, 238);
    public static final Color ACCENT_HOVER = new Color(50, 75, 200);
    public static final Color SUCCESS = new Color(34, 139, 87);
    public static final Color ERROR = new Color(200, 50, 50);
    public static final Color TEXT = new Color(30, 30, 40);
    public static final Color SUBTEXT = new Color(100, 110, 130);
    public static final Color BORDER = new Color(220, 224, 235);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}

            JFrame frame = new JFrame("CSE432 — Automata Project");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(920, 700);
            frame.getContentPane().setBackground(BG);

            // Header
            JPanel header = new JPanel(new BorderLayout());
            header.setBackground(ACCENT);
            header.setBorder(BorderFactory.createEmptyBorder(14, 24, 14, 24));
            JLabel headerTitle = new JLabel("CSE432 — Automata & Computability");
            headerTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
            headerTitle.setForeground(Color.WHITE);
            JLabel headerSub = new JLabel("Ain Shams University  ·  Spring 2026");
            headerSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            headerSub.setForeground(new Color(200, 210, 255));
            header.add(headerTitle, BorderLayout.WEST);
            header.add(headerSub, BorderLayout.EAST);

            JTabbedPane tabs = new JTabbedPane();
            tabs.setBackground(BG);
            tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));
            tabs.addTab("  CFG → PDA  ", new CFGtoPDAPanel());
            tabs.addTab("  DFA Simulator  ", new DFAPanel());
            tabs.addTab("  PDA — aⁿbⁿ  ", new PDAPanel());

            JPanel main = new JPanel(new BorderLayout());
            main.add(header, BorderLayout.NORTH);
            main.add(tabs, BorderLayout.CENTER);

            frame.add(main);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public static JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 28, 10, 28));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(ACCENT_HOVER); }
            public void mouseExited(java.awt.event.MouseEvent e) { btn.setBackground(ACCENT); }
        });
        return btn;
    }

    public static JTextArea styledTextArea() {
        JTextArea area = new JTextArea();
        area.setFont(new Font("Consolas", Font.PLAIN, 13));
        area.setBackground(new Color(250, 251, 255));
        area.setForeground(TEXT);
        area.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    public static JTextField styledField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setForeground(TEXT);
        field.setBackground(WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    public static JScrollPane styledScroll(JTextArea area) {
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        return scroll;
    }

    public static JPanel card() {
        JPanel p = new JPanel();
        p.setBackground(WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        return p;
    }

    public static JLabel sectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(SUBTEXT);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        return lbl;
    }
}