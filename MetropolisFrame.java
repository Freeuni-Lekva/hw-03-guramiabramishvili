import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MetropolisFrame extends JFrame{

    private JTextField metropolisField,continentField,populationField;
    private Box buttons,search_options;
    private JButton add,search;
    private JComboBox popDropMenu,matchDropMenu;
    private JScrollPane infoTable;
    private Metropolis model;

    public MetropolisFrame() {
        super("Metropolis Viewer");
        setLayout(new BorderLayout(4,4));

        //top
        JPanel top = new JPanel();
        JLabel metropolis = new JLabel("Metropolis:");
        JLabel continent = new JLabel("Continent:");
        JLabel population = new JLabel("Population:");

        metropolisField = new JTextField(15);
        continentField = new JTextField(15);
        populationField = new JTextField(15);

        top.add(metropolis);
        top.add(metropolisField);
        top.add(continent);
        top.add(continentField);
        top.add(population);
        top.add(populationField);

        add(top,BorderLayout.NORTH);

        //east
        search_options = Box.createVerticalBox();
        search_options.setBorder(new TitledBorder("Search Options"));
        buttons = Box.createVerticalBox();

        //Buttons
        add = new JButton("Add");
        search = new JButton("Search");
        addActionListeners();

        add.setAlignmentX( JComponent.LEFT_ALIGNMENT);
        search.setAlignmentX( JComponent.LEFT_ALIGNMENT);
        buttons.add(add);
        buttons.add(Box.createRigidArea(new Dimension(0,5)));
        buttons.add(search);
        buttons.add(Box.createRigidArea(new Dimension(0,5)));


        //Search options
        popDropMenu = new JComboBox(new String[]{"Population Larger Than", "Population Smaller Than"});;
        matchDropMenu = new JComboBox(new String[]{"Exact Match", "Partial Match"});

        popDropMenu.setAlignmentX( JComponent.LEFT_ALIGNMENT);
        matchDropMenu.setAlignmentX( JComponent.LEFT_ALIGNMENT);

        popDropMenu.setMaximumSize(new Dimension(popDropMenu.getMaximumSize().width,
                                                    popDropMenu.getPreferredSize().height));
        matchDropMenu.setMaximumSize(new Dimension(matchDropMenu.getMaximumSize().width,
                                                    matchDropMenu.getPreferredSize().height));
        search_options.add(popDropMenu);
        search_options.add(matchDropMenu);

        buttons.add(search_options);
        add(buttons,BorderLayout.EAST);

        //center
        model = new Metropolis();
        JTable table = new JTable(model);
        infoTable = new JScrollPane(table);
        infoTable.setPreferredSize(new Dimension(300,500));
        add(infoTable,BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void addActionListeners(){
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.add(metropolisField.getText(),continentField.getText(),populationField.getText());
            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean pop = false;
                boolean match = false;
                if(popDropMenu.getSelectedItem().equals("Population Larger Than")) pop = true;
                if(matchDropMenu.getSelectedItem().equals("Exact Match")) match = true;
                    model.search(metropolisField.getText(),
                            continentField.getText(), populationField.getText(), pop, match);

            }
        });
    }


        public static void main(String[] args) {
            // GUI Look And Feel
            // Do this incantation at the start of main() to tell Swing
            // to use the GUI LookAndFeel of the native platform. It's ok
            // to ignore the exception.
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) { }

            MetropolisFrame frame = new MetropolisFrame();
        }
}
