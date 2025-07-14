package com.example.peppol.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Simplified tasklet that simulates fetching archives and extracting them.
 * <p>
 * In this example the tasklet merely unzips any <code>.zip</code> files found
 * in the input directory and places the contents under the working directory.
 * Encryption and remote download handling should be added in a real
 * implementation.
 * </p>
 */
public class FetchDecryptUnzipTasklet implements Tasklet {

    private final Path inputDir;
    private final Path workDir;

    public FetchDecryptUnzipTasklet(Path inputDir, Path workDir) {
        this.inputDir = inputDir;
        this.workDir = workDir;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Files.createDirectories(workDir);
        try (Stream<Path> s = Files.list(inputDir)) {
            s.filter(p -> p.toString().toLowerCase().endsWith(".zip"))
             .forEach(this::unzip);
        }
        return RepeatStatus.FINISHED;
    }

    private void unzip(Path zip) {
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zip))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }
                String filename = Paths.get(entry.getName()).getFileName().toString();
                Path targetDir = workDir;
                String lower = filename.toLowerCase();
                if (lower.endsWith(".xml")) {
                    targetDir = workDir.resolve("xml");
                } else if (lower.endsWith(".csv")) {
                    targetDir = workDir.resolve("csv");
                }
                Path out = targetDir.resolve(filename);
                Files.createDirectories(out.getParent());
                Files.copy(zis, out, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to unzip " + zip, e);
        }
    }
}
