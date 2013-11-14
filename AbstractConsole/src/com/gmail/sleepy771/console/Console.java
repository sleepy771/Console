package com.gmail.sleepy771.console;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console{
    private Map<String, Command> cmdMap = new HashMap<String, Command>();
    private LinkedList<String> keyStack = new LinkedList<String>();
    private LinkedList<String> argumentStack = new LinkedList<String>();
    private static final Pattern WORD_PATTERN = Pattern.compile("(\\w+|\\\"[^\"]*\\\")");
    
    public String executeLine(String line) throws SyntaxException {
	splitInput(line);
	while(!keyStack.isEmpty()) {
	    handleWord(keyStack.removeLast());
	}
	return argumentStack.removeLast();
    }
    
    private void splitInput(String line) {
	Matcher lineMatcher = WORD_PATTERN.matcher(line);
	while(lineMatcher.find()) {
	    String match = lineMatcher.group();
	    if(match.charAt(0)=='\"' && match.charAt(match.length()-1)=='\"') {
		match = match.substring(1, match.length()-1);
	    }
	    keyStack.addLast(match);
	}
    }
    
    public boolean isCommand(String word) {
	return cmdMap.containsKey(word);
    }
    
    private void handleWord(String word) throws SyntaxException{
	if(cmdMap.containsKey(word)) {
	    Command cmd = cmdMap.get(word);
	    cmd.addArguments(argumentStack);
	    String result = cmd.execute();
	    argumentStack.addLast(result);
	} else {
	    argumentStack.addLast(word);
	}
    }

    public void clearCommands() {
	cmdMap.clear();
    }

    public boolean containsCommand(String cmd) {
	return cmdMap.containsKey(cmd);
    }

    public Command get(String cmd) {
	return cmdMap.get(cmd);
    }

    public boolean isEmpty() {
	return cmdMap.isEmpty();
    }

    public Set<String> commandSet() {
	return cmdMap.keySet();
    }

    public Command put(String cmdName, Command cmd) {
	return cmdMap.put(cmdName, cmd);
    }

    public Command remove(String cmd) {
	return cmdMap.remove(cmd);
    }

    public int size() {
	return cmdMap.size();
    }
}
