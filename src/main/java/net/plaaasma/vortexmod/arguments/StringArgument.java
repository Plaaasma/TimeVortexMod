package net.plaaasma.vortexmod.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;

public class StringArgument implements ArgumentType<String> {
    private static final Collection<String> EXAMPLES = Arrays.asList("home", "mines", "mars_base");

    private StringArgument() {
    }

    public static String getString(CommandContext<CommandSourceStack> pContext, String pName) {
        return pContext.getArgument(pName, String.class);
    }

    public static StringArgument stringArgument() {
        return new StringArgument();
    }

    @Override
    public String parse(StringReader pReader) throws CommandSyntaxException {
        return pReader.getString();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}