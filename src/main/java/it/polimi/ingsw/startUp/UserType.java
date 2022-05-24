package it.polimi.ingsw.startUp;

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
                case "server" -> UserType.SERVER;
                case "t-client" -> UserType.CLI;
                case "g-client" -> UserType.GUI;
                default -> null;
        };
    }

}
