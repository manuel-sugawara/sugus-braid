package mx.sugus.braid.plugins.data.producers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.AbstractMethodSyntax;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.ConstructorMethodSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import software.amazon.smithy.model.shapes.MemberShape;

public class CompositeDirectedClass implements DirectedClass {
    private final List<DirectedClass> generators;

    public CompositeDirectedClass(DirectedClass... generators) {
        this.generators = Arrays.asList(generators);
    }

    @Override
    public ClassName className(ShapeCodegenState state) {
        return generators.get(0).className(state);
    }

    @Override
    public ClassSyntax.Builder typeSpec(ShapeCodegenState state) {
        return generators.get(0).typeSpec(state);
    }

    @Override
    public List<FieldSyntax> fieldsFor(ShapeCodegenState state, MemberShape member) {
        var result = new ArrayList<FieldSyntax>();
        for (var generator : generators) {
            result.addAll(generator.fieldsFor(state, member));
        }
        return result;
    }

    @Override
    public List<FieldSyntax> extraFields(ShapeCodegenState state) {
        var result = new ArrayList<FieldSyntax>();
        for (var generator : generators) {
            result.addAll(generator.extraFields(state));
        }
        return result;
    }

    @Override
    public List<ConstructorMethodSyntax> constructors(ShapeCodegenState state) {
        var result = new ArrayList<ConstructorMethodSyntax>();
        for (var generator : generators) {
            result.addAll(generator.constructors(state));
        }
        return result;
    }

    @Override
    public List<MethodSyntax> methodsFor(ShapeCodegenState state, MemberShape member) {
        var result = new ArrayList<MethodSyntax>();
        for (var generator : generators) {
            result.addAll(generator.methodsFor(state, member));
        }
        return result;
    }

    @Override
    public List<AbstractMethodSyntax> abstractMethodsFor(ShapeCodegenState state, MemberShape member) {
        var result = new ArrayList<AbstractMethodSyntax>();
        for (var generator : generators) {
            result.addAll(generator.abstractMethodsFor(state, member));
        }
        return result;
    }

    @Override
    public List<MethodSyntax> extraMethods(ShapeCodegenState state) {
        var result = new ArrayList<MethodSyntax>();
        for (var generator : generators) {
            result.addAll(generator.extraMethods(state));
        }
        return result;
    }

    @Override
    public List<AbstractMethodSyntax> extraAbstractMethods(ShapeCodegenState state) {
        var result = new ArrayList<AbstractMethodSyntax>();
        for (var generator : generators) {
            result.addAll(generator.extraAbstractMethods(state));
        }
        return result;
    }

    @Override
    public List<DirectiveToTypeSyntax> innerTypes(ShapeCodegenState state) {
        var result = new ArrayList<DirectiveToTypeSyntax>();
        for (var generator : generators) {
            result.addAll(generator.innerTypes(state));
        }
        return result;
    }

}
