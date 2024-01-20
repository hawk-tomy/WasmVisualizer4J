package gui;

import core.AST.Module;
import core.Parser;
import core.util.ParseException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Frame extends JFrame {
    final LayoutManager layout;

    public Frame() {
        super();
        this.setTitle("WasmVisualizer4J");
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.layout = this.getLayout();
    }

    public void start() {
        this.startReceiveFile();
    }

    public void startReceiveFile() {this.startReceiveFile(null);}

    public void startReceiveFile(ParseException e) {
        ReceiveFile rf = new ReceiveFile(this);
        Container c = this.getContentPane();
        c.removeAll();

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 1));
        p.add(rf.init());
        p.add(this.errPanel(e));
        new DropTarget(p, rf);
        c.add(p);
        this.setVisible(true);
    }

    public JPanel errPanel(ParseException e) {
        JPanel p = new JPanel();
        if (e != null) {
            p.add(new JLabel("<html><pre>parse error:\n" + e + "</pre></html>"));
        }
        return p;
    }

    public void receiveFile(File f) {
        byte[] wasm;
        try {
            wasm = Files.readAllBytes(f.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Parser p = new Parser(wasm);
        p.parse().mapConsume(
            m -> this.startVisualize(f.getName(), m),
            this::startReceiveFile
        );
    }

    public void startVisualize(String filename, Module module) {
        VisualizePane vp = new VisualizePane(filename, module);
        Container c = this.getContentPane();
        c.setLayout(this.layout);
        c.removeAll();
        c.add(vp.init());
        this.setVisible(true);
    }
}
