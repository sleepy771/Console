package com.gmail.sleepy771.console;

import java.util.LinkedList;

public interface Command {
    public String execute() throws SyntaxException;
    
    public void addArguments(LinkedList<String> stack) throws SyntaxException;
}
