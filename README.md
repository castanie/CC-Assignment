# CC-Assignment

## Requisites

There are no hard requirements besides having installed the Java Development Kit and an internet connection; all other dependencies are either included within the repository or are loaded at buildtime.
This project was built and tested using Java 19 and Gradle 8 (included in this repo).

## Building, running, testing

To build the project, simply run `gradle clean build -x test`.
If you want to run the project, you can call `gradle clean run -x test`.
Gradle will automatically run tests while building binaries (without the `-x` option), but you can call them separately with `gradle test`.

## Tool usage
The interactive command line interface asks for three parameters: URL of the website, recursion depth when crawling and the language of the final report file. After entering all three, the crawler will take a few seconds to load all sites and build a report. The report will be written to the current folder as `report.md`.
