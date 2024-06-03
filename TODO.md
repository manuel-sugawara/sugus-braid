# TODOs items

## Project Wise

* Add testing, almost nothing is unit-tested. Device a strategy
  tackling one module at the time.

* Some stuff such as the suffix for input/output structures should be
  a configuration setting.

## Enums

* The unknow variant name should be configurable such that we can use
  the naming of other projects sucha as the AWS Java SDK.

## Codegen

* `AbstractControlFlow` vs concrete ones, arguably concrete ones will
  give more control but on the other hand it's hard to handle nicely
  all of these

## Plugins

### Serde

* Consider a generic serde, ala rust, plugin. This will need an
  adjacent project to define the serde logic and then a plugin to
  codegen for it.

### Node Serde

* Bring back the plugin that reads/writes using smithy-node. That will
  be useful to projects that already take a dependency on smithy such
  as

  * This codegen plugin settings for this project

  * AWS Java SDK codegen, e.g., all the mapping from/to C2J models,
    including endpoints and other nuances.

  * Trait codegen (alternative to what smithy already has)

### Syntax Visitors

* Rewrite visitor disappeared along the way, consider adding it back,
  to make it useful we need to keep the parent along the way.

  * We can add helper methods along the lines of `enclosingMethod` and
    `enclosingType`, that'd be cool.

* Walk Visitor traversing order is not correct for interfaces &
  inheritors: inheritors members should go *after* (??)

## Known Issues

### Node Serde

* fromNode is incorrect for enums, e.g.,
```
  builder.name(obj.expectMember("name")TypePrimitiveName.from(.expectStringNode().getValue()));
```


----
## Done

* Consolidate all the package into one, including the syntax model and
  java traits.

* Move the naming logic onto the symbolProvider to handle:

  * Adders to singular case needs to check for reserved words

* Move away from `DirectedCodegen` in favor of a plain smithy plugin
  plugin and clean up after that.

  * The naming logic might be easier to move after as we will get to
    decide how to pass and keep the symbol provider instead of letting
    the directed codegen do that for us.

* Cashing the hashCode should not always be present, consider using
  some heuristics such as includes structures or aggregates to improve
  it.

* Using smithy's codegen writer should be an option but not the
  default for writing code, a much simpler one can be implemented.

## Known Issues

All classes are including the following non-required imports

```
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.Modifier;
```
