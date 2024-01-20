package gui.tree;

import core.AST.Module;
import core.AST.Type.MemoryType;

import javax.swing.tree.DefaultMutableTreeNode;

public class MemoryNode extends DefaultMutableTreeNode {
    final MemoryType mt;
    final int idx;

    MemoryNode(Module ignoredModule, int idx, MemoryType mt) {
        this.mt = mt;
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
        return this.mt.content();
    }
}
