package mx.sugus.braid.test;

import java.util.Collections;
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
        boolean listMemberChanged = false;
        int listMemberSize = listMember.size();
        for (int idx = 0; idx < listMemberSize; idx++) {
            StructureSimple value = listMember.get(idx);
            StructureSimple newValue = visitStructureSimple(value);
            if (!listMemberChanged && value != newValue) {
                listMemberChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.listMember(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addListMember(listMember.get(innerIdx));
                }
            }
            if (listMemberChanged) {
                builder.addListMember(newValue);
            }
        }
        Set<StructureSimple> setMember = node.setMember();
        boolean setMemberChanged = false;
        for (StructureSimple value : setMember) {
            StructureSimple newValue = visitStructureSimple(value);
            if (!setMemberChanged && value != newValue) {
                setMemberChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.setMember(Collections.emptySet());
                for (StructureSimple innerValue : setMember) {
                    if (innerValue == value) {
                        break;
                    }
                    builder.addSetMember(innerValue);
                }
            }
            if (setMemberChanged) {
                builder.addSetMember(newValue);
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