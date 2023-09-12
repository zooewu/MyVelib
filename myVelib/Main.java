import top.Administrator;
import scenarios.*;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args)  throws FileNotFoundException, NumberFormatException, InterruptedException{
        // Administration CLI
        Administrator.main(args);

        // Simulation of n planning ride
        ScrambleBikeScenario.main(args);

    }
}
