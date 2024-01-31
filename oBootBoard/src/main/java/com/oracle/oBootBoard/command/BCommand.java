package com.oracle.oBootBoard.command;

import org.springframework.ui.Model;

// Service(Command라고도 불림) Interface
public interface BCommand {

	void execute(Model model);
	
}
