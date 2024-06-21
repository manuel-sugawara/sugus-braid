$version: "2.0"

namespace mx.sugus.braid.jsyntax

use mx.sugus.braid.traits#addAllOverrides
use mx.sugus.braid.traits#adderOverrides
use mx.sugus.braid.traits#fromFactories
use mx.sugus.braid.traits#multiAddOverrides
use mx.sugus.braid.traits#newBuilderOverrides
use mx.sugus.braid.traits#setterOverrides

apply ClassSyntax @newBuilderOverrides([
    {
        args: [
            {
                type: "java.lang#String"
                name: "name"
            }
        ]
        body: ["return builder().name(name)"]
    }
])

apply InterfaceSyntax @newBuilderOverrides([
    {
        args: [
            {
                type: "java.lang#String"
                name: "name"
            }
        ]
        body: ["return builder().name(name)"]
    }
])

apply EnumSyntax @newBuilderOverrides([
    {
        args: [
            {
                type: "java.lang#String"
                name: "name"
            }
        ]
        body: ["return builder().name(name)"]
    }
])

apply ModifierList @multiAddOverrides([
    {
        args: [
            {
                type: "javax.lang.model.element.Modifier"
                name: "modifier1"
            }
            {
                type: "javax.lang.model.element.Modifier"
                name: "modifier2"
            }
        ]
        body: ["modifier1", "modifier2"]
    }
    {
        args: [
            {
                type: "javax.lang.model.element.Modifier"
                name: "modifier1"
            }
            {
                type: "javax.lang.model.element.Modifier"
                name: "modifier2"
            }
            {
                type: "javax.lang.model.element.Modifier"
                name: "modifier3"
            }
        ]
        body: ["modifier1", "modifier2", "modifier3"]
    }
])

apply FieldSyntax @fromFactories([
    {
        args: [
            {
                type: "TypeName"
                name: "type"
            }
            {
                type: "java.lang#String"
                name: "name"
            }
        ]
        body: ["""
            return FieldSyntax.builder()
                       .addModifiers(javax.lang.model.element.Modifier.PRIVATE, javax.lang.model.element.Modifier.FINAL)
                       .name(name)
                       .type(type)
                       .build()"""]
    }
    {
        args: [
            {
                type: "java.lang#Class<?>"
                name: "kclass"
            }
            {
                type: "java.lang#String"
                name: "name"
            }
        ]
        body: ["""
            return FieldSyntax.builder()
                       .addModifiers(javax.lang.model.element.Modifier.PRIVATE, javax.lang.model.element.Modifier.FINAL)
                       .name(name)
                       .type(ClassName.from(kclass))
                       .build()"""]
    }
    {
        name: "mutableFrom"
        args: [
            {
                type: "TypeName"
                name: "type"
            }
            {
                type: "java.lang#String"
                name: "name"
            }
        ]
        body: ["""
            return FieldSyntax.builder()
                       .addModifier(javax.lang.model.element.Modifier.PRIVATE)
                       .name(name)
                       .type(type)
                       .build()"""]
    }
    {
        name: "mutableFrom"
        args: [
            {
                type: "java.lang#Class<?>"
                name: "kclass"
            }
            {
                type: "java.lang#String"
                name: "name"
            }
        ]
        body: ["""
            return FieldSyntax.builder()
                       .addModifier(javax.lang.model.element.Modifier.PRIVATE)
                       .name(name)
                       .type(ClassName.from(kclass))
                       .build()"""]
    }
])

apply Annotation @newBuilderOverrides([
    {
        args: [
            {
                type: "ClassName"
                name: "type"
            }
        ]
        body: ["return builder().type(type)"]
    }
    {
        args: [
            {
                type: "java.lang#Class<?>"
                name: "kclass"
            }
        ]
        body: ["return builder().type(ClassName.from(kclass))"]
    }
])

apply Annotation @fromFactories([
    {
        javadoc: """
        Creates a new annotation with a single string member value.
        """
        name: "fromStringValue"
        args: [
            {
                type: "java.lang#Class<?>"
                name: "kclass"
            }
            {
                type: "String"
                name: "value"
            }
        ]
        body: ["""
        return builder().type(ClassName.from(kclass))
            .putMember("value", MemberValue.forExpression(CodeBlock.from("$$S", value)))
            .build()
        """]
    }
])

