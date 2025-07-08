package com.example.peppol.batch.tasklet;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.stream.Stream;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Deletes temporary directories created during the job run.
 */
public class CleanupTasklet implements Tasklet {

    private final Path[] dirs;

    public CleanupTasklet(Path... dirs) {
        this.dirs = dirs;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        for (Path dir : dirs) {
            deleteRecursively(dir);
        }
        return RepeatStatus.FINISHED;
    }

    private void deleteRecursively(Path dir) throws IOException {
        if (!Files.exists(dir)) {
            return;
        }
        List<Path> paths;
        try (Stream<Path> s = Files.walk(dir)) {
            paths = s.sorted(Comparator.reverseOrder()).toList();
        }
        for (Path p : paths) {
            try {
                Files.deleteIfExists(p);
            } catch (IOException e) {
                // ignore cleanup failures
            }
        }
    }
}
