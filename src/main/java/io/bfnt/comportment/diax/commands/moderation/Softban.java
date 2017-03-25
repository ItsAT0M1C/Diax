package io.bfnt.comportment.diax.commands.moderation;

import io.bfnt.comportment.diax.api.command.CommandDescription;
import io.bfnt.comportment.diax.api.command.ErrorType;
import io.bfnt.comportment.diax.api.command.ModerationCommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Comporment on 25/03/2017 at 09:23
 * Dev'ving like a sir since 1998. | https://github.com/Comportment
 */
@CommandDescription(guildOnly = true, minimumArgs = 1, permission = Permission.KICK_MEMBERS, name = "softban", emoji = "🔨", args = "@mention")
public class Softban extends ModerationCommand
{
    public void execute(Message trigger)
    {
        try
        {
            Member member = getMemberFromString(trigger.getRawContent().split(" ")[1], trigger.getGuild());
            trigger.getGuild().getController().ban(member, 7).queue(_void ->
                    trigger.getGuild().getController().unban(member.getUser()).queue(_void_ ->
                        trigger.getChannel().sendMessage(makeMessage("Softbanned!", getNiceName(member) + " has been softbanned.").build()).queue()));
        }
        catch (NullPointerException e)
        {
            makeError(trigger.getChannel(), ErrorType.USER_NOT_FOUND);
        }
    }
}