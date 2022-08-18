package com.fx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.*;

public class App extends Application {

    private boolean turnX = true;
    private boolean canPlay = true;
    private List<Position> positionList = new ArrayList<>();
    private Square[][] squaresXY = new Square[3][3];
    private Pane pane = new Pane();

    private Parent createMainView() {
        pane.setPrefSize(600,700);
        pane.setStyle("-fx-background-color: lightgrey;");

        for(int i = 0; i < 3; i++) {                // i is for Y
            for(int j = 0; j < 3; j++) {            // j is for X
                Square square = new Square();
                square.setTranslateX(j * 200);
                square.setTranslateY(i * 200);

                pane.getChildren().add(square);

                squaresXY[j][i] = square;
            }
        }

        for (int x = 0; x < 3; x++) {
            positionList.add(new Position(squaresXY[x][0], squaresXY[x][1], squaresXY[x][2]));
        }

        for (int y = 0; y < 3; y++) {
            positionList.add(new Position(squaresXY[0][y], squaresXY[1][y], squaresXY[2][y]));
        }

        positionList.add(new Position(squaresXY[0][0], squaresXY[1][1], squaresXY[2][2]));
        positionList.add(new Position(squaresXY[0][2], squaresXY[1][1], squaresXY[2][0]));

        return pane;
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(createMainView()));
        stage.setResizable(false);
        stage.setTitle("Tic Tac Toe");
        stage.show();
    }

    private void checkState() {
        for (Position position : positionList) {
            if(position.isFull()) {
                canPlay = false;
                showWinnerAnimation(position);
                break;
            }
        }
    }

    private void showWinnerAnimation(Position position) {
        Line line = new Line();
        line.setStartX(position.squares[0].getCenterX());
        line.setStartY(position.squares[0].getCenterY());
        line.setEndX(position.squares[0].getCenterX());
        line.setEndY(position.squares[0].getCenterY());
        line.setStyle(("-fx-stroke: red; -fx-stroke-width: 5"));

        pane.getChildren().add(line);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
                new KeyValue(line.endXProperty(), position.squares[2].getCenterX()),
                new KeyValue(line.endYProperty(), position.squares[2].getCenterY())));
        timeline.play();
    }

    private class Position {
        private Square[] squares;

        public Position(Square... squares) {
            this.squares = squares;
        }

        public boolean isFull() {
            if (squares[0].getMark().isEmpty() || squares[1].getMark().isEmpty() || squares[2].getMark().isEmpty()) {
                return false;
            } else {
                return squares[0].getMark().equals(squares[1].getMark())      // true if marks are equal, false if not
                        && squares[0].getMark().equals(squares[2].getMark());
            }
        }
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
                if (!canPlay) {
                    return;
                }

                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if(turnX) {
                        printX();
                        turnX = false;
                        checkState();
                    } else {
                        printO();
                        turnX = true;
                        checkState();
                    }
                } else {
                    return;
                }
            });
        }

        public double getCenterX() {
            return getTranslateX() + 100;
        }

        public double getCenterY() {
            return getTranslateY() + 100;
        }

        public String getMark() {
            return mark.getText();
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
