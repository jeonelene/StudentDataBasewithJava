/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
     * to change this license
 */
package com.mycompany.gewrgoulas1;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author eleno
 */

public class Gewrgoulas1 {

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection
            ("jdbc:mariadb://localhost:3306/askisi1_db", "christofidou", "password")) {

            System.out.println("ΕΠιτυχής σύνδεση!");

            Scanner scanner = new Scanner(System.in);
            int choice;

            do {
                printMenu();
                System.out.print("Εισαγωγή επιλογής: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        printAllItems(connection);
                        break;
                    case 2:
                        printItemsBySurname(connection);
                        break;
                    case 3:
                        printItemsBySemester(connection);
                        break;
                    case 4:
                        updateItemBySurname(connection);
                        break;
                    case 5:
                        deleteItemBySurname(connection);
                        break;
                    case 6:
                        System.out.println("Έξοδος από το πρόγραμα. Αντίο!");
                        break;
                    default:
                        System.out.println("Άκυρη επιλογή. Παρακαλώ εισάγετε έγκυρη επιλογή.");
                }
                
            } while (choice != 6);
            scanner.close();
        } catch (SQLException e) {
            System.err.println("ΑΠοτυχία σύνδεσης. Σφάλμα: " + e.getMessage());
        } finally {
            
        }

    }

    private static void printMenu() {
        System.out.println("===== Μενού =====");
        System.out.println("1. Εκτύπωση Όλων των Στοιχείων");
        System.out.println("2. Εκτύπωση Στοιχείων ανά Επώνυμο");
        System.out.println("3. Εκτύπωση Στοιχείων ανά Εξάμηνο");
        System.out.println("4. Ενημέρωση Στοιχείου ανά Επώνυμο");
        System.out.println("5. Διαγραφή Στοιχείου ανά Επώνυμο");
        System.out.println("6. Έξοδος");
    }

    private static void printAllItems(Connection connection) {
        String query = "select * from students;";
        try (Statement print = connection.createStatement()) {
            ResultSet rs = print.executeQuery(query);
            while (rs.next()) {
                System.out.printf("|%3s|%-10s||%-10s||%-5s||%-5s|\n"
                        , rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
                        rs.getString(5));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private static void printItemsBySurname(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Εισάγετε έγκυρο επώνυμο για πληροφορίες: ");
        String surname = scanner.nextLine();
        String query = "select * from students where surname = ?;";
        try (PreparedStatement search = connection.prepareStatement(query)) {
            search.setString(1, surname);
            ResultSet rs = search.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("Δεν βρέθηκαν στοιχεία για το επώνυμο: " + surname);
            }

            while (rs.next()) {
                System.out.printf("|%3s|%-10s||%-10s||%-5s||%-5s|\n"
                        , rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
                        rs.getString(5));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private static void printItemsBySemester(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        int semester = -1;

        while (semester < 0) {
            try {
                System.out.print("Εισάγετε έγκυρο αριθμό εξαμήνου για πληροφορίες: ");
                semester = scanner.nextInt();

                if (semester < 1 || semester > 10) {
                    System.out.println("Άκυρη επιλογή. Ο αριθμός εξαμήνου πρέπει να βρίσκεται "
                            + "μεταξύ του 1 και του 10.");
                    semester = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Άκυρη εισαγωγή. Παρακαλώ εισάγετε έναν έγκυρο ακέραιο "
                        + "για τον αριθμό εξαμήνου.");
                scanner.next();
            }
        }

        String query = "select * from students where semester = ?;";
        try (PreparedStatement search = connection.prepareStatement(query)) {
            search.setInt(1, semester);
            ResultSet rs = search.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("Δε βρέθηκαν στοιχεία για το εξάμηνο: " + semester);
            }

            while (rs.next()) {
                System.out.printf("|%3s|%-10s||%-10s||%-5s||%-5s|\n"
                        , rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private static void updateItemBySurname(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Εισάγετε επώνυμο μαθητ@ τ@ οποί@ θέλετε να αλλάξετε τον αριθμό "
                + "περασμένων μαθημάτων: ");
        String surname = scanner.nextLine();

        System.out.print("Εισάγετε νέο αριθμό περασμένων μαθημάτων για τ@ " + surname + ": ");
        String newPassedSum = scanner.nextLine();

        String query = "update students set passed_sum = ? where surname = ?;";
        try (PreparedStatement update = connection.prepareStatement(query)) {
            update.setString(1, newPassedSum);
            update.setString(2, surname);

            int rowsAffected = update.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Δε βρέθηκαν στοιχεία για το επώνυμο: " + surname);
            } else {
                System.out.println("Ενημέρωση επιτυχής. Rows affected: " + rowsAffected);

                // Retrieve and print the updated data
                String selectQuery = "select * from students where surname = ?;";
                try (PreparedStatement selectUpdatedData = connection.prepareStatement(selectQuery)) {
                    selectUpdatedData.setString(1, surname);
                    ResultSet rs = selectUpdatedData.executeQuery();

                    if (rs.next()) {
                        System.out.printf("|%3s|%-10s||%-10s||%-5s||%-5s|\n"
                                , rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
                                rs.getString(5));

                    } else {
                        System.out.println("Αποτυχία. Σφάλμα κατά την ανάκτηση ενημερωμένων δεδομένων.");
                    }
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private static void deleteItemBySurname(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Εισάγετε ένα επώνυμο για να διαγράψετε την αντίστοιχη εγγραφή του πίνακα: ");
        String surname = scanner.nextLine();

        String query = "delete from students where surname = ?;";
        try (PreparedStatement delete = connection.prepareStatement(query)) {
            delete.setString(1, surname);

            int rowsAffected = delete.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Δεν βρέθηκαν δεδομένα για το επώνυμο: " + surname);
            } else {
                System.out.println("Διαγραφή επιτυχής. Rows affected: " + rowsAffected);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private static void deleteAllItems(Connection connection){
        String query = "delete from students;";
        try (PreparedStatement deleteAll = connection.prepareStatement(query)){
            
            int rowsAffected = deleteAll.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Ενημέρωση ανεπιτυχής.");
            } else {
                System.out.println("Ενημέρωση επιτυχής. Rows affected: " + rowsAffected);
               }
        } catch (SQLException e) {
           handleSQLException(e);
        }
    }

    private static void handleSQLException(SQLException e) {
        for (Throwable ex : e) {
            System.err.println("Προέκυψε σφάλμα " + ex);
        }
        System.out.println("Σφάλμα κατά την ανάκτηση δεδομένων.");
    }
}
