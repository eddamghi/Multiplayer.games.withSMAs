package org.example;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
public class FirstGamerGui extends Application {
    private FirstGamer firstGamer;
    ObservableList<String> data = FXCollections.observableArrayList();
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        startContainer();
        BorderPane root = new BorderPane();
        ListView<String> listView = new ListView<>(data);
        Button buttonSend = new Button("Send");
        TextField textFieldMsg = new TextField();
        Label labelMsg = new Label("Answer :");
        HBox hBox = new HBox(labelMsg, textFieldMsg, buttonSend);
        root.setBottom(hBox);
        root.setCenter(listView);
        Scene scene = new Scene(root, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
        buttonSend.setOnAction(event -> {
            String message = textFieldMsg.getText();
            data.add(message);
            GuiEvent guiEvent = new GuiEvent(this, 1);
            guiEvent.addParameter(message);
            firstGamer.onGuiEvent(guiEvent);
            textFieldMsg.setText("");
        });
    }
    public void showMessage(String message) {
        data.add(message);
    }
    private void startContainer() throws StaleProxyException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl(false);
        profile.setParameter(ProfileImpl.MAIN_HOST, "localhost");
        AgentContainer container = runtime.createAgentContainer(profile);
        AgentController client = container.createNewAgent("Zineb",
                "org.example.FirstGamer", new Object[]{this});
        client.start();
    }
    public void setAgentClient(FirstGamer firstGamer) {
        this.firstGamer = firstGamer;
    }
    }

