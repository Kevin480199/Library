package org.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {

    @FXML
    private TextArea display;

    @FXML
    private TextField searchBar;

    @FXML
    void search(ActionEvent event) throws SQLException {
        String input = searchBar.getText();
        String sql = "SELECT id, title, author FROM Books WHERE title LIKE ? OR author LIKE ?";
        PreparedStatement ps = Database.getConnection().prepareStatement(sql);
        ps.setString(1, "%" + input + "%");
        ps.setString(2, "%" + input + "%");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {  // Move the cursor to the first row
            // If a row is found, retrieve the values
            String title = rs.getString("title");
            String author = rs.getString("author");

            // Update the display with the results
            display.setText("Title: " + title + "\nAuthor: " + author);
        } else {
            // If no result was found, show an appropriate message
            display.setText("No book found with that title.");
        }
    }
    @FXML
    void borrow(ActionEvent event) throws SQLException {
        String input = searchBar.getText();
        String sql = "INSERT INTO Borrowed(userId, booksId,startTime, endTime) VALUES(1,(SELECT id FROM Books WHERE title=? OR author=?),CURDATE(),DATE_ADD(CURDATE(), INTERVAL 30 DAY))";
        PreparedStatement ps = Database.getConnection().prepareStatement(sql);
        ps.setString(1, input);
        ps.setString(2, input);
        int rows = ps.executeUpdate();
        display.setText("Borrowed: " + input + " rows");
    }

    @FXML
    void returnBook(ActionEvent event) {

    }

}