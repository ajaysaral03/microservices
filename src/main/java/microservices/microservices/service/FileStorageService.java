package microservices.microservices.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageService {

    @Value("${product.image.upload-dir:uploads}") // default folder "uploads"
    private String uploadDir;

    public String storeFile(MultipartFile file) {
        // ensure folder exists
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // get extension (.jpg, .png etc.)
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.lastIndexOf(".") != -1) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // ✅ generate unique name
        String uniqueFileName = System.currentTimeMillis() + extension;

        // save file in uploads folder
        File dest = new File(directory, uniqueFileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + e.getMessage());
        }

        // ✅ DB me sirf filename save hoga (e.g. 1694308452789.jpg)
        return uniqueFileName;
    }
}
