package main.java.AdvancedGui.ScriptFactorySDN;

import org.parabot.environment.scripts.Category;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

import static main.java.VarsMethods.*;

/**
 * Script Factory SDN GUI, shows all scripts
 *
 * @author Everel, stolen by Before
 */
public class ScriptFactorySDNGui extends JFrame {
    private static final long serialVersionUID = 1L;
    private final int WIDTH;
    private final int HEIGHT;
    private HashMap<String, DefaultMutableTreeNode> categories;
    private HashMap<String, ScriptFactoryScript> format;
    private DefaultMutableTreeNode root;
    private DefaultTreeModel model;
    private Font fontCategory = new Font("Arial", Font.BOLD, 12);
    private Font fontScript = new Font("Arial", Font.PLAIN, 12);
    private JTree tree;
    private JEditorPane scriptInfo;

    public ScriptFactorySDNGui() {
        this.categories = new HashMap<>();
        this.format = new HashMap<>();
        this.root = new DefaultMutableTreeNode("Scripts");
        this.WIDTH = 640;
        this.HEIGHT = 256 + 128;
        this.model = new DefaultTreeModel(root);
        putScripts();
        createUI();
    }

    private void downloadScript(ScriptFactoryScript desc) {
        log("Downloading script " + desc.scriptName + "...");
        createScriptFile(desc);
        log("Downloading dependencies...");
        for (String i : desc.dependencies)
        {
            downloadScript(ScriptFactoryScript.getScriptByName(i));
        }
    }

    private void createScriptFile(ScriptFactoryScript desc) {
        String filePath = DEFAULT_DIR + FSEP + (desc.category.equals("Dependency") ? "dependencies" + FSEP : "") + desc.scriptName;
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new File(filePath));
            writer.write(desc.code);
            writer.close();
        } catch (FileNotFoundException ignored) {
        }

    }

    private void putScripts() {
        final ScriptFactoryScript[] descs = ScriptFactoryScript.getDescriptions();
        if (descs == null) {
            return;
        }
        for (final ScriptFactoryScript scriptDesc : descs) {
            if (categories.get(scriptDesc.category) == null) {
                DefaultMutableTreeNode cat = null;
                if (scriptDesc.category.equals("Dependency"))
                    cat = new DefaultMutableTreeNode("Dependencies");
                else
                    cat = new DefaultMutableTreeNode(Category.valueOf(scriptDesc.category.toUpperCase()));
                cat.add(new DefaultMutableTreeNode(scriptDesc.scriptName));
                root.add(cat);
                categories.put(scriptDesc.category, cat);
            } else {
                categories.get(scriptDesc.category).add(
                        new DefaultMutableTreeNode(scriptDesc.scriptName));
            }

            format.put(scriptDesc.scriptName, scriptDesc);
        }
    }

    private String getScriptName(String path) {
        return path.split(", ")[2].replaceAll("\\]", "");
    }

    private String getServerDesc(final String[] servers) {
        if (servers == null) {
            return "Unknown";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < servers.length; i++) {
            builder.append(servers[i]);
            if ((i + 1) < servers.length) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    private void createUI() {

        this.setTitle("Script Selector");
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        tree = new JTree();
        tree.setCellRenderer(new ScriptTreeCellRenderer());
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        tree.setModel(model);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                String path = e.getPath().toString();
                if (path.split(",").length == 3) {

                    // local scripts
                    ScriptFactoryScript def = format.get(getScriptName(e
                            .getPath().toString()));
                    if (def != null) {
                        StringBuilder html = new StringBuilder("<html>");
                        html.append("<h1><font color=\"black\">")
                                .append(def.scriptName)
                                .append("</font></h1><br/>");
                        html.append("<font color=\"black\"><b>Author: </b>")
                                .append(def.author).append("</font><br/>");
                        html.append("<font color=\"black\"><b>Servers: </b>")
                                .append(getServerDesc(def.servers))
                                .append("</font><br/>");
                        html.append("<font color=\"black\"><b>Version: </b>")
                                .append(def.version).append("</font><br/>");
                        html.append(
                                "<font color=\"black\"><b>Description: </b>")
                                .append(def.description).append("</font><br/>");
                        html.append("</html>");
                        scriptInfo.setText(new String(html));
                    }

                }
            }
        });

        scriptInfo = new JEditorPane();
        scriptInfo.setContentType("text/html");
        scriptInfo.setEditable(false);

        JScrollPane scrlScriptTree = new JScrollPane(tree);
        scrlScriptTree.setBounds(4, 4, WIDTH / 2 - 4 - 64, HEIGHT - 4 - 28);

        JScrollPane scrlScriptInfo = new JScrollPane(scriptInfo);
        scrlScriptInfo.setBounds(WIDTH / 2 + 4 - 64, 4, WIDTH / 2 - 8 + 64,
                HEIGHT - 4 - 28);

        JButton cmdStart = new JButton("Download");
        cmdStart.setBounds(WIDTH - 156 - 4, HEIGHT - 24 - 4, 156, 24);
        cmdStart.addActionListener(e -> {
            String s = getScriptName(tree.getSelectionPath().toString());
            downloadScript(format.get(s));
            JOptionPane.showMessageDialog(null, "Script downloaded successfully. You can now run it by clicking \"Load\" in the main menu.");
        });

        JButton cmdHome = new JButton("Open home");
        cmdHome.setBounds(WIDTH - (96 * 2 + 60) - 4 - 32, HEIGHT - 24 - 4, 96 + 32,
                24);
        cmdHome.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new File(DEFAULT_DIR));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        panel.add(scrlScriptTree);
        panel.add(scrlScriptInfo);
        panel.add(cmdStart);
        panel.add(cmdHome);

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(getOwner());

    }

    private class ScriptTreeCellRenderer implements TreeCellRenderer {
        private JLabel label;

        ScriptTreeCellRenderer() {
            label = new JLabel();
        }

        @Override
        public Component getTreeCellRendererComponent(JTree list, Object value,
                                                      boolean selected, boolean expanded, boolean leaf, int row,
                                                      boolean focused) {
            Object o = ((DefaultMutableTreeNode) value).getUserObject();
            BufferedImage icon = (o instanceof Category ? ((Category) o)
                    .getIcon() : Category.getIcon("script"));
            label.setIcon(icon != null ? new ImageIcon(icon) : null);
            label.setFont(o instanceof Category ? fontCategory : fontScript);
            label.setForeground(selected ? Color.DARK_GRAY : Color.BLACK);
            label.setText(String.valueOf(value));
            return label;
        }
    }
}
