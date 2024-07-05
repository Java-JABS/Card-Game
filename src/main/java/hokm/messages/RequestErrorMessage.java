package hokm.messages;

public enum RequestErrorMessage {
    CREATE_ROOM("Couldn't create room!\nTry again."),
    NOT_SIGNED_UP("You are not signed up!"),
    NOT_IN_ROOM("Player is not in a room!"),
    IN_ROOM("Player is already in a room!"),
    ROOM_NOT_EXISTS("Room doesn't exists!"),
    NOT_ROOM_OWNER("You are not room owner!"),
    NOT_IN_GAME("Player is not in a game!"),
    IN_GAME("Player is already in a game!"),
    ROOM_NOT_FULL("Room has not reached its capacity!"),
    ROOM_FULL("Room is full!"),
    NOT_PUT_STATE("Can not put card right now!"),
    NOT_HAVE_CARD("You don't Have this Card!"),
    NOT_YOUR_TURN("It's not your turn!"),
    NOT_GROUND_SUIT("Select Ground Card!"),
    NOT_HOKM_STATE("Can not hokm right now!"),
    NOT_RULER("You are not the ruler!"),

    ;
    final String message;

    RequestErrorMessage(String message) {
        this.message = message;
    }
}
