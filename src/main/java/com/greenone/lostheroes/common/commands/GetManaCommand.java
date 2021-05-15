package com.greenone.lostheroes.common.commands;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import java.util.Collection;
import java.util.Collections;

public class GetManaCommand{
    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> literalArgumentBuilder = Commands
                .literal("getmana").executes(cs -> {
                    return getMana(cs, Collections.singleton(cs.getSource().getPlayerOrException()));
                })
                .then(Commands.argument("player", EntityArgument.players()).requires(cs -> cs.hasPermission(2)).executes(cs -> {
                    return getMana(cs, Collections.singleton(EntityArgument.getPlayer(cs, "player")));
                }));
        return literalArgumentBuilder;
    }

    private static int getMana(CommandContext<CommandSource> source, Collection<ServerPlayerEntity> players) {
        for(ServerPlayerEntity player : players){
            IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
            sendFeedback(source, player, playerCap.getMana());
        }
        return 0;
    }

    private static void sendFeedback(CommandContext<CommandSource> source, ServerPlayerEntity player, float amount) {
        source.getSource().sendSuccess(new StringTextComponent("You have " + amount + " mana"), true);
    }
}
