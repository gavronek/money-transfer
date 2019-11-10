<h1 align="center"> Money transfer service </h1> <br>

<p align="center">
  A minimalistic implementation in Kotlin of a REST based microservice to provide money transfer functionality.
  You can create/list/view/delete accounts and create new money transfer transactions between them.
</p>


## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Quick Start](#quick-start)
- [Testing](#testing)
- [API](#requirements)
- [Acknowledgements](#acknowledgements)




## Introduction

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

The goal of this minimalistic backend service is to provide a REST interface for a basic money transfer functionality. The requirements stated the following:
- Minimal framework support (no Spring!)
- In-memory data storage
- Executable as a standalone program

## Features

* Account management (Create, List, View, Delete)
* Create and show transactions (Create, List, View)


## Requirements
The application can be run locally. The requirements to run locally are listed below:

### Local
* min. Java 11 SDK
* min. Kotlin 1.3.41

### Run Local
```bash
$ ./gradlew run
```

Application will run on port `7000`

## API
[Detailed API documentation](docs/api.md)
