package model;

public class Player {

	private int id;
	private String name;
	private int games;
	private int victories;
	private String user;
	private String password;
	
	public Player(String nombre) {
		this.name = nombre;
		this.games = 0;
		this.victories = 0;
	}

	public Player(int id, String user, String password,  String name, int games, int victories) {
		this.id = id;
		this.name = name;
		this.games = games;
		this.victories = victories;
		this.user = user;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGames() {
		return games;
	}

	public void setGames(int games) {
		this.games = games;
	}

	public int getVictories() {
		return victories;
	}

	public void setVictories(int victories) {
		this.victories = victories;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", games=" + games + ", victories=" + victories + "]";
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	
}
