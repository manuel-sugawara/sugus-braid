package mx.sugus.braid.plugins.data.symbols;

import java.util.function.BiFunction;
import mx.sugus.braid.core.plugin.CodegenState;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.util.Name;
import mx.sugus.braid.jsyntax.Block;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.plugins.data.symbols.SymbolConstants.AggregateType;
import mx.sugus.braid.traits.UseBuilderReferenceTrait;
import software.amazon.smithy.codegen.core.Property;
import software.amazon.smithy.model.shapes.MemberShape;

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
     * Property for the name of the method in the {@link #BUILDER_REFERENCE_JAVA_TYPE} that creates the builder from a persistent
     * value.
     */
    public static final Property<String> BUILDER_REFERENCE_FROM_PERSISTENT = from(String.class);

    /**
     * Property for the java name for a given symbol.
     */
    public static final Property<Name> RAW_NAME = from(Name.class);

    /**
     * Property for the unescaped name for a given symbol.
     */
    public static final Property<Name> SIMPLE_NAME = from(Name.class);

    /**
     * Property for the java name for a given symbol.
     */
    public static final Property<Name> JAVA_NAME = from(Name.class);

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
     * Property to mark the symbol as required.
     */
    public static final Property<Boolean> IS_REQUIRED = Property.named("is-required?");

    /**
     * Property to mark the symbol as constant.
     */
    public static final Property<Boolean> IS_CONSTANT = Property.named("is-constant?");

    /**
     * Property to get a code block for the default value of a member shape.
     */
    public static final Property<BiFunction<CodegenState, MemberShape, CodeBlock>> DEFAULT_VALUE =
        Property.named("default-value");

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
    public static final Property<BiFunction<ShapeCodegenState, MemberShape, Block>> BUILDER_EMPTY_INIT
        = Property.named("builder-empty-init");

    public static final Property<BiFunction<ShapeCodegenState, MemberShape, CodeBlock>> BUILDER_EMPTY_INIT_EXPRESSION
        = Property.named("builder-empty-init-expression");

    /**
     * From data builder initializer code.
     */
    public static final Property<BiFunction<ShapeCodegenState, MemberShape, Block>> BUILDER_DATA_INIT = Property.named("builder-data-init");

    /**
     * From data builder initializer expression.
     */
    public static final Property<BiFunction<ShapeCodegenState, MemberShape, CodeBlock>> BUILDER_DATA_INIT_EXPRESSION
        = Property.named("builder-data-init-expression");

    /**
     * From data builder initializer expression.
     */
    public static final Property<BiFunction<ShapeCodegenState, MemberShape, CodeBlock>> BUILDER_UNION_DATA_INIT_EXPRESSION
        = Property.named("builder-union-data-init-expression");

    /**
     * From data builder code for setting a member.
     */
    public static final Property<BiFunction<ShapeCodegenState, MemberShape, Block>> BUILDER_SETTER_FOR_MEMBER = Property.named("builder-setter");

    /**
     * From builder data initializer code.
     */
    public static final Property<BiFunction<ShapeCodegenState, MemberShape, Block>> DATA_BUILDER_INIT = Property.named("data-builder-init");

    private SymbolProperties() {
    }

    private static <T> Property<T> from(Class<T> kclass) {
        return Property.named(kclass.getName());
    }
}
