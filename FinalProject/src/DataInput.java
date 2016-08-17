import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by yixing on 10/16/15.
 */
public class DataInput {
//    private double[][] trainingData;
//    private double[][] testingData;
    protected double[][] data;
//    protected String path = "spambase.data.txt";
    protected String path;
    //    private Matrix trainingMatrix;
//    private Matrix testingMatrix;
    public DataInput(String path) {

//        this.path = "spambase.data.txt";
        this.path = path;
//        this.trainingData = convertToArray(insertDataToArray(training));
//        this.testingData = convertToArray(insertDataToArray(testing));
        this.data = convertToArray(insertDataToArray(path));
    }



    protected ArrayList<double[]> insertDataToArray (String document) {
        ArrayList<double[]> newData = new ArrayList<double[]>();
        BufferedReader readFile = null;
        try {
            readFile = new BufferedReader(new FileReader(document));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            while (true) {
                String line = readFile.readLine();
                if (line == null) break;
                if (line.trim() == "") break;
                String[] oneLineStrings = line.trim().split("\\s+|,");
                double[] oneLineDoubles = new double[oneLineStrings.length];
                for (int i = 0; i < oneLineStrings.length; i++) {
                    oneLineDoubles[i] = Double.parseDouble(oneLineStrings[i]);
//                    System.out.println(oneLineDoubles[i]);
                }
                newData.add(oneLineDoubles);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            readFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newData;
    }
    private double[][] convertToArray (ArrayList<double[]> arrayList) {
        int columns = arrayList.get(0).length;
//        System.out.println(arrayList.size());
//        System.out.print(columns);
//        System.out.println("\n");
        double[][] matrix = new double[arrayList.size()][columns];
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = (arrayList.get(i))[j];
            }
        }
        return matrix;
    }

    public double[][] getData() {
        return data;
    }
}
