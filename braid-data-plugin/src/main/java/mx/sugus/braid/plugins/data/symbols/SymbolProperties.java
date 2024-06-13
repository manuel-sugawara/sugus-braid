package mx.sugus.braid.plugins.data.symbols;

import java.util.function.Function;
import mx.sugus.braid.core.util.Name;
import mx.sugus.braid.jsyntax.Block;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.plugins.data.symbols.SymbolConstants.AggregateType;
import mx.sugus.braid.traits.UseBuilderReferenceTrait;
import software.amazon.smithy.codegen.core.Property;
import software.amazon.smithy.codegen.core.Symbol;
import software.amazon.smithy.model.shapes.ShapeType;

/**
 * Symbol properties.
 */
public final class SymbolProperties {
    /**
     * Property for the java type for a given symbol.
     */
    public static final Property<TypeName> JAVA_TYPE = from(TypeName.class);

    /**
     * Property for the java type for members to be used in the builder.
     */
    public static final Property<TypeName> BUILDER_JAVA_TYPE = from(TypeName.class);

    /**
     * Property for the java type for members that use builder references to be used in the builder.
     */
    public static final Property<TypeName> BUILDER_REFERENCE_JAVA_TYPE = from(TypeName.class);
    public static final Property<TypeName> BUILDER_REFERENCE_BUILDER_JAVA_TYPE = from(TypeName.class);

    /**
     * Property for the name of the method in the {@link #BUILDER_REFERENCE_JAVA_TYPE} that creates the builder from a
     * persistent value.
     */
    public static final Property<String> BUILDER_REFERENCE_FROM_PERSISTENT = from(String.class);

    /**
     * Property for the java name for a given symbol.
     */
    public static final Property<Name> SIMPLE_NAME = from(Name.class);

    /**
     * Property for the setter name for a given symbol.
     */
    public static final Property<Name> SETTER_NAME = Property.named("setter-name");

    /**
     * Property for the setter name for a given symbol.
     */
    public static final Property<Name> GETTER_NAME = Property.named("getter-name");

    /**
     * Property for the adder name for a given symbol.
     */
    public static final Property<Name> ADDER_NAME = Property.named("adder-name");

    /**
     * Property for the adder name for a given symbol.
     */
    public static final Property<Name> MULTI_ADDER_NAME = Property.named("multi-adder-name");

    /**
     * Property for the shape type for the symbol.
     */
    public static final Property<ShapeType> SHAPE_TYPE = Property.named("shape-type");

    /**
     * Property to mark the symbol as required.
     */
    public static final Property<Boolean> IS_REQUIRED = Property.named("is-required?");

    /**
     * Property to mark the symbol as constant.
     */
    public static final Property<Boolean> IS_CONSTANT = Property.named("is-constant?");

    /**
     * Property for the default value of a member shape.
     */
    public static final Property<String> DEFAULT_VALUE = Property.named("default-value");

    /**
     * Property for the type of aggregate the symbol represents.
     */
    public static final Property<AggregateType> AGGREGATE_TYPE = from(AggregateType.class);

    /**
     * Property for when a member should use a builder reference as in the given trait.
     */
    public static final Property<UseBuilderReferenceTrait> BUILDER_REFERENCE = from(UseBuilderReferenceTrait.class);

    /**
     * Property to flag if the shape has to preserve insertion order. Valid for sets and maps.
     */
    public static final Property<Boolean> IS_ORDERED = from(Boolean.class);

    /**
     * The method name in the class to get the value for the symbol.
     */
    public static final Property<String> GETTER = from(String.class);

    /**
     * The method name in the builder to set the value for the symbol.
     */
    public static final Property<String> SETTER = from(String.class);

    /**
     * The method name in the builder to add to a symbol representing a collection.
     */
    public static final Property<String> ADDER = from(String.class);

    /**
     * The method name in the builder to put in a symbol representing a map.
     */
    public static final Property<String> PUTTER = from(String.class);

    /**
     * The empty builder initializer code.
     */
    public static final Property<Function<Symbol, Block>> BUILDER_EMPTY_INIT = Property.named("builder-empty-init");

    /**
     * From data builder initializer code.
     */
    public static final Property<Function<Symbol, Block>> BUILDER_DATA_INIT = Property.named("builder-data-init");

    /**
     * From data builder code for setting a member.
     */
    public static final Property<Function<Symbol, Block>> BUILDER_SETTER_FOR_MEMBER = Property.named("builder-setter");

    /**
     * From builder data initializer code.
     */
    public static final Property<Function<Symbol, Block>> DATA_BUILDER_INIT = Property.named("data-builder-init");

    private SymbolProperties() {
    }

    private static <T> Property<T> from(Class<T> kclass) {
        return Property.named(kclass.getName());
    }
}
