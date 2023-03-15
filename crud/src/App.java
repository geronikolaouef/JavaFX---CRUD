import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class App extends Application 
{
    @Override
    public void start(Stage primaryStage) 
    {
        Parent root;
        try 
        {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Main.fxml"));
            Scene scene = new Scene(root);

            primaryStage.setTitle("JavaFX - CRUD");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void stop()
    {
        System.out.println("Stage is closing...");
    }
    public static void main(String[] args) 
    {
        launch(args);
    }
}