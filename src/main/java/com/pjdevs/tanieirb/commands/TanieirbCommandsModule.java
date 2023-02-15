package com.pjdevs.tanieirb.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import com.pjdevs.tanieirb.TanieirbModule;
import com.pjdevs.tanieirb.commands.todo.TodoCommand;

public class TanieirbCommandsModule extends TanieirbModule {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            for (TanieirbCommand command : allCommands()) {
                if (command.shouldRegister.apply(environment)) {
                    dispatcher.register(command.commandBuilder);
                }
            }
        });        
    }

    private static TanieirbCommand[] allCommands() {
        return new TanieirbCommand[] {
            new TodoCommand(),
        };
    }
}
