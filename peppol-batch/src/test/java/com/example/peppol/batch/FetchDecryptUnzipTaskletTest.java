package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.jupiter.api.Test;

import com.example.peppol.batch.tasklet.FetchDecryptUnzipTasklet;

class FetchDecryptUnzipTaskletTest {
    @Test
    void unzipsFilesFromInputDirectory() throws Exception {
        Path inputDir = Files.createTempDirectory("input");
        Path workDir = Files.createTempDirectory("work");

        Path zip = inputDir.resolve("archive.zip");
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zip))) {
            zos.putNextEntry(new ZipEntry("test.txt"));
            zos.write("hello".getBytes());
            zos.closeEntry();
        }

        FetchDecryptUnzipTasklet tasklet = new FetchDecryptUnzipTasklet(inputDir, workDir);
        tasklet.execute(null, null);

        assertTrue(Files.exists(workDir.resolve("test.txt")));
    }
}
