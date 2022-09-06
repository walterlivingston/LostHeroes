package com.greenone.lostheroes.common.commands;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import java.util.Collection;
import java.util.Collections;

public class SetManaCommand{
    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> literalArgumentBuilder = Commands
                .literal("setmana").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("amount", FloatArgumentType.floatArg()).executes(cs -> {
                    return setMana(cs, Collections.singleton(cs.getSource().getPlayerOrException()), FloatArgumentType.getFloat(cs, "amount"));
                })
                .then(Commands.argument("player", EntityArgument.players()).executes(cs -> {
                    return setMana(cs, Collections.singleton(EntityArgument.getPlayer(cs, "player")), FloatArgumentType.getFloat(cs, "amount"));
                })));
        return literalArgumentBuilder;
    }

    private static int setMana(CommandContext<CommandSource> source, Collection<ServerPlayerEntity> players, float amount) {
        for(ServerPlayerEntity player : players){
            IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
            if(amount >= playerCap.getMaxMana())
                playerCap.setMana(playerCap.getMaxMana());
            else
                playerCap.setMana(amount);
            sendFeedback(source, player, playerCap.getMana());
        }
        return 0;
    }

    private static void sendFeedback(CommandContext<CommandSource> source, ServerPlayerEntity player, float amount) {
        source.getSource().sendSuccess(new StringTextComponent("You have " + amount + " mana"), true);
    }
}
