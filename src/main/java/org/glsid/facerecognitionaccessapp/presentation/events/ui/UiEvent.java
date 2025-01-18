package org.glsid.facerecognitionaccessapp.presentation.events.ui;

import javafx.event.Event;
import javafx.event.EventType;

public class UiEvent extends Event {

        public static final EventType<UiEvent> TITLE_BAR_VISIBILITY_EVENT = new EventType<>(Event.ANY, "TITLE_BAR_VISIBILITY_EVENT");

        private final boolean visible;

        public UiEvent(boolean visible) {
            super(TITLE_BAR_VISIBILITY_EVENT);
            this.visible = visible;
        }

        public boolean isVisible() {
            return visible;
        }
}
