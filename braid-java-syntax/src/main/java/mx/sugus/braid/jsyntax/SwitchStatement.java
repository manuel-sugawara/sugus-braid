package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Represents a {@code switch} statement.
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class SwitchStatement implements Statement {
    private final Expression expression;
    private final List<CaseClause> cases;
    private final DefaultCaseClause defaultCase;
    private int _hashCode = 0;

    private SwitchStatement(Builder builder) {
        this.expression = Objects.requireNonNull(builder.expression, "expression");
        this.cases = Objects.requireNonNull(builder.cases.asPersistent(), "cases");
        this.defaultCase = builder.defaultCase;
    }

    public StatementKind stmtKind() {
        return StatementKind.SWITCH_STATEMENT;
    }

    /**
     * 
     * @return The value of the {@code expression} member
     */
    public Expression expression() {
        return this.expression;
    }

    /**
     * 
     * @return The value of the {@code cases} member
     */
    public List<CaseClause> cases() {
        return this.cases;
    }

    /**
     * 
     * @return The value of the {@code defaultCase} member
     */
    public DefaultCaseClause defaultCase() {
        return this.defaultCase;
    }

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Accepts a {@link SyntaxNodeVisitor<VisitorR>} visitor
     * 
     * @param visitor The visitor to accept
     * @param <VisitorR> The result type from the visitor
     * @return The result from the visitor
     */
    @Override
    public <VisitorR> VisitorR accept(SyntaxNodeVisitor<VisitorR> visitor) {
        return visitor.visitSwitchStatement(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SwitchStatement that = (SwitchStatement) obj;
        return this.expression.equals(that.expression)
            && this.cases.equals(that.cases)
            && Objects.equals(this.defaultCase, that.defaultCase);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + this.stmtKind().hashCode();
            hashCode = 31 * hashCode + expression.hashCode();
            hashCode = 31 * hashCode + cases.hashCode();
            hashCode = 31 * hashCode + (defaultCase != null ? defaultCase.hashCode() : 0);
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "SwitchStatement{"
            + "stmtKind: " + stmtKind()
            + ", expression: " + expression
            + ", cases: " + cases
            + ", defaultCase: " + defaultCase + "}";
    }

    /**
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A class to build instances of SwitchStatement
     */
    public static final class Builder implements Statement.Builder {
        private Expression expression;
        private CollectionBuilderReference<List<CaseClause>> cases;
        private DefaultCaseClause defaultCase;

        Builder() {
            this.cases = CollectionBuilderReference.forList();
        }

        Builder(SwitchStatement data) {
            this.expression = data.expression;
            this.cases = CollectionBuilderReference.fromPersistentList(data.cases);
            this.defaultCase = data.defaultCase;
        }

        /**
         * Sets the value for {@code expression}.
         * 
         * @param expression The value to be set.
         * @return This instance for chain calling.
         */
        public Builder expression(Expression expression) {
            this.expression = Objects.requireNonNull(expression, "expression");
            return this;
        }

        /**
         * Sets the value for {@code cases}.
         * 
         * @param cases The value to be set.
         * @return This instance for chain calling.
         */
        public Builder cases(List<CaseClause> cases) {
            this.cases.clear();
            this.cases.asTransient().addAll(cases);
            return this;
        }

        /**
         * Adds a value to {@code cases}.
         * 
         * @param cases The value tp add
         * @return This instance for chain calling.
         */
        public Builder addCase(CaseClause aCase) {
            this.cases.asTransient().add(aCase);
            return this;
        }

        /**
         * Sets the value for {@code defaultCase}.
         * 
         * @param defaultCase The value to be set.
         * @return This instance for chain calling.
         */
        public Builder defaultCase(DefaultCaseClause defaultCase) {
            this.defaultCase = defaultCase;
            return this;
        }

        /**
         * Returns a new instance of {@link SwitchStatement}
         * 
         * @return A new instance of {@link SwitchStatement}
         */
        public SwitchStatement build() {
            return new SwitchStatement(this);
        }
    }
}
