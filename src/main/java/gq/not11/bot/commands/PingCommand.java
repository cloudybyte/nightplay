package gq.not11.bot.commands;

import gq.not11.bot.core.command.Command;
import gq.not11.bot.core.command.CommandEvent;
import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.GuildUnavailableException;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;

public class PingCommand extends Command {

    public PingCommand() {
        super("Let's you calculate the bot's current ping", new String[]{"ping"}, "");
    }

    @Override
    public void run(CommandEvent event) {
        GuildMessageReceivedEvent raw = ((GuildMessageReceivedEvent) event.getRaw());
        SentryClient sentry = SentryClientFactory.sentryClient();

        long call = System.currentTimeMillis();
        try {
            raw.getChannel().sendMessage("Calculating ping ...").queue(message -> {
                long back = System.currentTimeMillis();
                message.editMessage(String.format("%sms", back - call)).queue();
            });
        }
        catch (UnsupportedOperationException | GuildUnavailableException | InsufficientPermissionException e){
            sentry.sendException(e);
        }
    }
}
