import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
class Autosar implements Comparable<Autosar> {
    private String containerID;
    private String shortName;
    private String longName;

    public String getContainerID() {
        return containerID;
    }

    public void setContainerID(String containerID) {
        this.containerID = containerID;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public Autosar() {
    }

    @Override
    public String toString() {
        return "    <CONTAINER UUID=" + this.getContainerID() + ">\n"
                + "     <SHORT-NAME>Container" + this.getShortName() + "</SHORT-NAME>\n"
                + "      <LONG-NAME>" + this.getLongName() + "</LONG-NAME>\n"
                + "  </CONTAINER>\n";
    }


    @Override
    public int compareTo(Autosar o) {
        if (this.getShortName().charAt(0) > o.getShortName().charAt(0)) {
            return 1;
        } else if (this.getShortName().charAt(0) < o.getShortName().charAt(0)) {
            return -1;
        } else {
            return 0;
        }
    }
}


    class EmptyAutosarFileException extends Exception {
        public EmptyAutosarFileException(String m) {
            System.out.println(m);
        }
    }

    class NotVaildAutosarFileException extends Exception {
        public NotVaildAutosarFileException(String m) {
            System.out.println(m);
        }
    }

    public class Main {
        public static void main(String[] args) {
            try {
                String fileName = args[0];
                if (!fileName.endsWith(".arxml")) {
                    throw new NotVaildAutosarFileException("Invalid file extension");
                }
                File file = new File(fileName);
                if (file.length() == 0) {
                    throw new EmptyAutosarFileException("empty file!");
                }
                FileInputStream inputStream = new FileInputStream(file);
                int R;
                StringBuilder stringBuilder = new StringBuilder();
                while ((R = inputStream.read()) != -1) {
                    stringBuilder.append((char) R);
                }
                String data = stringBuilder.toString();
                Scanner scanner = new Scanner(data);
                ArrayList<Autosar> autosars = new ArrayList<>();
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.contains("<CONTAINER UUID")) {
                        String containerID = line.substring(line.indexOf("\""), line.indexOf(">"));
                        String s = scanner.nextLine();
                        String shortName = s.substring(s.indexOf("r") + 1, s.indexOf("</"));
                        String l = scanner.nextLine();
                        String longName = l.substring(s.indexOf(">") , l.indexOf("</"));
                        Autosar auto = new Autosar();
                        auto.setContainerID(containerID);
                        auto.setShortName(shortName);
                        auto.setLongName(longName);
                        autosars.add(auto);
                    }
                }
                Collections.sort(autosars);
                String outName = fileName.substring(0, fileName.indexOf(".")) + "_mod.arxml";
                FileOutputStream outputStream = new FileOutputStream(outName);
                outputStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes());
                outputStream.write("<AUTOSAR>\n".getBytes());
                for (int i = 0; i < autosars.size(); i++) {
                    outputStream.write(autosars.get(i).toString().getBytes());
                }
                outputStream.write("</AUTOSAR>".getBytes());

            } catch (NotVaildAutosarFileException e) {
                e = new NotVaildAutosarFileException("file not vali!d");
            } catch (FileNotFoundException e) {
                e = new FileNotFoundException("file not found!");
            } catch (IOException e) {
                e = new IOException("IO exception!");
            } catch (EmptyAutosarFileException e) {
                e = new EmptyAutosarFileException("Empty file!");
            }
        }
    }


