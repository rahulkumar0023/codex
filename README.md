# Codex

This repository contains a sample Spring Batch project for processing PEPPOL UBL invoices. The XML files adhere to the UBL 2.1 specification. The batch job is organised into two tasklet based steps: the first reads invoices from the `input` directory and stores them in the job context, while the second writes the invoices to the `output` directory.

Sample invoice XML files are located under `peppol-batch/src/test/resources`. In addition to `sample-invoice.xml`, a more detailed example is provided in `complex-invoice.xml`.

## Maven configuration

The `peppol-batch/pom.xml` file declares the dependencies needed for the batch job:
- `spring-boot-starter-batch` and `spring-boot-starter` provide Spring Batch support.
- `pdfbox` enables extracting embedded XML from PDFs.
- `spring-boot-starter-test` supplies JUnit for tests.
- `peppol-ubl21` provides JAXB classes for working with UBL 2.1 invoices.

- `jakarta.xml.bind-api` and `jaxb-runtime` bring in Jakarta JAXB 4 so the code
  compiles on Java 17.


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

The batch steps internally rely on `XmlInvoiceReader` to read each invoice into
`InvoiceType` objects and `XmlInvoiceWriter` to write them back to XML.
`XmlCreditNoteReader` and `UblCreditNoteWriter` provide the same functionality
for credit notes.




## Parsing invoices to Java objects

The project includes a simple `XmlInvoiceReader` that uses the `peppol-ubl21`
library to convert invoice XML into JAXB classes. The following snippet parses


an invoice and prints its ID:

```java
String xml = Files.readString(Path.of("complex-invoice.xml"));
XmlInvoiceReader parser = new XmlInvoiceReader();
InvoiceType invoice = parser.parse(xml);
System.out.println(invoice.getID().getValue());
```

Once an `InvoiceType` object is available it can be written back to XML using
`XmlInvoiceWriter`:

```java

XmlInvoiceWriter writer = new XmlInvoiceWriter(); // main writer
Path outFile = Path.of("invoice.xml");
writer.write(invoice, outFile);
```
The writer uses a `NamespacePrefixMapper` so the output starts with:
`<Invoice xmlns="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"` and
declares the canonical `cac` and `cbc` prefixes.

Similarly, credit notes can be handled with:

```java
XmlCreditNoteReader cnParser = new XmlCreditNoteReader();
CreditNoteType creditNote = cnParser.parse(xml);
UblCreditNoteWriter cnWriter = new UblCreditNoteWriter();
cnWriter.write(creditNote, Path.of("credit-note.xml"));
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


## Example end-to-end scenario

The project can be extended into a full batch pipeline using the following
approach:

1. **Fetch archives** from an S3 compatible store and place them under
   `/tmp/raw`.
2. **Decrypt and unzip** each `zip.pgp` archive with a custom `Tasklet`. The
   extracted XML files should end up in `/tmp/unzipped`.
3. **Parse invoices** using a `MultiResourceItemReader<InvoiceType>` backed by
   JAXB classes generated from the PEPPOL UBL 2.1 schemas.
4. **Process invoices** as needed. The reference code simply re‑marshals them
   without writing to a database.
5. **Write results** to `/tmp/processed/xml` and generate placeholder PDFs in
   `/tmp/processed/pdf`. A `NamespacePrefixMapper` ensures that the output uses
   the standard `cac` and `cbc` prefixes with no `nsXX` namespaces.
6. **Upload or deliver** the processed files if required.
7. **Clean up** temporary directories so every job run starts with a clean
   workspace.

Running the batch job repeatedly can be achieved with a Spring `TaskScheduler`
that launches it every minute.

