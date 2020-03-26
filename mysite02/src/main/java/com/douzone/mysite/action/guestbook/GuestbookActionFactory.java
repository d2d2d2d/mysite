package com.douzone.mysite.action.guestbook;

import com.douzone.mysite.action.main.MainAction;
import com.douzone.web.action.Action;
import com.douzone.web.action.ActionFactory;

public class GuestbookActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		switch(actionName) {
		case "list" : return new ListAction();
		case "insert" : return new InsertAction();
		case "deleteform" : return new DeleteFormAction();
		case "delete" : return new DeleteAction();
		default : return new ListAction();
		}
	}

}
