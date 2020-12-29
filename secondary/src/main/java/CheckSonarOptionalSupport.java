import java.util.List;
import java.util.Optional;

public class CheckSonarOptionalSupport {
    public static int checkMe() {
        Optional<Integer> optional = List.of(1, 2, 3).stream().filter(i -> i > 2)
                .findFirst();

        if(optional.isEmpty()) {
            return 4;
        }

        // will sonar complain about accessing optional without is present call?
        return 2 * optional.get();
    }

}
