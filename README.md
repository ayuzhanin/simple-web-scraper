# Simple Web Scraper

### Description

Console [web scraper](http://en.wikipedia.org/wiki/Web_scraping) utility for [HireRight](http://hireright.com/) which accepts as command line parameters:

  - web resources URL or path to plain text file containing a list of URLs
  - path to file for writing results (or to standard output)
  - word (or list of words with comma as a delimiter)
  - data processing commands:
  - - count number of provided word(s) occurrence on webpage(s) `-w`,
  - - count number of characters of each web page `-c`,
  - - extract sentences’ which contain given words `-e`,
  - output verbosity flag `-v`. (If on then the output contains information about time spend on data scraping and data processing)

### Packaging
Clone the project and run
```sh
mvn clean package jar:jar
```
Now check `./target/` directory for `scrapper.jar` artifact.

### Usage example and running

Let's say you want count number of word `Java` in Java wikipedia [page](https://en.wikipedia.org/wiki/Java_(programming_language)). So you can run
```sh
java –jar scraper.jar https://en.wikipedia.org/wiki/Java_(programming_language) _ Java –w
```

What if you want also to know how often `James Gosling` is mentioned there? Try this
```sh
java –jar scraper.jar https://en.wikipedia.org/wiki/Java_(programming_language) _ James Gosling, Java –w
```

Maybe you want to check several URLs in one command? Let's say you want to count occurrences of words `Java`, `Scala`, `James Gosling` and `Martin Odersky` in Java wikipedia [page](https://en.wikipedia.org/wiki/Java_(programming_language)) and Scala wikipedia [page](https://en.wikipedia.org/wiki/Scala_(programming_language)).

So you might be want to try create a file `myurls` with content as following
```sh
https://en.wikipedia.org/wiki/Java_(programming_language) 
https://en.wikipedia.org/wiki/Scala_(programming_language)
```

and then run

```sh
java –jar scraper.jar ~/myurls _ Java, Scala, James Gosling, Martin Odersky –w
```


Output may become too long, so it would be nice to have it in separate file.
Instead of command above run this
```sh
java –jar scraper.jar ~/myurls ~/output Java, Scala, James Gosling, Martin Odersky –w
```

Also you can use several flags at once
```sh
java –jar scraper.jar ~/myurls ~/output Java, Scala, James Gosling, Martin Odersky –w -v -c -e
```
