package com.pjdevs.tanieirb.commands;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import com.mojang.brigadier.Command;

import static net.minecraft.server.command.CommandManager.*;

public class TodoCommand extends TanieirbCommand {
    public TodoCommand() {
        this.shouldRegister = e -> true;
        this.commandBuilder = literal("todo").executes(context -> {
            ServerCommandSource source = context.getSource();
            source.sendFeedback(Text.literal(String.format("You %s invoked Tani'eirb todo", source.getPlayer().getName().getString())), false);

            return Command.SINGLE_SUCCESS;
        });
    }
}
