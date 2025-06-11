# Codex

This repository contains a sample Spring Batch project for extracting embedded PEPPOL invoice XML files from PDF invoices. The batch job reads PDF files in the `input` directory and writes the extracted XML files to the `output` directory.

## Building

Use Maven to build the project:

```bash
cd peppol-batch
mvn package
```

## Running the batch job

Place your PDF invoices under the `input` directory, then run the job using:

```bash
java -jar target/peppol-batch-0.0.1-SNAPSHOT.jar
```

The extracted XML files will be created in the `output` directory.
