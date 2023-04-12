# Variant Caller

Variant Caller is a project developed by Polytechnic University students. It is a command-line application written in Java that allows you to call variants from DNA sequencing data in VCF (Variant Call Format) files.

## Usage
To use Variant Caller, you must have a VCF file with your sequencing data. The application will then analyze the data and output a list of variants with their corresponding genomic positions, reference bases, alternative bases, and quality scores.

## Technologies Used
This project uses the following technologies:

Java - The programming language used to write the application.
Maven - The build tool used to manage dependencies and build the project.
JUnit - The testing framework used to write and run tests.
Picard - The library used to perform various operations on SAM/BAM/CRAM and VCF files in Java.
HTSJDK - A Java library for high-throughput sequencing data analysis.
Development
To build and test the application, you can use the following commands:

```
$ mvn compile # compile the application
$ mvn test # run tests
```
