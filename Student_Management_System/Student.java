import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Objects;

public class Student extends JFrame {
    private JTextField user, port, dbnm;
    private JPasswordField pwd;
    private Connection con;

    private JTextField name, rollnumber;
    private JComboBox grade;

    public Student() {
        initializeUI();
       
    }

    private void initializeUI() {
        setTitle("LOGIN");
        setLayout(new GridLayout(5, 2));
        setLocationRelativeTo(null);
        setSize(600, 300);
        add(new JLabel("USERNAME:"));
        user = new JTextField();
        add(user);
        add(new JLabel("PASSWORD:"));
        pwd = new JPasswordField();
        add(pwd);
        add(new JLabel("PORT:"));
        port = new JTextField();
        add(port);
        add(new JLabel("DATABASE NAME:"));
        dbnm = new JTextField();
        add(dbnm);
        JButton next = new JButton("Login");
        next.addActionListener(e -> connectToDatabase());
        add(next);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }
    void connectToDatabase(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:" + port.getText() + "/" + dbnm.getText();
            con = DriverManager.getConnection(url, new String(user.getText()), new String(pwd.getPassword()));
            JOptionPane.showMessageDialog(this, "Connected to the database.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            createTable();
            resetLoginUI();
            operations();
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to the database: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetLoginUI() {
        getContentPane().removeAll(); 
        revalidate(); 
        repaint(); 
    }
    private void createTable() {
        try {
            PreparedStatement st = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS studentlist (rollnumber INT PRIMARY KEY, name VARCHAR(255), grade VARCHAR(1))");
            st.executeUpdate();
            JOptionPane.showMessageDialog(this, "Table created successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error creating table: " + e.getMessage());
        }
    }

    private void INSERT() {
        try {
            int rollNumber = Integer.parseInt(rollnumber.getText());
            String studentName = name.getText();
            String studentGrade = Objects.requireNonNull(grade.getSelectedItem()).toString();

            PreparedStatement st = con.prepareStatement("INSERT INTO studentlist VALUES (?, ?, ?)");
            st.setInt(1, rollNumber);
            st.setString(2, studentName);
            st.setString(3, studentGrade);
            st.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data inserted successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid roll number. Please enter a valid integer value.");
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Please select a grade.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error inserting data: " + e.getMessage());
        }
    }

    private void DELETE() {
        try {
            int rollNumber = Integer.parseInt(rollnumber.getText());

            PreparedStatement st = con.prepareStatement("DELETE FROM studentlist WHERE rollnumber = ?");
            st.setInt(1, rollNumber);
            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Record deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "No record found for the given roll number.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid roll number. Please enter a valid integer value.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting data: " + e.getMessage());
        }
    }

   private void DISPLAY() {
    try {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM studentlist");

      
        DefaultTableModel tableModel = new DefaultTableModel();

 
        tableModel.addColumn("Roll Number");
        tableModel.addColumn("Name");
        tableModel.addColumn("Grade");


        while (rs.next()) {
            Object[] rowData = { rs.getInt("rollnumber"), rs.getString("name"), rs.getString("grade") };
            tableModel.addRow(rowData);
        }

   
        JTable studentTable = new JTable(tableModel);

   
        JScrollPane scrollPane = new JScrollPane(studentTable);

        JOptionPane.showMessageDialog(this, scrollPane, "Student Information", JOptionPane.PLAIN_MESSAGE);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error displaying data: " + e.getMessage());
    }
}


    private void UPDATE() {
        try {
            int rollNumber = Integer.parseInt(rollnumber.getText());
            String studentName = name.getText();
            String studentGrade = Objects.requireNonNull(grade.getSelectedItem()).toString(); 
                                                                                              

            PreparedStatement st = con
                    .prepareStatement("UPDATE studentlist SET name = ?, grade = ? WHERE rollnumber = ?");
            st.setString(1, studentName);
            st.setString(2, studentGrade);
            st.setInt(3, rollNumber);
            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data updated successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "No record found for the given roll number.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid roll number. Please enter a valid integer value.");
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Please select a grade.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating data: " + e.getMessage());
        }
    }

    private void operations() {
        setTitle("STUDENT DATABASE MANAGEMENT ");
        setLayout(new GridLayout(5, 2));
        setLocationRelativeTo(null);
        setSize(600, 300);
        add(new JLabel("Student name"));
        name = new JTextField();
        add(name);

        add(new JLabel("Student Roll number"));
        rollnumber = new JTextField();
        add(rollnumber);

        String g[] = { "A", "B", "C", "D", "F" };
        grade = new JComboBox<>(g);
        add(new JLabel("GRADE OF THE STUDENT"));
        add(grade);

        String buttton[] = { "INSERT", "UPDATE", "DELETE", "DISPLAY" };

        for (String t : buttton) {
            JButton b = new JButton(t);
            b.addActionListener(e -> {
                switch (t) {
                    case "INSERT":
                        INSERT();
                        break;
                    case "UPDATE":
                        UPDATE();
                        break;
                    case "DELETE":
                        DELETE();
                        break;
                    case "DISPLAY":
                        DISPLAY();
                        break;
                }
            });
            add(b);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

    }

    public static void main(String[] args) {
        new Student();
    }
}
