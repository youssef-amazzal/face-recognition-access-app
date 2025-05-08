package org.glsid.facerecognitionaccessapp.application.usecases;

import org.glsid.facerecognitionaccessapp.core.dto.db.PictureDTO;
import org.glsid.facerecognitionaccessapp.core.dto.db.UserDTO;
import org.glsid.facerecognitionaccessapp.core.dto.ui.EmbeddedImage;
import org.glsid.facerecognitionaccessapp.core.dto.ui.UserImageFxDTO;
import org.glsid.facerecognitionaccessapp.core.entities.Picture;
import org.glsid.facerecognitionaccessapp.infrastructure.db.sqlite.PictureDAO;
import org.glsid.facerecognitionaccessapp.infrastructure.db.sqlite.SQLiteConnection;
import org.glsid.facerecognitionaccessapp.infrastructure.db.sqlite.UserDAO;
import org.glsid.facerecognitionaccessapp.presentation.utils.JavaFxImageConverter;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateUser {
    private final UserDAO userDAO = new UserDAO(SQLiteConnection.getInstance().getConnection());
    private final PictureDAO pictureDAO = new PictureDAO(SQLiteConnection.getInstance().getConnection());


    public void use(UserImageFxDTO input) {
        UserDTO user = new UserDTO(
                null,
                input.firstName(),
                input.lastName(),
                input.notes(),
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        Long userId = userDAO.save(user).id();
        String fileName = user.firstName() + "_" + user.lastName();
        AtomicInteger seq = new AtomicInteger();

        List<PictureDTO> pictures = input
                .images()
                .stream()
                .map(img -> saveImageToFile(img, userId, fileName + seq.getAndIncrement() + ".png"))
                .toList();

        pictures.forEach(pictureDAO::save);
    }

    private PictureDTO saveImageToFile(EmbeddedImage embeddedImage, long userId, String fileName) {
        // Get the APPDATA directory
        String appDataPath = System.getenv("APPDATA");
        if (appDataPath == null) {
            throw new IllegalStateException("APPDATA environment variable is not set.");
        }

        File appDir = new File(appDataPath, "FaceLockApp");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        // Define the file path in the application directory
        String filePath = new File(appDir, fileName).getAbsolutePath();
        Mat matImage = JavaFxImageConverter.toMat(embeddedImage.image());

        Imgcodecs.imwrite(filePath, matImage);

        return new PictureDTO(
                null,
                fileName,
                filePath,
                Picture.Types.PROFILE.toString(),
                embeddedImage.embedding(),
                userId,
                0L,
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

}
