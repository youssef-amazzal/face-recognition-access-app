package org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl;

import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFrameCapture;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class FrameCapture implements IFrameCapture {
    private VideoCapture camera;
    private Mat frame;
    private Thread captureThread;
    private volatile boolean running;

    @Override
    public void startCamera() {
        camera = new VideoCapture(0);
        if (!camera.isOpened()) {
            throw new RuntimeException("Failed to open camera.");
        }
        frame = new Mat();
        running = true;
        captureThread = new Thread(() -> {
            while (running) {
                synchronized (this) {
                    camera.read(frame);
                }
            }
        });
        captureThread.setDaemon(true);
        captureThread.start();
    }

    @Override
    public void stopCamera() {
        running = false;
        try {
            if (captureThread != null) {
                captureThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (camera != null) {
            camera.release();
        }
    }

    @Override
    public synchronized Mat captureFrame() {
        return frame.clone();
    }
}