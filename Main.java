
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Main extends JFrame {

    JTextField nameField, locationField, searchField;
    JTable table;
    DefaultTableModel model;
    ArrayList<String[]> items = new ArrayList<>();

    Main() {
        setTitle("Campus Lost & Found System");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel(
                "Campus Lost & Found System",
                SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(15, 5, 10, 5));
        add(title, BorderLayout.NORTH);

        // Input form
        JPanel form = new JPanel(new GridLayout(2, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        nameField = new JTextField();
        locationField = new JTextField();

        form.add(new JLabel("Item Name:"));
        form.add(nameField);
        form.add(new JLabel("Location:"));
        form.add(locationField);

        // Buttons
        JButton lostBtn = new JButton("Report Lost");
        JButton foundBtn = new JButton("Report Found");
        JButton viewBtn = new JButton("View All");
        JButton searchBtn = new JButton("Search");
        JButton clearBtn = new JButton("Clear Search");

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(lostBtn);
        buttons.add(foundBtn);
        buttons.add(viewBtn);
        buttons.add(searchBtn);
        buttons.add(clearBtn);

        // Search field
        searchField = new JTextField(18);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search item:"));
        searchPanel.add(searchField);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(form, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(buttons, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.CENTER);

        // Table
        String[] columns = {"ID", "Item Name", "Location", "Type"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(750, 250));
        add(scroll, BorderLayout.SOUTH);

        // Button actions
        lostBtn.addActionListener(e -> addItem("LOST"));
        foundBtn.addActionListener(e -> addItem("FOUND"));
        viewBtn.addActionListener(e -> showItems(items));
        searchBtn.addActionListener(e -> searchItems());
        clearBtn.addActionListener(e -> {
            searchField.setText("");
            showItems(items);
        });

        // Enter key also performs search
        searchField.addActionListener(e -> searchItems());

        setVisible(true);
    }

    void addItem(String type) {
        String name = nameField.getText().trim();
        String location = locationField.getText().trim();

        if (name.isEmpty() || location.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter item name and location.");
            return;
        }

        String[] item = {
            String.valueOf(items.size() + 1),
            name,
            location,
            type
        };

        items.add(item);
        showItems(items);

        nameField.setText("");
        locationField.setText("");

        JOptionPane.showMessageDialog(this,
                type + " item added successfully!");
    }

    void showItems(ArrayList<String[]> list) {
        model.setRowCount(0);

        for (String[] item : list) {
            model.addRow(item);
        }
    }

    void searchItems() {
        String query = searchField.getText().trim();

        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter an item name to search.");
            return;
        }

        ArrayList<String[]> results = new ArrayList<>();

        for (String[] item : items) {
            if (item[1].toLowerCase()
                    .contains(query.toLowerCase())) {
                results.add(item);
            }
        }

        showItems(results);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No matching item found.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
