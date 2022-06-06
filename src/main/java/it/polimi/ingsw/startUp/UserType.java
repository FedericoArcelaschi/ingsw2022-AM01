package it.polimi.ingsw.startUp;

/**
 * Used in the Main to get the right user type.
 */
public enum UserType {
    SERVER,
    CLI,
    GUI;

    static UserType getUserType(String userType){
        return
            switch (userType.
                    toLowerCase().
                    strip() //removes all whitespaces.
                    ) {
                case "server", "sv", "sr" -> UserType.SERVER;
                case "t-client", "tc", "ct" -> UserType.CLI;
                case "g-client", "gc", "cg" -> UserType.GUI;
                default -> null;
        };
    }

}
