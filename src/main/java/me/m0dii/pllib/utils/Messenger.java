package me.m0dii.pllib.utils;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Messenger {
    private List<CommandSender> receivers = new ArrayList<>();
    private final List<String> messages = new ArrayList<>();

    public Messenger(CommandSender receiver) {
        this.receivers.add(receiver);
    }

    public Messenger(List<CommandSender> receivers) {
        this.receivers = receivers;
    }

    public Messenger add(List<String> message) {
        for (String msg : message) {
            this.messages.add(TextUtils.format(msg));
        }

        return this;
    }

    public Messenger addFirst(String message) {
        this.messages.add(0, TextUtils.format(message));

        return this;
    }

    public Messenger add(String message) {
        this.messages.add(TextUtils.format(message));

        return this;
    }

    public Messenger clear() {
        this.messages.clear();

        return this;
    }

    public Messenger setReceiver(CommandSender receiver) {
        this.receivers.clear();
        this.receivers.add(receiver);

        return this;
    }

    public Messenger setReceivers(List<CommandSender> receivers) {
        this.receivers = receivers;

        return this;
    }

    public Messenger addReceiver(CommandSender receiver) {
        this.receivers.add(receiver);

        return this;
    }

    public Messenger addReceivers(List<CommandSender> receivers) {
        this.receivers.addAll(receivers);

        return this;
    }

    public Messenger removeReceiver(CommandSender receiver) {
        this.receivers.remove(receiver);

        return this;
    }

    public Messenger removeReceivers(List<CommandSender> receivers) {
        this.receivers.removeAll(receivers);

        return this;
    }

    public Messenger setMessages(List<String> messages) {
        this.messages.clear();
        this.messages.addAll(messages);

        return this;
    }

    public Messenger send() {
        for (CommandSender receiver : this.receivers) {
            for (String msg : this.messages) {
                receiver.sendMessage(msg);
            }
        }

        return this;
    }
}
