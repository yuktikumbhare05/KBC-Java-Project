import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * ================================================================
 * KBC - KAUN BANEGA CROREPATI : JAVA EDITION
 * ================================================================
 *
 * A console based quiz game inspired by the format of KBC.
 *
 * Features:
 * 1. Ten multiple-choice questions.
 * 2. Progressive prize money.
 * 3. 50:50 lifeline.
 * 4. Hint lifeline.
 * 5. Safe-haven amounts.
 * 6. Input validation.
 * 7. Game rules.
 * 8. Replay option.
 * 9. Score and prize display.
 * 10. Object-oriented programming structure.
 *
 * Compile:
 *      javac KBCGame.java
 *
 * Run:
 *      java KBCGame
 *
 * ================================================================
 */

public class KBCGame {

    // ------------------------------------------------------------
    // CONSTANTS
    // ------------------------------------------------------------

    private static final String GAME_NAME =
            "KAUN BANEGA CROREPATI - JAVA EDITION";

    private static final String[] OPTION_LABELS = {
            "A", "B", "C", "D"
    };

    private static final int TOTAL_QUESTIONS = 10;

    private static final long[] PRIZE_MONEY = {
            1000,
            2000,
            3000,
            5000,
            10000,
            20000,
            40000,
            80000,
            160000,
            320000
    };

    /*
     * Safe-haven amounts.
     *
     * In this educational version:
     * - Questions 1 to 5 establish a first safe amount.
     * - Questions 6 to 10 establish the final prize.
     */
    private static final long FIRST_SAFE_AMOUNT = 10000;

    // ------------------------------------------------------------
    // INSTANCE VARIABLES
    // ------------------------------------------------------------

    private final Scanner scanner;

    private final ArrayList<Question> questions;

    private boolean fiftyFiftyUsed;

    private boolean hintUsed;

    private long currentWinnings;

    private int currentQuestionNumber;

    private boolean gameOver;

    private String playerName;

    // ------------------------------------------------------------
    // CONSTRUCTOR
    // ------------------------------------------------------------

    public KBCGame() {

        scanner = new Scanner(System.in);

        questions = new ArrayList<>();

        fiftyFiftyUsed = false;

        hintUsed = false;

        currentWinnings = 0;

        currentQuestionNumber = 0;

        gameOver = false;

        playerName = "Contestant";
    }

    // ------------------------------------------------------------
    // MAIN METHOD
    // ------------------------------------------------------------

    public static void main(String[] args) {

        KBCGame game = new KBCGame();

                if (args.length > 0 && args[0].equalsIgnoreCase("--console")) {

                        game.startApplication();

                } else {

                            SwingUtilities.invokeLater(() -> new KBCWindow().open());
                }

    }

    // ------------------------------------------------------------
    // APPLICATION START
    // ------------------------------------------------------------

    public void startApplication() {

        showWelcomeScreen();

        loadQuestions();

        getPlayerName();

        boolean playAgain = true;

        while (playAgain) {

            resetGame();

            showRules();

            waitForEnter();

            playGame();

            playAgain = askForReplay();
        }

        showGoodbye();

        scanner.close();
    }

    // ------------------------------------------------------------
    // RESET GAME
    // ------------------------------------------------------------

    private void resetGame() {

        fiftyFiftyUsed = false;

        hintUsed = false;

        currentWinnings = 0;

        currentQuestionNumber = 0;

        gameOver = false;
    }

    // ------------------------------------------------------------
    // WELCOME SCREEN
    // ------------------------------------------------------------

    private void showWelcomeScreen() {

        clearScreen();

        printLine();

        System.out.println(
                "        WELCOME TO " + GAME_NAME
        );

        printLine();

        System.out.println();

        System.out.println(
                "             ★ ★ ★ ★ ★ ★ ★ ★ ★"
        );

        System.out.println(
                "             TEST YOUR KNOWLEDGE"
        );

        System.out.println(
                "             WIN UP TO ₹3,20,000"
        );

        System.out.println(
                "             ★ ★ ★ ★ ★ ★ ★ ★ ★"
        );

        System.out.println();

        System.out.println(
                "This is a Java console implementation of a KBC-style quiz."
        );

        System.out.println(
                "You will face " + TOTAL_QUESTIONS + " multiple-choice questions."
        );

        System.out.println(
                "Use your lifelines wisely!"
        );

        System.out.println();

        waitForEnter();
    }

