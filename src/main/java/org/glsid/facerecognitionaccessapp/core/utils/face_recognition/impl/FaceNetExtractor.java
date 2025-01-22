package org.glsid.facerecognitionaccessapp.core.utils.face_recognition.impl;

import org.glsid.facerecognitionaccessapp.core.utils.face_recognition.IFaceFeatureExtractor;
import org.opencv.core.Mat;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Path;

import static java.nio.file.Files.readAllBytes;

public class FaceNetExtractor implements IFaceFeatureExtractor {
    String modelPath = "src/main/resources/org/glsid/facerecognitionaccessapp/models/Facenet.pb";
    private Graph graph;
    private Session session;
    private static final int FACENET_INPUT_SIZE = 160;

    public FaceNetExtractor() {
        loadModel();
    }

    @Override
    public float[] extractFeatures(Mat face) {
        float[] array = flattenMat(face);

        try (Tensor<Float> inputTensor = Tensor.create(new long[]{1, FACENET_INPUT_SIZE, FACENET_INPUT_SIZE, 3}, FloatBuffer.wrap(array));
             Tensor<Float> phaseTrainTensor = (Tensor<Float>) Tensor.create(false)) {

            Tensor<Float> outputTensor = session.runner()
                    .feed("input", inputTensor)
                    .feed("phase_train", phaseTrainTensor)
                    .fetch("embeddings")
                    .run()
                    .getFirst()
                    .expect(Float.class);

            float[][] embeddings = new float[1][(int) outputTensor.shape()[1]];
            outputTensor.copyTo(embeddings);
            return embeddings[0];
        }
    }

    private float[] flattenMat(Mat face) {
        int rows = face.rows();
        int cols = face.cols();
        int channels = face.channels();
        float[] floatArray = new float[rows * cols * channels];
        int index = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double[] pixel = face.get(row, col);
                for (int channel = 0; channel < channels; channel++) {
                    floatArray[index++] = (float) pixel[channel];
                }
            }
        }
        return floatArray;
    }

    private void loadModel() {
        try {
            graph = new Graph();
            byte[] graphDef = readAllBytes(Path.of(modelPath));
            graph.importGraphDef(graphDef);
            session = new Session(graph);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (session != null) session.close();
        if (graph != null) graph.close();
    }
}