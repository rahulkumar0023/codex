package com.example.peppol.batch;

import java.nio.file.Path;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.peppol.batch.tasklet.InvoiceReadTasklet;
import com.example.peppol.batch.tasklet.InvoiceWriteTasklet;

@Configuration
@EnableBatchProcessing

public class BatchConfig {

    @Bean
    public Job peppolInvoiceJob(JobBuilderFactory jobs, Step readStep, Step writeStep) {
        return jobs.get("peppolInvoiceJob")
                .incrementer(new RunIdIncrementer())
                .start(readStep)
                .next(writeStep)
                .build();
    }

    @Bean
    public Step readStep(StepBuilderFactory steps) {
        Tasklet readTasklet = new InvoiceReadTasklet(Path.of("input"));
        return steps.get("readStep").tasklet(readTasklet).build();
    }

    @Bean
    public Step writeStep(StepBuilderFactory steps) {
        Tasklet writeTasklet = new InvoiceWriteTasklet(Path.of("output"));
        return steps.get("writeStep").tasklet(writeTasklet).build();
    }

}
