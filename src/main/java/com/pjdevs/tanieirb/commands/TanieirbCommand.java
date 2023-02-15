package com.pjdevs.tanieirb.commands;

import java.util.function.Function;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.CommandManager.RegistrationEnvironment;


public class TanieirbCommand {
    public Function<RegistrationEnvironment, Boolean> shouldRegister;
    public LiteralArgumentBuilder<ServerCommandSource> commandBuilder;
}
