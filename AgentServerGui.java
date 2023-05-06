package org.example;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AgentServerGui extends Application {
    private AgentServer agentServer;
    ObservableList<String> data= FXCollections.observableArrayList();
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        startContainer();
        BorderPane root = new BorderPane();
        ListView<String> listView = new ListView<>(data);
        root.setCenter(listView);
        Scene scene = new Scene(root,400,250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void startContainer() throws StaleProxyException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl(false);
        profile.setParameter(ProfileImpl.MAIN_HOST,"localhost");
        AgentContainer container = runtime.createAgentContainer(profile);
        AgentController server = container.createNewAgent("server",
                "org.example.AgentServer",new Object[]{this});
        server.start();
    }
    public void showMessage(String message){
            data.add(message);

    }
}