    // ------------------------------------------------------------
    // PLAYER NAME
    // ------------------------------------------------------------

    private void getPlayerName() {

        clearScreen();

        System.out.println("==============================================");

        System.out.println("             CONTESTANT DETAILS");

        System.out.println("==============================================");

        System.out.print("Enter your name: ");

        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {

            playerName = "Contestant";

        } else {

            playerName = input;
        }

        System.out.println();

        System.out.println(
                "Welcome, " + playerName + "!"
        );

        waitForEnter();
    }

    // ------------------------------------------------------------
    // RULES
    // ------------------------------------------------------------

    private void showRules() {

        clearScreen();

        printLine();

        System.out.println("                     GAME RULES");

        printLine();

        System.out.println();

        System.out.println("1. There are 10 questions in this game.");

        System.out.println(
                "2. Every question has four options: A, B, C and D."
        );

        System.out.println(
                "3. Select the option you believe is correct."
        );

        System.out.println(
                "4. You can use the 50:50 lifeline only once."
        );

        System.out.println(
                "5. You can use the Hint lifeline only once."
        );

        System.out.println(
                "6. A lifeline cannot be reused after it is consumed."
        );

        System.out.println(
                "7. A wrong answer ends the game."
        );

        System.out.println(
                "8. You keep the safe-haven amount after reaching it."
        );

        System.out.println(
                "9. You may quit before answering a question."
        );

        System.out.println(
                "10. Type A, B, C or D to answer."
        );

        System.out.println();

        System.out.println(
                "             AVAILABLE LIFELINES"
        );

        System.out.println(
                "             -------------------"
        );

        System.out.println(
                "             1. 50:50"
        );

        System.out.println(
                "             2. HINT"
        );

        System.out.println();

        System.out.println(
                "             SAFE HAVEN: ₹" + FIRST_SAFE_AMOUNT
        );

        printLine();
    }

    // ------------------------------------------------------------
    // LOAD QUESTIONS
    // ------------------------------------------------------------

    private void loadQuestions() {

        questions.clear();

        questions.add(
                new Question(
                        1,
                        "Which planet is known as the Red Planet?",
                        new String[]{
                                "Earth",
                                "Mars",
                                "Jupiter",
                                "Venus"
                        },
                        1,
                        "The Red Planet is Mars because of iron oxide on its surface.",
                        "Science",
                        "Easy"
                )
        );

        questions.add(
                new Question(
                        2,
                        "Who is known as the Father of the Indian Constitution?",
                        new String[]{
                                "Mahatma Gandhi",
                                "Jawaharlal Nehru",
                                "Dr. B. R. Ambedkar",
                                "Sardar Patel"
                        },
                        2,
                        "Dr. B. R. Ambedkar chaired the Drafting Committee of the Constitution.",
                        "Indian Polity",
                        "Easy"
                )
        );

        questions.add(
                new Question(
                        3,
                        "Which language is primarily used to style web pages?",
                        new String[]{
                                "HTML",
                                "CSS",
                                "SQL",
                                "Java"
                        },
                        1,
                        "CSS stands for Cascading Style Sheets and controls presentation and layout.",
                        "Computer",
                        "Easy"
                )
        );

        questions.add(
                new Question(
                        4,
                        "What is the capital of Australia?",
                        new String[]{
                                "Sydney",
                                "Melbourne",
                                "Canberra",
                                "Perth"
                        },
                        2,
                        "Canberra is the capital city of Australia.",
                        "Geography",
                        "Easy"
                )
        );

        questions.add(
                new Question(
                        5,
                        "Which data structure follows the LIFO principle?",
                        new String[]{
                                "Queue",
                                "Stack",
                                "Array",
                                "Graph"
                        },
                        1,
                        "A stack follows Last In, First Out, commonly called LIFO.",
                        "Data Structures",
                        "Medium"
                )
        );

        questions.add(
                new Question(
                        6,
                        "Which keyword is used to inherit a class in Java?",
                        new String[]{
                                "implements",
                                "inherits",
                                "extends",
                                "super"
                        },
                        2,
                        "The extends keyword creates inheritance between classes.",
                        "Java",
                        "Medium"
                )
        );

        questions.add(
                new Question(
                        7,
                        "Which SQL command is used to retrieve data from a database?",
                        new String[]{
                                "SELECT",
                                "INSERT",
                                "DELETE",
                                "UPDATE"
                        },
                        0,
                        "SELECT is used to retrieve records from a database.",
                        "DBMS",
                        "Medium"
                )
        );

        questions.add(
                new Question(
                        8,
                        "Which operating system component manages hardware resources?",
                        new String[]{
                                "Browser",
                                "Kernel",
                                "Compiler",
                                "Text editor"
                        },
                        1,
                        "The kernel is the core part of an operating system and manages resources.",
                        "Operating Systems",
                        "Hard"
                )
        );

        questions.add(
                new Question(
                        9,
                        "What does CPU stand for?",
                        new String[]{
                                "Central Processing Unit",
                                "Computer Personal Unit",
                                "Central Program Utility",
                                "Control Processing User"
                        },
                        0,
                        "CPU means Central Processing Unit.",
                        "Computer Fundamentals",
                        "Hard"
                )
        );

        questions.add(
                new Question(
                        10,
                        "Which concept allows one interface to have many implementations?",
                        new String[]{
                                "Encapsulation",
                                "Inheritance",
                                "Polymorphism",
                                "Compilation"
                        },
                        2,
                        "Polymorphism allows the same interface or method call to behave differently.",
                        "OOP",
                        "Hard"
                )
        );
    }

