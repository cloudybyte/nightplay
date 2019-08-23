package gq.not11.bot.core.command;

import com.sun.jdi.InvalidTypeException;
import net.dv8tion.jda.core.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageUpdateEvent;

import java.security.PublicKey;

import static gq.not11.bot.util.Colors.*;

public class CommandEvent {

    public static String[] getArgs;
    private CommandType type;
    private String prefix;
    private String command;
    private String content;
    private GenericGuildMessageEvent raw;
    private String[] args;

    public CommandEvent(GenericGuildMessageEvent event) throws InvalidTypeException {
        if (event instanceof GuildMessageReceivedEvent) {
            type = CommandType.NEW;
            raw = event;
 
            //TODO Clarify InvalidTypeExeption

            prefix = System.getenv("PREFIX");
            command = ((GuildMessageReceivedEvent) event).getMessage().getContentDisplay().substring(prefix.length()).split(" ")[0];

            content = ((GuildMessageReceivedEvent) event).getMessage().getContentDisplay().substring(prefix.length() + command.length());



            if (content.startsWith(" ")) {
                content = content.substring(1);
            }
            String[] args = ((GuildMessageReceivedEvent) event).getMessage().getContentRaw().split(" ");
        } else if (event instanceof GuildMessageUpdateEvent) {
            type = CommandType.EDIT;
            raw = event;
            //TODO IMPLEMENT READING OF EDITED MESSAGES
        } else {
            throw new InvalidTypeException("CommandEvent cannot be initialized with that GuildMessageEvent!");
        }
    }

    public CommandType getType() {
        return type;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getCommand() {
        return command;
    }

    public String getContent() {
        return content;
    }

    public String[] getArgs() { return args; }

    public GenericGuildMessageEvent getRaw() {
        return raw;
    }


}
