package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import main.common.ScreenController;
import main.model.Event;

import java.io.IOException;

import static main.controller.NewTimelineFragment.myTime;

public class NewEventFragment {

    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    @FXML private TextField eventTitle;
    @FXML private DatePicker eventDate;
    static Event myEvent;

    public void initialize() {
        if (myEvent != null) {
            eventTitle.setText(myEvent.getEvent_title());
            eventDate.setValue(myEvent.getEvent_startDate());
        }
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
        // Modify the event when event exists already:
        if (myEvent != null) {
            myTime.deleteEvent(myEvent);
            myEvent.setEvent_startDate(eventDate.getValue());
            myEvent.setEvent_title(eventTitle.getText());
        } else {
            myEvent = new Event(eventTitle.getText(),"TEST DESCRIPTION",eventDate.getValue());
        }
        myTime.addEvent(myEvent);
        ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
    }
}
