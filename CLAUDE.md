# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

This project has no build system — compile and run manually with `javac`/`java`.

**Compile all files:**
```bash
javac Main.java CFGtoPDAPanel.java DFAPanel.java PDAPanel.java
```

**Run the application:**
```bash
java Main
```

**Requires Java JDK 21+.** The README mentions Java 26; any JDK 21+ works fine.

## Architecture

The app is a Java Swing desktop GUI with a single `JFrame` containing a `JTabbedPane` with three tabs, each implemented as a `JPanel` subclass:

- [Main.java](Main.java) — Entry point. Sets up the frame, header, and tabs. Also acts as a **shared UI factory**: all panels call static helpers here (`Main.styledButton()`, `Main.styledField()`, `Main.styledTextArea()`, `Main.card()`, `Main.styledScroll()`, `Main.sectionLabel()`) to maintain consistent styling. Shared color constants (`Main.BG`, `Main.ACCENT`, `Main.SUCCESS`, `Main.ERROR`, etc.) are also defined here.

- [CFGtoPDAPanel.java](CFGtoPDAPanel.java) — Tab 1. Parses grammar rules entered as text (format: `S -> a S b | ε`, one rule per line) and generates PDA transitions using the LL parsing approach. Logic lives in the `convert()` method.

- [DFAPanel.java](DFAPanel.java) — Tab 2. Simulates a DFA over `{0,1}` that accepts strings where the count of `1`s is divisible by 3 AND the string ends with `0`. Logic lives in `checkDFA()`, which tracks `onesMod3` state and `lastChar` in a single pass.

- [PDAPanel.java](PDAPanel.java) — Tab 3. Simulates a PDA for the language `aⁿbⁿ` using a `java.util.Stack`. Logic lives in `checkPDA()`, with two states (`q0` for reading `a`s, `q1` for reading `b`s) and a bottom-of-stack sentinel `Z`.

All simulation logic is self-contained within each panel's private method — there are no shared automata utilities or external libraries beyond standard Java Swing.
