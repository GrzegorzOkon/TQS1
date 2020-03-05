package okon;

import okon.config.ConfigurationParamsReader;
import okon.exception.AppException;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TQS1App {
    static final Queue<Job> jobs = new LinkedList<>();
    static final List<Message> messages = new ArrayList();

    public static void main(String[] args) {
        initializeQueue();
        startThreadPool(4);
        print();
    }

    static void initializeQueue() {
        List<Log> logs = ConfigurationParamsReader.readLogParams(new File("./config/params.xml"));
        createJobs(logs);
    }

    static void createJobs(List<Log> logs) {
        for (Log log : logs) {
            jobs.add(new Job(log.getSystem(), log.getDirectory(), log.getMatches(), log.getLines()));
        }
    }

    static void startThreadPool(int threadSum) {
        Thread[] threads = new Thread[threadSum];
        for (int i = 0; i < threadSum; i++) {
            threads[i] = new MessageProducerThread();
        }
        for (int i = 0; i < threadSum; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threadSum; i++) {
            try {
                threads[i].join();
            } catch (Exception e) {
                throw new AppException(e);
            }
        }
    }

    static void print() {
        printToConsole();
        printToFile();
    }

    static void printToConsole() {
        for (int i = 0, size = messages.size(); i < size; i++) {
            System.out.println("*** " + messages.get(i).getDescription() + " ***");
            for (int j = messages.get(i).getResult().size() - 1; j >= 0; j--) {
                System.out.println();
                System.out.print(messages.get(i).getResult().get(j));

            }
            if (i < size - 1) {
                System.out.println();
                System.out.println();
            }
        }
    }

    static void printToFile() {
        try (Writer out = new FileWriter(new java.io.File(TQS1App.getJarFileName() + ".txt"))) {
            for (int i = 0, size = messages.size(); i < size; i++) {
                out.write("*** " + messages.get(i).getDescription() + " ***");
                out.write(System.getProperty("line.separator"));
                for (int j = messages.get(i).getResult().size() - 1; j >= 0; j--) {
                    out.write(System.getProperty("line.separator"));
                    out.write(messages.get(i).getResult().get(j));
                }
                if (i < size - 1) {
                    out.write(System.getProperty("line.separator"));
                    out.write(System.getProperty("line.separator"));
                }
            }
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    static String getJarFileName() {
        String path = TQS1App.class.getResource(TQS1App.class.getSimpleName() + ".class").getFile();
        path = path.substring(0, path.lastIndexOf('!'));
        path = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf('.'));
        return path;
    }
}
