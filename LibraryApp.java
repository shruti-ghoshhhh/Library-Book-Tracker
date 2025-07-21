import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LibraryApp extends JFrame {
    private Library lib;
    private JTextArea output;

    public LibraryApp() {
        lib = new Library(); // Uses your existing Library class
        output = new JTextArea(12, 40);
        output.setEditable(false);

        // Create buttons
        JButton showBtn = new JButton("Show Books");
        JButton addBtn = new JButton("Add Book");
        JButton borrowBtn = new JButton("Borrow Book");
        JButton returnBtn = new JButton("Return Book");

        // Add action listeners
        showBtn.addActionListener(_ -> showBooks());
        addBtn.addActionListener(_ -> addBook());
        borrowBtn.addActionListener(_ -> borrowBook());
        returnBtn.addActionListener(_ -> returnBook());

        // Button panel (top row)
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(showBtn);
        buttonPanel.add(addBtn);
        buttonPanel.add(borrowBtn);
        buttonPanel.add(returnBtn);

        // Scrollable output area (center)
        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Library Output"));

        // Main panel (vertical stacking)
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Frame setup
        this.setTitle("📚 Library Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null); // Center the window
        this.setVisible(true);
    }

    private void showBooks() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Book> books = lib.getBooks();
        if (books.isEmpty()) {
            sb.append("No books in the library.\n");
        } else {
            for (int i = 0; i < books.size(); i++) {
                Book b = books.get(i);
                String status = b.isBorrowed() ? "Borrowed" : "Available";
                sb.append((i + 1) + ". " + b.getBookname() + " by " + b.getAuthorname() + " - " + status + "\n");
            }
        }
        output.setText(sb.toString());
    }

    private void addBook() {
        String name = JOptionPane.showInputDialog(this, "Enter book name:");
        if (name == null || name.trim().isEmpty()) {
            output.setText("Book name can't be empty.");
            return;
        }

        String author = JOptionPane.showInputDialog(this, "Enter author name:");
        if (author == null || author.trim().isEmpty()) {
            output.setText("Author name can't be empty.");
            return;
        }

        boolean added = lib.addBook(name.trim(), author.trim());
        if (added) {
            output.setText("Book added successfully.");
        } else {
            output.setText("Failed to add book.");
        }
    }

    private void borrowBook() {
        String input = JOptionPane.showInputDialog(this, "Enter book index to borrow:");
        try {
            int index = Integer.parseInt(input) - 1;
            if (lib.borrowBook(index)) {
                output.setText("Book borrowed successfully.");
            } else {
                output.setText("Cannot borrow this book. It may already be borrowed or the index is invalid.");
            }
        } catch (Exception ex) {
            output.setText("Invalid input.");
        }
    }

    private void returnBook() {
        String input = JOptionPane.showInputDialog(this, "Enter book index to return:");
        try {
            int index = Integer.parseInt(input) - 1;
            ArrayList<Book> books = lib.getBooks();
            if (index < 0 || index >= books.size()) {
                output.setText("Invalid index.");
                return;
            }

            Book book = books.get(index);
            if (!book.isBorrowed()) {
                output.setText("This book wasn't borrowed.");
                return;
            }

            lib.returnBook(index);
            output.setText("Book returned successfully.");
        } catch (Exception ex) {
            output.setText("Invalid input.");
        }
    }

    public static void main(String[] args) {
    try {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }

        // 🌑 Custom Dark Mode Colors
        Color bg = new Color(30, 30, 30);     // dark background
        Color fg = new Color(220, 220, 220);  // light foreground
        Color accent = new Color(60, 63, 65); // dark grayish border

        // 🌕 Fonts and UI overrides
        UIManager.put("control", bg);
        UIManager.put("info", bg);
        UIManager.put("nimbusBase", new Color(50, 50, 50));
        UIManager.put("nimbusBlueGrey", new Color(60, 63, 65));
        UIManager.put("nimbusLightBackground", bg);
        UIManager.put("text", fg);
        UIManager.put("menuText", fg);
        UIManager.put("controlText", fg);
        UIManager.put("textForeground", fg);
        UIManager.put("Label.foreground", fg);
        UIManager.put("TextArea.background", accent);
        UIManager.put("TextArea.foreground", fg);
        UIManager.put("TextArea.caretForeground", fg);
        UIManager.put("Button.background", accent);
        UIManager.put("Button.foreground", fg);
        UIManager.put("Panel.background", bg);
        UIManager.put("ScrollPane.background", bg);
        UIManager.put("TitledBorder.border", BorderFactory.createLineBorder(fg));

        // 🖋 Fonts
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("TextArea.font", new Font("Consolas", Font.PLAIN, 13));
    } catch (Exception e) {
        System.out.println("Nimbus not supported. Using default Look & Feel.");
    }

    SwingUtilities.invokeLater(() -> new LibraryApp());
}

}
