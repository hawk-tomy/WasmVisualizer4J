package gui;

import core.AST.Module;
import gui.tree.Root;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import java.awt.Component;
import java.awt.Dimension;

public class VisualizePane {
    String filename;
    final Module module;
    final Root root;
    final Label label;

    public VisualizePane(String filename, Module module) {
        this.label = new Label();
        this.root = new Root(filename, module, this.label);
        this.module = module;
    }

    public Component init() {
        JSplitPane pane = new JSplitPane();
        pane.setDividerSize(5);

        JScrollPane sp = new JScrollPane(this.root.init());
        sp.setMinimumSize(new Dimension(400, 100));
        pane.setLeftComponent(sp);

        pane.setRightComponent(new JScrollPane(this.label));

        return pane;
    }
}
