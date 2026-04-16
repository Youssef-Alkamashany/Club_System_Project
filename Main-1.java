import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main extends JFrame {

    ArrayList<Club>   clubs   = new ArrayList<>();
    ArrayList<Member> members = new ArrayList<>();
    ArrayList<Sport>  sports  = new ArrayList<>();

    JTextArea outputArea;

    public Main() {
        insertData();
        buildGUI();
    }

    // ─────────────────────────────────────────────
    //  INSERT INITIAL DATA
    // ─────────────────────────────────────────────
    void insertData() {
        clubs.add(new Club("Zamalek",    "5", "Ahmed Ali",    "Cairo"));
        clubs.add(new Club("Ahly",       "8", "Mohamed Omar", "Cairo"));
        clubs.add(new Club("Smouha",     "3", "Karim Hassan", "Alexandria"));
        clubs.add(new Club("Ittihad",    "4", "Samy Nour",    "Alexandria"));
        clubs.add(new Club("Wadi Degla", "6", "Tarek Saad",   "Giza"));

        members.add(new Member(3, "Nour Khaled", "01001234567", 2));
        members.add(new Member(1, "Ali Hassan",  "01112345678", 0));
        members.add(new Member(5, "Sara Ahmed",  "01223456789", 1));
        members.add(new Member(2, "Omar Samir",  "01534567890", 3));
        members.add(new Member(4, "Mona Tarek",  "01045678901", 2));

        sports.add(new Sport("Football",   101, 10));
        sports.add(new Sport("Basketball", 102,  6));
        sports.add(new Sport("Tennis",     103,  4));
        sports.add(new Sport("Swimming",   104,  5));
        sports.add(new Sport("Volleyball", 105,  7));
    }

    // ─────────────────────────────────────────────
    //  1. BUBBLE SORT  →  Sort Clubs by Name
    //  Big O: O(n^2)
    // ─────────────────────────────────────────────
    void bubbleSortClubs(ArrayList<Club> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (list.get(j).name.compareTo(list.get(j + 1).name) > 0) {
                    Club temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    // ─────────────────────────────────────────────
    //  2. SELECTION SORT  →  Sort Members by ID
    //  Big O: O(n^2)
    // ─────────────────────────────────────────────
    void selectionSortMembers(ArrayList<Member> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (list.get(j).id < list.get(minIndex).id) {
                    minIndex = j;
                }
            }
            Member temp = list.get(i);
            list.set(i, list.get(minIndex));
            list.set(minIndex, temp);
        }
    }

    // ─────────────────────────────────────────────
    //  3. INSERTION SORT  →  Sort Sports by Name
    //  Big O: O(n^2)
    // ─────────────────────────────────────────────
    void insertionSortSports(ArrayList<Sport> list) {
        int n = list.size();
        for (int i = 1; i < n; i++) {
            Sport current = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j).name.compareTo(current.name) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, current);
        }
    }

    // ─────────────────────────────────────────────
    //  4. MERGE SORT  →  Sort Clubs by Name
    //  Big O: O(n log n)
    // ─────────────────────────────────────────────
    void mergeSortClubs(ArrayList<Club> list, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortClubs(list, left, mid);
            mergeSortClubs(list, mid + 1, right);
            mergeClubs(list, left, mid, right);
        }
    }

    void mergeClubs(ArrayList<Club> list, int left, int mid, int right) {
        ArrayList<Club> leftPart  = new ArrayList<>();
        ArrayList<Club> rightPart = new ArrayList<>();

        for (int i = left; i <= mid; i++)       leftPart.add(list.get(i));
        for (int i = mid + 1; i <= right; i++)  rightPart.add(list.get(i));

        int i = 0, j = 0, k = left;
        while (i < leftPart.size() && j < rightPart.size()) {
            if (leftPart.get(i).name.compareTo(rightPart.get(j).name) <= 0) {
                list.set(k++, leftPart.get(i++));
            } else {
                list.set(k++, rightPart.get(j++));
            }
        }
        while (i < leftPart.size())  list.set(k++, leftPart.get(i++));
        while (j < rightPart.size()) list.set(k++, rightPart.get(j++));
    }

    // ─────────────────────────────────────────────
    //  5. QUICK SORT  →  Sort Members by ID
    //  Big O: O(n log n) average  /  O(n^2) worst
    // ─────────────────────────────────────────────
    void quickSortMembers(ArrayList<Member> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionMembers(list, low, high);
            quickSortMembers(list, low, pivotIndex - 1);
            quickSortMembers(list, pivotIndex + 1, high);
        }
    }

    int partitionMembers(ArrayList<Member> list, int low, int high) {
        int pivot = list.get(high).id;
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).id <= pivot) {
                i++;
                Member temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        Member temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }

    // ─────────────────────────────────────────────
    //  BINARY SEARCH  →  Search Club by Name
    //  Big O: O(log n)   (list must be sorted first)
    // ─────────────────────────────────────────────
    int binarySearchClub(ArrayList<Club> list, String targetName) {
        int left = 0, right = list.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int cmp = list.get(mid).name.compareToIgnoreCase(targetName);
            if (cmp == 0)       return mid;
            else if (cmp < 0)   left  = mid + 1;
            else                right = mid - 1;
        }
        return -1;
    }

    // ─────────────────────────────────────────────
    //  BINARY SEARCH  →  Search Member by ID
    //  Big O: O(log n)   (list must be sorted first)
    // ─────────────────────────────────────────────
    int binarySearchMember(ArrayList<Member> list, int targetId) {
        int left = 0, right = list.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if      (list.get(mid).id == targetId) return mid;
            else if (list.get(mid).id  < targetId) left  = mid + 1;
            else                                   right = mid - 1;
        }
        return -1;
    }

    // ─────────────────────────────────────────────
    //  GUI  (BONUS)
    // ─────────────────────────────────────────────
    void buildGUI() {
        setTitle("Club System - Algorithms Project");
        setSize(900, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title bar
        JLabel title = new JLabel("  Club Management System", JLabel.LEFT);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBackground(new Color(10, 10, 40));
        title.setOpaque(true);
        title.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        // Output area
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setBackground(new Color(15, 15, 35));
        outputArea.setForeground(new Color(0, 230, 100));
        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);

        // Buttons panel
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(0, 1, 8, 8));
        btnPanel.setBackground(new Color(25, 25, 55));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(15, 12, 15, 12));

        // --- Sorting buttons ---
        addSectionLabel(btnPanel, "── Sorting ──");
        JButton btnBubble    = makeButton("Bubble Sort (Clubs)");
        JButton btnSelection = makeButton("Selection Sort (Members)");
        JButton btnInsertion = makeButton("Insertion Sort (Sports)");
        JButton btnMerge     = makeButton("Merge Sort (Clubs)");
        JButton btnQuick     = makeButton("Quick Sort (Members)");
        btnPanel.add(btnBubble);
        btnPanel.add(btnSelection);
        btnPanel.add(btnInsertion);
        btnPanel.add(btnMerge);
        btnPanel.add(btnQuick);

        // --- Search buttons ---
        addSectionLabel(btnPanel, "── Search ──");
        JButton btnSearchClub    = makeButton("Search Club by Name");
        JButton btnSearchMember  = makeButton("Search Member by ID");
        btnPanel.add(btnSearchClub);
        btnPanel.add(btnSearchMember);

        // --- Add / Show buttons ---
        addSectionLabel(btnPanel, "── Data ──");
        JButton btnAddClub    = makeButton("Add Club");
        JButton btnAddMember  = makeButton("Add Member");
        JButton btnAddSport   = makeButton("Add Sport");
        JButton btnShowAll    = makeButton("Show All Data");
        btnPanel.add(btnAddClub);
        btnPanel.add(btnAddMember);
        btnPanel.add(btnAddSport);
        btnPanel.add(btnShowAll);

        add(title,   BorderLayout.NORTH);
        add(btnPanel, BorderLayout.WEST);
        add(scroll,   BorderLayout.CENTER);

        // Button actions
        btnBubble.addActionListener(e -> {
            bubbleSortClubs(clubs);
            print("Clubs sorted by Name  —  Bubble Sort  |  Big O: O(n²)\n\n", clubs);
        });

        btnSelection.addActionListener(e -> {
            selectionSortMembers(members);
            print("Members sorted by ID  —  Selection Sort  |  Big O: O(n²)\n\n", members);
        });

        btnInsertion.addActionListener(e -> {
            insertionSortSports(sports);
            print("Sports sorted by Name  —  Insertion Sort  |  Big O: O(n²)\n\n", sports);
        });

        btnMerge.addActionListener(e -> {
            mergeSortClubs(clubs, 0, clubs.size() - 1);
            print("Clubs sorted by Name  —  Merge Sort  |  Big O: O(n log n)\n\n", clubs);
        });

        btnQuick.addActionListener(e -> {
            quickSortMembers(members, 0, members.size() - 1);
            print("Members sorted by ID  —  Quick Sort  |  Big O: O(n log n) avg\n\n", members);
        });

        btnSearchClub.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter Club Name:");
            if (input == null || input.isEmpty()) return;
            bubbleSortClubs(clubs);
            int idx = binarySearchClub(clubs, input.trim());
            outputArea.setText("Binary Search  →  Club by Name  |  Big O: O(log n)\n\n");
            if (idx != -1) outputArea.append("Found: " + clubs.get(idx));
            else           outputArea.append("Club \"" + input + "\" was not found.");
        });

        btnSearchMember.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter Member ID:");
            if (input == null || input.isEmpty()) return;
            try {
                int id = Integer.parseInt(input.trim());
                selectionSortMembers(members);
                int idx = binarySearchMember(members, id);
                outputArea.setText("Binary Search  →  Member by ID  |  Big O: O(log n)\n\n");
                if (idx != -1) outputArea.append("Found: " + members.get(idx));
                else           outputArea.append("Member with ID " + id + " was not found.");
            } catch (NumberFormatException ex) {
                outputArea.setText("Please enter a valid number.");
            }
        });

        btnAddClub.addActionListener(e -> {
            String n  = JOptionPane.showInputDialog(this, "Club Name:");
            String b  = JOptionPane.showInputDialog(this, "Branches:");
            String mg = JOptionPane.showInputDialog(this, "Manager:");
            String l  = JOptionPane.showInputDialog(this, "Location:");
            if (n != null && !n.isEmpty()) {
                clubs.add(new Club(n, b, mg, l));
                outputArea.setText("Club added!\n\n" + clubs.get(clubs.size() - 1));
            }
        });

        btnAddMember.addActionListener(e -> {
            try {
                int    id = Integer.parseInt(JOptionPane.showInputDialog(this, "Member ID:"));
                String n  = JOptionPane.showInputDialog(this, "Name:");
                String ph = JOptionPane.showInputDialog(this, "Phone:");
                int    ch = Integer.parseInt(JOptionPane.showInputDialog(this, "Number of Children:"));
                members.add(new Member(id, n, ph, ch));
                outputArea.setText("Member added!\n\n" + members.get(members.size() - 1));
            } catch (Exception ex) {
                outputArea.setText("Invalid input.");
            }
        });

        btnAddSport.addActionListener(e -> {
            try {
                String n  = JOptionPane.showInputDialog(this, "Sport Name:");
                int    id = Integer.parseInt(JOptionPane.showInputDialog(this, "Sport ID:"));
                int    t  = Integer.parseInt(JOptionPane.showInputDialog(this, "Number of Teams:"));
                sports.add(new Sport(n, id, t));
                outputArea.setText("Sport added!\n\n" + sports.get(sports.size() - 1));
            } catch (Exception ex) {
                outputArea.setText("Invalid input.");
            }
        });

        btnShowAll.addActionListener(e -> showAll());

        showAll();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ─────────────────────────────────────────────
    //  HELPER METHODS
    // ─────────────────────────────────────────────
    void showAll() {
        outputArea.setText("========== ALL DATA ==========\n\n");
        outputArea.append("--- Clubs ---\n");
        for (Club c : clubs)     outputArea.append(c + "\n");
        outputArea.append("\n--- Members ---\n");
        for (Member m : members) outputArea.append(m + "\n");
        outputArea.append("\n--- Sports ---\n");
        for (Sport s : sports)   outputArea.append(s + "\n");
    }

    void print(String header, ArrayList<?> list) {
        outputArea.setText(header);
        for (Object obj : list) outputArea.append(obj + "\n");
    }

    void addSectionLabel(JPanel panel, String text) {
        JLabel lbl = new JLabel(text, JLabel.CENTER);
        lbl.setForeground(Color.CYAN);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lbl);
    }

    JButton makeButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(40, 40, 100));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.PLAIN, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 180)));
        return btn;
    }

    // ─────────────────────────────────────────────
    //  MAIN
    // ─────────────────────────────────────────────
    public static void main(String[] args) {
        insertDataAndRun();
    }

    static void insertDataAndRun() {
        new Main();
    }
}
