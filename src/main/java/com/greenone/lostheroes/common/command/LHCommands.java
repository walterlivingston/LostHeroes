package com.greenone.lostheroes.common.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class LHCommands {
    public static void register(CommandDispatcher<CommandSource> dispatcher){
        dispatcher.register(Commands.literal("lh")
                .then(SetParent.register(dispatcher))
        );
    }
}
