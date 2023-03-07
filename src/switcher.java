import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

class Switcher {

  public static void main(String filePath) {
    Switcher switcher = new Switcher();
    switcher.readFirstLine(filePath);
  }

  public void readFirstLine(String filePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String firstLine = br.readLine();
      if (firstLine == null) {
        JOptionPane.showMessageDialog(null, "File is empty");
        return;
      }
      String[] fields = firstLine.split(",");
      switch (fields.length) {
        case 3:
          if (fields[0].equals("Date") && fields[1].equals("ID") && fields[2].equals("Click Cost")) {
            csvClickLogReader.main(filePath);
          } else {
            JOptionPane.showMessageDialog(null, "Illegal file format");
          }
          break;
        case 7:
          if (fields[0].equals("Date") && fields[1].equals("ID") && fields[2].equals("Gender")
              && fields[3].equals("Age") && fields[4].equals("Income") && fields[5].equals("Context")
              && fields[6].equals("Impression Cost")) {
            csvImpressionLogReader.main(filePath);
          } else {
            JOptionPane.showMessageDialog(null, "Illegal file format");
          }
          break;
        case 5:
          if (fields[0].equals("Entry Date") && fields[1].equals("ID") && fields[2].equals("Exit Date")
              && fields[3].equals("Pages Viewed") && fields[4].equals("Conversion")) {
            csvServerLogReader.main(filePath);
          } else {
            JOptionPane.showMessageDialog(null, "Illegal file format");
          }
          break;
        default:
          JOptionPane.showMessageDialog(null, "Illegal file format");
          break;
      }
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Error reading file");
    }
  }
}
