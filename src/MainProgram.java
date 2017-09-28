public class MainProgram {
    Computation computation;
    IOHandler handler = new IOHandler();

    MainProgram() {
    }

    void startComputing() {
        // the indicate the result from which computation it is
        int numberOfResults = 1;
        boolean negative = false;
        //execute until the text file is empty
        while (handler.isLinesEmpty()) {
            int radix = handler.getRadix();
            if (radix == 0) {
                break;
            }
            String operation = handler.getOperation();
            String firstNumber = handler.getFirstNumber();
            String secondNumber = handler.getSecondNumber();
            computation = new Computation(radix, operation, firstNumber, secondNumber);
            String result = computation.compute();
            handler.trimmer();
            if (operation.equals("[multiply]") || operation.equals("[karatsuba]")) {
                handler.outputSteps(computation.singleSteps);
            }
            if (result.substring(0, 2).contains("--")) {
                result = result.substring(2, result.length());
            }
            if (result.substring(0, 1).equals("-")) {
                negative = true;
                result = result.substring(1, result.length());
            }
            while (result.substring(0, 1).equals("0")) {
                result = result.substring(1, result.length());
            }
            if (negative) {
                result = "-" + result;
                negative = false;
            }
            handler.output("Computation number " + numberOfResults + " [result] " + result);
            numberOfResults++;
        }


    }

    public static void main(String[] args) {
        MainProgram program = new MainProgram();
        program.startComputing();
    }

}
