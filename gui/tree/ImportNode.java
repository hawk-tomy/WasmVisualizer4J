package gui.tree;

import core.AST.Component.ImportComponentBase;
import core.AST.Module;

import javax.swing.tree.DefaultMutableTreeNode;

public class ImportNode extends DefaultMutableTreeNode {
    final ImportComponentBase importC;
    final Module module;

    ImportNode(Module module, ImportComponentBase importC) {
        this.importC = importC;
        this.module = module;
        this.setUserObject(this);
    }

    public String toString() {
        return this.title();
    }

    String title() {
        return this.importC.title();
    }

    String content() {
        return this.importC.content(this.module);
    }
}
