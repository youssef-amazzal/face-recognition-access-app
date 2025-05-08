package org.glsid.facerecognitionaccessapp.application.usecases;

import org.glsid.facerecognitionaccessapp.core.entities.Picture;
import org.glsid.facerecognitionaccessapp.core.entities.User;
import org.glsid.facerecognitionaccessapp.infrastructure.db.sqlite.PictureDAO;
import org.glsid.facerecognitionaccessapp.infrastructure.db.sqlite.SQLiteConnection;
import org.glsid.facerecognitionaccessapp.infrastructure.db.sqlite.UserDAO;

import java.util.ArrayList;

public class WhoIsIt {
    private final UserDAO userDAO = new UserDAO(SQLiteConnection.getInstance().getConnection());
    private final PictureDAO pictureDAO = new PictureDAO(SQLiteConnection.getInstance().getConnection());

    public User find(float[] targetEmbedding) {
        User closestUser = null;
        double highestSimilarity = 0.0;

        var users = userDAO.findAll()
                .stream()
                .map(userDTO -> {
                    var pictures = pictureDAO.findByUserId(userDTO.id()).stream().map(pictureDTO -> new Picture(
                            pictureDTO.id(),
                            pictureDTO.name(),
                            pictureDTO.picturePath(),
                            Picture.Types.PROFILE,
                            pictureDTO.embeddings(),
                            pictureDTO.createdAt(),
                            pictureDTO.updatedAt()
                    )).toList();
                    User user = new User();
                    user.setPictures(new ArrayList<>());
                    user.setFirstName(userDTO.firstName());
                    user.setLastName(userDTO.lastName());
                    user.pictures().addAll(pictures);

                    return user;
                }).toList();

        for (User user : users) {
            for (Picture picture : user.pictures()) {
                double similarity = calculateSimilarity(targetEmbedding, picture.getEmbeddings());
                if (similarity > highestSimilarity) {
                    highestSimilarity = similarity;
                    closestUser = user;
                }
            }
        }

        double threshold = 0.55;
        return highestSimilarity >= threshold ? closestUser : null;
    }

    private double calculateSimilarity(float[] embedding1, float[] embedding2) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (int i = 0; i < embedding1.length; i++) {
            dotProduct += embedding1[i] * embedding2[i];
            norm1 += embedding1[i] * embedding1[i];
            norm2 += embedding2[i] * embedding2[i];
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

}
