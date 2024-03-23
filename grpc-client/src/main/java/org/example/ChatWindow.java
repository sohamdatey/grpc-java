package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Text area to display messages
        TextArea messageArea = new TextArea();
        messageArea.setEditable(false);

        // Text field for user input
        TextField inputField = new TextField();
        inputField.setPromptText("Type your message here");

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            String message = inputField.getText();
            if (!message.isEmpty()) {
                // Process user input (e.g., send message to server)
                // For demonstration, we just append the message to the text area
                messageArea.appendText("You: " + message + "\n");
                // Clear input field
                inputField.clear();
            }
        });

        // Layout setup
        VBox inputContainer = new VBox();
        inputContainer.getChildren().addAll(inputField, submitButton);
        root.setCenter(messageArea);
        root.setBottom(inputContainer);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chat Window");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
