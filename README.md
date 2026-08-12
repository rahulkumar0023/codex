# Codex

This repository contains a sample Spring Batch project for processing PEPPOL UBL invoices. The XML files adhere to the UBL 2.1 specification.

## Project layout

- `peppol-batch/` — Spring Boot batch job
- `peppol-batch/src/test/resources` — sample invoice XML files (`sample-invoice.xml`, `complex-invoice.xml`)

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

To run the batch job locally after building:

```bash
java -jar target/peppol-batch-0.0.1-SNAPSHOT.jar
```

## Notes

- JAXB classes are generated from the UBL 2.1 schemas using the `jaxb-maven-plugin`.
- The project targets Java 17 and uses Jakarta XML bind runtime.

## Contributing

Contributions welcome. Please open an issue or submit a PR.

## License

MIT
