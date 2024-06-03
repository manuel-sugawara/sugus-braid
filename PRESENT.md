
## Summary

* Bride is a Smithy java code generator

* It supports all non-service shapes

* It can be easily extended to support service shapes

* It diverges from the "golden-path" in several ways


## How it works

* The core just orchestrates the code generation

* It is extended using plugins

* Each plugin can

  * Produce, generate anything out of a Smithy shape, (e.g., in-memory
    structures that represent Java syntax)

  * Transform, modify previously produced or transformed artifacts
    (e.g., adding methods to a generated java class)

  * Consume, the last phase of the generation pipeline, takes
    artifacts from previous phases, usually used to write to artifacts
    (e.g., Java files).

## Java Codegen

* The main use-case as of now is Java codegen

* There are several traits that can be added to the model to enhance
  the generated code.

  * For instance to add common methods, in particular to the builders, that
    helps with the usability of the resulting API.

  * Also, representing interfaces and nodes implementing the interface
    (c.f., to sealed interface types, and union types)

  * Mapping structures to non-modeled Java classes

  * Members with constant-static values

## Java Syntax

* To generate Java it uses its own representation of Java syntax

* The API is designed to be similar to Java Poet

* The syntax node are themselves are generated out a Smithy model

## Plugins

* The base data plugins generates "POJO"s for every non-service Smithy
  shape

* There are plugins for other tasks, we currently have

  * A plugin that generates visitors for a model and adds "accept"
    methods to the data shapes.

  * A plugin that generates to/from node methods to support Smithy
    Node based serde (easy way to serialize but can also be used to
    generate traits).

  * The java syntax mode has its own plugin that adds methods to aid
    to the usability of some classes.

## Final remarks

* Building structures in memory, as opposed to "building strings",
  allows extensibility and code reuse

* The generation as-is can accommodate several use cases

  * Plain Java codegen (c.f., lombok)

  * Data objects with serde (c.f., events, SNS messages, DynamoDB
    records, etc)

  * Abstract Syntax Trees

  * Also, SDKs
