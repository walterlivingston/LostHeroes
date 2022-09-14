package com.greenone.lostheroes.common.command;

import com.greenone.lostheroes.common.deity.Deities;
import com.greenone.lostheroes.common.deity.Deity;
import com.greenone.lostheroes.common.player.capability.IParent;
import com.greenone.lostheroes.common.player.capability.PlayerCapabilities;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import java.util.Collection;
import java.util.Collections;

public class SetParent{
    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> literalArgumentBuilder = Commands
                .literal("setparent").requires(cs -> cs.hasPermission(2));
        for(Deity parent : Deities.list){
            literalArgumentBuilder.then(Commands.literal(parent.getFormattedName()).executes((cs) ->
                    setParent(cs, parent))
                    .then(Commands.argument("player", EntityArgument.players()).executes((cs) ->
                            setParent(cs, EntityArgument.getPlayers(cs, "player"), parent))));
        }
        return literalArgumentBuilder;
    }

    private static int setParent(CommandContext<CommandSource> source, Collection<ServerPlayerEntity> players, Deity parent) {
        for(ServerPlayerEntity player : players){
            IParent parentCap = player.getCapability(PlayerCapabilities.PARENT_CAPABILITY).orElse(null);
            if(parentCap.getParent() != null){
                parentCap.getParent().removeAttributeModifiers(player, player.getAttributes());
                player.removeEffect(parentCap.getParent().getBlessing());
            }
            parentCap.setParent(parent);
            sendFeedback(source, player, parent);
        }
        return 0;
    }

    private static int setParent(CommandContext<CommandSource> source, Deity parent) throws CommandSyntaxException {
        ServerPlayerEntity player = source.getSource().getPlayerOrException();
        IParent parentCap = player.getCapability(PlayerCapabilities.PARENT_CAPABILITY).orElse(null);
        if(parentCap.getParent() != null){
            parentCap.getParent().removeAttributeModifiers(player, player.getAttributes());
            player.removeEffect(parentCap.getParent().getBlessing());
        }
        parentCap.setParent(parent);
        sendFeedback(source, parent);
        return 0;
    }

    private static void sendFeedback(CommandContext<CommandSource> source, Deity parent) {
        source.getSource().sendSuccess(new StringTextComponent("Your godly parent is " + parent.getFormattedName()), true);
    }

    private static void sendFeedback(CommandContext<CommandSource> source, ServerPlayerEntity player, Deity parent) {
        source.getSource().sendSuccess(new StringTextComponent(player.getScoreboardName() + "'s godly parent is " + parent.getFormattedName()), true);
        player.sendMessage(new StringTextComponent("Your godly parent is " + parent.getFormattedName()), player.getUUID());
    }
}
