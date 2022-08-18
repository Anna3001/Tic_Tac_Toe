package com.fx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.paint.Color.*;

public class App extends Application {

    private boolean turnX = true;

    private Parent createMainView() {
        Pane pane = new Pane();
        pane.setPrefSize(600,700);
        pane.setStyle("-fx-background-color: lightgrey;");

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                Square square = new Square();
                square.setTranslateX(i * 200);
                square.setTranslateY(j * 200);
                pane.getChildren().add(square);
            }
        }
        return  pane;
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(createMainView()));
        stage.setResizable(false);
        stage.setTitle("Tic Tac Toe");
        stage.show();
    }

    private class Square extends StackPane {
        private Text mark= new Text();

        public Square() {
            Rectangle rectangle = new Rectangle(200,200);
            rectangle.setFill(WHITE);
            rectangle.setStroke(GRAY);
            rectangle.setStrokeWidth(3.0);

            mark.setFont(Font.font(86));
            setAlignment(Pos.CENTER);
            getChildren().addAll(rectangle, mark);

            setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if(turnX) {
                        printX();
                        turnX = false;
                    } else {
                        printO();
                        turnX = true;
                    }
                } else {
                    return;
                }
            });
        }

        private void printO() {
            mark.setText("O");
        }

        private void printX() {
            mark.setText("X");
        }
    }
    public static void main(String[] args) {
        launch();
    }
}