apply AnnotationList @adderOverrides([
    {
        args: [
            {
                type: "ClassName"
                name: "type"
            }
        ]
        body: ["Annotation.builder(type).build()"]
    }
    {
        args: [
            {
                type: "java.lang#Class<?>"
                name: "kclass"
            }
        ]
        body: ["Annotation.builder(kclass).build()"]
    }
])

// TODO: Consider adding this by default without having to manually configure it for all union shapes.
apply MemberValue @fromFactories([
    {
        javadoc: "Creates a new `MemberValue` for the expression variant"
        name: "forExpression"
        args: [
            {
                type: "java.lang#String"
                name: "format"
            }
            {
                type: "java.lang#Object..."
                name: "args"
            }
        ]
        body: ["return builder().expression(CodeBlock.from(format, args)).build()"]
    }
    {
        javadoc: "Creates a new `MemberValue` for the expression variant"
        name: "forExpression"
        args: [
            {
                type: "CodeBlock"
                name: "codeBlock"
            }
        ]
        body: ["return builder().expression(codeBlock).build()"]
    }
    {
        javadoc: "Creates a new `MemberValue` for the array expression variant"
        name: "forArrayExpression"
        args: [
            {
                type: "CodeBlock..."
                name: "values"
            }
        ]
        body: ["return builder().arrayExpression(java.util.Arrays.asList(values)).build()"]
    }
])

apply ParameterList @adderOverrides([
    {
        args: [
            {
                type: "java.lang#Class<?>"
                name: "kclass"
            }
            {
                type: "java.lang#String"
                name: "name"
            }
        ]
        body: ["Parameter.builder().name(name).type(TypeName.from(kclass)).build()"]
    }
    {
        args: [
            {
                type: "TypeName"
                name: "type"
            }
            {
                type: "java.lang#String"
                name: "name"
            }
        ]
        body: ["Parameter.builder().name(name).type(type).build()"]
    }
])

apply MethodSyntax @newBuilderOverrides([
    {
        args: [
            {
                type: "java.lang#String"
                name: "name"
            }
        ]
        body: ["return builder().name(name)"]
    }
])

apply AbstractMethodSyntax @newBuilderOverrides([
    {
        args: [
            {
                type: "java.lang#String"
                name: "name"
            }
        ]
        body: ["return builder().name(name)"]
    }
])

apply CodeBlock @fromFactories([
    {
        args: [
            {
                type: "java.lang#String"
                name: "format"
            }
            {
                type: "java.lang#Object..."
                name: "args"
            }
        ]
        body: ["return builder().parts(FormatParser.parseFormat(format, args)).build()"]
    }
])

apply FormatterNodeList @addAllOverrides([
    {
        name: "addCode"
        javadoc: """
            Adds the formatted code to the block builder
            """
        args: [
            {
                type: "java.lang#String"
                name: "format"
            }
            {
                type: "java.lang#Object..."
                name: "args"
            }
        ]
        body: ["FormatParser.parseFormat(format, args)"]
    }
])

