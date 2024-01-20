package gui.tree;

import core.AST.Component.DataComponent;
import core.AST.Component.GlobalsComponent;
import core.AST.Component.ImportComponentBase;
import core.AST.Module;
import core.AST.Section.DataSection;
import core.AST.Section.FunctionSection;
import core.AST.Section.GlobalSection;
import core.AST.Section.ImportSection;
import core.AST.Section.MemorySection;
import core.AST.Section.TableSection;
import core.AST.Type.MemoryType;
import core.AST.Type.TableType;
import gui.Label;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Component;

public class Root extends DefaultMutableTreeNode implements TreeSelectionListener {
    final Module module;
    final JTree tree;
    final Label label;

    public Root(String filename, Module module, Label label) {
        super(filename);
        this.module = module;
        this.label = label;
        this.tree = new JTree(this);
        this.tree.addTreeSelectionListener(this);

        this.setupImportNode(new DefaultMutableTreeNode("Import"));
        this.setupFunctionNode(new DefaultMutableTreeNode("Function"));
        this.setupTableNode(new DefaultMutableTreeNode("Table"));
        this.setupMemoryNode(new DefaultMutableTreeNode("Memory"));
        this.setupGlobalNode(new DefaultMutableTreeNode("Global"));
        this.setupDataNode(new DefaultMutableTreeNode("Data"));
    }

    void setupImportNode(DefaultMutableTreeNode node) {
        this.add(node);
        for (ImportSection importS : this.module.imports) {
            for (ImportComponentBase importC : importS.imports) {
                node.add(new ImportNode(this.module, importC));
            }
        }
    }

    void setupFunctionNode(DefaultMutableTreeNode node) {
        this.add(node);
        int i = 0;
        for (FunctionSection func : this.module.functions) {
            for (Integer idx : func.type_indexes) {
                node.add(new FunctionNode(this.module, i, idx));
                i++;
            }
        }
    }

    void setupTableNode(DefaultMutableTreeNode node) {
        this.add(node);
        int i = 0;
        for (TableSection table : this.module.tables) {
            for (TableType tt : table.tts) {
                node.add(new TableNode(this.module, i, tt));
                i++;
            }
        }
    }

    void setupMemoryNode(DefaultMutableTreeNode node) {
        this.add(node);
        int i = 0;
        for (MemorySection memory : this.module.memories) {
            for (MemoryType mt : memory.mts) {
                node.add(new MemoryNode(this.module, i, mt));
                i++;
            }
        }
    }

    void setupGlobalNode(DefaultMutableTreeNode node) {
        this.add(node);
        int i = 0;
        for (GlobalSection globalS : this.module.globals) {
            for (GlobalsComponent globalC : globalS.global) {
                node.add(new GlobalNode(this.module, i, globalC));
                i++;
            }
        }
    }

    void setupDataNode(DefaultMutableTreeNode node) {
        this.add(node);
        for (DataSection dataS : this.module.data) {
            for (DataComponent dataC : dataS.data) {
                node.add(new DataNode(this.module, dataC));
            }
        }
    }

    public Component init() {
        this.tree.setRootVisible(true);
        this.tree.expandRow(0);
        return this.tree;
    }

    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.tree.getLastSelectedPathComponent();
        if (node == null) {return;}
        String s = switch (node.getUserObject()) {
            case ImportNode n -> n.content();
            case FunctionNode n -> n.content();
            case TableNode n -> n.content();
            case MemoryNode n -> n.content();
            case GlobalNode n -> n.content();
            case DataNode n -> n.content();
            default -> "";
        };
        //System.out.println(s);
        this.label.setText("<html><pre>" + s + "</pre></html>");
    }
}
