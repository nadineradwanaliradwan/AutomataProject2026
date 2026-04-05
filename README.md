CSE432 — Automata & Computability Project Spring 2026

A Java Swing desktop application implementing three automata concepts as part of the CSE432 Automata and Computability course term project

 Project Overview

This project consists of two parts:
- Part 1: A comprehensive report on Turing Machines and Pushdown Automata
- Part 2: A Java GUI application implementing three automata programs


Application Features

Tab 1 — CFG to PDA Converter
Converts a Context-Free Grammar (CFG) into an equivalent Pushdown Automaton (PDA) using the LL parsing approach. The user enters grammar rules in the format `S -> a S b | ε` and the app generates all PDA transitions automatically.

Tab 2 — DFA Simulator
Simulates a Deterministic Finite Automaton over the alphabet {0, 1} that accepts strings where:
- The number of 1s is divisible by 3, AND
- The string ends with 0

Displays a full step-by-step execution trace showing each state transition.

Tab 3 — PDA Simulator for aⁿbⁿ
Simulates a Pushdown Automaton for the language:
L = { aⁿbⁿ | n ≥ 0 }
Uses a stack to match each `a` with a corresponding `b`. Displays the full stack trace at each step.


How to Run

Prerequisites
- Java JDK 21 or higher installed
- Any terminal or VS Code

Test Cases

DFA Simulator
| Input | Result |
|-------|--------|
| `1110` |  Accepted |
| `111000` |  Accepted |
| `0` |  Accepted |
| `110` |  Rejected |
| `111` |  Rejected |
| `abc` |  Invalid |

PDA Simulator
| Input | Result |
|-------|--------|
| `ab` |  Accepted |
| `aabb` |  Accepted |
| `aaabbb` |  Accepted |
| `aab` |  Rejected |
| `ba` |  Rejected |
| `123` |  Invalid |

Technologies Used
- Java 26
- Java Swing (GUI)
- VS Code

