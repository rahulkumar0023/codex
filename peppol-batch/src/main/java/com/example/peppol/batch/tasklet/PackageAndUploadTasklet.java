package com.example.peppol.batch.tasklet;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Tasklet that zips the processed files and simulates uploading them.
 *
 * <p>Only local filesystem operations are performed in this example. The
 * resulting archive is created under the provided upload directory.</p>
 */
public class PackageAndUploadTasklet implements Tasklet {

    private final Path processedDir;
    private final Path uploadDir;

    public PackageAndUploadTasklet(Path processedDir, Path uploadDir) {
        this.processedDir = processedDir;
        this.uploadDir = uploadDir;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Files.createDirectories(uploadDir);
        Path zipFile = uploadDir.resolve("package.zip");
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipFile))) {
            Files.walk(processedDir)
                .filter(Files::isRegularFile)
                .forEach(p -> addEntry(zos, processedDir.relativize(p), p));
        }
        return RepeatStatus.FINISHED;
    }

    private void addEntry(ZipOutputStream zos, Path relative, Path file) {
        try {
            zos.putNextEntry(new ZipEntry(relative.toString().replace('\\', '/')));
            Files.copy(file, zos);
            zos.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException("Failed to add " + file + " to archive", e);
        }
    }
}
