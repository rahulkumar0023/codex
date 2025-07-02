# Codex

This repository contains a sample Spring Batch project for processing PEPPOL UBL invoices. The XML files adhere to the UBL 2.1 specification. The batch job is organised into two tasklet based steps: the first reads invoices from the `input` directory and stores them in the job context, while the second writes the invoices to the `output` directory.

Sample invoice XML files are located under `peppol-batch/src/test/resources`. In addition to `sample-invoice.xml`, a more detailed example is provided in `complex-invoice.xml`.

## Maven configuration

The `peppol-batch/pom.xml` file declares the dependencies needed for the batch job:
- `spring-boot-starter-batch` and `spring-boot-starter` provide Spring Batch support.
- `pdfbox` enables extracting embedded XML from PDFs.
- `spring-boot-starter-test` supplies JUnit for tests.
- `peppol-ubl21` provides JAXB classes for working with UBL 2.1 invoices.



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


## Parsing invoices to Java objects

The project includes a simple `UblInvoiceParser` that uses the `peppol-ubl21`
library to convert invoice XML into JAXB classes. The following snippet parses
an invoice and prints its ID:

```java
String xml = Files.readString(Path.of("complex-invoice.xml"));
UblInvoiceParser parser = new UblInvoiceParser();
InvoiceType invoice = parser.parse(xml);
System.out.println(invoice.getID().getValue());
```

## Using samples from the Oxalis peppol-specifications repository

To try additional invoice examples, clone the specifications repository next to this project:

```bash
git clone https://github.com/OxalisCommunity/peppol-specifications.git
```

After cloning, run the batch job pointing the `input` directory at one of the XML files from the cloned repository. You can also run the test `SpecificationsInvoiceTest` by providing the repository location using the `peppolSpecDir` system property:

```bash
mvn test -DpeppolSpecDir=../peppol-specifications
```

The `SpecificationsInvoiceTest` reads the first XML file it can find in the
cloned repository and writes the invoice to a temporary directory. The output
path is reported by the test and the file contents should match the original
invoice.

