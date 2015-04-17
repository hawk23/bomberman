package game.model;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Explosion extends GameObject implements IUpdateable, IRenderable
{
	private int range = 1;
	private int timer;		// in mili secs
	
	public Explosion(int range)
	{
		this.range = range;
		this.timer = 2000;
	}
	
	private void calculateFlame()
	{
		int[][] flameRange = new int[3 + range - 1][3 + range - 1];
		
//		for(int direction=0; direction<blastDirection[0].length;direction++){
//			for(int r=1;r<=range;r++){
//      //Debugger.log("(" + getTileX() + blastDirection[0][direction] * r + "," + getTileY() + blastDirection[1][direction] * r + ")");
//  /*If we hit a collision blast won't spread any longer in this direction*/
//          if(oldBombermanMap.isBlocked(getTileX() + blastDirection[0][direction] * r, getTileY() + blastDirection[1][direction] * r)) {
//              oldBombermanMap.destroy(getTileX() + blastDirection[0][direction]*r,getTileY() +blastDirection[1][direction]*r);
//              break;
//          }else
//              oldBombermanMap.destroy(getTileX() + blastDirection[0][direction]*r,getTileY()+blastDirection[1][direction]*r);
	}
	
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		// TODO Auto-generated method stub
		
	}
}
