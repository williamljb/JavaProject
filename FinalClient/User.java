
public class User {
	String id, name, password, image;
	User(String id, String name, String pass, String image)
	{
		this.id = id;
		this.name = name;
		this.password = pass;
		this.image = image;
	}
	User(){id = "a"; name = "NULL"; password = "NULL";}
}
