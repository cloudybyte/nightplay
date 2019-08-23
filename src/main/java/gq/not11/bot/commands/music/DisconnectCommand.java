package gq.not11.bot.commands.music;

import gq.not11.bot.core.command.Command;
import gq.not11.bot.core.command.CommandEvent;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

public class DisconnectCommand extends Command {

    public DisconnectCommand() {
        super("Let's the bot disconnect from the voice channel", new String[]{"dc"}, "");
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
            audioManager.closeAudioConnection();
            raw.getChannel().sendMessage("Disconnected!").queue();
        }
        catch (IllegalArgumentException e){
            sentry.sendException(e);
        }
        };
    }

