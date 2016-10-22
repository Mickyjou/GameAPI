package MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Stats {

	public static boolean playerExists(String uuid) {

		try {
			ResultSet rs = PlayerStats.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");

			if (rs.next()) {
				return rs.getString("UUID") != null;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void createPlayer(String uuid) {

		if (!(playerExists(uuid))) {
			PlayerStats.mysql.update("INSERT INTO Stats(UUID, KILLS, DEATHS, WINS, LOSES, GAMES) VALUES ('" + uuid
					+ "', '0', '0', '0', '0', '0');");
		}
	}

	// KILLS
	public static Integer getKills(String uuid) {
		Integer i = 0;

		if (playerExists(uuid)) {
			try {
				ResultSet rs = PlayerStats.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
				if ((!rs.next()) || (Integer.valueOf(rs.getInt("KILLS")) == null))
					;

				i = rs.getInt("KILLS");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			getKills(uuid);
		}
		return i;
	}

	public static void setKills(String uuid, Integer kills) {
		if (playerExists(uuid)) {
			PlayerStats.mysql.update("UPDATE Stats SET KILLS= '" + kills + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setKills(uuid, kills);
		}
	}

	public static void addKills(String uuid, Integer kills) {
		if (playerExists(uuid)) {
			setKills(uuid, Integer.valueOf(getKills(uuid).intValue() + kills.intValue()));
		} else {
			createPlayer(uuid);
			addKills(uuid, kills);
		}
	}

	public static void removeKills(String uuid, Integer kills) {
		if (playerExists(uuid)) {
			int i = Integer.valueOf(getKills(uuid).intValue() - kills.intValue());
			if (i > 0) {
				setKills(uuid, i);
			} else {
				setKills(uuid, 0);
			}
		} else {
			createPlayer(uuid);
			removeKills(uuid, kills);
		}
	}

	// DEATHS
	public static Integer getDeaths(String uuid) {
		Integer i = 0;

		if (playerExists(uuid)) {
			try {
				ResultSet rs = PlayerStats.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
				if ((!rs.next()) || (Integer.valueOf(rs.getInt("DEATHS")) == null))
					;

				i = rs.getInt("DEATHS");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			getDeaths(uuid);
		}
		return i;
	}

	public static void setDeaths(String uuid, Integer deaths) {
		if (playerExists(uuid)) {
			PlayerStats.mysql.update("UPDATE Stats SET DEATHS= '" + deaths + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setDeaths(uuid, deaths);
		}
	}

	public static void addDeaths(String uuid, Integer deaths) {
		if (playerExists(uuid)) {
			setDeaths(uuid, Integer.valueOf(getDeaths(uuid).intValue() + deaths.intValue()));
		} else {
			createPlayer(uuid);
			addDeaths(uuid, deaths);
		}
	}

	public static void removeDeaths(String uuid, Integer deaths) {
		if (playerExists(uuid)) {
			int i = Integer.valueOf(getDeaths(uuid).intValue() - deaths.intValue());
			if (i > 0) {
				setDeaths(uuid, i);
			} else {
				setDeaths(uuid, 0);
			}

		} else {
			createPlayer(uuid);
			removeDeaths(uuid, deaths);
		}
	}

	// WINS

	public static Integer getWins(String uuid) {
		Integer i = 0;

		if (playerExists(uuid)) {
			try {
				ResultSet rs = PlayerStats.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
				if ((!rs.next()) || (Integer.valueOf(rs.getInt("WINS")) == null))
					;

				i = rs.getInt("WINS");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			getWins(uuid);
		}
		return i;
	}

	public static void setWins(String uuid, Integer wins) {
		if (playerExists(uuid)) {
			PlayerStats.mysql.update("UPDATE Stats SET WINS= '" + wins + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setWins(uuid, wins);
		}
	}

	public static void addWins(String uuid, Integer wins) {
		if (playerExists(uuid)) {
			setWins(uuid, Integer.valueOf(getWins(uuid).intValue() + wins.intValue()));
		} else {
			createPlayer(uuid);
			addWins(uuid, wins);
		}
	}

	public static void removeWins(String uuid, Integer wins) {
		if (playerExists(uuid)) {
			int i = Integer.valueOf(getWins(uuid).intValue() - wins.intValue());
			if (i > 0) {
				setWins(uuid, i);
			} else {
				setWins(uuid, 0);
			}
		} else {
			createPlayer(uuid);
			removeWins(uuid, wins);
		}
	}

	// LOSES

	public static Integer getLoses(String uuid) {
		Integer i = 0;

		if (playerExists(uuid)) {
			try {
				ResultSet rs = PlayerStats.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
				if ((!rs.next()) || (Integer.valueOf(rs.getInt("LOSES")) == null))
					;

				i = rs.getInt("LOSES");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			getLoses(uuid);
		}
		return i;
	}

	public static void setLoses(String uuid, Integer loses) {
		if (playerExists(uuid)) {
			PlayerStats.mysql.update("UPDATE Stats SET LOSES= '" + loses + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setLoses(uuid, loses);
		}
	}

	public static void addLoses(String uuid, Integer loses) {
		if (playerExists(uuid)) {
			setLoses(uuid, Integer.valueOf(getLoses(uuid).intValue() + loses.intValue()));
		} else {
			createPlayer(uuid);
			addLoses(uuid, loses);
		}
	}

	public static void removeLoses(String uuid, Integer loses) {
		if (playerExists(uuid)) {
			int i = Integer.valueOf(getLoses(uuid).intValue() - loses.intValue());
			if (1 > 0) {
				setLoses(uuid, i);
			} else {
				setLoses(uuid, 0);
			}
		} else {
			createPlayer(uuid);
			removeLoses(uuid, loses);
		}
	}

	// GAMES

	public static Integer getGames(String uuid) {
		Integer i = 0;

		if (playerExists(uuid)) {
			try {
				ResultSet rs = PlayerStats.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
				if ((!rs.next()) || (Integer.valueOf(rs.getInt("LOSES")) == null))
					;

				i = rs.getInt("GAMES");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			createPlayer(uuid);
			getGames(uuid);
		}
		return i;
	}

	public static void setGames(String uuid, Integer games) {
		if (playerExists(uuid)) {
			PlayerStats.mysql.update("UPDATE Stats SET GAMES= '" + games + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setGames(uuid, games);
		}
	}

	public static void addGames(String uuid, Integer games) {
		if (playerExists(uuid)) {
			setGames(uuid, Integer.valueOf(getGames(uuid).intValue() + games.intValue()));
		} else {
			createPlayer(uuid);
			addGames(uuid, games);
		}
	}

	public static void removeGames(String uuid, Integer games) {
		if (playerExists(uuid)) {
			setGames(uuid, Integer.valueOf(getGames(uuid).intValue() - games.intValue()));
		} else {
			createPlayer(uuid);
			removeGames(uuid, games);
		}
	}

}