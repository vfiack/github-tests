package fr.vfiack.websocket;

import java.util.List;
import java.util.Optional;

public class CheckDuplicateAcrossProjects {
    public static int checkMe() {
        Optional<Integer> optional = List.of(1, 2, 3).stream().filter(i -> i > 2)
                .findFirst();

        if(optional.isEmpty()) {
            return 0;
        }

        // will sonar complain about accessing optional without is present call?
        return 2 * optional.get();
    }

    //this should pass the quality gate
    //single module update
    public static int cleaner(int i)  {
        return i*i;
    }

}
