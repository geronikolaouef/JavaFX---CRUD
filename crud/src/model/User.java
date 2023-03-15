package model;

public class User 
{
    private String ID;
    private String firstName;
    private String lastName;
    private String email;

    public User(String ID, String firstName, String lastName, String email) 
    {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getID() 
    {
        return ID;
    }

    public void setID(String ID) 
    {
        this.ID = ID;
    }

    public String getFirstName() 
    {
        return firstName;
    }

    public void setFirstName(String firstName) 
    {
        this.firstName = firstName;
    }

    public String getLastName() 
    {
        return lastName;
    }

    public void setLastName(String lastName) 
    {
        this.lastName = lastName;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    @Override
    public String toString() 
    {
        return "User [ID=" + ID + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
    }
}
