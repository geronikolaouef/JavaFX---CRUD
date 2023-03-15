package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.Database;
import model.User;

public class MainController 
{
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField tfEMailLeft;
    @FXML
    private TextField tfEMailRight;
    @FXML
    private TextField tfFirstNameLeft;
    @FXML
    private TextField tfFirstNameRight;
    @FXML
    private TextField tfIDRight;
    @FXML
    private TextField tfLastNameLeft;
    @FXML
    private TextField tfLastNameRight;
    @FXML
    private Label lbEmailRight;
    @FXML
    private Label lbFirstNameRight;
    @FXML
    private Label lbLastNameRight;
    @FXML
    private Label lbConsoleMessage;
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> tcEmail;
    @FXML
    private TableColumn<User, String> tcFName;
    @FXML
    private TableColumn<User, String> tcID;
    @FXML
    private TableColumn<User, String> tcLName;

    private Database db;
    private ResultSet rs;

    private ArrayList<User> users;
    private ObservableList<User> list;

    private boolean userFound;

    @FXML
    public void initialize() throws SQLException 
    {
        setConsoleMessage("succes", "Database loaded.");   
        
        tcID.setCellValueFactory(new PropertyValueFactory<User, String>("ID"));
        tcFName.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        tcLName.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<User, String>("email"));

        db = new Database();
        users = new ArrayList<>();
        refreshUserList();
        table.setItems(list);
    }

    @FXML
    void btnAddClicked(ActionEvent event) throws SQLException 
    {
        if(validateInputs("left"))
        {
            db.executeQuery("INSERT INTO users(firstName, lastName, email) VALUES ('" + tfFirstNameLeft.getText() + "', ' " + tfLastNameLeft.getText() + " ', ' " + tfEMailLeft.getText() + " ')");
            setConsoleMessage("succes", "New user with the credentials: " + tfFirstNameLeft.getText().trim() + " " + tfLastNameLeft.getText().trim() + " " + tfEMailLeft.getText().trim() + " has been added to the  database!");
            tfEMailLeft.clear();
            tfFirstNameLeft.clear();
            tfLastNameLeft.clear();
            refreshUserList();
            table.setItems(list);
        }
        else
        {
            setConsoleMessage("alert", "Fields must not be empty!");
        }
    }

    @FXML
    void btnSearchClicked(ActionEvent event) 
    { 
        userFound = false;
        if(tfIDRight.getText().trim().isEmpty())
        {
            setConsoleMessage("alert", "An ID has not been inserted!");
        }
        else
        {
            for(User user : users)
            {
                if(tfIDRight.getText().equals(user.getID()))
                {
                    tfEMailRight.setText(user.getEmail());
                    tfFirstNameRight.setText(user.getFirstName());
                    tfLastNameRight.setText(user.getLastName());
                    userFound = true;
                    setRightTextFieldsVisible(true);
                    setRightLabelsVisible(true);
                    setRightButtonsVisible(true);
                    tfIDRight.setDisable(true);
                }
            }
            if(!userFound)
            {
                setConsoleMessage("alert", "The ID you inserted does not corepsond to a user!");
            }
        }
    }

    @FXML
    void btnDeleteClicked(ActionEvent event) throws SQLException 
    {
        if(userFound)
        {
            db.executeQuery("DELETE FROM users WHERE id = '" + tfIDRight.getText() + "'");
            setConsoleMessage("succes", "Deleted the user with the id: " + tfIDRight.getText());
            setRightTextFieldsVisible(false);
            setRightLabelsVisible(false);
            setRightButtonsVisible(false);
            clearRightTextFields();
            tfIDRight.setDisable(false);
            refreshUserList();
            table.setItems(list);
        }
    }

    @FXML
    void btnUpdateClicked(ActionEvent event) throws SQLException 
    {
        if(userFound)
        {
            if(validateInputs("right"))
            {
                db.executeQuery("UPDATE users SET firstName = '" + tfFirstNameRight.getText().trim() + "', lastName = '" + tfLastNameRight.getText().trim() + "', email = '" + tfEMailRight.getText().trim() + "' WHERE id = '" + tfIDRight.getText() + "' ");
                setConsoleMessage("succes", "Updated the user with the id: " + tfIDRight.getText());
                setRightTextFieldsVisible(false);
                setRightLabelsVisible(false);
                setRightButtonsVisible(false);
                tfIDRight.setDisable(false);
                clearRightTextFields();
                refreshUserList();
                table.setItems(list);
            }
            else
            {
                setConsoleMessage("alert", "Fields must not be empty!");
            }
        }
    }

    @FXML
    void brnCancelClicked(ActionEvent event) 
    {
        setRightTextFieldsVisible(false);
        setRightLabelsVisible(false);
        setRightButtonsVisible(false);
        clearRightTextFields();
        tfIDRight.setDisable(false);
    }

    @FXML
    void tvClicked(MouseEvent event) 
    {
        User user = table.getSelectionModel().getSelectedItem();
        tfIDRight.setText(user.getID());
        tfEMailRight.setText(user.getEmail());
        tfFirstNameRight.setText(user.getFirstName());
        tfLastNameRight.setText(user.getLastName());
        userFound = true;
        setRightTextFieldsVisible(true);
        setRightLabelsVisible(true);
        setRightButtonsVisible(true);
        tfIDRight.setDisable(true);
    }

    private void clearRightTextFields()
    {
        tfIDRight.clear();
        tfEMailRight.clear();
        tfFirstNameRight.clear();
        tfLastNameRight.clear();
    }

    private void setRightTextFieldsVisible(boolean arg)
    {
        tfEMailRight.setVisible(arg);
        tfFirstNameRight.setVisible(arg);
        tfLastNameRight.setVisible(arg);
    }

    private void setRightLabelsVisible(boolean arg)
    {
        lbEmailRight.setVisible(arg);
        lbFirstNameRight.setVisible(arg);
        lbLastNameRight.setVisible(arg);
    }

    private void setRightButtonsVisible(boolean arg)
    {
        btnDelete.setVisible(arg);
        btnUpdate.setVisible(arg);
        btnCancel.setVisible(arg);
    }

    private void refreshUserList() throws SQLException
    {
        users.clear();
        rs = db.getResultSet("SELECT * FROM users");
        while(rs.next())
        {
            users.add(new User(rs.getString("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("email")));
        }
        db.disconnect();

        list = FXCollections.observableArrayList(users);
    }

    private void setConsoleMessage(String type, String message)
    {
        if(type.equalsIgnoreCase("alert"))
        {
            lbConsoleMessage.setTextFill(Color.RED);
        }
        else if(type.equalsIgnoreCase("inform"))
        {
            lbConsoleMessage.setTextFill(Color.BLUE);
        }
        else if(type.equalsIgnoreCase("succes"))
        {
            lbConsoleMessage.setTextFill(Color.GREEN);
        }

        lbConsoleMessage.setText(message);
    }

    private boolean validateInputs(String field)
    {
        if(field.equalsIgnoreCase("right"))
        {
            if(tfEMailRight.getText().trim().isEmpty() || tfFirstNameRight.getText().trim().isEmpty() || tfLastNameRight.getText().trim().isEmpty())
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else if(field.equalsIgnoreCase("left"))
        {
            if(tfEMailLeft.getText().trim().isEmpty() || tfFirstNameLeft.getText().trim().isEmpty() || tfLastNameLeft.getText().trim().isEmpty())
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }
}
