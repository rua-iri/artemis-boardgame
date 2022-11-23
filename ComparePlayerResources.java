package artemis.game;

import java.util.Comparator;

public class ComparePlayerResources implements Comparator<Player>{

	@Override
	public int compare(Player p1, Player p2) {
		// TODO Auto-generated method stub
		return (int) (p1.getResources() - p2.getResources());
	}

}
