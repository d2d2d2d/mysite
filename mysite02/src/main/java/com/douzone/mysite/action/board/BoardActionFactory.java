package com.douzone.mysite.action.board;

import com.douzone.mysite.action.main.MainAction;
import com.douzone.web.action.Action;
import com.douzone.web.action.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		switch(actionName) {
		case "list" : return new ListAction();
		case "write" : return new WriteAction();
		case "writeform" : return new WriteFormAction();
		case "view" : return new ViewAction();
		case "delete" : return new DeleteAction();
		case "replyform" : return new ReplyFormAction();
		case "reply" : return new ReplyAction();
		case "modifyform" : return new ModifyFormAction();
		case "modify" : return new ModifyAction();
		case "search" : return new ListAction();
		default : return new ListAction();
		}
	}

}
