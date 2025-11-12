# ioss-intermediary-registration-ui-tests

IOSS Intermediary Registration UI journey tests.

## Pre-requisites

### Services

Start Mongo Docker container as follows:

```bash
docker run --rm -d -p 27017:27017 --name mongo percona/percona-server-mongodb:6.0
```

Start `IMPORT_ONE_STOP_SHOP_ALL` services as follows:

```bash
sm2 --start IMPORT_ONE_STOP_SHOP_ALL
```

## Tests

Run tests on the command line via one of the run scripts:
```bash
./run_tests.sh
```
or
```bash
./run_extra_tests.sh
```

These were simply split into two scripts so that they could run in parallel on Jenkins to save time.
```

## Scalafmt

Check all project files are formatted as expected as follows:

```bash
sbt scalafmtCheckAll scalafmtCheck
```

Format `*.sbt` and `project/*.scala` files as follows:

```bash
sbt scalafmtSbt
```

Format all project files as follows:

```bash
sbt scalafmtAll
```

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
