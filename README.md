# Codex

This repository contains a sample Spring Batch project for processing PEPPOL UBL invoices. The batch job reads XML invoice files from the `input` directory and writes them unchanged to the `output` directory. The XML structure follows the PEPPOL UBL 2.1 specification.

Sample invoice XML files are located under `peppol-batch/src/test/resources`. In addition to `sample-invoice.xml`, a more detailed example is provided in `complex-invoice.xml`.

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

## Using samples from the Oxalis peppol-specifications repository

To try additional invoice examples, clone the specifications repository next to this project:

```bash
git clone https://github.com/OxalisCommunity/peppol-specifications.git
```

After cloning, run the batch job pointing the `input` directory at one of the XML files from the cloned repository. You can also run the test `SpecificationsInvoiceTest` by providing the repository location using the `peppolSpecDir` system property:

```bash
mvn test -DpeppolSpecDir=../peppol-specifications
```

