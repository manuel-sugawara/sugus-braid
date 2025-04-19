package mx.sugus.braid.test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.syntax#SyntaxModelPlugin")
public class SyntaxNodeRewriteVisitor implements SyntaxNodeVisitor<SyntaxNode> {

    @Override
    public StructureShape visitStructureShape(StructureShape node) {
        StructureShape.Builder builder = null;
        StructureSimple structureMember = node.structureMember();
        StructureSimple structureMemberNew = null;
        if (structureMember != null) {
            structureMemberNew = visitStructureSimple(structureMember);
        }
        if (!Objects.equals(structureMember, structureMemberNew)) {
            builder = node.toBuilder();
            builder.structureMember(structureMemberNew);
        }
        List<StructureSimple> listMember = node.listMember();
        List<StructureSimple> newListMember = null;
        for (int idx = 0; idx < listMember.size(); idx++) {
            StructureSimple value = listMember.get(idx);
            StructureSimple newValue = visitStructureSimple(value);
            if (newListMember == null && !value.equals(newValue)) {
                newListMember = new ArrayList<>(listMember.size());
                newListMember.addAll(listMember.subList(0, idx));
            }
            if (newListMember != null) {
                newListMember.add(newValue);
            }
        }
        if (newListMember != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.listMember(newListMember);
        }
        Set<StructureSimple> setMember = node.setMember();
        Set<StructureSimple> newSetMember = null;
        for (StructureSimple value : setMember) {
            StructureSimple newValue = visitStructureSimple(value);
            if (newSetMember == null && !value.equals(newValue)) {
                newSetMember = new LinkedHashSet<>(setMember.size());
                for (StructureSimple innerValue : setMember) {
                    if (innerValue == value) {
                        break;
                    }
                    newSetMember.add(innerValue);
                }
            }
            if (newSetMember != null) {
                newSetMember.add(newValue);
            }
        }
        if (newSetMember != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.setMember(newSetMember);
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