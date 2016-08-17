/**
 * Created by yixing on 8/15/16.
 */
public class Main {

    private static double[][] trainFunctionResult;
    private static double[][] testFunctionResult;
    private static double[] trainOrigLabel;
    private static double[] testOrigLabel;
    private static double[][] ecocFunction;

    public static void main(String[] args) {
//        ECOC ecoc = new ECOC();
//        ecoc.runECOC();
        readModel();
        System.out.println("The train set is" + trainOrigLabel.length);
        System.out.println("The test set is" + testOrigLabel.length);
        System.out.println("The train set is" + trainFunctionResult.length);
        System.out.println("The test set is" + testFunctionResult.length);
        double trainAcc = predict(trainFunctionResult, trainOrigLabel);
        double testAcc = predict(testFunctionResult, testOrigLabel);
        System.out.println("The train accurancy is: " + trainAcc);
        System.out.println("The test accurancy is: " + testAcc);
    }

    public static void readModel() {
        DataInput train = new DataInput("trainFunction.txt");
        DataInput test = new DataInput("testFunction.txt");
        DataInput ecoc = new DataInput("ecocFunction.txt");
        DataInput trainLabel = new DataInput("trainLabel.txt");
        DataInput testLabel = new DataInput("testLable.txt");
        trainFunctionResult = train.getData();
        testFunctionResult = test.getData();
        ecocFunction = ecoc.getData();
        trainOrigLabel = transfer2Dto1D(trainLabel.getData());
        testOrigLabel = transfer2Dto1D(testLabel.getData());

    }

    public static double[] transfer2Dto1D(double[][] matrix) {
        double[] temp = new double[matrix[0].length];
        for(int i = 0; i < matrix[0].length; i++) {
            temp[i] = matrix[0][i];
        }
        return temp;
    }


    private static double predict(double[][] functionResult, double[] origLabel) {
        double acc = 0.00000;
        for(int i = 0; i < functionResult.length; i++) {
            double temp = getDistant(functionResult[i]);
//            System.out.println("The predict label is " + temp + " The true label is " + origLabel[i]);
            if (temp == origLabel[i]) {
                acc++;
            }
        }
        return acc / functionResult.length;
    }

    private static double getDistant(double[] list) {
//        printArray(list);
//        System.out.println(list.length);
        double minDistance = list.length+1;
        double classIndex = 0.0;
        for(int i = 0; i < ecocFunction.length; i++) {
            if(list.equals(ecocFunction[i])) {
                return (double)i;
            } else {
                double distance = 0.0;
                for(int j = 0; j < list.length; j++) {
                    if(ecocFunction[i][j] != list[j]) {
                        distance++;
                    }
                }
                if(minDistance > distance) {
                    classIndex = i;
                    minDistance = distance;
                }
            }
        }
        return classIndex;
    }
}
