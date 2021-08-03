package com.greenone.lostheroes.common.init;

import com.greenone.lostheroes.common.commands.GetManaCommand;
import com.greenone.lostheroes.common.commands.GetParentCommand;
import com.greenone.lostheroes.common.commands.SetManaCommand;
import com.greenone.lostheroes.common.commands.SetParentCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class LHCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("lh")
            .then(SetManaCommand.register(dispatcher))
            .then(GetManaCommand.register(dispatcher))
            .then(SetParentCommand.register(dispatcher))
            .then(GetParentCommand.register(dispatcher))
        );
    }
}
