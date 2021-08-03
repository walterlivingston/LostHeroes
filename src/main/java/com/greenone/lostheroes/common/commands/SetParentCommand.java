package com.greenone.lostheroes.common.commands;

import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.init.Deities;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.Collections;

public class SetParentCommand{
    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> literalArgumentBuilder = Commands
                .literal("setparent").requires(cs -> cs.hasPermission(2));
        for(Deity parent : Deities.list.values()){
            literalArgumentBuilder.then(Commands.literal(parent.getFormattedName()).executes((cs) -> {
                return setParent(cs, Collections.singleton(cs.getSource().getPlayerOrException()), parent);
            }).then(Commands.argument("player", EntityArgument.players()).executes((cs) -> {
                return setParent(cs, EntityArgument.getPlayers(cs, "player"), parent);
            })));
        }
        return literalArgumentBuilder;
    }

    private static int setParent(CommandContext<CommandSourceStack> source, Collection<ServerPlayer> players, Deity parent) {
        for(ServerPlayer player : players){
            IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
            playerCap.getParent().removeAttributeModifiers(player, player.getAttributes(), 0);
            playerCap.setParent(parent);
            sendFeedback(source, player, parent);
        }
        return 0;
    }

    private static void sendFeedback(CommandContext<CommandSourceStack> source, ServerPlayer player, Deity parent) {
        source.getSource().sendSuccess(new TextComponent("Your godly parent is " + parent.getFormattedName()), true);
    }
}
