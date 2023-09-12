import top.Administrator;
import scenarios.*;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args)  throws FileNotFoundException, NumberFormatException, InterruptedException{
        // Test simple scenarios
        Administrator.main(args);

        // Test scramble scenarios
        ScrambleBikeScenario.main(args);

        //
    }
}
