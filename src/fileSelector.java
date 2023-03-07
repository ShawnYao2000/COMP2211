import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class fileSelector extends JFrame {

  private JTextField pathField;
  private String filePath;

  public fileSelector() {
    super("File Selector");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create the path field
    pathField = new JTextField();
    pathField.setEditable(false);
    pathField.setColumns(20);

    // Create the button to select a file
    JButton selectButton = new JButton("Select File");
    selectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(fileSelector.this);
        if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          filePath = selectedFile.getAbsolutePath();
          pathField.setText(filePath);
        }
      }
    });

    // Create the button to process the selected file
    JButton processButton = new JButton("Process");
    processButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (filePath != null && !filePath.isEmpty()) {
          // Pass the file path to another class
          Switcher.main(filePath);
          // Display a pop-up notification
          JOptionPane.showMessageDialog(fileSelector.this, "File has been processed.");
        }
      }
    });

    JButton displayButton = new JButton("Display");
    displayButton.setBackground(Color.GREEN);
    displayButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFrame statsFrame = new JFrame("Statistics");
        statsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      }
    });

    // Add the components to the frame
    Container contentPane = getContentPane();
    contentPane.setLayout(new FlowLayout());
    contentPane.add(pathField);
    contentPane.add(selectButton);
    contentPane.add(processButton);
    contentPane.add(displayButton);

    // Set the size and visibility of the frame
    setSize(500, 100);
    setVisible(true);
  }

  public static void main(String[] args) {
    // Create an instance of the fileSelector class
    new fileSelector();
  }
}
