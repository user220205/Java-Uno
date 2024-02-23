package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Card;
import model.Player;

public class DaoImpl implements Dao {
    private Connection connection;

    @Override
    public void connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/java_base?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
        String user = "root";
        String password = "";
        connection = DriverManager.getConnection(url, user, password);
    }

    @Override
    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public int getLastCardId(int playerId) throws SQLException {
        int lastId = 0;
        String query = "SELECT MAX(id) FROM card WHERE id_player = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                lastId = rs.getInt(1);
            }
        }
        return lastId;
    }

    @Override
    public Card getLastCard() throws SQLException {
        Card lastCard = null;
        String query = "SELECT * FROM uno INNER JOIN card ON uno.id_card = card.id ORDER BY uno.id DESC LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                lastCard = new Card(rs.getInt("id"), rs.getString("number"), rs.getString("color"),
                        rs.getInt("id_player"));
            }
        }
        return lastCard;
    }

    @Override
    public Player getPlayer(String username, String password) throws SQLException {
        Player player = null;
        String query = "SELECT * FROM player WHERE user = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                player = new Player(rs.getInt("id"), rs.getString("user"), rs.getString("password"),
                        rs.getString("name"), rs.getInt("games"), rs.getInt("victories"));
            }
        }
        return player;
    }

    @Override
    public ArrayList<Card> getCards(int playerId) throws SQLException {
        ArrayList<Card> cards = new ArrayList<>();
        String query = "SELECT * FROM card LEFT JOIN uno ON card.id = uno.id_card WHERE id_player=? AND uno.id is null";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Card card = new Card(rs.getInt("id"), rs.getString("number"), rs.getString("color"),
                        rs.getInt("id_player"));
                cards.add(card);
            }
        }
        return cards;
    }

    @Override
    public Card getCard(int cardId) throws SQLException {
        Card card = null;
        String query = "SELECT * FROM card WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cardId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                card = new Card(rs.getInt("id"), rs.getString("number"), rs.getString("color"),
                        rs.getInt("id_player"));
            }
        }
        return card;
    }

    @Override
    public void saveGame(Card card) throws SQLException {
        String query = "INSERT INTO uno (id_card) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, card.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void saveCard(Card card) throws SQLException {
        String query = "INSERT INTO card (id_player, number, color) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, card.getPlayerId());
            stmt.setString(2, card.getNumber());
            stmt.setString(3, card.getColor());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                card.setId(rs.getInt(1));
            }
        }
    }

    @Override
    public void deleteCard(Card card) throws SQLException {
        String query = "DELETE FROM card WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, card.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void clearDeck(int playerId) throws SQLException {
        String query = "DELETE FROM card WHERE id_player = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, playerId);
            stmt.executeUpdate();
        }
    }

    @Override
    public void addVictories(int playerId) throws SQLException {
        String query = "UPDATE player SET victories = victories + 1 WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, playerId);
            stmt.executeUpdate();
        }
    }

    @Override
    public void addGames(int playerId) throws SQLException {
        String query = "UPDATE player SET games = games + 1 WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, playerId);
            stmt.executeUpdate();
        }
    }

	@Override
	public int getLastIdCard(int playerId) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
}


