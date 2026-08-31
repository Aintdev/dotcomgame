public enum Color {
    RESET("\033[0m"),
    RED("\033[0;31m"),
    BLUE("\033[0;34m"),

    REDBG("\033[41m");


    private final String ansiSequence;

    Color(String ansiSequence) {
        this.ansiSequence = ansiSequence;
    }

    public String toString() {
        return ansiSequence;
    }
}
