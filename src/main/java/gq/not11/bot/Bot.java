package gq.not11.bot;

import gq.not11.bot.commands.PingCommand;
import gq.not11.bot.commands.music.DisconnectCommand;
import gq.not11.bot.commands.music.JoinCommand;
import gq.not11.bot.commands.music.PlayCommand;
import gq.not11.bot.core.command.CommandHandler;
import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import net.dv8tion.jda.bot.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.bot.sharding.ShardManager;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import java.util.concurrent.TimeUnit;
import javax.security.auth.login.LoginException;
import io.sentry.context.Context;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.UserBuilder;






public class Bot {

    long startupTime = System.currentTimeMillis();
    private static SentryClient sentry;

    private ShardManager shardManager;
    private CommandHandler commandHandler;


    private Bot(String[] args) {
        Sentry.init(System.getenv("SENTRY_DSN"));

        sentry = SentryClientFactory.sentryClient();
        sentry.setServerName({"server_name_": "Vserver1"});

        Sentry.capture("I am a test!");

        String testnull = null;
        try {
            System.out.println(testnull);
        }
        catch (NullPointerException e) {
            sentry.sendException(e);
        }



        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder()
                .setToken(System.getenv("DISCORD_TOKEN"))
                .setStatus(OnlineStatus.ONLINE)
                .setGame(Game.listening(".help"));




        commandHandler = new CommandHandler(this);
        commandHandler.register(new PingCommand());
        commandHandler.register(new PlayCommand());
        commandHandler.register(new JoinCommand());
        commandHandler.register(new DisconnectCommand());
        builder.addEventListeners(commandHandler);




        try {
            shardManager = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }




    }

    public static void main(String[] args) {
        new Bot(args);
    }
}
