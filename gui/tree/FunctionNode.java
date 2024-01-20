package gui.tree;

import core.AST.Component.CodeComponent;
import core.AST.Module;
import core.AST.Type.FunctionType;

import javax.swing.tree.DefaultMutableTreeNode;

public class FunctionNode extends DefaultMutableTreeNode {
    int idx;
    FunctionType func;
    CodeComponent code;

    FunctionNode(Module module, int idx, Integer typeIdx) {
        try {
            this.func = module.getFuncType(typeIdx).unwrap();
            this.code = module.getCodeType(idx).unwrap();
        } catch (RuntimeException e) {
            throw new Error("Unknown Function Type.");
        }
        this.idx = idx;
        this.setUserObject(this);
    }

    public String toString() {
        return this.title();
    }

    String title() {
        return "id: " + this.idx + ", " + this.func.content();
    }

    String content() {
        return "id: " + this.idx + "\n" + this.func.content() + "\n" + this.code.content();
    }
}
