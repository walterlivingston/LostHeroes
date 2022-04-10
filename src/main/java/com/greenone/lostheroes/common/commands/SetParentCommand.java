package com.greenone.lostheroes.common.commands;

import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.init.Deities;
import com.greenone.lostheroes.common.network.LHNetworkHandler;
import com.greenone.lostheroes.common.network.RiptidePacket;
import com.greenone.lostheroes.common.network.SetParentPacket;
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
import net.minecraftforge.fml.network.NetworkDirection;

import java.util.Collection;
import java.util.Collections;

public class SetParentCommand{
    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> literalArgumentBuilder = Commands
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

    private static int setParent(CommandContext<CommandSource> source, Collection<ServerPlayerEntity> players, Deity parent) {
        for(ServerPlayerEntity player : players){
            LHNetworkHandler.INSTANCE.sendTo(new SetParentPacket(parent), (player).connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
            sendFeedback(source, player, parent);
        }
        return 0;
    }

    private int setParent(CommandContext<CommandSource> source, Deity parent) throws CommandSyntaxException {
        ServerPlayerEntity player = source.getSource().getPlayerOrException();
        LHNetworkHandler.INSTANCE.sendTo(new SetParentPacket(parent), (player).connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
        sendFeedback(source, player, parent);
        return 0;
    }

    private static void sendFeedback(CommandContext<CommandSource> source, ServerPlayerEntity player, Deity parent) {
        source.getSource().sendSuccess(new StringTextComponent("Your godly parent is " + parent.getFormattedName()), true);
    }
}
