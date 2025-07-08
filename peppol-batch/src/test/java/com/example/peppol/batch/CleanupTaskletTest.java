package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import com.example.peppol.batch.tasklet.CleanupTasklet;

class CleanupTaskletTest {

    @Test
    void deletesDirectoriesRecursively() throws Exception {
        Path dir = Files.createTempDirectory("cleanup");
        Files.createDirectories(dir.resolve("sub").resolve("nested"));
        Files.writeString(dir.resolve("sub/file.txt"), "data");

        CleanupTasklet tasklet = new CleanupTasklet(dir);
        assertEquals(org.springframework.batch.repeat.RepeatStatus.FINISHED,
                tasklet.execute((StepContribution) null, (ChunkContext) null));
        assertFalse(Files.exists(dir));
    }
}
