package gui.tree;

import core.AST.Component.GlobalsComponent;
import core.AST.Module;

import javax.swing.tree.DefaultMutableTreeNode;

public class GlobalNode extends DefaultMutableTreeNode {
    final GlobalsComponent global;
    final int idx;

    GlobalNode(Module ignoredModule, int idx, GlobalsComponent global) {
        this.global = global;
        this.idx = idx;
        this.setUserObject(this);
    }

    public String toString() {
        return this.title();
    }

    String title() {
        return "idx: " + this.idx;
    }

    String content() {
        return this.title()
               + "\n"
               + this.global.gt.content()
               + "\nExpression\n"
               + this.global.expr.content();
    }
}
