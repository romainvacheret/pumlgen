# Pumlgen

## What is Pumlgen ?

It is an UML generator for Java projets which uses the [PlantUML](https://plantuml.com/en) langage

## Installation

Prerequisites:
- [JDK 16](https://www.oracle.com/java/technologies/javase/jdk16-archive-downloads.html)
- [Gradle 7](https://gradle.org/install/) 

```bash
git clone https://github.com/RomainVacheret/pumlgen.git
cd pumlgen
```

## Execution
To run the example:
```bash
gradle run --args='-e'
```

To run on a chosen directory:
```bash
gradle run --args='-p PATH_TO_DIRECTORY'
```