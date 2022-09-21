package com.greenone.lostheroes.common.command;

import com.greenone.lostheroes.common.player.capability.IMana;
import com.greenone.lostheroes.common.player.capability.PlayerCapabilities;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import java.util.Collection;

public class SetMana {
    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands
                .literal("setmana").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("amount", FloatArgumentType.floatArg()).executes(cs ->
                                setMana(cs, FloatArgumentType.getFloat(cs, "amount")))
                .then(Commands.argument("player", EntityArgument.players()).executes((cs) ->
                            setMana(cs, EntityArgument.getPlayers(cs, "player"), FloatArgumentType.getFloat(cs, "amount")))));
    }

    private static int setMana(CommandContext<CommandSource> source, Collection<ServerPlayerEntity> players, float mana) {
        for(ServerPlayerEntity player : players){
            IMana manaCap = player.getCapability(PlayerCapabilities.MANA_CAPABILITY).orElse(null);
            manaCap.setMana(mana);
            manaCap.sync(player);
            sendFeedback(source, player, mana);
        }
        return 0;
    }

    private static int setMana(CommandContext<CommandSource> source, float mana) throws CommandSyntaxException {
        ServerPlayerEntity player = source.getSource().getPlayerOrException();
        IMana manaCap = player.getCapability(PlayerCapabilities.MANA_CAPABILITY).orElse(null);
        manaCap.setMana(mana);
        manaCap.sync(player);
        sendFeedback(source, mana);
        return 0;
    }

    private static void sendFeedback(CommandContext<CommandSource> source, float mana) {
        source.getSource().sendSuccess(new StringTextComponent("Your mana level is " + mana), true);
    }

    private static void sendFeedback(CommandContext<CommandSource> source, ServerPlayerEntity player, float mana) {
        source.getSource().sendSuccess(new StringTextComponent(player.getScoreboardName() + "'s mana level is " + mana), true);
        player.sendMessage(new StringTextComponent("Your mana level is " + mana), player.getUUID());
    }
}
