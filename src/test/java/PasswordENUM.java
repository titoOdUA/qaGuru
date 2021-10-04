public enum PasswordENUM {
    VALID_PASSWORD("cOrrectPass123!"),
    EMPTY_PASSWORD(""),
    SHORT_PASSWORD("123!O"),
    PASSWORD_WITHOUT_UPPERCASE_SYMBOL("notvolidpass123");

    String text;

    PasswordENUM(String pass) {
        this.text = pass;
    }
}
