package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipFile;

import org.junit.jupiter.api.Test;

import com.example.peppol.batch.tasklet.PackageAndUploadTasklet;

class PackageAndUploadTaskletTest {
    @Test
    void packagesProcessedFiles() throws Exception {
        Path processed = Files.createTempDirectory("processed");
        Files.writeString(processed.resolve("a.txt"), "a");
        Files.createDirectories(processed.resolve("sub"));
        Files.writeString(processed.resolve("sub/b.txt"), "b");

        Path upload = Files.createTempDirectory("upload");

        PackageAndUploadTasklet tasklet = new PackageAndUploadTasklet(processed, upload);
        tasklet.execute(null, null);

        Path zip = upload.resolve("package.zip");
        assertTrue(Files.exists(zip));
        try (ZipFile zf = new ZipFile(zip.toFile())) {
            assertNotNull(zf.getEntry("a.txt"));
            assertNotNull(zf.getEntry("sub/b.txt"));
        }
    }
}