    // ------------------------------------------------------------
    // PLAY GAME
    // ------------------------------------------------------------

    private void playGame() {

        for (int i = 0; i < questions.size(); i++) {

            currentQuestionNumber = i + 1;

            Question question = questions.get(i);

            boolean continueGame = askQuestion(question);

            if (!continueGame) {

                gameOver = true;

                break;
            }

            currentWinnings = PRIZE_MONEY[i];

            showCorrectAnswerMessage(question);

            if (i == 4) {

                showSafeHavenMessage();
            }

            if (i < questions.size() - 1) {

                waitForEnter();
            }
        }

        if (!gameOver) {

            showFinalWinnerScreen();
        }
    }

    // ------------------------------------------------------------
    // ASK QUESTION
    // ------------------------------------------------------------

    private boolean askQuestion(Question question) {

        boolean answered = false;

        boolean questionFinished = false;

        while (!questionFinished) {

            clearScreen();

            showHeader();

            showQuestionDetails(question);

            showLifelineStatus();

            System.out.println();

            System.out.println(
                    "Enter A/B/C/D to answer."
            );

            System.out.println(
                    "Enter 1 for 50:50, 2 for Hint, or Q to quit."
            );

            System.out.print("Your choice: ");

            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("1")) {

                useFiftyFifty(question);

                waitForEnter();

                continue;
            }

            if (input.equals("2")) {

                useHint(question);

                waitForEnter();

                continue;
            }

            if (input.equals("Q")) {

                showQuitMessage();

                return false;
            }

            if (isValidOption(input)) {

                int selectedIndex = optionToIndex(input);

                answered = true;

                questionFinished = true;

                if (selectedIndex == question.getCorrectAnswer()) {

                    return true;

                } else {

                    showWrongAnswerScreen(question, selectedIndex);

                    return false;
                }
            }

            System.out.println();

            System.out.println(
                    "Invalid input. Please enter A, B, C, D, 1, 2 or Q."
            );

            pauseBriefly();
        }

