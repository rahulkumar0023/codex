# Codex

This repository contains a sample Spring Batch project for processing PEPPOL UBL invoices. The batch job reads XML invoice files from the `input` directory and writes them unchanged to the `output` directory. The XML structure follows the PEPPOL UBL 2.1 specification.

## Building

Use Maven to build the project:

```bash
cd peppol-batch
mvn package
```

## Testing

Run the unit tests with Maven:

```bash
mvn test
```

## Running the batch job

Place your PEPPOL XML invoices under the `input` directory, then run the job using:

```bash
java -jar target/peppol-batch-0.0.1-SNAPSHOT.jar
```

The XML files will be created in the `output` directory with the same file names.
