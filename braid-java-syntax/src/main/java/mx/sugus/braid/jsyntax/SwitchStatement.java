package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represents a <code>switch</code> statement.</p>
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

    public Expression expression() {
        return this.expression;
    }

    public List<CaseClause> cases() {
        return this.cases;
    }

    public DefaultCaseClause defaultCase() {
        return this.defaultCase;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

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
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
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
         * <p>Sets the value for <code>expression</code></p>
         */
        public Builder expression(Expression expression) {
            this.expression = expression;
            return this;
        }

        /**
         * <p>Sets the value for <code>cases</code></p>
         */
        public Builder cases(List<CaseClause> cases) {
            this.cases.clear();
            this.cases.asTransient().addAll(cases);
            return this;
        }

        /**
         * <p>Adds a single value for <code>cases</code></p>
         */
        public Builder addCase(CaseClause aCase) {
            this.cases.asTransient().add(aCase);
            return this;
        }

        /**
         * <p>Sets the value for <code>defaultCase</code></p>
         */
        public Builder defaultCase(DefaultCaseClause defaultCase) {
            this.defaultCase = defaultCase;
            return this;
        }

        public SwitchStatement build() {
            return new SwitchStatement(this);
        }
    }
}