        return answered;
    }

    // ------------------------------------------------------------
    // HEADER
    // ------------------------------------------------------------

    private void showHeader() {

        printLine();

        System.out.println(
                "                  KBC JAVA EDITION"
        );

        printLine();

        System.out.println(
                "Player: " + playerName
        );

        System.out.println(
                "Question: " + currentQuestionNumber +
                        " / " + TOTAL_QUESTIONS
        );

        System.out.println(
                "Current Winnings: ₹" + currentWinnings
        );

        printLine();
    }

    // ------------------------------------------------------------
    // QUESTION DETAILS
    // ------------------------------------------------------------

    private void showQuestionDetails(Question question) {

        System.out.println();

        System.out.println(
                "Question " + question.getNumber() +
                        " for ₹" + PRIZE_MONEY[question.getNumber() - 1]
        );

        System.out.println();

        System.out.println(
                question.getQuestionText()
        );

        System.out.println();

        String[] options = question.getOptions();

        for (int i = 0; i < options.length; i++) {

            String optionText = options[i];

            if (optionText == null) {

                continue;
            }

            System.out.println(
                    OPTION_LABELS[i] + ". " + optionText
            );
        }

        System.out.println();
    }

    // ------------------------------------------------------------
    // LIFELINE STATUS
    // ------------------------------------------------------------

    private void showLifelineStatus() {

        System.out.println(
                "Lifelines:"
        );

        System.out.println(
                "50:50 -> " +
                        (fiftyFiftyUsed ? "USED" : "AVAILABLE")
        );

        System.out.println(
                "Hint  -> " +
                        (hintUsed ? "USED" : "AVAILABLE")
        );
    }

    // ------------------------------------------------------------
    // 50:50 LIFELINE
    // ------------------------------------------------------------

    private void useFiftyFifty(Question question) {

        clearScreen();

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "                 50:50 LIFELINE"
        );

        System.out.println(
                "=============================================="
        );

        if (fiftyFiftyUsed) {

            System.out.println();

            System.out.println(
                    "Sorry! You have already used the 50:50 lifeline."
            );

            return;
        }

        fiftyFiftyUsed = true;

        int correct = question.getCorrectAnswer();

        Random random = new Random();

        int incorrectToRemove = 2;

        boolean[] removed = new boolean[4];

        removed[correct] = false;

        int removedCount = 0;

        while (removedCount < incorrectToRemove) {

            int index = random.nextInt(4);

            if (index != correct && !removed[index]) {

                removed[index] = true;

                removedCount++;
            }
        }

        System.out.println();

        System.out.println(
                "Two incorrect options have been removed!"
        );

        System.out.println();

        String[] options = question.getOptions();

        for (int i = 0; i < options.length; i++) {

            if (removed[i]) {

                System.out.println(
                        OPTION_LABELS[i] + ". [REMOVED]"
                );

            } else {

                System.out.println(
                        OPTION_LABELS[i] + ". " + options[i]
                );
            }
        }

        System.out.println();

        System.out.println(
                "The lifeline has now been marked as USED."
        );
    }

    // ------------------------------------------------------------
    // HINT LIFELINE
    // ------------------------------------------------------------

    private void useHint(Question question) {

        clearScreen();

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "                   HINT"
        );

        System.out.println(
                "=============================================="
        );

        if (hintUsed) {

            System.out.println();

            System.out.println(
                    "Sorry! You have already used the Hint lifeline."
            );

            return;
        }

        hintUsed = true;

        System.out.println();

        System.out.println(
                "Hint for Question " + question.getNumber() + ":"
        );

        System.out.println();

        System.out.println(
                question.getHint()
        );

        System.out.println();

        System.out.println(
                "Think carefully before selecting your answer."
        );

        System.out.println();

        System.out.println(
                "The Hint lifeline has now been marked as USED."
        );
    }

    // ------------------------------------------------------------
    // CORRECT ANSWER MESSAGE
    // ------------------------------------------------------------

    private void showCorrectAnswerMessage(Question question) {

        clearScreen();

        System.out.println(
                "**********************************************"
        );

        System.out.println(
                "              CORRECT ANSWER!"
        );

        System.out.println(
                "**********************************************"
        );

        System.out.println();

        int correct = question.getCorrectAnswer();

        System.out.println(
                "Correct option: " +
                        OPTION_LABELS[correct]
        );

        System.out.println(
                "Answer: " +
                        question.getOptions()[correct]
        );

        System.out.println();

        System.out.println(
                "Congratulations, " + playerName + "!"
        );

        System.out.println();

        System.out.println(
                "You have won: ₹" +
                        PRIZE_MONEY[question.getNumber() - 1]
        );

        System.out.println();

        if (question.getNumber() < TOTAL_QUESTIONS) {

            System.out.println(
                    "Get ready for the next question!"
            );
        }
    }

    // ------------------------------------------------------------
    // SAFE HAVEN MESSAGE
    // ------------------------------------------------------------

    private void showSafeHavenMessage() {

        System.out.println();

        printLine();

        System.out.println(
                "                 SAFE HAVEN REACHED"
        );

        System.out.println();

        System.out.println(
                "You have crossed Question 5."
        );

        System.out.println(
                "Your minimum protected winnings are ₹" +
                        FIRST_SAFE_AMOUNT
        );

        printLine();
    }

    // ------------------------------------------------------------
    // WRONG ANSWER SCREEN
    // ------------------------------------------------------------

    private void showWrongAnswerScreen(
            Question question,
            int selectedIndex) {

        clearScreen();

        System.out.println(
                "##############################################"
        );

        System.out.println(
                "                 WRONG ANSWER"
        );

        System.out.println(
                "##############################################"
        );

        System.out.println();

        System.out.println(
                "You selected: " +
                        OPTION_LABELS[selectedIndex] +
                        ". " +
                        question.getOptions()[selectedIndex]
        );

        System.out.println();

        int correct = question.getCorrectAnswer();

        System.out.println(
                "Correct answer: " +
                        OPTION_LABELS[correct] +
                        ". " +
                        question.getOptions()[correct]
        );

        System.out.println();

        System.out.println(
                "Explanation:"
        );

        System.out.println(
                question.getExplanation()
        );

        System.out.println();

        long guaranteedMoney = calculateGuaranteedAmount();

        System.out.println(
                "Your guaranteed winnings: ₹" +
                        guaranteedMoney
        );

        System.out.println();

        System.out.println(
                "Thank you for playing, " + playerName + "!"
        );

        printLine();

        waitForEnter();
    }

    // ------------------------------------------------------------
    // GUARANTEED AMOUNT
    // ------------------------------------------------------------

    private long calculateGuaranteedAmount() {

        if (currentQuestionNumber >= 5) {

            return FIRST_SAFE_AMOUNT;
        }

        return 0;
    }

    // ------------------------------------------------------------
    // FINAL WINNER SCREEN
    // ------------------------------------------------------------

    private void showFinalWinnerScreen() {

        clearScreen();

        System.out.println(
                "================================================"
        );

        System.out.println(
                "              CONGRATULATIONS!"
        );

        System.out.println(
                "================================================"
        );

        System.out.println();

        System.out.println(
                "             ★ ★ ★ ★ ★ ★ ★"
        );

        System.out.println(
                "             YOU ARE THE WINNER"
        );

        System.out.println(
                "             ★ ★ ★ ★ ★ ★ ★"
        );

        System.out.println();

        System.out.println(
                "Contestant: " + playerName
        );

        System.out.println(
                "Questions completed: " + TOTAL_QUESTIONS
        );

        System.out.println(
                "Final prize: ₹" + currentWinnings
        );

        System.out.println();

        System.out.println(
                "You successfully answered all 10 questions!"
        );

        System.out.println();

        printLine();

        System.out.println(
                "                 WELL PLAYED!"
        );

        printLine();

        waitForEnter();
    }

    // ------------------------------------------------------------
    // QUIT MESSAGE
    // ------------------------------------------------------------

    private void showQuitMessage() {

        clearScreen();

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "                 GAME QUIT"
        );

        System.out.println(
                "=============================================="
        );

        System.out.println();

        System.out.println(
                "You decided to quit the game."
        );

        System.out.println();

        System.out.println(
                "Current winnings: ₹" + currentWinnings
        );

        System.out.println();

        System.out.println(
                "Thank you for participating, " + playerName + "!"
        );
    }

    // ------------------------------------------------------------
    // REPLAY
    // ------------------------------------------------------------

    private boolean askForReplay() {

        System.out.println();

        System.out.println(
                "Would you like to play again?"
        );

        System.out.println(
                "Enter Y for Yes or N for No."
        );

        while (true) {

            System.out.print("Choice: ");

            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("Y")) {

                return true;
            }

            if (input.equals("N")) {

                return false;
            }

            System.out.println(
                    "Invalid input. Please enter Y or N."
            );
        }
    }

    // ------------------------------------------------------------
    // GOODBYE
    // ------------------------------------------------------------

    private void showGoodbye() {

        clearScreen();

        printLine();

        System.out.println(
                "             THANK YOU FOR PLAYING!"
        );

        System.out.println();

        System.out.println(
                "                 " + playerName
        );

        System.out.println();

        System.out.println(
                "              KBC JAVA EDITION"
        );

        System.out.println();

        System.out.println(
                "                 GOODBYE!"
        );

        printLine();
    }

    // ------------------------------------------------------------
    // INPUT VALIDATION
    // ------------------------------------------------------------

    private boolean isValidOption(String input) {

        return input.equals("A")
                || input.equals("B")
                || input.equals("C")
                || input.equals("D");
    }

    // ------------------------------------------------------------
    // OPTION TO INDEX
    // ------------------------------------------------------------

    private int optionToIndex(String input) {

        switch (input) {

            case "A":
                return 0;

            case "B":
                return 1;

            case "C":
                return 2;

            case "D":
                return 3;

            default:
                return -1;
        }
    }

    // ------------------------------------------------------------
    // PRINT LINE
    // ------------------------------------------------------------

    private void printLine() {

        System.out.println(
                "================================================"
        );
    }

    // ------------------------------------------------------------
    // WAIT FOR ENTER
    // ------------------------------------------------------------

    private void waitForEnter() {

        System.out.println();

        System.out.print(
                "Press ENTER to continue..."
        );

        scanner.nextLine();
    }

    // ------------------------------------------------------------
    // PAUSE
    // ------------------------------------------------------------

    private void pauseBriefly() {

        try {

            Thread.sleep(700);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }

    // ------------------------------------------------------------
    // CLEAR SCREEN
    // ------------------------------------------------------------

    private void clearScreen() {

        /*
         * ANSI escape sequences work in many modern terminals.
         * The extra blank lines also make the program usable in
         * terminals where ANSI commands are not supported.
         */

        System.out.print("\033[H\033[2J");

        System.out.flush();

        for (int i = 0; i < 3; i++) {

            System.out.println();
        }
    }

    // ------------------------------------------------------------
    // QUESTION INNER CLASS
    // ------------------------------------------------------------

        private static class KBCWindow {

                private final KBCGame game = new KBCGame();

                private final JFrame frame = new JFrame("KBC Java Edition");

                private final JLabel progress = new JLabel();

                private final JLabel winnings = new JLabel();

                private final JLabel questionLabel = new JLabel();

                private final JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 12, 12));

                private final JButton fiftyFiftyButton = new JButton("50:50");

                private final JButton hintButton = new JButton("Hint");

                private int questionIndex;

                private KBCWindow() {

                        game.loadQuestions();
                }

                private void open() {

                        String name = JOptionPane.showInputDialog(
                                        null, "Enter your name:", "Welcome to KBC", JOptionPane.PLAIN_MESSAGE);

                        if (name != null && !name.trim().isEmpty()) {

                                game.playerName = name.trim();
                        }

                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setMinimumSize(new Dimension(760, 520));
                        frame.setSize(900, 620);
                        frame.setLocationRelativeTo(null);
                        buildScreen();
                        showQuestion();
                        frame.setVisible(true);
                }

                private void buildScreen() {

                        JPanel root = new JPanel(new BorderLayout(18, 18));
                        root.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
                        root.setBackground(new Color(11, 35, 74));

                        JLabel title = new JLabel("KAUN BANEGA CROREPATI", SwingConstants.CENTER);
                        title.setForeground(new Color(255, 214, 91));
                        title.setFont(new Font("Serif", Font.BOLD, 30));
                        root.add(title, BorderLayout.NORTH);

                        JPanel center = new JPanel(new BorderLayout(14, 14));
                        center.setOpaque(false);

                        JPanel status = new JPanel(new BorderLayout());
                        status.setOpaque(false);
                        progress.setForeground(Color.WHITE);
                        winnings.setForeground(Color.WHITE);
                        status.add(progress, BorderLayout.WEST);
                        status.add(winnings, BorderLayout.EAST);
                        center.add(status, BorderLayout.NORTH);

                        JPanel questionBox = new JPanel(new BorderLayout(12, 20));
                        questionBox.setBorder(BorderFactory.createCompoundBorder(
                                        BorderFactory.createLineBorder(new Color(255, 214, 91), 2),
                                        BorderFactory.createEmptyBorder(22, 24, 22, 24)));
                        questionBox.setBackground(new Color(20, 61, 116));
                        questionLabel.setForeground(Color.WHITE);
                        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 21));
                        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        questionBox.add(questionLabel, BorderLayout.NORTH);

                        optionsPanel.setOpaque(false);
                        questionBox.add(optionsPanel, BorderLayout.CENTER);
                        center.add(questionBox, BorderLayout.CENTER);
                        root.add(center, BorderLayout.CENTER);

                        JPanel lifelines = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
                        lifelines.setOpaque(false);
                        styleButton(fiftyFiftyButton);
                        styleButton(hintButton);
                        fiftyFiftyButton.addActionListener(event -> useFiftyFifty());
                        hintButton.addActionListener(event -> useHint());
                        lifelines.add(fiftyFiftyButton);
                        lifelines.add(hintButton);
                        root.add(lifelines, BorderLayout.SOUTH);

                        frame.setContentPane(root);
                }

                private void styleButton(JButton button) {

                        button.setFocusPainted(false);
                        button.setFont(new Font("SansSerif", Font.BOLD, 15));
                        button.setBackground(new Color(255, 214, 91));
                        button.setForeground(new Color(11, 35, 74));
                }

                private void showQuestion() {

                        game.currentQuestionNumber = questionIndex + 1;
                        game.currentWinnings = questionIndex == 0 ? 0 : PRIZE_MONEY[questionIndex - 1];
                        Question question = game.questions.get(questionIndex);

                        progress.setText("Question " + (questionIndex + 1) + " / " + TOTAL_QUESTIONS);
                        winnings.setText("Winnings: ₹" + game.currentWinnings);
                        questionLabel.setText("<html><div style='text-align:center;width:650px'>"
                                        + question.getQuestionText() + "</div></html>");
                        optionsPanel.removeAll();

                        String[] options = question.getOptions();
                        for (int i = 0; i < options.length; i++) {

                                final int selected = i;
                                JButton option = new JButton(OPTION_LABELS[i] + ".  " + options[i]);
                                option.setHorizontalAlignment(SwingConstants.LEFT);
                                option.setFont(new Font("SansSerif", Font.PLAIN, 16));
                                option.setFocusPainted(false);
                                option.addActionListener(event -> answer(selected));
                                optionsPanel.add(option);
                        }

                        fiftyFiftyButton.setEnabled(!game.fiftyFiftyUsed);
                        hintButton.setEnabled(!game.hintUsed);
                        optionsPanel.revalidate();
                        optionsPanel.repaint();
                }

                private void answer(int selected) {

                        Question question = game.questions.get(questionIndex);
                        if (selected != question.getCorrectAnswer()) {

                                JOptionPane.showMessageDialog(frame,
                                                "Wrong answer. The correct answer is "
                                                                + question.getOptions()[question.getCorrectAnswer()]
                                                                + ".\nYou won ₹" + calculateGuaranteedAmount() + ".",
                                                "Game over", JOptionPane.ERROR_MESSAGE);
                                restart();
                                return;
                        }

                        game.currentWinnings = PRIZE_MONEY[questionIndex];
                        if (questionIndex == TOTAL_QUESTIONS - 1) {

                                JOptionPane.showMessageDialog(frame,
                                                "Congratulations, " + game.playerName + "!\nFinal prize: ₹"
                                                                + game.currentWinnings,
                                                "Winner", JOptionPane.INFORMATION_MESSAGE);
                                restart();
                                return;
                        }

                        JOptionPane.showMessageDialog(frame,
                                        "Correct! You have won ₹" + game.currentWinnings + ".",
                                        "Correct answer", JOptionPane.INFORMATION_MESSAGE);
                        questionIndex++;
                        showQuestion();
                }

                private void useFiftyFifty() {

                        game.fiftyFiftyUsed = true;
                        Question question = game.questions.get(questionIndex);
                        ArrayList<Integer> removable = new ArrayList<>();
                        for (int i = 0; i < question.getOptions().length; i++) {
                                if (i != question.getCorrectAnswer()) removable.add(i);
                        }
                        Collections.shuffle(removable);
                        for (int i = 0; i < 2; i++) {
                                int removed = removable.get(i);
                                optionsPanel.getComponent(removed).setEnabled(false);
                                optionsPanel.getComponent(removed).setForeground(Color.GRAY);
                        }
                        fiftyFiftyButton.setEnabled(false);
                }

                private void useHint() {

                        game.hintUsed = true;
                        hintButton.setEnabled(false);
                        JOptionPane.showMessageDialog(frame,
                                        game.questions.get(questionIndex).getHint(), "Hint", JOptionPane.INFORMATION_MESSAGE);
                }

                private long calculateGuaranteedAmount() {

                        return game.currentQuestionNumber >= 5 ? FIRST_SAFE_AMOUNT : 0;
                }

                private void restart() {

                        questionIndex = 0;
                        game.resetGame();
                        showQuestion();
                }
        }

    private static class Question {

        private final int number;

        private final String questionText;

        private final String[] options;

        private final int correctAnswer;

        private final String hint;

        private final String explanation;

        private final String category;

        private final String difficulty;

        public Question(
                int number,
                String questionText,
                String[] options,
                int correctAnswer,
                String hint,
                String category,
                String difficulty) {

            this.number = number;

            this.questionText = questionText;

            this.options = options;

            this.correctAnswer = correctAnswer;

            this.hint = hint;

            this.explanation =
                    createExplanation(options, correctAnswer);

            this.category = category;

            this.difficulty = difficulty;
        }

        private static String createExplanation(
                String[] options,
                int correctAnswer) {

            return "The correct answer is "
                    + options[correctAnswer]
                    + ".";
        }

        public int getNumber() {

            return number;
        }

        public String getQuestionText() {

            return questionText;
        }

        public String[] getOptions() {

            return options;
        }

        public int getCorrectAnswer() {

            return correctAnswer;
        }

        public String getHint() {

            return hint;
        }

        public String getExplanation() {

            return explanation;
        }

        public String getCategory() {

            return category;
        }

        public String getDifficulty() {

            return difficulty;
        }
    }
}

