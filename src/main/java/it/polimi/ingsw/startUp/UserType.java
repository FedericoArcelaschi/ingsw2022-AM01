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
                case "server","sv","host","srv","sr"-> UserType.SERVER;
                case "t-client","tc","ct","cli"     -> UserType.CLI;
                case "g-client","gc","cg","gui"     -> UserType.GUI;
                default                             -> null;
        };
    }

}
