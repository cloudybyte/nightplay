package gq.not11.bot.commands.music;

import gq.not11.bot.core.command.Command;
import gq.not11.bot.core.command.CommandEvent;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.GuildUnavailableException;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.core.managers.AudioManager;

public class JoinCommand extends Command {

    public JoinCommand() {
        super("Let's the bot join your channel", new String[]{"join"}, ".join");
    }


    @Override
    public void run(CommandEvent event) {

        GuildMessageReceivedEvent raw = ((GuildMessageReceivedEvent) event.getRaw());
        Guild guild = raw.getGuild();
        Member member = raw.getMember();
        VoiceChannel vc = member.getVoiceState().getChannel();
        AudioManager audioManager = guild.getAudioManager();
        SentryClient sentry = SentryClientFactory.sentryClient();

        try {
            audioManager.openAudioConnection(vc);
            raw.getChannel().sendMessage("Successfully joined your Voice Channel!").queue();
        }
        catch (IllegalArgumentException e) {
            raw.getChannel().sendMessage("Sorry, but it seems like you aren't connected to a Voice Channel!").queue();
        }
        catch (UnsupportedOperationException | GuildUnavailableException e){
            sentry.sendException(e);

        }
        catch (InsufficientPermissionException e){
            sentry.sendException(e);
            raw.getChannel().sendMessage("Sorry, but I don't have the proper permission to perform this action or the user limit may be exceeded!").queue();
        }

    }
}
