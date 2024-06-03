package mx.sugus.braid.jsyntax.writer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.CompilationUnit;
import mx.sugus.braid.jsyntax.EnumSyntax;
import mx.sugus.braid.jsyntax.FormatterBlock;
import mx.sugus.braid.jsyntax.FormatterNode;
import mx.sugus.braid.jsyntax.FormatterTypeName;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.jsyntax.SyntaxNode;
import mx.sugus.braid.jsyntax.SyntaxNodeWalkVisitor;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.jsyntax.TypeSyntax;

public final class ImportableNames {
    private CodegenImportContainer importContainer;

    public Map<String, ClassName> importableNames(CompilationUnit unit) {
        return importableNames(unit.packageName(), unit, Map.of());
    }

    public Map<String, ClassName> importableNames(CompilationUnit unit, Map<String, ClassName> implicitPackageNames) {
        return importableNames(unit.packageName(), unit, implicitPackageNames);
    }

    public Map<String, ClassName> importableNames(
        String containingPackage,
        SyntaxNode node,
        Map<String, ClassName> implicitPackageNames
    ) {
        Set<ClassName> imports = Collections.emptySet();
        if (node instanceof CompilationUnit cu) {
            imports = cu.imports();
        }
        importContainer = new CodegenImportContainer(containingPackage, implicitPackageNames, imports);
        node.accept(new CodegenPrepareImports(importContainer));
        return Collections.unmodifiableMap(importContainer.simpleNames());
    }

    public CodegenImportContainer importContainer() {
        return importContainer;
    }

    static final class CodegenPrepareImports extends SyntaxNodeWalkVisitor {
        private final Deque<TypeSyntax> types = new ArrayDeque<>();
        private final CodegenImportContainer importContainer;
        private String packageName;

        public CodegenPrepareImports(CodegenImportContainer importContainer) {
            this.importContainer = importContainer;
        }

        @Override
        public SyntaxNode visitCompilationUnit(CompilationUnit node) {
            this.packageName = node.packageName();
            super.visitCompilationUnit(node);
            this.packageName = null;
            return node;
        }

        @Override
        public SyntaxNode visitClassSyntax(ClassSyntax node) {
            this.types.push(node);
            super.visitClassSyntax(node);
            importSymbols();
            this.types.pop();
            return node;
        }

        @Override
        public SyntaxNode visitEnumSyntax(EnumSyntax node) {
            this.types.push(node);
            super.visitEnumSyntax(node);
            importSymbols();
            this.types.pop();
            return node;
        }

        @Override
        public SyntaxNode visitInterfaceSyntax(InterfaceSyntax node) {
            this.types.push(node);
            super.visitInterfaceSyntax(node);
            importSymbols();
            this.types.pop();
            return node;
        }

        @Override
        public SyntaxNode visitCodeBlock(CodeBlock node) {
            for (var part : node.parts()) {
                visitFormatterNode(part);
            }
            return node;
        }

        @Override
        public SyntaxNode visitClassName(ClassName node) {
            importContainer.tryImportSymbol(node);
            return node;
        }

        private void importSymbols() {
            var simpleName = new StringBuilder();
            var iterator = types.descendingIterator();
            var nameSegments = new ArrayList<String>();
            var namePart = iterator.next().name();
            simpleName.append(namePart);
            nameSegments.add(namePart);
            while (iterator.hasNext()) {
                namePart = iterator.next().name();
                nameSegments.add(namePart);
                simpleName.append(".");
                simpleName.append(namePart);
            }
            var qualifiedClassName = ClassName.from(packageName, simpleName.toString());
            for (var idx = 0; idx < nameSegments.size(); idx++) {
                var partial = String.join(".", nameSegments.subList(idx, nameSegments.size()));
                importContainer.importSymbol(partial, qualifiedClassName);
            }
        }

        private void visitFormatterNode(FormatterNode part) {
            if (part instanceof FormatterTypeName t) {
                var typeName = t.value();
                typeName.accept(this);
            } else if (part instanceof FormatterBlock b) {
                var block = b.value();
                block.accept(this);
            }
        }
    }

    public static class CodegenImportContainer {
        private static final Set<String> HIGHER_PRIORITY = Set.of("java.lang", "java.util");
        private final Map<String, ClassName> simpleNames = new HashMap<>();
        private final String containingPackage;

        CodegenImportContainer(
            String containingPackage,
            Map<String, ClassName> implicitPackageNames,
            Set<ClassName> givenImports
        ) {
            this.containingPackage = containingPackage;
            for (var className : givenImports) {
                simpleNames.put(className.name(), className);
            }
            simpleNames.putAll(implicitPackageNames);
        }

        public Map<String, ClassName> simpleNames() {
            return simpleNames;
        }

        public String containingPackage() {
            return containingPackage;
        }

        public boolean tryImportSymbol(ClassName className) {
            var candidate = normalize(className);
            var existing = simpleNames.get(candidate.name());
            if (existing != null) {
                if (!shouldReplace(existing, candidate)) {
                    return false;
                }
            }
            simpleNames.put(candidate.name(), candidate);
            return true;
        }

        public void importSymbol(TypeName type) {
            var className = normalize(ClassName.toClassName(type));
            simpleNames.put(className.name(), className);
        }

        public void importSymbol(String simpleName, TypeName type) {
            var className = normalize(ClassName.toClassName(type));
            simpleNames.put(simpleName, className);
        }

        private ClassName normalize(ClassName from) {
            // Note: this might not play well with inner types, say package.Class.Inner -> package.Inner
            // what about we have package.Inner and package.Class.Inner, then package.Inner needs
            // to be imported and package.Class.Inner can be called Inner inside Class.
            // Should be OK given that we will prefer avoiding imports in the same package
            if (from.packageName() == null) {
                return from.toBuilder().packageName(containingPackage).build();
            }
            return from;
        }

        private boolean shouldReplace(ClassName existing, ClassName candidate) {
            if (existing.equals(candidate)) {
                return false;
            }
            if (Objects.equals(existing.packageName(), containingPackage)) {
                return false;
            }
            if (Objects.equals(candidate.packageName(), containingPackage)) {
                return true;
            }
            if (HIGHER_PRIORITY.contains(existing.packageName())) {
                return false;
            }
            if (HIGHER_PRIORITY.contains(candidate.packageName())) {
                return true;
            }
            return false;
        }

    }
}
