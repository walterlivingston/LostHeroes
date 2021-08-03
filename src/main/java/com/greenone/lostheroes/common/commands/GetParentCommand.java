package com.greenone.lostheroes.common.commands;

import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.Collections;

public class GetParentCommand{
    public static LiteralArgumentBuilder<CommandSourceStack> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> literalArgumentBuilder = Commands
                .literal("getparent").executes(cs -> {
                    return getParent(cs, Collections.singleton(cs.getSource().getPlayerOrException()));
                })
                .then(Commands.argument("player", EntityArgument.players()).requires(cs -> cs.hasPermission(2)).executes(cs -> {
                    return getParent(cs, Collections.singleton(EntityArgument.getPlayer(cs, "player")));
                }));
        return literalArgumentBuilder;
    }

    private static int getParent(CommandContext<CommandSourceStack> source, Collection<ServerPlayer> players) {
        for(ServerPlayer player : players){
            IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
            sendFeedback(source, player, playerCap.getParent());
        }
        return 0;
    }

    private static void sendFeedback(CommandContext<CommandSourceStack> source, ServerPlayer player, Deity parent) {
        source.getSource().sendSuccess(new TextComponent("Your godly parent is " + parent.getFormattedName()), true);
    }
}
