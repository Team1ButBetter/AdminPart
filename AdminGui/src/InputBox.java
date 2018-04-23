import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class InputBox
	{
		public TextField box;
		public Text name;
		public int[] getCentrePos()
		{
			int[] size = getSize();
			int x = (int)((this.name.xProperty().get()) + box.getWidth() / 2);
			int y = (int)(this.name.yProperty().get());
			return new int[] {x, y};
		}
		public int[] getSize()
		{
			double width = this.box.getWidth() + this.name.getStrokeWidth() * this.name.toString().length();
			double height = this.box.getHeight();
			int[] sizeVector = {(int)width, (int)height};
			return sizeVector;
		}
		public void setCentrePosition(int[] position)
		{
			this.name.setX(position[0] - this.box.getWidth() / 2);
			this.name.setY(position[1]);
			this.box.setLayoutX(position[0] + this.box.getWidth() / 2);
			this.box.setLayoutY(position[1] + 10);
		}
		public void setSize(int[] sizeVect)
		{
			this.box.setPrefWidth((double) sizeVect[0]);
			this.box.setPrefHeight((double) sizeVect[1]);
		}
		
		//constructor overloads
		InputBox(String name, int[] position)
		{
			this.name = new Text(name);
			box = new TextField();
			setCentrePosition(position);
		}
		InputBox(String name)
		{
			this.name = new Text(name);
			box = new TextField();
		}
		InputBox(int[] position)
		{
			this.name = new Text();
			box = new TextField();
			setCentrePosition(position);
		}
		InputBox()
		{
			this.name = new Text();
			box = new TextField();
		}
	}