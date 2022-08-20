package client.Application;

import client.Application.UI.Controller.Chat;
import client.UI.ChatWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@SpringBootApplication
public class Main extends Application {

    private ConfigurableApplicationContext context;
    private Parent root;

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(Main.class);

        URL location = getClass().getResource("/fxml/Chat.fxml");
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(location);
        loader.setControllerFactory(context::getBean);
        root = loader.load();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        context.stop();
    }
}
