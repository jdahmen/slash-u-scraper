package slashuscraper.analyze;

import java.util.concurrent.Callable;

import slashuscraper.object.User;

public class AnalyzeUser implements Callable<User> {

	private User user = null;
	
	public AnalyzeUser(User user) {
		this.user = user;
	}
	
	@Override
	public User call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
