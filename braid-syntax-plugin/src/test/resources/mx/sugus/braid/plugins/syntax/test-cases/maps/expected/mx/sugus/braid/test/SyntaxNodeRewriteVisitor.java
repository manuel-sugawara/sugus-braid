package mx.sugus.braid.test;

import java.util.LinkedHashMap;
import java.util.Map;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.syntax#SyntaxModelPlugin")
public class SyntaxNodeRewriteVisitor implements SyntaxNodeVisitor<SyntaxNode> {

    @Override
    public StructureShape visitStructureShape(StructureShape node) {
        StructureShape.Builder builder = null;
        Map<String, StructureSimple> members = node.members();
        Map<String, StructureSimple> newMembers = null;
        for (Map.Entry<String, StructureSimple> kvp : members.entrySet()) {
            StructureSimple value = kvp.getValue();
            StructureSimple newValue = visitStructureSimple(value);
            if (newMembers == null && !value.equals(newValue)) {
                newMembers = new LinkedHashMap<>(members.size());
                for (Map.Entry<String, StructureSimple> innerKvp : members.entrySet()) {
                    if (innerKvp.getValue() == value) {
                        break;
                    }
                    newMembers.put(innerKvp.getKey(), innerKvp.getValue());
                }
            }
            if (newMembers != null) {
                newMembers.put(kvp.getKey(), newValue);
            }
        }
        if (newMembers != null) {
            builder = node.toBuilder();
            builder.members(newMembers);
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