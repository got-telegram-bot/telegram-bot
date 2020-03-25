package so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.parsers;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//Телеграмм к форматированным сообщениям посылает лист MessageEntity, с информацией, как форматировать текст. Возвращаем сообщение в html формате.
public class IncomingMessageHtmlParser {

    public static String parseInHtml(Message message){
        String parsedMessage = message.getText();
        if (message.getEntities() != null){
            List<ParsedEntity> parsedEntities = message.getEntities().stream().map(e -> {
                ParsedEntity parsedEntity = new ParsedEntity(message.getText().substring(e.getOffset(), e.getOffset() + e.getLength()));
                parsedEntity.setOffset(e.getOffset());
                parsedEntity.setLength(e.getLength());
                parsedEntity.setTag(e.getType() == null ? null : Arrays.stream(HtmlTags.values()).filter(tag -> tag.getType().equals(e.getType())).findFirst().orElse(null));
                return parsedEntity;
            }).collect(Collectors.toList());

            Integer parserOffset = 0;
            StringBuffer stringBuffer = new StringBuffer(parsedMessage);
            for (ParsedEntity parsedEntity : parsedEntities){
                if (parsedEntity.getTag() != null){
                    stringBuffer.insert(parsedEntity.getOffset() + parserOffset, parsedEntity.getTag().getOpenTag());
                    parserOffset += parsedEntity.getTag().getOpenTag().length();
                    stringBuffer.insert(parsedEntity.getOffset() + parsedEntity.getLength() + parserOffset, parsedEntity.getTag().getCloseTag());
                    parserOffset += parsedEntity.getTag().getCloseTag().length();
                }
            }

            return stringBuffer.toString();
        }
        return parsedMessage;
    }

    private static class ParsedEntity{
        private String text;
        private Integer offset;
        private Integer length;
        private HtmlTags tag;

        public ParsedEntity(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Integer getOffset() {
            return offset;
        }

        public void setOffset(Integer offset) {
            this.offset = offset;
        }

        public Integer getLength() {
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        public HtmlTags getTag() {
            return tag;
        }

        public void setTag(HtmlTags tag) {
            this.tag = tag;
        }
    }
}
