package mx.sugus.braid.test;

import java.util.Map;
import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.syntax#SyntaxModelPlugin")
public class SyntaxNodeRewriteVisitor implements SyntaxNodeVisitor<SyntaxNode> {

    @Override
    public StructureShape visitStructureShape(StructureShape node) {
        StructureShape.Builder builder = null;
        Map<String, StructureSimple> members = node.members();
        boolean membersChanged = false;
        for (Map.Entry<String, StructureSimple> kvp : members.entrySet()) {
            StructureSimple value = kvp.getValue();
            StructureSimple newValue = visitStructureSimple(value);
            if (!membersChanged && value != newValue) {
                membersChanged = true;
                builder = node.toBuilder();
                for (Map.Entry<String, StructureSimple> innerKvp : members.entrySet()) {
                    if (innerKvp.getValue() == value) {
                        break;
                    }
                    builder.putMember(innerKvp.getKey(), innerKvp.getValue());
                }
            }
            if (membersChanged) {
                builder.putMember(kvp.getKey(), newValue);
            }
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public StructureShape2 visitStructureShape2(StructureShape2 node) {
        StructureShape2.Builder builder = null;
        StructureShape structureShape = node.structureShape();
        StructureShape structureShapeNew = null;
        if (structureShape != null) {
            structureShapeNew = visitStructureShape(structureShape);
        }
        if (!Objects.equals(structureShape, structureShapeNew)) {
            builder = node.toBuilder();
            builder.structureShape(structureShapeNew);
        }
        Map<String, StructureSimple> members = node.members();
        boolean membersChanged = false;
        for (Map.Entry<String, StructureSimple> kvp : members.entrySet()) {
            StructureSimple value = kvp.getValue();
            StructureSimple newValue = visitStructureSimple(value);
            if (!membersChanged && value != newValue) {
                membersChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                for (Map.Entry<String, StructureSimple> innerKvp : members.entrySet()) {
                    if (innerKvp.getValue() == value) {
                        break;
                    }
                    builder.putMember(innerKvp.getKey(), innerKvp.getValue());
                }
            }
            if (membersChanged) {
                builder.putMember(kvp.getKey(), newValue);
            }
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public StructureSimple visitStructureSimple(StructureSimple node) {
        return node;
    }
}