package so.siva.telegram.bot.got_t_bot.core;

public enum Decks {
    WESTEROS_1("Колода Вестероса 1", "w1"),
    WESTEROS_2("Колода Вестероса 1", "w2"),
    WESTEROS_3("Колода Вестероса 1", "w3"),
    WESTEROS_4("Колода Вестероса 1", "w3"),
    WILDLINGS("Колода Одичалых", "wild"),
    BANK("Колода Железного Банка", "bank");

    private String rusName;

    /**
     * Для консольных команд
     */
    private String domain;

    Decks(String rusName, String domain) {
        this.rusName = rusName;
        this.domain = domain;
    }

    public Houses getKey(){
        return this.getKey();
    }

    public String getRusName(){
        return this.rusName;
    }

    public String getDomain() {
        return this.domain;
    }
}
