package mx.sugus.braid.jsyntax.block;

import java.util.ArrayDeque;
import java.util.Deque;
import mx.sugus.braid.jsyntax.AbstractControlFlow;
import mx.sugus.braid.jsyntax.CodeBlock;

public class AbstractControlFlowBuilder extends AbstractBlockBuilder<AbstractControlFlowBuilder, AbstractControlFlow> {

    private final AbstractControlFlow.Builder builder;
    private final Deque<NextControlFlowBuilder> nextControlFlowStack;

    public AbstractControlFlowBuilder(CodeBlock prefix) {
        this.builder = AbstractControlFlow.builder()
                                          .prefix(prefix);
        this.nextControlFlowStack = new ArrayDeque<>();
    }

    @Override
    public AbstractControlFlow build() {
        if (nextControlFlowStack.isEmpty()) {
            return builder.statement(toBlock())
                          .build();
        }
        var current = nextControlFlowStack.pop().build();
        while (!nextControlFlowStack.isEmpty()) {
            var next = nextControlFlowStack.pop();
            next.builder.next(current);
            current = next.build();
        }
        var res = builder.statement(toBlock())
                         .next(current)
                         .build();
        return res;
    }

    NextControlFlowBuilder addNext(CodeBlock prefix) {
        var result = new NextControlFlowBuilder(prefix);
        nextControlFlowStack.push(result);
        return result;
    }

    static class NextControlFlowBuilder extends AbstractBlockBuilder<NextControlFlowBuilder, AbstractControlFlow> {
        private final AbstractControlFlow.Builder builder;

        NextControlFlowBuilder(CodeBlock prefix) {
            this.builder = AbstractControlFlow.builder()
                                              .prefix(prefix);
        }

        @Override
        public AbstractControlFlow build() {
            return builder.statement(toBlock())
                          .build();
        }
    }
}