/*
 * ================================================================
 * PROJECT EXPLANATION
 * ================================================================
 *
 * The program demonstrates several Java concepts.
 *
 * 1. CLASS
 * ---------------------------------------------------------------
 * KBCGame is the main class.
 *
 * Question is a separate inner class used to represent a question.
 *
 *
 * 2. OBJECT
 * ---------------------------------------------------------------
 * The main method creates an object:
 *
 *     KBCGame game = new KBCGame();
 *
 *
 * 3. ENCAPSULATION
 * ---------------------------------------------------------------
 * Variables inside Question are private.
 *
 * They are accessed through getter methods.
 *
 *
 * 4. ARRAY
 * ---------------------------------------------------------------
 * Options are stored in a String array.
 *
 * Prize amounts are stored in a long array.
 *
 *
 * 5. ARRAYLIST
 * ---------------------------------------------------------------
 * All questions are stored in:
 *
 *     ArrayList<Question>
 *
 *
 * 6. CONDITIONAL STATEMENTS
 * ---------------------------------------------------------------
 * if, else and switch are used for:
 *
 * - Checking answers
 * - Checking lifelines
 * - Validating input
 * - Controlling the game
 *
 *
 * 7. LOOPS
 * ---------------------------------------------------------------
 * for loops are used to display and process questions.
 *
 * while loops are used for input validation.
 *
 *
 * 8. METHODS
 * ---------------------------------------------------------------
 * The program is divided into many methods such as:
 *
 * - showWelcomeScreen()
 * - loadQuestions()
 * - showRules()
 * - playGame()
 * - askQuestion()
 * - useFiftyFifty()
 * - useHint()
 * - showCorrectAnswerMessage()
 * - showWrongAnswerScreen()
 * - askForReplay()
 *
 *
 * 9. RANDOM CLASS
 * ---------------------------------------------------------------
 * Random is used by the 50:50 lifeline to randomly remove
 * two incorrect options.
 *
 *
 * 10. SCANNER
 * ---------------------------------------------------------------
 * Scanner reads input from the user.
 *
 *
 * 11. EXCEPTION HANDLING
 * ---------------------------------------------------------------
 * Thread.sleep() can generate InterruptedException.
 * The program handles it using try-catch.
 *
 *
 * ================================================================
 * POSSIBLE FUTURE IMPROVEMENTS
 * ================================================================
 *
 * 1. Add a GUI using Java Swing.
 *
 * 2. Add JavaFX animations.
 *
 * 3. Add sound effects.
 *
 * 4. Add a timer for every question.
 *
 * 5. Add more questions using a database.
 *
 * 6. Add user login.
 *
 * 7. Store high scores in MySQL.
 *
 * 8. Add an Ask the Audience lifeline.
 *
 * 9. Add Phone a Friend.
 *
 * 10. Add difficulty levels.
 *
 * 11. Randomize questions.
 *
 * 12. Randomize options.
 *
 * 13. Add player statistics.
 *
 * 14. Add a leaderboard.
 *
 * 15. Create a graphical KBC-style interface.
 *
 * ================================================================
 */
