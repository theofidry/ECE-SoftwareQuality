Software Quality
================

<a href="http://www.ece.fr">
    <img src="http://upload.wikimedia.org/wikipedia/fr/thumb/f/f8/Logo_ECE_Paris.svg/1024px-Logo_ECE_Paris.svg.png" width="auto" height="75px" />
</a>

ECE Project for Software Quality class: program a simple landing gear system (cf. [Case study](https://github.com/theofidry/Software-Quality/blob/master/Case%20study.pdf)).

Check [this page](http://theofidry.github.io/SoftwareQuality-ECEProject) for the build results (includes tests, javadoc, artefacts...).

Travis CI build:

[![Build Status](https://travis-ci.org/theofidry/SoftwareQuality-ECEProject.svg?branch=master)](https://travis-ci.org/theofidry/SoftwareQuality-ECEProject)

Sonar analysis available [here](http://87.98.155.63:9000/dashboard/index/14).

Compatibilities and dependencies:

* Gradle 2.2.1
* JDK7+

# Getting started

Clone this project:

```bash
git clone https://github.com/theofidry/SoftwareQuality-ECEProject.git
```

Install gradle: [link](http://www.gradle.org/installation)

If you wish to open the project as a whole, import it as a Gradle project.

To build the application:

```bash
gradle build    # with tests
gradle assemble # just build the Java classes
```

You can the find the jars in `build/libs`.

You can also start the application right away by doing:
```bash
gradle run
```

# Project layout

```
.
└── src                
    ├── main           
    |   ├── java       # production Java source
    |   ├── resources  # production resources
    └── test           # test Java source
```

# Contributors

* [Théo FIDRY](https://github.com/theofidry)
* [Dreena BISWAS](https://github.com/dreenabiswas)
