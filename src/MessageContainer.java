import java.io.*;
import java.util.*;
abstract public class MessageContainer
{

	abstract public void setLineMessage(Object message);
	
	abstract public Object getLineMessage();

}

class StringMessage extends DataObject implements Serializable
{

	String message;

	public void setMessage(String message)
	{
		this.message = (String)message;
	}
	
	public String getMessage()
	{
			return message;	
	}

}

class UserMessage extends DataObject implements Serializable
{

	String message;

	public void setMessage(String message)
	{
		this.message = (String)message;
	}
	
	public String getMessage()
	{
			return message;	
	}

}

class LineMessage extends MessageContainer implements Serializable
{

	ArrayList<Line> message;

	public void setLineMessage(Object message)
	{
		this.message = (ArrayList)message;
	}
	
	public Object getLineMessage()
	{
			return message;	
	}
}

class Line implements Serializable
{
	int startx, starty, endx, endy;
		
	public Line()
		{
		}
	public Line(int sx, int sy, int ex, int ey)
		{
			setStartX(sx);
			setStartY(sy);
			setEndX(ex);
			setEndY(ey);
		}
	public void setStartX(int sx)
		{
			startx = sx;
		}
	public void setStartY(int sy)
		{
			starty = sy;
		}
	public void setEndX(int ex)
		{
			endx = ex;
		}
	public void setEndY(int ey)
		{
			endy = ey;
		}
	public int getStartX()
		{
			return startx;
		}
	public int getStartY()
		{
			return starty;
		}
	public int getEndX()
		{
			return endx;
		}
	public int getEndY()
		{
			return endy;
		}
}