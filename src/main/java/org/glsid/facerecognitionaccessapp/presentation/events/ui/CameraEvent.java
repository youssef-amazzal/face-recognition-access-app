package org.glsid.facerecognitionaccessapp.presentation.events.ui;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.image.Image;

import java.util.List;

public class CameraEvent extends Event {

    public static final EventType<CameraEvent> IMAGE_CAPTURED_EVENT = new EventType<>(Event.ANY, "IMAGE_CAPTURED_EVENT");

    private final List<Image> capturedImages;

    public CameraEvent(List<Image> capturedImages) {
        super(IMAGE_CAPTURED_EVENT);
        System.out.println("firing camera event");
        this.capturedImages = capturedImages;
    }

    public List<Image> getCapturedImages() {
        return capturedImages;
    }
}
