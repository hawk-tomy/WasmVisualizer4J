package gui;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReceiveFile implements ActionListener, DropTargetListener {
    JFileChooser chooser;
    JPanel panel;
    final Frame f;

    ReceiveFile(Frame f) {
        this.f = f;

    }

    public Component init() {
        this.chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "WebAssembly binary", "wasm"
        );
        this.chooser.setFileFilter(filter);

        this.panel = new JPanel();
        JButton button = new JButton("open wasm.");
        button.addActionListener(this);
        this.panel.add(button);
        return this.panel;
    }

    public void actionPerformed(ActionEvent e) {
        int ret = this.chooser.showOpenDialog(this.panel);
        if (ret == JFileChooser.APPROVE_OPTION) {
            this.f.receiveFile(this.chooser.getSelectedFile());
        }
    }

    public void drop(DropTargetDropEvent e) {
        try {
            e.acceptDrop(DnDConstants.ACTION_LINK);
            @SuppressWarnings("unchecked")
            List<File> files = (List<File>) e.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
            this.f.receiveFile(files.getFirst());
        } catch (UnsupportedFlavorException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void dragEnter(DropTargetDragEvent e) {}

    public void dragOver(DropTargetDragEvent e) {}

    public void dropActionChanged(DropTargetDragEvent e) {}

    public void dragExit(DropTargetEvent dte) {}
}
