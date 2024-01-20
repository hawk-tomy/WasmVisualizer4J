package gui.tree;

import core.AST.Component.DataComponent;
import core.AST.Module;
import core.util.ToStringUtil;

import javax.swing.tree.DefaultMutableTreeNode;

public class DataNode extends DefaultMutableTreeNode {
    final DataComponent data;

    DataNode(Module ignoredModule, DataComponent data) {
        this.data = data;
        this.setUserObject(this);
    }

    public String toString() {
        return this.title();
    }

    String title() {
        return "memory idx: " + this.data.memIdx;
    }

    String content() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.title());
        sb.append("\nexpression:\n");
        sb.append(this.data.expr.content());
        sb.append("\nbytes (length: ");
        sb.append(this.data.bytes.size());
        sb.append(")\n");
        for (int i = 0; i < this.data.bytes.size(); i++) {
            sb.append(ToStringUtil.f.toHexDigits(this.data.bytes.get(i))); // get hex without "0x"
            if (i == 0) {continue;}
            if (i % 16 == 0) {
                sb.append("\n");
            } else if (i % 8 == 0) {
                sb.append(" ");
            }
        }
        if (this.data.bytes.size() % 16 != 0) {
            sb.append("\n");
        }
        return sb.toString();
    }
}
