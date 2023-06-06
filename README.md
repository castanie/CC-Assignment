# CC-Assignment

## Requisites

There are no hard requirements besides having installed the Java Development Kit and an internet connection; all other dependencies are either included within the repository or are loaded at buildtime.
This project was built and tested using Java 19 and Gradle 8 (included in this repo).

## Building, running, testing
To build and run or test the crawler, simply run the App or the tests in your IDE.

## Tool usage
The interactive command line interface asks for three different parameters: URLs of the websites to crawl, recursion depth when crawling and the language of the final report file. After entering one URL, the User is asked to put another URL to be able to input as many as he or she likes, or the User can press Enter to continue and input the other parameters. After entering all, the crawler will take a few seconds to load all sites and build a report. The report will be written to the current folder as `report.md`. Moreover, an error log will be generated and automatically written into the logs file.
