package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import main.common.AlertMessage;
import main.common.ScreenController;
import main.model.Event;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static main.common.TimelineDB.myTime;

public class NewEventFragment {

    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    @FXML private TextField eventTitle;
    @FXML private DatePicker eventStartDate;
    @FXML private DatePicker eventEndDate;
    @FXML private CheckBox durational;
    @FXML private TextArea eventDescription;
    @FXML private AnchorPane PaneMain;
    static Event myEvent=  new Event();

    public void initialize() {
        eventStartDate.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("M/d/yyyy");
            @Override
            public String toString(LocalDate localDate) {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }
            @Override
            public LocalDate fromString(String dateString) {
                if(dateString==null || dateString.trim().isEmpty())
                    return null;
                try{
                    return LocalDate.parse(dateString,dateTimeFormatter);
                }
                catch(Exception e){
                    new AlertMessage("Wrong date format", "The date was entered the wrong way. Correct way:\nM/d/yyyy", Alert.AlertType.ERROR);
                    return null;
                }
            }
        });

        eventStartDate.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                eventStartDate.setValue(eventStartDate.getConverter().fromString(eventStartDate.getEditor().getText()));
            }
        });

        eventEndDate.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("M/d/yyyy");
            @Override
            public String toString(LocalDate localDate) {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }
            @Override
            public LocalDate fromString(String dateString) {
                if(dateString==null || dateString.trim().isEmpty())
                    return null;
                try{
                    return LocalDate.parse(dateString,dateTimeFormatter);
                }
                catch(Exception e){
                    new AlertMessage("Wrong date format", "The date was entered the wrong way. Correct way:\nM/d/yyyy", Alert.AlertType.ERROR);
                    return null;
                }
            }
        });

        eventEndDate.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                eventEndDate.setValue(eventEndDate.getConverter().fromString(eventEndDate.getEditor().getText()));
            }
        });

        // Sets visibility of EndDate if Checkbox for durational event is used:
        durational.selectedProperty().addListener((observable, oldValue, newValue) -> {
            eventEndDate.setDisable(oldValue);
            eventEndDate.setVisible(newValue);
            if (newValue)
                eventStartDate.setPromptText("Start date...");
            else
                eventStartDate.setPromptText("Event date...");
        });

        PaneMain.setOnKeyPressed(e->{
            if(e.getCode() == KeyCode.ENTER){

                try {
                    saveEvent();
                } catch (NumberFormatException | IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });
    }

    public void back() throws IOException {
        ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
    }

    @FXML
    public void cancelNewEvent() throws IOException {
        ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
    }

    @FXML
    public void saveEvent() throws IOException {
        if (durational.isSelected()) {
            if (eventTitle.getText().isEmpty()) {
                new AlertMessage("Empty title", "Please enter a title for this event", Alert.AlertType.ERROR);
            } else if ( (eventEndDate.getValue().isBefore(myTime.getStartDate()) || eventEndDate.getValue().isAfter(myTime.getEndDate()))
                && (eventStartDate.getValue().isBefore(myTime.getStartDate()) || eventStartDate.getValue().isAfter(myTime.getEndDate())) ) {
                new AlertMessage("Incorrect date", "Event dates must be between " + myTime.getStartDate().toString() + " and " + myTime.getEndDate().toString(), Alert.AlertType.ERROR);
            } else if ( eventEndDate.getValue().isBefore(eventStartDate.getValue())) {
                new AlertMessage("Incorrect dates", "End date must be after start date", Alert.AlertType.ERROR);
            } else {
                myEvent = new Event(eventTitle.getText(), eventDescription.getText(), eventStartDate.getValue(), eventEndDate.getValue());
                myTime.addEvent(myEvent);
                ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
            }
        } else {
            if (eventTitle.getText().isEmpty()) {
                new AlertMessage("Empty title", "Please enter a title for this event", Alert.AlertType.ERROR);
            } else if (eventStartDate.getValue().isBefore(myTime.getStartDate()) || eventStartDate.getValue().isAfter(myTime.getEndDate())) {
                new AlertMessage("Incorrect starting date", "Event start date must be between " + myTime.getStartDate().toString() + " and " + myTime.getEndDate().toString(), Alert.AlertType.ERROR);
            } else {
                myEvent = new Event(eventTitle.getText(), eventDescription.getText(), eventStartDate.getValue());
                myTime.addEvent(myEvent);
                ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
            }
        }

//        if (eventTitle.getText().isEmpty()) {
//            new AlertMessage("Empty title", "Please enter a title for this event", Alert.AlertType.WARNING);
//        } else if (eventStartDate.getValue().isBefore(myTime.getStartDate()) || eventStartDate.getValue().isAfter(myTime.getEndDate())) {
//            new AlertMessage("Incorrect starting date", "Event start date must be between " + myTime.getStartDate().toString() + " and " + myTime.getEndDate().toString(), Alert.AlertType.WARNING);
//        } else if (durational.isSelected()) {
//            if (eventEndDate.getValue().isBefore(myTime.getStartDate()) || eventEndDate.getValue().isAfter(myTime.getEndDate())) {
//                new AlertMessage("Incorrect ending date", "Event end date must be between " + eventStartDate.getValue().toString() + " and " + myTime.getEndDate().toString(), Alert.AlertType.WARNING);
//            }
//        } //else {
//        if (durational.isSelected())
//            myEvent = new Event(eventTitle.getText(), eventDescription.getText(), eventStartDate.getValue(), eventEndDate.getValue());
//        else
//            myEvent = new Event(eventTitle.getText(), eventDescription.getText(), eventStartDate.getValue());
//        myTime.addEvent(myEvent);
//        ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
    }
//        if (durational.isSelected()) {
//            if (eventTitle.getText().isEmpty()) {
//                new AlertMessage("Empty title", "Please enter a title for this event", Alert.AlertType.WARNING);
//            } else if (eventStartDate.getValue().isBefore(myTime.getStartDate()) || eventStartDate.getValue().isAfter(myTime.getEndDate())) {
//                new AlertMessage("Incorrect starting date", "Event start date must be between\n " + myTime.getStartDate().toString() + " and " + myTime.getEndDate().toString(), Alert.AlertType.WARNING);
//            } else if (eventEndDate.getValue().isBefore(myTime.getStartDate()) || eventEndDate.getValue().isAfter(myTime.getEndDate())) {
//                new AlertMessage("Incorrect ending date", "Event end date must be between\n " + eventStartDate.getValue().toString() + " and " + myTime.getEndDate().toString(), Alert.AlertType.WARNING);
//            } else {
//                myEvent = new Event(eventTitle.getText(), eventDescription.getText(), eventStartDate.getValue(), eventEndDate.getValue());
//                myTime.addEvent(myEvent);
//
//                ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
//            }
//        } else {
//            if ((eventStartDate.getValue().isBefore(myTime.getEndDate()) && ) eventStartDate.getValue().isAfter(myTime.getStartDate()) && !eventTitle.getText().isEmpty()) {
//                myEvent = new Event(eventTitle.getText(), eventDescription.getText(), eventStartDate.getValue());
//                myTime.addEvent(myEvent);
//                ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
//            } else {
//                new AlertMessage("Missing details", "Please enter title and date(s)", Alert.AlertType.WARNING);
//            }
//        }
//    }
}