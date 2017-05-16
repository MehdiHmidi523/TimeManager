package main.controller;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.common.ScreenController;
import main.model.Timeline;

import java.io.IOException;
import java.util.ArrayList;

import static main.common.StageManager.getStage;
import static main.controller.NewTimelineFragment.numberOfTimelines;

public class ProjectsFragment {
    @FXML private AnchorPane projectPane;
    @FXML private Button ButtonBack;
    @FXML private Label icon,inProgress,all,completed;
    @FXML private ScrollPane scrollProjects;
    @FXML private ImageView timelineIcon;
    @FXML private Pane projectDisplay;
    ArrayList<Timeline> createdTimelines = new ArrayList<>();
    private double panePosition=73.0;

    public void initialize(){
        try {
            ButtonBack.setOnMouseEntered(e -> getStage().getScene().setCursor(Cursor.HAND));
            ButtonBack.setOnMouseExited(e -> getStage().getScene().setCursor(Cursor.DEFAULT));
            all.setText("All("+numberOfTimelines+")");
            projectDisplay.setStyle(
                    "-fx-background-color: lightgrey; " +
                            "-fx-background-insets: 10; " +
                            "-fx-background-radius: 10; " +
                            "-fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);"
            );
            for(int i=0;i<createdTimelines.size();i++)
                newTimeline(createdTimelines.get(i));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // we need to figure out how can the system remember which screen i was in!!

    @FXML
    public void back() throws IOException{ScreenController.setScreen(ScreenController.Screen.HOME);}

    @FXML
    public void eventAdd() {

    }

    @FXML
    public void newTimeline(Timeline repoTime) throws IOException {
        Pane nw = new Pane();
        nw.setId("Project"+numberOfTimelines++);
        panePosition+=280;
        nw.setLayoutX(panePosition);
        nw.setLayoutY(120.0);
        nw.setPrefHeight(377.0);
        nw.setPrefWidth(265.0);

        nw.setStyle(
                "-fx-background-color: lightgrey; " +
                        "-fx-background-insets: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);"
        );
        Label title = new Label("Title Missing");
        Label num = new Label(repoTime.getNumberOfEvents()+"");
        title.setLayoutX(87.0);
        title.setLayoutY(271.0);
        title.setTextFill(Color.BLACK);
        title.setFont(Font.font("Segoe UI", 16));

        num.setFont(Font.font("Segoe UI", 16));
        num.setTextFill(Color.BLACK);
        num.setLayoutY(220.0);
        num.setLayoutX(87.0);
        nw.getChildren().addAll(title,num);
        projectPane.getChildren().add(nw);
        nw.setOnMouseClicked(e -> {
            try {
                ScreenController.setScreen(ScreenController.Screen.NEW_TIMELINE);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });// still on going.
    }
    @FXML
    public void newTimeline(MouseEvent mouseEvent) throws IOException {
        Timeline a = new Timeline();
        createdTimelines.add(a);
        newTimeline(a);
    }
}
