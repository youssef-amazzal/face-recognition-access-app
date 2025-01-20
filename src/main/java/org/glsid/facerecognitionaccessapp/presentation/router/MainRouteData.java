package org.glsid.facerecognitionaccessapp.presentation.router;

import org.glsid.facerecognitionaccessapp.presentation.components.TitleBar;

public class MainRouteData extends RouteData {
    private final TitleBar titleBar;
    public MainRouteData(TitleBar titleBar) {
        super();
        this.titleBar = titleBar;
    }

    public TitleBar getTitleBar() {
        return titleBar;
    }
}
