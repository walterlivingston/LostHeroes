package com.greenone.lostheroes.common.commands;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.Collections;

public class SetManaCommand{
    public static LiteralArgumentBuilder<CommandSourceStack> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> literalArgumentBuilder = Commands
                .literal("setmana").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("amount", FloatArgumentType.floatArg()).executes(cs -> {
                    return setMana(cs, Collections.singleton(cs.getSource().getPlayerOrException()), FloatArgumentType.getFloat(cs, "amount"));
                })
                .then(Commands.argument("player", EntityArgument.players()).executes(cs -> {
                    return setMana(cs, Collections.singleton(EntityArgument.getPlayer(cs, "player")), FloatArgumentType.getFloat(cs, "amount"));
                })));
        return literalArgumentBuilder;
    }

    private static int setMana(CommandContext<CommandSourceStack> source, Collection<ServerPlayer> players, float amount) {
        for(ServerPlayer player : players){
            IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
            if(amount >= playerCap.getMaxMana())
                playerCap.setMana(playerCap.getMaxMana());
            else
                playerCap.setMana(amount);
            sendFeedback(source, player, playerCap.getMana());
        }
        return 0;
    }

    private static void sendFeedback(CommandContext<CommandSourceStack> source, ServerPlayer player, float amount) {
        source.getSource().sendSuccess(new TextComponent("You have " + amount + " mana"), true);
    }
}
