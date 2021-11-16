package com.greenone.lostheroes.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class LHCommands {
    public static void register(CommandDispatcher<CommandSource> dispatcher){
        dispatcher.register(Commands.literal("lh")
            .then(SetManaCommand.register(dispatcher))
            .then(GetManaCommand.register(dispatcher))
            .then(GetLevelCommand.register(dispatcher))
            .then(SetParentCommand.register(dispatcher))
            .then(GetParentCommand.register(dispatcher))
        );
    }
}
