package com.skyandforest.reboot_shop.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CommandFramework implements CommandExecutor {

    public static boolean register(JavaPlugin plugin, CommandFramework command) {
        PluginCommand pluginCommand = plugin.getCommand(command.label);

        if (pluginCommand == null) {
            return false;
        }

        pluginCommand.setExecutor(command);
        return true;
    }

    private String label;

    public CommandFramework(String label) {
        this.label = label;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            execute(sender, label, args);
        } catch (CommandException ex) {
            if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
                sender.sendMessage(ChatColor.RED + ex.getMessage());
            }
        }
        return true;
    }

    public abstract void execute(CommandSender sender, String label, String[] args);

    public static class CommandException extends RuntimeException {

        private static final long serialVersionUID = 1L;
        public CommandException(String msg) {
            super(msg);
        }

    }

    public static class CommandValidate {

        public static void notNull(Object o, String msg) {
            if (o == null) {
                throw new CommandException(msg);
            }
        }

        public static void isTrue(boolean b, String msg) {
            if (!b) {
                throw new CommandException(msg);
            }
        }

        public static int getPositiveInteger(String input) {
            try {
                int i = Integer.parseInt(input);
                if (i < 0) {
                    throw new CommandException("The number must be 0 or positive.");
                }
                return i;
            } catch (NumberFormatException e) {
                throw new CommandException("Invalid number \"" + input + "\".");
            }
        }

        public static int getPositiveIntegerNotZero(String input) {
            try {
                int i = Integer.parseInt(input);
                if (i <= 0) {
                    throw new CommandException("The number must be positive.");
                }
                return i;
            } catch (NumberFormatException e) {
                throw new CommandException("Invalid number \"" + input + "\".");
            }
        }

        public static double getPositiveDouble(String input) {
            try {
                double d = Double.parseDouble(input);
                if (d < 0) {
                    throw new CommandException("The number must be 0 or positive.");
                }
                return d;
            } catch (NumberFormatException e) {
                throw new CommandException("Invalid number \"" + input + "\".");
            }
        }

        public static double getPositiveDoubleNotZero(String input) {
            try {
                double d = Integer.parseInt(input);
                if (d <= 0) {
                    throw new CommandException("The number must be positive.");
                }
                return d;
            } catch (NumberFormatException e) {
                throw new CommandException("Invalid number \"" + input + "\".");
            }
        }

        public static void minLength(Object[] array, int minLength, String msg) {
            if (array.length < minLength) {
                throw new CommandException(msg);
            }
        }
    }
}
