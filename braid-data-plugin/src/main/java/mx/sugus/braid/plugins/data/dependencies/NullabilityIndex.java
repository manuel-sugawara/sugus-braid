package mx.sugus.braid.plugins.data.dependencies;

import software.amazon.smithy.model.knowledge.NullableIndex;
import software.amazon.smithy.model.shapes.MemberShape;

/**
 * Represents the logic to determine if a given member is nullable. Similar to {@link NullableIndex} but the check modes are
 * encapsulated.
 */
public interface NullabilityIndex {
    /**
     * Returns true if the given member is nullable.
     *
     * @param member The member to check.
     * @return true if the given member is nullable.
     */
    boolean isMemberNullable(MemberShape member);

    /**
     * Returns true if the given member is required. Equivalent to {@code !isMemberNullable(member)}.
     *
     * @param member The member to check.
     * @return true if the given member is reqired.
     */
    default boolean isMemberRequired(MemberShape member) {
        return !isMemberNullable(member);
    }
}
