package edu.asu.stratego.game;

import java.awt.Point;
import java.io.Serializable;

public class Move implements Serializable {

	private static final long serialVersionUID = -8315478849105334331L;
	
	private Point start = new Point(-1, -1);
	private Point end   = new Point(-1, -1);
    
    private PieceColor moveColor = null;
    
    private Piece startPiece;
    private Piece endPiece;
    
    private boolean isAttack;
    private boolean attackWin;
    private boolean defendWin;
    
    public boolean isAttackMove() {
    	return isAttack;
    }
    
    public void setAttackMove(boolean bool) {
    	isAttack = bool;
    }

    public Point getStart() {
        return start;
    }
    
    public Point getEnd() {
        return end;
    }
    
    public void setStart(int rowStart, int colStart) {
    	start = new Point(rowStart, colStart);
    }
    
    public void setEnd(int rowEnd, int colEnd) {
    	end = new Point(rowEnd, colEnd);
    }

    public void setStart(Point startP) {
    	start = startP;
    }
    
    public void setEnd(Point endP) {
    	end = endP;
    }
    
	public PieceColor getMoveColor() {
		return moveColor;
	}

	public void setMoveColor(PieceColor moveColor) {
		this.moveColor = moveColor;
	}

	public Piece getStartPiece() {
		return startPiece;
	}

	public void setStartPiece(Piece startPiece) {
		this.startPiece = startPiece;
	}
	
	public Piece getEndPiece() {
		return endPiece;
	}

	public void setEndPiece(Piece endPiece) {
		this.endPiece = endPiece;
	}

	public boolean isAttackWin() {
		return attackWin;
	}

	public void setAttackWin(boolean attackWin) {
		this.attackWin = attackWin;
	}

	public boolean isDefendWin() {
		return defendWin;
	}

	public void setDefendWin(boolean defendWin) {
		this.defendWin = defendWin;
	}
}
