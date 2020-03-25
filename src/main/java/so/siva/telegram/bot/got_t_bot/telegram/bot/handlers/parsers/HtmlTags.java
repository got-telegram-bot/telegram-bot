package so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.parsers;

public enum HtmlTags {
    BOLD("bold", "<b>", "</b>"),
    ITALIC("italic", "<i>", "</i>"),
    STRIKETHROUGH("strikethrough", "<s>", "</s>"),
    PRE("pre", "<pre>", "</pre>"),
    CODE("code", "<code>", "</code>"),
    UNDERLINE("underline", "<u>", "</u>");


    private String type;
    private String openTag;
    private String closeTag;

    public String getType() {
        return type;
    }

    public String getOpenTag() {
        return openTag;
    }

    public String getCloseTag() {
        return closeTag;
    }

    public String getKey(){
        return this.name();
    }

    HtmlTags(String type, String openTag, String closeTag) {
        this.type = type;
        this.openTag = openTag;
        this.closeTag = closeTag;
    }
}
