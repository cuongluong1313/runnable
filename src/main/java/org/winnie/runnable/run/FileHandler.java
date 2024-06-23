package org.winnie.runnable.run;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.stream.Stream;

public class FileHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileHandler.class);

    public static void main(String[] args) throws IOException {
        String path = "/Users/cuongluongthien/file-handler";
        shallowShowFilesByFile(path);
        deepShowFilesByFilesWalk(path);
        loadWithInputStreamReader(path + "/file-handler-file-1");
        loadWithFileReader(path + "/file-handler-file-1");
        //
        Double num = 123123123.12312;
        System.out.println(checkDouble(num, 19, 9));
    }

    public static boolean checkDouble(Double value, Integer length, Integer decimalLength) {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(340);
        String[] strValueArr = df.format(value).split("\\.");
        return strValueArr.length == 2 && strValueArr[0].length() <= length && strValueArr[1].length() <= decimalLength;
    }

    public static Integer readDate(String date, String format) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        DateTime dt = fmt.parseDateTime(date);
        return (dt.year().get() * 100 + dt.monthOfYear().get()) * 100 + dt.dayOfMonth().get();
    }

    public static void shallowShowFilesByFile(String directory) {
        File file = new File(directory);
        showFiles(Objects.requireNonNull(file.listFiles()));
    }

    public static void showFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getAbsolutePath());
//        showFiles(file.listFiles()); // Calls same method again.
            } else {
                System.out.println("File: " + file.getName());
            }
        }
    }

    // Accept both directory and file
    public static void deepShowFilesByFilesWalk(String path) throws IOException {
        Stream<Path> walk = Files.walk(Paths.get(path));
        walk.forEach(p -> System.out.println(p.toAbsolutePath()));
    }

    // Can use for both Online and Local Path
    public static void loadWithInputStreamReader(String hybridPath) throws IOException {
        boolean isOnlinePath = false;
        URL url = null;

        try {
            url = new URL(hybridPath);
            isOnlinePath = true;
        } catch (MalformedURLException ex) {
            LOGGER.debug(ex.getMessage());
            LOGGER.debug(String.format("%s is not online path", hybridPath));
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(isOnlinePath ? url.openStream() : new FileInputStream(hybridPath)))) {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) LOGGER.info(inputLine);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public static void loadWithFileReader(String FilePath) {
        String str = null;
        StringBuilder strb = new StringBuilder();

        // the following line means the try block takes care of closing the resource
        try (BufferedReader br = new BufferedReader(new FileReader(FilePath))) {
            while ((str = br.readLine()) != null) {
                strb.append(str).append("\n");
            }
        } catch (FileNotFoundException f) {
            LOGGER.error(FilePath + " does not exist");
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info(strb.toString());
    }

    public static void writeWithFileWriter(String path) {
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(path))) {
            String result = "some" + "\n" + "thing";
            buffer.write(result);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public static void checkAndCreateDirectory(String directoryName) {
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static boolean checkDirectory(String path) {
        Path directory = Paths.get(path);
        if (!Files.isDirectory(directory)) {
            LOGGER.error("Path must be directory!!");
            return false;
        }
        return true;
    }

    public static void createDirectories(String directoriesPath) throws IOException {
        Files.createDirectories(Paths.get(directoriesPath));
    }

    public static void checkFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println(file.isDirectory());
            System.out.println(file.isFile());
            System.out.println(file.getName());
        }
    }

    public static Boolean checkFormatString(String value, Integer length) {
        return value != null && value.length() == length;
    }

    public static Boolean checkFormatInteger(Integer value, Integer length) {
        return value != null && value.toString().length() == length;
    }

}
