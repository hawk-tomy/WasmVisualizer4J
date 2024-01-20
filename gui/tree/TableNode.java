package gui.tree;

import core.AST.Module;
import core.AST.Type.TableType;

import javax.swing.tree.DefaultMutableTreeNode;

public class TableNode extends DefaultMutableTreeNode {
    final TableType tt;
    final int idx;

    TableNode(Module ignoredModule, int idx, TableType tt) {
        this.idx = idx;
        this.tt = tt;
        this.setUserObject(this);
    }

    public String toString() {
        return this.title();
    }

    String title() {
        return "idx: " + this.idx;
    }

    String content() {
        return this.tt.content();
    }
}