apply ClassName @fromFactories([
    {
        javadoc: """
            Creates a class name using the given java class.
            """
        args: [
            {
                type: "java.lang#Class<?>"
                name: "kclass"
            }
        ]
        body: [
            """
            if (kclass.isArray()) {
                throw new IllegalArgumentException("Array types not supported, try using TypeName.from(Class<?>) instead");
            }
            Class<?> enclosing = kclass.getEnclosingClass();
            if (enclosing == null) {
                return builder().packageName(kclass.getPackageName()).name(kclass.getSimpleName()).build();
            }
            java.util.Deque<String> deque = new java.util.ArrayDeque<>();
            deque.add(kclass.getSimpleName());
            while (enclosing != null) {
                deque.push(enclosing.getSimpleName());
                enclosing = enclosing.getEnclosingClass();
            }
            return builder().packageName(kclass.getPackageName()).name(String.join(".", deque)).build()"""
        ]
    }
    {
        javadoc: """
            Creates a class name with the given package and simple name.
            """
        args: [
            {
                type: "java.lang#String"
                name: "packageName"
            }
            {
                type: "java.lang#String"
                name: "simpleName"
            }
        ]
        body: ["return builder().packageName(packageName).name(simpleName).build()"]
    }
    {
        javadoc: """
            Creates a class name without a package.
            """
        args: [
            {
                type: "java.lang#String"
                name: "simpleName"
            }
        ]
        body: [
            """
            return builder()
                .name(simpleName)
                .build()"""
        ]
    }
    {
        name: "parse"
        javadoc: """
            Parses the given name as qualified java type. Recognizes `#` as package separator to
            distinguish the package name from the class name. If not uses dots and takes the last
            segment as a class name and the previous ones as package name.
            """
        args: [
            {
                type: "java.lang#String"
                name: "name"
            }
        ]
        body: [
            """
            int splitIndex = -1;
            // Check if the `name` is from a shape
            int sharpIndex = name.indexOf('#');
            if (sharpIndex != -1) {
                splitIndex = sharpIndex;
            } else {
                // Check if the `name` is from a fully qualified class name
                int lastDotIndex = name.lastIndexOf('.');
                if (lastDotIndex == -1) {
                    return ClassName.builder().name(name).build();
                }
                splitIndex = lastDotIndex;
            }
            return builder()
                .packageName(name.substring(0, splitIndex))
                .name(name.substring(splitIndex + 1))
                .build()"""
        ]
    }
    {
        name: "toEnclosing"
        javadoc: """
            Returns the top level enclosing class name if this class name represents an inner class,
            otherwise returns this className unchanged.
            """
        args: [
            {
                type: "ClassName"
                name: "className"
            }
        ]
        body: [
            """
            String name = className.name();
            int indexOfDot = name.indexOf('.');
            if (indexOfDot == -1) {
                return className;
            }
            return className.toBuilder().name(name.substring(0, indexOfDot)).build()"""
        ]
    }
    {
        name: "toClassName"
        javadoc: """
            Returns the type name as class name. If the given type name is a parametrized type name it
            returns its base class, if this is an array type, the component class.
            """
        args: [
            {
                type: "TypeName"
                name: "type"
            }
        ]
        body: [
            """
            while (true) {
                if (type instanceof ClassName c) {
                    return c;
                }
                if (type instanceof ParameterizedTypeName p) {
                    return p.rawType();
                }
                if (type instanceof ArrayTypeName a) {
                    type = a.componentType();
                    continue;
                }
                break;
            }
            throw new IllegalArgumentException("Cannot convert type: " + type.kind() + ", to java class")"""
        ]
    }
])

apply ParameterizedTypeName @fromFactories([
    {
        args: [
            {
                type: "ClassName"
                name: "base"
            }
            {
                type: "TypeName..."
                name: "params"
            }
        ]
        body: [
            """
            ParameterizedTypeName.Builder builder = builder()
                                                    .rawType(base);
            for (TypeName param : params) {
                builder.addTypeArgument(param);
            }
            return builder.build()"""
        ]
    }
    {
        args: [
            {
                type: "java.lang#Class<?>"
                name: "kclass"
            }
            {
                type: "TypeName..."
                name: "params"
            }
        ]
        body: [
            """
            ParameterizedTypeName.Builder builder = builder()
                                                    .rawType(ClassName.from(kclass));
            for (TypeName param : params) {
                builder.addTypeArgument(param);
            }
            return builder.build()"""
        ]
    }
    {
        args: [
            {
                type: "java.lang#Class<?>"
                name: "kclass"
            }
            {
                type: "java.lang#Class<?>..."
                name: "params"
            }
        ]
        body: [
            """
            ParameterizedTypeName.Builder builder = builder()
                                                    .rawType(ClassName.from(kclass));
            for (Class<?> param : params) {
                builder.addTypeArgument(TypeName.from(param));
            }
            return builder.build()"""
        ]
    }
])

apply TypeVariableTypeName @fromFactories([
    {
        args: [
            {
                type: "java.lang#String"
                name: "name"
            }
        ]
        body: [
            """
            return TypeVariableTypeName.builder().name(name).build()"""
        ]
    }
])

apply TypeName @fromFactories([
    {
        javadoc: """
            Creates a new TypeName instance out of the given class.
            """
        args: [
            {
                type: "java.lang#Class<?>"
                name: "kclass"
            }
        ]
        body: [
            """
            return mx.sugus.braid.jsyntax.ext.TypeNameExt.from(kclass)"""
        ]
    }
])

apply Javadoc @setterOverrides([
    {
        args: [
            {
                type: "java.lang#String"
                name: "format"
            }
            {
                type: "java.lang#Object..."
                name: "args"
            }
        ]
        body: "CodeBlock.from(format, args)"
    }
])

apply TypeName @setterOverrides([
    {
        args: [
            {
                type: "java.lang#Class<?>"
                name: "clazz"
            }
        ]
        body: "TypeName.from(clazz)"
    }
])
